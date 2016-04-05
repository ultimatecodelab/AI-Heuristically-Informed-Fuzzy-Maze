package ie.gmit.sw.game;

import java.util.Random;

import ie.gmit.sw.node.Node;
import ie.gmit.sw.node.NodeType;
import ie.gmit.sw.traversers.DepthLimitedDFSTraversator;
public class Enemy implements Runnable {
	private Node[][] maze;
	private int row;
	private int col;
	private int enemyStrength = 100;
	private Node targetNode;
	
	public Enemy() {

	}

	//enemy strength
	public int getLifeForce() {
		return enemyStrength;
	}
	//setting x and y position in the 
	private void setXandY() {
		int offSet = (int) ((int) maze.length * 0.20);
		Random ran = new Random();
		int row = ran.nextInt(this.maze.length - offSet) + offSet;
		int col = ran.nextInt(this.maze[0].length - offSet) + offSet;
		this.row = row;
		this.col = col;
		maze[row][col].setNodeType(NodeType.EnemyNode);
	}

	

	public Enemy(Node[][] maze, Node targetNode) throws InterruptedException {
		this.maze = maze;
		this.targetNode = targetNode;
		setXandY();
	}
	//updating the enemy path. Enemies are moving through the maze using DepthLimitedDFS. 
	//After every move, updatePath() is called by an algorithm.
	public void updatePath(int x, int y) throws InterruptedException {
		if (maze[this.row][this.col].getNodeType() != NodeType.PlayerNode) {
			maze[this.row][this.col].setNodeType(NodeType.WalkableNode);
			maze[x][y].setNodeType(NodeType.EnemyNode);
			this.setRow(x);
			this.setCol(y);
		}
	}
	//setting row
	public void setRow(int row) {
		this.row = row;
	}
	//setting col
	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	//Firing DepthLimitedDFS for enemies movement
	private void traverse() throws InterruptedException {
		DepthLimitedDFSTraversator tr = new DepthLimitedDFSTraversator(200, this);
		tr.traverse(maze, maze[this.getRow()][this.getCol()]);
	}

	/*public List<Node> getComputedPaths() {
		return paths;
	}*/

	@Override
	public void run() {
		try {
			traverse();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
