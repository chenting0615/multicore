package edu.ucf.cs.multicore.project.test;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Level;

import edu.ucf.cs.multicore.project.Utility.Utility;
import edu.ucf.cs.multicore.project.graphgenerator.GraphGenerator;
import edu.ucf.cs.multicore.project.graphgenerator.WattsStrogatzBetaGenerator;

public class TestConfig {
	private static final String DEFAULT_PROPERTIES_FILE = "graphoptimization.properties";
	private static final Properties PROPERTIES = new Properties();
	private static final String DEGREE_PROPERTY_KEY = "degree";
	private static final String BETA_PROPERTY_KEY = "beta";
	private static final String NUMBER_OF_NODES_PROPERTY_KEY = "numberOfNodes";
	private static final String GRAPH_GENERATOR_PROPERTY_KEY = "graphGenerator";
	private static final String SOURCE_NODEX_INDEX_KEY = "sourceNodeIndex";
	private static final String DEST_NODEX_INDEX_KEY = "destNodeIndex";
	private static final String NUMBER_OF_THREADS_KEY = "numberOfThreads";
	private static final String K_PROPERTY_KEY = "K";
	private static final String TESTS_PROPERTY_KEY = "TESTS";
	
	private static final int DEFAULT_NUMBER_OF_NODES = 10000;
	private static final int DEFAULT_DEGREE = 10;
	private static final double DEFAULT_BETA = 0.6;
	private static final int DEFAULT_SOURCE_NODE_INDEX = 100;
	private static final int DEFAULT_DEST_NODE_INDEX = 700;
	private static final int DEFAULT_NUMBER_OF_THREADS = 700;
	private static final int DEFAULT_K = 4;
	private static final int DEFAULT_TESTS = 4;

	private static final GraphGenerator DEFAULT_GRAPH_GENERATOR = new WattsStrogatzBetaGenerator(
			DEFAULT_NUMBER_OF_NODES, DEFAULT_DEGREE, DEFAULT_BETA);

	int numberOfNodes;
	int degree;
	int sourceNodeIndex;
	int destNodeIndex;
	int numberOfThreads;
	int K;
	int TESTS;
	double beta;
	GraphGenerator graphGenerator;
	String resultFileName = "result.txt";
	
	public TestConfig(String propertyFileName) {
		this.propertyFileName = propertyFileName;
	}

	public TestConfig() {
		this.propertyFileName = DEFAULT_PROPERTIES_FILE;
	}

	private String propertyFileName;

	void loadConfig() {

		try {
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			InputStream propertiesFile = classLoader
					.getResourceAsStream(propertyFileName);
			if (propertiesFile == null) {
				Utility.log(Level.ERROR,"Properties file '" + propertyFileName
						+ "' is missing in classpath.");
				loadDefaults();
				return;
			}
			PROPERTIES.load(propertiesFile);
			numberOfNodes = Integer.parseInt(PROPERTIES
					.getProperty(NUMBER_OF_NODES_PROPERTY_KEY));
			degree = Integer.parseInt(PROPERTIES
					.getProperty(DEGREE_PROPERTY_KEY));
			sourceNodeIndex = Integer.parseInt(PROPERTIES
					.getProperty(SOURCE_NODEX_INDEX_KEY));
			destNodeIndex = Integer.parseInt(PROPERTIES
					.getProperty(DEST_NODEX_INDEX_KEY));
			numberOfThreads = Integer.parseInt(PROPERTIES
					.getProperty(NUMBER_OF_THREADS_KEY));
			TESTS = Integer.parseInt(PROPERTIES
					.getProperty(TESTS_PROPERTY_KEY));
			K = Integer.parseInt(PROPERTIES
					.getProperty(K_PROPERTY_KEY));
			beta = Double
					.parseDouble(PROPERTIES.getProperty(BETA_PROPERTY_KEY));
			createGraphGenerator();
		} catch (Throwable e) {
			Utility.log(Level.ERROR,"Cannot load properties file '"
					+ propertyFileName + "'.");
			loadDefaults();
			return;
		}
	}

	public void createGraphGenerator() {
		String graphGeneratorClassName = PROPERTIES
				.getProperty(GRAPH_GENERATOR_PROPERTY_KEY);
		if ("WattsStrogatzBetaGenerator".equals(graphGeneratorClassName))
			graphGenerator = new WattsStrogatzBetaGenerator(numberOfNodes,
					degree, beta);
	}


	private void loadDefaults() {
		this.numberOfNodes = DEFAULT_NUMBER_OF_NODES;
		this.degree = DEFAULT_DEGREE;
		this.beta = DEFAULT_BETA;
		this.graphGenerator = DEFAULT_GRAPH_GENERATOR;
		this.sourceNodeIndex = DEFAULT_SOURCE_NODE_INDEX;
		this.destNodeIndex = DEFAULT_DEST_NODE_INDEX;
		this.numberOfThreads = DEFAULT_NUMBER_OF_THREADS;
		this.TESTS = DEFAULT_TESTS;
		this.K = DEFAULT_K;
	}
}
