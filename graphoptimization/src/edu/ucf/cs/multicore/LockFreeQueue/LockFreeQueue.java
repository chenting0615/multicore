package edu.ucf.cs.multicore.LockFreeQueue;

import java.util.concurrent.atomic.AtomicReference;

import edu.ucf.cs.multicore.project.model.Node;
import edu.ucf.cs.multicore.project.test.GraphOptimizationController;

public class LockFreeQueue {
	public AtomicReference<QNode> tail;
	public AtomicReference<QNode> head;
	public LockFreeQueue() {
		// TODO Auto-generated constructor stub
		QNode qNode = new QNode(new Node("-1"));
		this.head=new AtomicReference<QNode>(qNode);
		this.tail=new AtomicReference<QNode>(qNode);
	///	QNode qNode = tail.get();
	//	qNode.next.getAndSet(head.get());
	//	tail.getAndSet(qNode);
		
	}
	
	public boolean isEmpty(){
		return (tail.get() == head.get());
	}
	
	public void enqueue(Node node){
		QNode qnode=new QNode(node);
		while(true){
			QNode last=tail.get();
			QNode next=last.next.get();
			
			if(last==tail.get()){
				if(next==null){
					if(last.next.compareAndSet(next, qnode)){
						tail.compareAndSet(last, qnode);
						return;
					}
					else GraphOptimizationController.failCount++;
				}
				else{
					if(!tail.compareAndSet(last, next)) GraphOptimizationController.failCount++;
				}
			}
		}
	}
	
	public  Node dequeue() throws Exception{
		while(true){
			QNode first=head.get();
			QNode last=tail.get();
			QNode next=first.next.get();
			if(first==head.get()){
				if(first==last){
					if(next==null){
						throw new Exception();
					}
					if(!tail.compareAndSet(last, next)) GraphOptimizationController.failCount++;
				}
				else{
					Node value=next.value;
					if(head.compareAndSet(first, next)){
						return value;
					}
					else{
						GraphOptimizationController.failCount++;
					}
				}
			}
		}
	}
}
