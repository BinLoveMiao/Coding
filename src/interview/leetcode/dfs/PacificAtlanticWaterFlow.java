package interview.leetcode.dfs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class PacificAtlanticWaterFlow {

  public static void main(String[] args) {
    int[][] matrix =
        {{1, 2, 2, 3, 5}, {3, 2, 3, 4, 4}, {2, 4, 5, 3, 1}, {6, 7, 1, 4, 5}, {5, 1, 1, 2, 4}};
    // int[][] matrix = {{1, 2}};
    PacificAtlanticWaterFlow waterFlow = new PacificAtlanticWaterFlow();
    List<int[]> results = waterFlow.pacificAtlantic(matrix);
    for (int[] result : results) {
      System.out.println(result[0] + ", " + result[1]);
    }
  }
  
  public List<int[]> pacificAtlantic(int[][] matrix) {
    List<int[]> results = new ArrayList<int[]>();
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
        return results;
    }
    int nRow = matrix.length, nCol = matrix[0].length;
    boolean[][][] canFlow = new boolean[nRow][nCol][2];
    int[] pos = {nRow - 1, 0};
    while (pos[0] != 0 || pos[1] != nCol) {
      if (!canFlow[pos[0]][pos[1]][0]) {
        bfs(matrix, pos[0], pos[1], canFlow, true, results);
      }
      if (pos[0] != 0) {
        --pos[0];
      } else {
        ++pos[1];
      }
    }
    pos[0] = nRow - 1;
    pos[1] = 0;
    while (pos[0] != -1 || pos[1] != nCol - 1) {
      if (!canFlow[pos[0]][pos[1]][1]) {
        bfs(matrix, pos[0], pos[1], canFlow, false, results);
      }
      if (pos[1] != nCol - 1) {
        ++pos[1];
      } else {
        --pos[0];
      }
    }
    return results;
}

  public void bfs(int[][] matrix, int row, int col, boolean[][][] canFlow, boolean isPacific,
      List<int[]> results) {
    Queue<int[]> queue = new LinkedList<int[]>();
    queue.offer(new int[] {row, col});
    int[][] offsets = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    boolean[][] visited = new boolean[matrix.length][matrix[0].length];
    while (!queue.isEmpty()) {
      int[] pos = queue.poll();
      visited[pos[0]][pos[1]] = true;
      if (isPacific) {
        canFlow[pos[0]][pos[1]][0] = true;
      } else {
        if (!canFlow[pos[0]][pos[1]][1]) {
          canFlow[pos[0]][pos[1]][1] = true;
          if (canFlow[pos[0]][pos[1]][0]) {
            results.add(pos);
          }
        }
      }
      for (int[] offset : offsets) {
        int[] nbr = {pos[0] + offset[0], pos[1] + offset[1]};
        if (isValidNeighbor(matrix, pos, nbr) && !visited[nbr[0]][nbr[1]]) {
          queue.offer(nbr);
        }
      }
    }
  }

  private boolean isValidNeighbor(int[][] matrix, int[] pos, int[] nbr) {
    if (nbr[0] < 0 || nbr[0] >= matrix.length || nbr[1] < 0 || nbr[1] >= matrix[0].length) {
      return false;
    }
    return matrix[pos[0]][pos[1]] <= matrix[nbr[0]][nbr[1]];
  }
}
