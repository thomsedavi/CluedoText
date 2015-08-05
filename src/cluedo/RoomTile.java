package cluedo;

/**
 * Tile representing a Room on the board. This Tile relies on the Room it is
 * part of to determine what is drawn here, as some Room tiles are capable of
 * displaying Suspects and Weapons. This depends on the integer position of this
 * tile.
 *
 * position = 0: displays standard Room tile.
 *
 * position = 1 to 6: displays Suspect in this room from List in associated
 * Room, standard room tile otherwise.
 *
 * position = -1 to -6: displays Weapons in this room from List in associated
 * Room, standard room tile otherwise.
 *
 * @author David Thomsen & Pauline Kelly
 *
 */
public class RoomTile implements Tile {

	private int position;
	private Room room;

	public RoomTile(Room room, int position) {
		this.room = room;
		this.position = position;
	}

	/**
	 * Get the code for the room tile at this position.
	 */
	public String getCode() {
		return room.getCode(position);
	}

	/**
	 * The Player cannot move onto a Room tile. They can only be put in
	 * their specified position.
	 *
	 * @return if the player can move here.
	 */
	public boolean canMove(Direction direction) {
		return false; // Cannot move onto this tile
	}

	@Override
	public void setSuspect(Suspect suspect) {
		throw new InvalidMoveError("Suspect cannot move onto this tile");
	}

	@Override
	public void removeSuspect(Suspect suspect) {
		throw new InvalidMoveError("Suspect cannot be on this tile");
	}
}
