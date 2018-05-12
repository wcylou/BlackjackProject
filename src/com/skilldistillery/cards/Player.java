package com.skilldistillery.cards;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private String name;
	private int chips;
	private int winStreak;
	private int wagerAmount;
	private List<Card> playerHand;

	public Player(String name, int chips, int winStreak, List<Card> playerHand) {
		super();
		this.name = name;
		this.chips = chips;
		this.winStreak = winStreak;
		this.playerHand = playerHand;
	}

	public Player(String name, int chips, int winStreak) {
		super();
		this.name = name;
		this.chips = chips;
		this.winStreak = winStreak;
	}
	
	public void createHand() {
		playerHand = new ArrayList<>();
	}

	public void addCard(Card card) {
		playerHand.add(card);
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

	public int getWagerAmount() {
		return wagerAmount;
	}

	public void setWagerAmount(int wagerAmount) {
		this.wagerAmount = wagerAmount;
	}

	public List<Card> getPlayerHand() {
		return playerHand;
	}

	public void setPlayerHand(List<Card> playerHand) {
		this.playerHand = playerHand;
	}
	
	
}
