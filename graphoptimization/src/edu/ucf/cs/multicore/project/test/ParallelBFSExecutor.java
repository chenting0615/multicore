package edu.ucf.cs.multicore.project.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import edu.ucf.cs.multicore.LockFreeQueue.LockFreeQueue;
import edu.ucf.cs.multicore.project.Utility.Utility;
import edu.ucf.cs.multicore.project.model.Node;

public class ParallelBFSExecutor implements Runnable {

	int THid;
	Node srcNode=null;
	Node destNode=null;
	//ConcurrentDataStructure concDSObj=null;
	ParallelBFSExecutor(int id,Node srcNode,Node destNode){
		this.THid=id;
		//this.concDSObj=concDSObj;
		this.srcNode=srcNode;
		this.destNode=destNode;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		searchNodeUsingParallelBFS(srcNode, destNode);
		
		
	}
	
	public void searchNodeUsingParallelBFS(Node srcNode, Node destNode){
		
			//check if queue is empty
			
			while(!GraphOptimizationController.destinationReached){//Loop until the destination is reached
				
//				ConcurrentLinkedQueue<Node> localqueue= ConcurrentDataStructure.bfsQueue;
				LockFreeQueue lockFreeQueue= ConcurrentDataStructure.lockFreeQueue;
				
				if(ConcurrentDataStructure.lockFreeQueue.isEmpty()){
					//Enqueu the source node
//					localqueue.add(srcNode);
					lockFreeQueue.enqueue(srcNode);
					System.out.println("Thread:"+THid+" Enqueing:"+srcNode.toString());
					
				}
				else{
					//Dequeue the bf queue
					
					try{
						
//						Node currentNode=localqueue.remove();
						Node currentNode = lockFreeQueue.dequeue();
						if(currentNode.toString().equals(destNode.toString())){
							System.out.println("FOund the destination node.....");
							GraphOptimizationController.destinationReached=true;
							//System.exit(0);
						}
						else{
							List<Node> nodeList=new ArrayList<Node>();
							nodeList=currentNode.getNeighboringNodes();
							for(int i=0;i<nodeList.size();i++){
								//Check if the node is not visited
								if(!GraphOptimizationController.concDSObj.visitedArray[Integer.parseInt(nodeList.get(i).toString())]){
									GraphOptimizationController.concDSObj.visitedArray[Integer.parseInt(nodeList.get(i).toString())]=true;
//									localqueue.add(nodeList.get(i));
									lockFreeQueue.enqueue(nodeList.get(i));
									System.out.println("Thread:"+THid+" Enqueing:"+nodeList.get(i).toString());
								}
								
							}
							
						}
						
						
						
					}
					catch(Exception e){
						//System.out.println("Erro occured while performing dequeue:"+e.getMessage());
						//e.printStackTrace();
					}
				}
			
				
			}
			
		//Analyse the current node and see if it is visited.
				//If not mark it as visited, else 

		
	}

}
