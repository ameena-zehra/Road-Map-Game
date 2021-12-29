
public class Edge {
	private Node u;
	private Node v;
	private int type;
	public Edge(Node u, Node v, int type) {
		this.u =u;
		this.v=v;
		this.type = type;
	}
	
	public Node firstEndpoint() {
		return u;
	}
	
	public Node secondEndpoint() {
		return v;
	}
	
	public int getType() {
		return type;
	}

}
