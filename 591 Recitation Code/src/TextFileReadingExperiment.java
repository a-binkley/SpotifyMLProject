import java.util.*;
import java.io.*;

public class TextFileReadingExperiment {
	public static void main(String[] args) {
		File f = new File("testing.txt");
		try {
			Scanner fileScanner = new Scanner(f);
			int lineCount = 1;
			while (fileScanner.hasNextLine()) {
				System.out.println("(" + lineCount + ") " + fileScanner.nextLine());
				lineCount++;
			}
			fileScanner.close();
		} catch (FileNotFoundException e) {
			// Prints something if the given exception was thrown
			System.out.println("Invalid text file name");
			e.printStackTrace();
		}
	}

}
