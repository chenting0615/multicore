package edu.ucf.cs.multicore.project.model;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	private String label;
	private List<Node> neihboringNodes=new ArrayList<Node>();
	
	public Node(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return label != null ? label : super.toString();
	}
	
	public List<Node> getNeighboringNodes(){
		
		
		return neihboringNodes;
	}

	public void addNeighboringNode(Node target) {
		// TODO Auto-generated method stub
		this.neihboringNodes.add(target);
		
	}

}
