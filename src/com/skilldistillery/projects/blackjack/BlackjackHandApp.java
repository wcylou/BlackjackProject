package com.skilldistillery.projects.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.cards.Card;
import com.skilldistillery.cards.Deck;

public class BlackjackHandApp {
	private Deck deck = new Deck();
	Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		BlackjackHandApp bjha = new BlackjackHandApp();
		bjha.run();
	}

	private void run() {
		List<Card> playerHand = new ArrayList<>();
		List<Card> dealerHand = new ArrayList<>();
		System.out.println("Welcome to Blackjack. 21 = winner winner chicken dinner");
		deck.shuffle();
		System.out.println("You have been assigned");
		playerHand.add(deck.dealCard());
		playerHand.add(deck.dealCard());
		dealerHand.add(deck.dealCard());
		dealerHand.add(deck.dealCard());
		System.out.println("Your hand: " + playerHand);
		System.out.println("Total value: " + getHandValue(playerHand));
		if (getHandValue(playerHand) == 21) {
			System.out.println("You got blackjack");
			if (getHandValue(dealerHand) == 21) {
				System.out.println("Computer also got blackjack. What are the odds eh");
			}
			else {
			System.exit(0);
			}
		}
		System.out.println("Dealer's first card is " + dealerHand.get(0));

		playerTurn(playerHand);
		if (getHandValue(playerHand) <= 21) {
			dealerTurn(dealerHand);
			determineWinner(playerHand, dealerHand);
		} else {
			System.out.println("Game finished");
		}
	}

	private void determineWinner(List<Card> playerHand, List<Card> dealerHand) {
		int finalPlayerCount = getHandValue(playerHand);
		int finalDealerCount = getHandValue(dealerHand);
		if (finalDealerCount > 21 && finalPlayerCount > 21) {
			System.out.println("No one wins boo");
		} else if (finalDealerCount <= 21 && finalPlayerCount > 21) {
			System.out.println("You lose");
		} else if (finalDealerCount > 21 && finalPlayerCount <= 21) {
			System.out.println("You win");
		} else if (finalDealerCount <= 21 && finalPlayerCount <= 21) {
			if (finalDealerCount == finalPlayerCount) {
				System.out.println("You draw");
			} else if (finalDealerCount > finalPlayerCount) {
				System.out.println("Computer wins");
			} else {
				System.out.println("You win");
			}
		}

	}

	private void dealerTurn(List<Card> dealerHand) {
		boolean keepPlaying = true;
		System.out.println("Dealer's hand: " + dealerHand);
		System.out.println("Dealer's total is: " + getHandValue(dealerHand));

		while (keepPlaying) {
			if (getHandValue(dealerHand) < 17) {
				Card dealerCard = deck.dealCard();
				dealerHand.add(dealerCard);
				System.out.println("Dealer gets dealt " + dealerCard);
				System.out.println("Dealer's total is: " + getHandValue(dealerHand));
				if (getHandValue(dealerHand) < 21) {
					keepPlaying = false;
				}
			}
			if (getHandValue(dealerHand) > 21) {
				System.out.println("Dealer goes bust");
				keepPlaying = false;
			}
		}
	}


	private void playerTurn(List<Card> hand) {
		boolean keepPlaying = true;
		while (keepPlaying) {
			System.out.println("What do you want to do?\n1.Hit\n2.Stick");
			int hitOrStick = sc.nextInt();
			switch (hitOrStick) {
			case 1:
				Card d = deck.dealCard();
				System.out.println(d);
				hand.add(d);
				System.out.println(getHandValue(hand));
				if (getHandValue(hand) > 21) {
					System.out.println("You go bust");
					keepPlaying = false;
				}
				break;
			case 2:
				System.out.println("You stick");
				System.out.println("Your hand value is " + getHandValue(hand));
				keepPlaying = false;
				break;
			}
		}
	}

	private int getHandValue(List<Card> hand) {
		int totalValue = 0;
		for (Card card : hand) {
			totalValue += card.getValue();
		}
		return totalValue;
	}
}
