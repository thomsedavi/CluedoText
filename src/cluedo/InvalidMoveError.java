package cluedo;

public class InvalidMoveError extends RuntimeException {

	public InvalidMoveError(String string) {
		throw new RuntimeException(string);
	}
}