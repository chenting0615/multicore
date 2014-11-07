package edu.ucf.cs.multicore.project.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.log4j.Level;

import edu.ucf.cs.multicore.project.Utility.Utility;
import edu.ucf.cs.multicore.project.bfs.BFSStrategy;
import edu.ucf.cs.multicore.project.bfs.ParallelBFS;
import edu.ucf.cs.multicore.project.bfs.SequentialBFS;
import edu.ucf.cs.multicore.project.datastructure.ConcurrentLinkedQueueWrapper;
import edu.ucf.cs.multicore.project.datastructure.LockFreeQueue.LockFreeQueue;
import edu.ucf.cs.multicore.project.datastructure.k_fifoqueue.LockFreeKQueue;
import edu.ucf.cs.multicore.project.graphgenerator.FileLoadedGenerator;
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
	private static String filePath = "graph.txt";
	
	
	public static void main(String[] args) {
		try {
			init(true);
			//storeGraph();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Utility.log(Level.INFO, "Finished initialization");
		String input=null;
		/*if(!input.equals("")){
			 samplesNo=Integer.parseInt(input);
		}
		else
			samplesNo=1;*/
		
			
			try {
				init(false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("Initialization of the graph is done. Please specify the number of threads");
			
			
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
				
				
				
				System.out.println("Number of CAS failures:"+lockFreeQueue.getCasFailCount());
				//System.out.println("Number of CAS failures in K FIFO Q:"+lockFreeKQueue.getCasFailCount());
			//}
		
		
		
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

	private static void init(boolean initializeGraph) throws IOException {
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
		graphGenerator = config.graphGenerator;
		graph = new Graph();
		

		/*if(initializeGraph){
			
			graphGenerator.generateGraph(graph, new NodeFactory() {
				@Override
				public Node createNode(Integer index, String label) {
					return new Node(index, label, graph);
				}
			});
				}*/
		loadGraph();
		sourceNode = graph.findNodeByIndex(config.sourceNodeIndex);
		destNode = graph.findNodeByIndex(config.destNodeIndex);
		
		
	}
	
	public static Integer getNumberOfNodes(){
		return config.numberOfNodes;
	}
	
	public static void storeGraph() throws IOException{
		List<Node> nodeList = graph.nodeList();
		List<String> fileLines = new ArrayList<String>();
		for (Node node : nodeList) {
			StringBuilder lineBuilder = new StringBuilder(String.valueOf(node.getIndex()));
			lineBuilder.append("     ");
			for (Node adj : node.getNeighboringNodes()) {
				lineBuilder.append(adj.getIndex());
				lineBuilder.append(" ");
			}
			fileLines.add(lineBuilder.toString());
		}
		BufferedWriter graphFile = new BufferedWriter(new FileWriter(filePath,true));
		for (String line : fileLines) {
			graphFile.write(line);
			graphFile.newLine();
		}
		graphFile.close();		
	}
	
	
	public static void loadGraph() throws IOException{
		List<String> doRead = doRead();
		Map<Integer,List<Integer>> graphMap = new HashMap<Integer,List<Integer>>();
		for (String line : doRead) {
			StringTokenizer st = new StringTokenizer(line," ");
			if(st.hasMoreTokens()){
				String nextToken = st.nextToken();
				Integer index = Integer.parseInt(nextToken);
				List<Integer> adjList = graphMap.get(index);
				if(adjList == null){
					adjList = new ArrayList<Integer>();
				}
				while(st.hasMoreTokens()){
					nextToken = st.nextToken();
					int index2 = Integer.parseInt(nextToken);
					adjList.add(index2);
				}
				graphMap.put(index, adjList);
			}
		}
		graph = new Graph();
		new FileLoadedGenerator(graphMap).generateGraph(graph, new NodeFactory() {
			@Override
			public Node createNode(Integer index, String label) {
				return new Node(index, label, graph);
			}
		});
	}
	
	public static List<String> doRead() throws IOException{
		if(filePath == null){
			System.err.println("please specify file path ...");
			return null;
		}
		BufferedReader inFile = null;
		try {
			inFile = new BufferedReader(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> fileContent = new ArrayList<String>();
		String curLine = " ";
		try {
			while( (curLine = inFile.readLine()) != null){
				fileContent.add(curLine);				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inFile.close();
		return fileContent;
	}


}
