package ie.gmit.sw.node;

import java.util.Random;

import ie.gmit.sw.game.Player;

public class Maze {
	private int MAZE_DIMENSION = 70;
	private int NUMBER_OF_EXIST_POINTS = 1;
	private Node[][] maze = new Node[MAZE_DIMENSION][MAZE_DIMENSION];

	private Node player;
	private Player playerChar;
	private Node goalNode;
	private int row=0;
	private int col=0;
	
	public Maze(int rows, int cols) {
		this.row = rows;
		this.col = cols;
		this.MAZE_DIMENSION = rows;
		
		initMaze();
		populateFeatures(rows, cols);
		buildMaze();
		setExitPoint();
		buildPath();
	}

	private void buildPath() {

		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[row].length; col++) {
				if (col < maze[row].length - 1) {
					if (maze[row][col + 1].getNodeType() == NodeType.WalkableNode) {
						maze[row][col].addPath(Node.Direction.West);
					}
				}

				if (col > 0) {
					if (maze[row][col - 1].getNodeType() == NodeType.WalkableNode) {
						maze[row][col].addPath(Node.Direction.East);
					}
				}
				if (row < maze.length - 1) {
					if (maze[row + 1][col].getNodeType() == NodeType.WalkableNode) {
						maze[row][col].addPath(Node.Direction.North);
					}
				}
				if (row > 0) {
					if (maze[row - 1][col].getNodeType() == NodeType.WalkableNode) {
						maze[row][col].addPath(Node.Direction.South);
					}
				}
			}
		}

	}

	private void populateFeatures(int rows, int cols) {
		
		int featureNumber = (int) ((rows * cols) * 0.01);
		addFeature(NodeType.SwordNode, NodeType.WallNode, featureNumber);
		addFeature(NodeType.HelpNode, NodeType.WallNode, featureNumber);
		addFeature(NodeType.HydrogenBombNode, NodeType.WallNode, featureNumber);
		addFeature(NodeType.BombNode, NodeType.WallNode, featureNumber);
	}

	private void addFeature(NodeType feature, NodeType replace, int featureNumber) {
		int counter = 0;
		while (counter < featureNumber) {
			int row = (int) (maze.length * Math.random());
			int col = (int) (maze[0].length * Math.random());

			if (maze[row][col].nodeType == replace) {
				maze[row][col].nodeType = feature;
				counter++;
			}
		}
	}

	private void initMaze() {
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[row].length; col++) {
				maze[row][col] = new Node(row, col);
				maze[row][col].setNodeType(NodeType.WallNode);
			}
		}
	}

	public void buildMaze() {
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[row].length - 1; col++) {
				int num = (int) (Math.random() * 10);

				if (num >= 5 && col + 1 < maze[row].length - 1) {
					maze[row][col + 1].nodeType = NodeType.WalkableNode;
					continue;
				}
				if (row + 1 < maze.length) { // Check south
					maze[row + 1][col].nodeType = NodeType.WalkableNode;
				}

			}
		}
	}

	public Node getPlayer() {
		return player;
	}

	public void setPlayer() {
	    int currentRow = (int) ((MAZE_DIMENSION-20) * Math.random());
		int currentCol = (int) ((MAZE_DIMENSION-20) * Math.random());
		
		while(maze[currentRow][currentCol].getNodeType()!=NodeType.WalkableNode){
			 currentRow = (int) ((MAZE_DIMENSION-20) * Math.random());
			 currentCol = (int) ((MAZE_DIMENSION-20) * Math.random());
		}
		maze[currentRow][currentCol].setNodeType(NodeType.PlayerNode);
		System.out.println("player at: " + currentRow + " Col : " + currentCol);
		player = maze[currentRow][currentCol];
	}

	public Node getGoalNode() {
		return goalNode;
	}

	public void setExitPoint() {
		Random ran = new Random();
		for (int i = 0; i < NUMBER_OF_EXIST_POINTS; i++) {
			int currentRow = ran.nextInt(MAZE_DIMENSION-10) + 10;
			int currentCol = ran.nextInt(MAZE_DIMENSION-10) + 10;
			while(maze[currentRow][currentCol].getNodeType()!=NodeType.WalkableNode || 
					maze[currentRow][currentCol].getNodeType()==NodeType.PlayerNode){
				 currentRow = ran.nextInt(MAZE_DIMENSION-10) + 10;
				 currentCol = ran.nextInt(MAZE_DIMENSION-10) + 10;
			}
			this.getMaze()[currentRow][currentCol].setNodeType(NodeType.ExitPoint);
			goalNode = this.getMaze()[currentRow][currentCol];
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[row].length; col++) {
				sb.append(maze[row][col].nodeType);
				if (col < maze[row].length - 1)
					sb.append(",");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public Node[][] getMaze() {
		return this.maze;
	}

}
