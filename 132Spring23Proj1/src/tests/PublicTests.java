package tests;

import deckOfCards.*;
import blackjack.*;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;
import org.junit.Test;

public class PublicTests {

	@Test
	public void testDeckConstructorAndDealOneCard() {
		Deck deck = new Deck();
		for (int suitCounter = 0; suitCounter < 4; suitCounter++) {
			for (int valueCounter = 0; valueCounter < 13; valueCounter++) {
				Card card = deck.dealOneCard();
				assertEquals(card.getSuit().ordinal(), suitCounter);
				assertEquals(card.getRank().ordinal(), valueCounter);
			}
		}
	}
	
	/* This test will pass only if an IndexOutOfBoundsException is thrown */
	@Test (expected = IndexOutOfBoundsException.class)
	public void testDeckSize() {
		Deck deck = new Deck();
		for (int i = 0; i < 53; i++) {  // one too many -- should throw exception
			deck.dealOneCard();
		}
	}

	@Test
	public void testDeckShuffle() {
		Deck deck = new Deck();
		Random random = new Random(1234);
		deck.shuffle(random);
		assertEquals(new Card(Rank.KING, Suit.CLUBS), deck.dealOneCard());
		assertEquals(new Card(Rank.TEN, Suit.CLUBS), deck.dealOneCard());
		assertEquals(new Card(Rank.JACK, Suit.SPADES), deck.dealOneCard());
		for (int i = 0; i < 20; i++) {
			deck.dealOneCard();
		}
		assertEquals(new Card(Rank.SIX, Suit.CLUBS), deck.dealOneCard());
		assertEquals(new Card(Rank.FIVE, Suit.CLUBS), deck.dealOneCard());
		for (int i = 0; i < 24; i++) {
			deck.dealOneCard();
		}
		assertEquals(new Card(Rank.EIGHT, Suit.CLUBS), deck.dealOneCard());
		assertEquals(new Card(Rank.JACK, Suit.HEARTS), deck.dealOneCard());
		assertEquals(new Card(Rank.JACK, Suit.CLUBS), deck.dealOneCard());
	}
	
	@Test
	public void testGameBasics() {
		Random random = new Random(3723);
		BlackjackModel game = new BlackjackModel();
		game.createAndShuffleDeck(random);
		game.initialPlayerCards();
		game.initialDealerCards();
		game.playerTakeCard();
		game.dealerTakeCard();
		ArrayList<Card> playerCards = game.getPlayerCards();
		ArrayList<Card> dealerCards = game.getDealerCards();
		assertTrue(playerCards.get(0).equals(new Card(Rank.QUEEN, Suit.HEARTS)));
		assertTrue(playerCards.get(1).equals(new Card(Rank.SIX, Suit.DIAMONDS)));
		assertTrue(playerCards.get(2).equals(new Card(Rank.EIGHT, Suit.HEARTS)));
		assertTrue(dealerCards.get(0).equals(new Card(Rank.THREE, Suit.CLUBS)));
		assertTrue(dealerCards.get(1).equals(new Card(Rank.NINE, Suit.SPADES)));
		assertTrue(dealerCards.get(2).equals(new Card(Rank.FIVE, Suit.CLUBS)));		
	}
	
	@Test
	public void testHands() {
		ArrayList<Card> playerCards = new ArrayList<>();
		playerCards.add(new Card(Rank.QUEEN, Suit.HEARTS));
		playerCards.add(new Card(Rank.ACE, Suit.HEARTS));
		assertTrue(BlackjackModel.possibleHandValues(playerCards).get(0) == 11);
		assertTrue(BlackjackModel.possibleHandValues(playerCards).get(1) == 21);
		assertTrue(BlackjackModel.assessHand(playerCards) == 
				HandAssessment.NATURAL_BLACKJACK);
		playerCards.clear();
		playerCards.add(new Card(Rank.QUEEN, Suit.HEARTS));
		playerCards.add(new Card(Rank.QUEEN, Suit.SPADES));
		playerCards.add(new Card(Rank.QUEEN, Suit.CLUBS));
		assertTrue(BlackjackModel.possibleHandValues(playerCards).get(0) == 30);
		assertTrue(BlackjackModel.assessHand(playerCards) == 
				HandAssessment.BUST);
		playerCards.clear();
		assertTrue(BlackjackModel.assessHand(playerCards) == 
				HandAssessment.INSUFFICIENT_CARDS);
	}
	
