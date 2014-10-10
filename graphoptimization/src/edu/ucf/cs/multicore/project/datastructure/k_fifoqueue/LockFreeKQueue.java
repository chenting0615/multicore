package edu.ucf.cs.multicore.project.datastructure.k_fifoqueue;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

import edu.ucf.cs.multicore.project.datastructure.Queuable;
import edu.ucf.cs.multicore.project.model.Node;

/**
 * 
 * based on  <a href="http://www.cosy.sbg.ac.at/research/tr/2012-04_Kirsch_Lippautz_Payer.pdf">http://www.cosy.sbg.ac.at/research/tr/2012-04_Kirsch_Lippautz_Payer.pdf</a>
 *
 */

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

	public void enqueue(Node node) {
		AtomicReference<KQNode> newItem = new AtomicReference<KQNode>(new KQNode(node));
		while (true) {
			AtomicReference<KQSegment> oldTail = tail;
			AtomicReference<KQSegment> next = oldTail.get().next;
			Integer index = findEmptySlot(oldTail);
			if(oldTail == tail){ //checks if the k-FIFO queue state has been consistently observed by checking whether tail changed in the meantime which would trigger a retry
				if(index != null && oldTail.get().nodes.get(index) == null){ //an empty slot is found 
					AtomicReference<KQNode> oldItem = oldTail.get().nodes.get(index);
					if (oldItem == null) {
						if (oldTail.get().nodes.compareAndSet(index, oldItem,newItem)) { // tries to insert the item at the location of the empty slot using a compare-and-swap (CAS) operation
							if (commited(oldTail, newItem, index))  //validate the insertion
								return;
						}else
							casFailCount++;
					}
				}else{ //no empty slot is found in the current tail k-segment
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
			if (oldHead == head) {// checks if the queue state has been consistently observed by checking whether head changed in the meantime which would trigger a retry 
				if(index != null && oldHead.get().nodes.get(index) != null){ // an item was found
					AtomicReference<KQNode> oldItem = oldHead.get().nodes.get(index);
					if(oldHead.get().equals(oldTail.get())){
						advanceTail(oldTail, next);//the method tries to increment tail by k to prevent starvation of items in the queue and to provide a linearizable empty check
					}
					AtomicReference<KQNode> emptyItem = new AtomicReference<KQNode>(null);
					if(oldHead.get().nodes.compareAndSet(index,oldItem, emptyItem)){
						return oldItem.get().value.get();
					}else
						casFailCount++;
				}else{
					if(oldHead.get().equals(oldTail.get()) && oldTail.get().equals(tail.get()))
						return null;
			
					advanceHead(oldHead, next);//tries to increment head and performs a retry
				}
			}
		}
	}

	/**
	 * validates an insertion
	 * @param oldTail
	 * @param newItem
	 * @param index
	 * @return
	 */
	private boolean commited(AtomicReference<KQSegment> oldTail, AtomicReference<KQNode> newItem,
			Integer index) {
		if(oldTail.get().nodes.get(index).get() != newItem.get()) //the inserted item already got dequeued at validation time by a concurrent thread
			return true;
		AtomicReference<KQSegment> currentHead = head;
		AtomicReference<KQSegment> currentTail = tail;
		AtomicReference<KQNode> emptyItem = new AtomicReference<KQNode>(null);
		if(inQueueAfterHead(oldTail,currentTail,currentHead)) //the tail k-segment where the item was inserted is in between the current head k-segment and the current tail k-segment but not equal to the current head k-segment
			return true;
		else if(!inQueue(oldTail, currentTail, currentHead)){ // the inserted item already got dequeued at validation time by a concurrent thread 
			if(!oldTail.get().nodes.compareAndSet(index,newItem, emptyItem)){ // so try to undo the insertion using CAS 
				casFailCount++;
				return true;
			}
		}else{
			AtomicReference<KQSegment> newHead = head.get().next;
			if(head.compareAndSet(currentHead.get(), newHead.get())){// a race with concurrent dequeueing threads may occur which may not have observed the insertion and may try to advance the head pointer in the meantime. This would result in loss of the inserted item. To prevent that the method tries to increment the ABA counter in the head atomic value using CAS
				return true;
			}else{
				casFailCount++;
			}
			//If this fails a concurrent dequeue operation may have changed head which would make the insertion potentially
			//invalid. Hence after that the method tries to undo the insertion using CAS			
			if(!oldTail.get().nodes.compareAndSet(index,newItem, emptyItem)){//the inserted item already got dequeued at validation time by a concurrent thread
				casFailCount++;
				return true;
			}
		}
		return false;
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
	
/**
 * The find item method randomly selects an index in
 between [head , head + k] and then linearly searches for an item starting with the selected index wrapping around at index head + k −1	
 * @param oldHead
 * @return
 */
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

	/**
	 * The find empty slot method randomly selects
	 * an index in between [tail , tail + k] and then linearly searches at most TESTS ≥1
	 * array locations for an empty slot starting with the selected index wrapping around at
	 * index tail + k −1.
	 * @param oldTail
	 * @return
	 */
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
	

	public boolean isEmpty() {
		return (tail.get() == head.get());
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
