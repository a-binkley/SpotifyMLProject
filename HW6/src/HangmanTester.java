import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.io.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HangmanTester {
	ArrayList<String> testWords;
	ByteArrayOutputStream outContent;

	@BeforeEach
	void setUp() {
		Hangman.makeWordList("src/short_list.txt");
		testWords = Hangman.getWords();
		outContent = new ByteArrayOutputStream();
	}
	
	@Test
	void testDisplayState() {
		setUp();
		System.setOut(new PrintStream(outContent));
		String expectedOutput = "Guess a letter\r\nD _ G \r\n";
		Hangman.displayState("D_G", "DOG");
		assertEquals(expectedOutput, outContent.toString());
	}
	
	@Test
	void testDisplayStateWithIncorrect() {
		setUp();
		ArrayList<Character> incorrect = new ArrayList<>();
		incorrect.add('Q');
		incorrect.add('Z');
		Hangman.setIncorrectGuesses(incorrect);
		System.setOut(new PrintStream(outContent));
		String expectedOutput = "Guess a letter\r\nD _ G \r\nIncorrect guesses:\n[Q, Z]\r\n";
		Hangman.displayState("D_G", "DOG");
		assertEquals(expectedOutput, outContent.toString());
	}
	
	@Test
	void testMakeHash() {
		setUp();
		ArrayList<String> sameLenWordsTest = new ArrayList<>();
		File f = new File("src/short_list.txt");
		Scanner s = null;
		try {
			s = new Scanner(f);
			while (s.hasNextLine()) {
				String word = s.nextLine();
				if (word.length() == 4) {
					sameLenWordsTest.add(word.toUpperCase());
				}
			}
		} catch (FileNotFoundException e) {
			
		}
		Hangman.setSameLenWords(sameLenWordsTest);
		HashMap<String, ArrayList<String>> testHash = new HashMap<>();
		String oldWord = "_I__";
		ArrayList<String> values = new ArrayList<>();
		testHash.put("EI__", new ArrayList<String>());
		testHash.put("_IE_", new ArrayList<String>());
		values.add("BITE");
		values.add("FIVE");
		testHash.put("_I_E", values);
		values = new ArrayList<>();
		values.add("MINT");
		values.add("WITH");
		values.add("WILD");
		testHash.put("_I__", values);
		assertEquals(testHash, Hangman.makeHash(oldWord, 'E'));
	}
	
	@Test
	void testSetNewWords() {
		setUp();
		ArrayList<String> sameLenWordsTest = new ArrayList<>();
		File f = new File("src/short_list.txt");
		Scanner s = null;
		try {
			s = new Scanner(f);
			while (s.hasNextLine()) {
				String word = s.nextLine();
				if (word.length() == 5) {
					sameLenWordsTest.add(word.toUpperCase());
				}
			}
		} catch (FileNotFoundException e) {
			
		}
		Hangman.setSameLenWords(sameLenWordsTest);
		HashMap<String, ArrayList<String>> testHash = new HashMap<>();
		// TODO: determine how to hit coverage of lines 123-130
		String oldWord = "B____";
		ArrayList<String> values = new ArrayList<>();
		values.add("BOOKS");
		testHash.put("BO___", values);
		values.add("BOOKS");
		values.add("BLOOD");
		values.add("BROOD");
		testHash.put("B_O__", values);
		values.remove("BOOKS");
		testHash.put("B__O_", values);
		testHash.put("B___O", new ArrayList<>());
		assertEquals(testHash.get("B_O__"), Hangman.setNewWords(oldWord, 
				Hangman.makeHash(oldWord, 'O')));
	}
	
	@Test
	void testSetNewWordsBadInput() {
		setUp();
		
	}
	
	@Test
	void testEvilSwap() {
		setUp();
		assertEquals("MINT", Hangman.evilSwap("_I__", 'E'));
	}
	
	@Test
	void testEvilSwapBadInput() {
		setUp();
		
	}
	
	@Test
	void testPlay() {
		setUp();
		
	}
}
