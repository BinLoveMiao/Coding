package interview.leetcode;

import java.util.HashMap;
import java.util.Map;

public class MaxProducts {
	
	public static void main(String[] args){
		String[] test = {"abcw","baz","foo","bar","xtfn","abcdef"};
		MaxProducts mp = new MaxProducts();
		System.out.println(mp.maxProduct(test));
	}
	
	public int maxProduct(String[] words) {
        if(words == null || words.length == 0) return 0;
        int len = words.length;
        Map<String, Integer> map = new HashMap<String, Integer>();
        for(String s : words){
            int bit = 0;
            for(int i = 0; i < s.length(); ++i){
                bit |= (1 << char2Int(s.charAt(i)));
            }
            map.put(s, bit);
        }
        
        int p = partition(words, 0, len - 1, 1, map);
        return maxProduct(words, 0, p - 1, p, len - 1, 2, map);
    }
    
    private int maxProduct(String[] words, int start1, int end1, int start2, 
        int end2, int i, Map<String, Integer> map){
        if(i == (1 << 26)) return 0;
        if(end1 < start1 || end2 < start2) return 0;
        if(end1 == start1 && end2 == start2) 
            return words[start1].length() * words[start2].length();
        int p1 = partition(words, start1, end1, i, map);
        int p2 = partition(words, start2, end2, i, map);

        int ret = Math.max(
            maxProduct(words, start1, p1 - 1, p2, end2, i << 1, map),
            maxProduct(words, p1, end1, start2, p2 - 1, i << 1, map)
        );
        
        ret = Math.max(ret, 
        	maxProduct(words, start1, p1 - 1, start2, p2 - 1, i << 1, map)	
        );
        
        return ret;
    }
    
    private int partition(String[] words, int start, int end, int i,
        Map<String, Integer> map){
        while(start <= end){
            if((map.get(words[start]) & i) != 0) swap(words, start, end--);
            else ++start;
        }
        return start;
    }
    
    private void swap(String[] words, int i, int j){
        String temp = words[i];
        words[i] = words[j];
        words[j] = temp;
    }
    
    private int char2Int(char c){
        return (int) (c - 'a');
    }
	
}