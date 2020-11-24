import java.util.*;
import java.io.*;

public class WordWithScores implements Comparable<Object> {
	private String word;
	private int score;

	public WordWithScores(String word, int score) {
		this.word = word;
		this.score = score;
	}

	@Override
	public String toString() {
		return word;
	}

	@Override
	public boolean equals(Object obj) {
		WordWithScores wws = (WordWithScores) obj;
		return (word == wws.word && score == wws.score);
	}

	@Override
	public int compareTo(Object o) {
		WordWithScores other = (WordWithScores) o;
		if (score != other.score) {
			return score - other.score;
		} else {
			return (word.length() - other.word.length());
		}
	}

	/**
	 * Computes a metric called getSimilarity in which an average is assigned to two
	 * strings being compared to each other. The comparisons is looking at how many
	 * characters are shared at the same index from left to right and vice versa.
	 *
	 */
	public static double getSimilarity(String string1, String string2) {
		char[] firstWord = string1.toCharArray();
		char[] secondWord = string2.toCharArray();
		double leftSimilarity = 0;
		double rightSimilarity = 0;

		if (firstWord.length < secondWord.length) {
			for (int i = 0; i < firstWord.length; i++) {
				if (firstWord[i] == secondWord[i]) {
					leftSimilarity += 1;
				}
			}
		} else {
			for (int i = 0; i < secondWord.length; i++) {
				if (secondWord[i] == firstWord[i]) {
					leftSimilarity += 1;
				}
			}
		}
		// checks score moving backwards
		if (firstWord.length < secondWord.length) {
			int lengthDiff = secondWord.length - firstWord.length;
			for (int i = firstWord.length - 1; i >= 0; i--) {
				if (firstWord[i] == secondWord[i + lengthDiff]) {
					rightSimilarity += 1;
				}
			}
		} else {
			for (int i = secondWord.length - 1; i >= 0; i--) {
				int lengthDiff = firstWord.length - secondWord.length;
				if (secondWord[i] == firstWord[i + lengthDiff]) {
					rightSimilarity += 1;
				}
			}
		}
		return (leftSimilarity + rightSimilarity) / 2.0;
	}

	public static void main(String[] args) {
		ArrayList<WordWithScores> scoredWords = new ArrayList<>();
		File f = new File("src/short_list.txt");
		Scanner s = null;
		try {
			s = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (s.hasNextLine()) {
			String readLine = s.nextLine();
			WordWithScores w = new WordWithScores(readLine, (int) getSimilarity("dwdggr", readLine));
			scoredWords.add(w);
		}

		Collections.sort(scoredWords);

		for (int i = 0; i < 10; i++) {
			System.out.println(scoredWords.get(i));
		}
	}
}
