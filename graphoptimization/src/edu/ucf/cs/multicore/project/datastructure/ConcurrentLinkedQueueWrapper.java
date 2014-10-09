package edu.ucf.cs.multicore.project.datastructure;

import java.util.concurrent.ConcurrentLinkedQueue;

import edu.ucf.cs.multicore.project.model.Node;

public class ConcurrentLinkedQueueWrapper implements Queuable<Node>{

	public static ConcurrentLinkedQueue<Node> wrappedQueue=new ConcurrentLinkedQueue<Node>();
	@Override
	public void enqueue(Node element) {	
		wrappedQueue.add(element);
	}

	@Override
	public Node dequeue() throws Exception {
		return wrappedQueue.remove();
	}

	@Override
	public boolean isEmpty() {
		return wrappedQueue.isEmpty();
	}

}
