package edu.ucf.cs.multicore.project.bfs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.ucf.cs.multicore.project.datastructure.Queuable;
import edu.ucf.cs.multicore.project.model.Node;

public class ParallelBFS implements BFSStrategy{
	
	
	private Integer numberOfThreads;
	private static ExecutorService executor;
	private Runnable[] workers;
	private Queuable<Node> queue;
	public ParallelBFS(Integer numberOfThreads, Queuable<Node> queue){
		this.numberOfThreads = numberOfThreads;
		executor=Executors.newFixedThreadPool(numberOfThreads);
		workers = new Runnable[numberOfThreads]; 
		this.queue = queue;
	}

	@Override
	public void runBFS(Node source, Node dest) {
		for(int i=0;i<numberOfThreads;i++){
			Runnable worker=new ParallelBFSExecutor(i, source, dest, queue);
			workers[i] = worker;
		}
		for(int i=0;i<numberOfThreads;i++){
			executor.execute(workers[i]);
		}
		executor.shutdown();
		while(!executor.isTerminated()){}
	}

}
