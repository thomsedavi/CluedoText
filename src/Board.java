import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {

	Tile[][] board;
	Room[] rooms;

	public Board(Room[] rooms, Suspect[] suspects, Weapon[] weapons) {

		this.rooms = rooms;
		board = new Tile[26][27];

		for (int y = 0; y < 27; y++) {
			for (int x = 0; x < 26; x++) {
				board[x][y] = new Wall();
			}
		}

		for (int y = 2; y < 25; y++) {
			for (int x = 1; x < 25; x++) {
				board[x][y] = new Corridor();
			}
		}

		for (int y = 2; y < 25; y++) {
			for (int x = 1; x < 25; x++) {
				board[x][y] = new Corridor();
			}
		}

		for (int y = 11; y < 18; y++) {
			for (int x = 11; x < 16; x++) {
				board[x][y] = new TextTile("~~");
			}
		}

		for (int y = 2; y < 8; y++) {
			for (int x = 1; x < 7; x++) {
				board[x][y] = new RoomTile(rooms[0], 0);
			}
		}

		board[1][2] = new RoomTile(rooms[0], 1);
		board[3][2] = new RoomTile(rooms[0], 2);
		board[5][2] = new RoomTile(rooms[0], 3);
		board[1][3] = new RoomTile(rooms[0], 4);
		board[3][3] = new RoomTile(rooms[0], 5);
		board[5][3] = new RoomTile(rooms[0], 6);
		board[1][6] = new RoomTile(rooms[0], -1);
		board[3][6] = new RoomTile(rooms[0], -2);
		board[5][6] = new RoomTile(rooms[0], -3);
		board[1][5] = new RoomTile(rooms[0], -4);
		board[3][5] = new RoomTile(rooms[0], -5);
		board[5][5] = new RoomTile(rooms[0], -6);

		for (int y = 2; y < 9; y++) {
			for (int x = 9; x < 17; x++) {
				board[x][y] = new RoomTile(rooms[1], 0);
			}
		}

		board[11][3] = new RoomTile(rooms[1], 1);
		board[13][3] = new RoomTile(rooms[1], 2);
		board[15][3] = new RoomTile(rooms[1], 3);
		board[10][4] = new RoomTile(rooms[1], 4);
		board[12][4] = new RoomTile(rooms[1], 5);
		board[14][4] = new RoomTile(rooms[1], 6);
		board[10][7] = new RoomTile(rooms[1], -1);
		board[12][7] = new RoomTile(rooms[1], -2);
		board[14][7] = new RoomTile(rooms[1], -3);
		board[11][6] = new RoomTile(rooms[1], -4);
		board[13][6] = new RoomTile(rooms[1], -5);
		board[15][6] = new RoomTile(rooms[1], -6);

		for (int y = 2; y < 7; y++) {
			for (int x = 19; x < 25; x++) {
				board[x][y] = new RoomTile(rooms[2], 0);
			}
		}

		board[20][2] = new RoomTile(rooms[2], 1);
		board[22][2] = new RoomTile(rooms[2], 2);
		board[24][2] = new RoomTile(rooms[2], 3);
		board[21][3] = new RoomTile(rooms[2], 4);
		board[23][3] = new RoomTile(rooms[2], 5);
		board[22][3] = new RoomTile(rooms[2], 6);
		board[20][5] = new RoomTile(rooms[2], -1);
		board[22][5] = new RoomTile(rooms[2], -2);
		board[24][5] = new RoomTile(rooms[2], -3);
		board[21][5] = new RoomTile(rooms[2], -4);
		board[23][5] = new RoomTile(rooms[2], -5);
		board[24][4] = new RoomTile(rooms[2], -6);

		for (int y = 9; y < 14; y++) {
			for (int x = 19; x < 25; x++) {
				board[x][y] = new RoomTile(rooms[3], 0);
			}
		}

		board[20][10] = new RoomTile(rooms[3], 1);
		board[22][10] = new RoomTile(rooms[3], 2);
		board[24][10] = new RoomTile(rooms[3], 3);
		board[21][10] = new RoomTile(rooms[3], 4);
		board[23][10] = new RoomTile(rooms[3], 5);
		board[24][9] = new RoomTile(rooms[3], 6);
		board[20][12] = new RoomTile(rooms[3], -1);
		board[22][12] = new RoomTile(rooms[3], -2);
		board[24][12] = new RoomTile(rooms[3], -3);
		board[21][12] = new RoomTile(rooms[3], -4);
		board[23][12] = new RoomTile(rooms[3], -5);
		board[24][13] = new RoomTile(rooms[3], -6);

		for (int y = 15; y < 20; y++) {
			for (int x = 18; x < 25; x++) {
				board[x][y] = new RoomTile(rooms[4], 0);
			}
		}

		board[19][16] = new RoomTile(rooms[4], 1);
		board[21][16] = new RoomTile(rooms[4], 2);
		board[23][16] = new RoomTile(rooms[4], 3);
		board[20][16] = new RoomTile(rooms[4], 4);
		board[22][16] = new RoomTile(rooms[4], 5);
		board[24][16] = new RoomTile(rooms[4], 6);
		board[19][18] = new RoomTile(rooms[4], -1);
		board[21][18] = new RoomTile(rooms[4], -2);
		board[23][18] = new RoomTile(rooms[4], -3);
		board[20][18] = new RoomTile(rooms[4], -4);
		board[22][18] = new RoomTile(rooms[4], -5);
		board[24][18] = new RoomTile(rooms[4], -6);

		for (int y = 22; y < 26; y++) {
			for (int x = 18; x < 25; x++) {
				board[x][y] = new RoomTile(rooms[5], 0);
			}
		}

		board[24][24] = new RoomTile(rooms[5], 1);
		board[22][24] = new RoomTile(rooms[5], 2);
		board[20][24] = new RoomTile(rooms[5], 3);
		board[23][24] = new RoomTile(rooms[5], 4);
		board[21][24] = new RoomTile(rooms[5], 5);
		board[19][24] = new RoomTile(rooms[5], 6);
		board[19][25] = new RoomTile(rooms[5], -1);
		board[21][25] = new RoomTile(rooms[5], -2);
		board[23][25] = new RoomTile(rooms[5], -3);
		board[20][25] = new RoomTile(rooms[5], -4);
		board[22][25] = new RoomTile(rooms[5], -5);
		board[24][25] = new RoomTile(rooms[5], -6);

		for (int y = 19; y < 26; y++) {
			for (int x = 10; x < 16; x++) {
				board[x][y] = new RoomTile(rooms[6], 0);
			}
		}

		board[11][20] = new RoomTile(rooms[6], 1);
		board[13][20] = new RoomTile(rooms[6], 2);
		board[14][21] = new RoomTile(rooms[6], 3);
		board[12][21] = new RoomTile(rooms[6], 4);
		board[12][20] = new RoomTile(rooms[6], 5);
		board[13][21] = new RoomTile(rooms[6], 6);
		board[14][25] = new RoomTile(rooms[6], -1);
		board[12][25] = new RoomTile(rooms[6], -2);
		board[11][24] = new RoomTile(rooms[6], -3);
		board[13][24] = new RoomTile(rooms[6], -4);
		board[14][23] = new RoomTile(rooms[6], -5);
		board[12][23] = new RoomTile(rooms[6], -6);

		for (int y = 20; y < 26; y++) {
			for (int x = 1; x < 8; x++) {
				board[x][y] = new RoomTile(rooms[7], 0);
			}
		}

		board[1][21] = new RoomTile(rooms[7], 1);
		board[3][21] = new RoomTile(rooms[7], 2);
		board[5][21] = new RoomTile(rooms[7], 3);
		board[2][23] = new RoomTile(rooms[7], 4);
		board[4][23] = new RoomTile(rooms[7], 5);
		board[6][23] = new RoomTile(rooms[7], 6);
		board[2][25] = new RoomTile(rooms[7], -1);
		board[4][25] = new RoomTile(rooms[7], -2);
		board[6][25] = new RoomTile(rooms[7], -3);
		board[1][24] = new RoomTile(rooms[7], -4);
		board[3][24] = new RoomTile(rooms[7], -5);
		board[5][24] = new RoomTile(rooms[7], -6);

		for (int y = 10; y < 17; y++) {
			for (int x = 1; x < 9; x++) {
				board[x][y] = new RoomTile(rooms[8], 0);
			}
		}

		board[1][11] = new RoomTile(rooms[8], 1);
		board[3][11] = new RoomTile(rooms[8], 2);
		board[5][11] = new RoomTile(rooms[8], 3);
		board[2][12] = new RoomTile(rooms[8], 4);
		board[4][12] = new RoomTile(rooms[8], 5);
		board[6][12] = new RoomTile(rooms[8], 6);
		board[1][15] = new RoomTile(rooms[8], -1);
		board[3][15] = new RoomTile(rooms[8], -2);
		board[5][15] = new RoomTile(rooms[8], -3);
		board[2][14] = new RoomTile(rooms[8], -4);
		board[4][14] = new RoomTile(rooms[8], -5);
		board[6][14] = new RoomTile(rooms[8], -6);

		Exit exit;

		board[1][0] = new TextTile("  ");
		board[2][0] = new TextTile("CL");
		board[3][0] = new TextTile("UE");
		board[4][0] = new TextTile("DO");
		board[5][0] = new TextTile("  ");
		board[10][1] = new Corridor();
		board[15][1] = new Corridor();
		board[6][2] = new TextTile("==");
		board[7][2] = new Wall();
		board[9][2] = new Corridor();
		board[10][2] = new Corridor();
		board[15][2] = new Corridor();
		board[16][2] = new Corridor();
		board[18][2] = new Wall();
		board[2][4] = new TextTile("KI");
		board[3][4] = new TextTile("TC");
		board[4][4] = new TextTile("HE");
		board[5][4] = new TextTile("N\u2591");
		board[20][4] = new TextTile("CO");
		board[21][4] = new TextTile("NS");
		board[22][4] = new TextTile("ER");
		board[23][4] = new TextTile("VA");
		board[11][5] = new TextTile("BA");
		board[12][5] = new TextTile("LL");
		board[13][5] = new TextTile(" R");
		board[14][5] = new TextTile("OO");
		board[15][5] = new TextTile("M\u2591");
		board[19][5] = new Door(19, 5, "SOUTH", rooms[2]);
		exit = new Exit("01");
		board[8][6] = exit;
		rooms[1].addExit(exit);
		board[9][6] = new Door(9, 6, "WEST", rooms[1]);
		board[16][6] = new Door(16, 6, "EAST", rooms[1]);
		exit = new Exit("04");
		board[17][6] = exit;
		rooms[1].addExit(exit);
		exit = new Exit("01");
		board[19][6] = exit;
		rooms[2].addExit(exit);
		board[23][6] = new TextTile("==");
		board[24][6] = new Wall();
		board[1][7] = new Wall();
		board[5][7] = new Door(5, 7, "SOUTH", rooms[0]);
		exit = new Exit("01");
		board[5][8] = exit;
		rooms[0].addExit(exit);
		board[10][8] = new Door(10, 8, "SOUTH", rooms[1]);
		board[15][8] = new Door(15, 8, "SOUTH", rooms[1]);
		board[24][8] = new Wall();
		board[1][9] = new Wall();
		exit = new Exit("02");
		board[10][9] = exit;
		rooms[1].addExit(exit);
		exit = new Exit("03");
		board[15][9] = exit;
		rooms[1].addExit(exit);
		board[6][10] = new Corridor();
		board[7][10] = new Corridor();
		board[8][10] = new Corridor();
		exit = new Exit("01");
		board[18][10] = exit;
		rooms[3].addExit(exit);
		board[19][10] = new Door(19, 10, "WEST", rooms[3]);
		board[20][11] = new TextTile("BI");
		board[21][11] = new TextTile("LL");
		board[22][11] = new TextTile("IA");
		board[23][11] = new TextTile("RD");
		board[2][13] = new TextTile("DI");
		board[3][13] = new TextTile("NI");
		board[4][13] = new TextTile("NG");
		board[5][13] = new TextTile(" R");
		board[6][13] = new TextTile("OO");
		board[7][13] = new TextTile("M\u2591");
		board[8][13] = new Door(8, 13, "EAST", rooms[8]);
		exit = new Exit("01");
		board[9][13] = exit;
		rooms[8].addExit(exit);
		board[23][13] = new Door(23, 13, "SOUTH", rooms[3]);
		exit = new Exit("01");
		board[21][14] = exit;
		rooms[4].addExit(exit);
		exit = new Exit("02");
		board[23][14] = exit;
		rooms[3].addExit(exit);
		board[24][14] = new Wall();
		board[18][15] = new Corridor();
		board[21][15] = new Door(21, 15, "NORTH", rooms[4]);
		board[24][15] = new Wall();
		board[7][16] = new Door(7, 16, "SOUTH", rooms[8]);
		board[1][17] = new Wall();
		exit = new Exit("02");
		board[7][17] = exit;
		rooms[8].addExit(exit);
		exit = new Exit("02");
		board[17][17] = exit;
		rooms[4].addExit(exit);
		board[18][17] = new Door(18, 17, "WEST", rooms[4]);
		board[20][17] = new TextTile("LI");
		board[21][17] = new TextTile("BR");
		board[22][17] = new TextTile("AR");
		board[23][17] = new TextTile("Y\u2591");
		exit = new Exit("01");
		board[12][18] = exit;
		rooms[6].addExit(exit);
		exit = new Exit("02");
		board[13][18] = exit;
		rooms[6].addExit(exit);
		board[1][19] = new Wall();
		exit = new Exit("01");
		board[7][19] = exit;
		rooms[7].addExit(exit);
		board[12][19] = new Door(12, 19, "NORTH", rooms[6]);
		board[13][19] = new Door(13, 19, "NORTH", rooms[6]);
		board[18][19] = new Corridor();
		board[24][19] = new Wall();
		board[1][20] = new TextTile("==");
		board[7][20] = new Door(7, 20, "NORTH", rooms[7]);
		board[15][21] = new Door(15, 21, "EAST", rooms[6]);
		exit = new Exit("03");
		board[16][21] = exit;
		rooms[6].addExit(exit);
		exit = new Exit("01");
		board[18][21] = exit;
		rooms[5].addExit(exit);
		board[24][21] = new Wall();
		board[3][22] = new TextTile("LO");
		board[4][22] = new TextTile("UN");
		board[5][22] = new TextTile("GE");
		board[12][22] = new TextTile("HA");
		board[13][22] = new TextTile("LL");
		board[18][22] = new Door(18, 22, "NORTH", rooms[5]);
		board[24][22] = new TextTile("==");
		board[20][23] = new TextTile("ST");
		board[21][23] = new TextTile("UD");
		board[22][23] = new TextTile("Y\u2591");
		board[7][25] = new Wall();
		board[8][25] = new Corridor();
		board[17][25] = new Corridor();
		board[18][25] = new Wall();

		// TODO obviously fix this

		for (Suspect s : suspects) {
			switch (s.getCode()) {
			case "mS":
				board[8][25].setSuspect(s);
				s.setLocation(8, 25);
				break;
			case "cM":
				board[1][18].setSuspect(s);
				s.setLocation(1, 18);
				break;
			case "mW":
				board[10][1].setSuspect(s);
				s.setLocation(10, 1);
				break;
			case "rG":
				board[15][1].setSuspect(s);
				s.setLocation(15, 1);
				break;
			case "mP":
				board[24][7].setSuspect(s);
				s.setLocation(24, 7);
				break;
			case "pP":
				board[24][20].setSuspect(s);
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

	public boolean canMove(Suspect suspect, String direction) {
		int x = suspect.getX();
		int y = suspect.getY();
		switch (direction) {
		case "NORTH":
			return board[x][y - 1].canMove(x, y);
		case "EAST":
			return board[x + 1][y].canMove(x, y);
		case "SOUTH":
			return board[x][y + 1].canMove(x, y);
		case "WEST":
			return board[x - 1][y].canMove(x, y);
		}
		return false;
	}

	public void printBoard(Suspect suspect) {
		Room temp = suspect.getRoom();
		if (temp != null)
			temp.showExits();
		for (int y = 0; y < 27; y++) {
			for (int x = 0; x < 26; x++) {
				System.out.print(board[x][y].getCode());
			}
			System.out.println();
		}
		System.out.println();
		if (temp != null)
			temp.hideExits();
	}

	public void moveSuspect(Suspect suspect, String direction) {
		int x = suspect.getX();
		int y = suspect.getY();
		board[x][y].setSuspect(null);
		switch (direction) {
		case "NORTH":
			board[x][y - 1].setSuspect(suspect);
			suspect.setLocation(x, y - 1);
			break;
		case "EAST":
			board[x + 1][y].setSuspect(suspect);
			suspect.setLocation(x + 1, y);
			break;
		case "SOUTH":
			board[x][y + 1].setSuspect(suspect);
			suspect.setLocation(x, y + 1);
			break;
		case "WEST":
			board[x - 1][y].setSuspect(suspect);
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
					if (exit.canMove(0, 0))
						return true;
			return false;
		} else {
			int x = suspect.getX();
			int y = suspect.getY();
			return board[x][y - 1].canMove(x, y)
					|| board[x][y + 1].canMove(x, y)
					|| board[x + 1][y].canMove(x, y)
					|| board[x - 1][y].canMove(x, y);
		}
	}

	public boolean canUseExit(Suspect suspect, int exit) {
		Room tempRoom = suspect.getRoom();
		if (tempRoom == null) {
			return false;
		} else {
			if (tempRoom.getName().equals("Kitchen") && exit == 1)
				return board[5][8].canMove(0, 0);
			if (tempRoom.getName().equals("Ball Room") && exit == 1)
				return board[8][6].canMove(0, 0);
			if (tempRoom.getName().equals("Ball Room") && exit == 2)
				return board[10][9].canMove(0, 0);
			if (tempRoom.getName().equals("Ball Room") && exit == 3)
				return board[15][9].canMove(0, 0);
			if (tempRoom.getName().equals("Ball Room") && exit == 4)
				return board[17][6].canMove(0, 0);
			if (tempRoom.getName().equals("Conservatory") && exit == 1)
				return board[19][6].canMove(0, 0);
			if (tempRoom.getName().equals("Billiard Room") && exit == 1)
				return board[18][10].canMove(0, 0);
			if (tempRoom.getName().equals("Billiard Room") && exit == 2)
				return board[23][14].canMove(0, 0);
			if (tempRoom.getName().equals("Library") && exit == 1)
				return board[21][14].canMove(0, 0);
			if (tempRoom.getName().equals("Library") && exit == 2)
				return board[17][17].canMove(0, 0);
			if (tempRoom.getName().equals("Study") && exit == 1)
				return board[18][21].canMove(0, 0);
			if (tempRoom.getName().equals("Hall") && exit == 1)
				return board[12][18].canMove(0, 0);
			if (tempRoom.getName().equals("Hall") && exit == 2)
				return board[13][18].canMove(0, 0);
			if (tempRoom.getName().equals("Hall") && exit == 3)
				return board[16][21].canMove(0, 0);
			if (tempRoom.getName().equals("Lounge") && exit == 1)
				return board[7][19].canMove(0, 0);
			if (tempRoom.getName().equals("Dining Room") && exit == 1)
				return board[9][13].canMove(0, 0);
			if (tempRoom.getName().equals("Dining Room") && exit == 2)
				return board[7][17].canMove(0, 0);
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
				board[5][8].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Ball Room") && exit == 1) {
				suspect.setLocation(8, 6);
				board[8][6].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Ball Room") && exit == 2) {
				suspect.setLocation(10, 9);
				board[10][9].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Ball Room") && exit == 3) {
				suspect.setLocation(15, 9);
				board[15][9].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Ball Room") && exit == 4) {
				suspect.setLocation(17, 6);
				board[17][6].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Conservatory") && exit == 1) {
				suspect.setLocation(19, 6);
				board[19][6].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Billiard Room") && exit == 1) {
				suspect.setLocation(18, 10);
				board[18][10].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Billiard Room") && exit == 2) {
				suspect.setLocation(23, 14);
				board[23][14].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Library") && exit == 1) {
				suspect.setLocation(21, 14);
				board[21][14].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Library") && exit == 2) {
				suspect.setLocation(17, 17);
				board[17][17].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Study") && exit == 1) {
				suspect.setLocation(18, 21);
				board[18][21].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Hall") && exit == 1) {
				suspect.setLocation(12, 18);
				board[12][18].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Hall") && exit == 2) {
				suspect.setLocation(13, 18);
				board[13][18].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Hall") && exit == 3) {
				suspect.setLocation(16, 21);
				board[16][21].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Lounge") && exit == 1) {
				suspect.setLocation(7, 19);
				board[5][8].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Dining Room") && exit == 1) {
				suspect.setLocation(9, 13);
				board[9][13].setSuspect(suspect);
			}
			if (tempRoom.getName().equals("Dining Room") && exit == 2) {
				suspect.setLocation(7, 17);
				board[7][17].setSuspect(suspect);
			}
		}
	}
}
