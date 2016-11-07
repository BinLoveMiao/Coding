package interview.leetcode.hashtable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 451. Sort Characters By Frequency (https://leetcode.com/problems/sort-characters-by-frequency/) <br>
 * 
 * Given a string, sort it in decreasing order based on the non-increasing frequency of characters.
 * 
 * @author Robin Lai
 * @email longbin.lai@gmail.com
 *
 */
public class CharacterFrequencySort {
	
	public String frequencySort(String s) {
        if(s == null || s.length() == 0) return s;
        int len = s.length();
        Map<Character, Integer> freq = new HashMap<Character, Integer>();
        // We create an invertedList on the frequency
        // This is very typical bucket sort
        ArrayList<Character>[] invertList = new ArrayList[len];
        for(int i = 0; i < len; ++i){
            char c = s.charAt(i);
            if(!freq.containsKey(c)) freq.put(c, 1);
            else freq.put(c, freq.get(c) + 1);
        }
        for(char c : freq.keySet()){
            int f = freq.get(c);
            if(invertList[f - 1] == null) invertList[f - 1] = new ArrayList<Character>();
            invertList[f - 1].add(c);
        }
        char[] result = new char[len];
        int cnt = 0;
        for(int i = len - 1; i >= 0; --i){
            if(invertList[i] == null) continue;
            for(char c : invertList[i]){
                int f = i + 1;
                while((f--) > 0){
                    result[cnt++] = c;
                }
            }
        }
        return String.valueOf(result, 0, len);
    }
	
}