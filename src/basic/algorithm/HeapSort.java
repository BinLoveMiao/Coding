package basic.algorithm;

import java.util.Random;
import data.structure.Heap;

public class HeapSort<E extends Comparable<E>> extends Heap<E>{
	
	public static void main(String[] args){
		int capacity = 1000000;
		Integer[] array = new Integer[capacity];
		Random rand = new Random(System.currentTimeMillis());
		for(int i = 0; i < capacity; ++i) array[i] = rand.nextInt(capacity);
		HeapSort<Integer> hs = new HeapSort<Integer>(array);
		long begin = System.currentTimeMillis();
		hs.sort();
		long end = System.currentTimeMillis();
		System.out.println((end - begin) + " ms");
		/*
		for(int i = 0; i < array.length; ++i){
			System.out.print(array[i] + ",");
		}*/
	}
	
	public HeapSort(E[] input){
		super(input);
	}
	
	public void sort() {
		while (this.heapSize > 1) {
			int end = heapSize - 1;
			swap(0, end);
			this.siftDown(0, end - 1);
			--heapSize;
		}
	}


}