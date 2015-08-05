package cluedoTests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.*;

import cluedo.Board;
import cluedo.Direction;
import cluedo.Room;
import cluedo.Suspect;
import cluedo.Weapon;

/**
 * Tests possible movements types on the Board.
 *
 * @author Pauline Kelly & David Thomsen
 *
 */
public class MovementTests {

	public static final Suspect[] SUSPECTS = new Suspect[6];

	public static final Weapon[] WEAPONS = { new Weapon("Candlestick", "Cs"),
			new Weapon("Dagger", "Dg"), new Weapon("Lead Pipe", "Lp"),
			new Weapon("Revolver", "Rv"), new Weapon("Rope", "Rp"),
			new Weapon("Spanner", "Sp") };

	public static final Room[] ROOMS = { new Room("Kitchen", "KI"),
			new Room("Ball Room", "BA"), new Room("Conservatory", "CO"),
			new Room("Billiard Room", "BI"), new Room("Library", "LI"),
			new Room("Study", "ST"), new Room("Hall", "HA"),
			new Room("Lounge", "LO"), new Room("Dining Room", "DR") };

	private Board board;

	/**
	 * Sets up the board.
	 */
	@Before
	public void before() {

		ROOMS[0].addTeleport(ROOMS[5]);
		ROOMS[5].addTeleport(ROOMS[0]);
		ROOMS[2].addTeleport(ROOMS[7]);
		ROOMS[7].addTeleport(ROOMS[2]);

		SUSPECTS[0] = new Suspect("Miss Scarlett", "mS", 8, 25);
		SUSPECTS[1] = new Suspect("Colonel Mustard", "cM", 1, 18);
		SUSPECTS[2] = new Suspect("Mrs White", "mW", 10, 1);
		SUSPECTS[3] = new Suspect("Rev Green", "rG", 15, 1);
		SUSPECTS[4] = new Suspect("Mrs Peacock", "mP", 24, 7);
		SUSPECTS[5] = new Suspect("Professor Plum", "pP", 24, 20);

		try {
			board = new Board(ROOMS, SUSPECTS, WEAPONS);
		} catch (IOException e) {
			fail();
		}
	}

	@Test
	public void testCorridorMovement() {
		board.moveSuspect(SUSPECTS[0], Direction.NORTH);
		board.moveSuspect(SUSPECTS[0], Direction.NORTH);
		assertEquals("Should be at correct X coordinate", 8, SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 23,
				SUSPECTS[0].getY());
		board.moveSuspect(SUSPECTS[0], Direction.EAST);
		assertEquals("Should be at correct X coordinate", 9, SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 23,
				SUSPECTS[0].getY());
		board.moveSuspect(SUSPECTS[0], Direction.SOUTH);
		assertEquals("Should be at correct X coordinate", 9, SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 24,
				SUSPECTS[0].getY());
		board.moveSuspect(SUSPECTS[0], Direction.WEST);
		assertEquals("Should be at correct X coordinate", 8, SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 24,
				SUSPECTS[0].getY());
	}

	@Test
	public void testRoomEnteringAndExiting() {
		board.cheatMove(SUSPECTS[0], 12, 18);
		board.moveSuspect(SUSPECTS[0], Direction.SOUTH);
		assertEquals("Should be in the Hall", "Hall", SUSPECTS[0].getRoom()
				.getName());
		board.exitRoom(SUSPECTS[0], 1);
		assertEquals("Should be at correct X coordinate", 12,
				SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 18,
				SUSPECTS[0].getY());
		assertNull("Should no longer have a room!", SUSPECTS[0].getRoom());
		board.cheatMove(SUSPECTS[1], 13, 18);
		assertFalse("Other player cannot move here now",
				board.canMove(SUSPECTS[1], Direction.WEST));
	}

