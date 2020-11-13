import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.io.*;

import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Dictates the order of the tests
													  // so changes made in later tests
													  // don't mess up earlier ones
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
	@Order(1)
	void testDisplayState() {
		setUp();
		System.setOut(new PrintStream(outContent));
		String expectedOutput = "Guess a letter\r\nD _ G \r\n";
		Hangman.displayState("D_G", "DOG");
		assertEquals(expectedOutput, outContent.toString());
	}
	
	@Test
	@Order(2)
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
	@Order(3)
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
		assertEquals(true, sameLenWordsTest.contains("BITE"));
		assertEquals(true, sameLenWordsTest.contains("FIVE"));
		assertEquals(true, sameLenWordsTest.contains("MINT"));
		assertEquals(true, sameLenWordsTest.contains("WILD"));
		assertEquals(true, sameLenWordsTest.contains("WITH"));
		assertEquals(true, sameLenWordsTest.contains(newWord));
	}
	
	@Test
	@Order(4)
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
		assertEquals(true, sameLenWordsTest.contains("MINT"));
		assertEquals(true, sameLenWordsTest.contains("WILD"));
		assertEquals(true, sameLenWordsTest.contains("WITH"));
		assertEquals(true, sameLenWordsTest.contains(newWord));
	}
	
	@Test
	@Order(5)
	void testPlayWin() {
		setUp();
		InputStream sysInBackup = System.in;
		ByteArrayInputStream in = new ByteArrayInputStream("_ ff i e w l e d t h".getBytes());
		System.setIn(in);
		ArrayList<String> testWords4 = new ArrayList<>();
		for (String word : testWords) {
			if (word.length() == 4) {
				testWords4.add(word);
			}
		}
		Hangman.setWords(testWords4); //Allows more controlled testing
		Hangman.play();
		
		System.setIn(sysInBackup);
	}
	
	@Test
	@Order(6)
	void testPlayLose() {
		setUp();
		InputStream sysInBackup = System.in;
		ByteArrayInputStream in = new ByteArrayInputStream("_ ff i e w l e d q z v x r p h".getBytes());
		System.setIn(in);
		ArrayList<String> testWords4 = new ArrayList<>();
		for (String word : testWords) {
			if (word.length() == 4) {
				testWords4.add(word);
			}
		}
		Hangman.setWords(testWords4); //Allows more controlled testing
		Hangman.play();
		
		System.setIn(sysInBackup);
	}
}
