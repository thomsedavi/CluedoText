package cluedo;

/**
 * A special kind of Tile that the Suspect cannot move onto which can be used to
 * decorate the Board with text.
 *
 * @author Pauline Kelly & David Thomsen
 */
public class TextTile implements Tile {

	final String text; //Two character code used to decorate the Board

	public TextTile(String text) {
		this.text = text;
	}

	@Override
	public String getCode() {
		return text;
	}

	@Override
	public boolean canMove(Direction direction) {
		return false; // essentially the same as a wall.
	}

	@Override
	public void setSuspect(Suspect suspect) {
		throw new InvalidMoveError("Suspect cannot move onto this tile");
	}

}
