package com.skilldistillery.cards;

public enum Rank {

	TWO(2, 1), THREE(3, 1), FOUR(4, 1), FIVE(5, 1), SIX(6, 1), SEVEN(7, 0), EIGHT(8, 0), NINE(9, 0), TEN(10, -1), 
	JACK(10, -1), QUEEN(10, -1), KING(10, -1), ACE(11, -1);

	private int value;
	private int cardCoutingValue;
	
	
	private Rank(int value, int cardCoutingValue) {
		this.value = value;
		this.cardCoutingValue = cardCoutingValue;
	}

	private Rank(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public int getCardCoutingValue() {
		return cardCoutingValue;
	}

	public void setCardCoutingValue(int cardCoutingValue) {
		this.cardCoutingValue = cardCoutingValue;
	}

	public void setValue(int value) {
		this.value = value;
	}

}