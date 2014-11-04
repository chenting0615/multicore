package edu.ucf.cs.multicore.project.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Level;

import edu.ucf.cs.multicore.project.Utility.Utility;
import edu.ucf.cs.multicore.project.bfs.BFSStrategy;
import edu.ucf.cs.multicore.project.bfs.ParallelBFS;
import edu.ucf.cs.multicore.project.bfs.SequentialBFS;
import edu.ucf.cs.multicore.project.datastructure.ConcurrentLinkedQueueWrapper;
import edu.ucf.cs.multicore.project.datastructure.LockFreeQueue.LockFreeQueue;
import edu.ucf.cs.multicore.project.datastructure.k_fifoqueue.LockFreeKQueue;
import edu.ucf.cs.multicore.project.graphgenerator.GraphGenerator;
import edu.ucf.cs.multicore.project.model.Graph;
import edu.ucf.cs.multicore.project.model.Node;
import edu.ucf.cs.multicore.project.model.NodeFactory;

public class GraphOptimizationController {

	public static HashMap<Integer,Integer> threadCASFailures=new HashMap<Integer, Integer>();
	private static TestConfig config;
	private static Graph graph;
	private static GraphGenerator graphGenerator;
	public static boolean destinationReached=false;
	private static BFSStrategy sequentialBFS;
	private static BFSStrategy parallelBFS;
	private static BFSStrategy parallelBFSKQueue;
	private static BFSStrategy parallelBFSConcurrentQueue;
	private static Node sourceNode;
	private static Node destNode;
	private static SequentialBFSRunnable sequentialBFSRunnable;
	private static ParallelBFSRunnable parallelBFSRunnable;
	private static ParallelBFSKQueueRunnable parallelBFSKQueueRunnable;
	private static ParallelBFSConcurrentQueueRunnable parallelBFSConcurrentQueueRunnable;
	private static LockFreeQueue lockFreeQueue;
	private static LockFreeKQueue lockFreeKQueue;
	private static ConcurrentLinkedQueueWrapper concurrentLinkedQueueWrapper;
	private static int samplesNo=0;
	private static List<Integer> casFailuresArrayKQueue=new ArrayList<Integer>();
	private static List<Integer> casFailuresArrayLockFreeQueue=new ArrayList<Integer>();
	public static int MAXTHREADS=4;
	public static boolean enableExpBackoffStrategy=false;

	
	
	public static void main(String[] args) {
		init(true);
		
		Utility.log(Level.INFO, "Finished initialization");
		String input=null;
		/*if(!input.equals("")){
			 samplesNo=Integer.parseInt(input);
		}
		else
			samplesNo=1;*/
		do{
			flushallQueues();
			init(false);
			System.out.println("Initialization of the graph is done. Please specify the number of threads");
			Scanner scObject=new Scanner(System.in);
			input=scObject.next();
			config.numberOfThreads=Integer.parseInt(input);
			MAXTHREADS=config.numberOfThreads;
			//for(int i=0;i<samplesNo;i++){
				
				//PerformanceMeter.measure(sequentialBFSRunnable);
				//parallelBFSKQueue = new ParallelBFS(config.numberOfThreads, lockFreeKQueue);
				//PerformanceMeter.measure(parallelBFSKQueueRunnable);
				parallelBFSRunnable = new ParallelBFSRunnable();
				parallelBFS = new ParallelBFS(config.numberOfThreads, lockFreeQueue);
				PerformanceMeter.measure(parallelBFSRunnable);
				//parallelBFSConcurrentQueue = new ParallelBFS(config.numberOfThreads, concurrentLinkedQueueWrapper);
				//PerformanceMeter.measure(parallelBFSConcurrentQueueRunnable);
				casFailuresArrayKQueue.add(lockFreeKQueue.getCasFailCount());
				casFailuresArrayLockFreeQueue.add(lockFreeQueue.getCasFailCount());
				System.out.println("Number of CAS failures:"+lockFreeQueue.getCasFailCount());
				System.out.println("Number of CAS failures in K FIFO Q:"+lockFreeKQueue.getCasFailCount());
			//}
		}
		while(!(input.equals("exit")||input.equals("n")));
		
		
		/*for(int i=0;i<casFailuresArrayKQueue.size();i++){
			System.out.println("Sample:"+i+"Number of CAS failure:"+casFailuresArrayLockFreeQueue.get(i));
			System.out.println("Number of CAs failures:"+casFailuresArrayKQueue.get(i));
		}*/
	}
	
	private static void flushallQueues() {
		// TODO Auto-generated method stub
		config = null;
		
		sequentialBFS = null;
		sequentialBFSRunnable = null;
		parallelBFSRunnable = null;
		parallelBFSKQueueRunnable = null;
		parallelBFSConcurrentQueueRunnable = null;
		lockFreeQueue = null;
		lockFreeKQueue = null;
		concurrentLinkedQueueWrapper = null;
		parallelBFS = null;
		
		System.gc();
		
	}

	private static class SequentialBFSRunnable implements Runnable {
		@Override
		public void run() {
			sequentialBFS.runBFS(sourceNode, destNode);
		}
	}

	private static class ParallelBFSRunnable implements Runnable {
		@Override
		public void run() {
			parallelBFS.runBFS(sourceNode, destNode);
		}
	}
	
	private static class ParallelBFSKQueueRunnable implements Runnable {
		@Override
		public void run() {
			parallelBFSKQueue.runBFS(sourceNode, destNode);
		}
	}
	
	private static class ParallelBFSConcurrentQueueRunnable implements Runnable {
		@Override
		public void run() {
			parallelBFSConcurrentQueue.runBFS(sourceNode, destNode);
		}
	}

	private static void init(boolean initializeGraph) {
		config = new TestConfig();
		config.loadConfig();
		
		sequentialBFS = new SequentialBFS();
		sequentialBFSRunnable = new SequentialBFSRunnable();
		parallelBFSRunnable = new ParallelBFSRunnable();
		parallelBFSKQueueRunnable = new ParallelBFSKQueueRunnable();
		parallelBFSConcurrentQueueRunnable = new ParallelBFSConcurrentQueueRunnable();
		lockFreeQueue = new LockFreeQueue();
		lockFreeKQueue = new LockFreeKQueue(config.K, config.TESTS);
		concurrentLinkedQueueWrapper = new ConcurrentLinkedQueueWrapper();
		parallelBFS = new ParallelBFS(config.numberOfThreads, lockFreeQueue);
		if(initializeGraph){
			graphGenerator = config.graphGenerator;
			graph = new Graph();
			graphGenerator.generateGraph(graph, new NodeFactory() {
				@Override
				public Node createNode(Integer index, String label) {
					return new Node(index, label, graph);
				}
			});
			sourceNode = graph.findNodeByIndex(config.sourceNodeIndex);
			destNode = graph.findNodeByIndex(config.destNodeIndex);
		}
		
		
	}
	
	public static Integer getNumberOfNodes(){
		return config.numberOfNodes;
	}
	



}
