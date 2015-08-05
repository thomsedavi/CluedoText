package cluedo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import cluedo.Hud.STATUS;

/**
 * Represents a game of Cluedo with a Board, a Deck and all of the Players.
 * Keeps track of the general status of game, including whose turn it is and how
 * many dice roll movements are remaining. Takes input from the user and parses
 * it into actions performed on the Board. Passes movement input from the user
 * onto the Board to confirm whether particular actions can be performed.
 * Contains lists of all the Suspects, Weapons and Rooms used in the game. Ends
 * once all Players have been eliminated or a correct Accusation has been made.
 *
 * @author David Thomsen & Pauline Kelly
 *
 */
public class GameOfCluedo {

	/**
	 * All Suspects, Weapons and Rooms have a name and a 2-digit code for
	 * representing the position on the board. Suspects have an additional X and
	 * Y coordinate representing their starting position.
	 */
	public static final Suspect[] SUSPECTS = {
			new Suspect("Miss Scarlett", "mS", 8, 25),
			new Suspect("Colonel Mustard", "cM", 1, 18),
			new Suspect("Mrs White", "mW", 10, 1),
			new Suspect("Rev Green", "rG", 15, 1),
			new Suspect("Mrs Peacock", "mP", 24, 7),
			new Suspect("Professor Plum", "pP", 24, 20) };

	public static final Weapon[] WEAPONS = { new Weapon("Candlestick", "Cs"),
			new Weapon("Dagger", "Dg"), new Weapon("Lead Pipe", "Lp"),
			new Weapon("Revolver", "Rv"), new Weapon("Rope", "Rp"),
			new Weapon("Spanner", "Sp") };

	public static final Room[] ROOMS = { new Room("Kitchen", "KI"),
			new Room("Ball Room", "BA"), new Room("Conservatory", "CO"),
			new Room("Billiard Room", "BI"), new Room("Library", "LI"),
			new Room("Study", "ST"), new Room("Hall", "HA"),
			new Room("Lounge", "LO"), new Room("Dining Room", "DR") };

	private Board board;
	private Deck deck;
	private Hud hud; // Heads-Up Display
	String message; // special message to sometimes be displayed at the start of
	// turns

	private List<Card> cards;
	STATUS status; // Passed into the Hud, which will display a different screen
					// depending on what state the game is in. For example,
					// MOVE_PIECE will show the amount of movements remaining
					// and the compass directions.

	boolean isWon = false; // game will play until true

	public List<Card> getCards() {
		return cards;
	}

	private int movesRemaining; // set to a dice roll then decremented with each
								// movement

	private List<Player> players;

	private Card cardToBeDisplayed; // The card to displayed when one player
									// makes a Suggestion and other player has
									// to reveal a particular card.

	public int inputCounter = 0; // FOR TESTING ONLY - COUNTS THE NUMBER OF
									// TIMES INPUT IS REQUIRED

	public GameOfCluedo() throws InterruptedException {
		cards = new ArrayList<>();
		players = new ArrayList<Player>();
		Scanner sc = new Scanner(System.in);
		initialise(sc);
		run(sc);
	}

	// TEST CONSTRUCTOR
	public GameOfCluedo(List<Player> players, List<Card> cards)
			throws InterruptedException {
		this.players = players;
		this.cards = cards;
	}

	/**
	 * Main game loop.
	 *
	 * @throws InterruptedException
	 */
	private void run(Scanner sc) throws InterruptedException {
		while (true) { // continue until return is given
			for (Player player : players) {

				if (playersRemaining() == 1) { // Checks if there are more
												// players. If only one is
												// remaining, that player wins
												// by default.
					for (Player winner : players) {
						if (!winner.isEliminated())
							status = STATUS.WIN_GAME;
						displayBoard(winner);
						return;
					}
				}

				if (player.isEliminated()) { // Checks if the player was
					// eliminated from the game
					continue;
				}
				getHudInput(player, sc); // Present options to the player
				if (isWon) {
					return;
				}
			}
		}
	}

