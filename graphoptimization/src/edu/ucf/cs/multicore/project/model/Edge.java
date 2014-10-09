package edu.ucf.cs.multicore.project.model;

import org.jgrapht.graph.DefaultEdge;

public class Edge extends DefaultEdge{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7129547325985641106L;
	
	public Node getSource(){
		return (Node) super.getSource();
	}
	
	public Node getTarget(){
		return (Node) super.getTarget();
	}
	
	public Node getOtherSide(Node oneSide){
		if(getSource().equals(oneSide))
			return getTarget();
		if(getTarget().equals(oneSide))
			return getSource();
		return null;
	}
	
}
