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
		new Room("Billiard Room", "BI"), new Room("Library", "LI"), new Room("Study", "ST"),
		new Room("Hall", "HA"), new Room("Lounge", "LO"), new Room("Dining Room", "DR") };

	private Board board;
	private Deck deck;
	private Hud hud;

	boolean isWon = false;

	int movesRemaining;

	private Card[] suggestion;

	private List<Player> players;

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
		while (!isWon) {						//checks if the game has been won
			for(Player player : players){

				if(player.isEliminated()){		//Checks if the player was eliminated from the game
					continue;
				}
				displayBoard(player, STATUS.START_TURN);
				getHudInput(player, sc);

				//If the player has been eliminated, remove them from the list of players now.
				//If it's the last player, then the game is over.
				if(player.isEliminated()){
					if(players.size() == 1){
						System.out.println("Everybody loses! ;)");
						return;
					}
					players.remove(player);  //concurrentModexception
				}	
			}
		}
	}

	/**
	 * Interacts with the HUD based on player responses.
	 * @param player
	 * @param sc
	 */
	private void getHudInput(Player player, Scanner sc) {
		boolean turnOver = false;

		STATUS status = STATUS.START_TURN;  //Shouldn't ever be null. If incorrect input, display the board for the start of their turn.

		while(!turnOver){
			System.out.println("\n\nChoose from the displayed actions: \n");
			String input = sc.next().toLowerCase();

			switch(input){

			case "c": //see cards
				status = displayCards(player, status, sc);
				break;
			case "d": //roll dice and move
				status = rollAndMove(player, status, sc);
				turnOver = true;
				break;
			case "a": //make accusation
				status = makeAccusation(player, status, sc);
				turnOver = true;
				break;
			case "t": //try to teleport
				status = tryToTeleport(player, status);
				turnOver = true;
				break;
			default:
				System.out.println("Try another option.");
				System.out.println("\n");
				continue;  //do not display the board if input is incorrect
			}
			System.out.println("\n");
			assert status != null;
			displayBoard(player, status);
		}
	}

	/**
	 * The player makes an accusation.
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

		if(deck.checkSolution(suspect, room, weapon)){
			isWon = true;
			System.out.println("You guessed right!");
		}
		else {
			System.out.println("You guessed wrong :(");
			player.eliminate();
		}
		status = STATUS.START_TURN;
		return status;
	}

	/**
	 * Selects the weapon for the character to guess.
	 * @param player
	 * @param sc
	 * @return
	 */
	private <T> T selectCard(Player player, Scanner sc, T[] cards) {
		int i = -1;
		System.out.println("\nPlease select a number:\n");
		while(true){
			try {
				i = sc.nextInt() - 1;
				if(i >= 0 && i < cards.length){
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
	 * Checks if the player can teleport or not. If not, returns standard menu for player turn.
	 * @param player
	 * @return
	 */
	private STATUS tryToTeleport(Player player, STATUS status) {
		Suspect playerSuspect = player.getSuspect();
		boolean canTeleport = board.canTeleport(playerSuspect);

		if(canTeleport){
			board.teleport(playerSuspect);
			status = STATUS.CHOOSE_ROOM;
		}

		return status;
	}

	/**
	 * Rolls the dice, and the player can choose where to move.
	 *
	 * @param player
	 * @param sc
	 */
	private STATUS rollAndMove(Player player, STATUS status, Scanner sc) {
		System.out.println("/n");
		status = STATUS.MOVE_PIECE;
		displayBoard(player, status);
		movesRemaining = rollDice();
		System.out.println("\nYou rolled a " + movesRemaining);

		while(movesRemaining != 0){
			movePiece(player, status, sc);
			movesRemaining--;
			displayBoard(player, status);
		}
		return status;
	}

	/**
	 * Calls for the player to move the piece.
	 *
	 * @param player
	 * @param status
	 * @param sc
	 */
	private void movePiece(Player player, STATUS status, Scanner sc) {
		System.out.println("\nSelect where to move the piece:\n N for North \n S for South \n E for East \n W for West. \nYou have " + movesRemaining + " moves remaining.");
		while(true){
			String input = sc.next();
			input = input.toLowerCase();

			Suspect suspect = player.getSuspect();

			switch(input){

			case "n":
				if(move(suspect,Direction.NORTH)){
					return;
				}
			case "s":
				if(move(suspect,Direction.SOUTH)){
					return;
				}
			case "e":
				if(move(suspect,Direction.EAST)){
					return;
				}
			case "w":
				if(move(suspect,Direction.WEST)){
					return;
				}
			default:
				System.out.println("Please enter a direction.");
				System.out.println("\n");
			}
		}
	}

	/**
	 * Try to move the piece
	 * @param suspect
	 * @param north
	 */
	private boolean move(Suspect suspect, Direction dir) {
		System.out.println("moving" + dir);
		if(board.canMove(suspect, dir))	{
			board.moveSuspect(suspect, dir);
			return true;
		}
		else {
			System.out.println("\n You can't move that way!\n");
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

		while(true){
			String input = sc.next();
			if(input.equalsIgnoreCase("E")){
				return STATUS.START_TURN;  //Returns the player to their main menu
			}
		}
	}

	/**
	 * Simulates rolling the dice in the game.
	 *
	 * @return A random number between 1 and 6 for the player.
	 */
	private int rollDice() {
		Random rand = new Random();
		return (rand.nextInt((6 - 1) + 1));
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
						System.out.println("That number wasn't an option.\n Please try again:");
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
		while(true){
			System.out.println("Player " + i + ", please enter your name:");
			String input = sc.next();

			while(true){
				System.out.println("Is " + input +" the name you want?");
				String answer = sc.next();

				if(answer.equalsIgnoreCase("Y")){
					return input;
				}
				else if(answer.equalsIgnoreCase("N")){
					break;
				}
				else {
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
		List<Card> weapons = Arrays.asList(WEAPONS);
		List<Card> rooms = Arrays.asList(ROOMS);
		List<Card> suspects = Arrays.asList(SUSPECTS);

		return new Deck(weapons, rooms, suspects);
	}

	/**
	 * Gets the number of players for the game. Accepts only integer values.
	 *
	 * @return The number of players provided by Standard Input.
	 */
	private int getNumPlayers(Scanner sc) {
		while(true) {
			try {
				int numPlayers = sc.nextInt();

				return numPlayers;	//for testing ONLY. TODO
				//				if (numPlayers > 2 && numPlayers < 7) {
				//					return numPlayers;
				//				} else {
				//					System.out
				//					.println("The number of players must be between 3 and 6(inclusive).\n Please try again:");
				//					sc.next();
				//					continue;
				//				}
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
				result = result + " <-- ";
			else
			result = result + "     ";
			result = result
					+ hud.display(y, player,
							status, teleport);
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

	public static void main(String[] args) throws InterruptedException {
		new GameOfCluedo();
	}
}