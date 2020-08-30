package tester;

import model.Card;

public class Tester {

	public static void main(String[] args) {
		Card card;
		try {
			card = new Card(Card.Suit.CLUBS, 4);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		System.out.println(card.getNumber());
		System.out.println(card.getSuit());

		

	}

}
