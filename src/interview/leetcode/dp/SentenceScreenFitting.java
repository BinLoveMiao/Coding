package interview.leetcode.dp;

/**
 * Given a rows x cols screen and a sentence represented by a list of non-empty words, find how many
 * times the given sentence can be fitted on the screen. <br>
 * 
 * Note: <br>
 * 
 * A word cannot be split into two lines. The order of words in the sentence must remain unchanged.
 * Two consecutive words in a line must be separated by a single space. Total words in the sentence
 * won't exceed 100. Length of each word is greater than 0 and won't exceed 10. 1 ≤ rows, cols ≤
 * 20,000.
 * 
 * @author robin
 *
 */
class SentenceScreenFitting {

  public static void main(String[] args) {
    String[] words = {"a", "bcd", "e"};
    int rows = 3, cols = 6;
    System.out.println(wordsTyping(words, rows, cols));
  }

  public static int wordsTyping(String[] sentence, int rows, int cols) {
    if (sentence == null || sentence.length == 0)
      return 0;
    int len = sentence.length;
    // dp[i] means starting from the word at index i in the sentence to fit
    // the words into a row, what is the next index that gonna start the next row.
    // Note dp[i] can be larger than sentence.length, means one row can possible
    // fit the whole sentence multiple times.
    int[] dp = new int[len];
    for (int i = 0; i < len; ++i) {
      int colsTaken = sentence[i].length();
      // Means the screen can not even fit a single word
      if (colsTaken > cols) {
        return 0;
      }
      int j = i + 1;
      while (true) {
        colsTaken += (1 + sentence[j % len].length());
        if (colsTaken > cols) {
          break;
        }
        ++j;
      }
      dp[i] = j;
    }

    int curRow = 0, startIndex = 0, totalWordsFit = 0;
    while (curRow < rows) {
      totalWordsFit += (dp[startIndex] - startIndex);
      startIndex = dp[startIndex] % len;
      curRow += 1;
    }
    return (totalWordsFit / len);
  }
}
