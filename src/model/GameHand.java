package model;

public class GameHand {
	
	Card[] flop = new Card[3];
	Card turn, river;
	
	boolean betsAreOver = false;
	
	int pot = 0, betToMatch = 0, bigBlind;

}
