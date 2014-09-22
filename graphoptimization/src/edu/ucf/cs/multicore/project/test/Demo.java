package edu.ucf.cs.multicore.project.test;

import java.util.List;
import java.util.Random;

import edu.ucf.cs.multicore.project.graphgenerator.GraphGenerator;
import edu.ucf.cs.multicore.project.graphgenerator.WattsStrogatzBetaGenerator;
import edu.ucf.cs.multicore.project.model.Graph;
import edu.ucf.cs.multicore.project.model.Node;
import edu.ucf.cs.multicore.project.model.NodeFactory;

public class Demo {


	private static Graph graph;
	private static GraphGenerator graphGenerator;
	
	public static void main(String[] args) {
		init();
		Node sourceNode = findSourceNode();
		System.out.println(String.format("graph: %s\nnode: %s\ninDegree: %s\noutDegree: %s", graph,sourceNode,graph.inDegreeOf(sourceNode),graph.outDegreeOf(sourceNode)));
		//Test
	}
	
	private static void init() {
		int numberOfNodes = 50;
		int degree = 4;
		double beta = 0.5;
		graphGenerator = new WattsStrogatzBetaGenerator(numberOfNodes, degree, beta);
		graph = new Graph();
		graphGenerator.generateGraph(graph, new NodeFactory() {
			@Override
			public Node createNode(String label) {
				return new Node(label);
			}
		});
		
	}

	private static Node findSourceNode() {
		List<Node> nodeList = graph.nodeList();
		int randomIndex = new Random().nextInt(nodeList.size());
		return nodeList.get(randomIndex);
	}
	


}
