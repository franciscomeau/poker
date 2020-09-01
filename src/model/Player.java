package model;

public class Player {
	
	public Card[] hand = new Card[2];
	
	public void setHand(Card card1, Card card2) throws Exception {
		if (card1==null||card2==null) throw new Exception("card cannot be null");
		hand[0] = card1;
		hand[1] = card2;
	}
	
	public String readHand() {
		return hand[0].readCard()+" and "+hand[1].readCard();
	}

}
