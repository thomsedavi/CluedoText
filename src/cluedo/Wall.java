package cluedo;

/**
 * A Wall Tile that a Suspect cannot move onto.
 * @author Pauline Kelly & David Thomsen
 */
public class Wall implements Tile {

	@Override
	public String getCode() {
		return "\u2588\u2588"; // unicode for black square
	}

	@Override
	public boolean canMove(Direction direction) {
		return false; //Suspect cannot move onto a wall!
	}

	@Override
	public void setSuspect(Suspect suspect) {
		throw new InvalidMoveError("Suspect cannot move onto this tile"); //bummer
	}

	@Override
	public void removeSuspect(Suspect suspect) {
		throw new InvalidMoveError("Suspect cannot be on this tile"); //bummer
	}
}
