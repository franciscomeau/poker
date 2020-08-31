package tester;

import model.*;

public class Tester {

	public static void main(String[] args) {
		Deck deck = new Deck();		
		for (int i=0; i<52;i++) System.out.println(deck.cards.get(i).readCard());

		System.out.println("Now shuffling deck...");
		deck.shuffleSelf();
		System.out.println("Player draw 2 cards:");
		System.out.println(deck.drawCard().readCard()+" and "+deck.drawCard().readCard());
		System.out.println("Burning one card...");
		System.out.println("And flop is:");
		System.out.println(deck.drawCard().readCard()+", "+deck.drawCard().readCard()+", and "+deck.drawCard().readCard());

	}

}
