package edu.ucf.cs.multicore.project.contentionStrategies;

import java.math.BigInteger;

import edu.ucf.cs.multicore.project.test.GraphOptimizationController;

public class ExpBackoff {

	public ExpBackoff(){
		
	}
	
	public void applyBackoffStrategy(Thread currentThread, int casFailCount){
		
		if(!GraphOptimizationController.threadCASFailures.containsKey(Thread.currentThread().getId())){
			GraphOptimizationController.threadCASFailures.put(Integer.valueOf((int) Thread.currentThread().getId()), 1);
		}
		else{
			int val=GraphOptimizationController.threadCASFailures.get(Thread.currentThread().getId());
			GraphOptimizationController.threadCASFailures.put((int) Thread.currentThread().getId(), val+1);
		}
		int val=(int)GraphOptimizationController.threadCASFailures.get(Integer.valueOf((int) Thread.currentThread().getId()));
		if(val>=1){
			try {
				System.out.println("cas #" + casFailCount);
				currentThread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
}
