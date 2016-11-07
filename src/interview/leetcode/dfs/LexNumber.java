package interview.leetcode.dfs;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 386. Lexicographical Numbers. <br>
 * https://leetcode.com/problems/lexicographical-numbers/ <br>
 * This question requires to sort 1 - n (n is given) via lexicographical order.
 * Naively, we can sort 1 - n with customized comparator, and this takes
 * O(n logn) complexity. However, we can do much better. Observe that we always
 * start with a 1 (0 is it is not the first digit), then 2, 3, etc., and this follows
 * a typical dfs traversal routine. As we need only traverse each number once, the complexity
 * is O(n). Below is how we implement the idea. 
 * 
 * @author Robeen Lai
 * @email longbin.lai@gmail.com
 *
 */
public class LexNumber{
	public List<Integer> lexicalOrder(int n) {
        List<Integer> result = new ArrayList<Integer>();
        if(n <= 0) return result;
        for(int i = 1; i < 10; ++i){
            if(i > n) return result;
            dfs(i, n, result);
        }
        return result;
    }
    
    private void dfs(int val, int n, List<Integer> list){
        if(val > n) return; // This line is not necessary
        list.add(val);
        for(int i = 0; i < 10; ++i){
            int nextVal = val * 10 + i;
            if(nextVal > n) return;
            dfs(nextVal, n, list);
        }
    }
}