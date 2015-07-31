package cluedo;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private List <Card> hand;
	private String name;
	private Suspect suspect;

	public Player(String name, Suspect selected) {
		this.name = name;
		this.suspect = selected;
		this.hand = new ArrayList<>();
	}

	/**
	 * Adds the card to the players hand.
	 *
	 * @param The card to add to the hand
	 */
	public void addToHand(Card card) {
		hand.add(card);
	}

	public Suspect getSuspect() {
		return suspect;
	}

	public String getName() {
		return name;
	}

	public List<Card> getHand() {
		return hand;
	}

}