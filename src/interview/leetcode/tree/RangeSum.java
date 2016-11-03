package interview.leetcode.tree;

/**
 * LeetCode 307. Range Sum Query - Mutable <br>
 * https://leetcode.com/problems/range-sum-query-mutable/ <br>
 * 
 * Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.
 * Note that the value of the element can change by calling update(i). <br>
 * 
 * Here we are using the binary-indiced tree. Another typical data structure used in the problem
 * is segment tree. 
 * 
 * @author Robeen Lai
 * @email longbin.lai@gmail.com
 *
 */
public class RangeSum {
    
    int[] tree;
    int[] num;
    
    public static void main(String[] args){
    	int[] nums = {1, 3, 5};
    	RangeSum rs = new RangeSum(nums);
    	System.out.println(rs.sumRange(0, 2));
    	rs.update(1, 2);
    	System.out.println(rs.sumRange(0, 2));
    }

    public RangeSum(int[] array) {
        int len = array.length;
        tree = new int[len + 1];
        num = new int[len + 1];
        for(int i = 0; i < len; ++i){
        	num[i + 1] = array[i];
        	add(i, array[i]);
        }
    }
    
    void add(int i, int val){
    	int idx = i + 1;
    	while(idx < tree.length){
    		tree[idx] += val;
    		idx += (idx & (-idx));
    	}
    }

    void update(int i, int val) {
    	add(i, val - num[i + 1]);
    	num[i + 1] = val;
    }
    
    int getSum(int i){
    	int sum = 0;
    	int idx = i;
    	while(idx > 0){
    		sum += tree[idx];
    		idx -= (idx & (-idx));
    	}
    	return sum;
    }

    public int sumRange(int i, int j) {
    	assert(i <= j && i >= 0 && j < num.length - 1);
    	if(i == j){
    		return num[i + 1];
    	}
    	else {
    		return getSum(j + 1) - getSum(i);
    	}
    }
}