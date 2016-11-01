package interview.leetcode.design;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 146. LRU Cache <br>
 * https://leetcode.com/problems/lru-cache/
 * 
 * @author Robeen Lai
 * @email longbin.lai@gmail.com
 *
 */
public class LRUCache{
	
	public static void main(String[] args){
		LRUCache cache = new LRUCache(2);
		cache.set(1, 2);
		cache.set(2, 3);
		
		assert(cache.get(1) == 2);
		assert(cache.get(2) == 3);
		
		// Now 1 is the least recently used, and it will be replaced
		cache.set(3, 4);
		assert(cache.get(1) == -1);
		assert(cache.get(2) == 3);
		assert(cache.get(3) == 4);
		
		// Change the value on the key 2
		cache.set(2, 5);
		
		// 3 is the least recently used, and it will be replaced.
		cache.set(1, 6);
		assert(cache.get(1) == 6);
		assert(cache.get(2) == 5);
		assert(cache.get(3) == -1);
		
	}
	
	public static class Node{
		int key;
		int value;
		Node prev;
		Node next;
		
		public Node(int key, int val){
			this.key = key;
			this.value = val;
		}
	}
	
	private Map<Integer, Node> cache;
	private int capacity;
	private int size;
	private Node head;
	private Node tail;
	
	
	
	public LRUCache(int capacity) {
        assert(capacity > 0);
        this.cache = new HashMap<Integer, Node>(capacity);
        this.capacity = capacity;
        this.size = 0;
    }
	
	public boolean isEmpty(){
		return this.size == 0;
	}
	
	public boolean isFull(){
		return this.size == capacity;
	}
	
	/**
	 * Remove a node from the double linked list.
	 * @param node The node to remove
	 */
	private void removeNode(Node node){
	    if(node == head && node == tail){
	        head = null;
	        tail = null;
	    }
	    else if(node == head){
	        head = head.next;
        	head.prev = null;
	    }
	    else if(node == tail){
	        tail = node.prev;
	        tail.next = null;
	    }
	    else{
        	node.prev.next = node.next;
        	node.next.prev = node.prev;
        }
	}
	
	/**
	 * Insert a node to the end of the double linked list.
	 * @param node The node to add.
	 */
	private void addNode(Node node){
	    if(head == null){
	        head = tail = node;
	    }
	    else{ // add node to the tail of the list
		    node.prev = tail;
		    node.next = null;
            tail.next = node;
            tail = node;
	    }
	}
    
    public int get(int key) {
        if(!this.cache.containsKey(key)){
        	return -1;
        }
        Node node = this.cache.get(key);
        // do nothing if it is already the tail
        if(node != tail){ 
        	removeNode(node);
            addNode(node);
        }
       
        return node.value;
    }
    
    public void set(int key, int value) {
        Node node = null;
        if(!this.cache.containsKey(key)){
        	node = new Node(key, value);
        	this.addNode(node);
        	this.cache.put(key, node);
        	if(!isFull()){
        		++this.size;
        	}
        	else{
        		// remove the least recently used item
        		this.cache.remove(head.key);
        		this.removeNode(head);
        	}
        }
        else{
            node = this.cache.get(key);
            node.value = value;
            if(node != tail){
                this.removeNode(node);
                this.addNode(node);
            }
        }
    }
}