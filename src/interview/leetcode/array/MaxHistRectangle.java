package interview.leetcode.array;

import java.util.Stack;

/**
 * This is a classic interviewing question: Maximal rectangle in the histogram. <br>
 * 
 * Here is an explanation: https://www.youtube.com/watch?v=VNbkzsnllsU <br>
 * 
 * Suppose each histogram has width 1. Given an array that suggests the heights of 
 * the histogram, we want to compute the maximal rectangle (area) contained in these
 * histograms. <br>
 * 
 * 
 * 
 * @author Robin
 * @email longbin.lai@gmail.com
 *
 */
public class MaxHistRectangle{
	
	public static void main(String[] args){
		int[] hist = {2, 0, 2, 1, 1};
		System.out.println(maxHist(hist));
	}
	
	public static int maxHist(int[] hist){
		
		if(hist == null || hist.length == 0) return 0;
		Stack<Integer> position = new Stack<Integer>();
		Stack<Integer> height = new Stack<Integer>();
		
		int result = 0;
		
		for(int i = 0; i < hist.length; ++i){
			int cur = hist[i];
			if(height.isEmpty() || cur > height.peek()){
				position.push(i);
				height.push(cur);
			}
			else{
				int p = 0;
				while(!height.isEmpty() && cur < height.peek()){
					p = position.pop();
					result = Math.max(result, (i - p) * height.pop());
				}
				if(height.isEmpty()){
					height.push(cur);
					position.push(0);
				}
				else if(cur > height.peek()){
					height.push(cur);
					position.push(p);
				}
			}
		}
		
		while(!height.isEmpty() && !position.isEmpty()){
			result = Math.max(result, (hist.length - position.pop()) * height.pop());
		}
		return result;
		
	}
}