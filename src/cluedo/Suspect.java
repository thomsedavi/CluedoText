package cluedo;

/**
 * A Suspect Card and also the representation of that character on the board.
 * @author Pauline Kelly & David Thomsen
 */
public class Suspect extends Card {

	private boolean isPresent = false;
	private Room room; //Room the suspect is currently in, null if not in a room.
	private Integer x, y; //coordinates on Board

	public Suspect(String name, String code, int x, int y) {
		super.name = name;
		super.code = code;
		this.x = x;
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code; // Two character representation of Suspect on board
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setRoom(Room room) {
		this.room = room; //can be set to null when exiting froom
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Room getRoom() {
		return room; //can return null
	}

	/**
	 * Whether or not the suspect is in the room.
	 * @return
	 */
	public boolean isInRoom() {
		return room != null;
	}

	public boolean isPresent() {
		return isPresent;
	}

	public void makePresent() {
		isPresent = true;
	}
}
