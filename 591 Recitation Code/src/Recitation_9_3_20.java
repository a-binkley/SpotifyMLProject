import java.util.*;

public class Recitation_9_3_20 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
	    //write code here
	    System.out.println("Enter the year you were born:");
	    int birthYear = s.nextInt();
	    System.out.println("You are " + (2020 - birthYear) + " years old");
	    s.close();
	}
}