	@Test
	public void testGameResults() {
		ArrayList<Card> playerCards = new ArrayList<>();
		ArrayList<Card> dealerCards = new ArrayList<>();
		playerCards.add(new Card(Rank.QUEEN, Suit.HEARTS));
		playerCards.add(new Card(Rank.ACE, Suit.HEARTS));
		dealerCards.add(new Card(Rank.QUEEN, Suit.CLUBS));
		dealerCards.add(new Card(Rank.ACE, Suit.CLUBS));
		BlackjackModel game = new BlackjackModel();
		game.setPlayerCards(playerCards);
		game.setDealerCards(dealerCards);
		assertTrue(game.gameAssessment() == GameResult.PUSH);
		playerCards.clear();
		dealerCards.clear();
		dealerCards.add(new Card(Rank.QUEEN, Suit.CLUBS));
		dealerCards.add(new Card(Rank.ACE, Suit.CLUBS));
		playerCards.add(new Card(Rank.ACE, Suit.HEARTS));
		playerCards.add(new Card(Rank.ACE, Suit.SPADES));
		game.setPlayerCards(playerCards);
		game.setDealerCards(dealerCards);
		assertTrue(game.gameAssessment() == GameResult.PLAYER_LOST);
		playerCards.clear();
		dealerCards.clear();
		playerCards.add(new Card(Rank.QUEEN, Suit.HEARTS));
		playerCards.add(new Card(Rank.ACE, Suit.CLUBS));
		dealerCards.add(new Card(Rank.ACE, Suit.HEARTS));
		dealerCards.add(new Card(Rank.ACE, Suit.SPADES));
		game.setPlayerCards(playerCards);
		game.setDealerCards(dealerCards);
		assertTrue(game.gameAssessment() == GameResult.NATURAL_BLACKJACK);
		playerCards.clear();
		dealerCards.clear();
		playerCards.add(new Card(Rank.QUEEN, Suit.HEARTS));
		playerCards.add(new Card(Rank.KING, Suit.CLUBS));
		dealerCards.add(new Card(Rank.ACE, Suit.HEARTS));
		dealerCards.add(new Card(Rank.ACE, Suit.SPADES));
		game.setPlayerCards(playerCards);
		game.setDealerCards(dealerCards);
		assertTrue(game.gameAssessment() == GameResult.PLAYER_WON);
		playerCards.clear();
		dealerCards.clear();
		playerCards.add(new Card(Rank.TEN, Suit.HEARTS));
		playerCards.add(new Card(Rank.TWO, Suit.HEARTS));
		dealerCards.add(new Card(Rank.SEVEN, Suit.CLUBS));
		dealerCards.add(new Card(Rank.FIVE, Suit.CLUBS));
		game.setPlayerCards(playerCards);
		game.setDealerCards(dealerCards);
		assertTrue(game.gameAssessment() == GameResult.PUSH);
		playerCards.clear();
		dealerCards.clear();
		dealerCards.add(new Card(Rank.ACE, Suit.DIAMONDS));
		dealerCards.add(new Card(Rank.ACE, Suit.CLUBS));
		playerCards.add(new Card(Rank.ACE, Suit.HEARTS));
		playerCards.add(new Card(Rank.ACE, Suit.SPADES));
		game.setPlayerCards(playerCards);
		game.setDealerCards(dealerCards);
		assertTrue(game.gameAssessment() == GameResult.PUSH);
		playerCards.clear();
		dealerCards.clear();
		dealerCards.add(new Card(Rank.ACE, Suit.DIAMONDS));
		dealerCards.add(new Card(Rank.FIVE, Suit.CLUBS));
		playerCards.add(new Card(Rank.JACK, Suit.HEARTS));
		playerCards.add(new Card(Rank.SIX, Suit.SPADES));
		game.setPlayerCards(playerCards);
		game.setDealerCards(dealerCards);
		assertTrue(game.gameAssessment() == GameResult.PUSH);
		playerCards.clear();
		dealerCards.clear();
		dealerCards.add(new Card(Rank.ACE, Suit.DIAMONDS));
		dealerCards.add(new Card(Rank.JACK, Suit.CLUBS));
		playerCards.add(new Card(Rank.JACK, Suit.HEARTS));
		playerCards.add(new Card(Rank.KING, Suit.SPADES));
		playerCards.add(new Card(Rank.ACE, Suit.SPADES));
		game.setPlayerCards(playerCards);
		game.setDealerCards(dealerCards);
		assertTrue(game.gameAssessment() == GameResult.PUSH);
	}

}
