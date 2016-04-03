package ie.gmit.sw.game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import ie.gmit.sw.ai.AStarTraversator;
import ie.gmit.sw.ai.TraversatorStats;

import ie.gmit.sw.fuzzylogic.FuzzyLogic;
import ie.gmit.sw.node.Maze;
import ie.gmit.sw.node.Node;
import ie.gmit.sw.node.NodeType;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameRunner implements KeyListener {

	private static final int MAZE_DIMENSION = 70;
	private Node[][] model;
	private GameView view;
	private int currentRow;
	private int currentCol;
	Node goal;
	Node enemy;
	Maze m = null;
	private Node player;
	private Player playerStuffs = new Player();
	private int nEnemies = 30; // enemies
	ExecutorService ex = Executors.newCachedThreadPool();
	AStarTraversator playerPath;

	// panel
	JPanel panel;
	// Labels
	JLabel lblHealth_1;
	JLabel lblMyWeapons;
	JLabel lblHydrogenBomb;
	JLabel lblHealth;
	JLabel lblHydrogenBomb_1;
	JLabel lblBomb;
	JLabel lblBomb_1;
	JLabel lblSword;
	JLabel lblSword_1;
	JLabel lblGoalNode;
	JLabel goalNodeCount;
	JLabel lblEnemiesCount;

	public GameRunner() throws Exception {

		m = new Maze(MAZE_DIMENSION, MAZE_DIMENSION);
		model = m.getMaze();

		view = new GameView(model);
		view.setBounds(192, 5, 770, 800);
		Dimension d = new Dimension(GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);

		view.setPreferredSize(d);
		view.setMinimumSize(d);
		view.setMaximumSize(d);

		JFrame f = new JFrame("GMIT - B.Sc. in Computing (Software Development)");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.addKeyListener(this);
		f.getContentPane().setLayout(null);
		f.getContentPane().add(view);

		f.setSize(1200, 900);

		f.setLocation(100, 100);

		f.setVisible(true);
		
		guiElementsInit(f); // init gui elements
		
		placePlayer();// place player
		
		setGoal();// set goal
		
		periodicPathUpdate();// constant path update
		
		validatePath(); // validate path
		
		spawnEnemies();// spawn enemies
	}

	private void guiElementsInit(JFrame f) {
		panel = new JPanel();
		panel.setBounds(0, 5, 191, 532);
		f.getContentPane().add(panel);
		panel.setLayout(null);

		lblHealth = new JLabel("0");
		lblHealth.setBounds(157, 11, 24, 14);
		panel.add(lblHealth);

		lblHealth_1 = new JLabel("Health");
		lblHealth_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblHealth_1.setBounds(10, 11, 46, 14);
		panel.add(lblHealth_1);

		lblMyWeapons = new JLabel("My Weapons");
		lblMyWeapons.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblMyWeapons.setBounds(10, 36, 139, 24);
		panel.add(lblMyWeapons);

		lblHydrogenBomb = new JLabel("0");
		lblHydrogenBomb.setBounds(157, 76, 24, 14);
		panel.add(lblHydrogenBomb);

		lblHydrogenBomb_1 = new JLabel("Hydrogen Bomb");
		lblHydrogenBomb_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblHydrogenBomb_1.setBounds(10, 76, 114, 14);
		panel.add(lblHydrogenBomb_1);

		lblBomb = new JLabel("0");
		lblBomb.setBounds(157, 101, 24, 14);
		panel.add(lblBomb);

		lblBomb_1 = new JLabel("Bomb");
		lblBomb_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblBomb_1.setBounds(10, 101, 46, 14);
		panel.add(lblBomb_1);

		lblSword = new JLabel("0");
		lblSword.setBounds(157, 127, 24, 14);
		panel.add(lblSword);

		lblSword_1 = new JLabel("Sword");
		lblSword_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblSword_1.setBounds(10, 126, 46, 14);
		panel.add(lblSword_1);

		lblGoalNode = new JLabel("Goal Node  : ");
		lblGoalNode.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblGoalNode.setBounds(10, 201, 114, 14);
		panel.add(lblGoalNode);

		goalNodeCount = new JLabel("0");
		goalNodeCount.setBounds(157, 202, 24, 14);
		panel.add(goalNodeCount);

		JLabel lblNewLabel = new JLabel("Total Enemies");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 164, 114, 14);
		panel.add(lblNewLabel);

		lblEnemiesCount = new JLabel("0");
		lblEnemiesCount.setBounds(157, 164, 24, 14);
		panel.add(lblEnemiesCount);
	}

	private void setGoal() {
		goal = m.getGoalNode();
		goal.setGoalNode(true);
	}

	private void spawnEnemies() throws InterruptedException {
		for (int i = 0; i < nEnemies; i++) {
			ex.execute(new Enemy(model, player));
			System.out.println("executing enemies...");
		}
	}

	private void periodicPathUpdate() {
		goal = m.getGoalNode();
		goal.setNodeType(NodeType.ExitPoint);
		System.out.println(goal.getRow() + " : " + goal.getCol());
		AStarTraversator update = new AStarTraversator(goal);
		System.out.println(model.length + " is model.");
		System.out.println("Current row is: " + currentRow);
		System.out.println("Current col is: " + currentCol);
		update.traverse(model, model[currentRow][currentCol]);
		updateGUI(update);

	}

	private void validatePath() throws InterruptedException {
		int checker = (int) TraversatorStats.depth;
		if (checker == 0) {
			goal.setNodeType(NodeType.WalkableNode);
		}
		while (checker < 10) {
			m.setExitPoint();
			goal.setNodeType(NodeType.WalkableNode);
			setGoal();
			System.out.println("recalculating the path...");
			playerPath.traverse(model, model[player.getRow()][player.getCol()]);
			System.out.println("finished calculating...");
			checker = (int) TraversatorStats.depth;
			if (checker > 10) {
				break;
			}
		}
	}

	private void updateGUI(AStarTraversator playerPath) {
		goalNodeCount.setText(String.valueOf((int) TraversatorStats.depth));
		lblHydrogenBomb.setText(String.valueOf(playerStuffs.getHyDrogenCount()));
		lblBomb.setText(String.valueOf(playerStuffs.getBombCount()));
		lblSword.setText(String.valueOf(playerStuffs.getSwordCount()));
		lblHealth.setText(String.valueOf(playerStuffs.myLifeForce()));
		lblEnemiesCount.setText(String.valueOf(nEnemies));
	}

	private void placePlayer() {
		m.setPlayer();
		player = m.getPlayer();
		currentRow = player.getRow();
		currentCol = player.getCol();
		model[currentRow][currentCol].setNodeType(NodeType.PlayerNode);
		updateView();
	}

	private void updateView() {
		view.setCurrentRow(currentRow);
		view.setCurrentCol(currentCol);
	}

	@Override
	public void keyPressed(KeyEvent e) {

		periodicPathUpdate();

		if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentCol < MAZE_DIMENSION - 1) {
			if (isValidMove(currentRow, currentCol + 1)) {
				currentCol++;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT && currentCol > 0) {
			if (isValidMove(currentRow, currentCol - 1))
				currentCol--;
		} else if (e.getKeyCode() == KeyEvent.VK_UP && currentRow > 0) {
			if (isValidMove(currentRow - 1, currentCol))
				currentRow--;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN && currentRow < MAZE_DIMENSION - 1) {
			if (isValidMove(currentRow + 1, currentCol))
				currentRow++;
		} else if (e.getKeyCode() == KeyEvent.VK_Z) {
			view.toggleZoom();
		} else {
			return;
		}

		updateView();
	}

	public boolean fight(Player player, Enemy opponent) {
		FuzzyLogic f = new FuzzyLogic();

		f.fight(player.randomlyPickedWeapon(), player, opponent);

		return false;
	}

	@Override
	public void keyReleased(KeyEvent e) {
	} // Ignore

	@Override
	public void keyTyped(KeyEvent e) {
	} // Ignore

	private boolean isValidMove(int r, int c) {
		if (r <= model.length - 1 && c <= model[r].length - 1 && (model[r][c].getNodeType() == NodeType.WalkableNode)
				|| model[r][c].getNodeType() == NodeType.PathNode || model[r][c].getNodeType() == NodeType.BombNode
				|| model[r][c].getNodeType() == NodeType.HydrogenBombNode
				|| model[r][c].getNodeType() == NodeType.SwordNode || model[r][c].getNodeType() == NodeType.HelpNode
				|| model[r][c].getNodeType() == NodeType.ExitPoint) {

			if (model[r][c].getNodeType() != NodeType.WalkableNode && model[r][c].getNodeType() != NodeType.HelpNode) {
				playerStuffs.addWeaponCount(model[r][c].getNodeType());
				ie.gmit.sw.sound.SoundEffects.MOVE.play();
			}
			if (model[r][c].getNodeType() == NodeType.ExitPoint) {
				MessageBox.info("Congrats:) ", "You have escapped");
				System.exit(0); // exit the game...
			}
			if (model[r][c].getNodeType() == NodeType.HelpNode) {
				model[r][c].setNodeType(NodeType.WalkableNode);
			}
			if (model[r][c].getNodeType() == NodeType.EnemyNode) {
				model[r][c].setNodeType(NodeType.WalkableNode);
			}

			model[r][c].setNodeType(NodeType.PlayerNode);
			model[currentRow][currentCol].setNodeType(NodeType.WalkableNode);

			return true;
		} else if (r <= model.length - 1 && c <= model[r].length - 1
				&& (model[r][c].getNodeType() == NodeType.EnemyNode)) {
			model[currentRow][currentCol].setNodeType(NodeType.WalkableNode);
			System.out.println("fighting...");
			fight(playerStuffs, new Enemy());
			return true;
		} else {
			return false; // Can't move
		}
	}

	public static void main(String[] args) throws Exception {
		new GameRunner();
	}
}