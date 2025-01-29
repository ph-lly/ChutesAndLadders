/*
 * Created on: Mar 17, 2024
 * 
 * ULID: pbnguye
 * Class: IT 179
 */
package ilstu.edu;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Chutes and Ladders main program that runs Chutes and Ladders
 * @author Phillip
 *
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Chutes and Ladders! ");
		Scanner scan = new Scanner(System.in);
		boolean run = true;
		while(run) {
			try {
				System.out.print("\nEnter the number of players: ");
				int numPlayers = scan.nextInt();
				while(numPlayers <=0) {
					System.out.println("\nPlease enter in a positive number of players.");
					System.out.print("\nEnter the number of players: ");
					numPlayers = scan.nextInt();
				}
				Game game = new Game(numPlayers);
				game.play();
				run = false;
			}
			catch(InputMismatchException ime) {
				System.out.println("\nInvalid input. Please double check your input.");
				scan.nextLine();
			}
			catch(IllegalArgumentException ia) {
				System.out.println("\nInvalid input. Please double check your input.");	
				scan.nextLine();
			}
		}
		scan.close();
	}

}
