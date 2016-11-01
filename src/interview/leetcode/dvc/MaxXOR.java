package interview.leetcode.dvc;

public class MaxXOR {
	
	public static void main(String[] args){
		int[] test = {1, 2, 3 ,4, 5, 6, 7, 8, 9 };
		System.out.println(maxXOR(test));
	}

	private static void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	private static int partition(int[] array, int start, int end, int i) {
		int mask = (1 << i);
		int p1 = start, p2 = end;
		while (p1 <= p2) {
			if ((array[p1] & mask) == 0)
				++p1;
			else
				swap(array, p1, p2--);
		}
		return p1;
	}
	
	public static int maxXOR(int[] array){
		if(array == null || array.length < 2) return 0;
		if(array.length == 2) return array[0] ^ array[1];

		int temp1 = array[0];
		int temp2 = temp1;
		for(int i = 1; i < array.length; ++i){
			temp1 &= array[i];
			temp2 |= array[i];
		}
		
		temp1 ^= temp2; // This boost the process a little bit
		int i = 31 - Integer.numberOfLeadingZeros(temp1);
		int p = partition(array, 0, array.length - 1, i);
		return maxXOR(array, 0, p - 1, p, array.length - 1, i - 1);
		
	}
	
	public static int maxXOR(int[] array, int start1, int end1, int start2,
			int end2, int i){
		
		if(end1 < start1 || end2 < start2) return 0;
		// means duplication
		if(i < 0 || (start1 == end1 && start2 == end2))
			return array[start1] ^ array[start2];
		int p1 = partition(array, start1, end1, i);
		int p2 = partition(array, start2, end2, i);
		
		// This two partitions belong to the same 0 bit or 1 bit
		if((p1 == start1 && p2 == start2) || (p1 == end1 + 1 && p2 == end2 + 1)){
			return maxXOR(array, start1, end1, start2, end2, i - 1);
		}
		
		return Math.max(
				maxXOR(array, start1, p1 - 1, p2, end2, i - 1), 
				maxXOR(array, p1, end1, start2, p2 - 1, i - 1));
	}

}