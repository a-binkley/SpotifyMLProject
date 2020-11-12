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
	void testEvilSwapFirstRound() {
		setUp();
		ArrayList<String> sameLenWordsTest = new ArrayList<>();
		for (String w : Hangman.getWords()) {
			if (w.length() == 4) {
				sameLenWordsTest.add(w);
			}
		}
		Hangman.setSameLenWords(sameLenWordsTest);
		String newWord = Hangman.evilSwap("____", 'E');
		sameLenWordsTest = Hangman.getSameLenWords();
		assertEquals(false, sameLenWordsTest.contains("BITE"));
		assertEquals(true, sameLenWordsTest.contains("COIL"));
		assertEquals(false, sameLenWordsTest.contains("FIVE"));
		assertEquals(true, sameLenWordsTest.contains("FOUR"));
		assertEquals(true, sameLenWordsTest.contains("JUMP"));
		assertEquals(false, sameLenWordsTest.contains("MAKE"));
		assertEquals(true, sameLenWordsTest.contains("MINT"));
		assertEquals(true, sameLenWordsTest.contains("ROIL"));
		assertEquals(false, sameLenWordsTest.contains("SEXY"));
		assertEquals(true, sameLenWordsTest.contains("TOLD"));
		assertEquals(true, sameLenWordsTest.contains("WILD"));
		assertEquals(true, sameLenWordsTest.contains("WITH"));
		assertEquals(true, sameLenWordsTest.contains(newWord));
	}
	
	@Test
	void testEvilSwapLaterRound() {
		setUp();
		ArrayList<String> sameLenWordsTest = new ArrayList<>();
		for (String w : Hangman.getWords()) {
			if (w.length() == 4) {
				sameLenWordsTest.add(w);
			}
		}
		Hangman.setSameLenWords(sameLenWordsTest);
		String newWord = Hangman.evilSwap("_I__", 'E');
		sameLenWordsTest = Hangman.getSameLenWords();
		assertEquals(false, sameLenWordsTest.contains("BITE"));
		assertEquals(false, sameLenWordsTest.contains("COIL"));
		assertEquals(false, sameLenWordsTest.contains("FIVE"));
		assertEquals(false, sameLenWordsTest.contains("FOUR"));
		assertEquals(false, sameLenWordsTest.contains("JUMP"));
		assertEquals(false, sameLenWordsTest.contains("MAKE"));
		assertEquals(true, sameLenWordsTest.contains("MINT"));
		assertEquals(false, sameLenWordsTest.contains("ROIL"));
		assertEquals(false, sameLenWordsTest.contains("SEXY"));
		assertEquals(false, sameLenWordsTest.contains("TOLD"));
		assertEquals(true, sameLenWordsTest.contains("WILD"));
		assertEquals(true, sameLenWordsTest.contains("WITH"));
		assertEquals(true, sameLenWordsTest.contains(newWord));
	}
	
	@Test
	void testPlay() {
		setUp();
		
	}
}
