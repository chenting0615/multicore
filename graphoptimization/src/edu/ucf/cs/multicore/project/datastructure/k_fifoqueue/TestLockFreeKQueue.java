package edu.ucf.cs.multicore.project.datastructure.k_fifoqueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.ucf.cs.multicore.project.model.Node;

public class TestLockFreeKQueue {
	
	private static final int NumberOfOperations = 12;
	protected static final LockFreeKQueue lfkq = new LockFreeKQueue(4,4);
	private static final int numberOfThreads = 1;
	private static ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
	public static void main(String[] args) throws Exception {
		for(int i=0;i<numberOfThreads;i++){
			executor.execute(new Tester(++i));
		}		
		executor.shutdown();
		while(!executor.isTerminated()){}
	}
	
	static class Tester implements Runnable {
		
		Tester(int threadId){
			this.myId = threadId;
		}
		private int myId = 0;
		public void run() {
			try {
				testEnQ(NumberOfOperations, lfkq);
				testDeQ(NumberOfOperations, lfkq);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		public void testEnQ(int numberOfEnQs, LockFreeKQueue lfkq){
			
			for(int i = 0; i < numberOfEnQs; i++){
				lfkq.enqueue(new Node(i,myId+"-"+String.valueOf(i),null));
				System.err.println(String.format("Thread %d after enqueue %d\n%s",myId, i,lfkq.toString()));
			}
		}
		
		public void testDeQ(int numberOfDeQs, LockFreeKQueue lfkq) throws Exception{
			
			for(int i = 0; i < numberOfDeQs; i++){
				System.out.println(String.format("Thread %d after dequeue %s\n%s",myId, lfkq.dequeue().toString(),lfkq.toString()));
			}
		}
	};
	

}
