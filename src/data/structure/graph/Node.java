package data.structure.graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Node<T extends Comparable<T>> {

	public static enum Color {
		WHITE(0), GRAY(1), BLACK(2);
		int val;

		private Color(int v) {
			this.val = v;
		}
	}

	int id; // id of current node

	T label; // label of current node
	float weight;
	Color color;
	Node<T> parent; // Used in bfs/dfs search, and disjoint set
	int rank; // use in disjoint set
	int start;
	int finish;

	Map<Integer, Edge<T>> nbrs;

	private void init(int id, T label, float w, Color c, Node<T> p) {
		this.id = id;
		this.label = label;
		this.weight = w;
		this.color = c;
		this.parent = p;
		this.nbrs = new HashMap<Integer, Edge<T>>();
		this.rank = 0;
		this.start = 0;
		this.finish = 0;
	}

	/**
	 * Simple just initialize the id
	 * 
	 * @param id
	 */
	public Node(int id) {
		this.init(id, null, 0f, Color.WHITE, null);
	}

	/**
	 * Initialize id and the weight
	 * 
	 * @param id
	 * @param w
	 */
	public Node(int id, float w) {
		this.init(id, null, w, Color.WHITE, null);
	}

	/**
	 * Initialize the label's hashcode as id. Caution, this may introduce id
	 * conflict
	 * 
	 * @param label
	 * @param w
	 */
	public Node(T label, float w) {
		this.init(label.hashCode(), label, w, Color.WHITE, null);
	}

	public Iterator<Edge<T>> nbrIter() {
		return this.nbrs.values().iterator();
	}

	@Override
	public String toString() {
		return this.id + "[" + this.weight + "]";
	}

	public String nbr2Str() {
		Iterator<Edge<T>> iter = nbrIter();
		StringBuilder sb = new StringBuilder();
		while (iter.hasNext()) {
			Edge<T> e = iter.next();
			sb.append("{");
			sb.append(e.dst);
			sb.append(";");
			sb.append(e.flow);
			sb.append("/");
			sb.append(e.weight);
			sb.append("}");
			sb.append(",");
		}
		return sb.toString();
	}
	
	public Node<T> parent(){
		return this.parent;
	}
	
	public void setParent(Node<T> p){
		this.parent = p;
	}
	
	public int rank(){
		return rank;
	}
	
	public void setRank(int r){
		this.rank = r;
	}
	
	public boolean hasNeighbor(int id){
		return this.nbrs.containsKey(id);
	}
	
	public boolean hasNeighbor(Node<T> v){
		return this.hasNeighbor(v.id);
	}
	
	public Edge<T> edge(Node<T> dst){
		return this.nbrs.get(dst.id);
	}
}