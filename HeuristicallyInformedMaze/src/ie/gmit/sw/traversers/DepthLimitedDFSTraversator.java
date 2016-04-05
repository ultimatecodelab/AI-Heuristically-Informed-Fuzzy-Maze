package ie.gmit.sw.traversers;

import ie.gmit.sw.game.Enemy;
import ie.gmit.sw.node.Node;
import ie.gmit.sw.node.NodeType;
/*
 * Enemies in the game world uses Depth Limited DFS to move around the maze. Every enemies spawned in a 
 * game are independent of one another. They all have their own copy of maze and they traverse independently.
 * 
 */
public class DepthLimitedDFSTraversator implements Traversator {
	private Node[][] maze;
	private int limit;
	private boolean keepRunning = true;
	private long time = System.currentTimeMillis();
	private int visitCount = 0;
	private Enemy enemyNode;

	public DepthLimitedDFSTraversator(int limit, Enemy enemyNode) {
		this.limit = limit;
		this.enemyNode = enemyNode;
	}

	@Override
	public void traverse(Node[][] maze, Node node) throws InterruptedException {
		this.maze = maze;
		setParentsNull(maze);
		dfs(node, 1);
	}

	private void dfs(Node node, int depth) throws InterruptedException {
		if (!keepRunning || depth > limit)
			return;

		node.setVisited(true);
		visitCount++;

		if (node.getNodeType() == NodeType.PlayerNode) {
			time = System.currentTimeMillis() - time; // Stop the clock
			TraversatorStats.printStats(node, time, visitCount);
			keepRunning = false;
			return;
		}

		Node[] children = node.children(maze);
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null && !children[i].isVisited()) {
				
				if (node.getNodeType() == NodeType.WalkableNode || node.getNodeType() == NodeType.PathNode
						|| node.getNodeType() == NodeType.PlayerNode) {
					enemyNode.updatePath(node.getRow(), node.getCol());
					maze[node.getRow()][node.getCol()].setNodeType(NodeType.EnemyNode);
					Thread.sleep(3000);
				}else if (node.getRow() <= maze.length - 1 && node.getCol() <= maze[node.getRow()].length - 1
						&& (maze[node.getRow()][node.getCol()].getNodeType() == NodeType.PlayerNode)) {
					enemyNode.updatePath(node.getRow() - 1, node.getCol() - 1);
					maze[node.getRow() - 1][node.getCol() - 1].setNodeType(NodeType.EnemyNode);
				}
				children[i].setParent(node);
				dfs(children[i], depth + 1);
			}
		}
	}
	private void setParentsNull(Node[][] maze) {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				maze[i][j].setVisited(false);
				maze[i][j].setParent(null);
			}
		}
	}

}