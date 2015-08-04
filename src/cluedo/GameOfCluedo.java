package cluedo;

import java.awt.event.KeyListener;
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

	boolean isWon = false;

	int movesRemaining;

	private Card[] suggestion;

	private List<Player> players;

	private String CardToBeDisplayed;

	public GameOfCluedo() throws InterruptedException {
		players = new ArrayList<Player>();
		suggestion = new Card[3];
		Scanner sc = new Scanner(System.in);
		initialise(sc);
		run(sc);
		System.out.println("Game Over!");
	}

	/**
	 * Main game loop.
	 *
	 * @throws InterruptedException
	 */
	private void run(Scanner sc) throws InterruptedException {
		while (!isWon) { // checks if the game has been won
			for (Player player : players) {

				if (noMorePlayers()) { // Checks if there are more players
					System.out.println("No more players - everybody loses!");
					return;
				}

				if (player.isEliminated()) { // Checks if the player was eliminated from the game
					continue;
				}
				getHudInput(player, sc); // Present options to the player
			}
		}
	}

	/**
	 * Checks if everyone has been eliminated. If the number of eliminated
	 * players equals the number of players, then everyone is gone.
	 *
	 * @return If there's no more play
	 */
	private boolean noMorePlayers() {
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
	private void getHudInput(Player player, Scanner sc) {
		boolean turnOver = false;

		while (!turnOver) {
			STATUS status = STATUS.START_TURN; // Shouldn't ever be null. If
			// incorrect input, display the
			// board for the start of their
			// turn.
			displayBoard(player, status);

			System.out.println("\n\nChoose from the displayed actions: \n");
			String input = getStringInput(sc);

			switch (input) {

			case "c": // see cards
				status = displayCards(player, status, sc);
				break;
			case "d": // roll dice and move
				status = rollAndMove(player, status, sc);
				turnOver = true;
				break;
			case "a": // make accusation
				status = makeAccusation(player, status, sc);
				if(isWon){
					return;
				}
				turnOver = true;
				break;
			case "t": // try to teleport
				status = tryToTeleport(player, status, sc);
				turnOver = true;
				break;
			default:
				System.out.println("Try another option.");
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
	private int parseInteger(Scanner sc) {

		return 1;
	}

	/**
	 * Rolls the dice, and the player can choose where to move.
	 *
	 * @param player
	 * @param sc
	 */
	private STATUS rollAndMove(Player player, STATUS status, Scanner sc) {
		movesRemaining = rollDice();
		Suspect suspect = player.getSuspect();

		while (movesRemaining > 0) {

			if (suspect.isInRoom()) { // Check if you're in a room - and move
				// out of it
				status = STATUS.EXIT_ROOM;
				displayBoard(player, status);

				int i; // get the number of the exit

				while (true) {
					i = parseInteger(sc);
					if (board.canUseExit(suspect, i)) {
						board.exitRoom(suspect, i);
						break; // Break out of the user input loop
					}
				}
			} else { // Otherwise you are in the corridor
				status = STATUS.MOVE_PIECE;
				displayBoard(player, status);

				movePiece(player, status, sc);
				if(suspect.isInRoom()){
					makeSuggestion(player, status, sc);
					return STATUS.START_TURN;		//entered room so turn is over.
				}
			}
			System.out.println("\n");
			movesRemaining--;
			displayBoard(player, status);
		}
		return STATUS.START_TURN; // TODO - do I want to return here?
	}

	/**
	 * Calls for the player to move the piece.
	 *
	 * @param player
	 * @param status
	 * @param sc
	 */
	private void movePiece(Player player, STATUS status, Scanner sc) {
		System.out.println("\n\nYou have " + movesRemaining
				+ " moves remaining.");
		while (true) {
			String input = getStringInput(sc);

			Suspect suspect = player.getSuspect();

			switch (input) {

			case "n":
				if (move(suspect, Direction.NORTH)) {
					return;
				}
				break;
			case "s":
				if (move(suspect, Direction.SOUTH)) {
					return;
				}
				break;
			case "e":
				if (move(suspect, Direction.EAST)) {
					return;
				}
				break;
			case "w":
				if (move(suspect, Direction.WEST)) {
					return;
				}
				break;
			default:
				System.out.println("Please enter a direction.");
				System.out.println("\n");
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
	private STATUS makeAccusation(Player player, STATUS status, Scanner sc) {
		status = STATUS.CHOOSE_SUSPECT;
		displayBoard(player, status);
		Suspect suspect = selectCard(player, sc, SUSPECTS);

		status = STATUS.CHOOSE_ROOM;
		displayBoard(player, status);
		Room room = selectCard(player, sc, ROOMS);

		status = STATUS.CHOOSE_WEAPON;
		displayBoard(player, status);
		Weapon weapon = selectCard(player, sc, WEAPONS);

		if (deck.checkSolution(suspect, room, weapon)) {
			isWon = true;
			System.out.println("You guessed right!");
			return status;
		} else {
			System.out.println("You guessed wrong :(");
			System.out.println(player.getName() + " was eliminated from the game!");
			status = STATUS.START_TURN;
			player.eliminate();
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
	private STATUS makeSuggestion(Player player, STATUS status, Scanner sc) {

		List <Card> cards = new ArrayList<>();

		cards.add(player.getSuspect().getRoom()); // get the room the player's
		// suspect is in

		// now cycle around the players while the suggestion is made
		// Suggestion sg = new Suggestion(players);
		// Card [] weaponAndSuspect = sg.cyclePlayers();

		status = STATUS.CHOOSE_SUSPECT;
		displayBoard(player, status);
		cards.add(selectCard(player, sc, SUSPECTS));

		status = STATUS.CHOOSE_WEAPON;
		displayBoard(player, status);
		cards.add(selectCard(player, sc, WEAPONS));

		Card c;

		//cycle through players
		for(Player p : players){
			if(p.equals(player)){
				continue;
			}
			//cards
			if(p.qtyMatching(cards) > 0){
				status = STATUS.AWAIT_PLAYER;
				displayBoard(p, status);

				//get user input here

				//they press enter
				status = STATUS.REVEAL_CARD;  //this skips the player if they don't have suspicion cards
				displayBoard(p, status);

				//enter the string of the char code (that displays if its part of it)
			}
		}

//
//				String code;
//				while(true){
//					code = getStringInput(sc);
//					if(codeIsValid(code)){
//						break;
//					}
//				}
//				cardToBeDisplayed = code;
//				c = getCardFromCode(code);
//				return;

				//System.out.println(p.getName().has);
//
//			}
//		}
//
//		status = STATUS.AWAIT_PLAYER;
//
//		if (deck.checkSolution(suspect, room, weapon)) {
//			isWon = true;
//			System.out.println("You guessed right!");
//		} else {
//			System.out.println("You guessed wrong.");
//			System.out.println(player.getName() + " was eliminated from the game!");
//			player.eliminate();
//		}
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
		System.out.println("\nPlease select a number:\n");
		while (true) {
			try {
				i = sc.nextInt() - 1;
				if (i >= 0 && i < cards.length) {
					return cards[i];
				}
				System.out.println("Number must be within bounds!");
			} catch (InputMismatchException e) {
				System.out.println("Please enter an integer:");
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
	private STATUS tryToTeleport(Player player, STATUS status, Scanner sc) {
		Suspect playerSuspect = player.getSuspect();
		boolean canTeleport = board.canTeleport(playerSuspect);

		if (canTeleport) {
			board.teleport(playerSuspect);
			makeSuggestion(player, status, sc);
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
	private boolean move(Suspect suspect, Direction dir) {
		if (board.canMove(suspect, dir)) {
			board.moveSuspect(suspect, dir);
			return true;
		} else {
			System.out.println("\nYou can't move that way!");
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
	private STATUS displayCards(Player player, STATUS status, Scanner sc) {
		status = STATUS.SHOW_CARDS;
		displayBoard(player, status);

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
	private int rollDice() {
		// Random rand = new Random();
		// while(true){
		// int i = rand.nextInt(6) + 1;
		// if(i != 0){
		// return i;
		// }
		//
		// if(i > 6){
		// System.out.println("Dice roll is greater than 6" + i);
		// }
		// }
		return 20;
	}

	/**
	 * Sets the board up for the players
	 */
	private void initialise(Scanner sc) {
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

		System.out.println("\nOk, set! Player 1, start.\n");
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

			System.out.println("Player " + (i + 1) + ", pick a suspect:\n");
			for (int j = 0; j < tempSuspects.size(); ++j) {
				System.out.println(String.format("%d. %s", j + 1, tempSuspects
						.get(j).getName()));
			}

			// Adds the selected suspect to the players hand, removes from list
			for (;;) {
				try {
					int selection = sc.nextInt();

					if (selection > 0 && selection <= tempSuspects.size()) {
						Suspect selected = tempSuspects.get(selection - 1);

						tempSuspects.remove(selection - 1);

						System.out.print("You have selected "
								+ selected.getName() + ".\n");
						players.add(new Player(name, selected));
						selectedSuspects[counter] = selected;
						counter++;
						break; // break the loop, ready for the next player
					} else {
						System.out
						.println("That number wasn't an option.\n Please try again:");
						continue;
					}
				} catch (InputMismatchException e) {
					System.out.println("Please enter an integer:");
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

			while (true) {
				System.out.println("Is " + input + " the name you want?");
				String answer = sc.next();

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
		while (true) {
			try {
				int numPlayers = sc.nextInt();

				return numPlayers; // for testing ONLY. TODO
				// if (numPlayers > 2 && numPlayers < 7) {
				// return numPlayers;
				// } else {
				// System.out
				// .println("The number of players must be between 3 and 6(inclusive).\n Please try again:");
				// sc.next();
				// continue;
				// }
			} catch (InputMismatchException e) {
				System.out.println("Please enter an integer:");
				sc.next();
				continue;
			}
		}
	}

	private void displayBoard(Player player, STATUS status) {
		String result;
		Suspect suspect = player.getSuspect();
		boolean teleport = board.canTeleport(suspect);

		Room temp = suspect.getRoom();
		if (temp != null)
			temp.showExits();

		for (int y = 0; y < 27; y++) {
			result = "";
			result = result + board.getLine(y);
			if (y == suspect.getY())
				result = result + " <--   ";
			else
				result = result + "       ";
			result = result + hud.display(y, player, status, teleport);
			System.out.print(result);
		}

		if (temp != null)
			temp.hideExits();
	}

	public int movesRemaining() {
		return movesRemaining;
	}

	public Card[] getSuggestion() {
		return suggestion;
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

	public String getCardToBeDisplayed() {
		return CardToBeDisplayed;
	}
}