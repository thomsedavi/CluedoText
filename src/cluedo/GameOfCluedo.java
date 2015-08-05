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
 *
 * @author David Thomsen & Pauline Kelly
 *
 */
public class GameOfCluedo {

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
	private Hud hud;
	String message; // special message to sometimes be displayed at the start of
	// turns

	private List<Card> cards;
	STATUS status;

	boolean isWon = false;

	public List<Card> getCards() {
		return cards;
	}

	private int movesRemaining;

	private List<Player> players;

	private Card cardToBeDisplayed;

	public int inputCounter = 0;	//FOR TESTING ONLY - COUNTS THE NUMBER OF TIMES INPUT IS REQUIRED

	public GameOfCluedo() throws InterruptedException {
		cards = new ArrayList<>();
		players = new ArrayList<Player>();
		Scanner sc = new Scanner(System.in);
		initialise(sc);
		run(sc);
	}

	//TEST CONSTRUCTOR
	public GameOfCluedo(List<Player> players, List<Card> cards) throws InterruptedException {
		this.players = players;
		this.cards = cards;
	}

	/**
	 * Main game loop.
	 *
	 * @throws InterruptedException
	 */
	private void run(Scanner sc) throws InterruptedException {
		while (true) { // checks if the game has been won
			for (Player player : players) {

				if (noMorePlayers()) { // Checks if there are more players
					System.out.println("No more players - everybody loses!");
					return;
				}

				if (player.isEliminated()) { // Checks if the player was
					// eliminated from the game
					continue;
				}
				getHudInput(player, sc); // Present options to the player
				if(isWon){
					return;
				}
			}
		}
	}

	/**
	 * Checks if everyone has been eliminated. If the number of eliminated
	 * players equals the number of players, then everyone is gone.
	 *
	 * @return If there's no more play
	 */
	public boolean noMorePlayers() {
		int eliminated = 0;
		for (Player p : this.players) {
			if (p.isEliminated()) {
				eliminated++;
			}
		}

		if (players.size() - eliminated <= 0) {
			return true;
		}
		return false;
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

			// System.out.println("\n\nChoose from the displayed actions: \n");
			String input = getStringInput(sc); inputCounter++;

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
				status = tryToTeleport(player, sc);
				turnOver = true;
				break;
			case "e": // Player cannot move, time to end it all!
				if (!board.canPlayTurn(player.getSuspect()))
					turnOver = true;
				break;
			default:
				message = "Try another option.";
			}

			// System.out.println("\n");
			assert status != null;
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
					i = parseInteger(player, sc); inputCounter++;
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
	 * @param status
	 * @param sc
	 */
	private void movePiece(Player player, Scanner sc) {
		// System.out.println("\n\nYou have " + movesRemaining
		// + " moves remaining.");
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
	 * The player makes an accusation.
	 *
	 * @param player
	 * @param status
	 * @param sc
	 * @return
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
			// status = STATUS.START_TURN;
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
	 * @param status
	 * @param sc
	 * @return
	 */
	private STATUS makeSuggestion(Player player, Scanner sc) { // TODO

		cards.clear();
		cards.add(player.getSuspect().getRoom()); // get the room the player's
		// suspect is in

		status = STATUS.CHOOSE_SUSPECT;
		message = "In the " + player.getSuspect().getRoom().getName() + "...";
		displayBoard(player);
		cards.add(selectCard(player, sc, SUSPECTS));

		status = STATUS.CHOOSE_WEAPON;
		message = cards.get(1) + " in the "
				+ player.getSuspect().getRoom().getName() + " with...";
		displayBoard(player);
		cards.add(selectCard(player, sc, WEAPONS));

		for (Player p : players) {
			if (p.equals(player)) {
				continue;
			}
			if (p.qtyMatching(cards) > 0) {
				status = STATUS.AWAIT_PLAYER;
				displayBoard(p);

				while (true) {
					String input = sc.next();
					if (input.equalsIgnoreCase("E")) {
						break;
					}
				}

				status = STATUS.REVEAL_CARD; // this skips the player if they
				// don't have suspicion cards
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

				// wait for input (get an e)
				String input;
				while (true) {
					input = getStringInput(sc);
					if (input.equals("e")) {
						break;
					}
				}

				return status;
				// enter the string of the char code (that displays if its part
				// of it)
			}
		}
		message = "No matching cards!";
		return status;

	}

	/**
	 * Selects the weapon for the character to guess.
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
	private STATUS tryToTeleport(Player player, Scanner sc) {
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
	 * @param status
	 * @param sc
	 */
	private STATUS displayCards(Player player, Scanner sc) {
		status = STATUS.SHOW_CARDS;
		displayBoard(player);

		while (true) {
			String input = sc.next();
			if (input.equalsIgnoreCase("E")) {
				return STATUS.START_TURN; // Returns the player to their main
				// menu
			}
		}
	}

	/**
	 * Simulates rolling the dice in the game.
	 *
	 * @return A random number between 1 and 6 for the player.
	 */
	public int rollDice() {
		Random rand = new Random();
		while(true){
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
			while(true) {
				try {
					int selection = sc.nextInt(); inputCounter++;

					if (selection > 0 && selection <= tempSuspects.size()) {
						Suspect selected = tempSuspects.get(selection - 1);

						tempSuspects.remove(selection - 1);

						System.out.print("\nYou have selected "
								+ selected.getName() + ".\n\n\n");
						players.add(new Player(name, selected));
						selectedSuspects[counter] = selected;
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
			String input = sc.next(); inputCounter++;

			while (true) {
				System.out.println("\nIs " + input + " the name you want?");
				String answer = sc.next(); inputCounter++;

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
				String input = sc.next();  inputCounter++;  //Ignores any letters
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