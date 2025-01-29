/*
 * Created on: Mar 17, 2024
 * 
 * ULID: pbnguye
 * Class: IT 179
 */
package ilstu.edu;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Game class for Chutes and Ladders program 
 * (I have never played chutes and ladders)
 * 
 * @author Phillip
 *
 */
public class Game {
	/**
	 * Inner class Square for the Game class.
	 * @author Phillip
	 *
	 */
	private class Square {
		
		private Square next;
		private Square prev;
		private int space;
		private int jump;
		
		/**
		 * Square constructor that initializes Square values
		 * @param space - space to be used
		 * @param jump - jump to be used
		 * @param prev - prev to be used
		 */
		private Square(int space, int jump, Square prev) {
			this.space = space;
			this.jump = jump;
			this.next = null;
			this.prev = prev;
		}
	}
	
	private Square start; 
	private ArrayList<Square> players;
	private int currPlayer;
	
	/**
	 * Game constructor that initializes the game with the number of players and creates the board
	 * @param numPlayers - numPlayers to be used
	 */
	public Game(int numPlayers) {
		players = new ArrayList<Square>(numPlayers);
		for (int i = 0; i < numPlayers; i++) {
			players.add(null);
			currPlayer = 0;
		}
		generateBoard();
	}
	/**
	 * generateBoard helper method that generates the board
	 */
	private void generateBoard() {
		Square curr = null;
		Random rand = new Random();
		int chuteLadderSpaceCount = 0; 
		for (int i = 1; i <= 100; i++) {
			int jump = 0;
			if (rand.nextInt(5) == 4 && i != 1) { //80%, 0 1 2 3 4 only 4 will trigger 1/5 of the time
				if(chuteLadderSpaceCount < 20) {
					if (i == 100) { // space 100 no ladder check
						jump = 0;
					}
					else if(jump > 100-i) { 
						jump = rand.nextInt(100-i)+1;
					}
					else {
					jump = rand.nextInt(4) + 1;
					}
				}
				chuteLadderSpaceCount++;
			}
			if (rand.nextInt(2) == 0) {//50% for pos or neg 0 1 
				jump = -1*jump;
			}
			curr = new Square(i, jump, curr);
			if (i == 1) {
				start = curr;
			}
			else if (curr.prev != null) {
				curr.prev.next = curr;
			}
		}
		for (int i = 0; i < players.size(); i++) { //sets players to start
			players.set(i, start);
		}
	}
	/**
	 * getCurrPlayer method that gets the current player
	 * @return - int
	 */
	private int getCurrPlayer() {
		return currPlayer + 1;
	}
	/**
	 * nextPlayer helper method that rotates to the next player
	 */
	private int nextPlayer() {
		currPlayer++;
		if (currPlayer >= players.size()) {
			currPlayer = 0;
		}
		return currPlayer;
	} 
	/**
	 * play method that lets the player roll by pressing enter and plays out chutes and ladders
	 */
	public void play() {
		Scanner scan = new Scanner(System.in);
		boolean win = false;
		while (!win) {
			System.out.println("-----------------------------------------\n" +"Player " + (currPlayer + 1) + "'s turn. Press enter to roll.");
			scan.nextLine();
			int dieRoll = rollDie();
			System.out.println("Player " + (currPlayer + 1) + " rolled a " + dieRoll + ".");
			boolean finish = move(dieRoll);
			if (finish) {
				win = true;
				System.out.println("-----------------------------------------\n"+"Player " + (currPlayer + 1) + " is the winner!" + "\n-----------------------------------------");
			}
			nextPlayer();
		}
		scan.close();
	}
	/**
	 * move method that moves the player
	 * @param movement - movement to be used
	 * @return - boolean
	 */
	private boolean move(int movement) {
		Square curr = players.get(currPlayer);
		int start = curr.space;
		int end = start + movement;
		
		if (end > 100) {
			end = 100;
		}
		int jump = 0;
		
		while (movement > 0) {
			if (curr.next != null) {
				curr = curr.next;
			}
			movement--;
		}
		int currSpace = curr.space;
		jump = curr.jump;
		
		if (jump != 0) {
			String word = "";
			if (jump == 1 || jump == -1) {
				word = " space";
			}
			else {
				word = " spaces";
			}
			if (jump > 0 && end != 100) {
				System.out.println("Player " + getCurrPlayer() + " landed on a ladder!");
				System.out.println("The ladder is " + jump + word + " long.\n");
			}
			else if (jump < 0 && end != 100) {
				System.out.println("Player " + getCurrPlayer() + " landed on a chute!");
				System.out.println("The chute is " + -1*jump + word +" long.\n");
			}
		}
		boolean jumping = false;
		String cOrL = "";
		if(jump != 0) {
			jumping = true;
			if(jump > 0) {
				cOrL = "ladder";
			}
			else {
				cOrL = "chute";
			}
		}
		while (jump != 0) { //if jump
			if (jump > 0) {
				if (curr.next != null) {
					curr = curr.next;
				}
				jump--;	
			}
			else {
				if (curr.prev != null) {
					curr = curr.prev;
				}
				jump++;
			}
		}
		players.set(currPlayer, curr);
		if (curr.space == 100) {
			System.out.println("Player " + getCurrPlayer() + " moves from space " + start + " to space " + curr.space);
			System.out.println("Player " + getCurrPlayer() + " has made it to the end!");
			return true;
		}
		else if (jumping){
			System.out.println("Player " + getCurrPlayer() + " moves from space " + start + ", to a " + cOrL + " on space " + currSpace + ", taking the player to space "+ curr.space);
			return false;
		}
		else {
			System.out.println("Player " + getCurrPlayer() + " moves from space " + start + " to space " + curr.space);
			return false;
		}
	}
	/**
	 * rollDie method that returns a random int
	 * @return - int
	 */
	private int rollDie() {
		Random rand = new Random();
		return rand.nextInt(6)+1;
	}
	
	
}
