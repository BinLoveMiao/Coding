package interview.leetcode.twopointer;

/**
 * LeetCode 360. Sort Transformed Array <br>
 * 
 * Given a sorted array of integers nums and integer values a, b and c. Apply a function of the form
 * f(x) = ax2 + bx + c to each element x in the array. <br>
 * 
 * The returned array must be in sorted order. <br>
 * 
 * Expected time complexity: O(n) <br> <br>
 * 
 * Example: <br>
 * nums = [-4, -2, 2, 4], a = 1, b = 3, c = 5,
 * Result: [3, 9, 15, 33] <br> 
 * 
 * nums = [-4, -2, 2, 4], a = -1, b = 3, c = 5
 * Result: [-23, -5, 1, 7]
 * 
 * @author robin
 *
 */
public class SortTransformedArray {

  public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
    int len = nums.length;
    if (len == 0)
      return new int[] {};
    int[] result = new int[len];

    double centre = a == 0 ? Integer.MIN_VALUE : -(double) b / (a << 1);
    int j = findCentreIndex(nums, centre);
    int i = j - 1, k = 0;
    while ((j - i) != len + 1) {
      if (j == len) {
        result[k++] = f(nums[i--], a, b, c);
      } else if (i == -1) {
        result[k++] = f(nums[j++], a, b, c);
      } else {
        if (centre - nums[i] <= nums[j] - centre) {
          result[k++] = f(nums[i--], a, b, c);
        } else {
          result[k++] = f(nums[j++], a, b, c);
        }
      }
    }
    if (a < 0 || a == 0 && b < 0) {
      reverse(result);
    }
    return result;

  }

  private int f(int x, int a, int b, int c) {
    return a * x * x + b * x + c;
  }

  private int findCentreIndex(int[] nums, double centre) {
    int i = 0;
    for (; i < nums.length; ++i) {
      if (nums[i] > centre) {
        break;
      }
    }
    return i;
  }

  private void reverse(int[] nums) {
    int len = nums.length;
    int mid = len >> 1;
    for (int i = 0; i < mid; ++i) {
      int temp = nums[i];
      nums[i] = nums[len - 1 - i];
      nums[len - 1 - i] = temp;
    }
  }
}
