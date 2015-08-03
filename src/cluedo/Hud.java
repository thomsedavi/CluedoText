package cluedo;

/**
 * This returns lines representing the status of the game based on which line is
 * currently being 'scanned', who the current player is what the STATUS of the
 * game is. Contains references to all of the elements of the game.
 *
 * @author Pauline Kelly & David Thomsen
 */
public class Hud {

	public enum STATUS {
		START_TURN, SHOW_CARDS, MOVE_PIECE, EXIT_ROOM, CHOOSE_ROOM, CHOOSE_SUSPECT, CHOOSE_WEAPON, REVEAL_CARD, AWAIT_PLAYER;
	}

	Suspect[] suspects, playerSuspects;
	Weapon[] weapons;
	Room[] rooms;
	GameOfCluedo game;

	public Hud(Suspect[] playerSuspects, Suspect[] suspects, Weapon[] weapons,
			Room[] rooms, GameOfCluedo game) {
		this.playerSuspects = playerSuspects;
		this.suspects = suspects;
		this.weapons = weapons;
		this.rooms = rooms;
		this.game = game;
	}

	/**
	 * Calls a particular display mode depending on the Status of the game
	 *
	 * @param 'y' the horizontal line being scanned
	 * @param which
	 *            'player' is active 'status' of game 'teleport' is available to
	 *            the player or not
	 * @return
	 */
	public String display(int y, Player player, STATUS status, boolean teleport) {
		switch (status) {
		case START_TURN:
			return startTurn(y, player, teleport);
		case SHOW_CARDS:
			return showCards(y, player);
		case MOVE_PIECE:
			return movePiece(y, player);
		case EXIT_ROOM:
			return exitRoom(y, player);
		case CHOOSE_ROOM:
			return chooseRoom(y);
		case CHOOSE_SUSPECT:
			return chooseSuspect(y);
		case CHOOSE_WEAPON:
			return chooseWeapon(y);
		case REVEAL_CARD:
			return revealCard(y, player);
		case AWAIT_PLAYER:
			return awaitPlayer(y, player);
		}
		return "";
	}

	/**
	 * Used for when the game is waiting for a particular player to get to the
	 * console and the others have turned away.
	 */
	private String awaitPlayer(int y, Player player) {
		if (y == 0)
			return "Please press Enter when";
		else if (y == 1)
			return player.getName() + " is ready...";
		else
			return "";
	}

	/**
	 * When a Player has one or more Cards that have been Suggested by another
	 * player. This will display the Code of the Cards next to the cards that
	 * can be selected.
	 */
	private String revealCard(int y, Player player) {
		Card[] suggestion = game.getSuggestion();

		if (y >= player.getHand().size())
			return "";
		else {

			Card thisLine = player.getHand().get(y);

			for (Card c : suggestion) {
				if (thisLine.equals(c))
					return thisLine.toString() + " (" + thisLine.getCode()
							+ ")";
			}
			return thisLine.toString();
		}
	}

	/**
	 * Displays Weapons that can be chosen when a Player makes an Accusation or
	 * Suggestion
	 */
	private String chooseWeapon(int y) {
		if (y == 0)
			return "Please choose a Weapon:";
		if (y - 2 < weapons.length && y - 2 >= 0)
			return (y - 1) + ") " + weapons[y - 2].toString();
		else
			return "";
	}

	/**
	 * Displays Suspects that can be chosen when a Player makes an Accusation or
	 * Suggestion
	 */
	private String chooseSuspect(int y) {
		if (y == 0)
			return "Please choose a Suspect:";
		if (y - 2 < suspects.length && y - 2 >= 0)
			return (y - 1) + ") " + suspects[y - 2].toString();
		else
			return "";
	}

	/**
	 * Displays Rooms that can be chosen when a Player makes an Accusation or
	 * Suggestion
	 */
	private String chooseRoom(int y) {
		if (y == 0)
			return "Please choose a Room:";
		if (y - 2 < rooms.length && y - 2 >= 0)
			return (y - 1) + ") " + rooms[y - 2].toString();
		else
			return "";
	}

	/**
	 * Shows which player is moving, the code of their Suspect piece, how many
	 * moves are remaining to them and what the direction options are.
	 */
	private String movePiece(int y, Player player) {
		if (y == 0)
			return player.getName() + "'s turn ("
					+ player.getSuspect().getCode() + "):";
		else if (y == 1)
			return game.movesRemaining() + " moves remaining";
		else if (y == 3)
			return "    (N) for NORTH";
		else if (y == 4)
			return "(W) for WEST";
		else if (y == 5)
			return "      (E) for EAST";
		else if (y == 6)
			return "  (S) for SOUTH";
		else
			return "";
	}

	private String exitRoom(int y, Player player) {
		if (y == 0)
			return player.getName() + "'s turn ("
					+ player.getSuspect().getCode() + "):";
		else if (y == 2)
			return "Please select an Exit";
		else
			return "";
	}

	/**
	 * Shows all of the Cards the a Player has in their Hand.
	 */
	private String showCards(int y, Player player) {
		if (y == 0)
			return player.getName() + "'s Cards:";
		else if (y == 3 + player.getHand().size())
			return "Press E to return to main screen";
		else if (y - 2 < player.getHand().size() && y - 2 >= 0)
			return player.getHand().get(y - 2).toString();
		else
			return "";
	}

	/**
	 * Displays the options available to a Player at the beginning of their
	 * Turn.
	 */
	private String startTurn(int y, Player player, boolean teleport) {
		Suspect suspect = player.getSuspect();

		if (y == 0)
			return player.getName() + "'s turn!";
		else if (y - 2 < playerSuspects.length && y - 2 >= 0)
			if (suspect.equals(playerSuspects[y - 2]))
				return playerSuspects[y - 2].getName() + " ("
						+ playerSuspects[y - 2].getCode() + ") *";
			else
				return playerSuspects[y - 2].getName() + " ("
						+ playerSuspects[y - 2].getCode() + ")";
		else if (playerSuspects.length + 3 == y)
			return "Enter 'C' to see your Cards";
		else if (!teleport)
			if (playerSuspects.length + 4 == y)
				return "Enter 'D' to roll the Dice";
			else if (playerSuspects.length + 5 == y)
				return "Enter 'A' to make an Accusation";
			else
				return "";
		else if (playerSuspects.length + 4 == y)
			return "Enter 'D' to roll the Dice";
		else if (playerSuspects.length + 5 == y)
			return "Enter 'T' to Teleport";
		else if (playerSuspects.length + 6 == y)
			return "Enter 'A' to make an Accusation";
		else
			return "";
	}

}