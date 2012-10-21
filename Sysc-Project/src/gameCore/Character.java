package gameCore;

/**
 * A Character is a superclass for all of the animated creatures inside the game.
 * Player, and Monsters (subclass of NPC) are subclasses of character.
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
public class Character {
	protected Inventory inventory;
	private int health;
	private String name;
	private int attack;
	protected Tile myPosition;
	
	public Character(String name, int health, int attack, Tile myPosition){
		this.name=name;
		this.health=health;
		this.attack=attack;
		inventory = new Inventory();
		this.myPosition =myPosition;
	}
	
	/*
	 * Subtract the myHealth by value of the other character attack value
	 * Same for the defender.
	 */
	public void attack(Character c){
		
	}
	
	public void use(Item i){
		
	}
	
	public void use(Item i, Character c){
		
	}
	
	public void adjustHealth(int value){
		
	}
		
	public void dropItem(Item i){
		inventory.removeItem(i);
	}
	
	public boolean hasItem(Item i){
		return inventory.containsItem(i);
	}
	
	public boolean isDead(){
		return health==0;
	}
	
	public String getInventoryString(){
		return inventory.toString();
	}

}
