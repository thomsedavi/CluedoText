package cluedo;

import java.util.ArrayList;

import java.util.List;

/**
 * Represents a Player in the game of Cluedo. Has a Hand of Cards and also a
 * Suspect representing their character that is also a Card that can be in this
 * or someone else's hand.
 *
 * @author Pauline Kelly & David Thomsen
 *
 */
public class Player {

	private List<Card> hand;
	private String name;
	private Suspect suspect;
	private boolean eliminated = false; // whether the character has been
										// eliminated from the game after a
										// false Accusation
	private boolean winner = false;

	public Player(String name, Suspect selected) {
		this.name = name;
		this.suspect = selected;
		this.hand = new ArrayList<>();
	}

	/**
	 * Adds the card to the players hand.
	 *
	 * @param The
	 *            card to add to the hand
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

	/**
	 * @param Cards
	 *            Suggested by opponent
	 * @return quantity of matching cards in this Player's Hand
	 */
	public int qtyMatching(Card[] cards) {
		int result = 0;
		for (Card c : cards) {
			if (hand.contains(c))
				result++;
		}
		return result;
	}

	public void eliminate() {
		this.eliminated = true;
	}

	public boolean isEliminated() {
		return this.eliminated;
	}

	public void isWinner() {
		this.winner  = true;
	}

}