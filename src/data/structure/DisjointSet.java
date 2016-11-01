package data.structure;

import data.structure.graph.Node;

/**
 * A genearl implementat
 * @author robeen
 *
 * @param <E>
 */
public class DisjointSet {
	
	public static <T extends Comparable<T>> void makeSet(Node<T> x){
		x.setRank(0);
		x.setParent(x);
	}

	// Optimize via path compression
	public static <T extends Comparable<T>> Node<T> findSet(Node<T> x) {
		if (x.parent() != x)
			x.setParent(findSet(x.parent()));
		return x.parent();
	}

	public static <T extends Comparable<T>> void union(Node<T> x, Node<T> y) {
		Node<T> px = findSet(x);
		Node<T> py = findSet(y);
		if (px.rank() > py.rank()) py.setParent(px);
		else px.setParent(py);
		if (px.rank() == py.rank()) py.setRank(py.rank() + 1);
	}

}