package interview.leetcode.design;

import java.util.HashMap;
import java.util.Map;

import basic.structure.ListNode;

/**
 * LeetCode 362. Design Hit Counter. <br> <br>
 * 
 * Design a hit counter which counts the number of hits received in the past 5 minutes. <br>
 * 
 * Each function accepts a timestamp parameter (in seconds granularity) and you may assume that
 * calls are being made to the system in chronological order (ie, the timestamp is monotonically
 * increasing). You may assume that the earliest timestamp starts at 1. <br> <br>
 * 
 * Note: It is possible that several hits arrive roughly at the same time. <br> <br> 
 * 
 * Example: HitCounter counter = new HitCounter(); <br>
 * 
 * // hit at timestamp 1. counter.hit(1); <br>
 * 
 * // hit at timestamp 2. counter.hit(2); <br>
 * 
 * // hit at timestamp 3. counter.hit(3); <br>
 * 
 * // get hits at timestamp 4, should return 3. counter.getHits(4); <br>
 * 
 * // hit at timestamp 300. counter.hit(300); <br>
 * 
 * // get hits at timestamp 300, should return 4. counter.getHits(300); <br>
 * 
 * // get hits at timestamp 301, should return 3. counter.getHits(301); <br>
 * 
 * 
 * @author robin
 *
 */
public class HitCounter {
  private Map<Integer, Integer> countOfHit;
  ListNode start = null;
  ListNode end = null;
  int count = 0;

  /** Initialize your data structure here. */
  public HitCounter() {
    countOfHit = new HashMap<Integer, Integer>();
  }

  /**
   * Record a hit.
   * 
   * @param timestamp - The current timestamp (in seconds granularity).
   */
  public void hit(int timestamp) {
    if (!countOfHit.containsKey(timestamp)) {
      countOfHit.put(timestamp, 0);
      if (start == null) {
        start = end = new ListNode(timestamp);
      } else {
        end.next = new ListNode(timestamp);
        end = end.next;
      }
    }
    ++count;
    countOfHit.put(timestamp, countOfHit.get(timestamp) + 1);
  }

  /**
   * Return the number of hits in the past 5 minutes.
   * 
   * @param timestamp - The current timestamp (in seconds granularity).
   */
  public int getHits(int timestamp) {
    while (start != null && timestamp - start.val >= 300) {
      count -= countOfHit.get(start.val);
      countOfHit.remove(start.val);
      start = start.next;
    }
    return count;
  }
}
