package interview.leetcode.design;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Given two 1d vectors, implement an iterator to return their elements alternately. <br>
 * For example, given two 1d vectors: <br>
 * v1 = [1, 2], v2 = [3, 4, 5, 6]. <br>
 * By calling next repeatedly until hasNext returns false, <br>
 * the order of elements returned by next should be: [1, 3, 2, 4, 5, 6].
 * 
 * Note that we implement this ZigzagIterator in a way that it can be extended to <br>
 * multiple arrays.
 * 
 * @author robin
 */
public class ZigzagIterator {
  final static int NUM_LISTS = 2;
  int curList = 0;
  int pos[];
  int len[];
  List<List<Integer>> lists;

  public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
    pos = new int[] {0, 0};
    len = new int[] {v1.size(), v2.size()};
    lists = new ArrayList<List<Integer>>();
    lists.add(v1);
    lists.add(v2);
    if (v1.size() == 0)
      goToNextList();
  }

  private void goToNextList() {
    int i = curList;
    do {
      i = (i + 1 == NUM_LISTS) ? 0 : i + 1;
      if (pos[i] != len[i])
        break;
    } while (i != curList);
    curList = i;
  }

  public boolean hasNext() {
    return pos[curList] != len[curList];
  }

  public int next() {
    assert (pos[curList] != len[curList]);
    int result = lists.get(curList).get(pos[curList]++);
    goToNextList();
    return result;
  }
}
