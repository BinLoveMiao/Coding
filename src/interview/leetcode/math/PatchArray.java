package interview.leetcode.math;

public class PatchArray{
	
	public static void main(String[] args){
		int[] nums = {1,2,31,33};
		int n = Integer.MAX_VALUE;
		System.out.println(minPatches(nums, n));
		
	}
	
	public static int minPatches(int[] nums, int n) {
		int count = 0;
		long covered = 1;
		int i = 0;
		if (nums.length == 0 || nums[0] != 1)
			count += 1;
		else
			i = 1;
		while (i < nums.length && covered < n) {
			if ((covered + 1) < nums[i]) {
				++count;
				covered += (covered + 1);
			} else {
				covered += (long) nums[i];
				++i;
			}
		}
		while (covered < n) {
			covered += (covered + 1);
			++count;
		}
		return count;

	}
}