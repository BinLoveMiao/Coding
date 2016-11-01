package interview.leetcode.tree;

import basic.structure.TreeNode;

public class BuildTree {
	
	public static void main(String[] args){
		int[] inorder = {4,3,5,2,6,1,7};
		int[] preorder = {1,2,3,4,5,6,7};
		
		TreeNode root = buildTree(preorder, inorder);
		if(root != null){
			System.out.println("PreOrder:");
			preOrderTraversal(root);
			System.out.println();
			
			System.out.println("InOrder:");
			inOrderTraversal(root);
			System.out.println();
		}
	}
	
	public static TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder == null || inorder == null ||
        		preorder.length == 0 || inorder.length == 0 ||
        		preorder.length != inorder.length){
        	return null;
        }
     
        TreeNode root = new TreeNode(preorder[0]);
        search(preorder, 0, preorder.length - 1, 
        		inorder, 0, inorder.length - 1, root, true);
        return root;
        
    }
	
	
	public static void search(int[] preorder, int i, int j,
			int[] inorder, int s, int k, TreeNode root, boolean isLeft){
		assert(i < preorder.length && j < preorder.length
				&& s < inorder.length && k < inorder.length);
		TreeNode newNode = root.val == preorder[i] ? root : new TreeNode(preorder[i]);
		if(root.val != preorder[i]){
			if(isLeft){
				root.left = newNode;
			}
			else {
				root.right = newNode;
			}
		}
		if(i == j){
			return;
		}
		int idx = s;
		for(; idx <= k; ++idx){
			if(inorder[idx] == preorder[i]){
				break;
			}
		}
		int numLeft = idx - s;
		int numRight = k - idx;
		if(numLeft != 0){
			search(preorder, i + 1, i + numLeft, 
				inorder, s, s + numLeft - 1,
				newNode, true);
		}
		
		if(numRight != 0){
			search(preorder, i + numLeft + 1, j,
				inorder, s + numLeft + 1, k,
				newNode, false);
		}
		
	}
	
	public static void preOrderTraversal(TreeNode root){
		System.out.print(root.val + ",");
		if(root.left != null){
			preOrderTraversal(root.left);
		}
		if(root.right != null){
			preOrderTraversal(root.right);
		}
	}
	
	public static void inOrderTraversal(TreeNode root){	
		if(root.left != null){
			inOrderTraversal(root.left);
		}
		System.out.print(root.val + ",");
		if(root.right != null){
			inOrderTraversal(root.right);
		}
	}
	
}