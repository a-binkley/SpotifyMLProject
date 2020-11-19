import java.util.*;

public class Hangman {
	private static ArrayList<String> words = new ArrayList<>();
	private static ArrayList<String> sameLenWords = new ArrayList<>();
	private static ArrayList<Character> guessedLetters = new ArrayList<>();
	private static ArrayList<Character> incorrectGuesses = new ArrayList<>();

	/**
	 * Populates the set of words Hangman can choose from
	 */
	public static void makeWordList(String dictionary) {
		DictionaryReader dr = new DictionaryReader(dictionary);
		words = dr.getWordList();
	}

	/**
	 * Helper function for play() that outputs the current state of the game to the
	 * console
	 * 
	 * @param partialWord
	 * @param fullWord
	 */
	public static void displayState(String partialWord, String fullWord) {
		System.out.println("Guess a letter");
		// display the progress on the chosen word
		for (char c : partialWord.toCharArray()) {
			System.out.print(c + " ");
		}
		System.out.println();
		// if there are incorrect guesses, display them as well
		if (incorrectGuesses.size() != 0) {
			System.out.println("Incorrect guesses:\n" + incorrectGuesses);
		}
	}

	/**
	 * Returns a new word with the same pattern of _'s and guessed letters 
	 * as the input, taken from sameLenWords (the list of possible words)
	 * 
	 * @param oldWord
	 * @param letter
	 * @return
	 */
	public static String evilSwap(String displayWord, char letter) {
		HashMap<String, ArrayList<String>> wordFamilies = new HashMap<>();
		// populate the HashMap
		for (String word : sameLenWords) {
			String familyStr = word;
			for (int i = 0; i < familyStr.length(); i++) {
				if (!guessedLetters.contains(familyStr.charAt(i)) && !incorrectGuesses.contains(familyStr.charAt(i))) {
					familyStr = familyStr.substring(0, i) + "_" + familyStr.substring(i+1);
				}
			}
			if (!wordFamilies.keySet().contains(familyStr)) {
				wordFamilies.put(familyStr, new ArrayList<String>());
			}
			wordFamilies.get(familyStr).add(word);
		}
		
		// determine the largest word family
		int largest = 0;
		String family = "";
		for (String k : wordFamilies.keySet()) {
			if (wordFamilies.get(k).size() > largest) {
				largest = wordFamilies.get(k).size();
				family = k;
			}
		}
		sameLenWords = wordFamilies.get(family);
		return sameLenWords.get((int) (Math.random() * sameLenWords.size()));
	}

	/**
	 * The primary loop that controls the flow of the Hangman game
	 */
	public static void play() {
		// pick a starting word at random
		Scanner scnr = new Scanner(System.in);
		String chosenWord = words.get((int) (Math.random() * words.size())).toUpperCase();
		int wordLen = chosenWord.length();

		// narrow down the options to only words of the same length
		words.forEach((word) -> {
			if (word.length() == wordLen) {
				sameLenWords.add(word);
			}
		});

		// create the String to display each round
		String displayWord = "";
		for (int i = 0; i < wordLen; i++) {
			displayWord += ("_");
		}

		System.out.println("Begin! If you guess 8 incorrect letters, you lose.");

		// loop through user guesses until win or lose
		while (displayWord.contains("_") && incorrectGuesses.size() < 8) {
			displayState(displayWord, chosenWord);
			String userGuess = scnr.next();
			if (userGuess.length() != 1 || userGuess.contains("_")) {
				System.out.println("Invalid guess. Please try again:");
				continue;
			}
			// if the user entry is valid, check it against 
			// the chosen word and change the word accordingly
			char guess = userGuess.toUpperCase().charAt(0);
			if (guessedLetters.contains(guess) || incorrectGuesses.contains(guess)) {
				System.out.println("Already guessed that letter. Please try again:");
				continue;
			}
			guessedLetters.add(guess);
			// Evil part - swap to a new word that maintains 
			// the existing guessed letters and their locations
			chosenWord = evilSwap(displayWord, guess);
			// update the board
			if (chosenWord.contains(guess + "")) {
				// update the displayWord
				for (int i = 0; i < chosenWord.length(); i++) {
					if (guessedLetters.contains(chosenWord.charAt(i))) {
						displayWord = displayWord.substring(0, i) + 
								chosenWord.charAt(i) + displayWord.substring(i + 1);
					}
				}
			} else {
				incorrectGuesses.add(guess);
			}
		}
		// Game is over - determine whether the user won or lost
		String chosenWordStr = "";
		for (char c : chosenWord.toCharArray()) {
			chosenWordStr = chosenWordStr.concat(c + "");
		}
		if (displayWord.contains("_")) {
			System.out.println("You lost... the word was '" + chosenWordStr + "'");
		} else {
			System.out.println("You win! Word was '" + chosenWordStr + "'");
		}
		scnr.close();
	}

	public static ArrayList<String> getWords() {
		return words;
	}
	
	public static void setWords(ArrayList<String> w) {
		words = w;
	}

	public static ArrayList<String> getSameLenWords() {
		return sameLenWords;
	}
	
	public static void setSameLenWords(ArrayList<String> arr) {
		sameLenWords = arr;
	}
	
	public static void setIncorrectGuesses(ArrayList<Character> i) {
		incorrectGuesses = i;
	}
}
