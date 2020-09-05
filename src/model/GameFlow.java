package model;

import java.util.Scanner;
import java.util.regex.*;

public class GameFlow {
	
	static final int BUY_IN = 10000;
	
	static final int BIG_BLIND = 100;
	static final int SMALL_BLIND = BIG_BLIND / 2;
	static Player player1 = new Player();	static Player player2 = new Player();
	
	static Player button;
	static Player notButton;
	static Dealer dealer = new Dealer(new Deck());
	
	static Scanner scanner;
	
	

	public static void main(String[] args) {
		
		//get player names
		scanner = new Scanner(System.in);  // Create a Scanner object
		String userInput;
		
		System.out.println("Player1, please enter your name.");
	    String userName = scanner.nextLine();  // Read user input
	    player1.name = userName;
	    
	    System.out.println("Player2, please enter your name.");
	    userName = scanner.nextLine();  // Read user input
	    player2.name = userName;
	    
	    //initialize button, and stacks
	    button = player1;
	    notButton = player2;
	    
	    player1.stack = BUY_IN;	player2.stack = BUY_IN;

	    
		//play hand
		while (player1.stack>0 && player2.stack>0) {
			
			//reinitialize hand variables
			GameHand gameHand = new GameHand();
			
			//button posts small blind
			button.stack-=SMALL_BLIND;
			gameHand.pot+=SMALL_BLIND;
			
			//other player posts big blind
			notButton.stack-=BIG_BLIND;
			gameHand.pot+=BIG_BLIND;
			
			gameHand.betToMatch=BIG_BLIND;
			
			System.out.println(button.name+" has posted small blind, and "+notButton.name+" has posted big blind.");
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
			
			//betting process
			gameHand.betsAreOver = false;
			gameHand.handIsOver = false;
			
			while (!gameHand.betsAreOver) {
				doBettingProcess(gameHand);
			}
			
			gameHand.resetBettingProcess();
			
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
		Player placeHolder = button;
		button = notButton;
		notButton = placeHolder;
	}
	
	private static void printStacks() {
		System.out.println(player1.name+": "+player1.stack);
		System.out.println(player2.name+": "+player2.stack);

	}
	
	private static void doBettingProcess(GameHand gameHand) {
		String userInput;
		
		String queryMessage = ", what shall you do? Enter f for fold, c for call/check, or r XXX to raise where XXX is the amount...";
		
		//button folds, calls, or raises
		printStacks();
		System.out.println("Pot is: "+gameHand.pot);
		System.out.println(button.name+queryMessage);	
		userInput = scanner.nextLine();  // Read user input
		
		//try again if input not valid
		while (!validateUserInput(userInput, button)) {
			System.out.println("Invalid input: "+userInput);
			System.out.println(button.name+queryMessage);	
			userInput = scanner.nextLine();  // Read user input
		}
		
		//processUserInput
		processUserInput(userInput, button, gameHand);
		
		//other player checks, folds, calls, or raises
		printStacks();
		System.out.println("Pot is: "+gameHand.pot);
		System.out.println(notButton.name+queryMessage);	
		userInput = scanner.nextLine();  // Read user input
		
		//try again if input not valid
		while (!validateUserInput(userInput, notButton)) {
			System.out.println("Invalid input: "+userInput);
			System.out.println(notButton.name+queryMessage);	
			userInput = scanner.nextLine();  // Read user input
		}
		
		//processUserInput
		processUserInput(userInput, notButton, gameHand);
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
