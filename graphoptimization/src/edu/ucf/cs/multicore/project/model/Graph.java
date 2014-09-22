package edu.ucf.cs.multicore.project.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

public class Graph {

	private DirectedGraph<Node, Edge> wrappedGraph;

	public Graph() {
		wrappedGraph = new DefaultDirectedGraph<Node, Edge>(
				Edge.class);
	}
	
	public DirectedGraph<Node, Edge> AsGraph(){
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

	public int outDegreeOf(Node source) {
		return wrappedGraph.outDegreeOf(source);
	}

	public int inDegreeOf(Node node) {
		return wrappedGraph.inDegreeOf(node);
	}
	
	@Override
	public String toString() {
		return wrappedGraph.toString();
	}

}
