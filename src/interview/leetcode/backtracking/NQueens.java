package interview.leetcode.backtracking;

/**
 * LeetCode 51 & 52 NQueens.<br>
 * https://leetcode.com/problems/n-queens/
 * 
 * 
 * @author Robeen Lai
 * @email longbin.lai@gmail.com
 *
 */
public class NQueens{
	private int num = 0;
    
    private boolean canTakeCol(int i, int j, int[] col){
        if(i == 0) return true;
        for(int k = 0; k < i; ++k){
            if(col[k] == j || (i - k) == Math.abs(j - col[k])) return false;
        }
        return true;
    }
    
    public int totalNQueens(int n) {
        if(n <= 0) return 0;
        num = 0;
        queen(0, n, new int[n]);
        return num;
    }
    
    private void queen(int row, int n, int[] col){
        if(row == n) {
            ++num;
            return;
        }
        for(int j = 0; j < n; ++j){
            if(canTakeCol(row, j, col)){
                col[row] = j;
                queen(row + 1, n, col);
            }
        }
    }
}