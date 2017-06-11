package interview.leetcode.greedy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class MeetingRoomsII {

  static class Interval {
    int start = 0;
    int end = 0;

    public Interval(int start, int end) {
      this.start = start;
      this.end = end;
    }
  }

  public int minMeetingRooms(Interval[] intervals) {
    if (intervals == null || intervals.length == 0) {
      return 0;
    }
    int rooms = 0;
    Arrays.sort(intervals, new Comparator<Interval>() {
      @Override
      public int compare(Interval one, Interval two) {
        int comp = Integer.compare(one.start, two.start);
        if (comp != 0) {
          return comp;
        }
        return Integer.compare(one.end, two.end);
      }
    });
    LinkedList<Interval> list = new LinkedList<Interval>();
    for (Interval inter : intervals) {
      list.add(inter);
    }
    while (!list.isEmpty()) {
      int index = 0;
      while (index < list.size()) {
        Interval interval = list.remove(index);
        index = binarySearch(list, interval.end);
      }
      ++rooms;
    }
    return rooms;
  }

  private int binarySearch(List<Interval> intervals, int endTime) {
    int p1 = 0, p2 = intervals.size() - 1;
    int mid = 0;
    while (p1 <= p2) {
      mid = p1 + (p2 - p1) / 2;
      if (intervals.get(mid).start < endTime) {
        p1 = mid + 1;
      } else {
        p2 = mid - 1;
      }
    }
    return p2 + 1;
  }
}
