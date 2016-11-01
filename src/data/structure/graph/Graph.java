package data.structure.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import data.structure.graph.Node.Color;



public class Graph<T extends Comparable<T>> {
	
	public static void main(String[] args){
		
		Graph<Integer> g = new Graph<Integer>();
		g.addEdge(1, 2, 5f);
		g.addEdge(2, 3, 3f);
		g.addEdge(1, 3, 1f);
		g.addEdge(2, 4, 2f);
		g.addEdge(3, 4, 8f);
		g.addEdge(6, 7, 2f);
		
		g.dfs();
		
		Iterator<Node<Integer>> iter = g.nodeIter();
		while(iter.hasNext()){
			Node<Integer> u = iter.next();
			System.out.println(u + ": " + u.start + ", " + u.finish);
		}
		
		g.dfsRecur();
		
		iter = g.nodeIter();
		while(iter.hasNext()){
			Node<Integer> u = iter.next();
			System.out.println(u + ": " + u.start + ", " + u.finish);
		}
	}

	public class NodeComparator implements Comparator<Node<T>>{

		@Override
		public int compare(Node<T> u, Node<T> v) {
			// TODO Auto-generated method stub
			return Float.compare(u.weight, v.weight);
		}
		
	}
	
	// We will always use id to index the nodes
	protected Map<Integer, Node<T>> nodes;
	protected int numNodes;
	protected int numEdges;
	protected List<Edge<T>> edges;
	
	protected boolean directed;
	protected int time;

	public Graph() {
		nodes = new HashMap<Integer, Node<T>>();
		edges = new ArrayList<Edge<T>>();
		directed = false;
		this.numEdges = 0;
		this.numNodes = 0;
		this.time = 0;
	}
	
	/**
	 * A boolean parameter use to determine whether current graph is directed.
	 * @param isDirect
	 */
	public Graph(boolean isDirect) {
		nodes = new HashMap<Integer, Node<T>>();
		edges = new ArrayList<Edge<T>>();
		directed = isDirect;
		this.numEdges = 0;
		this.numNodes = 0;
		this.time = 0;
	}

	public Iterator<Node<T>> nodeIter() {
		return nodes.values().iterator();
	}
	
	public Iterator<Edge<T>> edgeIter() {
		return edges.iterator();
	}
	
	/**
	 * Add edge specified by source node and destination node.
	 * Make sure that the source and dest nodes are all existed.
	 * @param src
	 * @param dst
	 * @param weight
	 * @return
	 */
	public void addEdge(Node<T> src, Node<T> dst, float weight){
		//assert(this.nodes.containsKey(src.id) && this.nodes.containsKey(dst.id));
		if(!this.nodes.containsKey(src.id)) {
			this.nodes.put(src.id, src);
			++this.numNodes;
		}
		if(!this.nodes.containsKey(dst.id)) {
			this.nodes.put(dst.id, dst);
			++this.numNodes;
		}
		if(!src.nbrs.containsKey(dst.id)){
			Edge<T> e = new Edge<T>(src, dst, weight);
			src.nbrs.put(dst.id, e);
			// Adding edge to the graph
			this.edges.add(e);
			++numEdges; // increase the number of edges
			// If this is undirected graph, we should further add edge of the
			// opposite direction. 
			if(!directed && !dst.nbrs.containsKey(src.id)){
				dst.nbrs.put(src.id, new Edge<T>(dst, src, weight));
			}
		}
	}
	
	/**
	 * We focus on graph algorithm, and donot need label in this stage
	 * @param id1
	 * @param id2
	 * @param weight
	 */
	public void addEdge(int id1, int id2,  float weight){
		if(!nodes.containsKey(id1)){
			nodes.put(id1, new Node<T>(id1));
			++numNodes;
		}
		
		if(!nodes.containsKey(id2)){
			nodes.put(id2, new Node<T>(id2));
			++numNodes;
		}
		
		Node<T> src = nodes.get(id1);
		Node<T> dst = nodes.get(id2);
		this.addEdge(src, dst, weight);
	}

	
	public Node<T> getNode(int id){
		return nodes.containsKey(id) ? nodes.get(id) : null;
	}
	
