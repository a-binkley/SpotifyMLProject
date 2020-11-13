import java.util.*;
import java.io.*;

public class DictionaryReader {
	private ArrayList<String> wordList = new ArrayList<>();
	
	public DictionaryReader(String wordFile) {
		File f = new File(wordFile);
		Scanner scnr;
		try {
			scnr = new Scanner(f);
			while (scnr.hasNextLine()) {
				String nextWord = scnr.nextLine(); //TODO: change back to any length
				if (nextWord.length() == 8) {
					wordList.add(nextWord.toUpperCase());
				}
			}
			scnr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Invalid dictionary file provided.");
		}
	}
	
	public ArrayList<String> getWordList() {
		return wordList;
	}
}