	/**
	 * I was worried that if one Suspect tried to go into a room after another
	 * Suspect using the same entrance they would somehow get in each other's
	 * way.
	 */
	@Test
	public void testTwoPeopleEnteringRoom() {
		board.cheatMove(SUSPECTS[0], 12, 18);
		board.moveSuspect(SUSPECTS[0], Direction.SOUTH);
		assertEquals("Should be in the Hall", "Hall", SUSPECTS[0].getRoom()
				.getName());
		board.cheatMove(SUSPECTS[1], 12, 18);
		assertTrue("Can also enter room from this coordinate",
				board.canMove(SUSPECTS[1], Direction.SOUTH));
		board.moveSuspect(SUSPECTS[1], Direction.SOUTH);
		assertEquals("Should also be in the Hall", "Hall", SUSPECTS[1]
				.getRoom().getName());
	}

	@Test
	public void testCanMoveCorridor() {
		assertTrue("1", board.canMove(SUSPECTS[0], Direction.NORTH));
		assertFalse("2", board.canMove(SUSPECTS[0], Direction.EAST));
		assertFalse("3", board.canMove(SUSPECTS[0], Direction.SOUTH));
		assertFalse("4", board.canMove(SUSPECTS[0], Direction.WEST));
		board.moveSuspect(SUSPECTS[0], Direction.NORTH);
		assertTrue("5", board.canMove(SUSPECTS[0], Direction.NORTH));
		assertTrue("6", board.canMove(SUSPECTS[0], Direction.EAST));
		assertTrue("7", board.canMove(SUSPECTS[0], Direction.SOUTH));
		assertFalse("8", board.canMove(SUSPECTS[0], Direction.WEST));
		board.moveSuspect(SUSPECTS[0], Direction.SOUTH);
		assertTrue("9", board.canMove(SUSPECTS[0], Direction.NORTH));
		assertFalse("10", board.canMove(SUSPECTS[0], Direction.EAST));
		assertFalse("11", board.canMove(SUSPECTS[0], Direction.SOUTH));
		assertFalse("12", board.canMove(SUSPECTS[0], Direction.WEST));
	}

	@Test
	public void testCanMoveRoom() {
		board.cheatMove(SUSPECTS[0], 12, 18);
		assertTrue("Should be able to enter room",
				board.canMove(SUSPECTS[0], Direction.SOUTH));
		board.moveSuspect(SUSPECTS[0], Direction.SOUTH);
		assertFalse("Cannot leave the room by Moving!",
				board.canMove(SUSPECTS[0], Direction.NORTH));
		assertTrue("Can leave by exit 1",
				board.canUseExit(SUSPECTS[0], 1));
		board.cheatMove(SUSPECTS[1], 12, 18);
		assertFalse("Cannot leave by exit 1",
				board.canUseExit(SUSPECTS[0], 1));
		board.moveSuspect(SUSPECTS[1], Direction.WEST);
		assertTrue("Can leave by exit 1 again",
				board.canUseExit(SUSPECTS[0], 1));
	}

	@Test
	public void testCanPlayTurnRoom() {
		board.cheatMove(SUSPECTS[0], 12, 18);
		board.moveSuspect(SUSPECTS[0], Direction.SOUTH);
		assertTrue("Should be able to leave the room!",
				board.canPlayTurn(SUSPECTS[0]));
		board.cheatMove(SUSPECTS[1], 12, 18);
		board.cheatMove(SUSPECTS[2], 13, 18);
		board.cheatMove(SUSPECTS[3], 16, 21);
		assertFalse("All of the exits are blocked!",
				board.canPlayTurn(SUSPECTS[0]));
		board.moveSuspect(SUSPECTS[1], Direction.WEST);
		assertTrue("Can leave the room again!",
				board.canPlayTurn(SUSPECTS[0]));
	}

	@Test
	public void testCanPlayTurnCorridor() {
		assertTrue("Should be able to play a turn",
				board.canPlayTurn(SUSPECTS[0]));
		board.cheatMove(SUSPECTS[1], 8, 24);
		assertFalse("Cannot play a turn, is blocked!",
				board.canPlayTurn(SUSPECTS[0]));
		board.moveSuspect(SUSPECTS[1], Direction.NORTH);
		assertTrue("Can play a turn, no longer blocked",
				board.canPlayTurn(SUSPECTS[0]));
	}

