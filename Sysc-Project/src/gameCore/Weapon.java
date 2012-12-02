package gameCore;

import java.io.Serializable;

/**
 * A Weapon is a type of item that has an attack to increase the character's attack value.
 * 
 * @author Group D
 * @author Main author: Karen Madore
 * 
 * Group D Members
 * ---------------
 * Karen Madore
 * Trang Pham
 * Darrell Penner
 * 
 *
 * @version 1.0
 */
public class Weapon extends Item implements Serializable  {
	private static final long serialVersionUID = 1L;
	//------------Fields------------//
	private int attackValue;
	
	//------------Constructors------------//
	/**
	 * Construct a weapon.
	 * @param name - name of the weapon
	 * @param weight - weight of the weapon
	 * @param attackValue - weapon's attackValue
	 */
	public Weapon(String name, int weight, int attackValue){
		super(name, weight);
		this.attackValue=attackValue;
	}
	

	//------------Getters------------//
	/**
	 * Get the attack value of the weapon
	 * @return attack value of the weapon
	 */
	public int getAttackValue(){
		return attackValue;
	}
}
