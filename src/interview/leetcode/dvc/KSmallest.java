package interview.leetcode.dvc;
import java.util.Arrays;
import java.util.Random;

public class KSmallest{
	
	public static void main(String[] args){
	
	}

	
	
	public int findKthSmallest(int[] nums, int k){
		assert(nums != null && k > 0 && k <= nums.length);
		return findK(nums, 0, nums.length, k);
	}
	
	/**
	 * Find Kth smallest in the array nums indiced between from (included) and to (excluded)
	 * @param nums The array
	 * @param from The from index (included)
	 * @param to The to index (excluded)
	 * @param k 
	 * @return The kth smallest value's index
	 */
	public static int findK(int[] nums, int from, int to, int k){
		assert(to - from >= k);
		if(k == 1){
			return min(nums, from, to);
		}
		if(k == to - from){
			return max(nums, from, to);
		}
		
		
		Random rand = new Random(System.currentTimeMillis());
		int idx = from + rand.nextInt(to - from);
		int cand = nums[idx];
		swap(nums, idx, to - 1);
		int l = from, r = to - 2;
		while(l <= r){
			if(nums[l] >= cand){
				// Swap the l and r
				swap(nums, l, r);
				--r;
			}
			if(nums[l] < cand) ++l;
		}
		//if(nums[l] < cand) ++l;
		swap(nums, l, to - 1);
		assert(nums[l] == cand);
		r = l + 1 - from;
		if(k == r){
			return l;
		}
		else if(k > r){
			l = findK(nums, l + 1, to, k - r);
		}
		else { // k < r
			l = findK(nums, from, l, k);
		}	
		return l;
	}
	
	/**
	 * Return the index of min value in the array ranged from (included) and to (excluded)
	 * @param nums
	 * @param from
	 * @param to
	 * @return
	 */
	private static int min(int[] nums, int from, int to){
		int idx = from;
		int piv = Integer.MAX_VALUE;
		for(int i = from; i < to; ++i){
			if(nums[i] < piv){
				piv = nums[i];
				idx = i;
			}
		}
		return idx;
	}
	
	private static int max(int[] nums, int from, int to){
		int idx = from;
		int piv = Integer.MIN_VALUE;
		for(int i = from; i < to; ++i){
			if(nums[i] > piv){
				piv = nums[i];
				idx = i;
			}
		}
		return idx;
	}
	
	private static void swap(int[] nums, int i, int j){
		int temp = nums[i];
		nums[i] = nums[j];
		nums[j] = temp;
	}
	
}