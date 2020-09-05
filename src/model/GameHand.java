package model;

public class GameHand {
	
	Card[] flop = new Card[3];
	Card turn, river;
	
	Player handWinner;
	
	boolean betsAreOver = false, handIsOver = true;
	
	int pot = 0, betToMatch = 0, bigBlind;

}
