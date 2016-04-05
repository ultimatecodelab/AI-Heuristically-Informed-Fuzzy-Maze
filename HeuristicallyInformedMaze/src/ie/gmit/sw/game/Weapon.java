package ie.gmit.sw.game;

import ie.gmit.sw.node.NodeType;
/*
 * Weapon : Weapon class for setting up different properties of a weapon. 
 */
public class Weapon {
	private NodeType weaponType;
	private double weaponStrength;
	private int weaponCount;
	//constructor
	public Weapon(NodeType weaponType, double weaponStrength, int weaponCount) {
		setWeaponType(weaponType);
		setWeaponStrength(weaponStrength);
		setWeaponCount(weaponCount);
	}

	public NodeType getWeaponType() {
		return weaponType;
	}

	public void setWeaponType(NodeType weaponType) {
		this.weaponType = weaponType;
	}

	public double getWeaponStrength() {
		return weaponStrength;
	}

	public void setWeaponStrength(double weaponStrength) {
		this.weaponStrength = weaponStrength;
	}

	public int getWeaponCount() {
		return weaponCount;
	}

	public void setWeaponCount(int weaponCount) {
		this.weaponCount = weaponCount;
	}

}
