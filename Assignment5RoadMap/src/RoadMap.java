import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Stack;
import java.util.Iterator;
public class RoadMap {
	
	private Graph graph;
	private int start;
	private int destination;
	private int width;
	private int length;
	private int initialBudget;
	private int toll;
	private int gain;
	private Stack<Node> pathofNodes; // Path stack
//	private Stack<Node> pathwithBacktrack; // Path stack
	
	public RoadMap(String inputFile) throws MapException{
		System.out.println("is this ever entered?");
		FileReader fr = null;
		try {
			fr = new FileReader(inputFile);
		} catch (FileNotFoundException e) {
			throw new MapException("File does not exist");
		}
        Scanner inFile = new Scanner(fr);
        initializeVariables(inFile);
        
        
	}
	public Graph getGraph(int n) {
		this.graph= new Graph(n);
		return graph;
	}
	public Iterator<Node> findPath(int start, int destination, int initialMoney) {
		Node startNode = new Node(start);
		return stackofNodesDFS(startNode, destination).iterator();
	}
	
	public int getStartingNode() {
		return start;
	}
	public int getDestinationNode() {
		return destination;
	}
	public int getInitialMoney() {
		return initialBudget;
	}
	
	private void initializeVariables(Scanner inFile) {
		System.out.println("have the variables been initialized?");
		String line;
        // Read the first line from the file.
        line = inFile.nextLine();
        int i=1;
        String sequence="";
        while (inFile.hasNext()) {
        	line = inFile.nextLine();
        	if (i==1) {
        		start = Integer.parseInt(line);
        	}
        	if (i==2) {
        		destination = Integer.parseInt(line);
        	}
        	if (i==3) {
        		width = Integer.parseInt(line);
        	}
        	if (i==4) {
        		length = Integer.parseInt(line);
        	}
        	if (i==5) {
        		initialBudget = Integer.parseInt(line);
        	}
        	if (i==6) {
        		toll = Integer.parseInt(line);
        	}
        	if (i==7) {
        		gain = Integer.parseInt(line);
        	}
        	if (i>7) {
        		sequence+= line;
        	}
        	i++;
        }
        System.out.println(sequence);
        System.out.println(sequence.length());
        int widthSeq= (width*2)-1;
        int lengthSeq= (length*2)-1;
        System.out.println(widthSeq+" "+ lengthSeq);
        char[][] completeEdgeSequence= new char[lengthSeq+1][widthSeq+1];
        int counterforSequence=0;
        
        for (i=0; i<lengthSeq; i++) {
        	for (int j=0; j<widthSeq; j++) {
        		completeEdgeSequence[i][j]= sequence.charAt(counterforSequence);
        		
        		counterforSequence++;
        		
        		System.out.print(completeEdgeSequence[i][j]);
        	}
        	System.out.println();;
        }
        String horizontalLinks = getHorizontalLinks(completeEdgeSequence, widthSeq, lengthSeq);
        String verticalLinks = getVerticalLinks(completeEdgeSequence, widthSeq, lengthSeq);
        graph = getGraph(width*length);
        
        createConnections(horizontalLinks, verticalLinks);

//        Node one = graph.getNode(1);
//        Iterator<Edge> edgeIterator = graph.incidentEdges(one);
//        while (edgeIterator.hasNext()) { 
//        	System.out.println("THESE ARE THE EDGES "+edgeIterator.next().secondEndpoint().getName());
//        }
        
        
        // Vertical Links --> ODD Numbers
        // Horizontal Links --> EVEN Numbers
        
        // Create a list of horizontal links
        // Create a list of vertical links
	}
//	private void findPathhelper(Node startNode, int destination) {
//		stackofNodesDFS(startNode, destination);
//		
//	}
	
