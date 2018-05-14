package com.skilldistillery.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	private List<Card> cards;
	private double cardCountingValue;

	public Deck() {
		cards = createDeck();
	}

	private List<Card> createDeck() {
		List<Card> deck = new ArrayList<>();
		int randomNumberOfDecks = (int)((Math.random()*6) + 1);
		for (int i = 0; i < randomNumberOfDecks; i++) {
			for (Suit s : Suit.values()) {
				for (Rank r : Rank.values()) {
					deck.add(new Card(r, s));
				}
			}
		}
		return deck;
	}

	public int checkDeckSize() {
		return cards.size();
	}

	public void shuffle() {
		Collections.shuffle(cards);
	}

	public Card dealCard() {
		return cards.remove(0);
	}

	public void clearDeck() {
		cards.clear();
	}

	public double getCardCountingValue() {
		return cardCountingValue;
	}

	public void setCardCountingValue(double cardCountingValue) {
		this.cardCountingValue = cardCountingValue;
	}

}
