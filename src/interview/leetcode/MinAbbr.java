package interview.leetcode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class MinAbbr{
	
	public static void main(String[] args){
		MinAbbr m = new MinAbbr();
		String s = "leetcode";
		String abbr = "a" + String.valueOf(s.length() - 3) + "sf";
		String[] dict = {"lyftcode","leetcold","litecode","lietcode","leetccod","lyftcold"};
		System.out.println(m.minAbbreviation(s, dict));
	}
	
	public String minAbbreviation(String target, String[] dictionary) {
        if(target == null || target.length() == 0) return "";
        if(dictionary == null || dictionary.length == 0) return String.valueOf(target.length());
        return bfs(target, dictionary);
    }
	/**
	 * Probably a bfs search would be preferred
	 * @param abbr
	 * @param bit
	 * @param s
	 * @param visited
	 * @param dict
	 * @return
	 */
	public String bfs(String s, String[] dict){
		//if(!contains(abbr, dict)) return abbr;
		//visited.add(bit);
		Set<Integer> visited = new HashSet<Integer>();
		int len = s.length();
		int bit = (1 << len) - 1; // Start from 111111...1
		// a bfs setting
		Queue<Integer> queue = new LinkedList<Integer>();
		visited.add(bit);
		queue.add(bit);
		while(!queue.isEmpty()){
			bit = queue.poll();
			System.out.println("cur: " + Integer.toBinaryString(bit));
			if(bit == 0x7c){
				// 01111101
				System.out.println(1);
			}
			String abbr = abbr(s, bit);
			// While find the first one, it is guaranteed to be the minimum
			if(!contains(abbr, dict)){
				return abbr;
			}
			int tmp = bit;
			while(tmp != 0){
				int one = (tmp & (-tmp));
				bit ^= one;
				if(!visited.contains(bit)) {
					System.out.println("add: " + Integer.toBinaryString(bit));
					queue.offer(bit);
					visited.add(bit);
				}
				bit |= one;
				tmp -= one;
			}
		}
		
		// Means cannot find any abbr that is not contained by the dict
		return "";
	}
	
	public boolean contains(String abbr, String[] dict){
		for(String w : dict){
			if(match(w, abbr)) return true;
		}
		return false;
	}
	
	public boolean match(String word, String abbr){
		// We point a pointer to the current index of abbr and word
		int i = 0, j = 0;
		
		while(i < abbr.length() && j < word.length()){
			int num = 0;
			while(i < abbr.length() && Character.isDigit(abbr.charAt(i))){
				num = 10 * num + digit(abbr.charAt(i++));
			}
			j += num;
			if(i >= abbr.length() || j >= word.length()) break;
			if(word.charAt(j) != abbr.charAt(i)) break;
			++i; ++j;
		}
		return i == (abbr.length()) && (j == word.length());
	}
	
	public int digit(char c){
		return (int) (c - '0');
	}
	
	public String abbr(String s, int bit){
		int len = s.length();
		StringBuilder sb = new StringBuilder();
		int count = 0;
		for(int i = 0; i < len; ++i){
			if((bit & (1 << i)) == 0){ // expend the bit
				if(count != 0){
					sb.append(count);
					count = 0;
				}
				sb.append(s.charAt(i));
			}
			else ++count;
		}
		if(count != 0) sb.append(count);
		return sb.toString();
	}
	
}