	/**
	 * Checks if everyone has been eliminated. If the number of eliminated
	 * players equals the number of players, then everyone is gone.
	 *
	 * @return If there's no more players
	 */
	public int playersRemaining() {
		int playersRemaining = 0;
		for (Player p : this.players) {
			if (!p.isEliminated()) {
				playersRemaining++;
			}
		}
		return playersRemaining;
	}

	/**
	 * Interacts with the HUD based on player responses.
	 *
	 * @param player
	 * @param sc
	 */
	public void getHudInput(Player player, Scanner sc) {
		boolean turnOver = false;

		while (!turnOver) {

			status = STATUS.START_TURN; // Shouldn't ever be null. If
			// incorrect input, display the
			// board for the start of their
			// turn.

			displayBoard(player);

			String input = getStringInput(sc);
			inputCounter++;

			switch (input) {

			case "c": // see cards
				status = displayCards(player, sc);
				break;
			case "d": // roll dice and move
				status = rollAndMove(player, sc);
				turnOver = true;
				break;
			case "a": // make accusation
				status = makeAccusation(player, sc);
				// displayBoard(player);
				if (isWon) {
					return;
				}
				turnOver = true;
				break;
			case "t": // try to teleport
				if (!board.canTeleport(player.getSuspect())) {
					message = "Try another option.";
					break;
				}
				status = teleport(player, sc);
				turnOver = true;
				break;
			case "e": // Player cannot move, time to end it all!
				if (!board.canPlayTurn(player.getSuspect())) {
					turnOver = true;
				} else {
					message = "Try another option.";
				}
				break;
			default:
				message = "Try another option.";
			}
		}
	}

	/**
	 * Parse an integer from the user
	 *
	 * @param sc
	 * @return
	 */
	private int parseInteger(Player player, Scanner sc) {
		while (!sc.hasNextInt()) {
			message = "Please enter an integer";
			displayBoard(player);
			sc.next();
		}
		return sc.nextInt();
	}

	/**
	 * Rolls the dice, and the player can choose where to move.
	 *
	 * @param player
	 * @param sc
	 */
	private STATUS rollAndMove(Player player, Scanner sc) {
		movesRemaining = rollDice();
		message = player.getName() + " rolled a " + movesRemaining;
		Suspect suspect = player.getSuspect();

		while (movesRemaining > 0) {

			if (suspect.isInRoom()) { // Check if you're in a room - and move
				// out of it
				status = STATUS.EXIT_ROOM;
				displayBoard(player);

				int i; // get the number of the exit

				while (true) {
					i = parseInteger(player, sc);
					inputCounter++;
					if (board.canUseExit(suspect, i)) {
						board.exitRoom(suspect, i);
						break; // Break out of the user input loop
					} else {
						message = "Please select a valid exit";
						displayBoard(player);
					}
				}
			} else { // Otherwise you are in the corridor
				status = STATUS.MOVE_PIECE;
				displayBoard(player);

				movePiece(player, sc);
				if (suspect.isInRoom()) {
					makeSuggestion(player, sc);
					return STATUS.START_TURN; // entered room so turn is over.
				}
			}
			System.out.println("\n");
			movesRemaining--;
			displayBoard(player);
		}
		return STATUS.START_TURN;
	}

	/**
	 * Calls for the player to move the piece.
	 *
	 * @param player
	 * @param sc
	 */
	private void movePiece(Player player, Scanner sc) {
		while (true) {
			String input = getStringInput(sc);

			Suspect suspect = player.getSuspect();

			switch (input) {

			case "n":
				if (move(player, suspect, Direction.NORTH)) {
					return;
				}
				break;
			case "s":
				if (move(player, suspect, Direction.SOUTH)) {
					return;
				}
				break;
			case "e":
				if (move(player, suspect, Direction.EAST)) {
					return;
				}
				break;
			case "w":
				if (move(player, suspect, Direction.WEST)) {
					return;
				}
				break;
			default:
				message = "Please enter a direction.";
				displayBoard(player);
			}
		}
	}

