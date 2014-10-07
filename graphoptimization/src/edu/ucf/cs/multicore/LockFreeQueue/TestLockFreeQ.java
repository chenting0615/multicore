package edu.ucf.cs.multicore.LockFreeQueue;

import edu.ucf.cs.multicore.project.model.Node;

public class TestLockFreeQ {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Node n1= new Node("1");
		Node n2= new Node("2");
		Node n3= new Node("3");
		
		LockFreeQueue lfq=new LockFreeQueue();
		lfq.enqueue(n1);
		lfq.enqueue(n2);
		lfq.enqueue(n3);
		try {
			System.out.println(lfq.dequeue().toString());
			System.out.println(lfq.dequeue().toString());
			System.out.println(lfq.dequeue().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
