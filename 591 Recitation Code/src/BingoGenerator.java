import java.util.*;

public class BingoGenerator {

	public static void main(String[] args) {
		ArrayList<Integer> bingo_spots = new ArrayList<Integer>();
		for (int i = 0; i < 25; i++) {
			bingo_spots.add(i, i+1);
		}
		Collections.shuffle(bingo_spots);
		System.out.println("Bingo board: " + bingo_spots.toString());
	}

}
