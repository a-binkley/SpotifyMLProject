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
		// populate the HashMap keys
		for (int i = 0; i < displayWord.length(); i++) {
			String key = displayWord;
			if (displayWord.charAt(i) == '_') {
				key = key.substring(0, i) + letter + key.substring(i+1);
			} else {
				continue;
			}
			wordFamilies.put(key, new ArrayList<>());
		}
		wordFamilies.put(displayWord, new ArrayList<>());
		// populate the values for each key
		for (String key : wordFamilies.keySet()) {
			ArrayList<String> values = new ArrayList<>();
			for (String word : sameLenWords) {
				boolean isCandidate = true;
				// the all-blank key ("_____")
				if (!key.contains(letter + "")) {
					if (!word.contains(letter + "")) {
						for (int j = 0; j < displayWord.length(); j++) {
							if (displayWord.charAt(j) != '_' && word.charAt(j) != displayWord.charAt(j)) {
								isCandidate = false;
								break;
							}
						}
						if (isCandidate) {
							values.add(word);
						}
					}
					continue;
				}
				// add a word if it matches the key pattern
				for (int i = 0; i < word.length(); i++) {
					if (key.charAt(i) == '_') {
						// invalid if key or guessedLetters contain current letter
						if (displayWord.contains(word.charAt(i) + "") || guessedLetters.contains(word.charAt(i))
								|| incorrectGuesses.contains(word.charAt(i))) {
							isCandidate = false;
							//System.out.println("Key " + key + " Disqualified '" + word + "' due to pre-existing letter");
							break;
						}
					} else {
						if (key.charAt(i) != word.charAt(i)) {
							isCandidate = false;
							//System.out.println("Key " + key + " Disqualified '" + word + "' due to char mismatch");
							break;
						}
					}
				}
				if (isCandidate) {
					values.add(word);
				}
			}
			wordFamilies.replace(key, values);
		}

		int largest = 0;
		int valueListSum = 0;
		String family = "";
		for (String k : wordFamilies.keySet()) {
			System.out.println(k + " (size = " + wordFamilies.get(k).size() + "): " + wordFamilies.get(k)); //TEMP
			valueListSum += wordFamilies.get(k).size();
			if (wordFamilies.get(k).size() > largest) {
				largest = wordFamilies.get(k).size();
				family = k;
				System.out.println("set new family: " + k);
			}
		}
		System.out.println("Word change: " + (valueListSum - sameLenWords.size())); //TEMP
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
			// Evil part - swap to a new word that maintains 
			// the existing guessed letters and their locations
			chosenWord = evilSwap(displayWord, guess);
			System.out.println("New word: " + chosenWord); //TEMP
			// update the board
			if (chosenWord.contains(guess + "")) {
				guessedLetters.add(guess);
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
