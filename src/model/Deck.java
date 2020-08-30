package model;

import java.util.ArrayList;

import model.Card.*;

public class Deck {
	public ArrayList<Card> cards = new ArrayList<Card>();
   
	
	public Deck() {
		
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
	
	

}