	@SuppressWarnings("unchecked")
	private Stack<Node> stackofNodesDFS(Node startNode, int destination){
		Stack<Node> stack = new Stack<Node>();
		System.out.println(startNode.getName());
		startNode.setMark(true);
		stack.push(startNode);
		if (startNode.getName()==destination) {
			return stack;
		}
		@SuppressWarnings("unchecked")
		Iterator<Edge> edgeIterator = graph.incidentEdges(startNode);
		while(edgeIterator.hasNext()) {
			Stack<Node> subgraph = new Stack<Node>();
			Node otherNode = edgeIterator.next().secondEndpoint();
			System.out.println("OTHER NODE "+ otherNode.getName());
//			if (otherNode.getName()!=startNode.getName()) {
			if (otherNode.getMark()==false) {
				int edgeType = graph.getEdge(startNode, otherNode).getType();
			
				if ((canCross(edgeType))) {
					subgraph = stackofNodesDFS(otherNode, destination);
					
				}
				if (subgraph!=null) {
					while (subgraph.isEmpty()==false) {
						stack.push(subgraph.pop());
					}
					return stack;
				}
			}
		}
		stack.peek().setMark(false);
		stack.pop();
		return null;
	
	}
//	private boolean nextNodeDFS(Node startNode) {
////		System.out.println("START NODE  "+ startNode.getName());
//		startNode.setMark(true);
//		pathofNodes.push(startNode);
//		if(startNode.getName()==destination) {
//			return true;
//		}
//		Iterator<Edge> edgeIterator = graph.incidentEdges(startNode);
//		
//		while (edgeIterator.hasNext()) {
//			
//			Node otherNode = edgeIterator.next().secondEndpoint();
//			if (otherNode.getName()!=startNode.getName()) {
//				int edgeType = graph.getEdge(startNode, otherNode).getType();
//			
//				if ((otherNode.getMark()==false)&&(canCross(edgeType))) {
//					if (nextNodeDFS(otherNode)) {
//						return true;
//					}
//				}
//			}
//		}
//		pathofNodes.peek().setMark(false);
//		pathofNodes.pop();
//		return false;
////		nextNodeDFS(prevNode);
//		
//	}
	private boolean canCross(int type) {
		// T or private
		if ((type==0)&&(toll-initialBudget>=0)) {
			initialBudget -=toll;
			return true;
		}
		// F or public
		if ((type==1)) {
			return true;
		}
		// C or reward
		if ((type==2)) {
			initialBudget +=gain;
			return true;
		}
		// X or dead end or over budget
		return false;
		
	}

	
	private String getVerticalLinks(char[][] completeEdgeSequence, int widthSeq, int lengthSeq ){

		String verticalString="";
		for (int i=0;i<lengthSeq;i++) {
			for (int j=0; j<widthSeq; j++) {
				if ((i%2!=0)&&(j%2==0)) {
					verticalString+=completeEdgeSequence[i][j];
				}
        	}
		}

		return verticalString;
		
	}
	
	private String getHorizontalLinks(char[][] completeEdgeSequence, int widthSeq, int lengthSeq ){

		String horizontalString="";
		for (int i=0;i<lengthSeq;i++) {
			for (int j=0; j<widthSeq; j++) {
				if ((i%2==0)&&(j%2!=0)) {
					horizontalString+=completeEdgeSequence[i][j];
				}
        	}
		}

		
		return horizontalString;
	}
	
	
	private int getIDforchar(char id) {
		if (id=='T') {
			return 0;
		}
		if (id=='F') {
			return 1;
		}
		if (id=='C') {
			return 2;
		}
		if (id=='X') {
			return 3;
		}
		return -1;
	}
	private void createConnections(String horizontalString, String verticalString) {
		int largerCounter= 0;
		int trackerforString= 0;
		
		for(int i=0; i<length;i++) {
			for(int j=0; j<width;j++) {
				if ((largerCounter+1)%width!=0){
					// set the edge between the two nodes
//					System.out.println("LARGERCOUNTER "+largerCounter);
					int edgeId= getIDforchar(horizontalString.charAt(trackerforString));
					graph.insertEdge(graph.getNode(largerCounter), graph.getNode(largerCounter+1), edgeId );
					trackerforString++;
					System.out.println(i+" "+j);
				}
				largerCounter++;
			}
		}
		
		
		
		largerCounter= 0;
		trackerforString= 0;
		for(int i=0; i<length;i++) {
			for(int j=0; j<width;j++) {
				if (((i+1)!=length)) {
					// set the edge between the two nodes
//					System.out.println(largerCounter);
					int edgeId= getIDforchar(verticalString.charAt(trackerforString));
					graph.insertEdge(graph.getNode(largerCounter), graph.getNode(largerCounter+width), edgeId );
					trackerforString++;
				}
				largerCounter++;
			}
		}
		
	}
	

}
