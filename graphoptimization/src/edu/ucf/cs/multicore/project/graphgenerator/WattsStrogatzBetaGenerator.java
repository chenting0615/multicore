package edu.ucf.cs.multicore.project.graphgenerator;

import java.util.Random;

import edu.ucf.cs.multicore.project.model.Graph;
import edu.ucf.cs.multicore.project.model.Node;
import edu.ucf.cs.multicore.project.model.NodeFactory;

public class WattsStrogatzBetaGenerator implements GraphGenerator{

	private int size;
	private int degree;
	private double beta;

	public WattsStrogatzBetaGenerator(int size, int degree, double beta) {
		this.size = size;
		this.degree = degree;
		this.beta = beta;
	}

	@Override
	public void generateGraph(Graph target,NodeFactory nodeFactory) {
		Random random = new Random();
		Node[] nodes = new Node[size];
		// Creating a regular ring lattice
		for (int i = 0; i < size; ++i) {
			nodes[i] = nodeFactory.createNode(i,String.format("%d",i).toString());
			target.addNode(nodes[i]);
		}
		for (int i = 0; i < size; ++i) {
			for (int j = 1; j <= degree / 2; ++j) {
				target.addEdge(nodes[i], nodes[(i + j) % size]);
			}
		}
		// Rewiring edges
		for (int i = 0; i < size; ++i)
			for (int j = 1; j <= degree / 2; ++j)
				if (random.nextDouble() <= beta) {
					target.removeEdge(nodes[i], nodes[(i + j) % size]);
					int k = random.nextInt(size);
					while (k == i
							|| target.containsEdge(nodes[i], nodes[k]))
						k = random.nextInt(size);
					target.addEdge(nodes[i], nodes[k]);
				}
	}

}
