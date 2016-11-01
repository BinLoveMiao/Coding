package data.structure.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import data.structure.DisjointSet;
import data.structure.graph.Node.Color;

public class UndirectedGraph<T extends Comparable<T>> extends Graph<T>{
	
	public UndirectedGraph(){
		super(false);
	}
	
	public class EdgeComparator implements Comparator<Edge<T>>{
		@Override
		public int compare(Edge<T> e1, Edge<T> e2) {
			// TODO Auto-generated method stub
			return Float.compare(e1.weight, e2.weight);
		}	
	}
	
	/**
	 * The Kruskal's MST algorithm
	 * @return The set of minimum spanning tree
	 */
	public List<Edge<T>> kruskalMST(){
		Iterator<Node<T>> nodeIter = this.nodeIter();
		//Iterator<Edge<T>> edgeIter = this.edgeIter();
		while(nodeIter.hasNext()){
			DisjointSet.makeSet(nodeIter.next());
		}

		List<Edge<T>> mst = new ArrayList<Edge<T>>();
		this.edges.sort(new EdgeComparator());
		Iterator<Edge<T>> edgeIter = this.edgeIter();
		
		while(edgeIter.hasNext()){
			Edge<T> e = edgeIter.next(); // Extract the one with minimum weight
			if(DisjointSet.findSet(e.src) != DisjointSet.findSet(e.dst)){
				mst.add(e);
				DisjointSet.union(e.src, e.dst);
			}
		}
		return mst;
	}
	
	/**
	 * The prim's MST algorithm
	 * @param s
	 * @return
	 */
	public List<Edge<T>> primMST(Node<T> s){
		assert(this.nodes.containsKey(s.id));
		Iterator<Node<T>> nodeIter = this.nodeIter();
		s.weight = 0f;
		PriorityQueue<Node<T>> pq = new PriorityQueue<Node<T>>(new NodeComparator());
		List<Edge<T>> mst = new ArrayList<Edge<T>>();
		while(nodeIter.hasNext()){
			Node<T> u = nodeIter.next();
			if(u.id != s.id) u.weight = Float.MAX_VALUE;
			pq.offer(u);
		}
		while(!pq.isEmpty()){
			Node<T> u = pq.poll();
			if(u.color == Color.WHITE){
				if(u.parent != null){
					mst.add(u.parent.nbrs.get(u.id));
				}
				Iterator<Edge<T>> edgeIter = u.nbrIter();
				while(edgeIter.hasNext()){
					Edge<T> e = edgeIter.next();
					if(e.dst.color == Color.WHITE && e.dst.weight > e.weight){
						e.dst.weight = e.weight;
						e.dst.parent = u;
						pq.offer(e.dst);
					}
				}
				u.color = Color.BLACK;
			}
		}
		return mst;

	}
	
}