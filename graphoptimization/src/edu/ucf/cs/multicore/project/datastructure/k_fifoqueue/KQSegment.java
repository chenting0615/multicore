package edu.ucf.cs.multicore.project.datastructure.k_fifoqueue;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class KQSegment {
	
	public AtomicReference<KQSegment> next;
	public AtomicReferenceArray<AtomicReference<KQNode>> nodes;
	public KQSegment(int k){
		next = new AtomicReference<KQSegment>(null);
		nodes = new AtomicReferenceArray<AtomicReference<KQNode>>(k);
/*		for(int i = 0; i < k; i++){
			nodes.set(i, new AtomicReference<KQNode>(new KQNode(null)));
		}*/
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nodes.length(); i++) {
			sb.append(nodes.get(i));
			if(i != nodes.length() - 1)
				sb.append(",");
		}
		return sb.toString();
	}
	
	

}
