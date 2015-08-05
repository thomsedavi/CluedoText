package cluedo;

/**
 * An exception to indicate that an Invalid Move was attempted.
 * @author Pauline Kelly & David Thomsen
 *
 */
public class InvalidMoveError extends RuntimeException {

	public InvalidMoveError(String string) {
		throw new RuntimeException(string);
	}
}