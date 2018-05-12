package com.skilldistillery.cards;

public class Player {
	private String name;
	private int chips;

	public Player(String name, int chips) {
		super();
		this.name = name;
		this.chips = chips;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getChips() {
		return chips;
	}
	
	public void setChips(int chips) {
		this.chips = chips;
	}

	@Override
	public String toString() {
		return name + "currently has " + chips;
	}
	
	
}
