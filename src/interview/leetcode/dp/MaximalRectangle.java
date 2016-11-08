package interview.leetcode.dp;

/**
 * LeetCode 85. Maximal Rectangle. (https://leetcode.com/problems/maximal-rectangle/) <br>
 * 
 * Given a 2D binary matrix filled with 0's and 1's, 
 * find the largest rectangle containing only 1's and return its area. <br>
 * 
 * This question can actually be reduced to the problem:
 * Maximal rectangle in a histogram. <br>
 * 
 * The idea is to scan row by row. For each row, we compute an array height[n], where <br>
 * height[j] is the number of continues ones in col j starting with row[i][j]. <br><br>
 * 
 * For example, <br>
 * 
 * 1 0 1 1 0   <- height = [1, 0, 1, 1, 0] <br>
 * 1 1 0 1 0   <- height = [2, 1, 1, 2, 0] <br> <br>
 *
 * Then we compute the maximal rectangle by treating height[] in each row as the histogram. <br>
 * We further define two arrays: left[n], right[n] <br>
 *  left[j] = k, such that, for any k <= s < j, height[s] >= height[j] and k = 0 or height[k - 1] < height[j] <br>
 * right[j] = k, such that, for any j < s <= k, height[s] >= height[j] and k = n - 1 or height[k + 1] < height[j] <br>
 * 
 * Therefore, suppose we are in row i, and we have computed height[n], left[n] and right[n].
 * Then the area of the rectangle surrounded by current cell (i, j) is: height[j] * (right[j] - left[j] + 1)
 * 
 * @author Robin Lai
 * @email longbin.lai@gmail.com
 *
 */
public class MaximalRectangle {
	
	public static void main(String[] args){
		String[] test = {"0001010","0100000","0101001","0011001","1111110","1001011","0100101","1101110","1010101","1110000"};
		//String[] test = {"10100", "10111", "11111", "10010"};
		//String[] test = {"111"};
		char[][] matrix = new char[test.length][];
		for(int i = 0; i < test.length; ++i){
			matrix[i] = test[i].toCharArray();
		}
		MaximalRectangle mr = new MaximalRectangle();
		System.out.println(mr.maximalRectangle(matrix));
	}
	
	public int maximalRectangle(char[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        int m = matrix.length, n = matrix[0].length;
        int[] height = new int[n];
        int[] left = new int[n];
        int[] right = new int[n];
        int result = 0;
        for(int i = 0; i < m; ++i){
        	for(int j = 0; j < n; ++j){
        		if(matrix[i][j] == '0') height[j] = 0;
        		else height[j] += 1;
        	}
        	//result = Math.max(result, maxHist(height));
        	for(int j = 0; j < n; ++j){
        		int k = j - 1;
        		while(k >= 0 && height[k] >= height[j]){
        			k = left[k] - 1;
        		}
        		left[j] = k + 1;
        	}
        	for(int j = n - 1; j >= 0; --j){
        		int k = j + 1;
        		while(k < n && height[k] >= height[j]){
        			k = right[k] + 1;
        		}
        		right[j] = k - 1; 
        	}
        	for(int j = 0; j < n; ++j){
        		result = Math.max(result, height[j] * (right[j] - left[j] + 1));
        	}
        }
        return result;
    }
}