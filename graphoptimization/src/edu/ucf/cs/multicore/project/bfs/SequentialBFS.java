package edu.ucf.cs.multicore.project.bfs;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.ucf.cs.multicore.project.model.Node;
import edu.ucf.cs.multicore.project.test.GraphOptimizationController;

public class SequentialBFS implements BFSStrategy{
	private boolean[] visitedArray;
	Queue<Node> bfsQueue=new LinkedList<Node>();
	
	public SequentialBFS() {
		visitedArray = new boolean[GraphOptimizationController.getNumberOfNodes()];
		for (int i = 0; i < visitedArray.length; i++) {
			visitedArray[i] = false;
		}
	}

	@Override
	public void runBFS(Node srouce, Node dest) {
		
		visitedArray[srouce.getIndex()]=true;
		if(srouce.equals(dest)){
			
			System.out.println("Reached the destination node");
//			System.exit(0);
			return;
		}
		else{
			
			List<Node> neighboringList=srouce.getNeighboringNodes();
			
			for(int i=0;i<neighboringList.size();i++){
				if(!visitedArray[neighboringList.get(i).getIndex()]){
//					System.out.println("Current node being added into the queue:"+neighboringList.get(i));
					visitedArray[neighboringList.get(i).getIndex()]=true;
					bfsQueue.add(neighboringList.get(i));
				}
				
			}
			
			try{
				Node queuenode=(Node)bfsQueue.remove();
				runBFS(queuenode, dest);//Recurrsive call  to the function 
			}
			catch(Exception e){
				System.out.println("Error occured somewhere..Please figure out whats wrong. :D Or the nodes might not be connected");
				e.printStackTrace();
			}
		}
				
		
	}
	

}
