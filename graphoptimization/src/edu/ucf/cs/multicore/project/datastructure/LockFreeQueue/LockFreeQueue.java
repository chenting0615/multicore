package edu.ucf.cs.multicore.project.datastructure.LockFreeQueue;

import java.util.concurrent.atomic.AtomicReference;

import edu.ucf.cs.multicore.project.datastructure.Queuable;
import edu.ucf.cs.multicore.project.model.Node;

public class LockFreeQueue implements Queuable<Node> {
	public AtomicReference<QNode> tail;
	public AtomicReference<QNode> head;
	private int casFailCount;

	public LockFreeQueue() {
		QNode qNode = new QNode(new Node(-1, "-1", null));
		this.head = new AtomicReference<QNode>(qNode);
		this.tail = new AtomicReference<QNode>(qNode);
		casFailCount = 0;
	}

	public boolean isEmpty() {
		return (tail.get() == head.get());
	}

	public void enqueue(Node node) {
		QNode qnode = new QNode(node);
		while (true) {
			QNode last = tail.get();
			QNode next = last.next.get();

			if (last == tail.get()) {
				if (next == null) {
					if (last.next.compareAndSet(next, qnode)) {
						tail.compareAndSet(last, qnode);
						return;
					} else
						casFailCount++;
				} else {
					if (!tail.compareAndSet(last, next))
						casFailCount++;
				}
			}
		}
	}

	public Node dequeue() throws Exception {
		while (true) {
			QNode first = head.get();
			QNode last = tail.get();
			QNode next = first.next.get();
			if (first == head.get()) {
				if (first == last) {
					if (next == null) {
						throw new Exception();
					}
					if (!tail.compareAndSet(last, next))
						casFailCount++;
				} else {
					Node value = next.value;
					if (head.compareAndSet(first, next)) {
						return value;
					} else {
						casFailCount++;
					}
				}
			}
		}
	}
	
	public Integer getCasFailCount(){
		return casFailCount;
	}

}
