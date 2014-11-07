package edu.ucf.cs.multicore.project.test;

import java.io.IOException;


public class PerformanceMeter {
	
	public static long startTime=0;
	
	public static void measure(Runnable runnable){
		//long startTime=System.currentTimeMillis();
		runnable.run();
		long endTime=System.currentTimeMillis();
		long executionTime=endTime-startTime;
		report(runnable,executionTime);
	}

	private static void report(Runnable runnable, long executionTime) {
		String msg = String.format("Total time required to Execute using (%s) : %d",runnable.getClass().getSimpleName(),executionTime);
//		Utility.log(Level.INFO, msg);
		try {
			ResultWriter.writeInFile(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println(msg);
	}

}
