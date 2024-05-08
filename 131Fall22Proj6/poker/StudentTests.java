package poker;

import static org.junit.Assert.*;

import org.junit.Test;

public class StudentTests {

	@Test
	public void testConstructors() {
		Deck a = new Deck();
		// Test length
		assertTrue(a.getNumCards() == 52);
		// Test Copy Constructor
		Deck b = new Deck(a);
		for (int currCard = 0; currCard < a.getNumCards(); currCard++) {
			assertTrue(a.getCardAt(currCard) == b.getCardAt(currCard));
		}
	}
	
	@Test
	public void testGetter() {
		Deck a = new Deck();
		int value = 1;
		for (int currCard = 0; currCard < 13; currCard++) {
			assertTrue(a.getCardAt(currCard).getValue() == value);
			value++;
		}
		assertTrue(a.getNumCards() == 52);
	}
	@Test
	public void testShuffle() {
		Deck a = new Deck();
		Deck b = new Deck();
		a.shuffle();
		int counter = 0;
		for (int currCard = 0; currCard < a.getNumCards() / 2; currCard += 2) {
			assertTrue(a.getCardAt(currCard).getValue() 
					== b.getCardAt(counter).getValue());
			counter++;
		}
		// Testing when the deck has an odd number of cards.
		Deck c = new Deck();
		c.deal(1);
		c.shuffle();
		counter = 1;
		for (int currCard = 0; currCard < 15; currCard += 2) {
			assertTrue(c.getCardAt(currCard).getValue() 
					== b.getCardAt(counter).getValue());
			counter++;
		}
	}
	@Test
	public void testCut() {
		Deck a = new Deck();
		Deck b = new Deck();
		a.cut(15);
		for (int currCard = 0; currCard < 15; currCard++) {
			assertTrue(a.getCardAt(currCard).getValue() == 
					b.getCardAt(currCard + 15).getValue());
		}
	}
	@Test
	public void testDeal() {
		Deck a = new Deck();
		Card[] b = new Card[5];
		b = a.deal(5);
		Deck c = new Deck();
		for (int i = 0; i < 5; i++) {
			assertTrue(c.getCardAt(i).getValue() == b[i].getValue());
		}
	}
	
	@Test
	public void testHandSorters() {
		Card[] hand = {new Card(13,0), new Card(11,0), new Card(9,0),
				new Card(7,0), new Card(4,0)};
		Card[] handSortedValue = {new Card(4,0), new Card(7,0), new Card(9,0),
				new Card(11,0), new Card(13,0)};
		PokerHandEvaluator.sortHandValue(hand);
		for (int i = 0; i < hand.length; i++) {
			assertTrue(hand[i].getValue() == handSortedValue[i].getValue());
		}
		Card[] hand2 = {new Card(1,3), new Card(1,3), new Card(1,2), 
				new Card(1,1), new Card(1,0)};
		Card[] handSortedSuit = {new Card(1,0), new Card(1,1), new Card(1,2),
				new Card(1,3), new Card(1,3)};
		PokerHandEvaluator.sortHandSuit(hand2);
		for (int i = 0; i < hand2.length; i++) {
			assertTrue(hand2[i].getSuit() == handSortedSuit[i].getSuit());
		}
	}
	@Test
	public void testHasPair() {
		Card[] hand = {new Card(13,0), new Card(13,2), new Card(9,0),
				new Card(7,0), new Card(4,0)};
		assertTrue(PokerHandEvaluator.hasPair(hand));
		Card[] hand2 = {new Card(13,0), new Card(11,0), new Card(2,3),
				new Card(7,0), new Card(2,0)};
		assertTrue(PokerHandEvaluator.hasPair(hand2));
		Card[] hand3 = {new Card(13,0), new Card(13,1), new Card(13,2),
				new Card(13,3), new Card(4,0)};
		assertTrue(PokerHandEvaluator.hasPair(hand3));
	}
	@Test
	public void testHasTwoPair() {
		Card[] hand = {new Card(13,0), new Card(13,1), new Card(13,2),
				new Card(13,3), new Card(4,0)};
		assertFalse(PokerHandEvaluator.hasTwoPair(hand));
		Card[] hand2 = {new Card(13,0), new Card(4,0), new Card(4,3),
				new Card(7,0), new Card(7,1)};
		assertTrue(PokerHandEvaluator.hasTwoPair(hand2));
		Card[] hand3 = {new Card(1,0), new Card(11,0), new Card(2,3),
				new Card(2,0), new Card(1,1)};
		assertTrue(PokerHandEvaluator.hasTwoPair(hand3));
	}
	@Test
	public void testHasThreeOfAKind() {
		Card[] hand = {new Card(13,0), new Card(13,1), new Card(13,3),
				new Card(13,2), new Card(2,0)};
		assertTrue(PokerHandEvaluator.hasThreeOfAKind(hand));
		Card[] hand2 = {new Card(1,0), new Card(11,0), new Card(2,3),
				new Card(1,3), new Card(1,1)};
		assertTrue(PokerHandEvaluator.hasThreeOfAKind(hand2));
	}
	@Test
	public void testHasStraight() {
		Card[] hand = {new Card(13,0), new Card(12,0), new Card(11,3),
				new Card(10,0), new Card(9,2)};
		assertTrue(PokerHandEvaluator.hasStraight(hand));
		Card[] hand2 = {new Card(5,0), new Card(4,0), new Card(3,3),
				new Card(2,0), new Card(1,0)};
		assertTrue(PokerHandEvaluator.hasStraight(hand2));
	}
	@Test
	public void testHasFlush() {
		Card[] hand = {new Card(13,0), new Card(11,0), new Card(2,0),
				new Card(7,0), new Card(2,0)};
		assertTrue(PokerHandEvaluator.hasFlush(hand));
		Card[] hand2 = {new Card(13,3), new Card(11,3), new Card(2,3),
				new Card(1,3), new Card(2,3)};
		assertTrue(PokerHandEvaluator.hasFlush(hand2));
	}
	@Test
	public void testHasFullHouse() {
		Card[] hand = {new Card(13,0), new Card(11,0), new Card(11,3),
				new Card(11,1), new Card(13,2)};
		assertTrue(PokerHandEvaluator.hasFullHouse(hand));
		Card[] hand2 = {new Card(13,0), new Card(13,1), new Card(13,2),
				new Card(13,3), new Card(2,0)};
		assertFalse(PokerHandEvaluator.hasFullHouse(hand2));
		Card[] hand3 = {new Card(7,1), new Card(7,0), new Card(2,3),
				new Card(7,3), new Card(2,0)};
		assertTrue(PokerHandEvaluator.hasFullHouse(hand3));
	}
	@Test
	public void testHasFourOfAKind() {
		Card[] hand = {new Card(13,0), new Card(13,1), new Card(13,2),
				new Card(13,3), new Card(2,0)};
		assertTrue(PokerHandEvaluator.hasFourOfAKind(hand));
		Card[] hand2 = {new Card(13,0), new Card(2,1), new Card(2,2),
				new Card(2,3), new Card(2,0)};
		assertTrue(PokerHandEvaluator.hasFourOfAKind(hand2));
	}
	@Test
	public void testHasStraightFlush() {
		Card[] hand = {new Card(13,0), new Card(12,0), new Card(11,0),
				new Card(10,0), new Card(9,0)};
		assertTrue(PokerHandEvaluator.hasStraightFlush(hand));
		Card[] hand2 = {new Card(6,0), new Card(5,0), new Card(4,0),
				new Card(3,0), new Card(2,0)};
		assertTrue(PokerHandEvaluator.hasStraightFlush(hand2));
	}
}
