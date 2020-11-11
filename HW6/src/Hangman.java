import java.util.*;

public class Hangman {
	private static ArrayList<String> words = new ArrayList<>();
	private static ArrayList<String> sameLenWords = new ArrayList<>();
	private static ArrayList<Character> guessedLetters = new ArrayList<>();
	private static ArrayList<Character> incorrectGuesses = new ArrayList<>();

	/**
	 * Populates the set of words Hangman can choose from
	 */
	public static void makeWordList() {
		DictionaryReader dr = new DictionaryReader("src/engDictionary.txt");
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
	 * Helper function for evilSwap() that handles the details of creating the
	 * HashMap
	 * 
	 * @param oldWord
	 * @param letter
	 * @return
	 */
	public static HashMap<String, ArrayList<String>> makeHash(String oldWord, char letter) {
		HashMap<String, ArrayList<String>> wordFamilies = new HashMap<>();
		// populate the HashMap
		for (int i = 0; i <= oldWord.length(); i++) {
			String key = oldWord;
			ArrayList<String> values = new ArrayList<>();
			if (i == oldWord.length()) {
				// add in the "_____" case (key = oldWord)
				for (String word : sameLenWords) {
					if (!word.contains(letter + "")) {
						boolean isCandidate = true;
						for (int j = 0; j < word.length(); j++) {
							if (key.charAt(j) != '_') {
								if (key.charAt(j) != word.charAt(j)) {
									isCandidate = false;
									break;
								}
							}
						}
						if (isCandidate) {
							values.add(word);
						}
					}
				}
			} else {
				// populate the non-empty keys
				if (key.charAt(i) == '_') {
					key = key.substring(0, i) + letter + key.substring(i + 1, oldWord.length());
					if (!wordFamilies.containsKey(key)) {
						for (String word : sameLenWords) {
							if (word.charAt(i) == letter) {
								boolean isCandidate = true;
								for (int j = 0; j < word.length(); j++) {
									if (key.charAt(j) != '_') {
										if (key.charAt(j) != word.charAt(j)) {
											isCandidate = false;
											break;
										}
									}
								}
								if (isCandidate) {
									values.add(word);
								}
							}
						}
					} else {
						continue;
					}
				}
			}
			wordFamilies.put(key, values);
		}
		return wordFamilies;
	}

	/**
	 * Helper function for evilSwap() that handles the selection and cleaning of the largest word family
	 * @param oldWord
	 * @param families
	 * @return
	 */
	public static ArrayList<String> setNewWords(String oldWord, HashMap<String, ArrayList<String>> families) {
		// select the largest word family and set that to be the sameLenWords
		int largest = 0;
		String family = "";
		for (String k : families.keySet()) {
			if (families.get(k).size() > largest) {
				largest = families.get(k).size();
				family = k;
			}
		}
		sameLenWords = families.get(family);
		// remove any illegal words (extra instances of previously guessed letters, mismatching characters)
		ArrayList<String> updatedWords = new ArrayList<>();
		for (String word : sameLenWords) {
			boolean isCandidate = true;
			for (int i = 0; i < word.length(); i++) {
				if (oldWord.charAt(i) == '_') {
					if (oldWord.contains(word.charAt(i) + "")) {
						isCandidate = false;
						break;
					}
				} else {
					if (oldWord.charAt(i) != word.charAt(i)) {
						isCandidate = false;
						break;
					}
				}
			}
			if (isCandidate) {
				updatedWords.add(word);
			}
		}
		return updatedWords;
	}

	/**
	 * Returns a word with the same pattern of _'s and guessed letters as the input,
	 * taken from sameLenWords (the list of possible words)
	 * 
	 * @param oldWord
	 * @return
	 */
	public static String evilSwap(String oldWord, char newestLetter) {
		HashMap<String, ArrayList<String>> wordFamilies = makeHash(oldWord, newestLetter);
		sameLenWords = setNewWords(oldWord, wordFamilies);
		return sameLenWords.get(0);
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
			// if the user entry is valid, check it against the word and change the word accordingly
			char guess = userGuess.toUpperCase().charAt(0);
			if (guessedLetters.contains(guess) || incorrectGuesses.contains(guess)) {
				System.out.println("Already guessed that letter. Please try again:");
				continue;
			}
			// Evil part - swap to a new word that maintains the existing guessed letters and their locations
			chosenWord = evilSwap(displayWord, guess);
			// update the board
			if (chosenWord.contains(guess + "")) {
				guessedLetters.add(guess);
				// update the displayWord
				for (int i = 0; i < chosenWord.length(); i++) {
					if (guessedLetters.contains(chosenWord.charAt(i))) {
						displayWord = displayWord.substring(0, i) + chosenWord.charAt(i) + displayWord.substring(i + 1);
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
}
