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

	Scanner sc = new Scanner(System.in);
	private Deck deck;
	private Dealer dealer = new Dealer();
	private Player player = new Player("", 1000, 0);
	private int deckSize;

	public void run() {
		intro();
		runRepeat();
	}

	public void runRepeat() {
		wager();
		dealCards();
		checkConditions();
		playerTurn();
		dealerTurn();
		determineWinner();
		askPlayAgain();
	}

	private void intro() {
		System.out.println("Welcome to Blackjack");
		System.out.println("21 WINNER WINNER CHICKEN DINNER");
		System.out.println("What is your name");
		String name = sc.next();
		player.setName(name);
		deck = new Deck();
		deck.setCardCountingValue(0);
		System.out.println("Randomising 1-6 decks...SHUFLLING");
		System.out.println("You are playing with " + (deck.checkDeckSize() / 52) + " decks");
		deckSize = deck.checkDeckSize();
		System.out.println("Hi-Lo card counting strategy in play. Don't get caught");
		deck.shuffle();
	}

	private void wager() {
		System.out.println("Current chip count: " + player.getChips());
		System.out.println("How much do you want to wager?");
		player.setWagerAmount(sc.nextInt());
		while (player.getWagerAmount() < 0 || player.getWagerAmount() > player.getChips()) {
			System.out.println("Please enter amount between 0 and " + player.getChips());
			player.setWagerAmount(sc.nextInt());
		}
	}

	private void dealCards() {
		player.createHand();
		Card firstPlayerCard = deck.dealCard();
		player.addCard((firstPlayerCard));
		Card secondPlayerCard = deck.dealCard();
		player.addCard(secondPlayerCard);
		dealer.createHand();
		dealer.addCard(deck.dealCard());
		System.out.println(player.getName() + "'s hand: " + player.getPlayerHand());
		System.out.println("Current hand value: " + getHandValue(player.getPlayerHand()));
		System.out.println("Dealer's first card is " + dealer.getDealerHand().get(0) + "\n");
		dealer.addCard(deck.dealCard());
	}

	private void checkConditions() {
		boolean keepPlaying = true;
		while (keepPlaying) {
			if (dealer.getDealerHand().get(0).getValue() == 11) {
				insurance();
				if (getHandValue(player.getPlayerHand()) == 21) {
					blackjack();
				}
				keepPlaying = false;
			} else if (getHandValue(player.getPlayerHand()) == 21 || getHandValue(dealer.getDealerHand()) == 21) {
				blackjack();
				keepPlaying = false;
			}
			keepPlaying = false;
		}
		boolean keepChecking = true;
		while (keepChecking) {
			if (player.getPlayerHand().get(0).getValue() == player.getPlayerHand().get(1).getValue()) {
				if (player.getPlayerHand().get(0).getRank() == player.getPlayerHand().get(1).getRank()) {
					System.out.println("Do you want to split? Y/N");
					String splitAnswer = sc.next();
					if (splitAnswer.equalsIgnoreCase("Y")) {
						split();
					} else {
						keepChecking = false;
					}
				} else if (player.getPlayerHand().get(0).getRank() != player.getPlayerHand().get(1).getRank()) {
					keepChecking = false;
				}
			}
			keepChecking = false;
		}
		doudbleDown();
	}

	private void doudbleDown() {
		if (player.getPlayerHand().get(0).getValue() < 10 && player.getPlayerHand().get(1).getValue() < 10) {
			System.out.println(player.getName() + "'s hand contains two number cards");
			System.out.println("Do you want to double down? Y/N?");
			String doubleDownAnser = sc.next();
			if (doubleDownAnser.equalsIgnoreCase("Y")) {
				System.out.println("You are now wagering " + player.getWagerAmount() * 2 + " for this hand");
				player.setWagerAmount(player.getWagerAmount() * 2);
				Card d = deck.dealCard();
				System.out.println(d);
				player.getPlayerHand().add(d);
				getHandValue(player.getPlayerHand());
				System.out.println("Current hand value: " + getHandValue(player.getPlayerHand()));
				dealerTurn();
				determineWinner();
				askPlayAgain();
			}
		}
	}

	private void insurance() {
		int insuranceAmount = player.getWagerAmount() / 2;
		System.out.println("Do you want to buy insurance? Y/N. Go on, may as well");
		String insurance = sc.next();
		if (insurance.equalsIgnoreCase("Y")) {
			System.out.println(player.getName() + " puts down an extra " + insuranceAmount + " as insurance");
			if (dealer.getDealerHand().get(1).getValue() == 10) {
				System.out.println("Well done you beat the odds. Hurrah");
				System.out.println("Continue playing the main hand to win more!!");
				player.setChips(player.getChips() + player.getWagerAmount());
			} else {
				System.out.println("NO LUCK. Dealer != BLACKJACK");
				System.out.println("Insurance didn't work, let's continue");
				player.setChips(player.getChips() - insuranceAmount);
				System.out.println("Current chip count is: " + player.getChips());
			}
		}
	}

	private void blackjack() {
		if (getHandValue(player.getPlayerHand()) == 21 && getHandValue(dealer.getDealerHand()) == 21) {
			System.out.println("Computer also got blackjack... What are the odds eh");
			playerLoses();
			askPlayAgain();
		} else if (dealer.getDealerHand().get(0).getValue() == 11 && getHandValue(dealer.getDealerHand()) == 21) {
			System.out.println("Computer got BLACKJACK");
			playerLoses();
			askPlayAgain();
		} else if (getHandValue(player.getPlayerHand()) == 21) {
			player.setChips(player.getChips() + (int) (1.5 * player.getWagerAmount()));
			System.out.println(player.getName() + " got BLACKJACK");
			System.out.println("You win 1.5x!!");
			System.out.println(player.getName() + "'s current chip count is " + player.getChips());
			increaseWinStreak();
			askPlayAgain();
		}
	}

	private void split() {
		List<Card> firstNewHand = new ArrayList<>();
		Card originalFirstSplitCard = player.getPlayerHand().get(0);
		firstNewHand.add(originalFirstSplitCard);
		Card firstSplitCard = deck.dealCard();
		firstNewHand.add(firstSplitCard);
		System.out.println("\nFirst split card hand is currently " + firstNewHand);
		System.out.println("Total value: " + getHandValue(firstNewHand));
		playerTurnSplit(firstNewHand);

		List<Card> secondNewHand = new ArrayList<>();
		Card oringinalSecondSplitCard = player.getPlayerHand().get(1);
		secondNewHand.add(oringinalSecondSplitCard);
		Card secondSplitCard = deck.dealCard();
		secondNewHand.add(secondSplitCard);
		System.out.println("\nSecond split card hand is currently " + secondNewHand);
		System.out.println("Total value: " + getHandValue(secondNewHand));
		playerTurnSplit(secondNewHand);

		if (getHandValue(firstNewHand) <= 21 || getHandValue(secondNewHand) <= 21) {
			dealerTurn();

			int finalDealerCount = getHandValue(dealer.getDealerHand());

			System.out.println("First hand split:");
			int finalFirstHandPlayerCount = getHandValue(firstNewHand);
			if (finalDealerCount > 21 && finalFirstHandPlayerCount > 21) {
				playerLoses();
			} else if (finalDealerCount <= 21 && finalFirstHandPlayerCount > 21) {
				playerLoses();
			} else if (finalDealerCount > 21 && finalFirstHandPlayerCount <= 21) {
				playerWins();
			} else if (finalDealerCount <= 21 && finalFirstHandPlayerCount <= 21) {
				if (finalDealerCount == finalFirstHandPlayerCount) {
					playerLoses();
				} else if (finalDealerCount > finalFirstHandPlayerCount) {
					playerLoses();
				} else {
					playerWins();
				}
			}

			System.out.println("Second hand split:");
			int finalSecondHandPlayerCount = getHandValue(secondNewHand);
			if (finalDealerCount > 21 && finalSecondHandPlayerCount > 21) {
				playerLoses();
			} else if (finalDealerCount <= 21 && finalSecondHandPlayerCount > 21) {
				playerLoses();
			} else if (finalDealerCount > 21 && finalSecondHandPlayerCount <= 21) {
				playerWins();
			} else if (finalDealerCount <= 21 && finalSecondHandPlayerCount <= 21) {
				if (finalDealerCount == finalSecondHandPlayerCount) {
					playerLoses();
				} else if (finalDealerCount > finalSecondHandPlayerCount) {
					playerLoses();
				} else {
					playerWins();
				}
			}

			else if (getHandValue(firstNewHand) > 21 || getHandValue(secondNewHand) > 21) {
				System.out.println("First hand split:");
				playerLoses();
				System.out.println("Second hand split:");
				playerLoses();
			}
		}

		System.out.println(player.getName() + "'s current chip count is " + player.getChips());
		double runningCount = 0;
		for (Card card : firstNewHand) {
			runningCount += card.getCardCountingValue();
		}
		for (Card card : secondNewHand) {
			runningCount += card.getCardCountingValue();
		}
		runningCount = deck.getCardCountingValue() + runningCount / ((deck.checkDeckSize() / 52));
		deck.setCardCountingValue(runningCount);
		runningCardCounter();
		askPlayAgain();
	}

	private void playerTurnSplit(List<Card> hand) {
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
					System.out.println(player.getName() + " loses");
					System.out.println("\nCurrent win streak: 0");
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

	private void playerTurn() {
		boolean keepPlaying = true;
		while (keepPlaying) {
			System.out.println("What do you want to do?\n1.Hit\n2.Stick");
			int hitOrStick = sc.nextInt();
			switch (hitOrStick) {
			case 1:
				Card d = deck.dealCard();
				System.out.println(d);
				player.getPlayerHand().add(d);
				getHandValue(player.getPlayerHand());
				System.out.println("Current hand value: " + getHandValue(player.getPlayerHand()));
				if (getHandValue(player.getPlayerHand()) > 21) {
					playerLoses();
					keepPlaying = false;
					askPlayAgain();
				}
				break;
			case 2:
				System.out.println(player.getName() + " sticks");
				System.out.println(player.getName() + "'s hand value is " + getHandValue(player.getPlayerHand()));
				keepPlaying = false;
				break;
			}
		}
	}

	private void dealerTurn() {
		boolean keepPlaying = true;
		System.out.println("\nDealer's hand: " + dealer.getDealerHand());
		System.out.println("Dealer's total is: " + getHandValue(dealer.getDealerHand()));

		while (keepPlaying) {
			if (getHandValue(dealer.getDealerHand()) == 17) {
				for (Card card : dealer.getDealerHand()) {
					if (card.getValue() == 11 && getHandValueAceDealer(dealer.getDealerHand()) == 17) {
						dealDealerCard();
						if (getHandValueAceDealer(dealer.getDealerHand()) <= 21) {
							System.out.println("Computer sticks");
							keepPlaying = false;
						} else if (getHandValueAceDealer(dealer.getDealerHand()) > 21) {
							int dealerHandValue = getHandValueAceDealer(dealer.getDealerHand());
							dealerHandValue -=11;
							do {
								dealDealerCard();
							} while (dealerHandValue < 17);
							if (dealerHandValue >= 17 && dealerHandValue < 21) {
								System.out.println("Computer sticks");
								keepPlaying = false;
							} else if (dealerHandValue > 21)
								System.out.println("Dealer goes bust");
							keepPlaying = false;
						}
					} else {
						System.out.println("Computer sticks");
						keepPlaying = false;
					}
				}
			} else if (getHandValue(dealer.getDealerHand()) < 17) {
				dealDealerCard();
				if (getHandValue(dealer.getDealerHand()) >= 17 && getHandValue(dealer.getDealerHand()) <= 21) {
					System.out.println("Computer sticks");
					keepPlaying = false;
				} else if (getHandValue(dealer.getDealerHand()) > 21) {
					System.out.println("Dealer goes bust");
					keepPlaying = false;
				}
			} else {
				System.out.println("Computer sticks");
				keepPlaying = false;
			}
		}
	}

	private void dealDealerCard() {
		Card dealerCard;
		dealerCard = deck.dealCard();
		dealer.addCard(dealerCard);
		System.out.println("Dealer gets dealt " + dealerCard);
		System.out.println("Dealer's total is: " + getHandValue(dealer.getDealerHand()));
	}

	private void determineWinner() {
		int finalPlayerCount = getHandValue(player.getPlayerHand());
		int finalDealerCount = getHandValue(dealer.getDealerHand());
		if (finalDealerCount > 21 && finalPlayerCount > 21) {
			playerLoses();
		} else if (finalDealerCount <= 21 && finalPlayerCount > 21) {
			playerLoses();
		} else if (finalDealerCount > 21 && finalPlayerCount <= 21) {
			playerWins();
		} else if (finalDealerCount <= 21 && finalPlayerCount <= 21) {
			if (finalDealerCount == finalPlayerCount) {
				playerLoses();
			} else if (finalDealerCount > finalPlayerCount) {
				playerLoses();
			} else {
				playerWins();
			}
		}
		System.out.println(player.getName() + "'s current chip count is " + player.getChips());
	}

	private void playerWins() {
		System.out.println(player.getName() + " wins");
		player.setChips(player.getChips() + player.getWagerAmount());
		increaseWinStreak();
	}

	private void increaseWinStreak() {
		int countWinStreak = player.getWinStreak();
		countWinStreak++;
		player.setWinStreak(countWinStreak);
		System.out.println("\nCurrent win streak is: " + countWinStreak);
	}

	private void playerLoses() {
		System.out.println(player.getName() + " loses");
		player.setChips(player.getChips() - player.getWagerAmount());
		System.out.println("\nCurrent win streak: 0");
	}

	private void askPlayAgain() {
		while (player.getChips() > 0) {
			System.out.println("\nWould you like to play again? Y/N");
			String playAgain = sc.next();
			if (playAgain.equalsIgnoreCase("Y")) {
				deck.setCardCountingValue(runningCardCounter());
				runGameAgain();
			} else {
				System.out.println("Bye");
				System.exit(0);
			}
		}
		System.out.println(player.getName() + " grabs all the chips and runs out laughing. Who's the loser now.");
		System.exit(0);
	}

	private void runGameAgain() {
		if (deck.checkDeckSize() < (deckSize * 0.5)) {
			deck.clearDeck();
			deck = new Deck();
			deck.setCardCountingValue(0);
			System.out.println("Randomising 1-6 decks...shuffle...shuffle...shuffle");
			System.out.println("You are playing with " + (deck.checkDeckSize() / 52) + " decks");
		}
		runRepeat();
	}

	public int getHandValue(List<Card> hand) {
		boolean isAcePresent = false;
		int totalValue = 0;
		for (Card card : hand) {
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

	private int getHandValueAceDealer(List<Card> dealerHand) {
		int totalValue = 0;
		for (Card card : dealerHand) {
			totalValue += card.getValue();
		}
		return totalValue;
	}

	private double runningCardCounter() {
		double runningCount = 0;
		for (Card card : player.getPlayerHand()) {
			runningCount += card.getCardCountingValue();
		}
		for (Card card : dealer.getDealerHand()) {
			runningCount += card.getCardCountingValue();
		}
		runningCount = deck.getCardCountingValue() + (runningCount / ((deck.checkDeckSize() / 52)));
		System.out.println("True count: " + runningCount);
		return runningCount;
	}
}
