package poker;

public class Deck {

	private Card[] cards;
// Constructor
	public Deck() {
		this.cards = new Card[52];
		int cardNum = 0;
		for (int suit = 0; suit < 4; suit++) {
			for (int index = 1; index <= 13; index++) {
				this.cards[cardNum] = new Card(index, suit);
				cardNum++;
			}
		}
	}
	// Copy constructor
	public Deck(Deck other) {
		this.cards = new Card[other.getNumCards()];
		for (int cardNum = 0; cardNum < other.getNumCards(); cardNum++) {
			this.cards[cardNum] = other.cards[cardNum];
		}
	}

	// Returns the cards object at the specified position
	public Card getCardAt(int position) {
		return this.cards[position];
	}

	// Returns the length of the deck
	public int getNumCards() {
		int numCards = 0;
		for (int currCard = 0; currCard < this.cards.length; currCard++) {
			if (this.cards[currCard] != null) {
				numCards++;
			}
		}
		return numCards;
	}
	// Shuffles the deck by splitting it in half and putting one card from each 
	// half until none are left
	public void shuffle() {
		int evenOrOdd = 1;
		Card[] frontDeck;
		Card[] bottomDeck = new Card[this.getNumCards() / 2];
		int counter = 0;
		if (this.getNumCards() % 2 == 0) {
			evenOrOdd = 0;
		}
		frontDeck = new Card[(this.getNumCards() / 2) + evenOrOdd];
		for (int cardNum = 0; cardNum < this.getNumCards(); cardNum++) {
			if (cardNum < (this.getNumCards() / 2) + evenOrOdd) {
				frontDeck[cardNum] = this.getCardAt(cardNum);
			} else {
				bottomDeck[cardNum - (this.getNumCards() / 2 + evenOrOdd)] 
						= this.getCardAt(cardNum);
			}
		}
		if (evenOrOdd == 0) {
			for (int currCard = 0; currCard 
					< this.getNumCards(); currCard += 2) {
				this.cards[currCard] = frontDeck[counter];
				this.cards[currCard + 1] = bottomDeck[counter];
				counter++;
			}
		} else {
			for (int currCard = 0; currCard < 
					this.getNumCards() - 1; currCard += 2) {
				this.cards[currCard] = frontDeck[counter];
				this.cards[currCard + 1] = bottomDeck[counter];
				counter++;
			}
			this.cards[this.getNumCards() - 1] 
					= frontDeck[this.getNumCards() / 2];
		}
	}
	// Divides the deck into two sub-packets related to the argument. The cards
	// before the position will be brought to the back of the deck.
	public void cut(int position) {
		int counter = 0;
		Card[] frontDeck = new Card[position];
		Card[] bottomDeck = new Card[this.getNumCards() - position];
		for (int cardNum = 0; cardNum < this.getNumCards(); cardNum++) {
			if (cardNum < position) {
				frontDeck[cardNum] = this.getCardAt(cardNum);
			} else {
				bottomDeck[cardNum - position] = this.getCardAt(cardNum);
			}
		}
		for (int currCard = 0; currCard < this.getNumCards(); currCard++) {
			if (currCard < this.getNumCards() - position) {
				this.cards[currCard] = bottomDeck[currCard];
			} else {
				this.cards[currCard] = frontDeck[counter];
				counter++;
			}
		}
	}
	// Removes the specified number of cards from the top of the deck and
	// returns them as an array.
	public Card[] deal(int numCards) {
		Card[] smaller = new Card[this.getNumCards() - numCards];
		Card[] dealt = new Card[numCards];
		int counter = 0;
		for (int currCard = 0; currCard < numCards; currCard++) {
			dealt[currCard] = this.cards[currCard];
		}
		for (int currCard = numCards; currCard < this.getNumCards(); 
				currCard++) {
			smaller[counter] = this.cards[currCard];
			counter++;
		}
		this.cards = smaller;
		return dealt;
	}
}
