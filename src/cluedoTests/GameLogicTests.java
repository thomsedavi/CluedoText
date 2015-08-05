package cluedoTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.*;

import cluedo.Board;
import cluedo.Card;
import cluedo.GameOfCluedo;
import cluedo.Hud;
import cluedo.Player;
import cluedo.Room;
import cluedo.Suspect;
import cluedo.Weapon;

/**
 * Tests that user input on the board is correct
 * @author kellypaul1
 *
 */
public class GameLogicTests {

	public static final Suspect[] SUSPECTS = {
		new Suspect("Miss Scarlett", "mS", 8, 25),
		new Suspect("Colonel Mustard", "cM", 1, 18),
		new Suspect("Mrs White", "mW", 10, 1),
		new Suspect("Rev Green", "rG", 15, 1),
		new Suspect("Mrs Peacock", "mP", 24, 7),
		new Suspect("Professor Plum", "pP", 24, 20) };

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
	private GameOfCluedo game;

	private Hud hud = new Hud(SUSPECTS, null, WEAPONS, ROOMS, game);

	//Create a list of dummy players and cards
	private List<Player> players = new ArrayList<Player>();
	private List<Card> cards = new ArrayList<Card>();

	/**
	 * Sets up the board for the tests
	 */
	@Before
	public void before() {
		players.add(new Player("Jane", SUSPECTS[0]));
		players.add(new Player("Jim", SUSPECTS[1]));

		cards.add(SUSPECTS[0]);
		cards.add(WEAPONS[0]);
		cards.add(ROOMS[0]);
	}

	@Test
	public void validPlayersAreEliminated1() throws InterruptedException{
		GameOfCluedo game = new GameOfCluedo(players,cards);
		assertFalse(game.noMorePlayers());
	}

	@Test
	public void validPlayersAreEliminated2() throws InterruptedException{
		GameOfCluedo game = new GameOfCluedo(players,cards);

		for(Player p : players){
			p.eliminate();
		}

		assertTrue(game.noMorePlayers());
	}

	@Test
	public void checkDiceRolls() throws InterruptedException{
		GameOfCluedo game = new GameOfCluedo(players,cards);
		for(int i = 0; i < 200; ++i){
			int num = game.rollDice();
			assertTrue(num != 0 && num < 7);
		}

	}



	@Test
	public void invalidHudInput1(){

	}


}