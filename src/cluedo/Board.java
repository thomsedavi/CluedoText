package cluedo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representing a Board of cluedo, containing a grid of 26x27 Tiles and a
 * reference to all of the Rooms on the board. All functionality involving the
 * moving of pieces around the board and checking whether they can move is done
 * through this class.
 *
 * @author Pauline Kelly & David Thomsen
 */
public class Board {

	public static final int BOARD_WIDTH = 26;
	public static final int BOARD_HEIGHT = 27;

	Tile[][] tiles;
	Room[] rooms;

	/* Create each of the rooms */
	public Board(Room[] rooms, Suspect[] suspects, Weapon[] weapons) {

		this.rooms = rooms;
		tiles = new Tile[BOARD_WIDTH][BOARD_HEIGHT];

		for (int y = 0; y < BOARD_HEIGHT; y++) {
			for (int x = 0; x < BOARD_WIDTH; x++) {
				tiles[x][y] = new Wall();
			}
		}

		for (int y = 2; y < 25; y++) {
			for (int x = 1; x < 25; x++) {
				tiles[x][y] = new Corridor();
			}
		}

		for (int y = 2; y < 25; y++) {
			for (int x = 1; x < 25; x++) {
				tiles[x][y] = new Corridor();
			}
		}

		for (int y = 11; y < 18; y++) {
			for (int x = 11; x < 16; x++) {
				tiles[x][y] = new TextTile("~~");
			}
		}

		for (int y = 2; y < 8; y++) {
			for (int x = 1; x < 7; x++) {
				tiles[x][y] = new RoomTile(rooms[0], 0);
			}
		}

		/* Set each of these tiles to be a new room tile */
		tiles[1][2] = new RoomTile(rooms[0], 1);
		tiles[3][2] = new RoomTile(rooms[0], 2);
		tiles[5][2] = new RoomTile(rooms[0], 3);
		tiles[1][3] = new RoomTile(rooms[0], 4);
		tiles[3][3] = new RoomTile(rooms[0], 5);
		tiles[5][3] = new RoomTile(rooms[0], 6);
		tiles[1][6] = new RoomTile(rooms[0], -1);
		tiles[3][6] = new RoomTile(rooms[0], -2);
		tiles[5][6] = new RoomTile(rooms[0], -3);
		tiles[1][5] = new RoomTile(rooms[0], -4);
		tiles[3][5] = new RoomTile(rooms[0], -5);
		tiles[5][5] = new RoomTile(rooms[0], -6);

		for (int y = 2; y < 9; y++) {
			for (int x = 9; x < 17; x++) {
				tiles[x][y] = new RoomTile(rooms[1], 0);
			}
		}

		tiles[11][3] = new RoomTile(rooms[1], 1);
		tiles[13][3] = new RoomTile(rooms[1], 2);
		tiles[15][3] = new RoomTile(rooms[1], 3);
		tiles[10][4] = new RoomTile(rooms[1], 4);
		tiles[12][4] = new RoomTile(rooms[1], 5);
		tiles[14][4] = new RoomTile(rooms[1], 6);
		tiles[10][7] = new RoomTile(rooms[1], -1);
		tiles[12][7] = new RoomTile(rooms[1], -2);
		tiles[14][7] = new RoomTile(rooms[1], -3);
		tiles[11][6] = new RoomTile(rooms[1], -4);
		tiles[13][6] = new RoomTile(rooms[1], -5);
		tiles[15][6] = new RoomTile(rooms[1], -6);

		for (int y = 2; y < 7; y++) {
			for (int x = 19; x < 25; x++) {
				tiles[x][y] = new RoomTile(rooms[2], 0);
			}
		}

		tiles[20][2] = new RoomTile(rooms[2], 1);
		tiles[22][2] = new RoomTile(rooms[2], 2);
		tiles[24][2] = new RoomTile(rooms[2], 3);
		tiles[21][3] = new RoomTile(rooms[2], 4);
		tiles[23][3] = new RoomTile(rooms[2], 5);
		tiles[22][3] = new RoomTile(rooms[2], 6);
		tiles[20][5] = new RoomTile(rooms[2], -1);
		tiles[22][5] = new RoomTile(rooms[2], -2);
		tiles[24][5] = new RoomTile(rooms[2], -3);
		tiles[21][5] = new RoomTile(rooms[2], -4);
		tiles[23][5] = new RoomTile(rooms[2], -5);
		tiles[24][4] = new RoomTile(rooms[2], -6);

		for (int y = 9; y < 14; y++) {
			for (int x = 19; x < 25; x++) {
				tiles[x][y] = new RoomTile(rooms[3], 0);
			}
		}

		tiles[20][10] = new RoomTile(rooms[3], 1);
		tiles[22][10] = new RoomTile(rooms[3], 2);
		tiles[24][10] = new RoomTile(rooms[3], 3);
		tiles[21][10] = new RoomTile(rooms[3], 4);
		tiles[23][10] = new RoomTile(rooms[3], 5);
		tiles[24][9] = new RoomTile(rooms[3], 6);
		tiles[20][12] = new RoomTile(rooms[3], -1);
		tiles[22][12] = new RoomTile(rooms[3], -2);
		tiles[24][12] = new RoomTile(rooms[3], -3);
		tiles[21][12] = new RoomTile(rooms[3], -4);
		tiles[23][12] = new RoomTile(rooms[3], -5);
		tiles[24][13] = new RoomTile(rooms[3], -6);

		for (int y = 15; y < 20; y++) {
			for (int x = 18; x < 25; x++) {
				tiles[x][y] = new RoomTile(rooms[4], 0);
			}
		}

		tiles[19][16] = new RoomTile(rooms[4], 1);
		tiles[21][16] = new RoomTile(rooms[4], 2);
		tiles[23][16] = new RoomTile(rooms[4], 3);
		tiles[20][16] = new RoomTile(rooms[4], 4);
		tiles[22][16] = new RoomTile(rooms[4], 5);
		tiles[24][16] = new RoomTile(rooms[4], 6);
		tiles[19][18] = new RoomTile(rooms[4], -1);
		tiles[21][18] = new RoomTile(rooms[4], -2);
		tiles[23][18] = new RoomTile(rooms[4], -3);
		tiles[20][18] = new RoomTile(rooms[4], -4);
		tiles[22][18] = new RoomTile(rooms[4], -5);
		tiles[24][18] = new RoomTile(rooms[4], -6);

		for (int y = 22; y < 26; y++) {
			for (int x = 18; x < 25; x++) {
				tiles[x][y] = new RoomTile(rooms[5], 0);
			}
		}

		tiles[24][24] = new RoomTile(rooms[5], 1);
		tiles[22][24] = new RoomTile(rooms[5], 2);
		tiles[20][24] = new RoomTile(rooms[5], 3);
		tiles[23][24] = new RoomTile(rooms[5], 4);
		tiles[21][24] = new RoomTile(rooms[5], 5);
		tiles[19][24] = new RoomTile(rooms[5], 6);
		tiles[19][25] = new RoomTile(rooms[5], -1);
		tiles[21][25] = new RoomTile(rooms[5], -2);
		tiles[23][25] = new RoomTile(rooms[5], -3);
		tiles[20][25] = new RoomTile(rooms[5], -4);
		tiles[22][25] = new RoomTile(rooms[5], -5);
		tiles[24][25] = new RoomTile(rooms[5], -6);

		for (int y = 19; y < 26; y++) {
			for (int x = 10; x < 16; x++) {
				tiles[x][y] = new RoomTile(rooms[6], 0);
			}
		}

		tiles[11][20] = new RoomTile(rooms[6], 1);
		tiles[13][20] = new RoomTile(rooms[6], 2);
		tiles[14][21] = new RoomTile(rooms[6], 3);
		tiles[12][21] = new RoomTile(rooms[6], 4);
		tiles[12][20] = new RoomTile(rooms[6], 5);
		tiles[13][21] = new RoomTile(rooms[6], 6);
		tiles[14][25] = new RoomTile(rooms[6], -1);
		tiles[12][25] = new RoomTile(rooms[6], -2);
		tiles[11][24] = new RoomTile(rooms[6], -3);
		tiles[13][24] = new RoomTile(rooms[6], -4);
		tiles[14][23] = new RoomTile(rooms[6], -5);
		tiles[12][23] = new RoomTile(rooms[6], -6);

		for (int y = 20; y < 26; y++) {
			for (int x = 1; x < 8; x++) {
				tiles[x][y] = new RoomTile(rooms[7], 0);
			}
		}

		tiles[1][21] = new RoomTile(rooms[7], 1);
		tiles[3][21] = new RoomTile(rooms[7], 2);
		tiles[5][21] = new RoomTile(rooms[7], 3);
		tiles[2][23] = new RoomTile(rooms[7], 4);
		tiles[4][23] = new RoomTile(rooms[7], 5);
		tiles[6][23] = new RoomTile(rooms[7], 6);
		tiles[2][25] = new RoomTile(rooms[7], -1);
		tiles[4][25] = new RoomTile(rooms[7], -2);
		tiles[6][25] = new RoomTile(rooms[7], -3);
		tiles[1][24] = new RoomTile(rooms[7], -4);
		tiles[3][24] = new RoomTile(rooms[7], -5);
		tiles[5][24] = new RoomTile(rooms[7], -6);

		for (int y = 10; y < 17; y++) {
			for (int x = 1; x < 9; x++) {
				tiles[x][y] = new RoomTile(rooms[8], 0);
			}
		}

		tiles[1][11] = new RoomTile(rooms[8], 1);
		tiles[3][11] = new RoomTile(rooms[8], 2);
		tiles[5][11] = new RoomTile(rooms[8], 3);
		tiles[2][12] = new RoomTile(rooms[8], 4);
		tiles[4][12] = new RoomTile(rooms[8], 5);
		tiles[6][12] = new RoomTile(rooms[8], 6);
		tiles[1][15] = new RoomTile(rooms[8], -1);
		tiles[3][15] = new RoomTile(rooms[8], -2);
		tiles[5][15] = new RoomTile(rooms[8], -3);
		tiles[2][14] = new RoomTile(rooms[8], -4);
		tiles[4][14] = new RoomTile(rooms[8], -5);
		tiles[6][14] = new RoomTile(rooms[8], -6);

		Exit exit;

		tiles[1][0] = new TextTile("  ");
		tiles[2][0] = new TextTile("CL");
		tiles[3][0] = new TextTile("UE");
		tiles[4][0] = new TextTile("DO");
		tiles[5][0] = new TextTile("  ");
		tiles[10][1] = new Corridor();
		tiles[15][1] = new Corridor();
		tiles[6][2] = new TextTile("==");
		tiles[7][2] = new Wall();
		tiles[9][2] = new Corridor();
		tiles[10][2] = new Corridor();
		tiles[15][2] = new Corridor();
		tiles[16][2] = new Corridor();
		tiles[18][2] = new Wall();
		tiles[2][4] = new TextTile("KI");
		tiles[3][4] = new TextTile("TC");
		tiles[4][4] = new TextTile("HE");
		tiles[5][4] = new TextTile("N\u2591");
		tiles[20][4] = new TextTile("CO");
		tiles[21][4] = new TextTile("NS");
		tiles[22][4] = new TextTile("ER");
		tiles[23][4] = new TextTile("VA");
		tiles[11][5] = new TextTile("BA");
		tiles[12][5] = new TextTile("LL");
		tiles[13][5] = new TextTile(" R");
		tiles[14][5] = new TextTile("OO");
		tiles[15][5] = new TextTile("M\u2591");
		tiles[19][5] = new Door(19, 5, Direction.SOUTH, rooms[2]);
		exit = new Exit("01");
		tiles[8][6] = exit;
		rooms[1].addExit(exit);
		tiles[9][6] = new Door(9, 6, Direction.WEST, rooms[1]);
		tiles[16][6] = new Door(16, 6, Direction.EAST, rooms[1]);
		exit = new Exit("04");
		tiles[17][6] = exit;
		rooms[1].addExit(exit);
		exit = new Exit("01");
		tiles[19][6] = exit;
		rooms[2].addExit(exit);
		tiles[23][6] = new TextTile("==");
		tiles[24][6] = new Wall();
		tiles[1][7] = new Wall();
		tiles[5][7] = new Door(5, 7, Direction.SOUTH, rooms[0]);
		exit = new Exit("01");
		tiles[5][8] = exit;
		rooms[0].addExit(exit);
		tiles[10][8] = new Door(10, 8, Direction.SOUTH, rooms[1]);
		tiles[15][8] = new Door(15, 8, Direction.SOUTH, rooms[1]);
		tiles[24][8] = new Wall();
		tiles[1][9] = new Wall();
		exit = new Exit("02");
		tiles[10][9] = exit;
		rooms[1].addExit(exit);
		exit = new Exit("03");
		tiles[15][9] = exit;
		rooms[1].addExit(exit);
		tiles[6][10] = new Corridor();
		tiles[7][10] = new Corridor();
		tiles[8][10] = new Corridor();
		exit = new Exit("01");
		tiles[18][10] = exit;
		rooms[3].addExit(exit);
		tiles[19][10] = new Door(19, 10, Direction.WEST, rooms[3]);
		tiles[20][11] = new TextTile("BI");
		tiles[21][11] = new TextTile("LL");
		tiles[22][11] = new TextTile("IA");
		tiles[23][11] = new TextTile("RD");
		tiles[2][13] = new TextTile("DI");
		tiles[3][13] = new TextTile("NI");
		tiles[4][13] = new TextTile("NG");
		tiles[5][13] = new TextTile(" R");
		tiles[6][13] = new TextTile("OO");
		tiles[7][13] = new TextTile("M\u2591");
		tiles[8][13] = new Door(8, 13, Direction.EAST, rooms[8]);
		exit = new Exit("01");
		tiles[9][13] = exit;
		rooms[8].addExit(exit);
		tiles[23][13] = new Door(23, 13, Direction.SOUTH, rooms[3]);
		exit = new Exit("01");
		tiles[21][14] = exit;
		rooms[4].addExit(exit);
		exit = new Exit("02");
		tiles[23][14] = exit;
		rooms[3].addExit(exit);
		tiles[24][14] = new Wall();
		tiles[18][15] = new Corridor();
		tiles[21][15] = new Door(21, 15, Direction.NORTH, rooms[4]);
		tiles[24][15] = new Wall();
		tiles[7][16] = new Door(7, 16, Direction.SOUTH, rooms[8]);
		tiles[1][17] = new Wall();
		exit = new Exit("02");
		tiles[7][17] = exit;
		rooms[8].addExit(exit);
		exit = new Exit("02");
		tiles[17][17] = exit;
		rooms[4].addExit(exit);
		tiles[18][17] = new Door(18, 17, Direction.WEST, rooms[4]);
		tiles[20][17] = new TextTile("LI");
		tiles[21][17] = new TextTile("BR");
		tiles[22][17] = new TextTile("AR");
		tiles[23][17] = new TextTile("Y\u2591");
		exit = new Exit("01");
		tiles[12][18] = exit;
		rooms[6].addExit(exit);
		exit = new Exit("02");
		tiles[13][18] = exit;
		rooms[6].addExit(exit);
		tiles[1][19] = new Wall();
		exit = new Exit("01");
		tiles[7][19] = exit;
		rooms[7].addExit(exit);
		tiles[12][19] = new Door(12, 19, Direction.NORTH, rooms[6]);
		tiles[13][19] = new Door(13, 19, Direction.NORTH, rooms[6]);
		tiles[18][19] = new Corridor();
		tiles[24][19] = new Wall();
		tiles[1][20] = new TextTile("==");
		tiles[7][20] = new Door(7, 20, Direction.NORTH, rooms[7]);
		tiles[15][21] = new Door(15, 21, Direction.EAST, rooms[6]);
		exit = new Exit("03");
		tiles[16][21] = exit;
		rooms[6].addExit(exit);
		exit = new Exit("01");
		tiles[18][21] = exit;
		rooms[5].addExit(exit);
		tiles[24][21] = new Wall();
		tiles[3][22] = new TextTile("LO");
		tiles[4][22] = new TextTile("UN");
		tiles[5][22] = new TextTile("GE");
		tiles[12][22] = new TextTile("HA");
		tiles[13][22] = new TextTile("LL");
		tiles[18][22] = new Door(18, 22, Direction.NORTH, rooms[5]);
		tiles[24][22] = new TextTile("==");
		tiles[20][23] = new TextTile("ST");
		tiles[21][23] = new TextTile("UD");
		tiles[22][23] = new TextTile("Y\u2591");
		tiles[7][25] = new Wall();
		tiles[8][25] = new Corridor();
		tiles[17][25] = new Corridor();
		tiles[18][25] = new Wall();

		// TODO obviously fix this

		for (Suspect s : suspects) {
			switch (s.getCode()) {
			case "mS":
				tiles[8][25].setSuspect(s);
				s.setLocation(8, 25);
				break;
			case "cM":
				tiles[1][18].setSuspect(s);
				s.setLocation(1, 18);
				break;
			case "mW":
				tiles[10][1].setSuspect(s);
				s.setLocation(10, 1);
				break;
			case "rG":
				tiles[15][1].setSuspect(s);
				s.setLocation(15, 1);
				break;
			case "mP":
				tiles[24][7].setSuspect(s);
				s.setLocation(24, 7);
				break;
			case "pP":
				tiles[24][20].setSuspect(s);
				s.setLocation(24, 20);
				break;
			}
		}

		List<Integer> randRoom = new ArrayList<Integer>();

		for (int x = 0; x < 9; x++)
			randRoom.add(x);

		Collections.shuffle(randRoom);

		for (int x = 0; x < 6; x++) {
			rooms[randRoom.get(x)].addWeapon(weapons[x]);
			weapons[x].setRoom(rooms[randRoom.get(x)]);
		}
	}

