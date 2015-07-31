package cluedo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

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

	public static final Room[] ROOMS = { new Room("Kitchen"),
			new Room("Ball Room"), new Room("Conservatory"),
			new Room("Billiard Room"), new Room("Library"), new Room("Study"),
			new Room("Hall"), new Room("Lounge"), new Room("Dining Room") };

	private Board board;

	private Hud hud;

	private List<Player> players;

	public GameOfCluedo() throws InterruptedException {
		players = new ArrayList<>();
		initialise();
		run();
	}

	private void run() throws InterruptedException {
		while (true) {
		}
	}

	/**
	 * Sets the board up for the players
	 */
	private void initialise() {

		ROOMS[0].addTeleport(ROOMS[5]);
		ROOMS[5].addTeleport(ROOMS[0]);
		ROOMS[2].addTeleport(ROOMS[7]);
		ROOMS[7].addTeleport(ROOMS[2]);

		Scanner sc = new Scanner(System.in);
		System.out.println("----------------------");
		System.out.println("| Welcome to Cluedo! |");
		System.out.println("----------------------\n");
		System.out.println("How many players?");

		int numPlayers = getNumPlayers(sc);

		pickCharacter(sc, numPlayers);

		System.out.println("Dealing cards...");
		Deck deck = createDeck();
		deck.dealCards(players);

		try {
			board = new Board(ROOMS, SUSPECTS, WEAPONS);
		} catch (IOException e) {
			e.printStackTrace();
		}

		hud = new Hud(SUSPECTS);

		System.out.println("Ok, set! Player 1, start.\n");

		displayBoard(0);
	}

	/**
	 * Allows the players to select a character to play as. Removes the selected
	 * character from the pool.
	 *
	 * @param sc
	 *            Scanner for input.
	 */
	private void pickCharacter(Scanner sc, int numPlayers) {
		List<Suspect> tempSuspects = new ArrayList<Suspect>(
				Arrays.asList(SUSPECTS));

		System.out.println("\n=== SELECT PLAYERS ===\n");

		for (int i = 0; i < numPlayers; ++i) {
			System.out.println("Player " + (i + 1) + ", pick a suspect:\n");
			for (int j = 0; j < tempSuspects.size(); ++j) {
				System.out.println(String.format("%d. %s", j + 1, tempSuspects
						.get(j).getName()));
			}

			// Enter their name
			String name = enterName();

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
	}

	/**
	 * Allow the user to enter the name for their character.
	 *
	 * @return The name of the player
	 */
	private String enterName() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Creates the new Deck for the game. The deck deals with shuffling and
	 * distributing cards to Players.
	 *
	 * @return
	 */
	private Deck createDeck() {
		List<Card> weapons = new ArrayList<Card>(Arrays.asList(WEAPONS));
		List<Card> rooms = new ArrayList<Card>(Arrays.asList(ROOMS));
		List<Card> suspects = new ArrayList<Card>(Arrays.asList(SUSPECTS));

		return new Deck(weapons, rooms, suspects);
	}

	/**
	 * Gets the number of players for the game. Accepts only integer values.
	 *
	 * @return The number of players provided by Standard Input.
	 */
	private int getNumPlayers(Scanner sc) {
		for (;;) {
			try {
				int numPlayers = sc.nextInt();

				if (numPlayers > 2 && numPlayers < 7) {
					return numPlayers;
				} else {
					System.out
							.println("The number of players must be between 3 and 6(inclusive).\n Please try again:");
					sc.next();
					continue;
				}
			} catch (InputMismatchException e) {
				System.out.println("Please enter an integer:");
				sc.next();
				continue;
			}
		}
	}

	private void displayBoard(int suspect) {
		String result;
		boolean teleport = board.canTeleport(SUSPECTS[suspect]);

		Room temp = SUSPECTS[suspect].getRoom();
		if (temp != null)
			temp.showExits();

		for (int y = 0; y < 27; y++) {
			result = "";
			result = result + board.getLine(y, SUSPECTS[suspect]);
			result = result + "     ";
			result = result
					+ hud.display(y, SUSPECTS[suspect],
							teleport);
			System.out.print(result);

		}

		if (temp != null)
			temp.hideExits();
	}

	public static void main(String[] args) throws InterruptedException {
		new GameOfCluedo();
	}

}