import java.util.*;

public class TwoSum {

	public static void main(String[] args) {
		int[] nums = {2,2,2,2};
		int sum = 0;
		
		for (int i = 0; i < 256; i++) {
			nums[3] = 2 + (i % 4);
			nums[2] = 2 + (i/4 % 4);
			nums[1] = 2 + (i/16 % 4);
			nums[0] = 2 + (i/64);
			sum += (nums[0]*10 + nums[1])*(nums[2]*10 + nums[3]);
		}
		System.out.println(sum / 130);
	}
}
