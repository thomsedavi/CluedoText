public class Suspect {
	
	private Room room;
	private String name;
	private String code;
	private int x, y;
	
	public Suspect(String name, String code) {
		this.name = name;
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public String getCode() {
		return code;
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Room getRoom() {
		return room;
	}
	
	public boolean isInRoom() {
		return room != null;
	}
}
