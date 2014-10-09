package edu.ucf.cs.multicore.project.datastructure.LockFreeQueue;

import java.util.concurrent.atomic.AtomicReference;

import edu.ucf.cs.multicore.project.model.Node;
class QNode {

	public Node value;
	public AtomicReference<QNode> next;
	
	public QNode(Node node){
		this.value=node;
		next=new AtomicReference<QNode>(null);
	}
	
}
