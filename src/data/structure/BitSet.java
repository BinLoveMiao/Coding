
package data.structure;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * This is an implementation of simple BitVector, which can be very useful if we
 * want to record the presence of a universe of elements. Basically, we need: An
 * integer array in which each bit of each integer represents an element of
 * index i. For example, the index 1 element should be represented in the first
 * integer, in the 1 bit (starting from 0 bit), and the index 34 element should
 * be represented in the second integer, in the 1 bit. Since then, given an
 * index k element, k / 32 will give the which integer it is in, and k % 32
 * is the exact bit it is in the integer. For efficiency consideration, we
 * compute k / 32 with k >> 5, and k % 32 = k & 0x1F (11111). The interface: We
 * will implement: void set(int k) boolean get(int k) int getNextSetBit(int
 * from)
 */

public class BitSet {
	int[] words;
	int capacity;
	int cardinality;
	
	final static int SHIFT = Integer.numberOfTrailingZeros(Integer.SIZE);
	
	public static void main(String[] args){
		test();
	}
	
	public static void test(){
		// test against differnt capacity setting
		int[] caps = {1, 38, 60, 10234, Integer.MAX_VALUE};
		Random rand = new Random(System.currentTimeMillis());
		
		for(int capacity : caps){
			System.out.println();
			System.out.println("Test capacity: " + capacity);
			
			BitSet bit = new BitSet(capacity);
			Set<Integer> set = new HashSet<Integer>();
			for(int i = 0; i < Math.min(capacity, 50); ++i){
				int j = rand.nextInt(capacity);
				while(set.contains(j)){
					j = rand.nextInt(capacity);
				}
				System.out.println("Test " + j + "[-th] bit");
				bit.set(j);
				set.add(j);
				assert(bit.cardinality == (i + 1));
			}
			
			
			for(int i = bit.getNextSetBit(0); i >= 0; i = bit.getNextSetBit(i + 1)){
				System.out.println("Next Set bit: " + i);
				assert(set.contains(i));
				set.remove(i);
			}
			assert(set.size() == 0);
			
		}
	}

	public BitSet(int capacity) {
		assert (capacity >= 0);
		words = new int[(capacity >> SHIFT) + 1];
		this.capacity = capacity;
		this.cardinality = 0;
	}

	// Means all bits are set
	public boolean full() {
		return this.cardinality == this.capacity;
	}

	// Means no bit is set
	public boolean empty() {
		return this.cardinality == 0;
	}
	
	/**
	 * Return the combination of which integer and which bit
	 * @param k
	 * @return
	 */
	private int[] getBits(int k){
		int[] bit = {k >> SHIFT, (k & 0x1F)};
		return bit;
	}

	public void set(int k) {
		assert (k >= 0 && k < this.capacity);
		int[] bit = getBits(k);
		int mask = (1 << bit[1]);
		if((words[bit[0]] & mask) == 0){
			++this.cardinality;
			words[bit[0]] |= mask;
		}
	}
	
	public void clear(int k){
		assert (k >= 0 && k < this.capacity);
		int[] bit = getBits(k);
		int mask = (1 << bit[1]);
		if((words[bit[0]] & mask) != 0){
			--this.cardinality;
			words[bit[0]] &= ~mask;
		}
	}

	public boolean get(int k) {
		assert (k >= 0 && k < this.capacity);
		int[] bit = getBits(k);
		return (words[bit[0]] & (1 << bit[1])) != 0;
	}
	
	/**
	 * Get next set bit from a given index. 
	 * This is fairly tricky implementation. We need several bit operations for efficiency consideration.
	 * 
	 * @param from
	 * @return
	 */
	public int getNextSetBit(int from) { // From is exclusive
		if (from < 0 || from > this.capacity)
			return -1;
		int[] bit = getBits(from);
		int num = -1;
		while(true){
			// We mask off the bit after current index in the corresponding word.
			num = words[bit[0]] & ((-1) << bit[1]);
			// num & -num would give the trailing one. 
			num &= -num;
			// If there is a one here, we return corresponding index.
			if(num != 0) break;
			else{ // Else we must trail to next word by increasing bit[0].
				bit[0] += 1;
				bit[1] = 0;
			}
			if(bit[0] == this.words.length) return -1;
		}

		return (bit[0] << 5) + Integer.numberOfTrailingZeros(num);
	}

}