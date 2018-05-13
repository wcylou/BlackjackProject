package com.skilldistillery.cards;

public class Card {
	private Rank rank;
	private Suit suit;
	private CardCountingRank cardCountingRank;

	public Card() {

	}

	public Card(Rank rank, Suit suit) {
		super();
		this.rank = rank;
		this.suit = suit;
	}

	public int getValue() {
		return rank.getValue();
	}
	
	public int getCardCountingValue() {
		return cardCountingRank.getValue();
	}
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(rank);
		builder.append(" of ");
		builder.append(this.suit);
		return builder.toString();
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public CardCountingRank getCardCountingRank() {
		return cardCountingRank;
	}

	public void setCardCountingRank(CardCountingRank cardCountingRank) {
		this.cardCountingRank = cardCountingRank;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardCountingRank == null) ? 0 : cardCountingRank.hashCode());
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
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
		Card other = (Card) obj;
		if (cardCountingRank != other.cardCountingRank)
			return false;
		if (rank != other.rank)
			return false;
		if (suit != other.suit)
			return false;
		return true;
	}
}
