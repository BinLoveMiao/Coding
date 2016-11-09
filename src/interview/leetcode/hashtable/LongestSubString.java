package interview.leetcode.hashtable;

import java.util.HashMap;
import java.util.Map;

/**
 * There are multiple longest substring problem, which is quite related
 * to dynamic programming, hash table, two pointers etc. 
 * I will aggregate these problem in the same class, and solve each problem
 * individually.
 * @author robeen
 *
 */
public class LongestSubString{
	
	/**
	 * Brain Storm: 
	 * Given an input string s, find the longest substring that contains at 
	 * most k distinct characters. We can almost sense that the keyword "distinct" must be highly related to 
	 * hash table (or set). In addition, for this problem, O(n^2) is trivial, and O(nlogn) seems not on
	 * the right track, as no tree structure can be used (for logn (not always, but often)).
	 * Since then, it is definite to use hash table. What else? 
	 * An O(n) solution, especially in an array, is highly related to Greedy and
	 * two-pointer, which is exactly the idea of solving this question. 
	 * We initialize two pointers i and j, and a hash map M which records
	 * the frequency of characters appearing within i(inclusive) and j (exclusive),
	 * and we want to make sure that within the range [i, j), there are at most k
	 * distinct characters (in the map). Below is the analysis:
	 * (1) map already contains s[j], we just increase the frequency of s[j] and move
	 * to the next character aiming at finding a longer substring. 
	 * (2) map does not hold s[j], we put (s[j] ; 1) to the map, and we must check
	 * whether map.size > k. If not, we can safely move to the next iteration, otherwise,
	 * we must advance i until map.size == k, and while doing so, we decrease the frequency of s[i] in the
	 * hash table, and remove the entry if its frequency becomes zero. 
	 * @param s The input String.
	 * @param k The distinct number.
	 * @return The size of the longest such substring
	 */
	public static int kDistinctCharacters(String s, int k){
		// To rule out some corner case
		if(s == null || s.length() == 0 || k <= 0) return 0;
	    Map<Character, Integer> map = new HashMap<Character, Integer>();
	    int i = 0, j = 0;
	    int result = 0, len = s.length();
	    while(j < len){
		    char c = s.charAt(j);
		    if(map.containsKey(c)) map.put(c, map.get(c) + 1);
		    else {
			    map.put(c, 1);
			    while(map.size() > k) {
	                char c2 = s.charAt(i);
	                int freq = map.get(c2);
	                if((--freq) == 0) map.remove(c2);
	                else map.put(c2, freq);
	                ++i;
                }
            }
            result = Math.max(result, j - i + 1);
            ++j;
        }
        return result;
		
	}
}