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
public class Character  {
	//------------Fields------------//
	protected Inventory inventory;
	protected int health;
	
	private String name;
	private int attack;
	protected Tile myPosition;
	
	//------------Constructors------------//
	/**
	 * Constructs a character
	 * 
	 * @param name - the name of the character
	 * @param health - a character starts with an initial health value. Health points are lost during battles, 0 points=character dead  
	 * @param attack - a character starts with an initial attack value. Attack points are lost/gained during battles. 
	 * @param myPosition - a tile where the character is currently located
	 */
	public Character(String name, int health, int attack, Tile myPosition){
		this.name=name;
		this.health=health;
		this.attack=attack;
		inventory = new Inventory();
		this.myPosition =myPosition;
		//add myself to the tile 
		myPosition.addCharacter(this); 
	}
	
	/**
	 * Attack method allows one character to attack another.  Health points are lost during an attack.
	 * 
	 * @param c - another character.  The defender of the attack.
	 * @return true if defender died and I have moved. 
	 * Comments: Subtract the myHealth by value of the other character attack value
	 * Same for the defender.
	 */
	public boolean attack(Character c){
		
		this.adjustHealth(0-c.attack);
		if(this.isDead()){
			this.die();
			return false;
		}
		
		c.adjustHealth(0-this.attack);
		if(c.isDead()){
			c.die();
			return true;
		}
		return false;
	}
	
	/**
	 * Use the item up - ie a weapon will increase your attack value
	 * 
	 * --- not used in v1 ---
	 * 
	 * @param i - the item chosen from character's inventory
	 */
	public void use(Item i){
		//Potions - increase your health points
		
		//Keys - ??
		
		//
		
	}
	
	/**
	 * Method to use an item like a weapon on a character. 
	 * 
	 * @param i - item to use.  It will be removed from the characters inventory unless it is a weapon.
	 * @param c - character to use this item on.
	 */
	
	public void use(Item i, Character c){
		if(i instanceof Weapon){
			//increase the health during the attack and remove after.  Death of character is checked after.
			int wAttackVal;
			Weapon w= (Weapon) i;
			
			wAttackVal = w.getAttackValue();
			this.health=this.health+wAttackVal;
			attack(c);
			die();
		}
	}
	
	/**
	 * Adjust the Health of player
	 * @param value - a negative value decreases the player's health, a positive value increases the player's health
	 */
	
	public void adjustHealth(int value){
		int h=0;
		h=this.health+value;
		if(h<0){
			this.health=0;
		}else{
			this.health=h;
		}
		
	}
	/**
	 * A method to clean up the character from the tile
	 */
	protected void die(){
		if(this.isDead()){ //drop the character's inventory
			for(int i=0; i<this.inventory.size(); i++){
				dropItem(this.inventory.getItem(i));
			}
			myPosition.removeCharacter(); //remove character from tile
		}
	}
	/**
	 * Removes the item from the Characters inventory and add it to the tile's inventory
	 * 
	 * @param i
	 */
	public void dropItem(Item i){
		inventory.removeItem(i); 
		myPosition.addItem(i);
	}
	
	/**
	 * Check to see if the character has the item in his inventory
	 * 
	 * @param 	i - item you wish to check
	 * @return	true if item is found in character's inventory, otherwise false
	 */
	public boolean hasItem(Item i){
		return inventory.containsItem(i);
	}
	
	/**
	 * Check to see if character is dead.  Currently, if the health value reaches 0.
	 * 
	 * @return	true if character is dead, otherwise false.
	 */
	public boolean isDead(){
		return health==0;
	}
	
	/**
	 * Construct a string of the character's inventory
	 * 
	 * @return	a string representation of the character's inventory
	 */
	public String getInventoryString(){
		return inventory.toString();
	}
	/**
	 * Returns the inventory for use in the View
	 * @return the inventory the player is carrying
	 */
	public Inventory getInventory(){
		return inventory;
	}
	
	/**
	 * addItem is a method used to load the non-player's inventory
	 * 
	 */
	public void addItem(Item i){
		this.inventory.addItem(i);
	}

	@Override
	public String toString(){
		return name + "(hp: " + health + " atk:" + attack +")" ;
	}
	
	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
