package ie.gmit.sw.game;

import java.util.List;
import java.util.Random;

import ie.gmit.sw.ai.DepthLimitedDFSTraversator;
import ie.gmit.sw.node.Node;
import ie.gmit.sw.node.NodeType;

public class Enemy implements Runnable {

	private Node[][] maze;
	private List<Node> paths;
	private int row;
	private int col;
	private int enemyStrength = 100;

	public int getLifeForce() {
		return enemyStrength;
	}

	private void setXandY() {
		int offSet = (int) ((int) maze.length * 0.20);
		Random ran = new Random();
		int row = ran.nextInt(this.maze.length - offSet) + offSet;
		int col = ran.nextInt(this.maze[0].length - offSet) + offSet;

		this.row = row;
		this.col = col;
		maze[row][col].setNodeType(NodeType.EnemyNode);
	}

	public Enemy() {

	}

	public Enemy(Node[][] maze, Node targetNode) throws InterruptedException {
		this.maze = maze;
		setXandY();
	}

	public void updatePath(int x, int y) throws InterruptedException {
		if (maze[this.row][this.col].getNodeType() != NodeType.PlayerNode) {
			maze[this.row][this.col].setNodeType(NodeType.WalkableNode);
			maze[x][y].setNodeType(NodeType.EnemyNode);
			this.setRow(x);
			this.setCol(y);
		}
	}

	public void setRow(int row) {
		this.row = row;

	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	private void computePaths() throws InterruptedException {

		DepthLimitedDFSTraversator tr = new DepthLimitedDFSTraversator(200, this);
		tr.traverse(maze, maze[this.getRow()][this.getCol()]);

	}

	private void updatePath() {
		for (int k = 0; k < this.paths.size(); k++) {
			try {
				updatePath(this.getComputedPaths().get(k).getRow(), this.getComputedPaths().get(k).getCol());
				Thread.sleep(1500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

		}
	}

	public List<Node> getComputedPaths() {
		return paths;
	}

	@Override
	public void run() {
		try {
			computePaths();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
