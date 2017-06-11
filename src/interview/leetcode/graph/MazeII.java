package interview.leetcode.graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>
 * LeetCode 505 The Maze II.
 * </p>
 * 
 * <p>
 * There is a ball in a maze with empty spaces and walls. The ball can go through empty spaces by
 * rolling up, down, left or right, but it won't stop rolling until hitting a wall. When the ball
 * stops, it could choose the next direction.
 * </p>
 * 
 * <p>
 * Given the ball's start position, the destination and the maze, find the shortest distance for the
 * ball to stop at the destination. The distance is defined by the number of empty spaces traveled
 * by the ball from the start position (excluded) to the destination (included). If the ball cannot
 * stop at the destination, return -1.
 * </p>
 * 
 * <p>
 * The maze is represented by a binary 2D array. 1 means the wall and 0 means the empty space. You
 * may assume that the borders of the maze are all walls. The start and destination coordinates are
 * represented by row and column indexes.
 * </p>
 * 
 * 
 * Example 1 <br>
 * <br>
 * 
 * Input 1: a maze represented by a 2D array <br>
 * 
 * 0 0 1 0 0 <br>
 * 0 0 0 0 0 <br>
 * 0 0 0 1 0 <br>
 * 1 1 0 1 1 <br>
 * 0 0 0 0 0 <br>
 * 
 * Input 2: start coordinate (rowStart, colStart) = (0, 4) <br>
 * 
 * Input 3: destination coordinate (rowDest, colDest) = (4, 4) <br>
 * 
 * <p>
 * Output: 12 Explanation: One shortest way is : left -> down -> left -> down -> right -> down ->
 * right. The total distance is 1 + 1 + 3 + 1 + 2 + 2 + 2 = 12.
 * </p>
 * 
 * @author robin
 *
 */
public class MazeII {

  final static int[][] offsets = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

  public int shortestDistance(int[][] maze, int[] start, int[] destination) {
    if (maze == null || maze.length == 0 || maze[0].length == 0) {
      return -1;
    }
    return bfs(maze, start, destination);
  }

  private int bfs(int[][] matrix, int[] start, int[] dest) {

    int nRows = matrix.length, nCols = matrix[0].length;
    int[][] distance = new int[nRows][nCols];
    for (int i = 0; i < nRows; ++i) {
      Arrays.fill(distance[i], Integer.MAX_VALUE);
    }
    distance[start[0]][start[1]] = 0;
    Queue<int[]> queue = new LinkedList<int[]>();
    queue.offer(start);
    while (!queue.isEmpty()) {
      int[] curr = queue.poll();
      for (int[] offset : offsets) {
        int[] nextPos = {curr[0], curr[1]};
        int dist = 0;
        while (nextPos[0] >= 0 && nextPos[0] < nRows && nextPos[1] >= 0 && nextPos[1] < nCols
            && matrix[nextPos[0]][nextPos[1]] == 0) {
          nextPos[0] += offset[0];
          nextPos[1] += offset[1];
          ++dist;
        }
        nextPos[0] -= offset[0];
        nextPos[1] -= offset[1];
        --dist;
        if (dist >= distance[dest[0]][dest[1]]) {
          continue;
        }
        if (nextPos[0] != curr[0] || nextPos[1] != curr[1]) {
          // This is the key of this bfs search.
          // Instead of using a priority queue, We should add the grid back to the queue
          // when its distance becomes smaller.
          if (dist + distance[curr[0]][curr[1]] < distance[nextPos[0]][nextPos[1]]) {
            distance[nextPos[0]][nextPos[1]] = dist + distance[curr[0]][curr[1]];
            queue.offer(nextPos);
          }
        }
      }
    }
    return distance[dest[0]][dest[1]] == Integer.MAX_VALUE ? -1 : distance[dest[0]][dest[1]];
  }
}
