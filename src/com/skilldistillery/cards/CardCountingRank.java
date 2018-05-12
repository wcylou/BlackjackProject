package com.skilldistillery.cards;

public enum CardCountingRank {

	TWO(1), THREE(1), FOUR(1), FIVE(1), SIX(1), SEVEN(0), EIGHT(0), NINE(0), TEN(-1), JACK(-1), 
	QUEEN(-1), KING(-1), ACE(-1);

	private int value;

	private CardCountingRank(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
