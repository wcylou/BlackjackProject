package com.skilldistillery.cards;

import java.util.ArrayList;
import java.util.List;

public abstract class Hand {
	List<Card> hand = new ArrayList<>();

	public Hand() {

	}

	public int getHandValue() {
		int totalValue = 0;
		for (Card card : hand) {
			totalValue += card.getValue();
		}
		return totalValue;
	}

	public void addCard(Card card) {
		hand.add(card);
	}

	public void clearHand() {
		hand.clear();
	}

	public List<Card> getCards() {
		return hand;
	}

	@Override
	public String toString() {
		return "Hand value: " + getHandValue();
	}

	public List<Card> getHand() {
		return hand;
	}

	public void setHand(List<Card> hand) {
		this.hand = hand;
	}

}
