package edu.ucf.cs.multicore.project.datastructure.k_fifoqueue;

import java.util.concurrent.atomic.AtomicReference;

import edu.ucf.cs.multicore.project.model.Node;

class KQNode {

	public AtomicReference<Node> value;

	public KQNode(Node node) {
		this.value = new AtomicReference<Node>(node);
	}
	
	@Override
	public String toString() {
		return value.get().toString();
	}

}
