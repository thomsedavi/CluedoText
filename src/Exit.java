public class Exit implements Tile {

	final String exitNumber;
	private boolean active;
	private Suspect suspect;

	public Exit(String exitNumber) {
		this.exitNumber = exitNumber;
		active = false;
	}

	@Override
	public String getCode() {
		if (suspect != null) {
			return suspect.getCode();
		} else if (active)
			return exitNumber;
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
	
	public void activate() {
		active = true;
	}

	public void deactivate() {
		active = false;
	}
}
