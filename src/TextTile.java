
public class TextTile implements Tile {

	final String text;
	
	public TextTile(String text) {
		this.text = text;
	}
	
	@Override
	public String getCode() {
		return text;
	}

	@Override
	public boolean canMove(int x, int y) {
		return false;
	}

	@Override
	public void setSuspect(Suspect suspect) {
	}

}