	/**
	 * The player makes an accusation. They choose a Suspect, a Room and then a
	 * Weapon, then either wins or is eliminated from the game.
	 *
	 * @param player
	 *            current Player
	 * @param sc
	 *            the Scanner
	 * @return WIN_GAME status if player wins.
	 */
	private STATUS makeAccusation(Player player, Scanner sc) {
		cards.clear();

		status = STATUS.CHOOSE_SUSPECT;
		displayBoard(player);
		Suspect suspect = selectCard(player, sc, SUSPECTS);

		status = STATUS.CHOOSE_ROOM;
		displayBoard(player);
		Room room = selectCard(player, sc, ROOMS);

		status = STATUS.CHOOSE_WEAPON;
		displayBoard(player);
		Weapon weapon = selectCard(player, sc, WEAPONS);

		cards.add(room);
		cards.add(suspect);
		cards.add(weapon);

		if (deck.checkSolution(suspect, room, weapon)) {
			status = STATUS.WIN_GAME;
			displayBoard(player);
			isWon = true;
			return status;
		} else {
			message = "You guessed wrong :(\n" + player.getName()
					+ " was eliminated from the game!";
			player.eliminate();
			board.eliminateSuspect(player.getSuspect(), player.getSuspect()
					.getX(), player.getSuspect().getY());
		}
		return status;
	}

	/**
	 * The player makes an suggestion provided that A) they are in a room and B)
	 * that their suggestion includes the room they are in.
	 *
	 * @param player
	 * @param sc
	 * @return
	 */
	private STATUS makeSuggestion(Player player, Scanner sc) { // TODO

		cards.clear();
		Room room = player.getSuspect().getRoom();
		cards.add(room); // get the room the player's
		// suspect is in

		status = STATUS.CHOOSE_SUSPECT;
		message = "In the " + player.getSuspect().getRoom().getName() + "...";
		displayBoard(player);
		Suspect suspect = selectCard(player, sc, SUSPECTS);
		board.moveSuspectToRoom(suspect, room);
		cards.add(suspect);

		status = STATUS.CHOOSE_WEAPON;
		message = cards.get(1) + " in the "
				+ player.getSuspect().getRoom().getName() + " with...";
		displayBoard(player);
		Weapon weapon = selectCard(player, sc, WEAPONS);
		board.moveWeapon(weapon, room);
		cards.add(weapon);

		int thisPlayer = players.indexOf(player) + 1; // start loop from next
														// player after current
														// player and loops
														// around back to
														// current player.
		Player p = players.get(thisPlayer);

		do {
			if (p.qtyMatching(cards) > 0) {
				status = STATUS.AWAIT_PLAYER;
				displayBoard(p);

				while (!sc.hasNext())
					// Loops until this player confirms it is okay.
					;
				sc.next();

				status = STATUS.REVEAL_CARD; // Show screen with cards that can
												// be selected highlighted
				displayBoard(p);

				String code; // Get the char code
				while (true) {
					code = getStringInput(sc);
					if (deck.checkCodeIsValid(code)) {
						cardToBeDisplayed = deck.getCardFromCode(code);
						if (cards.contains(cardToBeDisplayed)
								&& p.getHand().contains(cardToBeDisplayed))
							break;
						else {
							message = "Not a valid card!";
							displayBoard(p);
						}
					} else {
						message = "Not a valid card!";
						displayBoard(p);
					}

				}

				status = STATUS.DISPLAY_CARD;
				displayBoard(p);

				while (!sc.hasNext()) //Waits for any input before moving on.
					;
				sc.next();

				return status;
			}
			thisPlayer++;
			thisPlayer %= players.size();
			p = players.get(thisPlayer);
		} while (!p.equals(player)); //ends loop when gets to current player
		message = "No matching cards!";
		return status;

	}

