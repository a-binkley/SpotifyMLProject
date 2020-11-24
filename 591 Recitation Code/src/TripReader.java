import java.util.*;
import java.io.*;

public class TripReader {
	public static void main(String[] args) {
		HashMap<String, ArrayList<Integer>> trips = new HashMap<>();
		File f = new File("src/trips.csv");
		Scanner s = null;
		try {
			s = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		s.nextLine(); // gets rid of the column names
		trips.put("<1 hr", new ArrayList<>());
		trips.put("1-2 hrs", new ArrayList<>());
		trips.put("2-3 hrs", new ArrayList<>());
		trips.put(">3 hrs", new ArrayList<>());
		
		while (s.hasNextLine()) {
			String[] entry = s.nextLine().split(",");
			int tripID = Integer.parseInt(entry[0]);
			int duration = Integer.parseInt(entry[1]);
			if (duration < 60) {
				trips.get("<1 hr").add(tripID);
			} else if (duration >= 60 && duration < 120) {
				trips.get("1-2 hrs").add(tripID);
			} else if (duration >= 120 && duration < 180) {
				trips.get("2-3 hrs").add(tripID);
			} else {
				trips.get(">3 hrs").add(tripID);
			}
		}
		for (String key : trips.keySet()) {
			System.out.println(key + " : " + trips.get(key).size());
		}
	}

}