	public boolean canMove(Suspect suspect, Direction direction) {
		int x = suspect.getX();
		int y = suspect.getY();
		switch (direction) {
		case NORTH:
			return tiles[x][y - 1].canMove(Direction.NORTH);
		case EAST:
			return tiles[x + 1][y].canMove(Direction.EAST);
		case SOUTH:
			return tiles[x][y + 1].canMove(Direction.SOUTH);
		case WEST:
			return tiles[x - 1][y].canMove(Direction.WEST);
		}
		return false;
	}

	public void printBoard(Suspect suspect) {
		Room temp = suspect.getRoom();
		if (temp != null)
			temp.showExits();
		for (int y = 0; y < BOARD_HEIGHT; y++) {
			for (int x = 0; x < BOARD_WIDTH; x++) {
				System.out.print(tiles[x][y].getCode());
			}
			System.out.println();
		}
		System.out.println();
		if (temp != null)
			temp.hideExits();
	}

	public void moveSuspect(Suspect suspect, Direction direction) {
		int x = suspect.getX();
		int y = suspect.getY();
		tiles[x][y].setSuspect(null);
		switch (direction) {
		case NORTH:
			tiles[x][y - 1].setSuspect(suspect);
			suspect.setLocation(x, y - 1);
			break;
		case EAST:
			tiles[x + 1][y].setSuspect(suspect);
			suspect.setLocation(x + 1, y);
			break;
		case SOUTH:
			tiles[x][y + 1].setSuspect(suspect);
			suspect.setLocation(x, y + 1);
			break;
		case WEST:
			tiles[x - 1][y].setSuspect(suspect);
			suspect.setLocation(x - 1, y);
			break;
		}
	}

