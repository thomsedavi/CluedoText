package cluedo;

public class InvalidDirectionError extends RuntimeException {

	public InvalidDirectionError(String string) {
		throw new RuntimeException(string);
	}

}