	/**
	 * Selects the card for the character to guess.
	 *
	 * @param player
	 * @param sc
	 * @return
	 */
	private <T> T selectCard(Player player, Scanner sc, T[] cards) {
		int i = -1;
		while (true) {
			try {
				i = sc.nextInt() - 1;
				if (i >= 0 && i < cards.length) {
					return cards[i];
				}
				message = "Number must be within bounds!";
				displayBoard(player);
			} catch (InputMismatchException e) {
				message = "Please enter an integer";
				displayBoard(player);
				sc.next();
			}
		}
	}

	/**
	 * Checks if the player can teleport or not. If not, returns standard menu
	 * for player turn.
	 *
	 * @param player
	 * @return
	 */
	private STATUS teleport(Player player, Scanner sc) {
		Suspect playerSuspect = player.getSuspect();
		boolean canTeleport = board.canTeleport(playerSuspect);

		if (canTeleport) {
			board.teleport(playerSuspect);
			makeSuggestion(player, sc);
			status = STATUS.CHOOSE_ROOM;
		}

		return status;
	}

	/**
	 * Try to move the piece
	 *
	 * @param suspect
	 * @param north
	 */
	private boolean move(Player player, Suspect suspect, Direction dir) {
		if (board.canMove(suspect, dir)) {
			board.moveSuspect(suspect, dir);
			return true;
		} else {
			message = "\nYou can't move that way!";
			displayBoard(player);
			return false;
		}
	}

	/**
	 * Displays the cards for the player.
	 *
	 * @param player
	 * @param sc
	 */
	private STATUS displayCards(Player player, Scanner sc) {
		status = STATUS.SHOW_CARDS;
		displayBoard(player);

		while (!sc.hasNext())
			;
		sc.next();
		return STATUS.START_TURN; // Returns the player to their main
		// menu
	}

	/**
	 * Simulates rolling the dice in the game.
	 *
	 * @return A random number between 1 and 6 for the player.
	 */
	public int rollDice() {
		Random rand = new Random();
		while (true) {
			int i = rand.nextInt(6) + 1;
			return i;
		}
	}

	/**
	 * Sets the board up for the players
	 */
	public void initialise(Scanner sc) {
		ROOMS[0].addTeleport(ROOMS[5]);
		ROOMS[5].addTeleport(ROOMS[0]);
		ROOMS[2].addTeleport(ROOMS[7]);
		ROOMS[7].addTeleport(ROOMS[2]);

		System.out.println("----------------------");
		System.out.println("| Welcome to Cluedo! |");
		System.out.println("----------------------\n");
		System.out.println("How many players?");

		int numPlayers = getNumPlayers(sc);

		Suspect[] suspects = pickCharacter(sc, numPlayers);

		System.out.println("Dealing cards...");
		deck = createDeck();
		deck.dealCards(players);

		try {
			board = new Board(ROOMS, suspects, WEAPONS);
		} catch (IOException e) {
			e.printStackTrace();
		}

		hud = new Hud(suspects, SUSPECTS, WEAPONS, ROOMS, this);

		message = "\nOk, set! Player 1, start.\n";
	}

	/**
	 * Allows the players to select a character to play as. Removes the selected
	 * character from the pool.
	 *
	 * @param sc
	 *            Scanner for input.
	 */
	private Suspect[] pickCharacter(Scanner sc, int numPlayers) {
		List<Suspect> tempSuspects = new ArrayList<Suspect>(
				Arrays.asList(SUSPECTS));
		Suspect[] selectedSuspects = new Suspect[numPlayers];
		int counter = 0;

		System.out.println("\n=== SELECT PLAYERS ===\n");

		for (int i = 0; i < numPlayers; ++i) {
			String name = enterName(i + 1, sc);

			System.out.println("\nPlayer " + (i + 1) + ", pick a suspect:\n");
			for (int j = 0; j < tempSuspects.size(); ++j) {
				System.out.println(String.format("%d. %s", j + 1, tempSuspects
						.get(j).getName()));
			}

			// Adds the selected suspect to the players hand, removes from list
			while (true) {
				try {
					int selection = sc.nextInt();
					inputCounter++;

					if (selection > 0 && selection <= tempSuspects.size()) {
						Suspect selected = tempSuspects.get(selection - 1);

						tempSuspects.remove(selection - 1);

						System.out.print("\nYou have selected "
								+ selected.getName() + ".\n\n\n");
						players.add(new Player(name, selected));
						selectedSuspects[counter] = selected;
						selected.makePresent();
						counter++;
						break; // break the loop, ready for the next player
					} else {
						message = "That number wasn't an option.\n Please try again:";
						continue;
					}
				} catch (InputMismatchException e) {
					message = "Please enter an integer";
					sc.next();
				}
			}
		}
		return selectedSuspects;
	}

