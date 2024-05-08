package poker;
public class PokerHandEvaluator {
	
	// Sorts the values of one's poker hand (5 cards) from smallest
	// to largest.
	public static void sortHandValue(Card[] cards) {
		Card temp;
		while (!(cards[0].getValue() <= cards[1].getValue() 
				&& cards[1].getValue() <= cards[2].getValue()
				&& cards[2].getValue() <= cards[3].getValue()
				&& cards[3].getValue() <= cards[4].getValue())) {
			for (int i = 0; i < cards.length - 1; i++) {
				if (cards[i].getValue() > cards[i + 1].getValue()) {
					temp = cards[i];
					cards[i] = cards[i+1];
					cards[i + 1] = temp;
				}
			}
		}
	}
	// Sorts one's hand (5 cards) based on suit, keeping suits together in the
	// order of spades, hearts, clubs, then diamonds.
	public static void sortHandSuit(Card[] cards) {
		Card temp;
		while (!(cards[0].getSuit() <= cards[1].getSuit() 
				&& cards[1].getSuit() <= cards[2].getSuit()
				&& cards[2].getSuit() <= cards[3].getSuit()
				&& cards[3].getSuit() <= cards[4].getSuit())) {
			for (int i = 0; i < cards.length - 1; i++) {
				if (cards[i].getSuit() > cards[i + 1].getSuit()) {
					temp = cards[i];
					cards[i] = cards[i+1];
					cards[i + 1] = temp;
				}
			}
		}
	}
	// Checks to see whether two cards in the hand shares a value
	public static boolean hasPair(Card[] cards) {
		int pair = 0;
		sortHandValue(cards);
		for (int i = 0; i < cards.length - 1; i++) {
			if (cards[i].getValue() == cards[i + 1].getValue()) {
				pair++;
			}
		}
		if (pair > 0) {
			return true;
		} else {
			return false;
		}
	}
	// Checks to see whether two pairs of cards share a value different from
	// each pair
	public static boolean hasTwoPair(Card[] cards) {
		int twoPair = 0;
		int pairValue = -1;
		sortHandValue(cards);
		if (hasPair(cards)) {
			for (int i = 0; i < cards.length - 1; i++) {
				if (cards[i].getValue() == cards[i + 1].getValue()) {
					pairValue = cards[i].getValue();
				}
			}
			for (int i = 0; i < cards.length - 1; i++) {
				if (cards[i].getValue() == cards[i + 1].getValue() 
						&& pairValue != cards[i].getValue()) {
					twoPair++;
				}
			}
		}
		if (twoPair > 0) {
			return true;
		} else {
			return false;
		}
	}
	// Checks to see if three cards in the hand share the same value
	public static boolean hasThreeOfAKind(Card[] cards) {
		int threeOfAKind = 0;
		sortHandValue(cards);
		for (int i = 0; i < cards.length - 2; i++) {
			if (cards[i].getValue() == cards[i + 1].getValue()
					&& cards[i + 1].getValue() == cards[i + 2].getValue()) {
				threeOfAKind++;
			}
		}
		if (threeOfAKind > 0) {
			return true;
		} else {
			return false;
		}
	}
	// Checks to see if the cards in the hand increase in value in sequential 
	// order ie 1-2-3-4-5
	public static boolean hasStraight(Card [] cards) {
		sortHandValue(cards);
		if (cards[0].getValue() + 4 == cards[1].getValue() + 3
				&& cards[1].getValue() + 3 == cards[2].getValue() + 2
				&& cards[2].getValue() + 2 == cards[3].getValue() + 1
				&& cards[3].getValue() + 1 == cards[4].getValue()) {
			return true;
		} else {
			return false;
		}
	}
	// Checks to see whether all of the cards in the hand share the same suit
	public static boolean hasFlush(Card[] cards) {
		int suitFlush = cards[0].getSuit();
		if (cards[1].getSuit() == suitFlush && cards[2].getSuit() == suitFlush
				&& cards[3].getSuit() == suitFlush
				&& cards[4].getSuit() == suitFlush) {
			return true;
		} else {
			return false;
		}
	}
	// Checks to see whether the hand has a three cards that share the same 
	// value and a pair of cards that share the same value that is different 
	// from the other three
	public static boolean hasFullHouse(Card[] cards) {
		sortHandValue(cards);
		int threeOfAKindValue = -1;
		int pairValue = -1;
		if (cards[0].getValue() == cards[1].getValue()
				&& cards[1].getValue() == cards[2].getValue()) {
			threeOfAKindValue = cards[0].getValue();
			if (cards[3].getValue() == cards[4].getValue()) {
				pairValue = cards[4].getValue();
			}
		} else if (cards[4].getValue() == cards[3].getValue()
				&& cards[3].getValue() == cards[2].getValue()) {
			threeOfAKindValue = cards[4].getValue();
			if (cards[0].getValue() == cards[1].getValue()) {
				pairValue = cards[0].getValue();
			}
		}
		if ((threeOfAKindValue >= 0) && threeOfAKindValue != pairValue
				&& pairValue >= 0) {
			return true;
		} else {
			return false;
		}
	}
	// Checks to see whether 4 of the cards in the hand share the same value
	public static boolean hasFourOfAKind(Card[] cards) {
		sortHandValue(cards);
		if (cards[0].getValue() == cards[1].getValue() 
				&& cards[1].getValue() == cards[2].getValue()
				&& cards[2].getValue() == cards[3].getValue()) {
			return true;
		} else if (cards[4].getValue() == cards[3].getValue() 
				&& cards[3].getValue() == cards[2].getValue()
				&& cards[2].getValue() == cards[1].getValue()) {
			return true;
		} else {
			return false;
		}
	}
	// Checks to see whether the hand has both a straight and a flush
	public static boolean hasStraightFlush(Card[] cards) {
		if (hasStraight(cards) && hasFlush(cards)) {
			return true;
		} else {
			return false;
		}
	}
}

