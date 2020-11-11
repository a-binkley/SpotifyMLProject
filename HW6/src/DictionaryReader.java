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
				wordList.add(scnr.nextLine().toUpperCase());
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
