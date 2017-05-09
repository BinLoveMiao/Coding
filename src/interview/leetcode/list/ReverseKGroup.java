package interview.leetcode.list;

import basic.structure.ListNode;

/**
 * Leetcode 25. Reverse Nodes in k-Group. <br>
 * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list. <br>
 * 
 * k is a positive integer and is less than or equal to the length of the linked list. If the number
 * of nodes is not a multiple of k then left-out nodes in the end should remain as it is. <br>
 * 
 * You may not alter the values in the nodes, only nodes itself may be changed. <br>
 *  
 * Only constant memory is allowed. <br> <br>
 * 
 * For example, Given this linked list: 1->2->3->4->5 <br>
 * 
 * For k = 2, you should return: 2->1->4->3->5 <br>
 * 
 * For k = 3, you should return: 3->2->1->4->5 
 * @author robin
 *
 */
class ReverseKGroup {
  public ListNode reverseKGroup(ListNode head, int k) {
    if (k == 1) {
      return head;
    }
    ListNode tail = getTail(head, k);
    if (tail == null) {
      // tail == null means k < list.size()
      return head;
    }
    return reverseKFullGroup(head, tail, k);
  }

  /**
   * Reverse a list starting from head and ending in tail whose size divides k.
   * 
   * @param head
   * @param tail
   * @param k
   * @return The newHead of the modified list.
   */
  private ListNode reverseKFullGroup(ListNode head, ListNode tail, int k) {
    ListNode currHead = head;
    ListNode prevHead = null;
    ListNode newHead = null;
    ListNode stop = tail.next;
    while (currHead != stop) {
      ListNode[] pair = reverseOneGroup(currHead, k);
      if (prevHead != null) {
        prevHead.next = pair[0];
      }
      prevHead = currHead;
      currHead = pair[1];
      if (newHead == null)
        newHead = pair[0];
    }
    prevHead.next = currHead;

    return newHead;
  }

  /**
   * Reverse one group of nodes, starting from head, with size k.
   * 
   * For example, given n1 -> n2 -> n3 -> n4, let head = n1, k = 3, the function will: <br>
   * (1) Reverse the link direction to n3 -> n2 -> n1 -> (n2, change when reverse next) <br>
   * (2) Return {n3, n4}, as n3 is the original tail node in the first group, and n4 is the original
   * head of the second group. <br>
   * 
   * @return The original tail node in current group + the original head node in the next group.
   * 
   * 
   */
  private ListNode[] reverseOneGroup(ListNode head, int k) {
    ListNode cur = head;
    ListNode next = cur.next;
    // k = 1 does not require any process, so we can assume that k at least
    // equal to 2, and next can not be null
    ListNode nextNext = next.next;
    while ((--k) > 0) {
      next.next = cur;
      cur = next;
      next = nextNext;
      // if next == null, we must break in the next iteration
      if (next != null) {
        nextNext = next.next;
      }
    }
    return new ListNode[] {cur, next};
  }

  /**
   * Get the tailed node to which the length of the list can divide k.
   * 
   * @param head
   * @param k
   * @return
   */
  private ListNode getTail(ListNode head, int k) {
    int count = 1;
    ListNode tail = null;
    ListNode cur = head;
    while (cur != null) {
      if (count == k) {
        count = 0;
        tail = cur;
      }
      cur = cur.next;
      ++count;
    }
    return tail;
  }
}
