package cluedo;

/**
 * Corridors can contain suspects - suspect may or may not be null.
 *
 * @author David Thomsen & Pauline Kelly
 *
 */
public class Corridor implements Tile {

	private Suspect suspect;

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

	//TODO
	/**
	 * Whether the suspect can move in a certain direction.
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
}
