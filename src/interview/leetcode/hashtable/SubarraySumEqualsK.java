package interview.leetcode.hashtable;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 560. Subarray Sum Equals K. <br> <br>
 * 
 * Given an array of integers and an integer k, you need to find the total number of continuous
 * subarrays whose sum equals to k. <br> <br>
 * 
 * Example 1: Input:nums = [1,1,1], k = 2 Output: 2
 * 
 * @author robin
 *
 */
public class SubarraySumEqualsK {

  public static int subarraySum(int[] nums, int k) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    Map<Integer, Integer> sumMap = new HashMap<Integer, Integer>();
    sumMap.put(0, 1);
    int sum = 0;
    int result = 0;
    for (int i = 0; i < nums.length; ++i) {
      sum += nums[i];
      if (sumMap.containsKey(sum - k)) {
        result += sumMap.get(sum - k);
      }
      sumMap.put(sum, sumMap.getOrDefault(sum, 0) + 1);
    }
    return result;
  }

}
