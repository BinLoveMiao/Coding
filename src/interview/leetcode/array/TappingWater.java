package interview.leetcode.array;

/**
 * Still a buggy version. 
 * TODO Fix specific bug.
 * Case: [6, 3, 2, 5, 4, 7]
 * @author robeen
 *
 */
public class TappingWater {

	public static void main(String[] args) {
		int[] height = { 6, 3, 2, 5, 4, 7 };
		System.out.println(trap(height));
	}

	public static int trap(int[] height) {
		if(height == null || height.length <= 2) return 0;
		int len = height.length;
		int[] prevMax = new int[len];
		int[] postMax = new int[len];
		for(int i = 1; i < len; ++i){
			prevMax[i] = Math.max(prevMax[i - 1], height[i- 1]);
		}
		for(int i = len - 2; i >= 0; --i){
			postMax[i] = Math.max(postMax[i + 1], height[i + 1]);
		}
		int result = 0;
		for(int i = 1; i < len - 1; ++i){
			int max = Math.min(prevMax[i], postMax[i]);
			if(max > height[i]) result += (max - height[i]);
		}
		return result;
	}
}