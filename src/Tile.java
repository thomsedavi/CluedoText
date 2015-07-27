public interface Tile {
	
	abstract String getCode();

	abstract boolean canMove(int x, int y);
	
	abstract void setSuspect(Suspect suspect);

}
