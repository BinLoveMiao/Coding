package data.structure;

/**
 * The Trie implementation: Trie, or so called Prefix Tree, is a string
 * searching index. Here, we are implementing a simpler version that only
 * considers characters. Each Node contains several attributes: Node: Node[]
 * children; int bit; // This is an integer that shows that whether a specific
 * character is a child of current node. The mapping table is as follows a - 0,
 * b - 1, c - 2, …, z - 25, $ - 26 (End of word) boolean contains(char c) int i
 * = (int) (c - ‘a’); return (bit & (1 << i)) != 0;
 * 
 * Node getNode(char c) int i = (int) (c - ‘a’); assert(i >= 0 && i < 26);
 * if(this.contains(c)) return children[i]; return null;
 * 
 * void addNode(char c) int i = (c == ‘$’) ? 26 : (int) (c - ‘a’);
 * if(!contains(c)) this.children[i] = new Node(); this.bit |= (1 << i);
 * 
 * boolean isWord() return (bit & (1 << 26)) != 0;
 **/

public class SimpleTrie {
	
	public static void main(String[] args){
		SimpleTrie trie = new SimpleTrie();
		String[] words = {"ab", "ac", "abc"};
		for(String word : words) trie.addword(word);
		System.out.println(trie.getWord("a"));
		System.out.println(trie.getWord("b"));
		System.out.println(trie.getWord("ab"));
		System.out.println(trie.getWord("ac"));
		System.out.println(trie.getWord("fd"));
		System.out.println(trie.getWord("abcd"));
	}

	public static class Node {
		Node[] children;
		int bit;

		public Node() {
			children = new Node[27];
			bit = 0;
		}
		
		public int index(char c){
			return  (c == '$') ? 26 : (int) (c - 'a');
		}

		public boolean contains(char c) {
			int i = index(c);
			if (i < 0 || i > 26)
				return false;
			return (bit & (1 << i)) != 0;
		}

		public Node getNode(char c) {
			if (!contains(c))
				return null;
			//int i = (c == '$') ? 26 : (int) (c - 'a');
			return children[index(c)];
		}

		public Node addNode(char c) {
			int i = index(c);
			if (!contains(c)) {
				children[i] = new Node();
				bit |= (1 << i);
			}
			return children[i];
		}

		public boolean isWord() {
			return this.contains('$');
		}
	}

	private Node root;

	public SimpleTrie() {
		root = new Node();
	}

	public void addword(String s) {
		if (s == null || s.length() == 0)
			return;
		int len = s.length();
		Node node = root;
		for (int i = 0; i < len; ++i) {
			node = node.addNode(s.charAt(i));
		}
		node.addNode('$');
	}

	public boolean getWord(String s) {
		if (s == null || s.length() == 0)
			return false;
		int len = s.length();
		Node node = root;
		for (int i = 0; i < len; ++i){
			node = node.getNode(s.charAt(i));
			if (node == null) return false;
		}
			
		return node.isWord();
	}
	
	public Node root(){
		return this.root;
	}

}
