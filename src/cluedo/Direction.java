package cluedo;

/**
 * An enum of compass Directions used by the Board in general.
 * @author Pauline Kelly & David Thomsen
 *
 */
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
