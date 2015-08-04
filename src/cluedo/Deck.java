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

	public Deck(List<Weapon> weapons, List<Room> rooms, List<Suspect> suspects) {
		this.cards = new ArrayList<>();
		getSolution(weapons, rooms, suspects);
		shuffleCards(weapons, rooms, suspects);

		System.out.print("Solution is: ");
		for(Card c : solution){
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
	public void getSolution(List<Weapon> weapons, List<Room> rooms, List<Suspect> suspects) {
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
	 * Shuffles the cards into one group.
	 *
	 * @return The List of shuffled cards.
	 */
	public void shuffleCards(List<Weapon> weapons, List<Room> rooms,
			List<Suspect> suspects) {

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

	/**
	 * Checks the solution against the suggestion or the accusation.
	 *
	 * @param suspect The suspect to check
	 * @param room The room to check
	 * @param weapon The weapon to check
	 * @return Whether all the Cards match
	 */
	public boolean checkSolution(Suspect suspect, Room room, Weapon weapon) {
		//System.out.println("S:" + solution[0] + "R:" + solution[1] + "W" + solution[2]);
		//System.out.println("S:" + suspect + "R:" + room + "W" + weapon);

		if(suspect.equals(solution[0]) &&
				room.equals(solution[1]) &&
				weapon.equals(solution[2])){
			return true;
		}
		return false;
	}
}