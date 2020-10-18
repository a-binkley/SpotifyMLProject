import java.util.*;

public class Dice {

	public int roll() {
		Random r = new Random();
		int out = r.nextInt(6);
		return out+1;
	}
	
	public static void main(String[] args) {
		Dice d1 = new Dice();
		Dice d2 = new Dice();
		
		int even_count = 0;
		int sum_count = 0;
		int roll_count = 100000;
		
		for (int i = 0; i < roll_count; i++) {
			int d1_val = d1.roll();
			int d2_val = d2.roll();
			sum_count += d1_val + d2_val;
			if ((d1_val + d2_val) % 2 == 0) {
				even_count++;
			}
		}
		System.out.println("Probability of rolling even sum is approximately " + (1.0 * even_count / roll_count));
		System.out.println("Average sum value is approximately " + (1.0 * sum_count / roll_count));
	}
}
