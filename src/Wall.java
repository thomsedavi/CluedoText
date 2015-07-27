public class Wall implements Tile {

	@Override
	public String getCode() {
		return "\u2588\u2588"; //\u2592
	}

	@Override
	public boolean canMove(int x, int y) {
		return false;
	}

	@Override
	public void setSuspect(Suspect suspect) {
	}
}
