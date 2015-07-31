package cluedo;

public class Hud {

	public enum STATUS {
		START_TURN, SHOW_CARDS, MOVE_PIECE, CHOOSE_ROOM, CHOOSE_SUSPECT, CHOOSE_WEAPON, REVEAL_CARD, AWAIT_PLAYER;
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

	public String display(int y, Player player, STATUS status, boolean teleport) {
		switch (status) {
		case START_TURN:
			return startTurn(y, player, teleport);
		case SHOW_CARDS:
			return showCards(y, player);
		case MOVE_PIECE:
			return movePiece(y, player);
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

	private String awaitPlayer(int y, Player player) {
		if (y == 0)
			return "Please press Enter when";
		else if (y == 1)
			return player.getName() + " is ready...";
		else
			return "";
	}

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

	private String chooseWeapon(int y) {
		if (y == 0)
			return "Please choose a Weapon:";
		if (y - 2 < weapons.length && y - 2 >= 0)
			return (y - 1) + ") " + weapons[y - 2].toString();
		else
			return "";
	}

	private String chooseSuspect(int y) {
		if (y == 0)
			return "Please choose a Suspect:";
		if (y - 2 < suspects.length && y - 2 >= 0)
			return (y - 1) + ") " + suspects[y - 2].toString();
		else
			return "";
	}

	private String chooseRoom(int y) {
		if (y == 0)
			return "Please choose a Room:";
		if (y - 2 < rooms.length && y - 2 >= 0)
			return (y - 1) + ") " + rooms[y - 2].toString();
		else
			return "";
	}

	private String movePiece(int y, Player player) {
		if (y == 0)
			return player.getName() + "'s turn";
		else if (y == 1)
			return game.movesRemaining() + " moves remaining";
		else
			return "";
	}

	private String showCards(int y, Player player) {
		if (y == 0)
			return player.getName() + "'s Cards:";
		else if (y == 3 + player.getHand().size())
			return "Press Enter to return to main screen";
		else if (y - 2 < player.getHand().size() && y - 2 >= 0)
			return player.getHand().get(y - 2).toString();
		else
			return "";
	}

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