	public void moveWeapon(Weapon weapon, Room room) {
		weapon.getRoom().removeWeapon(weapon);
		weapon.setRoom(room);
		room.addWeapon(weapon);
	}

	public boolean canTeleport(Suspect suspect) {
		if (!suspect.isInRoom())
			return false;
		String tempName = suspect.getRoom().getName();
		return (tempName.equals("Kitchen") || tempName.equals("Conservatory")
				|| tempName.equals("Study") || tempName.equals("Lounge"));
	}

	public void teleport(Suspect suspect) {
		Room tempRoom = suspect.getRoom();
		String tempName = tempRoom.getName();
		switch (tempName) {
		case "Kitchen":
			suspect.setRoom(rooms[5]);
			tempRoom.removeSuspect(suspect);
			rooms[5].addSuspect(suspect);
			break;
		case "Conservatory":
			suspect.setRoom(rooms[7]);
			tempRoom.removeSuspect(suspect);
			rooms[7].addSuspect(suspect);
			break;
		case "Study":
			suspect.setRoom(rooms[0]);
			tempRoom.removeSuspect(suspect);
			rooms[0].addSuspect(suspect);
			break;
		case "Lounge":
			suspect.setRoom(rooms[2]);
			tempRoom.removeSuspect(suspect);
			rooms[2].addSuspect(suspect);
			break;
		}
		// TODO Auto-generated method stub

	}

