package ie.gmit.sw.traversers;

import ie.gmit.sw.node.Node;
import ie.gmit.sw.node.NodeType;

import java.util.*;
/*Player uses AStar algorithm to find the path to the exit point. Every time a playe makes a move
 * The path is instantly updated. Press z  and move the character, you will be able to see the player
 * path being dynamically updated. 
 */
public class AStarTraversator implements Traversator {
	private Node goal;
	public boolean foundGoal = false;
	public Node[][] maze;
	public Node startNode;
	public NodeType targetNode;
	private int howManyMovesAway = 0;

	public AStarTraversator(Node goal) {
		this.goal = goal;
	}

	public void traverse(Node[][] maze, Node node) {
		setParentsNull(maze);
	
		long time = System.currentTimeMillis();
		int visitCount = 0;

		PriorityQueue<Node> open = new PriorityQueue<Node>(20,
				(Node current, Node next) -> (current.getPathCost() + current.getHeuristic(goal))
						- (next.getPathCost() + next.getHeuristic(goal)));
		java.util.List<Node> closed = new ArrayList<Node>();

		open.offer(node);
		node.setPathCost(0);
		while (!open.isEmpty()) {
			node = open.poll();
			closed.add(node);
			node.setVisited(true);
			visitCount++;

			if (node.getNodeType() == NodeType.ExitPoint) {
				time = System.currentTimeMillis() - time; // Stop the clock
				foundGoal = true;
				TraversatorStats.printStats(node, time, visitCount);
				howManyMovesAway = (int) TraversatorStats.depth;
				break;
			}
			// Process adjacent nodes
			Node[] children = node.children(maze);
			for (int i = 0; i < children.length; i++) {
				Node child = children[i];
				int score = node.getPathCost() + 1 + child.getHeuristic(goal);
				int existing = child.getPathCost() + child.getHeuristic(goal);

				if ((open.contains(child) || closed.contains(child)) && existing < score) {
					continue;
				} else {
					open.remove(child);
					closed.remove(child);
					child.setParent(node);
					child.setPathCost(node.getPathCost() + 1);
					open.add(child);
				}
			}
		}

	}

	// The path is computed dynamically, when player moves therefore we need to
	// re-init the maze do prevent
	// it going into infinite loop .
	private void setParentsNull(Node[][] maze) {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				maze[i][j].setVisited(false);
				maze[i][j].setParent(null);
			}
		}
	}

	public int getMovesNumber() {
		return howManyMovesAway;
	}
}
