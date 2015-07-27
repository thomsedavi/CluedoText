import java.io.IOException;

public class Test {

	public static void main(String[] args) {
		
		Suspect[] suspects = new Suspect[6];
		Weapon[] weapons = new Weapon[6];
		Room[] rooms = new Room[9];
		
		suspects[0] = new Suspect("Miss Scarlett", "mS");
		suspects[1] = new Suspect("Colonel Mustard", "cM");
		suspects[2] = new Suspect("Mrs White", "mW");
		suspects[3] = new Suspect("Rev Green", "rG");
		suspects[4] = new Suspect("Mrs Peacock", "mP");
		suspects[5] = new Suspect("Professor Plum", "pP");

		weapons[0] = new Weapon("Candlestick", "Cs");
		weapons[1] = new Weapon("Dagger", "Dg");
		weapons[2] = new Weapon("Lead Pipe", "Lp");
		weapons[3] = new Weapon("Revolver", "Rv");
		weapons[4] = new Weapon("Rope", "Rp");
		weapons[5] = new Weapon("Spanner", "Sp");

		rooms[0] = new Room("Kitchen");
		rooms[1] = new Room("Ball Room");
		rooms[2] = new Room("Conservatory");
		rooms[3] = new Room("Billiard Room");
		rooms[4] = new Room("Library");
		rooms[5] = new Room("Study");
		rooms[6] = new Room("Hall");
		rooms[7] = new Room("Lounge");
		rooms[8] = new Room("Dining Room");

		Board board = new Board(rooms, suspects, weapons);
		
		board.printBoard(suspects[0]);
		
		System.out.println(board.canMove(suspects[0], "NORTH"));
		System.out.println(board.canMove(suspects[0], "WEST"));
		System.out.println(board.canMove(suspects[0], "SOUTH"));
		System.out.println(board.canMove(suspects[0], "EAST"));
		
		board.moveSuspect(suspects[0], "NORTH");
		board.printBoard(suspects[0]);

		System.out.println(board.canMove(suspects[0], "NORTH"));
		System.out.println(board.canMove(suspects[0], "WEST"));
		System.out.println(board.canMove(suspects[0], "SOUTH"));
		System.out.println(board.canMove(suspects[0], "EAST"));

		board.moveSuspect(suspects[0], "NORTH");
		board.moveSuspect(suspects[0], "NORTH");
		board.moveSuspect(suspects[0], "NORTH");
		board.moveSuspect(suspects[0], "NORTH");
		board.printBoard(suspects[0]);
		System.out.println(board.canMove(suspects[0], "NORTH"));
		System.out.println(board.canMove(suspects[0], "WEST"));
		System.out.println(board.canMove(suspects[0], "SOUTH"));
		System.out.println(board.canMove(suspects[0], "EAST"));
		board.moveSuspect(suspects[0], "NORTH");
		board.moveSuspect(suspects[0], "WEST");
		board.printBoard(suspects[0]);
		System.out.println(board.canMove(suspects[0], "NORTH"));
		System.out.println(board.canMove(suspects[0], "WEST"));
		System.out.println(board.canMove(suspects[0], "SOUTH"));
		System.out.println(board.canMove(suspects[0], "EAST"));
		board.moveSuspect(suspects[0], "SOUTH");
		board.moveSuspect(suspects[1], "EAST");
		board.moveSuspect(suspects[1], "EAST");
		board.moveSuspect(suspects[1], "EAST");
		board.moveSuspect(suspects[1], "EAST");
		board.moveSuspect(suspects[1], "EAST");
		board.moveSuspect(suspects[1], "EAST");
		board.moveSuspect(suspects[1], "SOUTH");
		board.moveSuspect(suspects[1], "SOUTH");
		board.printBoard(suspects[0]);
		board.moveWeapon(weapons[0], rooms[7]);
		board.printBoard(suspects[0]);
		System.out.println(board.canTeleport(suspects[0]));
		System.out.println(board.canTeleport(suspects[1]));
		System.out.println(board.canTeleport(suspects[2]));
		board.moveSuspect(suspects[2], "SOUTH");
		board.moveSuspect(suspects[2], "WEST");
		board.moveSuspect(suspects[2], "WEST");
		board.moveSuspect(suspects[2], "SOUTH");
		board.moveSuspect(suspects[2], "SOUTH");
		board.moveSuspect(suspects[2], "SOUTH");
		board.moveSuspect(suspects[2], "SOUTH");
		board.moveSuspect(suspects[2], "EAST");
		board.printBoard(suspects[0]);
		System.out.println(board.canTeleport(suspects[0]));
		System.out.println(board.canTeleport(suspects[1]));
		System.out.println(board.canTeleport(suspects[2]));
		System.out.println(board.canTeleport(suspects[3]));
		board.teleport(suspects[0]);
		board.printBoard(suspects[0]);
		System.out.println(board.canPlayTurn(suspects[3]));
		board.moveSuspect(suspects[5], "WEST");
		board.moveSuspect(suspects[5], "WEST");
		board.moveSuspect(suspects[5], "WEST");
		board.moveSuspect(suspects[5], "WEST");
		board.moveSuspect(suspects[5], "WEST");
		board.moveSuspect(suspects[5], "WEST");
		board.moveSuspect(suspects[5], "WEST");
		board.moveSuspect(suspects[5], "NORTH");
		board.moveSuspect(suspects[5], "NORTH");
		board.moveSuspect(suspects[5], "NORTH");
		board.moveSuspect(suspects[5], "NORTH");
		board.moveSuspect(suspects[5], "NORTH");
		board.moveSuspect(suspects[5], "NORTH");
		board.moveSuspect(suspects[5], "NORTH");
		board.moveSuspect(suspects[5], "NORTH");
		board.moveSuspect(suspects[5], "NORTH");
		board.moveSuspect(suspects[5], "NORTH");
		board.moveSuspect(suspects[5], "NORTH");
		board.moveSuspect(suspects[5], "NORTH");
		board.moveSuspect(suspects[5], "NORTH");
		board.moveSuspect(suspects[5], "NORTH");
		board.moveSuspect(suspects[5], "NORTH");
		board.moveSuspect(suspects[5], "NORTH");
		board.moveSuspect(suspects[5], "NORTH");
		board.moveSuspect(suspects[5], "NORTH");
		board.moveSuspect(suspects[5], "WEST");
		board.moveSuspect(suspects[5], "WEST");
		board.printBoard(suspects[1]);
		System.out.println(board.canPlayTurn(suspects[3]));
		board.moveSuspect(suspects[5], "EAST");
		board.moveSuspect(suspects[5], "EAST");
		board.moveSuspect(suspects[5], "SOUTH");
		board.moveSuspect(suspects[5], "SOUTH");
		board.moveSuspect(suspects[5], "SOUTH");
		board.moveSuspect(suspects[5], "SOUTH");
		board.printBoard(suspects[1]);
		System.out.println(board.canUseExit(suspects[2], 3));
		board.exitRoom(suspects[2], 3);
		board.printBoard(suspects[1]);
		board.exitRoom(suspects[2], 3);
		board.teleport(suspects[1]);
		board.printBoard(suspects[1]);
	}
}
