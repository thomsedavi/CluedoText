package cluedo;

/**
 * Represents a Weapon Card in the Deck and also the Weapon piece on the board.
 * @author Pauline Kelly & David Thomsen
 *
 */
public class Weapon extends Card {

	final String code;
	private Room room;

	/**
	 * @param name of Weapon
	 * @param code Two character representation of Weapon
	 */
	public Weapon(String name, String code) {
		super.name = name;
		this.code = code;
	}

	/**
	 * @return the two character code representing this Weapon visually.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the room that this Weapon is currently placed in.
	 * @param room
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

	/**
	 * @return the Room that this Weapon is currently placed in.
	 */
	public Room getRoom() {
		return room;
	}
}
