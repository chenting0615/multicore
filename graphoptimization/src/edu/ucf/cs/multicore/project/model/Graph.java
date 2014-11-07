package edu.ucf.cs.multicore.project.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.SimpleGraph;

public class Graph {

	private UndirectedGraph<Node, Edge> wrappedGraph;

	public Graph() {
		wrappedGraph = new SimpleGraph<Node, Edge>(
				Edge.class);
	}
	
	public UndirectedGraph<Node, Edge> AsGraph(){
		return wrappedGraph;
	}
	
	public void addNode(Node player){
		wrappedGraph.addVertex(player);
	}

	public void addEdge(Node source, Node target) {
		wrappedGraph.addEdge(source, target);
	}

	public void removeEdge(Node source, Node target) {	
		wrappedGraph.removeEdge(source, target);
	}

	public boolean containsEdge(Node source, Node target) {
		return wrappedGraph.containsEdge(source, target);
	}
	
	public Set<Node> nodeSet(){
		return wrappedGraph.vertexSet();
	}

	public List<Node> nodeList(){
		return new ArrayList<Node>(wrappedGraph.vertexSet());
	}

	public int degreeOf(Node source) {
		return wrappedGraph.degreeOf(source);
	}
	
	public Set<Edge> edgesOf(Node node){
		return wrappedGraph.edgesOf(node);
	}

	public List<Node> getAdjacentNodeList(Node node){
		Set<Edge> edgesOfNode = edgesOf(node);
		List<Node> adjacentNodes = new ArrayList<Node>();
		for (Edge edge : edgesOfNode) {
			adjacentNodes.add(edge.getOtherSide(node));
		}
		return adjacentNodes;
	}
	
	 public Node findNodeByIndex(Integer index){
	    	for (Node node: nodeList()) {
	    	if(node.getIndex().equals(index))
	    	return node;
	    	}
	    	return null;
	 }
	
	@Override
	public String toString() {
		return wrappedGraph.toString();
	}

}
