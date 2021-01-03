package model;

import java.util.Scanner;
import java.util.regex.*;

public class GameFlow {
	
	
	static final int TABLE_SIZE = 9;
	static final int BUY_IN = 100;
	
	static final int BIG_BLIND = 2;
	static final int SMALL_BLIND = BIG_BLIND / 2;
	//static Player player1 = new Player();	static Player player2 = new Player();
	
	static int numberOfPlayers = 2;
	static Player[] players = new Player[numberOfPlayers];
	static int smallBlindPlayerIndex = 0;
	static int bigBlindPlayerIndex =1;
	static Player button;
	static Player notButton;
	static Dealer dealer = new Dealer(new Deck());
	
	static Scanner scanner;
	
	

	public static void main(String[] args) {
		
		scanner = new Scanner(System.in);
		String userInput;
		
		//TODO: send error message for invalid input
		do {
			System.out.print("Please enter the number of desired players: ");
		    numberOfPlayers = scanner.nextInt();  // Read user input
		} while (numberOfPlayers<2||numberOfPlayers>TABLE_SIZE);
		
		System.out.println();
		
		//get player's name then initialize their stack
		for (int i=0;i<numberOfPlayers; i++) {
			System.out.println("Player "+i+", please enter your name.");
		    String userName = scanner.nextLine();  // Read user input
		    players[i].name = userName;
		    players[i].stack = BUY_IN;
		}
	    
		GameHand gameHand;
		
		//play hand
		while (!checkHaveWinner(players)) { //TODO: change method in while condition to return the index number of the winning player or -1 if there's no winner yet 
			
			//reinitialize hand variables
			gameHand = new GameHand();
			
			//TODO: create players[index].postBet(int betSize) method in Player which returns the actual amount posted if the player doesnt have enough to post the whole blind
			//post small blind
			players[smallBlindPlayerIndex].stack-=SMALL_BLIND;
			gameHand.pot+=SMALL_BLIND;
			
			//post big blind
			players[bigBlindPlayerIndex].stack-=BIG_BLIND;
			gameHand.pot+=BIG_BLIND;
			
			gameHand.betToMatch=BIG_BLIND;
			
			System.out.println(players[smallBlindPlayerIndex]+" has posted small blind, and "+players[bigBlindPlayerIndex]+" has posted big blind.");
			printStacks();
			System.out.println("Pot is: "+gameHand.pot);

			
			dealer.getDeck().restockCards();
			dealer.getDeck().shuffleSelf();

			//dealer deals playerHands
			try {
				notButton.setHand(dealer.getDeck().drawCard(), dealer.getDeck().drawCard());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				button.setHand(dealer.getDeck().drawCard(), dealer.getDeck().drawCard());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//print players' hands
			String handMessage = ", your hand is: ";
			System.out.println(notButton.name+handMessage+notButton.readHand());
			System.out.println(button.name+handMessage+button.readHand());

			
			//betting process
			gameHand.betsAreOver = false;
			gameHand.handIsOver = false;
			
			while (!gameHand.betsAreOver) {
				doBettingProcess(gameHand);
			}
			
			//TODO: create new bettingProcess object instead of resetting gameHand variables
			gameHand.resetBettingProcess();
			
			//dealer flops
			
			//create new betting process
			
			//dealer turns...

			//create new betting process

			//dealer rivers...

			//create new betting process
			
			//hand judger process
			
			//show cards process
					
			//winner collects pot
			
			//increment positions for next hand
			smallBlindPlayerIndex = modAddition(smallBlindPlayerIndex, 1);
			bigBlindPlayerIndex = modAddition(bigBlindPlayerIndex, 1);
		}
		
		//reaches here when we have winner
		
		//find who is winner
		for (int i=0;i<players.length;i++) {
			if (players[i].stack>0) {
				System.out.println(players[i].name+" is the winner.");
				break;
			}
		}
	}
	
	static private int modAddition(int smallBlindPlayerIndex, int toAdd) {
		return (smallBlindPlayerIndex+toAdd)%numberOfPlayers;
	}
	
	private void swapPlayerPositions() {
		Player placeHolder = button;
		button = notButton;
		notButton = placeHolder;
	}
	
	private static void printStacks() {
		for (int i=0;i<numberOfPlayers; i++) {
			System.out.println(players[i].name+": "+players[i].stack);
		}
	}
	
	static private boolean checkHaveWinner(Player[] players) {
		int playersWithStacks = 0;
		for (int i=0;i<players.length;i++) {
			if (players[i].stack>0) {
				playersWithStacks++;
			}
		}
		
		if (playersWithStacks>=2) {
			return true;
		} else {
			return false;
		}
	}
	
	private static void doBettingProcess(GameHand gameHand) {
		String userInput;
		
		String queryMessage = ", what shall you do? Enter f for fold, c for call/check, or r XXX to raise where XXX is the amount...";
		
		
		for (int i=0;i<players.length;i++) {
			//player folds, calls, or raises
			printStacks();
			System.out.println("Pot is: "+gameHand.pot);
			System.out.println(players[i].name+queryMessage);	
			userInput = scanner.nextLine();  // Read user input
			
			//try again if input not valid
			while (!validateUserInput(userInput, players[i])) {
				System.out.println("Invalid input: "+userInput);
				System.out.println(players[i].name+queryMessage);	
				userInput = scanner.nextLine();  // Read user input
			}
			
			//processUserInput
			processUserInput(userInput, players[i], gameHand);
		}
	}
	
	public static boolean validateUserInput(String input, Player player) {
		
		String validExpressionFormat = "[fc]|r (\\d)+";
		Pattern pattern = Pattern.compile(validExpressionFormat);
		Matcher matcher = pattern.matcher(input);
		
		if (!matcher.matches()) return false;
		
		if (input.charAt(0)=='r') {
			int raiseAmount = Integer.parseInt(input.substring(2));
			if (raiseAmount < BIG_BLIND || raiseAmount > player.stack) return false;
		}
		
		return true;
	}
	
	private static void processUserInput(String userInput, Player player, GameHand gameHand) {
		
		if (userInput.charAt(0) == 'f') {
			gameHand.betsAreOver = true;
			gameHand.handIsOver = true;
			gameHand.handWinner = getOtherPlayer(player);
		}
		
		if (userInput.charAt(0) == 'c') {
			player.bet(gameHand.betToMatch);
			gameHand.pot += gameHand.betToMatch;
			gameHand.betsAreOver = true;
		}
		
		if (userInput.charAt(0) == 'r') {
			int raiseAmount = Integer.parseInt(userInput.substring(2));
			gameHand.betToMatch = raiseAmount;
			player.bet(raiseAmount);
			gameHand.pot += raiseAmount;
		}
		
	}
	
	public static Player getOtherPlayer(Player player) {
		if (player==button) return notButton;
		
		else return button;
	}

}
