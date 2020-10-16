import java.util.*;

public class Deck {
	public ArrayList<Card> deck;
	
	public Deck() {
		deck = new ArrayList<Card>();
		for (int i = 0; i < 4; i++) { // Suits
			char[] suitChars = {'C', 'D', 'H', 'S'}; // Clubs, Diamonds, Hearts, and Spades
			for (int j = 0; j < 13; j++) { // Values
				String cardValue = "";
				int cardPoints = 0;
				if (j == 0) {
					cardValue = "A" + suitChars[i];
					cardPoints = 11;
				} else if (j == 9) {
					cardValue = "T" + suitChars[i];
					cardPoints = 10;
				} else if (j == 10) {
					cardValue = "J" + suitChars[i];
					cardPoints = 10;
				} else if (j == 11) {
					cardValue = "Q" + suitChars[i];
					cardPoints = 10;
				} else if (j == 12) {
					cardValue = "K" + suitChars[i];
					cardPoints = 10;
				} else {
					cardValue = "" + (j+1) + suitChars[i];
					cardPoints = j+1;
				}
				Card nextCard = new Card(cardValue, cardPoints);
				deck.add(nextCard);
			}
		}
	}
	
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	public Card drawCard() {
		if (deck.size() > 0) {
			Card out = deck.remove(deck.size()-1);
			return out;
		} else {
			return null;
		}
	}
}
