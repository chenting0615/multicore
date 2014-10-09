package edu.ucf.cs.multicore.project.test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ResultWriter {
	
	private static final String RESULT_FILE_NAME = "result.txt";

	public static void writeInFile(String msg) throws IOException {
		BufferedWriter file = new BufferedWriter(new FileWriter(RESULT_FILE_NAME,true));
		file.write(msg);
		file.newLine();
		file.close();
	}

}
