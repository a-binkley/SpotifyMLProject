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
			//There are 7 slots total per row, including cards and empty spaces
			for (int j = 0; j < 7; j++) {
				if (j < 2) {
					System.out.print("   ");
				} else {
					int current_space = i*5 + (j-2);
					String padding_space = "   ";
					if (board[current_space] != null) {
						// Display the given card value
						for (int k = 0; k < board[current_space].value.length(); k++) {
							padding_space = padding_space.substring(1);
						}
					} else {
						// Display the space number
						for (int k = 0; k < ("" + (current_space+1)).length(); k++) {
							padding_space = padding_space.substring(1);
						}
					}
					System.out.print(padding_space + (current_space+1));
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
					int current_space = 10 + (i*3) + j-3;
					String padding_space = "   ";
					if (board[current_space] != null) {
						// Display the given card value
						for (int k = 0; k < board[current_space].value.length(); k++) {
							padding_space = padding_space.substring(1);
						}
					} else {
						// Display the space number
						for (int k = 0; k < ("" + current_space).length(); k++) {
							padding_space = padding_space.substring(1);
						}
					}
					System.out.print(padding_space + (current_space+1));
				} else {
					//Display the discard square
					int current_space = 16 + (i*2) + j;
					String padding_space = "   ";
					if (board[current_space] != null) {
						// Display the given card value
						for (int k = 0; k < board[current_space].value.length(); k++) {
							padding_space = padding_space.substring(1);
						}
					} else {
						// Display the space number
						for (int k = 0; k < ("" + current_space).length(); k++) {
							padding_space = padding_space.substring(1);
						}
					}
					System.out.print(padding_space + (current_space+1));
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
	public void placeCard(Card c, int location) {
		// Check if the chosen target location is invalid
		if (board[location-1] != null) {
			System.out.println("ERROR: Cannot place card in already full spot.");
			//call to repeat prompt method
			return;
		}
		// If the target is valid, place the card there and update the board_filled and point arrays
		board[location-1] = c;
		updateRowValue(c, location);
		updateColValue(c, location);
	}
	
	/* 
	 * Allows the user to play one round of Blackjack Solitaire
	 */
	public void play() {
		resetBoard();
		displayBoard();
		Deck gameDeck = new Deck();
		gameDeck.shuffle();
		//TEMP
		for (int i = 0; i < 52; i++) {
			System.out.print(gameDeck.drawCard().value + " ");
		}
		
	}
}
