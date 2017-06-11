package interview.leetcode.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AlienDictionary {

  public static void main(String[] args) {
    String[] words = {"wrt","wrf","er","ett","rftt"};
    AlienDictionary dict = new AlienDictionary();
    System.out.println(dict.alienOrder(words)); 
  }

  public String alienOrder(String[] words) {
    Trie trie = new Trie();
    if (words == null || words.length == 0) {
      return "";
    }
    for (String word : words) {
      trie.addWord(word, this.adjList);
    }
    return topoSort();
  }

  private String topoSort() {
    LinkedList<Character> list = new LinkedList<Character>();
    Iterator<Map.Entry<Character, List<Character>>> it = adjList.entrySet().iterator();
    boolean[] isPlaced = new boolean[26];
    while (it.hasNext()) {
      Map.Entry<Character, List<Character>> pair = it.next();
      int isVisited = 0;
      if (!dfs(pair.getKey(), isPlaced, isVisited, list)) {
        return "";
      }
    }
    StringBuilder sb = new StringBuilder();
    for (char c : list) {
      sb.append(c);
    }
    return sb.toString();
  }

  private boolean dfs(char c, boolean[] isPlaced, int isVisited, LinkedList<Character> list) {
    int index = c - 'a';
    if (isPlaced[index]) {
      return true;
    }
    if (isBitSet(isVisited, index)) {
      return false;
    }
    isVisited |= (1 << index);
    Iterator<Character> it = adjList.get(c).iterator();
    while (it.hasNext()) {
      char n = it.next();
      if (!dfs(n, isPlaced, isVisited, list)) {
        return false;
      }
    }
    list.addFirst(c);
    isPlaced[index] = true;
    return true;
  }

  private boolean isBitSet(int bit, int i) {
    return (bit & (1 << i)) != 0;
  }

  static class Trie {

    static class TrieNode {

      public TrieNode() {
        bit = 0;
        nextNodes = new TrieNode[26];
        nextChars = new char[26];
      }

      public TrieNode addChar(char c, Map<Character, List<Character>> adjList) {
        int index = (int) (c - 'a');
        if ((bit & (1 << index)) == 0) {
          bit |= (1 << index);
          nextNodes[index] = new TrieNode();
          nextChars[size] = c;
          if (size > 0) {
            adjList.get(nextChars[size - 1]).add(c);
          }
          ++size;
        }
        return nextNodes[index];
      }

      private int bit;
      private TrieNode[] nextNodes;
      private char[] nextChars;
      private int size = 0;
    }

    public Trie() {
      root = new TrieNode();
    }

    public void addWord(String word, Map<Character, List<Character>> adjList) {
      if (word == null || word.length() == 0) {
        return;
      }
      int len = word.length();
      TrieNode node = root;
      for (int i = 0; i < len; ++i) {
        char c = word.charAt(i);
        if (!adjList.containsKey(c)) {
          adjList.put(c, new ArrayList<>());
        }
        node = node.addChar(c, adjList);
      }
    }


    private TrieNode root;
  }

  private Map<Character, List<Character>> adjList = new HashMap<Character, List<Character>>();
}

