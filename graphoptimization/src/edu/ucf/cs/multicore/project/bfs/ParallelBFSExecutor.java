package edu.ucf.cs.multicore.project.bfs;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;

import edu.ucf.cs.multicore.project.Utility.Utility;
import edu.ucf.cs.multicore.project.datastructure.Queuable;
import edu.ucf.cs.multicore.project.model.Node;
import edu.ucf.cs.multicore.project.test.GraphOptimizationController;

public class ParallelBFSExecutor implements Runnable {

	private int THid;
	private Node srcNode;
	private Node destNode;
	private static Queuable<Node> bfsQueue;
	private  boolean[] visitedArray;
	private static volatile boolean destinationReached = false;

	ParallelBFSExecutor(int id,Node srcNode,Node destNode, Queuable<Node> queue){
		this.THid = id;
		this.srcNode = srcNode;
		this.destNode = destNode;
		ParallelBFSExecutor.bfsQueue = queue;
		visitedArray = new boolean[GraphOptimizationController.getNumberOfNodes()];
		for(int i= 0; i < visitedArray.length; i++){
			visitedArray[i] = false;
		}
	}
	
	@Override
	public void run() {
		searchNodeUsingParallelBFS();
	}
	
	private void searchNodeUsingParallelBFS() {
		//check if queue is empty
		while(!destinationReached){//Loop until the destination is reached
			if(bfsQueue.isEmpty()){
				//Enqueu the source node
				bfsQueue.enqueue(srcNode);
				//System.out.println("Thread:"+THid+" Enqueing:"+srcNode.toString());
			}
			else{
				//Dequeue the bf queue
				try{
					Node currentNode = bfsQueue.dequeue();
					if(currentNode.equals(destNode)){
						System.out.println("FOund the destination node.....");
						destinationReached=true;
					}
					else{
						List<Node> nodeList=new ArrayList<Node>();
						nodeList=currentNode.getNeighboringNodes();
						for(int i=0;i<nodeList.size();i++){
							//Check if the node is not visited
							if(!visitedArray[nodeList.get(i).getIndex()]){
								visitedArray[nodeList.get(i).getIndex()]=true;
								bfsQueue.enqueue(nodeList.get(i));
								//System.out.println("Thread:"+THid+" Enqueing:"+nodeList.get(i).toString());
							}
						}
					}
				}
				catch(Exception e){
					//Utility.log(Level.ERROR, e.getMessage());
				}
			}
		}
	}

}
