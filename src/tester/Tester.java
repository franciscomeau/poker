package tester;

import model.*;

public class Tester {

	public static void main(String[] args) {
		Deck deck = new Deck();
		
		deck.shuffleSelf();
		for (int i=0; i<52;i++) System.out.println(deck.cards.get(i).readCard());




	}

}
