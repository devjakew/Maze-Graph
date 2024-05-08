package blackjack;

import java.util.ArrayList;
import java.util.Random;

import deckOfCards.*;

public class BlackjackModel {
	
	
	private ArrayList<Card> dealerCards = new ArrayList<>();
	private ArrayList<Card> playerCards = new ArrayList<>();
	private Deck deck;
	
	// returns a copy of the dealer's cards
	public ArrayList<Card> getDealerCards() {
		ArrayList<Card> dCopy = new ArrayList<>();
		for (Card c : dealerCards) {
			dCopy.add(new Card(c.getRank(), c.getSuit()));
		}
		return dCopy;
	}
	
	// returns a copy of the player's cards
	public ArrayList<Card> getPlayerCards() {
		ArrayList<Card> dCopy = new ArrayList<>();
		for (Card c : playerCards) {
			dCopy.add(new Card(c.getRank(), c.getSuit()));
		}
		return dCopy;
	}
	
	// sets the dealer's cards to a copy of the parameter
	public void setDealerCards(ArrayList<Card> cards) {
		if (!(dealerCards.isEmpty())) {
			dealerCards.clear();
		}
		for (Card c : cards) {
			dealerCards.add(new Card(c.getRank(), c.getSuit()));
		}
	}
	
	// sets the player's cards to a copy of the parameter
	public void setPlayerCards(ArrayList<Card> cards) {
		if (!(playerCards.isEmpty())) {
			playerCards.clear();
		}
		for (Card c : cards) {
			playerCards.add(new Card(c.getRank(), c.getSuit()));
		}
	}
	
	// Creates a new deck and shuffles it
	public void createAndShuffleDeck(Random random) {
		deck = new Deck();
		deck.shuffle(random);
	}
	// Creates a list of cards for the Dealer and deals them two cards
	public void initialDealerCards() {
		dealerCards = new ArrayList<>();
		dealerCards.add(deck.dealOneCard());
		dealerCards.add(deck.dealOneCard());
	}
	
	// Creates a list of cards for the player and deals them two cards
	public void initialPlayerCards() {
		playerCards = new ArrayList<>();
		playerCards.add(deck.dealOneCard());
		playerCards.add(deck.dealOneCard());
	}
	
	// Deals the player one card, only after initialPlayerCards has been called
	public void playerTakeCard() {
		playerCards.add(deck.dealOneCard());
	}
	
	// Deals the dealer one card, only after initialDealerCards has been called
	public void dealerTakeCard() {
		dealerCards.add(deck.dealOneCard());
	}
	
	// Calculates the value of the hand
	public static ArrayList<Integer> possibleHandValues(ArrayList<Card> hand) {
		ArrayList<Integer> totalValues = new ArrayList<>();
		Integer totalValueOne = 0;
		Integer totalValueTwo = 0;
		boolean ace = false;
		for (Card c : hand) {
			if (c.getRank().getValue() == Rank.ACE.getValue()) {
				ace = true;
			}
			totalValueOne += c.getRank().getValue();
		}
		if (totalValueOne <= 11 && ace == true) {
			totalValueTwo = totalValueOne + 10;
		}
		totalValues.add(totalValueOne);
		if (totalValueTwo != 0) {
			totalValues.add(totalValueTwo);
		}
		return totalValues;
	}
	
	// Assesses the hand to see which hand it makes
	public static HandAssessment assessHand(ArrayList<Card> hand) {
		if (hand.size() == 2 && possibleHandValues(hand).size() == 2 
				&& possibleHandValues(hand).get(1) == 21) {
			return HandAssessment.NATURAL_BLACKJACK;
		}
		if (possibleHandValues(hand).get(0) > 21) {
			return HandAssessment.BUST;
		}
		if (possibleHandValues(hand).get(0) <= 21 && hand.size() >= 2) {
			return HandAssessment.NORMAL;
		}
		return HandAssessment.INSUFFICIENT_CARDS;
	}

