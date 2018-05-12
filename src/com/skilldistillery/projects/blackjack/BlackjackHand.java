package com.skilldistillery.projects.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.cards.Card;
import com.skilldistillery.cards.Dealer;
import com.skilldistillery.cards.Deck;
import com.skilldistillery.cards.Hand;
import com.skilldistillery.cards.Player;

public class BlackjackHand extends Hand {

	private Player player = new Player("", 1000);
	private Deck deck = new Deck();
	Scanner sc = new Scanner(System.in);

	public void run() {
		System.out.println("Welcome to Blackjack. Aim of the game is 21 WINNER WINNER CHICKEN DINNER");
		System.out.println("What is your name");
		String name = sc.next();
		player.setName(name);
		playGame();
	}

	private void playGame() {
		List<Card> playerHand = new ArrayList<>();
		List<Card> dealerHand = new ArrayList<>();
		System.out.println("Current chip count: " + player.getChips());
		System.out.println("How much do you want to wager?");
		int wagerAmount = sc.nextInt();
		deck.shuffle();
		initialDeal(playerHand, dealerHand, wagerAmount);
		playerTurn(playerHand);
		if (getHandValue(playerHand) <= 21) {
			dealerTurn(dealerHand);
			determineWinner(playerHand, dealerHand, wagerAmount, player);
			askPlayAgain();
		} else {
			askPlayAgain();
		}
	}

	private void initialDeal(List<Card> playerHand, List<Card> dealerHand, int wagerAmount) {
		Card firstPlayerCard = deck.dealCard();
		playerHand.add(firstPlayerCard);
		Card secondPlayerCard = deck.dealCard();
		playerHand.add(secondPlayerCard);
		dealerHand.add(deck.dealCard());
		dealerHand.add(deck.dealCard());
		System.out.println(player.getName() + "'s hand: " + playerHand);
		System.out.println("Total value: " + getHandValue(playerHand));
		if (getHandValue(playerHand) == 21) {
			blackjack(dealerHand, wagerAmount);
		}
		System.out.println("Dealer's first card is " + dealerHand.get(0));
		boolean keepChecking = true;
		while (keepChecking) {
			if (firstPlayerCard.getValue() == secondPlayerCard.getValue()) {
				if (firstPlayerCard.getRank() == secondPlayerCard.getRank()) {
					System.out.println("Do you want to split? Y/N");
					String splitAnswer = sc.next();
					if (splitAnswer.equalsIgnoreCase("Y")) {
						split(playerHand, dealerHand, wagerAmount);
					} else {
						keepChecking = false;
					}
				} else if (firstPlayerCard.getRank() != secondPlayerCard.getRank()) {
					keepChecking = false;
				}
			}
			keepChecking = false;
		}
	}

	private void split(List<Card> playerHand, List<Card> dealerHand, int wagerAmount) {
		List<Card> firstNewHand = new ArrayList<>();
		Card originalFirstSplitCard = playerHand.get(0);
		firstNewHand.add(originalFirstSplitCard);
		Card firstSplitCard = deck.dealCard();
		firstNewHand.add(firstSplitCard);
		System.out.println("First split card hand is currently " + firstNewHand);
		System.out.println("Total value: " + getHandValue(firstNewHand));
		playerTurn(firstNewHand);

		List<Card> secondNewHand = new ArrayList<>();
		Card oringinalSecondSplitCard = playerHand.get(1);
		secondNewHand.add(oringinalSecondSplitCard);
		Card secondSplitCard = deck.dealCard();
		secondNewHand.add(secondSplitCard);
		System.out.println("Second split card hand is currently " + secondNewHand);
		System.out.println("Total value: " + getHandValue(secondNewHand));
		playerTurn(secondNewHand);

		if (getHandValue(firstNewHand) <= 21 || getHandValue(secondNewHand) <= 21) {
			dealerTurn(dealerHand);
			System.out.println("First hand split:");
			determineWinner(firstNewHand, dealerHand, wagerAmount, player);
			System.out.println("Second hand split:");
			determineWinner(secondNewHand, dealerHand, wagerAmount, player);
			askPlayAgain();
		} else {
			askPlayAgain();
		}
	}

	private void blackjack(List<Card> dealerHand, int initialWager) {
		System.out.println(player.getName() + " got BLACKJACK");
		if (getHandValue(dealerHand) == 21) {
			System.out.println("Computer also got blackjack... What are the odds eh");
			System.out.println(player.getName() + "'s current chip count is " + player.getChips());
			askPlayAgain();
		} else {
			player.setChips(player.getChips() + (int) (1.5 * initialWager));
			System.out.println("You win 1.5x!!");
			System.out.println(player.getName() + "'s current chip count is " + player.getChips());
			askPlayAgain();
		}
	}

	private void askPlayAgain() {
		while (player.getChips() > 0) {
			System.out.println("Would you like to play again? Y/N");
			String playAgain = sc.next();
			if (playAgain.equalsIgnoreCase("Y")) {
				runGameAgain();
			} else {
				System.out.println("Bye");
				System.exit(0);
			}
		}
		System.out.println("You have no more chips");
	}

	private void runGameAgain() {
		playGame();
	}

	private void determineWinner(List<Card> playerHand, List<Card> dealerHand, int initialWager, Player player) {
		int finalPlayerCount = getHandValue(playerHand);
		int finalDealerCount = getHandValue(dealerHand);
		int currentChipCount = player.getChips();
		if (finalDealerCount > 21 && finalPlayerCount > 21) {
			System.out.println("No one wins boo");
		} else if (finalDealerCount <= 21 && finalPlayerCount > 21) {
			System.out.println(player.getName() + " loses");
			player.setChips(currentChipCount - initialWager);
		} else if (finalDealerCount > 21 && finalPlayerCount <= 21) {
			System.out.println(player.getName() + " wins");
			player.setChips(currentChipCount + initialWager);
		} else if (finalDealerCount <= 21 && finalPlayerCount <= 21) {
			if (finalDealerCount == finalPlayerCount) {
				System.out.println(player.getName() + " loses");
				player.setChips(currentChipCount - initialWager);
			} else if (finalDealerCount > finalPlayerCount) {
				System.out.println("Computer wins");
				player.setChips(currentChipCount - initialWager);
			} else {
				System.out.println(player.getName() + " wins");
				player.setChips(currentChipCount + initialWager);
			}
		}
		System.out.println(player.getName() + "'s current chip count is " + player.getChips());

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
			} else if (getHandValue(dealerHand) > 21) {
				System.out.println("Dealer goes bust");
				keepPlaying = false;
			} else {
				System.out.println("Computer sticks");
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
					System.out.println(player.getName() + " goes bust");
					keepPlaying = false;
				}
				break;
			case 2:
				System.out.println(player.getName() + " sticks");
				System.out.println(player.getName() + "'s hand value is " + getHandValue(hand));
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
