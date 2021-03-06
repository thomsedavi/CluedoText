package cluedo;

/**
 * Exits are used by the players. They are drawn as a standard Corridor tile
 * unless activated, in which case it will draw a number representing the exit.
 *
 * @author David Thomsen & Pauline Kelly
 *
 */
public class Exit implements Tile {

	final int exitNumber, x, y;
	private boolean active;
	private Suspect suspect;

	public Exit(int exitNumber, int x, int y) {
		this.exitNumber = exitNumber;
		this.x = x;
		this.y = y;
		active = false;
	}

	/**
	 * Displays a blank tile by default, otherwise a suspect if there is one,
	 * otherwise a room number if the room is occupied by the player making the
	 * current turn.
	 */
	public String getCode() {
		if (suspect != null) {
			return suspect.getCode();
		} else if (active) {
			String result = "\u2607" + exitNumber;
			return result;
		} else
			return "  ";
	}

	/**
	 * If a Suspect is occupying this space, you can't move.
	 */
	public boolean canMove(Direction direction) {
		return suspect == null; //someone is here, cannot move!
	}

	@Override
	public void setSuspect(Suspect suspect) {
		if (this.suspect == null && suspect != null)
			this.suspect = suspect;
		else if (this.suspect != null && suspect == null)
			this.suspect = null;
		else
			throw new InvalidMoveError("Suspect cannot move onto this tile");
	}

	/**
	 * If the player is in the room, and wishes to exit, it will display differently on the Board.
	 */
	public void activate() {
		active = true; // will draw exit number when called
	}

	/**
	 * If the player is not in the room, it will display differently on the Board.
	 */
	public void deactivate() {
		active = false; // will draw floor when called
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	 * Removes the Suspect from this Tile.
	 */
	public void removeSuspect(Suspect suspect) {
		this.suspect = null;
	}
}
