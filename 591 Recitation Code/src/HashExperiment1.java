import java.util.*;

public class HashExperiment1 {
	public static void main(String[] args) {
		// HashMaps can greatly increase runtime efficiency compared to ArrayLists
		// Declaration uses <Key, Value> where Key must be unique, but Value does not
		// Essentially a Python dict
		HashMap<Integer, String> studentMap = new HashMap<>();
		studentMap.put(1, "abc");
		studentMap.put(25, "def");
		studentMap.put(3, "abc");
		
		System.out.println(studentMap.get(25));
	}
}
