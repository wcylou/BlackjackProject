package com.skilldistillery.cards;

import java.util.ArrayList;
import java.util.List;

public class Dealer {
	private List<Card> dealerHand;
	
	public void addCard(Card card) {
		dealerHand.add(card);
	}
	
	public Dealer() {
	}

	public Dealer(List<Card> dealerHand) {
		super();
		this.dealerHand = dealerHand;
	}
	
	public void createHand() {
		dealerHand = new ArrayList<>();
	}

	public List<Card> getDealerHand() {
		return dealerHand;
	}

	public void setDealerHand(List<Card> dealerHand) {
		this.dealerHand = dealerHand;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dealerHand == null) ? 0 : dealerHand.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dealer other = (Dealer) obj;
		if (dealerHand == null) {
			if (other.dealerHand != null)
				return false;
		} else if (!dealerHand.equals(other.dealerHand))
			return false;
		return true;
	}


}
