package model;

public class GameHand {
	
	Card[] flop = new Card[3];
	Card turn, river;
	
	Player handWinner;
	
	boolean betsAreOver = false, handIsOver = false;
	
	int pot = 0, betToMatch = 0, bigBlind;
	
	//TODO: make bettingProcess its own class with its own variables; when resetting, create a new bettingProcess object;
	public void resetBettingProcess() {
		this.betsAreOver = false;
		this.betToMatch = 0;
	}

}