	@Test
	public void testWeaponMove() {
		board.moveWeapon(WEAPONS[0], ROOMS[0]);
		assertEquals("Should be in Kitchen", "Kitchen", WEAPONS[0].getRoom()
				.getName());
		board.moveWeapon(WEAPONS[0], ROOMS[1]);
		assertEquals("Should be in Ball Room", "Ball Room", WEAPONS[0]
				.getRoom().getName());
	}

	@Test
	public void testKitchenExits() {
		board.cheatMove(SUSPECTS[0], 5, 8);
		assertTrue("Should be able to enter Kitchen",
				board.canMove(SUSPECTS[0], Direction.NORTH));
		board.moveSuspect(SUSPECTS[0], Direction.NORTH);
		assertEquals("Should be in the Kitchen", "Kitchen", SUSPECTS[0]
				.getRoom().getName());
		assertTrue("Can use exit 1", board.canUseExit(SUSPECTS[0], 1));
		board.exitRoom(SUSPECTS[0], 1);
		assertEquals("Should be at correct X coordinate", 5, SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 8, SUSPECTS[0].getY());
	}

	@Test
	public void testBallRoomExits() {
		board.cheatMove(SUSPECTS[0], 8, 6);
		assertTrue("Should be able to enter Ball Room",
				board.canMove(SUSPECTS[0], Direction.EAST));
		board.moveSuspect(SUSPECTS[0], Direction.EAST);
		assertEquals("Should be in the Ball Room", "Ball Room", SUSPECTS[0]
				.getRoom().getName());
		assertTrue("Can use exit 1", board.canUseExit(SUSPECTS[0], 1));
		board.exitRoom(SUSPECTS[0], 1);
		assertEquals("Should be at correct X coordinate", 8, SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 6, SUSPECTS[0].getY());
		board.moveSuspect(SUSPECTS[0], Direction.EAST);
		assertTrue("Can use exit 1", board.canUseExit(SUSPECTS[0], 2));
		board.exitRoom(SUSPECTS[0], 2);
		assertEquals("Should be at correct X coordinate", 10,
				SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 9, SUSPECTS[0].getY());
		board.moveSuspect(SUSPECTS[0], Direction.NORTH);
		assertTrue("Can use exit 1", board.canUseExit(SUSPECTS[0], 3));
		board.exitRoom(SUSPECTS[0], 3);
		assertEquals("Should be at correct X coordinate", 15,
				SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 9, SUSPECTS[0].getY());
		board.moveSuspect(SUSPECTS[0], Direction.NORTH);
		assertTrue("Can use exit 1", board.canUseExit(SUSPECTS[0], 4));
		board.exitRoom(SUSPECTS[0], 4);
		assertEquals("Should be at correct X coordinate", 17,
				SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 6, SUSPECTS[0].getY());
	}

	@Test
	public void testConservatoryExits() {
		board.cheatMove(SUSPECTS[0], 19, 6);
		assertTrue("Should be able to enter Conservatory",
				board.canMove(SUSPECTS[0], Direction.NORTH));
		board.moveSuspect(SUSPECTS[0], Direction.NORTH);
		assertEquals("Should be in the Conservatory", "Conservatory",
				SUSPECTS[0].getRoom().getName());
		assertTrue("Can use exit 1", board.canUseExit(SUSPECTS[0], 1));
		board.exitRoom(SUSPECTS[0], 1);
		assertEquals("Should be at correct X coordinate", 19,
				SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 6, SUSPECTS[0].getY());
	}

