package ie.gmit.sw.ai;

import ie.gmit.sw.node.Node;

public interface Traversator {
	public void traverse(Node[][] maze, Node start) throws InterruptedException;
}
