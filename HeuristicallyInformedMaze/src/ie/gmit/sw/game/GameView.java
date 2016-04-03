package ie.gmit.sw.game;

import java.awt.*;

import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

import ie.gmit.sw.node.Node;
import ie.gmit.sw.node.NodeType;

public class GameView extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_VIEW_SIZE = 800;
	private static final int IMAGE_COUNT = 10;
	private int cellspan = 5;
	private int cellpadding = 2;
	private Node[][] maze;
	private BufferedImage[] images;
	private int player_state = 5;
	private Timer timer;
	private int currentRow;
	private int currentCol;
	private boolean zoomOut = false;
	private int imageIndex = -1;
	private int goalNode = 7;
	private int enemy_index = 9;

	public GameView(Node[][] maze) throws Exception {
		init();
		this.maze = maze;
		setBackground(Color.LIGHT_GRAY);
		setDoubleBuffered(true);
		timer = new Timer(300, this);
		setLayout(null);
		timer.start();
	}

	public void setCurrentRow(int row) {
		if (row < cellpadding) {
			currentRow = cellpadding;
		} else if (row > (maze.length - 1) - cellpadding) {
			currentRow = (maze.length - 1) - cellpadding;
		} else {
			currentRow = row;
		}
	}

	public void setCurrentCol(int col) {
		if (col < cellpadding) {
			currentCol = cellpadding;
		} else if (col > (maze[currentRow].length - 1) - cellpadding) {
			currentCol = (maze[currentRow].length - 1) - cellpadding;
		} else {
			currentCol = col;
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		cellspan = zoomOut ? maze.length : 5;
		final int size = DEFAULT_VIEW_SIZE / cellspan;
		g2.drawRect(0, 0, GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);

		for (int row = 0; row < cellspan; row++) {
			for (int col = 0; col < cellspan; col++) {
				int x1 = col * size;
				int y1 = row * size;
				int x2 = (col + 1) * size;
				int y2 = (row + 1) * size;
				NodeType nodeType = maze[row][col].getNodeType();

				if (zoomOut) {
					if (row == currentRow && col == currentCol) {
						g2.setColor(Color.YELLOW);
						g2.fillRect(x1, y1, size, size);
						g2.drawString("You are here.", x1, y1);
						continue;
					}
					if (nodeType == NodeType.ExitPoint) {
						g2.setColor(Color.BLACK);
						g2.fillRect(x1, y1, size, size);
						g2.drawString("Exit point", x1, y1);
						continue;
					}
					if (nodeType == NodeType.EnemyNode) {
						g2.setColor(Color.BLUE);
						g2.fillRect(x1, y1, size, size);
						continue;
					}

					if (nodeType == NodeType.HydrogenBombNode) {
						g2.setColor(Color.BLACK);
						// g2.fillRect(x1, y1, size, size);
						g2.drawString("H. Bomb", x1, y1);
						continue;
					}
					if (nodeType == NodeType.SwordNode) {
						g2.setColor(Color.BLACK);
						// g2.fillRect(x1, y1, size, size);
						g2.drawString("Sword", x1, y1);
						continue;
					}
					if (nodeType == NodeType.BombNode) {
						g2.setColor(Color.BLACK);
						g2.drawString("Bomb", x1, y1);
						continue;
					}
					if (nodeType == NodeType.HelpNode) {
						g2.setColor(Color.BLACK);
						g2.drawString("Help", x1, y1);
						continue;
					}

				} else {
					nodeType = maze[currentRow - cellpadding + row][currentCol - cellpadding + col].getNodeType();
				}

				if (nodeType == NodeType.WallNode) { // x = wallnode
					imageIndex = 0;
				} else if (nodeType == NodeType.SwordNode) {
					imageIndex = 1;
				} else if (nodeType == NodeType.HelpNode) {
					imageIndex = 2;
				} else if (nodeType == NodeType.BombNode) {
					imageIndex = 3;
				} else if (nodeType == NodeType.HydrogenBombNode) {

					imageIndex = 4;
				} else if (nodeType == NodeType.PlayerNode) {
					imageIndex = player_state;
				} else if (nodeType == NodeType.EnemyNode) {
					imageIndex = enemy_index;
				} else if (nodeType == NodeType.GoalNode) {
					imageIndex = goalNode;
				} else if (nodeType == NodeType.EnemyNode) {
					imageIndex = 9;
				} else if (nodeType == NodeType.ExitPoint) {
					imageIndex = 7;
				} else if (nodeType == NodeType.PathNode) {
					imageIndex = 8;
				} else {
					imageIndex = -1;
				}

				if (imageIndex >= 0) {
					g2.drawImage(images[imageIndex], x1, y1, null);

				} else {

					g2.setColor(Color.LIGHT_GRAY);
					g2.fillRect(x1, y1, size, size);
				}

				/*
				 * if (maze[row][col].isVisited() &&
				 * !(maze[row][col].getNodeType()==NodeType.PlayerNode) &&
				 * maze[row][col].getNodeType() == NodeType.WalkableNode) {
				 * g2.setColor(maze[row][col].getColor());
				 * 
				 * g2.fillRect(x1, y1, size, size); }
				 */

			}
		}
	}

	public void toggleZoom() {
		zoomOut = !zoomOut;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (player_state < 0 || player_state == 5) {
			player_state = 6;
		} else {
			player_state = 5;
		}
		this.repaint();
	}

	private void init() throws Exception {
		images = new BufferedImage[IMAGE_COUNT];
		images[0] = ImageIO.read(new java.io.File("resources/hedge.png"));
		images[1] = ImageIO.read(new java.io.File("resources/sword.png"));
		images[2] = ImageIO.read(new java.io.File("resources/help.png"));
		images[3] = ImageIO.read(new java.io.File("resources/bomb.png"));
		images[4] = ImageIO.read(new java.io.File("resources/h_bomb.png"));
		images[5] = ImageIO.read(new java.io.File("resources/characterhalt.png"));
		images[6] = ImageIO.read(new java.io.File("resources/charactermoving.png"));
		images[7] = ImageIO.read(new java.io.File("resources/castle.png"));
		images[8] = ImageIO.read(new java.io.File("resources/reddot.png"));
		images[9] = ImageIO.read(new java.io.File("resources/spider_up.png"));

	}
}