	@Test
	public void testBilliardRoomExits() {
		board.cheatMove(SUSPECTS[0], 18, 10);
		assertTrue("Should be able to enter Billiard Room",
				board.canMove(SUSPECTS[0], Direction.EAST));
		board.moveSuspect(SUSPECTS[0], Direction.EAST);
		assertEquals("Should be in the Billiard Room", "Billiard Room",
				SUSPECTS[0].getRoom().getName());
		assertTrue("Can use exit 1", board.canUseExit(SUSPECTS[0], 1));
		board.exitRoom(SUSPECTS[0], 1);
		assertEquals("Should be at correct X coordinate", 18,
				SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 10,
				SUSPECTS[0].getY());
		board.moveSuspect(SUSPECTS[0], Direction.EAST);
		assertTrue("Can use exit 1", board.canUseExit(SUSPECTS[0], 2));
		board.exitRoom(SUSPECTS[0], 2);
		assertEquals("Should be at correct X coordinate", 23,
				SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 14,
				SUSPECTS[0].getY());
	}

	@Test
	public void testLibraryExits() {
		board.cheatMove(SUSPECTS[0], 21, 14);
		assertTrue("Should be able to enter Library",
				board.canMove(SUSPECTS[0], Direction.SOUTH));
		board.moveSuspect(SUSPECTS[0], Direction.SOUTH);
		assertEquals("Should be in the Library", "Library", SUSPECTS[0]
				.getRoom().getName());
		assertTrue("Can use exit 1", board.canUseExit(SUSPECTS[0], 1));
		board.exitRoom(SUSPECTS[0], 1);
		assertEquals("Should be at correct X coordinate", 21,
				SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 14,
				SUSPECTS[0].getY());
		board.moveSuspect(SUSPECTS[0], Direction.SOUTH);
		assertTrue("Can use exit 1", board.canUseExit(SUSPECTS[0], 2));
		board.exitRoom(SUSPECTS[0], 2);
		assertEquals("Should be at correct X coordinate", 17,
				SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 17,
				SUSPECTS[0].getY());
	}

	@Test
	public void testStudyExits() {
		board.cheatMove(SUSPECTS[0], 18, 21);
		assertTrue("Should be able to enter Study",
				board.canMove(SUSPECTS[0], Direction.SOUTH));
		board.moveSuspect(SUSPECTS[0], Direction.SOUTH);
		assertEquals("Should be in the Study", "Study", SUSPECTS[0].getRoom()
				.getName());
		assertTrue("Can use exit 1", board.canUseExit(SUSPECTS[0], 1));
		board.exitRoom(SUSPECTS[0], 1);
		assertEquals("Should be at correct X coordinate", 18,
				SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 21,
				SUSPECTS[0].getY());
	}

	@Test
	public void testHallExits() {
		board.cheatMove(SUSPECTS[0], 12, 18);
		assertTrue("Should be able to enter Hall",
				board.canMove(SUSPECTS[0], Direction.SOUTH));
		board.moveSuspect(SUSPECTS[0], Direction.SOUTH);
		assertEquals("Should be in the Hall", "Hall", SUSPECTS[0].getRoom()
				.getName());
		assertTrue("Can use exit 1", board.canUseExit(SUSPECTS[0], 1));
		board.exitRoom(SUSPECTS[0], 1);
		assertEquals("Should be at correct X coordinate", 12,
				SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 18,
				SUSPECTS[0].getY());
		board.moveSuspect(SUSPECTS[0], Direction.SOUTH);
		assertTrue("Can use exit 1", board.canUseExit(SUSPECTS[0], 2));
		board.exitRoom(SUSPECTS[0], 2);
		assertEquals("Should be at correct X coordinate", 13,
				SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 18,
				SUSPECTS[0].getY());
		board.moveSuspect(SUSPECTS[0], Direction.SOUTH);
		assertTrue("Can use exit 1", board.canUseExit(SUSPECTS[0], 2));
		board.exitRoom(SUSPECTS[0], 3);
		assertEquals("Should be at correct X coordinate", 16,
				SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 21,
				SUSPECTS[0].getY());
	}

	@Test
	public void testLoungeExits() {
		board.cheatMove(SUSPECTS[0], 7, 19);
		assertTrue("Should be able to enter Lounge",
				board.canMove(SUSPECTS[0], Direction.SOUTH));
		board.moveSuspect(SUSPECTS[0], Direction.SOUTH);
		assertEquals("Should be in the Lounge", "Lounge", SUSPECTS[0].getRoom()
				.getName());
		assertTrue("Can use exit 1", board.canUseExit(SUSPECTS[0], 1));
		board.exitRoom(SUSPECTS[0], 1);
		assertEquals("Should be at correct X coordinate", 7, SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 19,
				SUSPECTS[0].getY());
	}

