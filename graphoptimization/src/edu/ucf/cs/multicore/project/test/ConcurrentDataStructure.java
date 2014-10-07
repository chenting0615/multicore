package edu.ucf.cs.multicore.project.test;


import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import edu.ucf.cs.multicore.LockFreeQueue.LockFreeQueue;
import edu.ucf.cs.multicore.project.model.Node;
public class ConcurrentDataStructure {

	public  boolean[] visitedArray; //Visited array. Shared array
	public ConcurrentDataStructure() {
		// TODO Auto-generated constructor stub
		visitedArray=new boolean[GraphOptimizationController.MAXNODES];
		//Initializr the visited array
		for(int i=0;i<GraphOptimizationController.MAXNODES;i++){
			visitedArray[i]=false;
		}
	}

	public static ConcurrentLinkedQueue<Node> bfsQueue=new ConcurrentLinkedQueue<Node>();
	public static LockFreeQueue lockFreeQueue = new LockFreeQueue();
	ConcurrentHashMap<Integer, ConcurrentLinkedQueue<AtomicInteger>> concurrentDS=new ConcurrentHashMap<Integer, ConcurrentLinkedQueue<AtomicInteger>>();
	
	public boolean addLevel(){
		
		
		
		return true;
	}
	
}
