import java.util.*;

public class CheckInLines {
	ArrayList<Integer> counters;
	
	public CheckInLines() {
		counters = new ArrayList<>();
	}
	
	public int getShortest() {
		int shortest_line = Integer.MAX_VALUE;
		for (int element : counters) {
			if (element < shortest_line) {
				shortest_line = element;
			}
		}
		return counters.indexOf(shortest_line);
	}
	
	public double getAverage() {
		double avg = 0.0;
		for (int element : counters) {
			avg += element;
		}
		return avg / counters.size();
	}
	
	public void closeCounter() {
		counters.remove(getShortest());
	}
	
	public void addPassengers(int num) {
		for (int i = 0; i < num; i++) {
			counters.set(getShortest(), counters.get(getShortest())+1);
		}
	}
	
	public static void main(String[] args) {
		CheckInLines test0 = new CheckInLines();
		test0.counters.add(2);
		test0.counters.add(5);
		test0.counters.add(8);
		test0.counters.add(3);
		test0.counters.add(10);
		System.out.println(test0.getShortest());
		System.out.println(test0.getAverage());
		test0.addPassengers(12);
		System.out.println(test0.counters);
	}

}
