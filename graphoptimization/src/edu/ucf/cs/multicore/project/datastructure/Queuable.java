package edu.ucf.cs.multicore.project.datastructure;

public interface Queuable<E> {
	
	public void enqueue(E element);
	public E dequeue() throws Exception;
	public boolean isEmpty();

}
