package data.structure.graph;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import data.structure.graph.Node.Color;

public class DirectedGraph<T extends Comparable<T>> extends Graph<T>{
	
	public static void main(String[] args){
		DirectedGraph<Integer> g = new DirectedGraph<Integer>();
		g.addEdge(0, 1, 1000000f);
		g.addEdge(0, 2, 1000000f);
		g.addEdge(1, 3, 1000000f);
		g.addEdge(2, 3, 1000000f);
		g.addEdge(1, 2, 1f);
		
		
		//g.testConstructRG();
		//Node<Integer> s = g.getNode(0), t = g.getNode(5);
		//DirectedGraph<Integer> Gf = g.constructResidualGraph();
		//g.testFindAugPath(Gf, s, t);
		//g.testAdjustFlow(Gf, s, t);
		
		System.out.println(g.maxFlow(g.getNode(1), g.getNode(2)));
		
		/*
		Iterator<Node<Integer>> iter = g.nodeIter();
		while(iter.hasNext()){
			Node<Integer> u = iter.next();
			System.out.println(u + ": " + u.nbr2Str());
		}
		
		List<Node<Integer>> topos = g.topoSort();
		for(Node<Integer> u : topos){
			System.out.print(u + ", ");
		}
		*/
	}
	
	public void testFindAugPath(DirectedGraph<T> Gf, Node<T> s, Node<T> t){
		Node<T> sf = Gf.getNode(s.id);
		Node<T> tf = Gf.getNode(t.id);
		
		Gf.findAugPath(sf, tf);
		
		Node<T> v = tf, u = tf.parent;
		while(u != null){
			System.out.println("(" + u + "," + v + ")" + ": " + u.edge(v).weight);
			v = u;
			u = u.parent;
		}
	}
	
	public void testAdjustFlow(DirectedGraph<T> Gf, Node<T> s, Node<T> t){
		Node<T> sf = Gf.getNode(s.id);
		Node<T> tf = Gf.getNode(t.id);
		Gf.adjustFlow(sf, tf, this);
		
		Iterator<Node<T>> nodeIter = this.nodeIter();
		while(nodeIter.hasNext()){
			Node<T> u = nodeIter.next();
			System.out.println(u + ": " + u.nbr2Str());
		}
		
	}
	
	public void testConstructRG(){
		DirectedGraph<T> Gf = this.constructResidualGraph();
		Iterator<Node<T>> nodeIter = Gf.nodeIter();
		while(nodeIter.hasNext()){
			Node<T> u = nodeIter.next();
			System.out.println(u + ": " + u.nbr2Str());
		}
	}
	
	public DirectedGraph(){
		super(true);
	}
	
	public List<Node<T>> topoSort(){
		Iterator<Node<T>> nodeIter = this.nodeIter();
		List<Node<T>> list = new LinkedList<Node<T>>();
		while(nodeIter.hasNext()){
			Node<T> u = nodeIter.next();
			if(u.color == Color.WHITE){
				if(!topoVisit(u, list)) {
					System.err.println("TopoSort not possible due to cycle detected.");
					list.clear();
					return list; // It is not possible for toposort
				}
			}
		}
		return list;
	}
	
	private boolean topoVisit(Node<T> u, List<Node<T>> list){
		u.color = Color.GRAY;
		Iterator<Edge<T>> edgeIter = u.nbrIter();
		boolean res = true;
		while(edgeIter.hasNext()){
			Edge<T> e = edgeIter.next();
			// There is a cycle, so toposort is not possible
			// A back edge indicates a cycle.
			if(e.dst.color == Color.GRAY) return false;
			if(e.dst.color == Color.WHITE) res = topoVisit(e.dst, list);
			
		}
		u.color = Color.BLACK;
		list.add(0, u);
		return res;
	}
	