	// Assesses the outcome of the round
	public GameResult gameAssessment() {

		// Previous attempt at this method that did not work!
		
		if (assessHand(playerCards) == HandAssessment.BUST) {
			return GameResult.PLAYER_LOST;
		}
		if (assessHand(playerCards) == HandAssessment.NATURAL_BLACKJACK
				&& assessHand(dealerCards) == HandAssessment.NATURAL_BLACKJACK){
			return GameResult.PUSH;
		}
		if (assessHand(playerCards) == HandAssessment.NATURAL_BLACKJACK
				&& assessHand(dealerCards) != HandAssessment.NATURAL_BLACKJACK){
			return GameResult.NATURAL_BLACKJACK;
		}
		if (assessHand(playerCards) != HandAssessment.NATURAL_BLACKJACK
				&& assessHand(dealerCards) == HandAssessment.NATURAL_BLACKJACK){
			if (possibleHandValues(playerCards).size() == 2) {
				if (possibleHandValues(playerCards).get(1) == 21) {
					return GameResult.PUSH;
				} else {
					return GameResult.PLAYER_LOST;
				}
			}
			if (possibleHandValues(playerCards).size() == 1) {
				if (possibleHandValues(playerCards).get(0) == 21) {
					return GameResult.PUSH;
				} else {
					return GameResult.PLAYER_LOST;
				}
			}
		}
		if (assessHand(dealerCards) == HandAssessment.BUST
				&& assessHand(playerCards) != HandAssessment.BUST) {
			return GameResult.PLAYER_WON;
		}
		if (assessHand(playerCards) == HandAssessment.NORMAL
				&& assessHand(dealerCards) == HandAssessment.NORMAL) {
			// playerCards size 2
			if (possibleHandValues(playerCards).size() == 2) {
				if (possibleHandValues(dealerCards).size() == 2) {
					if (possibleHandValues(playerCards).get(1) >
						possibleHandValues(dealerCards).get(1)) {
						return GameResult.PLAYER_WON;
					} else if (possibleHandValues(playerCards).get(1) <
						possibleHandValues(dealerCards).get(1)) {
						return GameResult.PLAYER_LOST;
					}
				}
				if (possibleHandValues(dealerCards).size() == 1) {
					if (possibleHandValues(playerCards).get(1) >
						possibleHandValues(dealerCards).get(0)) {
						return GameResult.PLAYER_WON;
					} else if (possibleHandValues(playerCards).get(1) <
						possibleHandValues(dealerCards).get(0)) {
						return GameResult.PLAYER_LOST;
					}
				}
			}
			// playerCards size 1
			if (possibleHandValues(playerCards).size() == 1) {
				if (possibleHandValues(dealerCards).size() == 2) {
					if (possibleHandValues(playerCards).get(0) >
						possibleHandValues(dealerCards).get(1)) {
						return GameResult.PLAYER_WON;
					} else if (possibleHandValues(playerCards).get(0) <
						possibleHandValues(dealerCards).get(1)) {
						return GameResult.PLAYER_LOST;
					}
				}
				if (possibleHandValues(dealerCards).size() == 1) {
					if (possibleHandValues(playerCards).get(0) >
						possibleHandValues(dealerCards).get(0)) {
						return GameResult.PLAYER_WON;
					} else if (possibleHandValues(playerCards).get(0) <
						possibleHandValues(dealerCards).get(0)) {
						return GameResult.PLAYER_LOST;
					}
				}
			}
		}
		return GameResult.PUSH;
		
		
		
		
		
		
		
		
		
		
	}
	
	// Evaluates what the dealer will do on their turn
	public boolean dealerShouldTakeCard() {
		if (possibleHandValues(dealerCards).size() == 2) {
			if (possibleHandValues(dealerCards).get(1) >= 18) {
				return false;
			} else {
				return true;
			}
		} else {
			if (possibleHandValues(dealerCards).get(0) >= 17) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	
	
}
