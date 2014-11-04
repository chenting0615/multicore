package edu.ucf.cs.multicore.project.graphgenerator;

import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.ucf.cs.multicore.project.model.Graph;
import edu.ucf.cs.multicore.project.model.Node;
import edu.ucf.cs.multicore.project.model.NodeFactory;

public class FileLoadedGenerator implements GraphGenerator{

	private Map<Integer,List<Integer>> graph;

	public FileLoadedGenerator(Map<Integer,List<Integer>> graph) {
		this.graph = graph;
	}

	@Override
	public void generateGraph(Graph target,NodeFactory nodeFactory) {
		Set<Integer> nodeKeys = graph.keySet();
		Node[] nodes = new Node[nodeKeys.size()];
		for (Integer nodeIndex : nodeKeys) {
			nodes[nodeIndex] = nodeFactory.createNode(nodeIndex,String.format("%d",nodeIndex).toString());
			target.addNode(nodes[nodeIndex]);
		}
		for (Integer nodeIndex : nodeKeys) {
			for (Integer adj : graph.get(nodeIndex)) {
				target.addEdge(nodes[nodeIndex], nodes[adj]);
			}
		}
	}

}
