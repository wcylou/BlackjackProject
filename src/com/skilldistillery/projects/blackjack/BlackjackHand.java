package com.skilldistillery.projects.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.cards.Card;
import com.skilldistillery.cards.Deck;
import com.skilldistillery.cards.Hand;
import com.skilldistillery.cards.Player;
import com.skilldistillery.cards.Rank;
import com.skilldistillery.cards.Suit;

public class BlackjackHand extends Hand {

	private Player player = new Player("", 1000, 0);
	Scanner sc = new Scanner(System.in);
	private Deck deck;

	public void run() {
		System.out.println("Welcome to Blackjack. Aim of the game is 21 WINNER WINNER CHICKEN DINNER");
		System.out.println("What is your name");
		String name = sc.next();
		player.setName(name);
		deck = new Deck();
		playGame();
	}

	private void playGame() {
		List<Card> playerHand = new ArrayList<>();
		List<Card> dealerHand = new ArrayList<>();
		System.out.println("Current chip count: " + player.getChips());
		System.out.println("How much do you want to wager?");
		int wagerAmount = sc.nextInt();
		while (wagerAmount < 0 || wagerAmount > player.getChips()) {
			System.out.println("Please enter amount between 0 and " + player.getChips());
			wagerAmount = sc.nextInt();
		}
		initialDeal(playerHand, dealerHand, wagerAmount);
		playerTurn(playerHand);
		if (getHandValue(playerHand) <= 21) {
			dealerTurn(dealerHand);
			System.out.println("Current card count " + deck.checkDeckSize());
			determineWinner(playerHand, dealerHand, wagerAmount, player);
			System.out.println("Current card count " + deck.checkDeckSize());
			askPlayAgain();
		} else {
			askPlayAgain();
		}
	}

	private void initialDeal(List<Card> playerHand, List<Card> dealerHand, int wagerAmount) {
		deck.shuffle();
		System.out.println(deck.checkDeckSize());
		Card firstPlayerCard = deck.dealCard();
		playerHand.add(firstPlayerCard);
		Card secondPlayerCard = deck.dealCard();
		playerHand.add(secondPlayerCard);
		dealerHand.add(deck.dealCard());
		dealerHand.add(deck.dealCard());
		System.out.println(player.getName() + "'s hand: " + playerHand);
		System.out.println("Current hand value: " + getHandValue(playerHand));
		System.out.println("Dealer's first card is " + dealerHand.get(0));
		if (dealerHand.get(0).getValue() == 11) {
			insurance(dealerHand, wagerAmount);
		}

		if (getHandValue(playerHand) == 21) {
			blackjack(dealerHand, wagerAmount);
		}
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
		doudbleDown(playerHand, wagerAmount);
	}

	private void doudbleDown(List<Card> playerHand, int wagerAmount) {
		if (playerHand.get(0).getValue() < 10 && playerHand.get(1).getValue() < 10) {
			System.out.println("Do you want to double down? Y/N?");
			String doubleDownAnser = sc.next();
			if (doubleDownAnser.equalsIgnoreCase("Y")) {
				System.out.println("You are now wagering " + wagerAmount * 2 + " for this hand");
				wagerAmount = wagerAmount * 2;

			}
		}
	}

	private void insurance(List<Card> dealerHand, int wagerAmount) {
		int insuranceAmount = wagerAmount / 2;
		System.out.println("Do you want to buy insurance? Y/N. Go on, may as well");
		String insurance = sc.next();
		if (insurance.equalsIgnoreCase("Y")) {
			System.out.println(player.getName() + " puts down an extra " + insuranceAmount + " as insurance");
			if (dealerHand.get(1).getValue() == 10) {
				System.out.println("Well done you beat the odds. Hurrah");
				System.out.println("Current win streak: 0");
				System.out.println(player.getName() + "'s current chip count is " + player.getChips());
			} else {
				System.out.println("Dealer's second card is not enough for blackjack");
				System.out.println("Insurance didn't work, let's continue");
				player.setChips(player.getChips() - insuranceAmount);
				System.out.println("Current chip count is: " + player.getChips());
			}
		}
	}

	private void blackjack(List<Card> dealerHand, int initialWager) {
		System.out.println(player.getName() + " got BLACKJACK");
		if (getHandValue(dealerHand) == 21) {
			System.out.println("Computer also got blackjack... What are the odds eh");
			System.out.println(player.getName() + "'s current chip count is " + player.getChips());
			System.out.println("Current win streak: 0");
			askPlayAgain();
		} else {
			player.setChips(player.getChips() + (int) (1.5 * initialWager));
			System.out.println("You win 1.5x!!");
			System.out.println(player.getName() + "'s current chip count is " + player.getChips());
			increaseWinStreak();
			askPlayAgain();
		}
	}

