import java.util.ArrayList;
import java.util.Iterator;

public class Graph implements GraphADT {
	private Node[] listofNodes;
	private ArrayList<ArrayList<Edge>> nestedListofEdges;

	public Graph(int n) {
		listofNodes = new Node[n];
		for(int i=0;i<n;i++) {
			Node newNode= new Node(i);
			listofNodes[i] = newNode;
		}
		nestedListofEdges= new ArrayList<ArrayList<Edge>>();
		for (int i=0; i<n;i++) {
			ArrayList<Edge> filler = new ArrayList<Edge>();
			nestedListofEdges.add(filler);	
		}
		
	}
	
	private int findhelper(Node nodetofind) {
		int index =-1;
		for (int i=0;i<listofNodes.length;i++) {
			if (listofNodes[i]==nodetofind) {
				
				
				index=i;
				return index;
				
			}
		}
		return index;
	}

	@Override
	public void insertEdge(Node nodeu, Node nodev, int edgeType) throws GraphException {
		// adds an edge of a given type connecting u and v
		// throws a graph exception if the Nodes don't exist OR if in the graph there is already an edge connecting the nodes
		
		int indexNodeu = findhelper(nodeu);
		int indexNodev = findhelper(nodev);
		if ((indexNodeu==-1)||(indexNodev==-1)) {
			throw new GraphException("Either node u or node v do not exist or in the graph ");
		}
		
		if (getGraphEdge(nodeu, nodev)!=null) {
			throw new GraphException("There is already an edge connecting the nodes");
		}
		ArrayList<Edge> listatNodeu = nestedListofEdges.get(indexNodeu);
		ArrayList<Edge> listatNodev = nestedListofEdges.get(indexNodev);
		Edge connectingEdgeu = new Edge(nodeu, nodev, edgeType);
		Edge connectingEdgev = new Edge(nodeu, nodev, edgeType);
		nestedListofEdges.get(nodeu.getName()).add(connectingEdgeu);
		nestedListofEdges.get(nodev.getName()).add(connectingEdgev);
	}

	@Override
	public Node getNode(int name) throws GraphException {
		for (int i=0; i<listofNodes.length; i++) {
			if ((listofNodes[i]!=null)&&(listofNodes[i].getName()==name)) {
				return listofNodes[i];
			}
		}
		throw new GraphException("No node exists");
	}

	@Override
	public Iterator incidentEdges(Node u) throws GraphException {
		int indexNodeu = findhelper(u);
		if ((indexNodeu==-1)) {
			throw new GraphException("Node u do not exist or in the graph ");
		}
		ArrayList<Edge> listatNodeu = nestedListofEdges.get(indexNodeu);
		return listatNodeu.iterator();
	}

	@Override
	public Edge getEdge(Node u, Node v) throws GraphException {
		// returns the edge connecting u and v 
		// throws a graph exception if there is no edge between u and v
		int indexNodeu = findhelper(u);
		int indexNodev = findhelper(v);
		if ((indexNodeu==-1)||(indexNodev==-1)) {
			throw new GraphException("Either node u or node v do not exist or in the graph ");
		}
		
//		ArrayList<Edge> listatNodeu = nestedListofEdges.get(indexNodeu);
//		ArrayList<Edge> listatNodev = nestedListofEdges.get(indexNodev);
//		if (listatNodeu.isEmpty()||listatNodev.isEmpty()) {
//			System.out.println("the list of edges is for some reason empty");
//		}
		Edge result = getGraphEdge(u, v);
		if(result == null) {
			throw new GraphException("Graph edge does not exist");
		}
		return result;

	}
	
	@Override
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		
		Edge returnableValue = getGraphEdge(u,v);
		if (returnableValue==null) {
			return false;
		}
		return true;
	}
	private Edge getGraphEdge(Node u, Node v) throws GraphException{
		Edge temp = null;
		Iterator<Edge> iter1 = incidentEdges(u);
		if(iter1 != null) {
			while(iter1.hasNext()) {
				temp = iter1.next();
				if(temp.firstEndpoint().equals(u) && temp.secondEndpoint().equals(v)) {
					return temp;
				}
			}
		}
		Iterator<Edge> iter2 = incidentEdges(v);
		if(iter2 != null) {
			while(iter2.hasNext()) {
				temp = iter2.next();
				if(temp.firstEndpoint().equals(v) && temp.secondEndpoint().equals(u)) {
					return temp;
				}
			}
		}
		return null;
	}
}
	
	