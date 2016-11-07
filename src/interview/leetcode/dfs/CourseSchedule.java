package interview.leetcode.dfs;

/**
 * LeetCode 210. Course Schedule II (https://leetcode.com/problems/course-schedule-ii/) <br>
 * 
 * This is a typical topological sort
 * @author Robeen Lai
 * @email longbin.lai@gmail.com
 *
 */
public class CourseSchedule {
    
    int numVisited;
    
    public static void main(String[] args){
    	int n = 2;
    	int[][] pre = {{1, 0}};
    	CourseSchedule cs = new CourseSchedule();
    	int[] res = cs.findOrder(n, pre);
    	for(int i = 0; i < res.length; ++i){
    		System.out.print(res[i] + ", ");
    	}
    }
    
    static class Node{
        int id;
        int color;
        
        Node(int _id){
            this.id = _id;
            this.color = 0;
        }
    }
    
    public CourseSchedule(){
        numVisited = 0;
    }
    
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        if(numCourses == 0){
            return new int[0];
        }
        int n = numCourses;
        int[] res = new int[n];
        int[] deg = new int[n];
        int[][] adj = new int[n][n];
        Node[] map = new Node[n];
        
        // The nodes
        for(int i = 0; i < n; ++i){
           map[i] = new Node(i);
        }
        
        // The edges
        for(int[] edge: prerequisites){
            int node1 = edge[0], node2 = edge[1];
            adj[node1][deg[node1]++] = node2;
        }
        
        boolean canFinish = true;
        
        for(int i = 0; i < n; ++i){
            if(map[i].color == 0){
                canFinish = dfs(i, adj, deg, map, res);
                if(!canFinish) break;
            }
        }
        if(canFinish){
            return res;
        }
        else return new int[0];
    }
    
    public boolean dfs(int node, int[][] adj, int[] deg, Node[] map, int[] res){
        boolean canFinish = true;
        Node nbrNode = null;
        map[node].color = 1;
        int nbr = 0;
        for(int i = 0; i < deg[node]; ++i){
            nbr = adj[node][i];
            nbrNode = map[nbr];
            if(nbrNode.color == 0){
                canFinish = dfs(nbr, adj, deg, map, res);
                if(!canFinish) return canFinish;
            }
            else if(nbrNode.color == 1){
                return false;
            }
        }
        map[node].color = 2;
        res[this.numVisited++] = node;
        return canFinish;
    }
}