	public boolean canPlayTurn(Suspect suspect) {
		if (suspect.isInRoom()) {
			if (canTeleport(suspect))
				return true;
			else
				for (Exit exit : suspect.getRoom().getExits())
					if (exit.canMove(null))
						return true;
			return false;
		} else {
			int x = suspect.getX();
			int y = suspect.getY();
			return tiles[x][y - 1].canMove(Direction.NORTH)
					|| tiles[x][y + 1].canMove(Direction.EAST)
					|| tiles[x + 1][y].canMove(Direction.SOUTH)
					|| tiles[x - 1][y].canMove(Direction.WEST);
		}
	}

	public boolean canUseExit(Suspect suspect, int exit) {
		Room tempRoom = suspect.getRoom();
		if (tempRoom == null) {
			return false;
		} else {
			if (tempRoom.getName().equals("Kitchen") && exit == 1)
				return tiles[5][8].canMove(null);
			if (tempRoom.getName().equals("Ball Room") && exit == 1)
				return tiles[8][6].canMove(null);
			if (tempRoom.getName().equals("Ball Room") && exit == 2)
				return tiles[10][9].canMove(null);
			if (tempRoom.getName().equals("Ball Room") && exit == 3)
				return tiles[15][9].canMove(null);
			if (tempRoom.getName().equals("Ball Room") && exit == 4)
				return tiles[17][6].canMove(null);
			if (tempRoom.getName().equals("Conservatory") && exit == 1)
				return tiles[19][6].canMove(null);
			if (tempRoom.getName().equals("Billiard Room") && exit == 1)
				return tiles[18][10].canMove(null);
			if (tempRoom.getName().equals("Billiard Room") && exit == 2)
				return tiles[23][14].canMove(null);
			if (tempRoom.getName().equals("Library") && exit == 1)
				return tiles[21][14].canMove(null);
			if (tempRoom.getName().equals("Library") && exit == 2)
				return tiles[17][17].canMove(null);
			if (tempRoom.getName().equals("Study") && exit == 1)
				return tiles[18][21].canMove(null);
			if (tempRoom.getName().equals("Hall") && exit == 1)
				return tiles[12][18].canMove(null);
			if (tempRoom.getName().equals("Hall") && exit == 2)
				return tiles[13][18].canMove(null);
			if (tempRoom.getName().equals("Hall") && exit == 3)
				return tiles[16][21].canMove(null);
			if (tempRoom.getName().equals("Lounge") && exit == 1)
				return tiles[7][19].canMove(null);
			if (tempRoom.getName().equals("Dining Room") && exit == 1)
				return tiles[9][13].canMove(null);
			if (tempRoom.getName().equals("Dining Room") && exit == 2)
				return tiles[7][17].canMove(null);
			return false;
		}

	}

