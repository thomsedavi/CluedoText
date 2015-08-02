package cluedo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representing a Board of Cluedo, containing a grid of 26x27 Tiles and a
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
	public Board(Room[] rooms, Suspect[] suspects, Weapon[] weapons)
			throws IOException {

		this.rooms = rooms;
		tiles = new Tile[BOARD_WIDTH][BOARD_HEIGHT];

		File map = new File("CluedoMap.txt");
		BufferedReader mapReader = null;

		try {
			mapReader = new BufferedReader(new FileReader(map));
			String line;
			int y = 0;
			while ((line = mapReader.readLine()) != null) {
				String[] split = line.split(",");
				for (int x = 0; x < BOARD_WIDTH; x++) {
					parseTile(split[x], x, y);
				}
				y++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			mapReader.close();
		}

		for (Suspect s : suspects) {
			tiles[s.getX()][s.getY()].setSuspect(s);
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

	private void parseTile(String string, int x, int y) {
		switch (string) {
		case "#":
			tiles[x][y] = new Wall();
			return;
		case "_":
			tiles[x][y] = new Corridor();
			return;
		case "+":
			tiles[x][y] = new RoomTile(rooms[0], 0);
			return;
		case "~":
			tiles[x][y] = new TextTile("~~");
			return;
		case "^":
			tiles[x][y] = new TextTile("\u2608\u2607");
			return;
		}

		if (string.length() == 2) {
			String text = "";

			if (string.charAt(0) != '+')
				text = text + string.substring(0, 1);
			else
				text = text + "\u2592";

			if (string.charAt(1) != '+')
				text = text + string.substring(1, 2);
			else
				text = text + "\u2592";

			tiles[x][y] = new TextTile(text);
		} else if (string.length() == 3) {
			tiles[x][y] = new RoomTile(rooms[Integer.parseInt(string.substring(
					0, 1))], Integer.parseInt(string.substring(1, 3)));
		} else if (string.length() == 4) {
			int rom = Integer.parseInt(string.substring(0, 1));
			int xit = Integer.parseInt(string.substring(3, 4));
			Exit exit = new Exit(xit, x, y);
			tiles[x][y] = exit;
			rooms[rom].addExit(xit, exit);
		} else if (string.length() == 5) {
			Direction dir = Direction.parseDirection(string.charAt(0));
			int rom = Integer.parseInt(string.substring(4, 5));
			tiles[x][y] = new Door(x, y, dir, rooms[rom]);
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
		throw new InvalidDirectionError("Not a valid direction");
	}

	public String getLine(int y, Suspect suspect) {
		String result = "";
		for (int x = 0; x < BOARD_WIDTH; x++) {
			result = result + tiles[x][y].getCode();
		}
		System.out.println();
		return result;
	}

	public void moveSuspect(Suspect suspect, Direction direction) {
		int x = suspect.getX();
		int y = suspect.getY();
		tiles[x][y].setSuspect(null);
		switch (direction) {
		case NORTH:
			tiles[x][y - 1].setSuspect(suspect);
			suspect.setLocation(x, y - 1);
			return;
		case EAST:
			tiles[x + 1][y].setSuspect(suspect);
			suspect.setLocation(x + 1, y);
			return;
		case SOUTH:
			tiles[x][y + 1].setSuspect(suspect);
			suspect.setLocation(x, y + 1);
			return;
		case WEST:
			tiles[x - 1][y].setSuspect(suspect);
			suspect.setLocation(x - 1, y);
			return;
		}
		throw new InvalidDirectionError("Not a valid direction");
	}

	public void moveWeapon(Weapon weapon, Room room) {
		weapon.getRoom().removeWeapon(weapon);
		weapon.setRoom(room);
		room.addWeapon(weapon);
	}

	public boolean canTeleport(Suspect suspect) {
		if (!suspect.isInRoom())
			return false;
		return (suspect.getRoom().getTeleport() != null);
	}

	public void teleport(Suspect suspect) {
		Room thisRoom = suspect.getRoom();
		Room oppRoom = thisRoom.getTeleport();

		suspect.setRoom(oppRoom);
		thisRoom.removeSuspect(suspect);
		oppRoom.addSuspect(suspect);
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
			int x = tempRoom.getExitX(exit);
			int y = tempRoom.getExitY(exit);
			return tiles[x][y].canMove(null);
		}
	}

	public void exitRoom(Suspect suspect, int exit) {
		Room tempRoom = suspect.getRoom();
		if (tempRoom != null) {

			tempRoom.removeSuspect(suspect);
			suspect.setRoom(null);

			int x = tempRoom.getExitX(exit);
			int y = tempRoom.getExitY(exit);

			suspect.setLocation(x, y);
			tiles[x][y].setSuspect(suspect);
		} else {
			throw new InvalidMoveError("Suspect not in a room");
		}
	}
}
