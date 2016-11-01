package interview.leetcode.design;

public class StreamingMedian {
	
	private static class Node{
		int val;
		Node left;
		Node right;
		
		public Node(int v){
			this.val = v;
		}
	}
	
	private int numLeft = 0;
	private int numRight = 0;
	
	private Node root;
	
	public static void main(String[] args){
		StreamingMedian stream = new StreamingMedian();
		stream.addNum(1);	
		stream.addNum(2);
		System.out.println(stream.getMedian());
		
		stream.addNum(3);
		System.out.println(stream.getMedian());
		
		stream.addNum(2);
		System.out.println(stream.getMedian());
		
		stream.addNum(8);
		System.out.println(stream.getMedian());
	}
	
	/**
	 * Insert the value to the given node
	 * @param val
	 * @param node
	 */
	private void insert(int val, Node node){
		if(node == null) return;
		Node parent = null;
		Node cur = node;
		while(cur != null){
			parent = cur;
			if(val <= cur.val) cur = cur.left;
			else cur = cur.right;
		}
		if(val <= parent.val) parent.left = new Node(val);
		else parent.right = new Node(val);
	}
	
	/**
	 * Insert the value to the root
	 * @param val
	 */
	public void insert(int val){
		if(root == null) root = new Node(val);
		else insert(val, root);
	}
	
	/**
	 * Find the most left node on the sub-tree rooted on root.
	 * We will also return the parent of the most left tree. 
	 * Further, if the tree does not have any left child on current branch
	 * parent will be null and root will be return as the left-most child
	 * @param root
	 * @return
	 */
	public Node[] findLeftMostChild(Node root){
		Node[] nodes = new Node[2];
		nodes[1] = root;
		while(nodes[1].left != null){
			nodes[0] = nodes[1];
			nodes[1] = nodes[1].left;
		}
		return nodes;
	}
	
	public Node[] findRightMostChild(Node root){
		Node[] nodes = new Node[2];
		nodes[1] = root;
		while(nodes[1].right != null){
			nodes[0] = nodes[1];
			nodes[1] = nodes[1].right;
		}
		return nodes;
	}
	
	private void makeBalance(){
		boolean left = (numLeft > numRight);
		Node[] nodes = null;
		if(left){
			nodes = findRightMostChild(root.left);
			Node parent = nodes[0] == null ? root : nodes[0];
			// remove the node from the left branch
			// replace it with the root node
			// and insert the root into the right branch
			if(nodes[1] == parent.left) parent.left = nodes[1].left;
			else parent.right = nodes[1].left;
			--numLeft;
			++numRight;
		}
		else{
			nodes = findLeftMostChild(root.right);
			Node parent = nodes[0] == null ? root : nodes[0];
			if(nodes[1] == parent.left) parent.left = nodes[1].right;
			else parent.right = nodes[1].right;
			++numLeft;
			--numRight;
		}
		int temp = root.val;
		root.val = nodes[1].val;
		if(nodes[1].val != temp){
			insert(temp);
		}
		else{
			if(!left){
				if(root.left == null) root.left = new Node(temp);
				else insert(temp, root.left);
			}
			else{
				if(root.right == null) root.right = new Node(temp);
				else insert(temp, root.right);
			}
		}
		
	}
	
	public void addNum(int val){
		if(root != null){
			if(val <= root.val) ++numLeft;
			else ++numRight;
		}
		insert(val);
		if(Math.abs(numLeft - numRight) > 1) makeBalance();
	}
	
	public float getMedian(){
		if(root == null) return 0f;
		if(numLeft == numRight) return (float) root.val;
		else if(numLeft == numRight + 1){
			Node[] nodes = findRightMostChild(root.left);
			return (root.val + nodes[1].val) / 2f;
		}
		else{
			Node[] nodes = findLeftMostChild(root.right);
			return (root.val + nodes[1].val) / 2f;
		}
	}
	
	
}