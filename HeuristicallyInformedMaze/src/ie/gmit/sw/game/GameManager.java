package ie.gmit.sw.game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import ie.gmit.sw.fuzzylogic.FuzzyLogic;
import ie.gmit.sw.node.Maze;
import ie.gmit.sw.node.Node;
import ie.gmit.sw.node.NodeType;
import ie.gmit.sw.traversers.AStarTraversator;
import ie.gmit.sw.traversers.Traversator;
import ie.gmit.sw.traversers.TraversatorStats;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameManager implements KeyListener {

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
	private int nEnemies = 20; // enemies
	ExecutorService ex = Executors.newCachedThreadPool();
	Traversator playerPath;

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
	private JLabel lblFuzzyLogicVictory;
	private JLabel labelFuzzy;
	private JLabel lblYourMoves;
	private JLabel nodesIExplored;
	private JLabel lblNewLabel;
	JLabel lblControls;
	JLabel lblUpArrow;
	JLabel lblDownArrow;
	JLabel lblLeft;
	JLabel lblRightArrow;
	JLabel lblAlgorithms;
	JLabel lblPlayerAstar;
	JLabel lblEnemiesdepthLimiteddfs;
	private JLabel lblMoves;
	private JLabel lblFuzzylogicjar;

	public GameManager() throws Exception {

		m = new Maze(MAZE_DIMENSION, MAZE_DIMENSION);
		model = m.getMaze();

		view = new GameView(model);
		view.setBounds(192, 5, 770, 800);
		Dimension d = new Dimension(GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);

		view.setPreferredSize(d);
		view.setMinimumSize(d);
		view.setMaximumSize(d);

		JFrame f = new JFrame("GMIT - B.Sc. in Computing (Software Development) - Arjun Kharel (G00298984)");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.addKeyListener(this);
		f.getContentPane().setLayout(null);
		f.getContentPane().add(view);

		f.setSize(1200, 900);

		f.setLocation(100, 100);

		f.setVisible(true);

		placePlayer();// place player

		guiElementsInit(f); // init gui elements

		setGoal();// set goal

		validatePath(); // validate path

		spawnEnemies();// spawn enemies
	}

	private void guiElementsInit(JFrame f) {
		panel = new JPanel();
		panel.setForeground(Color.GRAY);
		panel.setBounds(0, 5, 191, 638);
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
		lblMyWeapons.setForeground(Color.BLUE);
		lblMyWeapons.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblMyWeapons.setBounds(10, 36, 139, 24);
		panel.add(lblMyWeapons);

		lblHydrogenBomb = new JLabel("0");
		lblHydrogenBomb.setBounds(157, 76, 24, 14);
		panel.add(lblHydrogenBomb);

		lblHydrogenBomb_1 = new JLabel("Hydrogen Bomb");
		lblHydrogenBomb_1.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblHydrogenBomb_1.setBounds(10, 76, 114, 14);
		panel.add(lblHydrogenBomb_1);

		lblBomb = new JLabel("0");
		lblBomb.setBounds(157, 101, 24, 14);
		panel.add(lblBomb);

		lblBomb_1 = new JLabel("Bomb");
		lblBomb_1.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblBomb_1.setBounds(10, 101, 46, 14);
		panel.add(lblBomb_1);

		lblSword = new JLabel("0");
		lblSword.setBounds(157, 127, 24, 14);
		panel.add(lblSword);

		lblSword_1 = new JLabel("Sword");
		lblSword_1.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblSword_1.setBounds(10, 126, 46, 14);
		panel.add(lblSword_1);

		lblGoalNode = new JLabel("Exit : ");
		lblGoalNode.setForeground(Color.BLUE);
		lblGoalNode.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblGoalNode.setBounds(10, 201, 64, 14);
		panel.add(lblGoalNode);

		goalNodeCount = new JLabel("0");
		goalNodeCount.setBounds(69, 202, 25, 14);
		panel.add(goalNodeCount);

		lblNewLabel = new JLabel("Total Enemies");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 164, 114, 14);
		panel.add(lblNewLabel);

		lblEnemiesCount = new JLabel("0");
		lblEnemiesCount.setBounds(157, 164, 24, 14);
		panel.add(lblEnemiesCount);

		lblControls = new JLabel("Controls");
		lblControls.setForeground(Color.BLUE);
		lblControls.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblControls.setBounds(10, 247, 114, 14);
		panel.add(lblControls);

		lblUpArrow = new JLabel("Up Arrow");
		lblUpArrow.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblUpArrow.setBounds(10, 272, 70, 14);
		panel.add(lblUpArrow);

		lblDownArrow = new JLabel("Down Arrow");
		lblDownArrow.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblDownArrow.setBounds(10, 297, 99, 14);
		panel.add(lblDownArrow);

		lblLeft = new JLabel("Left Arrow");
		lblLeft.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblLeft.setBounds(10, 319, 114, 14);
		panel.add(lblLeft);

		lblRightArrow = new JLabel("Right Arrow");
		lblRightArrow.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblRightArrow.setBounds(10, 344, 114, 14);
		panel.add(lblRightArrow);

		lblAlgorithms = new JLabel("Algorithms");
		lblAlgorithms.setForeground(Color.BLUE);
		lblAlgorithms.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblAlgorithms.setBounds(10, 404, 99, 14);
		panel.add(lblAlgorithms);

		lblPlayerAstar = new JLabel("Player : AStar");
		lblPlayerAstar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPlayerAstar.setBounds(10, 429, 139, 14);
		panel.add(lblPlayerAstar);

		lblEnemiesdepthLimiteddfs = new JLabel("Enemies :Depth LimitedDFS");
		lblEnemiesdepthLimiteddfs.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEnemiesdepthLimiteddfs.setBounds(10, 454, 171, 14);
		panel.add(lblEnemiesdepthLimiteddfs);

		lblFuzzyLogicVictory = new JLabel("Fuzzy Logic Victory");
		lblFuzzyLogicVictory.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFuzzyLogicVictory.setBounds(10, 479, 150, 14);
		panel.add(lblFuzzyLogicVictory);

		labelFuzzy = new JLabel("No enemy collided ");
		labelFuzzy.setBounds(10, 504, 171, 14);
		panel.add(labelFuzzy);

		lblYourMoves = new JLabel("Nodes I explored:");
		lblYourMoves.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblYourMoves.setForeground(Color.BLUE);
		lblYourMoves.setBounds(10, 222, 126, 14);
		panel.add(lblYourMoves);

		nodesIExplored = new JLabel("0");
		nodesIExplored.setBounds(157, 223, 24, 14);
		panel.add(nodesIExplored);
		
		lblMoves = new JLabel("Moves away");
		lblMoves.setBounds(111, 202, 70, 14);
		panel.add(lblMoves);
		
		JLabel lblZToView = new JLabel("Z to view the map");
		lblZToView.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblZToView.setBounds(10, 369, 114, 14);
		panel.add(lblZToView);
		
		JLabel lblNewLabel_1 = new JLabel("External Dependency");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setBounds(10, 545, 171, 14);
		panel.add(lblNewLabel_1);
		
		lblFuzzylogicjar = new JLabel("Fuzzy logic library");
		lblFuzzylogicjar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFuzzylogicjar.setBounds(10, 570, 150, 14);
		panel.add(lblFuzzylogicjar);
		panel.repaint();
	}

	private void setGoal() {
		this.goal = m.getGoalNode();
		this.goal.setGoalNode(true);
	}

	private void spawnEnemies() throws InterruptedException {
		for (int i = 0; i < nEnemies; i++) {
			ex.execute(new Enemy(model, player));
		}
	}

	private void constantPathUpdate() {
		goal = m.getGoalNode();
		System.out.println("Goal node at: " + goal.getRow() + " : " + goal.getCol());
		AStarTraversator update = new AStarTraversator(goal);
		update.traverse(model, model[currentRow][currentCol]);
		updateGUI(update);

	}

	private void validatePath() throws InterruptedException {
		int checker = (int) TraversatorStats.depth;
		if (checker == 0) {
			goal.setNodeType(NodeType.WalkableNode);
		}
		while (checker < 20) {
			m.setExitPoint();
			goal.setNodeType(NodeType.WalkableNode);
			goal = m.getGoalNode();
			System.out.println("recalculating the path...");
			Traversator playerPath = new AStarTraversator(goal);
			playerPath.traverse(model, model[player.getRow()][player.getCol()]);
			System.out.println("finished calculating...");
			checker = (int) TraversatorStats.depth;
			if (checker > 20) {
				break;
			}
		}
	}

	private void updateGUI(Traversator playerPath) {
		goalNodeCount.setText(String.valueOf((int) TraversatorStats.depth-1));
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

		if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentCol < MAZE_DIMENSION - 1) {
			if (isValidMove(currentRow, currentCol + 1)) {
				currentCol++;
				constantPathUpdate();
				Player.incrementExploredMoves();
				nodesIExplored.setText(String.valueOf(Player.getExploredMoves()));
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT && currentCol > 0) {
			if (isValidMove(currentRow, currentCol - 1)) {
				currentCol--;
				constantPathUpdate();
				Player.incrementExploredMoves();
				nodesIExplored.setText(String.valueOf(Player.getExploredMoves()));
			}
		} else if (e.getKeyCode() == KeyEvent.VK_UP && currentRow > 0) {
			if (isValidMove(currentRow - 1, currentCol)) {
				currentRow--;
				constantPathUpdate();
				Player.incrementExploredMoves();
				nodesIExplored.setText(String.valueOf(Player.getExploredMoves()));
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN && currentRow < MAZE_DIMENSION - 1) {
			if (isValidMove(currentRow + 1, currentCol)) {
				currentRow++;
				constantPathUpdate();
				Player.incrementExploredMoves();
				nodesIExplored.setText(String.valueOf(Player.getExploredMoves()));
			}
		} else if (e.getKeyCode() == KeyEvent.VK_Z) {
			view.toggleZoom();
		} else {
			return;
		}

		updateView();
	}

	public void fight(Player player, Enemy opponent) {
		FuzzyLogic f = new FuzzyLogic();
		labelFuzzy.setText(String.valueOf(f.fight(player.randomlyPickedWeapon(), player, opponent)));
		this.nEnemies--;
		if (player.myLifeForce() <= 0) {
			MessageBox.info("Sorry you died :(,game will be closed now.", "Fuzzy Logic");
			System.exit(0); // exit the game...
		}
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
				|| model[r][c].getNodeType() == NodeType.SwordNode
				|| model[r][c].getNodeType() == NodeType.ExitPoint) {

			if (model[r][c].getNodeType() != NodeType.WalkableNode) {
				playerStuffs.addWeaponCount(model[r][c].getNodeType());
				ie.gmit.sw.sound.SoundEffects.MOVE.play();
			}
			if (model[r][c].getNodeType() == NodeType.ExitPoint) {
				MessageBox.info("Congrats,You have escapped in " + Player.getExploredMoves() + " moves");
				System.exit(0); // exit the game...
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
}