	@Test
	public void testDiningRoomExits() {
		board.cheatMove(SUSPECTS[0], 9, 13);
		assertTrue("Should be able to enter Dining Room",
				board.canMove(SUSPECTS[0], Direction.WEST));
		board.moveSuspect(SUSPECTS[0], Direction.WEST);
		assertEquals("Should be in the Dining Room", "Dining Room", SUSPECTS[0]
				.getRoom().getName());
		assertTrue("Can use exit 1", board.canUseExit(SUSPECTS[0], 1));
		board.exitRoom(SUSPECTS[0], 1);
		assertEquals("Should be at correct X coordinate", 9, SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 13,
				SUSPECTS[0].getY());
		board.moveSuspect(SUSPECTS[0], Direction.WEST);
		assertTrue("Can use exit 1", board.canUseExit(SUSPECTS[0], 2));
		board.exitRoom(SUSPECTS[0], 2);
		assertEquals("Should be at correct X coordinate", 7, SUSPECTS[0].getX());
		assertEquals("Should be at correct Y coordinate", 17,
				SUSPECTS[0].getY());
	}

	@Test
	public void testCanTeleportWithDoorBlocked() {
		board.cheatMove(SUSPECTS[0], 5, 8);
		board.moveSuspect(SUSPECTS[0], Direction.NORTH);
		board.cheatMove(SUSPECTS[1], 5, 8);
		assertTrue("Should be able to play turn",
				board.canPlayTurn(SUSPECTS[0]));
	}

	@Test
	public void testKitchenStudyTeleport() {
		board.cheatMove(SUSPECTS[0], 5, 8);
		board.moveSuspect(SUSPECTS[0], Direction.NORTH);
		assertEquals("Should be in the Kitchen", "Kitchen", SUSPECTS[0]
				.getRoom().getName());
		assertTrue("Should be able to teleport",
				board.canTeleport(SUSPECTS[0]));
		board.teleport(SUSPECTS[0]);
		assertEquals("Should be in the Study", "Study", SUSPECTS[0].getRoom()
				.getName());
		assertTrue("Should be able to teleport",
				board.canTeleport(SUSPECTS[0]));
		board.teleport(SUSPECTS[0]);
		assertEquals("Should be in the Kitchen again", "Kitchen", SUSPECTS[0]
				.getRoom().getName());
	}

	@Test
	public void testConservatoryLoungeTeleport() {
		board.cheatMove(SUSPECTS[0], 19, 6);
		board.moveSuspect(SUSPECTS[0], Direction.NORTH);
		assertEquals("Should be in the Conservatory", "Conservatory",
				SUSPECTS[0].getRoom().getName());
		assertTrue("Should be able to teleport",
				board.canTeleport(SUSPECTS[0]));
		board.teleport(SUSPECTS[0]);
		assertEquals("Should be in the Lounge", "Lounge", SUSPECTS[0].getRoom()
				.getName());
		assertTrue("Should be able to teleport",
				board.canTeleport(SUSPECTS[0]));
		board.teleport(SUSPECTS[0]);
		assertEquals("Should be in the Conservatory again", "Conservatory",
				SUSPECTS[0].getRoom().getName());
	}

	@Test
	public void testCannotEnterRoomFromWrongDirection() {
		board.cheatMove(SUSPECTS[0], 8, 20);
		assertFalse("Cannot enter from from this direction!",
				board.canMove(SUSPECTS[0], Direction.WEST));
	}

	@Test
	public void testWrongExitNumber() {
		board.cheatMove(SUSPECTS[0], 5, 8);
		board.moveSuspect(SUSPECTS[0], Direction.NORTH);
		assertFalse("There is no exit 2", board.canUseExit(SUSPECTS[0], 2));

	}
}
