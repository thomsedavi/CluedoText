package cluedo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Contains the deck of Cards. Picks a Weapon, Room and Suspect at random to
 * make into the solution. Sorts the remainder and distributes them evenly to
 * the players.
 *
 * @author Pauline Kelly
 *
 */
public class Deck {

	private List<Card> allCards; // all possible cards
	private List<Card> dealingCards; // the remainder of cards that are being
										// dealt out
	private Card[] solution = new Card[3];

	public Deck(List<Weapon> weapons, List<Room> rooms, List<Suspect> suspects) {
		allCards = new ArrayList<Card>();
		dealingCards = new ArrayList<Card>();
		getSolution(weapons, rooms, suspects);
		shuffleCards(weapons, rooms, suspects);

		System.out.print("Solution is: ");
		for (Card c : solution) {
			System.out.print(c + " ");
		}
		System.out.println("\n");
	}

	/**
	 * Deals the cards out to the players
	 *
	 * @param players
	 */
	public List<Player> dealCards(List<Player> players) {
		while (true) {

			for (Player p : players) {
				p.addToHand(dealingCards.remove(0)); // removes the first card,
														// others
				// slide down
				if (dealingCards.isEmpty()) {
					return players;
				}
			}
		}
	}

	/**
	 * Pick out the 3 cards from each of the Weapons, Rooms and Suspect cards.
	 *
	 * @param weapons
	 * @param rooms
	 * @param suspects
	 */
	public void getSolution(List<Weapon> weapons, List<Room> rooms,
			List<Suspect> suspects) {
		Random rand = new Random();
		int index;

		index = rand.nextInt(weapons.size() - 1);
		this.solution[0] = suspects.get(index);

		index = rand.nextInt(rooms.size() - 1);
		this.solution[1] = rooms.get(index);

		index = rand.nextInt(suspects.size() - 1);
		this.solution[2] = weapons.get(index);
	}

	/**
	 * Adds all the cards together, removes the solution Cards and then shuffles
	 * the remainder..
	 */
	public void shuffleCards(List<Weapon> weapons, List<Room> rooms,
			List<Suspect> suspects) {

		allCards.addAll(weapons);
		allCards.addAll(rooms);
		allCards.addAll(suspects);

		dealingCards.addAll(allCards);

		dealingCards.remove(solution[0]);
		dealingCards.remove(solution[1]);
		dealingCards.remove(solution[2]);

		Collections.shuffle(dealingCards);
	}

	/**
	 * Checks the solution against the suggestion or the accusation.
	 *
	 * @param suspect
	 *            The suspect to check
	 * @param room
	 *            The room to check
	 * @param weapon
	 *            The weapon to check
	 * @return Whether all the Cards match
	 */
	public boolean checkSolution(Suspect suspect, Room room, Weapon weapon) {
		if (suspect.equals(solution[0]) && room.equals(solution[1])
				&& weapon.equals(solution[2])) {
			return true;
		}
		return false;
	}

	/**
	 * @param str
	 *            2-letter code of Card
	 * @return if this is a valid code
	 */

	public boolean checkCodeIsValid(String str) {
		Card result = getCardFromCode(str);
		if (result != null)
			return true;
		else
			return false;

	}

	/**
	 * @param str
	 *            2-letter code of card
	 * @return
	 */
	public Card getCardFromCode(String str) {
		Card result = null;

		for (Card c : allCards) {
			if (str.equalsIgnoreCase(c.getCode()))
				result = c;
		}

		return result;
	}
}