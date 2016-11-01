package data.structure;

import java.lang.reflect.Array;
import java.util.Map;

public class HashMap<K extends Comparable<K>, V> {
	
	public static void main(String[] args){
		performanceTest();
	}
	
	public static void performanceTest(){
		HashMap<String, Integer> map = new HashMap<String, Integer>(2);
		Map<String, Integer> buildMap = new java.util.HashMap<String, Integer>();
		
		int testCases = 10000000;
		
		long begin = System.currentTimeMillis();
		for(int i = 0; i < testCases; ++i){
			map.put(String.valueOf(i), i);
		}
		
		for(int i = 0; i < testCases; ++i){
			assert(map.get(String.valueOf(i)) == i);
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Time elapsed: " + (end - begin));
		
		begin = System.currentTimeMillis();
		for(int i = 0; i < testCases; ++i){
			buildMap.put(String.valueOf(i), i);
		}
		
		for(int i = 0; i < testCases; ++i){
			assert(buildMap.get(String.valueOf(i)) == i);
		}
		end = System.currentTimeMillis();
		
		System.out.println("Time elapsed: " + (end - begin));
		
	}

	public static class Entry<K, V> {
		K key;
		V value;
		Entry<K, V> next;

		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	private Entry<K, V>[] table;
	private int size;
	private int capacity;
	private final static int INIT = 1 << 4;
	private final static float FACTOR = 0.75f;

	private void init(int capacity) {
		int powerTwo = INIT;
		while (powerTwo < capacity)
			powerTwo <<= 1;
		this.capacity = powerTwo;
		this.table = (Entry<K, V>[]) Array.newInstance(Entry.class, this.capacity);
		this.size = 0;
	}

	public HashMap() {
		this.init(INIT);
	}

	public HashMap(int capacity) {
		this.init(capacity);
	}

	// Could define own hash
	private int hash(K key) {
		int h = key.hashCode();
		return h ^ (h >>> 16);
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public V get(K key) {
		assert (!isEmpty());
		int h = hash(key) & (capacity - 1);
		Entry<K, V> entry = table[h];
		while (entry != null) {
			if (entry.key.compareTo(key) == 0)
				break;
			entry = entry.next;
		}
		return entry == null ? null : entry.value;
	}

	public V remove(K key) {
		assert (!isEmpty());
		int h = hash(key) & (capacity - 1);
		Entry<K, V> entry = table[h];
		Entry<K, V> prev = null;
		while (entry != null) {
			if (entry.key.compareTo(key) == 0) {
				if (prev != null)
					prev.next = entry.next;
				else
					table[h] = entry.next;
				--size;
				break;
			}
			prev = entry;
			entry = entry.next;
		}
		return entry == null ? null : entry.value;
	}

	private void rehashing(int h, Entry<K, V>[] table) {
		Entry<K, V> entry = table[h];
		Entry<K, V> prev = null;
		while (entry != null) {
			Entry<K, V> temp = entry.next;
			int hPrim = hash(entry.key) & (this.capacity - 1);
			if (hPrim != h) {
				// Remove from current bucket
				if (prev != null)
					prev.next = temp;
				else
					table[h] = temp;
				// Insert into new bucket
				entry.next = table[hPrim];
				table[hPrim] = entry;
			} else
				prev = entry;
			entry = temp;
		}
	}

	private void resize() {
		this.capacity <<= 1;
		Entry<K, V>[] newTable = (Entry<K, V>[]) Array.newInstance(Entry.class, this.capacity);
		for (int i = 0; i < table.length; ++i)
			newTable[i] = table[i];
		for (int i = 0; i < table.length; ++i)
			rehashing(i, newTable);
		table = null; // Trigger garbage collection
		table = newTable;
	}

	public void put(K key, V value) {
		if (this.size == FACTOR * capacity)
			resize();
		int h = hash(key) & (this.capacity - 1);
		Entry<K, V> entry = table[h];
		while (entry != null) {
			if (entry.key.compareTo(key) == 0) {
				entry.value = value;
				break;
			}
			entry = entry.next;
		}
		if (entry == null) {
			Entry<K, V> newEntry = new Entry<K, V>(key, value);
			newEntry.next = table[h];
			table[h] = newEntry;
			++size;
		}
	}
}