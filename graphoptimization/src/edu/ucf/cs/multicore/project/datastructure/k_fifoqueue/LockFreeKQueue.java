package edu.ucf.cs.multicore.project.datastructure.k_fifoqueue;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

import edu.ucf.cs.multicore.project.datastructure.Queuable;
import edu.ucf.cs.multicore.project.model.Node;

public class LockFreeKQueue implements Queuable<Node> {
	private Integer K = 4;
	private Integer TESTS = K;
	public AtomicReference<KQSegment> tail;
	public AtomicReference<KQSegment> head;
	private int casFailCount;
	private Random rand;

	public LockFreeKQueue(int K, int TESTS) {
		this.K = K;
		this.TESTS = TESTS;
		KQSegment kQSegment = new KQSegment(this.K);
		kQSegment.next.set(kQSegment);
		this.head = new AtomicReference<KQSegment>(kQSegment);
		this.tail = new AtomicReference<KQSegment>(kQSegment);
		casFailCount = 0;
		rand = new Random();
	}

	public boolean isEmpty() {
		return (tail.get() == head.get());
	}

	public void enqueue(Node node) {
		AtomicReference<KQNode> newItem = new AtomicReference<KQNode>(new KQNode(node));
		while (true) {
			AtomicReference<KQSegment> oldTail = tail;//.get();
			AtomicReference<KQSegment> next = oldTail.get().next;
			Integer index = findEmptySlot(oldTail);
			if(oldTail == tail){
				if(index != null && oldTail.get().nodes.get(index) == null){
					AtomicReference<KQNode> oldItem = oldTail.get().nodes.get(index);
					if (oldItem == null) {
						if (oldTail.get().nodes.compareAndSet(index, oldItem,
								newItem)) {
							if (commited(oldTail, newItem, index))
								return;
						}else
							casFailCount++;
					}
				}else{
					advanceTail(oldTail, next);
				}
			}
			
		}
	}

	public Node dequeue() throws Exception {
		while (true) {
			AtomicReference<KQSegment> oldTail = tail;
			AtomicReference<KQSegment> oldHead = head;
			AtomicReference<KQSegment> next = oldHead.get().next;
			Integer index = findItem(oldHead);
			if (oldHead == head) {
				if(index != null && oldHead.get().nodes.get(index) != null){
					AtomicReference<KQNode> oldItem = oldHead.get().nodes.get(index);
					if(oldHead.get().equals(oldTail.get())){
						advanceTail(oldTail, next);
					}
					AtomicReference<KQNode> emptyItem = new AtomicReference<KQNode>(null);
					if(oldHead.get().nodes.compareAndSet(index,oldItem, emptyItem)){
						return oldItem.get().value.get();
					}else
						casFailCount++;
				}else{
					if(oldHead.get().equals(oldTail.get()) && oldTail.get().equals(tail.get()))
						return null;
			
					advanceHead(oldHead, next);
				}
			}
		}
	}

	private void advanceHead(AtomicReference<KQSegment> oldHead,
			AtomicReference<KQSegment> next) {
		if(next.get() != null){
			casFailCount++;
			if(!head.compareAndSet(oldHead.get(), next.get()))
				casFailCount++;
		}
	}
	
	private void advanceTail(AtomicReference<KQSegment> oldTail,
			AtomicReference<KQSegment> next) {
		if(next.get() == null || oldTail.get().equals(next.get())){
			if(oldTail.get().next.compareAndSet(next.get(), new KQSegment(K))){
				if(!tail.compareAndSet(oldTail.get(), oldTail.get().next.get()))
					casFailCount++;
			}else{
				casFailCount++;
			}
		}else{
			if(!tail.compareAndSet(oldTail.get(), next.get()))
				casFailCount++;
		}
	}	

	private boolean commited(AtomicReference<KQSegment> oldTail, AtomicReference<KQNode> newItem,
			Integer index) {
		if(oldTail.get().nodes.get(index).get() != newItem.get())
			return true;
		AtomicReference<KQSegment> currentHead = head;
		AtomicReference<KQSegment> currentTail = tail;
		AtomicReference<KQNode> emptyItem = new AtomicReference<KQNode>(null);
		if(inQueueAfterHead(oldTail,currentTail,currentHead))
			return true;
		else if(!inQueue(oldTail, currentTail, currentHead)){
			if(!oldTail.get().nodes.compareAndSet(index,newItem, emptyItem)){
				casFailCount++;
				return true;
			}
		}else{
			AtomicReference<KQSegment> newHead = head.get().next;
			if(head.compareAndSet(currentHead.get(), newHead.get())){
				return true;
			}else{
				casFailCount++;
			}
			if(!oldTail.get().nodes.compareAndSet(index,newItem, emptyItem)){
				casFailCount++;
				return true;
			}
		}
		return false;
	}

	private boolean inQueue(AtomicReference<KQSegment> oldTail, AtomicReference<KQSegment> currentTail,
			AtomicReference<KQSegment> currentHead) {
		AtomicReference<KQSegment> currentSegment = currentHead;
		while(!currentSegment.get().equals(currentTail.get())){
			if(currentSegment.get().equals(oldTail.get()))
				return true;
			currentSegment = currentSegment.get().next; 
		}
		if(currentSegment.get().equals(oldTail.get()))
				return true;
		return false;
	}

	private boolean inQueueAfterHead(AtomicReference<KQSegment> oldTail, AtomicReference<KQSegment> currentTail,
			AtomicReference<KQSegment> currentHead) {
		if(currentHead.get().equals(oldTail.get()))
			return false;
		return inQueue(oldTail, currentTail, currentHead);
	}
	
	private Integer findItem(AtomicReference<KQSegment> oldHead) {
		AtomicReferenceArray<AtomicReference<KQNode>> nodes = oldHead.get().nodes;
		int nextInt = rand.nextInt(K);
		int i = nextInt - 1;
		do{
			if(i == K - 1)
				i = 0;
			else
				i++;
			if(nodes.get(i) != null && nodes.get(i).get() != null)
				return i;
		}while( i != ((nextInt+K-1) % K));
		return null;
	}	

	private Integer findEmptySlot(AtomicReference<KQSegment> oldTail) {
		AtomicReferenceArray<AtomicReference<KQNode>> nodes = oldTail.get().nodes;
		int nextInt = rand.nextInt(K);
		int i = nextInt - 1;
		int numberOfSearches = 0;
		do{
			if(i == K - 1)
				i = 0;
			else
				i++;
			if(nodes.get(i) == null)
				return i;
			numberOfSearches++;
		}while( (i != ((nextInt+K-1) % K)) && numberOfSearches <= TESTS);
		return null;
	}
	
	public Integer getCasFailCount(){
		return casFailCount;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		AtomicReference<KQSegment> currentSegment = head;
		while(!currentSegment.get().equals(tail.get())){
			sb.append("[");
			sb.append(currentSegment.get().toString());
			sb.append("]->");
			currentSegment = currentSegment.get().next; 
		}
		sb.append("[");
		sb.append(tail.get().toString());
		sb.append("]");
		return sb.toString();
	}

}
