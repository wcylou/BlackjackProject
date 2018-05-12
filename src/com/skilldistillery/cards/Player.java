package com.skilldistillery.cards;

public class Player {
	private String name;
	private Hand hand;
	private int chips;
	
	public Player(String name, Hand hand, int chips) {
		super();
		this.name = name;
		this.hand = hand;
		this.chips = chips;
	}

	public Player(String name, Hand hand) {
		super();
		this.name = name;
		this.hand = hand;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

	
}
