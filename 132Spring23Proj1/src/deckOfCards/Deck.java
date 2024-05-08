package deckOfCards;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {

	private ArrayList<Card> deck = new ArrayList<>();
	
	// Constructor
	public Deck() {
		for (Suit s : Suit.values()) {
			for (Rank r : Rank.values()) {
				deck.add(new Card(r, s));
			}
		}
	}
	
	// Shuffles the deck
	public void shuffle(Random randomNumberGenerator) {
		Collections.shuffle(deck, randomNumberGenerator);
	}
	
	public Card dealOneCard() {
		Card dealt = deck.get(0);
		deck.remove(0);
		return dealt;
	}
}
