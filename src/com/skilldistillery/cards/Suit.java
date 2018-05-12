package com.skilldistillery.cards;

public enum Suit {
	HEARTS(Character.toString((char) '\u2764')), SPADES(Character.toString((char) '\u2660')), CLUBS(
			Character.toString((char) '\u2663')), DIAMONDS(Character.toString((char) '\u2666'));
	private String name;

	private Suit(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}