package com.skilldistillery.cards;

public class Player {
	private String name;
	private int chips;
	private int winStreak;

	public Player(String name, int chips, int winStreak) {
		super();
		this.name = name;
		this.chips = chips;
		this.winStreak = winStreak;
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

	public int getWinStreak() {
		return winStreak;
	}

	public void setWinStreak(int winStreak) {
		this.winStreak = winStreak;
	}
	
	
}