	public void bfs(Node<T> s){
		// check the source node existed
		assert(nodes.containsKey(s.id));
		Queue<Node<T>> queue = new LinkedList<Node<T>>();
		
		Iterator<Node<T>> iter = this.nodeIter();
		
		while(iter.hasNext()){
			Node<T> u = iter.next();
			u.weight = Float.MAX_VALUE;
		}
		
		// Initialize as Gray before putting into the queue.
		// indicating that it is already touched (visited).
		s.color = Color.GRAY;
		s.weight = 0f;
		queue.add(s);
		
		while(!queue.isEmpty()){
			Node<T> u = queue.poll();
			System.out.print(u + "->");
			Iterator<Edge<T>> edgeIter = u.nbrIter();
			while(edgeIter.hasNext()){
				Edge<T> e = edgeIter.next();
				Node<T> v = e.dst;
				if(v.color == Color.WHITE){
					v.color = Color.GRAY;
					v.weight = u.weight + 1;
					v.parent = u;
					queue.add(v);
				}
			}
			u.color = Color.GRAY;
		}
		System.out.println();
	}
	
	public void dfsRecur(){
		this.time = 0;
		Iterator<Node<T>> iter = this.nodeIter();
		while(iter.hasNext()){
			Node<T> node = iter.next();
			if(node.color == Color.WHITE){
				dfsVisit(node);
			}
		}
	}

	/**
	 * Process dfs search recursively started from a given node
	 * @param node
	 */
	public void dfsVisit(Node<T> node){
		node.color = Color.GRAY;
		node.start = ++this.time;
		System.out.print("(" + node + ", ");
		Iterator<Edge<T>> iter = node.nbrIter();
		while(iter.hasNext()){
			Node<T> v = iter.next().dst;
			if(v.color == Color.WHITE){
				//v.color = Color.GRAY;
				v.parent = node;
				dfsVisit(v);
			}
		}
		node.color = Color.BLACK;
		node.finish = ++this.time;
		System.out.print(")");
	}
	
	public void dfs(){
		Iterator<Node<T>> iter = this.nodeIter();
		Stack<Node<T>> stack = new Stack<Node<T>>();
		int time = 0;
		Node<T> prev = null;
		while(iter.hasNext()){
			Node<T> s = iter.next();
			if(s.color == Color.WHITE){
				prev = null;
				s.color = Color.GRAY;
				s.start = ++time;
				stack.push(s);
				while(!stack.isEmpty()){
					Node<T> u = stack.peek();
					if(prev != null && prev.parent == u){
						stack.pop();
						u.finish = ++time;
						prev = u;
						continue;
					}
					//System.out.print(u + "->");
					Iterator<Edge<T>> eIter = u.nbrIter();
					boolean hasWhiteNode = false;
					while(eIter.hasNext()){
						Node<T> v = eIter.next().dst;
						if(v.color == Color.WHITE){
							hasWhiteNode = true;
							v.color = Color.GRAY;
							v.parent = u;
							v.start = ++time;
							stack.push(v);
						}
					}
					if(!hasWhiteNode){
						stack.pop();
						u.finish = ++time;
						prev = u;
					}
				}
			}
		}
	}
	
	public void dijkstra(Node<T> src){
		assert(this.nodes.containsKey(src.id));
		//Set<Node<T>> unvisited = new HashSet<Node<T>>();
		PriorityQueue<Node<T>> queue = new PriorityQueue<Node<T>>(new NodeComparator());
		Iterator<Node<T>> nodeIter = this.nodeIter();
		//int count = this.numNodes;
		while(nodeIter.hasNext()){
			Node<T> u = nodeIter.next();
			if(u.id == src.id){
				u.weight = 0f;
			}
			else u.weight = Float.MAX_VALUE;
			u.color = Color.WHITE;
			//unvisited.add(u);
			queue.offer(u);
		}
		
		while(!queue.isEmpty()){
			Node<T> u = queue.poll();
			Iterator<Edge<T>> edgeIter = u.nbrIter();
			while(edgeIter.hasNext()){
				Edge<T> e = edgeIter.next();
				if(e.dst.color != Color.BLACK){
					if(e.dst.weight > u.weight + e.weight){
						e.dst.weight = u.weight + e.weight;
						// The e.dst may be reinserted.
						// We are not affected by such duplicate due to the bfs search order
						queue.offer(e.dst);
					}
				}
			}
			u.color = Color.BLACK;
		}
	}

}
