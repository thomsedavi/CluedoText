package cluedo;

/**
 * Corridors are one way of moving between Rooms.
 * Every Corridor is initially set to null.
 *
 * @author David Thomsen & Pauline Kelly
 *
 */
public class Corridor implements Tile {

	private Suspect suspect; //can be Null

	public Corridor() {
		suspect = null;
	}

	/**
	 * Return the ASCII code for the suspect's character, otherwise return an empty Corridor.
	 * @return The code for the corridor.
	 */
	public String getCode() {
		if (suspect != null)
			 return suspect.getCode();
		else
			return "  ";
	}

	/**
	 * If there is no suspect on this tile, then someone else can move here.
	 * Direction is not used for this particular tile.
	 */
	public boolean canMove(Direction direction) {
		return suspect == null;
	}

	/**
	 * Set the new suspect at this position.
	 */
	public void setSuspect(Suspect suspect) {
		if (this.suspect == null && suspect != null)
			this.suspect = suspect;
		else if (this.suspect != null && suspect == null)
			this.suspect = null;
		else
			throw new InvalidMoveError("Suspect cannot move onto this tile");
	}

	/**
	 * Removes the suspect from this Tile.
	 */
	public void removeSuspect(Suspect suspect) {
		this.suspect = null;
	}
}
