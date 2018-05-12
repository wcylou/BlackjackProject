package com.skilldistillery.cards;

public enum Rank {

	TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(
			10), ACE(11);

	private int value;

	private Rank(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

//	public void printValue(Rank rank) {
//		switch (rank) {
//		case ACE:
//		case KING:
//		case QUEEN:
//		case JACK:
//		case TEN:
//			break;
//		case TWO:
//			break;
//		case THREE:
//			break;
//		case FOUR:
//			break;
//		case FIVE:
//			break;
//		case SIX:
//			break;
//		case SEVEN:
//			break;
//		case EIGHT:
//			break;
//		case NINE:
//			break;
//		default:
//			break;
//		}
	}
