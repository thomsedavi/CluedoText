package cluedoTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.*;

import cluedo.Card;
import cluedo.GameOfCluedo;
import cluedo.Player;
import cluedo.Room;
import cluedo.Suspect;
import cluedo.Weapon;

/**
 * Tests that user input on the board is correct
 * @author Pauline Kelly
 *
 */
public class InputTests {

	public static final Suspect[] SUSPECTS = {
		new Suspect("Miss Scarlett", "mS", 8, 25)};

	public static final Weapon[] WEAPONS = {
		new Weapon("Candlestick", "Cs")};

	public static final Room[] ROOMS = {
		new Room("Kitchen", "KI")};

	//Create a list of dummy players and cards
	private List<Player> players = new ArrayList<Player>();
	private List<Card> cards = new ArrayList<Card>();
	private Scanner sc;

	@Before
	public void before() {
		cards.add(SUSPECTS[0]);
		cards.add(WEAPONS[0]);
		cards.add(ROOMS[0]);
	}

	@After
	public void after(){
		sc.close();
	}

	@Test
	public void tryInitialiseWith3Players() throws InterruptedException, FileNotFoundException{
		GameOfCluedo game = new GameOfCluedo(players,cards);
		sc = new Scanner(new File("TestingFiles/3playerInitialisation.txt"));
		game.initialise(sc);
		assertEquals(10,game.inputCounter);  //1 for numPlayers, 3 names * 3 players
	}

	@Test
	public void tryInitialiseWith0Players() throws InterruptedException, FileNotFoundException{
		GameOfCluedo game = new GameOfCluedo(players,cards);
		sc = new Scanner(new File("TestingFiles/0playerInitialisation.txt"));
		game.initialise(sc);
		assertNotEquals(10, game.inputCounter);  //Additional input used in error handling
	}

	@Test
	public void tryInitialiseWith7Players() throws InterruptedException, FileNotFoundException{
		GameOfCluedo game = new GameOfCluedo(players,cards);
		sc = new Scanner(new File("TestingFiles/7playerInitialisation.txt"));
		game.initialise(sc);
		assertNotEquals(10, game.inputCounter);  //Additional input used in error handling
	}

	@Test
	public void tryInitialiseWithQPlayers() throws InterruptedException, FileNotFoundException{  //Checks for non-integer input
		GameOfCluedo game = new GameOfCluedo(players,cards);
		sc = new Scanner(new File("TestingFiles/QplayerInitialisation.txt"));
		game.initialise(sc);
		assertNotEquals(10, game.inputCounter);  //Additional input used in error handling
	}
}












