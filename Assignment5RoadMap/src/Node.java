
public class Node {
	
	private int name;
	private boolean mark;
	// constructor for the class and it creates a node with the given name
	// the name of a node is an integer value between 0 to n-1 where n is the number of nodes in the graph
	public Node(int name) {
		this.name =name;
		mark=false;
	}
	
	public void setMark(boolean mark) {
		this.mark = mark;
	}
	
	public int getName() {
		return name;
	}
	
	public boolean getMark() {
		return mark;
	}

}
