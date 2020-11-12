
public class HangmanRunner {
	public static void main(String[] args) {
		Hangman.makeWordList("src/short_list.txt"); //TODO: change back to engDictionary
		Hangman.play();
	}
}
