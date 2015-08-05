package cluedo;

/**
 * An exception to indicate that an Invalid Direction was attempted.
 * @author Pauline Kelly & David Thomsen
 *
 */
public class InvalidDirectionError extends RuntimeException {

	public InvalidDirectionError(String string) {
		throw new RuntimeException(string);
	}

}