	/**
	 * Allow the user to enter the name for their character.
	 *
	 * @return The name of the player
	 */
	private String enterName(int i, Scanner sc) {
		while (true) {
			System.out.println("Player " + i + ", please enter your name:");
			String input = sc.next();
			inputCounter++;

			while (true) {
				System.out.println("\nIs " + input + " the name you want?");
				String answer = sc.next();
				inputCounter++;

				if (answer.equalsIgnoreCase("Y")) {
					return input;
				} else if (answer.equalsIgnoreCase("N")) {
					break;
				} else {
					System.out.println("Please enter Y or N:");
					continue;
				}
			}
		}
	}

	/**
	 * Creates the new Deck for the game. The deck deals with shuffling and
	 * distributing cards to Players.
	 *
	 * @return
	 */
	private Deck createDeck() {
		List<Weapon> weapons = new ArrayList<Weapon>();
		weapons.addAll(Arrays.asList(WEAPONS));
		List<Room> rooms = new ArrayList<Room>();
		rooms.addAll(Arrays.asList(ROOMS));
		List<Suspect> suspects = new ArrayList<Suspect>();
		suspects.addAll(Arrays.asList(SUSPECTS));

		return new Deck(weapons, rooms, suspects);
	}

	/**
	 * Gets the number of players for the game. Accepts only integer values.
	 *
	 * @return The number of players provided by Standard Input.
	 */
	private int getNumPlayers(Scanner sc) {
		int numPlayers;
		while (true) {
			try {
				String input = sc.next();
				inputCounter++; // Ignores any letters
				System.out.println("input: " + input);

				numPlayers = Integer.parseInt(input);

				if (numPlayers > 2 && numPlayers < 7) {
					return numPlayers;
				} else {
					System.out
							.println("The number of players must be between 3 and 6 (inclusive).\n Please try again:");
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("Please enter an integer:");
			}
		}
	}

	private void displayBoard(Player player) {

		String result;
		Suspect suspect = player.getSuspect();
		boolean teleport = board.canTeleport(suspect);

		System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + message
				+ "\n");
		message = "";

		Room temp = suspect.getRoom();
		if (temp != null && status == STATUS.EXIT_ROOM)
			temp.showExits();

		for (int y = 0; y < 27; y++) {
			result = "";
			result = result + board.getLine(y);
			if (y == suspect.getY() && !suspect.isInRoom())
				result = result + " <-here   ";
			else
				result = result + "          ";
			result = result + hud.display(y, player, status, teleport);
			System.out.print(result);
		}

		if (temp != null)
			temp.hideExits();
	}

	public int movesRemaining() {
		return movesRemaining;
	}

	/**
	 * Returns a valid string TODO Check alphabetic char?
	 *
	 * @param sc
	 * @return
	 */
	private String getStringInput(Scanner sc) {
		return sc.next().toLowerCase();
	}

	public static void main(String[] args) throws InterruptedException {
		new GameOfCluedo();
	}

	public Card getCardToBeDisplayed() {
		return cardToBeDisplayed;
	}

	public Board getBoard() {
		return board;
	}

	public boolean playerIsEliminated(Suspect suspect) {
		for (Player p : players) {
			if (p.getSuspect().equals(suspect) && p.isEliminated())
				return true;
		}
		return false;
	}

	public List<Player> getPlayers() {
		return this.players;
	}
}