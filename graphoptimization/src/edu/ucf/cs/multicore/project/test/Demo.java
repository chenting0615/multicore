package edu.ucf.cs.multicore.project.test;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.xml.crypto.NodeSetData;

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
		System.out.println("Enter the source node");
		Scanner s=new Scanner(System.in);
		int source=s.nextInt();
		System.out.println("Enter the destination node");
		int dest=s.nextInt();
		Node sourceNode = findSpecificNode(source);
		Node destNode=findSpecificNode(dest);
		SequentialBFS seqBFSObj=new SequentialBFS();
		List<Node> nlist=sourceNode.getNeighboringNodes(sourceNode);
		
		seqBFSObj.searchNodeUsingSequentialBFS(sourceNode, destNode);
		
		/*for(int i=0;i<nlist.size();i++){
			System.out.println(sourceNode.toString()+":"+nlist.get(i));
		}*/
		
		//seqBFSObj.searchNodeUsingSequentialBFS(sourceNode, destNode)
		//findDestNode(sourceNode,dest);
			
		//System.out.println(String.format("graph: %s\nnode: %s\ninDegree: %s\noutDegree: %s", graph,sourceNode,graph.inDegreeOf(sourceNode),graph.outDegreeOf(sourceNode)));
		//Test
	}
	
	private static Node findSpecificNode(int index) {
		// TODO Auto-generated method stub
		List<Node> nodeList = graph.nodeList();
		return nodeList.get(index);
		
	}

	private static void findDestNode(Node sourceNode, int dest) {
		// TODO Auto-generated method stub
		//List<Node> nlist=sourceNode.getNeighboringNodes(sourceNode);
		for(int i=0;i<graph.nodeList().size();i++){
			if(sourceNode.toString().equals("Node"+" "+dest+"")){
				System.out.println("FOund....");
				System.exit(0);
			}
			else{
				List<Node> nlist=sourceNode.getNeighboringNodes(sourceNode);
				for(int j=0;j<nlist.size();j++){
					Node node=nlist.get(j);
					System.out.println("CurrentNode:"+node.toString());
					findDestNode(node, dest);
				}
			}
			
		}
	}

	private static void init() {
		int numberOfNodes = 10000;
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
