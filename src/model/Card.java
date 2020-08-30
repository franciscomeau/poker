package model;

public class Card {
	
	public static enum Suit {
		  SPADES,
		  CLUBS,
		  HEARTS,
		  DIAMONDS
		}
	
	private Suit suit;
	private int number;
	
	public Card(Suit suit, int number) throws Exception {
		if (!(number > 0 && number < 14)) throw new Exception("Number must be from 1 to 13");
		this.suit = suit;
		this.number = number;
	}
	
	public Suit getSuit() {
		return this.suit;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public String readCard() {
		return number+" of "+suit;
	}
	
	

}
