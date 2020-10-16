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
						System.out.print(padding_space + board[current_space-1]);
					} else {
						// Display the space number
						for (int k = 0; k < ("" + current_space).length(); k++) {
							padding_space = padding_space.substring(1);
						}
						System.out.print(padding_space + current_space);
					}
				} else {
					//Display the discard square
					int current_space = 16 + (i*2) + j;
					String padding_space = "   ";
					if (board[current_space-1] != null) {
						// Display the given card value
						for (int k = 0; k < board[current_space-1].value.length(); k++) {
							padding_space = padding_space.substring(1);
						}
						System.out.print(padding_space + board[current_space-1]);
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
		if (board[location-1] != null) {
			System.out.println("ERROR: Cannot place card in already full spot.");
			//call to repeat prompt method
			return false;
		}
		// If the target is valid, place the card there and update board_filled and point arrays
		board[location-1] = c;
		return true;
	}
	
	/*
	 * Calculate the final score for the game
	 */
	public int calculateScore() {
		//TODO: Determine scores for each row and column, including Blackjack combos, busts, etc.
		int score_sum = 0;
		for (int i = 0; i < 5; i++) {
			if (i < 4) {
				score_sum += 1; // STUB row_points[i] + column_points[i];
			} else {
				// row_points[4] would be out-of-bounds
				score_sum += 1; // STUB column_points[i];
			}
		}
		return score_sum;
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
