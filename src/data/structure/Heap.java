package data.structure;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;


public class Heap<E extends Comparable<E>> implements Collection<E> {
	protected Object[] array;
	protected int size = 0;
	private int capacity = 0;
	protected int heapSize = 0;
	protected Comparator<E> comp;
	
	private final static Object[] EMPTY_ARRAY = {};

	public final static int INIT = 1 << 4;
	
	public static void main(String[] args){
		//List<Integer> array = new ArrayList<Integer>();
		Integer[] test = {1, 1032, 3, 4, 5};
		Heap<Integer> heap = new Heap<Integer>(test);
		System.out.println(heap.peek());
	}

	// By default, this is a max heap, using for sort
	public class DefaultComparator implements Comparator<E> {
		@Override
		public int compare(E e1, E e2) {
			return e1.compareTo(e2);
		}
	}

	private void set(Collection<? extends E> input) {
		assert (input != null);
		this.array = input.toArray();
		if ((size = array.length) != 0) {
            // c.toArray might (incorrectly) not return Object[] (see 6260652)
            if (array.getClass() != Object[].class)
                array = Arrays.copyOf(array, size, Object[].class);
        } else {
            // replace with empty array.
            this.array = EMPTY_ARRAY;
        }
	}

	private void set(int capacity) {
		int powerTwo = INIT;
		while (powerTwo < capacity)
			powerTwo <<= 1;
		assert (powerTwo >= INIT); // Prevent capacity from getting too big
		//this.array = (E[]) Array.newInstance(input[0].getClass(), powerTwo);
		// this.array = new E[powerTwo];
		this.array = new Object[powerTwo];
		this.capacity = capacity;
		this.size = 0;
		this.heapSize = 0;
	}
	
	/**
	 * Only used for heap sort
	 * @param input
	 */
	protected Heap(E[] input){
		this.array = input;
		this.size = input.length;
		this.comp = new DefaultComparator();
		this.heapify();
	}

	public Heap(Collection<? extends E> input) {
		this.set(input);
		this.comp = new DefaultComparator();
		this.heapify();
	}

	public Heap(Collection<? extends E> input, Comparator<E> comp) {
		this.set(input);
		this.comp = comp;
		this.heapify();
	}

	public Heap(int capacity) {
		this.set(capacity);
		this.comp = new DefaultComparator();
	}

	public Heap(int capacity, Comparator<E> comp) {
		this.set(capacity);
		this.comp = comp;
	}

	public final static int parent(int i) {
		return i == 0 ? -1 : ((i - 1) >> 1);
	}

	public final static int left(int i) {
		return (i << 1) + 1;
	}

	public final static int right(int i) {
		return (i << 1) + 2;
	}

	@SuppressWarnings("unchecked")
	protected void swap(int i, int j) {
		E temp = (E) this.array[i];
		this.array[i] = this.array[j];
		this.array[j] = temp;
	}

	public void heapify() {
		int end = this.size - 1;
		int start = parent(end);
		while (start >= 0) {
			siftDown(start, end);
			start -= 1;
		}
		// After heapify, the heapsize become size
		this.heapSize = this.size;
	}

	protected void siftDown(int i, int end) {
		if (i >= end)
			return;
		int root = i;
		while (left(root) <= end) {
			int child = left(root);
			int swap = root;
			if (comp.compare((E) array[child], (E) array[swap]) > 0)
				swap = child;
			child = right(root);
			if (child <= end && comp.compare((E) array[child], (E) array[swap]) > 0)
				swap = child;
			if (swap == root)
				break;
			swap(swap, root);
			root = swap;
		}
	}

	private void bottomUp(int i) {
		int child = i;
		int p = 0;
		while (parent(child) >= 0) {
			p = parent(child);
			if (comp.compare((E) array[p], (E) array[child]) >= 0)
				break;
			swap(p, child);
			child = p;
		}
	}

	public E peek() {
		if(isEmpty()) return null;
		return (E) this.array[0];
	}

	
	private void resize() {
		this.capacity <<= 1;
		assert (this.capacity >= INIT);
		//E[] newArray = (E[]) Array.newInstance(this.array[0].getClass(), this.capacity);
		Object[] newArray = new Object[this.capacity];
		System.arraycopy(array, 0, newArray, 0, this.size);
		this.array = null; // explictely release the memory
		this.array = newArray;
	}
	
	public boolean add(E e) {
		if (this.size == this.capacity)
			resize();
		array[this.size++] = e;
		// push the newly added element to where it belongs to
		this.bottomUp(this.size - 1);
		++this.heapSize;
		return true;
	}


	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return (this.size == 0);
	}
	
	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return Arrays.copyOf(this.array, this.size);
	}
	
	@Override
	public <E> E[] toArray(E[] a) {
        if (a.length < size)
            // Make a new array of a's runtime type, but my contents:
            return (E[]) Arrays.copyOf(array, size, a.getClass());
        System.arraycopy(array, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return indexOf(o) != -1;
	}
	
	public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (array[i]==null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(array[i]))
                    return i;
        }
        return -1;
    }

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		for(int i = 0; i < this.size; ++i){
			array[i] = null;
		}
		this.size = 0;
	}
}
