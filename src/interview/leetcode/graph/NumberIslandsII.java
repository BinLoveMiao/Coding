package interview.leetcode.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * LeetCode 305. Number of Islands II (https://leetcode.com/problems/number-of-islands-ii/) <br> <br>
 * 
 * A 2d grid map of m rows and n columns is initially filled with water. <br>
 * We may perform an addLand operation which turns the water at position (row, col) into a land. <br>
 * Given a list of positions to operate, count the number of islands after each addLand operation. <br>
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. <br>
 * You may assume all four edges of the grid are all surrounded by water. <br> <br>
 * 
 * Naturally the application of disjoint set.
 * 
 * @author Robin
 * @email longbin.lai@gmail.com
 *
 */
public class NumberIslandsII {
	
	private int numIslands = 0;
	
	public static void main(String[] args){
		int m = 3, n = 3;
		int[][] positions = {{0,0}, {0,1}, {1,2}, {2,1}};
		
		List<Integer> result = new NumberIslandsII().numIslands2(m, n, positions);
		for(int r : result){
			System.out.print(r + ",");
		}
	}
    
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        this.numIslands = 0;
        List<Integer> result = new ArrayList<Integer>();
        if(m <= 0 || n <= 0 || positions == null || positions.length == 0) return result;
        boolean[][] matrix = new boolean[m][n];
        int[][][] parent = new int[m][n][2];
        for(int[] pos : positions){
            addIsland(pos[0], pos[1], parent, matrix, m, n, result);
        }
        return result;
    }
    
    private void addIsland(int x, int y, int[][][] parent, 
        boolean[][] matrix, int m, int n, List<Integer> result){
        if(matrix[x][y]) return;
        matrix[x][y] = true;
        int[] p = {-1, -1};
        int numP = 0;
        // Process four potential neighbors
        if(x != 0 && matrix[x - 1][y]) numP = neighbor(x - 1, y, p, numP, parent);  
        if(x != m - 1 && matrix[x + 1][y]) numP = neighbor(x + 1, y, p, numP, parent);    
        if(y != 0 && matrix[x][y - 1]) numP = neighbor(x, y - 1, p, numP, parent);      
        if(y != n - 1 && matrix[x][y + 1]) numP = neighbor(x, y + 1, p, numP, parent);
        
        if(numP == 0) {
            parent[x][y][0] = x;
            parent[x][y][1] = y;
        }
        else {
        	parent[x][y][0] = p[0];
        	parent[x][y][1] = p[1];
        }
        this.numIslands += (1 - numP);
        result.add(this.numIslands);
    }
    
    private int neighbor(int x, int y, int[] p, int numP, int[][][] parent){
        int[] p1 = findParent(x, y, parent);
        if(p[0] == -1 && p[1] == -1){
            ++numP;
            p[0] = p1[0];
            p[1] = p1[1];
        }
        else if(p[0] != p1[0] || p[1] != p1[1]){
            ++numP;
            merge(p, p1, parent);
        }
        return numP;
    }
    
    // Merge parent p2 to p1
    private void merge(int[] p1, int[] p2, int[][][] parent){
        parent[p2[0]][p2[1]][0] = p1[0];
        parent[p2[0]][p2[1]][1] = p1[1];
    }
    
    // Find the parent of current location
    private int[] findParent(int x, int y, int[][][] parent){
    	int[] p = {x, y};
        int[] pp = parent[x][y];
        while(p[0] != pp[0] || p[1] != pp[1]) {
            p = pp;
            pp = parent[p[0]][p[1]];
        }
        parent[x][y][0] = p[0];
        parent[x][y][1] = p[1];
        return p;
    }
	
}