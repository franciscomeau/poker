package model;

public class GameFlow {
	
	int bigBlind = 100;
	int smallBlind = bigBlind / 2;
	static Player player1 = new Player();	static Player player2 = new Player();
	
	Player dealer = player1;
	Player notDealer = player2;
	
	

	public static void main(String[] args) {
		
		while (player1.stack>0 && player2.stack>0) {
			//play hand
			
			//reinitialize hand variables
			GameHand gameHand = new GameHand();
			
			//button posts small blind
			
			//other player posts big blind
			
			//dealer deals playerHands
			
			//while players havent checked or called {
				//button folds, calls, or raises
				
				//other player checks, folds, calls, or raises
			//}
			
			//dealer flops
			
			//while players havent checked or called {
				//button folds, calls, or raises
				
				//other player checks, folds, calls, or raises
			//}
			
			//dealer turns...
			//etc
			//dealer rivers...
			//etc
			
			//show cards process (to be changed to actual poker rules later)
			//dealer shows hand
			//if notDealer beats, then show and winner = notDealer
			//if notDealer doesnt beat, then dont show and winner = dealer
					
			//winner collects pot
			//switch positions
		}
		
		if (player1.stack<=0) {
			System.out.println("Player2 is the winner.");
		} else {
			System.out.println("Player1 is the winner.");
		}


	}
	
	private void swapPlayerPositions() {
		Player placeHolder = dealer;
		dealer = notDealer;
		notDealer = placeHolder;
	}

}
