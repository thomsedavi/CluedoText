package cluedo;

import java.io.IOException;

public class Test {

	public static void main(String[] args) {

		Suspect[] suspects = new Suspect[6];
		Weapon[] weapons = new Weapon[6];
		Room[] rooms = new Room[9];

		suspects[0] = new Suspect("Miss Scarlett", "mS", 8, 25);
		suspects[1] = new Suspect("Colonel Mustard", "cM", 1, 18);
		suspects[2] = new Suspect("Mrs White", "mW", 10, 1);
		suspects[3] = new Suspect("Rev Green", "rG", 15, 1);
		suspects[4] = new Suspect("Mrs Peacock", "mP", 24, 7);
		suspects[5] = new Suspect("Professor Plum", "pP", 24, 20);

		weapons[0] = new Weapon("Candlestick", "Cs");
		weapons[1] = new Weapon("Dagger", "Dg");
		weapons[2] = new Weapon("Lead Pipe", "Lp");
		weapons[3] = new Weapon("Revolver", "Rv");
		weapons[4] = new Weapon("Rope", "Rp");
		weapons[5] = new Weapon("Spanner", "Sp");

		rooms[0] = new Room("Kitchen");
		rooms[1] = new Room("Ball Room");
		rooms[2] = new Room("Conservatory");
		rooms[3] = new Room("Billiard Room");
		rooms[4] = new Room("Library");
		rooms[5] = new Room("Study");
		rooms[6] = new Room("Hall");
		rooms[7] = new Room("Lounge");
		rooms[8] = new Room("Dining Room");

		rooms[0].addTeleport(rooms[5]);
		rooms[5].addTeleport(rooms[0]);
		rooms[2].addTeleport(rooms[7]);
		rooms[7].addTeleport(rooms[2]);

		Board board = null;
		try {
			board = new Board(rooms, suspects, weapons);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int y = 0; y < 27; y++)
			board.getLine(y, suspects[2]);
	}
}
