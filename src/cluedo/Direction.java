package cluedo;

public enum Direction {
	NORTH, EAST, SOUTH, WEST;

	public static Direction parseDirection(char charAt) {
		switch (charAt) {
		case 'N':
			return NORTH;
		case 'S':
			return SOUTH;
		case 'E':
			return EAST;
		case 'W':
			return WEST;
		}
		return null;
	}
}