	private void increaseWinStreak() {
		int countWinStreak = player.getWinStreak();
		countWinStreak++;
		player.setWinStreak(countWinStreak);
		System.out.println("Current win streak is: " + countWinStreak);
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
				getHandValue(hand);
				System.out.println("Current hand value: " + getHandValue(hand));
				if (getHandValue(hand) > 21) {
					System.out.println(player.getName() + " goes bust");
					System.out.println("Current win streak: 0");
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

	private void dealerTurn(List<Card> dealerHand) {
		boolean keepPlaying = true;
		int dealerHandValue = getHandValueDealer(dealerHand);
		System.out.println("Dealer's hand: " + dealerHand);
		System.out.println("Dealer's total is: " + getHandValueDealer(dealerHand));

		while (keepPlaying) {
			if (getHandValue() == 17) {
				for (Card card : dealerHand) {
					if (card.getValue() == 11 && dealerHandValue == 17) {
						dealDealerCard(dealerHand);
						if (dealerHandValue <= 21) {
							System.out.println("Computer sticks");
							keepPlaying = false;
						} else if (dealerHandValue > 21) {
							dealerHandValue -= 11;
							if (dealerHandValue < 17) {
								dealDealerCard(dealerHand);
								if (dealerHandValue >= 17 && dealerHandValue < 21) {
									System.out.println("Computer sticks");
									keepPlaying = false;
								}
							}
						}
						System.out.println("Dealer goes bust");
						keepPlaying = false;
					}
				}
				System.out.println(dealerHandValue);
			} else if (dealerHandValue < 17) {
				dealDealerCard(dealerHand);
				if (dealerHandValue < 21) {
					keepPlaying = false;
				}
			} else if (dealerHandValue > 21) {
				System.out.println("Dealer goes bust");
				keepPlaying = false;
			} else {
				System.out.println("Computer sticks");
				keepPlaying = false;
			}
		}
	}

	private void dealDealerCard(List <Card> dealerHand) {
		Card dealerCard;
		dealerCard = deck.dealCard();
		dealerHand.add(dealerCard);
		System.out.println("Dealer gets dealt " + dealerCard);
		System.out.println("Dealer's total is: " + getHandValueDealer(dealerHand));
	}

	private void determineWinner(List<Card> playerHand, List<Card> dealerHand, int wagerAmount, Player player) {
		int finalPlayerCount = getHandValue(playerHand);
		int finalDealerCount = getHandValue(dealerHand);
		int currentChipCount = player.getChips();
		if (finalDealerCount > 21 && finalPlayerCount > 21) {
			System.out.println("No one wins boo");
			System.out.println("Current win streak: 0");
		} else if (finalDealerCount <= 21 && finalPlayerCount > 21) {
			System.out.println(player.getName() + " loses");
			player.setChips(currentChipCount - wagerAmount);
			System.out.println("Current win streak: 0");
		} else if (finalDealerCount > 21 && finalPlayerCount <= 21) {
			System.out.println(player.getName() + " wins");
			player.setChips(currentChipCount + wagerAmount);
			increaseWinStreak();
		} else if (finalDealerCount <= 21 && finalPlayerCount <= 21) {
			if (finalDealerCount == finalPlayerCount) {
				System.out.println(player.getName() + " loses");
				System.out.println("Current win streak: 0");
			} else if (finalDealerCount > finalPlayerCount) {
				System.out.println("Computer wins");
				player.setChips(currentChipCount - wagerAmount);
				System.out.println("Current win streak: 0");
			} else {
				System.out.println(player.getName() + " wins");
				increaseWinStreak();
				player.setChips(currentChipCount + wagerAmount);
			}
		}
		System.out.println(player.getName() + "'s current chip count is " + player.getChips());
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
		if (deck.checkDeckSize() < 18) {
			deck.clearDeck();
			deck = new Deck();
		}
		playGame();
	}

	private int getHandValue(List<Card> playerHand) {
		boolean isAcePresent = false;
		int totalValue = 0;
		for (Card card : playerHand) {
			if (card.getValue() == 11) {
				isAcePresent = true;
			}
			totalValue += card.getValue();
		}
		if (totalValue > 21 && isAcePresent) {
			totalValue -= 10;
		}
		return totalValue;
	}
	
	private int getHandValueDealer(List<Card> dealerHand) {
		int totalValue = 0;
		for (Card card : dealerHand) {
			totalValue += card.getValue();
		}
		return totalValue;
	}
}
