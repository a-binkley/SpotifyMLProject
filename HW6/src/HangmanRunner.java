
public class HangmanRunner {
	public static void main(String[] args) {
		Hangman.makeWordList("src/engDictionary.txt");
		Hangman.play();
	}
}
