package ie.gmit.sw.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ie.gmit.sw.node.NodeType;
/*
 * Player class is responsible for keeping track of player movement,health,weapons etc
 */
public class Player {
	private static int playerHealth = 100;
	private int INITIAL_COUNT = 0;
	private List<Weapon> myWeapons = new ArrayList<>();

	private static int myExploredNodes = 0;

	public static void incrementExploredMoves() {
		myExploredNodes++;
	}

	public static int getExploredMoves() {
		return myExploredNodes;
	}

	public Player() {
		intWeaponsCountWithZeros();
	}

	public void decrementPlayerHealth(double d) {
		playerHealth -= d;
	}

	public int myLifeForce() {
		return playerHealth;
	}

	public boolean addWeaponCount(NodeType wep) {
		Weapon tempWeb;
		for (int i = 0; i < myWeapons.size(); i++) {
			tempWeb = myWeapons.get(i);

			if (wep == tempWeb.getWeaponType()) {
				tempWeb.setWeaponCount(tempWeb.getWeaponCount() + 1);
				System.out.println("Weapon: " + wep + " : " + tempWeb.getWeaponCount());
				return true;
			} else {
				System.out.println("Your weapon: " + wep);
				System.out.println("Curr wep: " + tempWeb.getWeaponType());
			}
		}
		return false;
	}
	//when enemy is encountered, random weapon will be picked to fight with an enemy.
	//fuzzy logic evaluates the strength of a weapon and player health to determine the victory level.
	public Weapon randomlyPickedWeapon() {
		Random rand = new Random();
		int myWeapon = rand.nextInt(myWeapons.size() - 1);
		if (myWeapons.get(myWeapon).getWeaponCount() > 0) {
			myWeapons.get(myWeapon).setWeaponCount(myWeapons.get(myWeapon).getWeaponCount() - 1); // decrement the count																						
		}
		return myWeapons.get(myWeapon);
	}
	
	//init weapons with zero initially
	public void intWeaponsCountWithZeros() {
		myWeapons.add(new Weapon(NodeType.HydrogenBombNode, 99, INITIAL_COUNT));
		myWeapons.add(new Weapon(NodeType.BombNode, 69, INITIAL_COUNT));
		myWeapons.add(new Weapon(NodeType.SwordNode, 39, INITIAL_COUNT));
		System.out.println("the size of weapon list is: " + myWeapons.size());
	}
	
	//returning hydrogen bomg
	public int getHyDrogenCount() {
		return myWeapons.get(0).getWeaponCount();
	}
	//returning bomb count
	public int getBombCount() {
		return myWeapons.get(1).getWeaponCount();
	}
	//returning sword count
	public int getSwordCount() {
		return myWeapons.get(2).getWeaponCount();
	}

}
