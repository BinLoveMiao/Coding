package basic.structure;

public class ListNode {
  public int val;
  public ListNode next;
  
  public ListNode(int val) {
    this.val = val;
  }
  
  public String toString() {
    return String.valueOf(this.val);
  }
}