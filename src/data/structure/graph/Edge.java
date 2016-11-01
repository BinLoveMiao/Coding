package data.structure.graph;

public class Edge<T extends Comparable<T>> {
		T label;
		Node<T> src;
		Node<T> dst;
		float weight;
		float flow;

		private void init(Node<T> s, Node<T> d, T label, float w) {
			this.src = s;
			this.dst = d;
			this.label = label;
			this.weight = w;
			this.flow = 0f;
		}
		
		public Edge(Node<T> s, Node<T> d) {
			init(s, d, null, 0f);
		}
		
		public Edge(Node<T> s, Node<T> d, float w) {
			init(s, d, null, w);
		}

		public Edge(Node<T> s, Node<T> d, T val) {
			init(s, d, val, 0f);
		}

		public Edge(Node<T> s, Node<T> d, T val, float w) {
			init(s, d, val, w);
		}

		public void setWeight(float w) {
			this.weight = w;
		}
		
		public String toString(){
			return "(" + this.src.id + "," + 
					this.dst.id + ")" + "[" + this.weight + "]";
		}

	}