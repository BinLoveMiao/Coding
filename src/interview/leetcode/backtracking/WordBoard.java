package interview.leetcode.backtracking;

import data.structure.SimpleTrie;

/**
 * This question describes as follows:
 * Given a dictionary of words, and a matrix of N * M with each grid containing
 * a character. From each grid of the matrix, we can create words by traveling
 *  (up, down, left, right) in the matrix if the index is within the boundary. 
 *  The problem asks how many words from the dictionary that we can create.
 * @author robeen
 *
 */
public class WordBoard{
	
	private static int count;
	
	public static void main(String[] args){
		char[][] matrix = {{'a','b', 'c'},{'a','c', 'b'}};
		String[] dict = {"abc", "aa", "ba", "a"};
		System.out.println(findWords(matrix, dict));
	}
	
	public static int findWords(char[][] matrix, String[] dict){
		count = 0;
		if(matrix == null || matrix[0].length == 0 ||
				dict == null || dict.length == 0) return 0;
		
		int len = 0;
		for(int i = 0; i < dict.length; ++i){
			len = Math.max(len, dict[i].length());
		}
		SimpleTrie trie = buildTrie(dict);
		int nRow = matrix.length;
		int nCol = matrix[0].length;
		
		char[] cur = new char[len];
		boolean[][] visited = new boolean[nRow][nCol];
		
		for(int i = 0; i < nRow; ++i){
			for(int j = 0; j < nCol; ++j){
				if(trie.root().contains(matrix[i][j])){
					cur[0] = matrix[i][j];
					search(i, j, matrix, visited, trie.root().getNode(matrix[i][j]), 1, cur);
				}
			}
		}
		
		return count;
	}
	
	/**
	 * The dfs search from a grid i and j. 
	 * We will include this grid in the cur value, and proceed the search
	 * in the next level.
	 * @param i
	 * @param j
	 * @param matrix
	 * @param visited The visited matrix
	 * @param node The trie node that 
	 * @param cur
	 * @param level
	 */
	private static void search(int i, int j, char[][] matrix, boolean[][] visited,
			SimpleTrie.Node node, int level, char[] cur){
		visited[i][j] = true;
		if(node.isWord()) {
			System.out.println("Find word: " + String.valueOf(cur, 0, level));
			++count;
		}

		int nRow = matrix.length, nCol = matrix[0].length;
		
		if(i - 1 >= 0 && !visited[i - 1][j] && node.contains(matrix[i - 1][j])){
			cur[level] = matrix[i - 1][j];
			search(i - 1, j, matrix, visited, node.getNode(matrix[i - 1][j]), 
					level + 1, cur);
		}
		if(j - 1 >= 0 && !visited[i][j - 1] && node.contains(matrix[i][j - 1])){
			cur[level] = matrix[i][j - 1];
			search(i, j - 1, matrix, visited, node.getNode(matrix[i][j - 1]), 
					level + 1, cur);
		}
		if(i + 1 < nRow && !visited[i + 1][j] && node.contains(matrix[i + 1][j])){
			cur[level] = matrix[i + 1][j];
			search(i + 1, j, matrix, visited, node.getNode(matrix[i + 1][j]), 
					level + 1, cur);
		}
		if(j + 1 < nCol && !visited[i][j + 1] && node.contains(matrix[i][j + 1])){
			cur[level] = matrix[i][j + 1];
			search(i, j + 1, matrix, visited, node.getNode(matrix[i][j + 1]), 
					level + 1, cur);
		}
		visited[i][j] = false; //backtracking
	}
	
	private static SimpleTrie buildTrie(String[] dict){
		SimpleTrie trie = new SimpleTrie();
		for(String s : dict){
			trie.addword(s);
		}
		return trie;
	}
	
}