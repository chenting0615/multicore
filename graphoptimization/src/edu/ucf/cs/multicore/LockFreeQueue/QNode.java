package edu.ucf.cs.multicore.LockFreeQueue;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import edu.ucf.cs.multicore.project.model.Node;
public class QNode {

	public Node value;
	public AtomicReference<QNode> next;
	
	public QNode(Node node){
		this.value=node;
		next=new AtomicReference<QNode>(null);
	}
	
}
