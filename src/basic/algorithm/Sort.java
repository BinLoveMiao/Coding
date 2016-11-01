
package basic.algorithm;

import java.util.Arrays;

/**
 * This is a routine sort algorithm.
 * Normally I would more focused on the algorithm and implementation 
 * than on the usability. All sortings are on integer array.
 * @author robeen
 *
 */
public class Sort{
	
	public static void main(String[] args){
		int[] arr = {6, 8, 2, 5, 4};
		quickSort(arr);
		for(int i = 0; i < arr.length; ++i)
			System.out.print(arr[i] + ",");
	}
	
	/**
     * A merge sort implementation
     */
    public static void mergeSort(int[] array){
    	// Ignore some bad situations
    	if(array == null || array.length == 0) return;
    	mergeSort(array, 0, array.length - 1);
    }
    
    /**
     * A merge sort recursive routine
     * @param array
     * @param start
     * @param end
     */
    public static void mergeSort(int[] array, int start, int end){
    	if(start >= end) return;
    	int mid = start + (end - start) / 2;
    	mergeSort(array, start, mid);
    	mergeSort(array, mid + 1, end);
    	merge(array, start, mid, end);
    }
    
   /**
    * The core merge sort operation.
    * @param array
    * @param start
    * @param mid
    * @param end
    */
    private static void merge(int[] array, int start, int mid, int end){
    	int l = end - mid;
    	// Copy the arr2 to the tempArr
    	// This makes merge sort non in-place
    	int[] tempArr = Arrays.copyOfRange(array, mid + 1, end + 1);
    	int p1 = mid, p2 = l - 1, p = end;
    	// p1 can reach start in array and p2 can reach 0 in tempArray
    	while(p1 >= start && p2 >= 0){
    		if(array[p1] > tempArr[p2]) array[p--] = array[p1--];
    		else array[p--] = tempArr[p2--];
    		
    	}
    	while(p2 >= 0) array[p--] = tempArr[p2--];
    }
    
    public static void quickSort(int[] array){
    	if(array == null || array.length == 0) return; 
    	quickSort(array, 0, array.length - 1);
    }
    
    /**
     * A quick sort recursive routine.
     * Sorting the sub-array array[start, end] (both inclusive)
     * @param array
     * @param start
     * @param end
     */
    public static void quickSort(int[] array, int start, int end){
    	if(start >= end) return;
    	int part = partition(array, start, end);
    	quickSort(array, start, part - 1);
    	quickSort(array, part + 1, end);
    }
    
    /**
     * The core quick sort partition function.
     * This function can be modified to solve the find-kth.
     * @param array
     * @param start
     * @param end
     * @return
     */
    private static int partition(int[] array, int start, int end){
    	int endIndex = end; // remember the endIndex
    	int pivot = array[end];
    	--end;
    	while(start <= end){
    		if(array[start] > pivot) swap(array, start, end--);
    		else ++start;
    	}
    	swap(array, start, endIndex);
    	return start;
    }
    
    /**
     * Swap ith and jth value in array
     * @param array
     * @param i
     * @param j
     */
    private static void swap(int[] array, int i, int j){
    	int temp = array[i];
    	array[i] = array[j];
    	array[j] = temp;
    }
    
    /**
     * Find the kth smallest value in the unsorted array.
     * We will use the partition routine in quicksort to help us
     * identify the kth smallest value. Specifically:<br>
     * We call part = partition(array, start, end) <br>
     * (1) part = k - 1: array[part] is the value that we want;<br>
     * (2) part < k - 1: We know that all values after part are larger or equal
     * than array[part], we can proceed to the right part to search the (k - 1 - part)[-th] value.
     * (3) part > k - 1: We know that all values before part are smaller than part. 
     * therefore, we go to the left part to continue searching the k-th smallest value.
     * @param array
     * @param k
     * @return
     */
    public static int findKth(int[] array, int k){
    	if(array == null || k <= 0 || k > array.length) return -1;
    	return findKth(array, 0, array.length - 1, k);
    }
    
    private static int findKth(int[] array, int start, int end, int k){
    	int part = partition(array, start, end);
    	int kPrim = part - start + 1;
    	if(kPrim == k) return array[part];
    	else if(kPrim < k) return findKth(array, part + 1, end, k - kPrim);
    	return findKth(array, start, part - 1, k);
    }
	
}