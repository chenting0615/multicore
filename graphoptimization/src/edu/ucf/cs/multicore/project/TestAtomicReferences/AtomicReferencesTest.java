package edu.ucf.cs.multicore.project.TestAtomicReferences;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
public class AtomicReferencesTest implements Runnable {
	
	int failcount=0;
	static int globalFailCount=0;
	public static AtomicInteger testInt=new AtomicInteger(0);
	
	public AtomicReferencesTest(int count) {
		// TODO Auto-generated constructor stub
		this.failcount=count;
	}
	
	public void incrementVal(){
		
		
		if(testInt.compareAndSet(testInt.get(), (testInt.get()+1))){
			System.out.println("ThreadId:"+Thread.currentThread().getId()+"succssfully set the counter to:"+testInt.get());
		}
		else{
			this.failcount++;
			globalFailCount++;
			Random obj=new Random();
			try {//BackoffMechanism
				System.out.println("ThreadID:"+Thread.currentThread().getId()+"Failed to set the value");
				/*if(this.failcount>2){
					Thread.currentThread().sleep(obj.nextInt(30));
					this.failcount=0;
				}*/
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(testInt.get()<1000000)
		incrementVal();
		
	}
	
	public static void main(String args[]){
		
		ExecutorService executor = Executors.newFixedThreadPool(10);
		
		long start=System.currentTimeMillis();
		
		try{
			
			for(int i=0;i<10;i++){
				Runnable worker=new AtomicReferencesTest(0);
				executor.execute(worker);
			}
			
			executor.shutdown();
        	while (!executor.isTerminated()) {
            }
        	long endtime=System.currentTimeMillis();
            System.out.println("Time for execution:"+(endtime-start));
            System.out.println("Total number of failures:"+globalFailCount);
		}
		catch(Exception e){
			System.out.println("Failed to execute Erro is :"+e.getMessage());
		}
	}
}
