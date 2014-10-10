package edu.ucf.cs.multicore.project.test;

import org.apache.log4j.Level;

import edu.ucf.cs.multicore.project.Utility.Utility;
import edu.ucf.cs.multicore.project.bfs.BFSStrategy;
import edu.ucf.cs.multicore.project.bfs.ParallelBFS;
import edu.ucf.cs.multicore.project.bfs.SequentialBFS;
import edu.ucf.cs.multicore.project.datastructure.LockFreeQueue.LockFreeQueue;
import edu.ucf.cs.multicore.project.datastructure.k_fifoqueue.LockFreeKQueue;
import edu.ucf.cs.multicore.project.graphgenerator.GraphGenerator;
import edu.ucf.cs.multicore.project.model.Graph;
import edu.ucf.cs.multicore.project.model.Node;
import edu.ucf.cs.multicore.project.model.NodeFactory;

public class GraphOptimizationController {


	private static TestConfig config;
	private static Graph graph;
	private static GraphGenerator graphGenerator;
	public static boolean destinationReached=false;
	private static BFSStrategy sequentialBFS;
	private static BFSStrategy parallelBFS;
	private static BFSStrategy parallelBFSKQueue;
	private static Node sourceNode;
	private static Node destNode;
	private static SequentialBFSRunnable sequentialBFSRunnable;
	private static ParallelBFSRunnable parallelBFSRunnable;
	private static ParallelBFSKQueueRunnable parallelBFSKQueueRunnable;
	private static LockFreeQueue lockFreeQueue;
	private static LockFreeKQueue lockFreeKQueue;

	
	
	public static void main(String[] args) {
		init();
		Utility.log(Level.INFO, "Finished initialization");

		PerformanceMeter.measure(sequentialBFSRunnable);
		PerformanceMeter.measure(parallelBFSRunnable);
		parallelBFSKQueue = new ParallelBFS(config.numberOfThreads, lockFreeKQueue);
		PerformanceMeter.measure(parallelBFSKQueueRunnable);

		System.out.println("Number of CAS failures:"+lockFreeQueue.getCasFailCount());
		System.out.println("Number of CAS failures in K FIFO Q:"+lockFreeKQueue.getCasFailCount());
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

	private static void init() {
		config = new TestConfig();
		config.loadConfig();
		graphGenerator = config.graphGenerator;
		graph = new Graph();
		sequentialBFS = new SequentialBFS();
		sequentialBFSRunnable = new SequentialBFSRunnable();
		parallelBFSRunnable = new ParallelBFSRunnable();
		parallelBFSKQueueRunnable = new ParallelBFSKQueueRunnable();
		lockFreeQueue = new LockFreeQueue();
		lockFreeKQueue = new LockFreeKQueue(config.K, config.TESTS);
		parallelBFS = new ParallelBFS(config.numberOfThreads, lockFreeQueue);
		graphGenerator.generateGraph(graph, new NodeFactory() {
			@Override
			public Node createNode(Integer index, String label) {
				return new Node(index, label, graph);
			}
		});
		sourceNode = graph.findNodeByIndex(config.sourceNodeIndex);
		destNode = graph.findNodeByIndex(config.destNodeIndex);
		
	}
	
	public static Integer getNumberOfNodes(){
		return config.numberOfNodes;
	}
	



}
