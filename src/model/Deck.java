package model;

import java.util.ArrayList;
import model.Card.*;
import java.util.Random;


public class Deck {
	public ArrayList<Card> cards = new ArrayList<Card>();
	Random rand = new Random();
   
	
	public Deck() {
		restockCards();
	}
	
	public void shuffleSelf() {
		ArrayList<Card> shuffledCards = new ArrayList<Card>();

		Card myCard;
		int randomNumber;
		for (int i=cards.size()-1; i>=0; i--) {
			randomNumber = rand.nextInt(i+1);
			myCard = this.cards.get(randomNumber);
			shuffledCards.add(myCard);
			this.cards.remove(randomNumber);
		}
		
		this.cards = shuffledCards;
	}
	
	public void restockCards() {
		this.cards.clear();
		Suit suit = Card.Suit.CLUBS;
		
		for (int j=0; j<4;j++) {
			if (j==0) suit = Card.Suit.CLUBS;
			else if (j==1) suit = Card.Suit.DIAMONDS;
			else if (j==2) suit = Card.Suit.HEARTS;
			else if (j==3) suit = Card.Suit.SPADES;
			for (int i=1; i<14; i++) {
				try {
					cards.add(new Card(suit, i));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public Card drawCard() {
		Card myCard = this.cards.get(0);
		this.cards.remove(0);
		return myCard;
	}
	
	

}
