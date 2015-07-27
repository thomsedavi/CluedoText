public class RoomTile implements Tile {
	
	private int position;
	private Room room;
	
	public RoomTile(Room room, int position) {
		this.room = room;
		this.position = position;
	}

	@Override
	public String getCode() {
		return room.getCode(position);
	}

	@Override
	public boolean canMove(int x, int y) {
		return false;
	}

	@Override
	public void setSuspect(Suspect suspect) {
	}
}
