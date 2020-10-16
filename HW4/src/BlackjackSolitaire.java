import java.util.*;

public class BlackjackSolitaire {
	public static Card[] board;
	public static int[] column_points = {0, 0, 0, 0, 0};
	public static int[] row_points = {0, 0, 0, 0};
	
	
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
	 * Adds the point value of the input Card to the corresponding row sum
	 */
	public void updateRowValue(Card c, int location) {
		// TODO: write logic for Blackjack combos, busting, etc.
		if (location < 6) {
			// First row
			row_points[0] += c.points;
		} else if (location < 11) {
			// Second row
			row_points[1] += c.points;
		} else if (location < 14) {
			// Third row
			row_points[2] += c.points;
		} else if (location < 17) {
			// Fourth row
			row_points[3] += c.points;
		} else {
			// Discard square
			
		}
	}
	
	/*
	 * Adds the point value of the input Card to the corresponding column sum
	 */
	public void updateColValue(Card c, int location) {
		// TODO: write logic for Blackjack combos, busting, etc.
		if (location == 1 || location == 6) {
			// First column
			column_points[0] += c.points;
		} else if (location % 7 == 0 || location == 2 || location == 11) {
			// Second column
			column_points[1] += c.points;
		} else if (location == 3 || location == 8 || location == 12 || location == 15) {
			// Third column
			column_points[2] += c.points;
		} else if (location == 4 || location == 9 || location == 13 || location == 16) {
			// Fourth column
			column_points[3] += c.points;
		} else if (location == 5 || location == 10) {
			// Fifth column
			column_points[4] += c.points;
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
		updateRowValue(c, location);
		updateColValue(c, location);
		return true;
	}
	
	/*
	 * Calculate the final score for the game
	 */
	public int calculateScore() {
		int score_sum = 0;
		for (int i = 0; i < 5; i++) {
			if (i < 4) {
				score_sum += row_points[i] + column_points[i];
			} else {
				// row_points[4] would be out-of-bounds
				score_sum += column_points[i];
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
		
		// The game is over and score will be calculated
		System.out.println("Game over! Final score: " + calculateScore());
	}
}