	public void exitRoom(Suspect suspect, int exit) {
		Room tempRoom = suspect.getRoom();
		if (tempRoom != null) {

			tempRoom.removeSuspect(suspect);
			suspect.setRoom(null);
			if (tempRoom.getName().equals("Kitchen") && exit == 1) {
				suspect.setLocation(5, 8);
				tiles[5][8].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Ball Room") && exit == 1) {
				suspect.setLocation(8, 6);
				tiles[8][6].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Ball Room") && exit == 2) {
				suspect.setLocation(10, 9);
				tiles[10][9].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Ball Room") && exit == 3) {
				suspect.setLocation(15, 9);
				tiles[15][9].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Ball Room") && exit == 4) {
				suspect.setLocation(17, 6);
				tiles[17][6].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Conservatory") && exit == 1) {
				suspect.setLocation(19, 6);
				tiles[19][6].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Billiard Room") && exit == 1) {
				suspect.setLocation(18, 10);
				tiles[18][10].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Billiard Room") && exit == 2) {
				suspect.setLocation(23, 14);
				tiles[23][14].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Library") && exit == 1) {
				suspect.setLocation(21, 14);
				tiles[21][14].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Library") && exit == 2) {
				suspect.setLocation(17, 17);
				tiles[17][17].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Study") && exit == 1) {
				suspect.setLocation(18, 21);
				tiles[18][21].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Hall") && exit == 1) {
				suspect.setLocation(12, 18);
				tiles[12][18].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Hall") && exit == 2) {
				suspect.setLocation(13, 18);
				tiles[13][18].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Hall") && exit == 3) {
				suspect.setLocation(16, 21);
				tiles[16][21].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Lounge") && exit == 1) {
				suspect.setLocation(7, 19);
				tiles[5][8].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Dining Room") && exit == 1) {
				suspect.setLocation(9, 13);
				tiles[9][13].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Dining Room") && exit == 2) {
				suspect.setLocation(7, 17);
				tiles[7][17].setSuspect(suspect);
			}
		}
	}
}
