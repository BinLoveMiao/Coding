package data.structure;

import java.util.Arrays;

/**
 * This time we implement a 1-D segment tree. Basically, segment tree is used in
 * range query.
 */
public class SegmentTree {
	int[] array;
	int[] tree; // The segment tree index
	int capacity; // capacity of the tree
	
	public static void main(String[] args){
		int[] input = {1, 4, 5, 6, 3, 8, 9};
		SegmentTree sgt = new SegmentTree(input);
		System.out.println(sgt.rangeSum(0, 4));
		System.out.println(sgt.rangeSum(0, 6));
		sgt.update(5, 20);
		System.out.println(sgt.rangeSum(0, 4));
		System.out.println(sgt.rangeSum(0, 6));
		
	}

	public final static int ROOT = 0;

	public SegmentTree(int[] input) {
		// ignore several useless cases
		assert (input != null && input.length > 0);
		int len = input.length;
		int k = (int) Math.ceil(Math.log(len) / Math.log(2)) + 1;
		this.capacity = (1 << k) - 1;
		array = Arrays.copyOf(input, input.length);
		tree = new int[capacity];
		buildTree(0, array.length - 1, ROOT);
	}

	public final static int parent(int i) {
		return (i - 1) >> 1;
	}

	public final static int left(int i) {
		return (i << 1) + 1;
	}

	public final static int right(int i) {
		return (i << 1) + 2;
	}
	
	private int buildTree(int start, int end, int ti){
		if(start == end) {
			tree[ti] = array[start];
		}
		else{
			int mid = start + (end - start) / 2;
			tree[ti] = buildTree(start, mid, left(ti)) +
					buildTree(mid + 1, end, right(ti));
		}
		return tree[ti];
		
	}

	public void update(int i, int newVal) {
		assert (i >= 0 && i < array.length);
		int diff = newVal - array[i];
		array[i] = newVal;
		if (diff == 0)
			return; // No change actually
		int start = 0, end = array.length - 1;
		int ti = ROOT;
		while (ti < capacity) {
			tree[ti] += diff;
			int mid = start + (end - start) / 2;
			if (i <= mid)
				ti = left(ti);
			else
				ti = right(ti);
		}
	}

	public int rangeSum(int i, int j) {
		assert (i >= 0 && i < array.length && j >= 0 && j < array.length);
		if (i == j)
			return array[i];
		return rangeSum(i, j, 0, array.length - 1, ROOT);
	}

	// Note that i and j are the query range.
	// start and end are the tree-representative range.
	// ti is the current tree index
	private int rangeSum(int i, int j, int start, int end, int ti) {
		// The current range completely fall into the query range
		if (start >= i && end <= j)
			return tree[ti];
		if (start > j || end < i)
			return 0;
		int mid = start + (end - start) / 2;
		return rangeSum(i, j, start, mid, left(ti)) + 
				rangeSum(i, j, mid + 1, end, right(ti));
	}
}