package edu.ucf.cs.multicore.project.model;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	private String label;
	private Integer index;
	private Graph graph;
	private List<Node> neihboringNodes;
	
	public Node(Integer index, String label, Graph graph) {
		this.label = label;
		this.graph = graph;
		this.index = index;
	}

	@Override
	public String toString() {
		return label != null ? label : super.toString();
	}
	
	public List<Node> getNeighboringNodes(){
		if(neihboringNodes == null){
			neihboringNodes = new ArrayList<Node>();
			neihboringNodes = graph.getAdjacentNodeList(this);
		}
		return neihboringNodes;
	}

	public Integer getIndex(){
		return this.index;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	
	
	

}
