package interview.leetcode.greedy;

/**
 * LeetCode 484. Find Permutation. <br> <br>
 * 
 * By now, you are given a secret signature consisting of character 'D' and 'I'. 'D' represents a
 * decreasing relationship between two numbers, 'I' represents an increasing relationship between
 * two numbers. And our secret signature was constructed by a special integer array, which contains
 * uniquely all the different number from 1 to n (n is the length of the secret signature plus 1).
 * For example, the secret signature "DI" can be constructed by array [2,1,3] or [3,1,2], but won't
 * be constructed by array [3,2,4] or [2,1,3,4], which are both illegal constructing special string
 * that can't represent the "DI" secret signature. <br> <br>
 * 
 * On the other hand, now your job is to find the lexicographically smallest permutation of [1, 2,
 * ... n] could refer to the given secret signature in the input.
 * 
 * @author robin
 *
 */
public class Permutation {

  public static void main(String[] args) {
    String s = "DDIDDD";
    int[] result = findPermutation(s);
    for (int i = 0; i < result.length; ++i) {
      System.out.print(result[i] + ", ");
    }
  }

  public static int[] findPermutation(String s) {
    if (s == null || s.length() == 0) {
      return null;
    }
    int len = s.length();
    int[] array = new int[len + 1];
    for (int i = 0; i <= len; ++i) {
      array[i] = i + 1;
    }
    for (int h = 0; h < len; ++h) {
      if (s.charAt(h) == 'D') {
        int l = h;
        while (h < len && s.charAt(h) == 'D') {
          ++h;
        }
        reverse(array, l, h);
      }
    }
    return array;
  }

  // This is very good array reversed method. Mark!!!
  static void reverse(int[] array, int l, int h) {
    while (l < h) {
      array[l] ^= array[h];
      array[h] ^= array[l];
      array[l] ^= array[h];
      ++l;
      --h;
    }
  }
}
