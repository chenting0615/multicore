package edu.ucf.cs.multicore.project.graphgenerator;

import edu.ucf.cs.multicore.project.model.Graph;
import edu.ucf.cs.multicore.project.model.NodeFactory;

public interface GraphGenerator {
	
	public void generateGraph(Graph target,NodeFactory nodeFactory);

}
