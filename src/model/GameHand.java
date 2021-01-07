package model;

public class GameHand {
	
	Card[] flop = new Card[3];
	Card turn, river;
	
	class BettingProcess{
		int betToMatch = 0;
		
		boolean betsAreOver = false;
		
		
	}
	
	public BettingProcess bettingProcess= new BettingProcess();
	
	Player handWinner;
	
	boolean handIsOver = false;
	
	int pot = 0, bigBlind;
	
	//TODO: make bettingProcess its own class with its own variables; when resetting, create a new bettingProcess object;
	public void resetBettingProcess() {
		
		this.bettingProcess= new BettingProcess();
	}
	
	private void doBettingProcess(Player[] players) {
		String userInput;
		
		String queryMessage = ", what shall you do? Enter f for fold, c for call/check, or r XXX to raise where XXX is the amount: ";
		
		
		for (int i=0;i<players.length;i++) {
			
			while (true) {
				
				//player folds, calls, or raises
				GameFlow.printStacks();
				System.out.println("Pot is: "+pot);
				System.out.println(players[i].name+queryMessage);	
				userInput = GameFlow.scanner.nextLine();  // Read user input
				
				if (GameFlow.validateUserInput(userInput, players[i])&&processUserInput(userInput, players[i])) {
					break;
				}
				
				//reach here if input was not valid
				
				System.out.println("Invalid input: "+userInput);
			}
			//reach here when input is valid
		}
	}
	
public boolean processUserInput(String userInput, Player player) {
		
		if (userInput.charAt(0) == 'f') {
			//gameHand.bettingProcess.betsAreOver = true;
			//gameHand.handIsOver = true;
			//gameHand.handWinner = getOtherPlayer(player);
		}
		
		if (userInput.charAt(0) == 'c') {
			player.postBet(this.bettingProcess.betToMatch);
			this.pot += this.bettingProcess.betToMatch;
			//gameHand.bettingProcess.betsAreOver = true;
		}
		
		if (userInput.charAt(0) == 'r') {
			int raiseAmount = Integer.parseInt(userInput.substring(2));
			if (raiseAmount<=this.bettingProcess.betToMatch||raiseAmount < GameFlow.BIG_BLIND || raiseAmount > player.stack) {
				return false;
			}
			this.bettingProcess.betToMatch = raiseAmount;
			player.postBet(raiseAmount);
			this.pot += raiseAmount;
		}
		
		return true;
		
	}
	
	private int proceedRound(int selfIndex, Player[] players, String action) {
		boolean invalid = false;
		
		
		if (invalid) {
			return -1;
		}
		
		return selfIndex+1;
	}
	
	

}
