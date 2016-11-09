package interview.leetcode.tree;

import basic.structure.TreeNode;

/**
 * Relate to a set of pathsum problem in leetcode. Includes:
 * 
 * 112. Path Sum (https://leetcode.com/problems/path-sum/) <br>
 * Given a binary tree and a sum, determine if the tree has a root-to-leaf path 
 * such that adding up all the values along the path equals the given sum. <br><br>
 * 
 * 113. Path Sum II (https://leetcode.com/problems/path-sum-ii/) <br>
 * Given a binary tree and a sum, find all root-to-leaf paths where each path's sum 
 * equals the given sum. <br><br>
 * 
 * 124. Binary Tree Maximum Path Sum (https://leetcode.com/problems/binary-tree-maximum-path-sum/) <br>
 * Given a binary tree, find the maximum path sum. <br>
 * For this problem, a path is defined as any sequence of nodes from 
 * some starting node to any node in the tree along the parent-child connections. 
 * The path must contain at least one node and does not need to go through the root. <br> <br>
 * 
 * 
 * 437. Path Sum III (https://leetcode.com/problems/path-sum-iii/) <br>
 * You are given a binary tree in which each node contains an integer value. 
 * Find the number of paths that sum to a given value. <br><br>
 * 
 * @author Robin Lai
 * @email longbin.lai@gmail.com
 *
 */
public class PathSum {
	
	private int result;
	
	/**
	 * LeetCode 112.
	 * Note that this is root-leaf path, and the condition for a leaf node is:
	 * leaf.left == null && leaf.right == null.
	 * @param root
	 * @param sum
	 * @return
	 */
	public boolean hasPathSum(TreeNode root, int sum){
		if(root == null) {
			return false;
		}
		if(root.left == null && root.right == null) return sum == root.val;
		return hasPathSum(root.left, sum - root.val) || 
				hasPathSum(root.right, sum - root.val);
	}
	
	/**
	 * LeetCode 124.
	 * 
	 * @param root
	 * @return
	 */
	public int maxPathSum(TreeNode root){
		if(root == null) return 0;
	    result = Integer.MIN_VALUE;
	    maxPathSumFromNode(root);
	    return result;
	}
	
	/**
	 * This function compute the maxPathSum from the given node.
	 * While doing so, we can also compute the max path sum cross current node, 
	 * and we use a global variable to track the maximum value of all such values.
	 * @param node
	 * @return The max pathSum started from current node.
	 */
	private int maxPathSumFromNode(TreeNode node){
		if(node == null) return 0;
		int left = node.left == null ? 0 : maxPathSumFromNode(node.left);
		int right = node.right == null ? 0 : maxPathSumFromNode(node.right);
		result = Math.max(result, node.val + Math.max(0, left) + Math.max(0, right));
		int val = node.val;
		if(left > right && left > 0) val += left;
		else if(right > left && right > 0) val += right;
		return val;
	}

	
	/**
	 * LeetCode 437.
	 * A typical divide-and-conquer manner. <br>
	 * 
	 * # paths = # paths on left tree + # paths on right tree + # paths that include current node
	 * @param root
	 * @param target
	 * @return
	 */
	public int pathSumIII(TreeNode root, int target) {
        if(root == null) return 0;
        return pathSumIII(root.left, target) + pathSumIII(root.right, target) +
            pathSumInclude(root, target);
    }
    
    private int pathSumInclude(TreeNode root, int target){
        if(root == null) return 0;
        int count = 0;
        if(target == root.val) count = 1;
        return count + pathSumInclude(root.left, target - root.val) + 
                    pathSumInclude(root.right, target - root.val);
    }
}