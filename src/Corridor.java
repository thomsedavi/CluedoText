public class Corridor implements Tile {
	
	private Suspect suspect;

	@Override
	public String getCode() {
		if (suspect != null)
			 return suspect.getCode();
		else
			return "  ";
	}

	@Override
	public boolean canMove(int x, int y) {
		return suspect == null;
	}
	
	@Override
	public void setSuspect(Suspect suspect) {
		this.suspect = suspect;
	}
}
