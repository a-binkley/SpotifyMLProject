import java.util.*;

public class BlackjackSolitaire {
	public static Card[] board;
	
	
	public void resetBoard() {
		board = new Card[20];
	}
	
	/*
	 * Displays a pseudo-GUI version of the board to the Console
	 */
	public void displayBoard() {
		//The top two rows will be drawn identically
		for (int i = 0; i < 2; i++) {
			System.out.print("        ");
			//There are 7 slots total per row, including cards and empty spaces
			for (int j = 1; j < 6; j++) {
				int current_space = i*5 + j;
				String padding_space = "   ";
				if (board[current_space-1] != null) {
					// Display the given card value
					for (int k = 0; k < board[current_space-1].value.length(); k++) {
						padding_space = padding_space.substring(1);
					}
					System.out.print(padding_space + board[current_space-1].value);
				} else {
					// Display the space number
					for (int k = 0; k < ("" + current_space).length(); k++) {
						padding_space = padding_space.substring(1);
					}
					System.out.print(padding_space + current_space);
				}
				System.out.print(" ");
			}
			System.out.println();
		}
		//Then the bottom two rows will be drawn (identically to each other)
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 7; j++) {
				if (j == 2 || j == 6) {
					System.out.print("   ");
				} else if (j > 2) {
					//Display the bottom two rows
					int current_space = 11 + (i*3) + j-3;
					String padding_space = "   ";
					if (board[current_space-1] != null) {
						// Display the given card value
						for (int k = 0; k < board[current_space-1].value.length(); k++) {
							padding_space = padding_space.substring(1);
						}
						System.out.print(padding_space + board[current_space-1].value);
					} else {
						// Display the space number
						for (int k = 0; k < ("" + current_space).length(); k++) {
							padding_space = padding_space.substring(1);
						}
						System.out.print(padding_space + current_space);
					}
				} else {
					//Display the discard square
					int current_space = 17 + (i*2) + j;
					String padding_space = "   ";
					if (board[current_space-1] != null) {
						// Display the given card value
						for (int k = 0; k < board[current_space-1].value.length(); k++) {
							padding_space = padding_space.substring(1);
						}
						System.out.print(padding_space + board[current_space-1].value);
					} else {
						// Display the space number
						for (int k = 0; k < ("" + current_space).length(); k++) {
							padding_space = padding_space.substring(1);
						}
						System.out.print(padding_space + current_space);
					}
				}
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	/*
	 * Allows the player to pick where they want the current top card to be placed on the board
	 */
	public boolean placeCard(Card c, int location) {
		// Check if the chosen target location is invalid
		if (location > 20 || location < 1) {
			System.out.println("Invalid target location.");
			return false;
		}
		if (board[location-1] != null) {
			System.out.println("ERROR: Cannot place card in already full spot.");
			return false;
		} 
		// If the target is valid, place the card there and update board_filled and point arrays
		board[location-1] = c;
		return true;
	}
	
	/*
	 * Helper function for calculateScore() that calculates the points from a given row
	 */
	public int getRowOrColPoints(int sum, int aces) {
		int rowPoints = 0;
		if (sum > 21 && aces > 0) {
			// Get the highest possible score under 21 by reducing ace point counts
			int overcount = sum - 21;
			if (overcount > (aces * 10)) {
				sum = 0; // Bust
			} else {
				if (overcount % 10 != 0) {
					sum -= 10 * (overcount / 10 + 1); // Reduce ace points to get score under 22
				} else {
					sum = 21;
				}
			}
		}
		if (sum == 21) {
			rowPoints = 7;
		} else if (sum > 21) {
			rowPoints = 0;
		} else if (sum < 17) {
			rowPoints = 1;
		} else {
			rowPoints = sum - 15;
		}
		sum = 0;
		return rowPoints;
	}
	
	/*
	 * Calculate the final score for the game
	 */
	public int calculateScore() {
		int scoreSum = 0;
		int tempSum = 0;
		int aceCount = 0;
		
		// Calculate first row sum
		for (int i = 0; i < 5; i++) {
			if (board[i].value.charAt(0) == 'A') {
				aceCount++;
			}
			tempSum += board[i].points;
		}
		scoreSum += getRowOrColPoints(tempSum, aceCount);
		aceCount = 0;
		tempSum = 0;
		
		// Calculate second row sum
		for (int i = 5; i < 10; i++) {
			if (board[i].value.charAt(0) == 'A') {
				aceCount++;
			}
			tempSum += board[i].points;
		}
		scoreSum += getRowOrColPoints(tempSum, aceCount);
		aceCount = 0;
		tempSum = 0;
		
		// Calculate third row sum
		for (int i = 10; i < 13; i++) {
			if (board[i].value.charAt(0) == 'A') {
				aceCount++;
			}
			tempSum += board[i].points;
		}
		scoreSum += getRowOrColPoints(tempSum, aceCount);
		aceCount = 0;
		tempSum = 0;

		// Calculate fourth row sum
		for (int i = 13; i < 16; i++) {
			if (board[i].value.charAt(0) == 'A') {
				aceCount++;
			}
			tempSum += board[i].points;
		}
		scoreSum += getRowOrColPoints(tempSum, aceCount);
		aceCount = 0;
		tempSum = 0;
		
		// For calculating column sums, check for Blackjack combos in first and fifth columns
		// Calculate first column sum
		tempSum = board[0].points + board[5].points;
		if (tempSum == 21) {
			// Blackjack!
			scoreSum += 10;
		} else {
			if (board[0].value.charAt(0) == 'A') {
				aceCount++;
			} 
			if (board[5].value.charAt(0) == 'A') {
				aceCount++;
			}
			scoreSum += getRowOrColPoints(tempSum, aceCount);
		}
		aceCount = 0;
		tempSum = 0;
		
		// Calculate second column sum
		int[] indices1 = {1, 6, 10, 13};
		for (int element : indices1) {
			tempSum += board[element].points;
			if (board[element].value.charAt(0) == 'A') {
				aceCount++;
			}
		}
		scoreSum += getRowOrColPoints(tempSum, aceCount);
		aceCount = 0;
		tempSum = 0;
		
		// Calculate third column sum
		int[] indices2 = {2, 7, 11, 14};
		for (int element : indices2) {
			tempSum += board[element].points;
			if (board[element].value.charAt(0) == 'A') {
				aceCount++;
			}
		}
		scoreSum += getRowOrColPoints(tempSum, aceCount);
		aceCount = 0;
		tempSum = 0;
		
		// Calculate fourth column sum
		int[] indices3 = {3, 8, 12, 15};
		for (int element : indices3) {
			tempSum += board[element].points;
			if (board[element].value.charAt(0) == 'A') {
				aceCount++;
			}
		}
		scoreSum += getRowOrColPoints(tempSum, aceCount);
		aceCount = 0;
		tempSum = 0;
		
		// Calculate fifth column sum
		tempSum = board[4].points + board[9].points;
		if (tempSum == 21) {
			// Blackjack!
			scoreSum += 10;
		} else {
			if (board[4].value.charAt(0) == 'A') {
				aceCount++;
			} 
			if (board[9].value.charAt(0) == 'A') {
				aceCount++;
			}
			scoreSum += getRowOrColPoints(tempSum, aceCount);
		}
		return scoreSum;
	}
	
	/* 
	 * Allows the user to play one round of Blackjack Solitaire
	 */
	public void play() {
		resetBoard();
		displayBoard();
		Deck gameDeck = new Deck();
		gameDeck.shuffle();
		
		// Game round loop setup
		boolean isOver = false;
		boolean hasNull = false;
		Scanner s = new Scanner(System.in);
		Card currentCard = gameDeck.drawCard();
		
		// Game round loop
		while (!isOver) {
			System.out.println("Enter the target spot to place the card (" + currentCard.value + "):");
			int target = s.nextInt();
			if (!placeCard(currentCard, target)) {
				// If the card placement is invalid, re-display the prompt
				continue; 
			}
			displayBoard();
			currentCard = gameDeck.drawCard();
			
			// Check the state of the board to see if we're done
			hasNull = false;
			for (int i = 0; i < 16; i++) {
				if (board[i] == null) {
					hasNull = true;
					break;
				}
			}
			if (!hasNull) {
				isOver = true; // The game is over, so total the score and show it in console
			}
		}
		
		s.close();

		System.out.println("Game over! Final score: " + calculateScore());
	}
}
