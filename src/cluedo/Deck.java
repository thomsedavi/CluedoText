package cluedo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Contains the deck of cards.
 * @author Pauline Kelly
 *
 */
public class Deck {

	private List<Card> cards;
	private Card[] solution = new Card[3];

	public Deck(List<Card> weapons, List<Card> rooms, List<Card> suspects) {
		this.cards = new ArrayList<>();
		getSolution(weapons, rooms, suspects);
		shuffleCards(weapons, rooms, suspects);
	}

	/**
	 * Deals the cards out to the players
	 *
	 * @param players
	 */
	public List<Player> dealCards(List<Player> players) {
		while (true) {
			for (Player p : players) {
				p.addToHand(cards.remove(0)); // removes the first card, others
				// slide down
				if (cards.isEmpty()) {
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
	public void getSolution(List<Card> weapons, List<Card> rooms,
			List<Card> suspects) {
		Random rand = new Random();
		int index;

		index = rand.nextInt(weapons.size() - 1);

		this.solution[0] = weapons.get(index);

		index = rand.nextInt(rooms.size() - 1);
		this.solution[1] = weapons.get(index);
		index = rand.nextInt(suspects.size() - 1);
		this.solution[2] = weapons.get(index);
	}

	/**
	 * Shuffles the cards into one group.
	 *
	 * @return The List of shuffled cards.
	 */
	public void shuffleCards(List<Card> weapons, List<Card> rooms,
			List<Card> suspects) {

		cards.addAll(weapons);
		cards.addAll(rooms);
		cards.addAll(suspects);

		cards.remove(solution[0]);
		cards.remove(solution[1]);
		cards.remove(solution[2]);

		Collections.shuffle(cards);
	}

	public Card[] getSolution() {
		return solution;
	}

	public void setSolution(Card[] solution) {
		this.solution = solution;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
}