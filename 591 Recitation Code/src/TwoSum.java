import java.util.*;

public class TwoSum {

	public static void main(String[] args) {
		int[] nums = {7, 8, 9, 32, -1, -71};
		int target = 40;
		
		for (int i = 0; i < nums.length-1; i++) {
			for (int j = i+1; j < nums.length; j++) {
				if (nums[i]+nums[j] == target) {
					System.out.println("" + target + " = " + nums[i] + " + " + nums[j]);
				}
			}
		}
	}
}
