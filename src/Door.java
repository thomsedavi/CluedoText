public class Door implements Tile {

	private int thisX, thisY, direction;
	private Room room;

	public Door(int thisX, int thisY, String dir, Room room) {
		this.thisX = thisX;
		this.thisY = thisY;
		this.room = room;
		switch (dir) {
		case "NORTH":
			direction = 0;
			break;
		case "EAST":
			direction = 1;
			break;
		case "SOUTH":
			direction = 2;
			break;
		case "WEST":
			direction = 3;
			break;
		}
	}

	@Override
	public String getCode() {
		switch (direction) {
		case 0:
			return "^^";
		case 1:
			return "\u2591\u25B7";
		case 2:
			return "vv";
		case 3:
			return "\u25C1\u2591";
		}
		return "##";
	}

	@Override
	public boolean canMove(int x, int y) {
		switch (direction) {
		case 0:
			return thisY == y + 1;
		case 1:
			return thisX == x - 1;
		case 2:
			return thisY == y - 1;
		case 3:
			return thisX == x + 1;
		}
		return false;
	}

	@Override
	public void setSuspect(Suspect suspect) {
		room.addSuspect(suspect);
		suspect.setRoom(room);
	}
}
