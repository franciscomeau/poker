package model;

import java.util.Scanner;
import java.util.regex.*;

public class GameFlow {
	
	
	static final int TABLE_SIZE = 9;
	static final int BUY_IN = 100;
	
	static final int BIG_BLIND = 2;
	static final int SMALL_BLIND = BIG_BLIND / 2;
	
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
		
		while (true) {
			System.out.print("Please enter the number of desired players: ");
		    numberOfPlayers = scanner.nextInt();  // Read user input
			System.out.println();
		    if (numberOfPlayers>=2 && numberOfPlayers<=TABLE_SIZE) {
		    	break;
		    }
		    System.out.println("Invalid input, number of players must be between 2 and "+TABLE_SIZE);
		}
		
		//get player's name then initialize their stack
		for (int i=0;i<numberOfPlayers; i++) {
			System.out.println("Player "+i+", please enter your name.");
		    String userName = scanner.nextLine();  // Read user input
		    players[i].name = userName;
		    players[i].stack = BUY_IN;
		}
	    
		GameHand gameHand;
		int winnerIndex = -1;
		
		//play hand
		while (winnerIndex==-1) {
			
			//TODO: check and update new number of players; for example that have left or been busted
	
			//reinitialize hand variables
			gameHand = new GameHand();
			
			//post small blind
			gameHand.pot+=players[smallBlindPlayerIndex].postBet(SMALL_BLIND);
			//post big blind
			gameHand.pot+=players[bigBlindPlayerIndex].postBet(BIG_BLIND);

			//TODO: make betToMatch part of a new bettingProcess class
			gameHand.betToMatch=BIG_BLIND;
			
			System.out.println(players[smallBlindPlayerIndex]+" has posted small blind, and "+players[bigBlindPlayerIndex]+" has posted big blind.");
			printStacks();
			System.out.println("Pot is: "+gameHand.pot);

			
			dealer.getDeck().restockCards();
			dealer.getDeck().shuffleSelf();

			//dealer deals players' hands and prints it to screen
			
			String handMessage = ", your hand is: ";
			
			for (int i=smallBlindPlayerIndex; i<players.length;i=modAddition(i,1)) {
				try {
					players[i].setHand(dealer.getDeck().drawCard(), dealer.getDeck().drawCard());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(players[i].name+handMessage+notButton.readHand());
			}
			
			//betting process
			//initialize betting process variables
			gameHand.betsAreOver = false;
			gameHand.handIsOver = false;
			
			while (!gameHand.betsAreOver) {
				doBettingProcess(gameHand);
			}
			
			//TODO: create new bettingProcess object, from new bettingProcess class, instead of resetting gameHand variables
			gameHand.resetBettingProcess();
			
			//dealer flops
			
			//create new betting process
			
			//dealer turns...

			//create new betting process

			//dealer rivers...

			//create new betting process
			
			//hand judging process
			
			//show cards process
					
			//winner collects pot
			
			//increment positions for next hand
			smallBlindPlayerIndex = modAddition(smallBlindPlayerIndex, 1);
			bigBlindPlayerIndex = modAddition(bigBlindPlayerIndex, 1);
			
			//check if we have a winner
			winnerIndex = checkHaveWinner(players);
		}
		
		//reaches here when we have winner due to previous while condition
		
		System.out.println(players[winnerIndex].name+" is the winner.");
		
	}
	
	static private int modAddition(int smallBlindPlayerIndex, int toAdd) {
		return (smallBlindPlayerIndex+toAdd)%numberOfPlayers;
	}
	
	private static void printStacks() {
		for (int i=0;i<numberOfPlayers; i++) {
			System.out.println(players[i].name+": "+players[i].stack);
		}
	}
	
	static private int checkHaveWinner(Player[] players) {
		int playersWithStacks = 0;
		int winnerIndex=0;
		for (int i=0;i<players.length;i++) {
			if (players[i].stack>0) {
				playersWithStacks++;
				if (playersWithStacks>1) {
					return -1;
				}
				winnerIndex=i;
			}
		}
		return winnerIndex;
	}
	
	//TODO: make bettingProcess its own class; when having another betting process, create a new bettingProcess object;
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
			player.postBet(gameHand.betToMatch);
			gameHand.pot += gameHand.betToMatch;
			gameHand.betsAreOver = true;
		}
		
		if (userInput.charAt(0) == 'r') {
			int raiseAmount = Integer.parseInt(userInput.substring(2));
			gameHand.betToMatch = raiseAmount;
			player.postBet(raiseAmount);
			gameHand.pot += raiseAmount;
		}
		
	}
	
	public static Player getOtherPlayer(Player player) {
		if (player==button) return notButton;
		
		else return button;
	}

}
