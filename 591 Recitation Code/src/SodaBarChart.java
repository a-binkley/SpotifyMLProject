import java.util.*;
import java.io.*;

public class SodaBarChart {

	public static void main(String[] args) {
		File f = new File("soda_sugar.csv");
		Scanner fileScanner;
		try {
			fileScanner = new Scanner(f);
		} catch (FileNotFoundException e) {
			System.out.println("Invalid input file");
			e.printStackTrace();
			return;
		}
		fileScanner.nextLine();
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> sugar = new ArrayList<String>();
		while (fileScanner.hasNextLine()) {
			String nextLine = fileScanner.nextLine();
			names.add(nextLine.split(",")[0]);
			String sugarStars = "";
			for (int i = 0; i < Integer.parseInt(nextLine.split(",")[1]); i++) {
				sugarStars += "*";
			}
			sugar.add(sugarStars);
		}
		for (int i = 0; i < names.size(); i++) {
			System.out.println(names.get(i) + " " + sugar.get(i));
		}
		fileScanner.close();
	}
}
