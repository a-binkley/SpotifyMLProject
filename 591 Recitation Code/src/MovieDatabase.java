import java.io.*;
import java.util.*;

public class MovieDatabase {
	private HashMap<String, ArrayList<String>> db;

	public void parseAndInsert(String line) {
		// find the movie
		if (line.contains("-")) {
			String[] tokens = line.split(" - ");
			String movie = tokens[0];
			String actorsString = tokens[1];
			// get the actors
			String[] actorsArray = actorsString.split(",");
			// create an arraylist out of them
			ArrayList<String> actors = new ArrayList<String>(Arrays.asList(actorsArray));

			// remove spaces
			for (int i = 0; i < actors.size(); i++) {
				String actor = actors.get(i);
				actors.set(i, actor.trim());
			}

			// now we can set up the map
			db.put(movie, actors);
		}
	}

	public MovieDatabase(String sourceFile) {
		db = new HashMap<>();
		File f = new File(sourceFile);
		try {
			Scanner s = new Scanner(f);
			while (s.hasNextLine()) {
				String line = s.nextLine();
				parseAndInsert(line);
			}
			s.close();
		} catch (FileNotFoundException e) {
			System.out.println("there is no spoon and there is no file");
		}
	}

	/***
	 * Given a movie, return all the actors
	 * 
	 * @param movie
	 * @return
	 */
	ArrayList<String> getCast(String movie) {
		return null;
	}

	/***
	 * Given an actor, return all of his/her movies
	 * 
	 * @param actor
	 * @return
	 */
	public ArrayList<String> getMoviesOf(String actor) {
		ArrayList<String> myMovies = new ArrayList<>();
		
		// ways to loop through a HashMap
		System.out.println("Method 1");
		// 1. Using the keySet in a for each loop
		for (String s : db.keySet()) {
			if (db.get(s).contains(actor)) {
				System.out.println(s);
				myMovies.add(s);
			}
		}

		System.out.println("Method 2");
		// 2. Looping through Entry by Entry
		for (Map.Entry<String, ArrayList<String>> entry : db.entrySet()) {
			if (entry.getValue().contains(actor)) {
				System.out.println(entry.getKey());
				myMovies.add(entry.getKey());
			}
		}

		System.out.println("Method 3");
		// 3. Lambda
		db.forEach((k, v) -> {
			if (v.contains(actor))
				System.out.println(k);
				myMovies.add(k);
		});
		
		return myMovies;
	}

	public void deleteMovie(String s) {
		db.remove(s);
	}

	/**
	 * get a list of all other people that actor has acted with
	 * 
	 * @param string
	 * @return
	 */
	public ArrayList<String> getCoActors(String actor) {
		ArrayList<String> coActors = new ArrayList<>();
		for (String movie : db.keySet()) {
			if (movie.contains(actor)) {
				for (String ca : db.get(movie)) {
					if (!ca.equals(actor) && !coActors.contains(ca)) {
						coActors.add(ca);
					}
				}
			}
		}
		return coActors;
	}
}