	/**
	 * Given a flow graph, and a source node s, sink node t,
	 * compute the maximum flow in this graph. 
	 * Here, we apply the Fork-Fulkerson method. 
	 * @param s The source node
	 * @param t The sink node
	 * @return The maximum flow
	 */
	public float maxFlow(Node<T> s, Node<T> t){
		if(s == null || t == null) {
			System.err.println("Null source / sink");
			return 0f;
		}
		DirectedGraph<T> Gf = this.constructResidualGraph();
		Node<T> sf = Gf.getNode(s.id), tf = Gf.getNode(t.id);
		float flow = 0f;
		while(Gf.findAugPath(sf, tf)){
			Gf.adjustFlow(sf, tf, this);
		}
		Iterator<Edge<T>> edgeIter = s.nbrIter();
		while(edgeIter.hasNext()){
			flow += edgeIter.next().flow;
		}
		return flow;
	}
	
	/**
	 * This must be called in a residential graph
	 * @param s
	 * @param t
	 * @param G
	 */
	private void adjustFlow(Node<T> s, Node<T> t, DirectedGraph<T> G){
		float f = computeAugFlow(s, t);
		Node<T> v = t;
		Node<T> u = t.parent;
		
		while(u != null){
			// (u, v) is contained in G
			if(G.getNode(u.id).hasNeighbor(v.id)){
				//u.edge(v).weight += f;
				u.edge(v).weight -= f;
				v.edge(u).weight += f;
				// Update edge value in graph G
				G.getNode(u.id).edge(v).flow += f;
			}
			else{
				//v.edge(u).weight -= f;
				v.edge(u).weight += f;
				u.edge(v).weight -= f;
				G.getNode(v.id).edge(u).flow -= f;
			}
			v = u;
			u = u.parent;
		}
		
	}
	
	/**
	 * Call in a residential Graph
	 * @param s
	 * @param t
	 * @return
	 */
	private float computeAugFlow(Node<T> s, Node<T> t){
		Node<T> v = t, u = t.parent;
		float min = Float.MAX_VALUE;
		while(u != null){
			Edge<T> e = u.nbrs.get(v.id);
			if(min > e.weight) min = e.weight;
			v = u;
			u = u.parent;
		}
		return min;
	}
	
	/**
	 * Use in Ford-Fulkerson method to locate the augmented path
	 * in each iteration. We implement the Edmonds-Karp algorithm
	 * by applying BFS in this step. Call in a residential graph as well.
	 * @param s The source node
	 * @param t The sink node
	 * @return True if there is an augmented path. 
	 */
	private boolean findAugPath(Node<T> s, Node<T> t){
		
		Iterator<Node<T>> nodeIter = this.nodeIter();
		while(nodeIter.hasNext()){
			Node<T> u = nodeIter.next();
			u.color = Color.WHITE;
			u.parent = null;
			u.weight = Float.MAX_VALUE;
		}
		Queue<Node<T>> queue = new LinkedList<Node<T>>();
		s.weight = 0f;
		s.color = Color.GRAY;
		queue.offer(s);
		while(!queue.isEmpty()){
			Node<T> u = queue.poll();
			// Find sink, return true
			if(u == t) return true;
			Iterator<Edge<T>> edgeIter = u.nbrIter();
			while(edgeIter.hasNext()){
				Edge<T> e = edgeIter.next();
				if(e.weight != 0 && e.dst.color == Color.WHITE){
					e.dst.color = Color.GRAY;
					e.dst.parent = u;
					e.dst.weight = u.weight + 1;
					queue.offer(e.dst);
				}
			}
			u.color = Color.BLACK;
		}
		return false;
		
	}
	
	/**
	 * Use in the maximum-flow algorithm.
	 * @return
	 */
	private DirectedGraph<T> constructResidualGraph(){
		DirectedGraph<T> Gf = new DirectedGraph<T>();
		Iterator<Edge<T>> iter = this.edgeIter();
		while(iter.hasNext()){
			Edge<T> e = iter.next();
			// Note that we shall use weight as capacity
			// basically clone the node for simplicity
			Gf.addEdge(e.src.id, e.dst.id, e.weight);
			Gf.addEdge(e.dst.id, e.src.id, 0f);
		}
		return Gf;
	}
	
}