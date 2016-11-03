package interview.leetcode.array;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode maximum subarray problem.
 * Including: <br>
 * 
 * 53. Maximum Subarray. <br>
 * (https://leetcode.com/problems/maximum-subarray/) <br>
 * 
 * 325. Maximum Size Subarray Sum Equals k. <br>
 * (https://leetcode.com/problems/maximum-size-subarray-sum-equals-k/) <br>
 * 
 * @author Robeen Lai
 * @email longbin.lai@gmail.com
 *
 */
public class MaxSubarray{
	
	/**
	 * 53. Maximum Subarray.
	 * Find the contiguous subarray within an array (containing at least one number) 
	 * which has the largest sum. <br>
	 * 
	 * Here, I still use the idea of computing rangeSum and utilizing range sum to
	 * compute sum of any subarray. As we want to find the maximum sum, so we record
	 * the min range sum till now, and use rangeSum[i] - min to compute the possible
	 * maximum sum in an array. <br>
	 * 
	 * Another typicla solution for this question is Divide-and-Conquer. 
	 * The idea is: given an array, find the medium index, and compute the maximum sum
	 * on the left, and on the right recursively, and compare them with the sum that is
	 * partly on the left and partly on the right. This gives a O(n logn) complexity.
	 * 
	 * @param nums
	 * @return
	 */
	public int sumToMax(int[] nums){
		if(nums == null || nums.length == 0) return 0;
        int len = nums.length;
        long[] sum = new long[len];
        sum[0] = (long) nums[0];
        for(int i = 1; i < len; ++i){
            sum[i] = sum[i - 1] + nums[i];
        }
        long res = Integer.MIN_VALUE, min = 0;
        for(int i = 0; i < len; ++i){
            res = Math.max(res, sum[i] - min);
            if(min > sum[i]) min = sum[i];
        }
        return (int) res;
	}
	
	/**
	 * 325. Maximum Size Subarray Sum Equals k.
	 * This is a typical subarray sum problem. 
	 * We can first compute the rangeSum, where rangeSum[i] = sum_{i = 0 ... i - 1} nums[i].
	 * Further, we have sum of subarray between i and j (both inclusive) as: <br>
	 * rangeSum[j + 1] - rangeSum[i]. <br>
	 * 
	 * Since then for every i, we approach to find the largest j such that: <br>
	 * rangeSum[j] = k + rangeSum[i]. <br>
	 * 
	 * As a result, we just index every rangeSum using a hashtable. <br>
	 * 
	 * One more thing, if all elements in the array are positive, we can apply the two-pointer solution.
	 * 
	 * @param nums
	 * @param k
	 * @return
	 */
	public int sumToK(int[] nums, int k){
		if(nums == null || nums.length == 0) return 0;
        int len = nums.length;
        int[] sum = new int[len + 1];
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        
        for(int i = 0; i < len; ++i){
            sum[i + 1] = sum[i] + nums[i];
            map.put(sum[i + 1], i); // It is ok there are multiple sum[i]
        }
        
        int result = 0;
        for(int i = 0; i < len + 1; ++i){
            if(map.containsKey(sum[i] + k)){
                result = Math.max(result, map.get(sum[i] + k) - i + 1);
            }
        }
        return result;
	}
}