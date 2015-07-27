package cluedo;

/**
 * Exits are used by the players. They are drawn as a standard Corridor tile
 * unless activated, in which case it will draw a number representing the exit.
 *
 * @author David Thomsen & Pauline Kelly
 *
 */
public class Exit implements Tile {

	final String exitNumber;
	private boolean active;
	private Suspect suspect;

	public Exit(String exitNumber) {
		this.exitNumber = exitNumber;
		active = false;
	}

	@Override
	public String getCode() {
		if (suspect != null) {
			return suspect.getCode();
		} else if (active)
			return exitNumber;
		else
			return "  ";
	}

	@Override
	public boolean canMove(Direction direction) {
		return suspect == null;
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

	public void activate() {
		active = true; // will draw exit number when called
	}

	public void deactivate() {
		active = false; // will draw floor when called
	}
}
