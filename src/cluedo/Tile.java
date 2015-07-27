package cluedo;

/**
 * The game board will contain various Tile objects.
 * @author Pauline Kelly & David Thomsen
 *
 */
public interface Tile {

	/**
	 * @return the two character Code representing this Tile on the Board
	 */
	abstract String getCode();

	/**
	 * @param direction the Suspect is appraoching from
	 * @return whether the Suspect can move into this tile from given direction
	 */
	abstract boolean canMove(Direction direction);		//Non-Movable Element?

	/**
	 * @param suspect the Suspect to be put on this Tile
	 */
	abstract void setSuspect(Suspect suspect);	//Sets the suspect in this position

}
