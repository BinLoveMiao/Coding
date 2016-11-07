package interview.leetcode.backtracking;

/**
 * LeetCode 10. Regular Expression Matching (https://leetcode.com/problems/regular-expression-matching/) <br>
 * 
 * Implement regular expression matching with support for '.' and '*'. <br>
 * '.' Matches any single character. <br>
 * '*' Matches zero or more of the preceding element.<br>

 * @author Robeen Lai
 * @email longbin.lai@gmail.com
 *
 */
public class RegularExpressionMatch{
	
	private int[][] cache;
	
	public static void main(String[] args){
		String[][] cases = {
				{"ab*", "ab****"}, {"ab", "ab****"}, {"ab", "ab**"}, 
				{"ab***", "ab***"}, {"a", "*"}, {"aa", "a*"}, {"", "a*"}, {"", "a*b*c*"},
				{"", "***"}, {"ac", "a."}, {"ac", ".a"}, {"ac", "ab*c"},
				{"abcdefg", ".*efg"}, {"aa", "a"}, {"aa", "."},
				{"ab", "ab*e*c*"}, {"abbbbbbb", "ab*e*c*"}, {"aab", "c*a*b*"},
				{"aab", "c*a*b"},{"aab", "c*a*aab"},{"aab", "c*d*aab"},
				{"aaa", "ab*a"}, {"aa", "b*a"}
				};
		boolean[] results = {true,false,false,true,false,true,true,true,false,true,
				false,true,true,false,false,true,true,true,true,true,true,false,false};
		RegularExpressionMatch re = new RegularExpressionMatch();
		int i = 0;
		for(String[] test : cases){
			assert(re.isMatch(test[0], test[1]) == results[i++]);
		}
		
	}
	
	public boolean isMatch(String s, String p) {
        if(s == null || p == null) return false;
        if(p.length() == 0) return s.length() == 0;
        cache = new int[s.length()][p.length()];
        return match(s.toCharArray(), p.toCharArray(), 0, 0);
    }
    
	/**
	 * Matching pattern p to an empty string. 
	 * The only matched pattern is like a*b*c*e*f*. As a result, 
	 * (1) the length must be even number;
	 * (2) we must have a "*" every other character.
	 * @param p
	 * @param pi
	 * @return
	 */
    private boolean matchEmpty(char[] p, int pi){
        if(pi == p.length) return true;
        if((p.length - pi & 1) != 0) return false;
        int i = pi + 1;
        while(i < p.length){
        	if(p[i] != '*') return false;
        	i += 2;
        }
        return true;
    }
    
    private boolean match(char[] s, char[] p, int si, int pi){
        if(si == s.length) return matchEmpty(p, pi);
        if(pi == p.length) return false;
        if(cache[si][pi] != 0) return cache[si][pi] == 1;
        if(pi + 1 != p.length && p[pi + 1] == '*'){
        	char cur = p[pi];
        	int i = si;
        	if(cur != '.'){
        		//if(s[i] != cur) return dfs(s, p, i, pi + 2);
        		// I should have found the problem via manual test
        		while(i <= s.length){ 
        			// Equivalent to x* matching to no x
        			if(match(s, p, i, pi + 2)) {
        				cache[si][pi] = 1;
        				return true;
        			}
        			// Equivalent to x* matching to several xs (if possible)
        			if(i >= s.length || s[i] != cur) break;
        			++i;
        		}
        	}
        	else{
        		// Equivalent to .* matching to any sequence.
        		while(i <= s.length){
        			if(match(s, p, i, pi + 2)) {
        				cache[si][pi] = 1;
        				return true;
        			}
        			++i;
        		}
        	}
        }
        else{
        	// Equivalent to x = x or x = .
        	if(s[si] == p[pi] || p[pi] == '.') 
        		return match(s, p, si + 1, pi + 1);
        }
        cache[si][pi] = -1;
        return false;
    }
}