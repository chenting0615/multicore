package edu.ucf.cs.multicore.project.bfs;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.ucf.cs.multicore.project.model.Node;
import edu.ucf.cs.multicore.project.test.GraphOptimizationController;

public class SequentialBFS implements BFSStrategy {
	private boolean[] visitedArray;
	Queue<Node> bfsQueue = new LinkedList<Node>();

	public SequentialBFS() {
		visitedArray = new boolean[GraphOptimizationController
				.getNumberOfNodes()];
		for (int i = 0; i < visitedArray.length; i++) {
			visitedArray[i] = false;
		}
	}

	@Override
	public void runBFS(Node srouce, Node dest) {

		visitedArray[srouce.getIndex()] = true;
		while (true) {
			if(bfsQueue.isEmpty()){
				bfsQueue.add(srouce);
			}else {
				Node queuenode = (Node) bfsQueue.remove();
				if (queuenode.equals(dest)) {
					System.out.println("Reached the destination node");
					return;
				}
				List<Node> neighboringList = queuenode.getNeighboringNodes();

				for (int i = 0; i < neighboringList.size(); i++) {
					if (!visitedArray[neighboringList.get(i).getIndex()]) {
						visitedArray[neighboringList.get(i).getIndex()] = true;
						bfsQueue.add(neighboringList.get(i));
					}
				}
			}
		}

	}



}
