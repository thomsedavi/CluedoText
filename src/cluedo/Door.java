package cluedo;

/**
 * Each door belongs to a Room, and has a direction which the Door is facing.
 *
 * @author David Thomsen & Pauline Kelly
 *
 */
public class Door implements Tile {

	final int xPos, yPos;
	final Room room;
	private Direction entranceDirection;

	public Door(int x, int y, Direction direction, Room room) {
		this.xPos = x;
		this.yPos = y;

		this.room = room;

		// Check the direction that has been entered, store it
		switch (direction) {
		case NORTH:
			entranceDirection = Direction.NORTH;
			break;
		case EAST:
			entranceDirection = Direction.EAST;
			break;
		case SOUTH:
			entranceDirection = Direction.SOUTH;
			break;
		case WEST:
			entranceDirection = Direction.WEST;
			break;
		}
	}

	/**
	 * The code is mapped to a set of ASCII characters, which display the
	 * direction of the door.
	 *
	 * @return the code for displaying the door direction on the board.
	 */
	public String getCode() {
		switch (entranceDirection) {
		case NORTH:
			return "\u25F9\u25F8"; // north
		case EAST:
			return "\u25C1\u2592"; // east
		case SOUTH:
			return"\u25FF\u25FA"; // south
		case WEST:
			return "\u2592\u25B7"; // west
		}
		throw new InvalidDirectionError("Not a valid direction");
	}

	/**
	 * Whether you can come from this direction into the room.
	 *
	 * @return Whether the player is able to move in a certain direction.
	 */
	public boolean canMove(Direction direction) {
		switch (entranceDirection) {
		case NORTH:
			return direction == Direction.NORTH;
		case EAST:
			return direction == Direction.EAST;
		case SOUTH:
			return direction == Direction.SOUTH;
		case WEST:
			return direction == Direction.WEST;
		}
		throw new InvalidDirectionError("Not a valid direction");
	}

	/**
	 * Once the player has used the Door, they will then be set up at the next
	 * specified position in the Room.
	 */
	public void setSuspect(Suspect suspect) {
		room.addSuspect(suspect);
		suspect.setRoom(room);
	}

	/**
	 * Removes the suspect from the Tile.
	 */
	public void removeSuspect(Suspect suspect) {
		room.removeSuspect(suspect);
	}
}
