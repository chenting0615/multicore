package edu.ucf.cs.multicore.project.test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.ucf.cs.multicore.project.model.Graph;
import edu.ucf.cs.multicore.project.model.Node;

public class SequentialBFS {
	Graph graph;
	private int[] visitedArray=new int[100000];
	Queue bfsQueue=new LinkedList<Node>();
	
	public void searchNodeUsingSequentialBFS(Node sourceNode,Node destNode){
		
		
		/*Node currentNode=null;
		bfsQueue.add(sourceNode);
		try{
			currentNode=(Node) bfsQueue.remove();
		}
		catch(Exception e){
			System.out.println("Was not able to add the Source node to queue. The exception is:"+e.getMessage());
		}*/
		visitedArray[Integer.parseInt(sourceNode.toString())]=1;
		if(sourceNode.toString().equals(destNode.toString())){
			
			System.out.println("Reached the destination node");
			System.exit(0);
		}
		else{
			
			List<Node> neighboringList=sourceNode.getNeighboringNodes(sourceNode);
			
			for(int i=0;i<neighboringList.size();i++){
				if(visitedArray[Integer.parseInt(neighboringList.get(i).toString())]!=1){
					System.out.println("Current node being added into the queue:"+Integer.parseInt(neighboringList.get(i).toString()));
					visitedArray[Integer.parseInt(neighboringList.get(i).toString())]=1;
					bfsQueue.add(neighboringList.get(i));
				}
				
			}
			
			try{
				Node queuenode=(Node)bfsQueue.remove();
				searchNodeUsingSequentialBFS(queuenode, destNode);//Recurrsive call  to the function 
			}
			catch(Exception e){
				System.out.println("Error occured somewhere..Please figure out whats wrong. :D Or the nodes might not be connected");
			}
		}
		
	}
	

}
