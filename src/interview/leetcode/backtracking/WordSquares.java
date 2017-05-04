package interview.leetcode.backtracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * LeetCode 425. Word Squares. <br>
 * 
 * Given a set of words (without duplicates), find all word squares you can build from them. <br>
 * 
 * A sequence of words forms a valid word square if the kth row and column read the exact same
 * string, where 0 ≤ k < max(numRows, numColumns). <br> <br>
 * 
 * For example, the word sequence ["ball","area","lead","lady"] forms a word square because each
 * word reads the same both horizontally and vertically. <br>
 * 
 * b a l l <br>
 * a r e a <br>
 * l e a d <br>
 * l a d y <br>
 * 
 * <br>
 * Note: <br> 
 * 1. There are at least 1 and at most 1000 words. <br>
 * 2. All words will have the exact same length. <br>
 * 3. Word length is at least 1 and at most 5. <br>
 * 4. Each word contains only lowercase English alphabet a-z. <br>
 * 
 * @author robin
 *
 */
public class WordSquares {
  static class Trie {
    static class TrieNode {
      public TrieNode(char c) {
        character = c;
        // We have 26 characters
        nodes = new TrieNode[27];
        words = new HashSet<String>();
      }

      public TrieNode set(char c, String word) {
        int index = c == '$' ? 26 : (int) (c - 'a');
        if ((prefixBit & (1 << index)) == 0) {
          prefixBit |= (1 << index);
          nodes[index] = new TrieNode(c);
        }
        words.add(word);
        return nodes[index];
      }

      public TrieNode get(char c) {
        int index = c == '$' ? 26 : (int) (c - 'a');
        return nodes[index];
      }

      public List<TrieNode> getNextNodes() {
        int index = 0;
        List<TrieNode> list = new ArrayList<TrieNode>();
        while (index < 26) {
          if ((prefixBit & (1 << index)) != 0) {
            list.add(nodes[index]);
          }
          ++index;
        }
        return list;
      }

      private char character;
      private int prefixBit = 0;
      private TrieNode[] nodes;
      private Set<String> words;
    }

    public Trie() {
      root = new TrieNode('^');
    }

    public void addWord(String word) {
      TrieNode currNode = root;
      for (int i = 0; i < word.length(); ++i) {
        char c = word.charAt(i);
        currNode = currNode.set(c, word);
      }
      // The last word
      currNode.set('$', word);
    }

    public void traversal(TrieNode node) {
      System.out.print(node.character + ": ");
      for (String word : node.words) {
        System.out.print(word + ", ");
      }
      System.out.println();
      List<TrieNode> nextNodes = node.getNextNodes();
      for (TrieNode next : nextNodes) {
        traversal(next);
      }
    }

    public TrieNode root() {
      return root;
    }

    private TrieNode root;

  }

  public List<List<String>> wordSquares(String[] words) {
    List<List<String>> results = new ArrayList<List<String>>();
    if (words == null || words.length == 0) {
      return results;
    }
    for (String word : words) {
      trie.addWord(word);
    }
    backtracking(new ArrayList<String>(), words[0].length(), 0, results);
    return results;
  }

  private void backtracking(List<String> square, int wordLen, int row, List<List<String>> results) {
    if (row == wordLen) {
      results.add(new ArrayList<String>(square));
      return;
    }
    Trie.TrieNode currNode = trie.root();
    for (String word : square) {
      currNode = currNode.get(word.charAt(row));
      if (currNode == null)
        return;
    }
    Set<String> words = currNode.words;
    for (String word : words) {
      square.add(word);
      backtracking(square, wordLen, row + 1, results);
      square.remove(square.size() - 1);
    }
  }

  private Trie trie = new Trie();
}
