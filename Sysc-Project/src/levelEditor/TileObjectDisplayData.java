package levelEditor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gameCore.*;
import gameCore.Character;

/**
 * TileObjectDisplayData is a factory 
 * 
 * @author Group D
 * @author Main Author: Darrell Penner
 * 
 * Group D Members
 * ---------------
 * Karen Madore
 * Trang Pham
 * Darrell Penner
 * 
 *
 * @version 1.0
 *
 */

class TileObjectDisplayData implements Iterable<TileObjectDisplayData.TileObjectDisplayDatum> {
	
	String type;
	List<TileObjectDisplayDatum> displayData;
	
	private TileObjectDisplayData(String type){
		this.type = type;
		displayData = new ArrayList<TileObjectDisplayDatum>();
	}
	
	//------------Factory Methods------------//	
	public static TileObjectDisplayData getItemDisplayData(Item i){
		TileObjectDisplayData data = new TileObjectDisplayData("Item");
		if (i == null){
			addBasicItemData(data, "New Item", "0");
		}
		else {
			addBasicItemData(data, i.getName(), Integer.toString(i.getWeight()));
		}
		return data;
	}
	
	public static TileObjectDisplayData getWeaponDisplayData(Weapon w){
		TileObjectDisplayData data = getItemDisplayData(w);
		data.type = "Weapon";
		if (w == null){
			data.addDatum("Attack: ", "1", true);
		}
		else {
			data.addDatum("Attack: ", Integer.toString(w.getAttackValue()), true);
		}
		return data;
	}
	
	private static TileObjectDisplayData getCharacterDisplayData(Character c){
		TileObjectDisplayData data = new TileObjectDisplayData("Character");
		if (c == null){
			addBasicCharacterData(data, "New Character", "1", "0");
		}
		else {
			addBasicCharacterData(data, c.getName(), Integer.toString(c.getHealth()), Integer.toString(c.getAttack()));
		}
		return data;
	}
	public static TileObjectDisplayData getMonsterDisplayData(Monster m){
		TileObjectDisplayData data = getCharacterDisplayData(m);
		data.type = "Monster";
		return data;
	}
	
	public static TileObjectDisplayData getPlayerDisplayData(Player p){
		TileObjectDisplayData data = getCharacterDisplayData(p);
		data.type = "Player";
		if (p == null){
			data.addDatum("Stamina: ", "0", true);
		}
		else {
			data.addDatum("Stamina: ", Integer.toString(p.getStamina()), true);
		}
		return data;
	}
	
	public static TileObjectDisplayData getWallDisplayData(Direction dir){
		TileObjectDisplayData data = new TileObjectDisplayData(dir.toString().toUpperCase() + " Wall");
		return data;
	}
	
	public static TileObjectDisplayData getDoorDisplayData(Exit door, Direction dir){
		TileObjectDisplayData data = new TileObjectDisplayData(dir.toString().toUpperCase() + " Door");
		if (door == null){
			data.addDatum("Key Name: ", "MonKey", false);
		}
		else {
			data.addDatum("Key Name: ", door.getKeyName(), false);
		}
		return data;
	}
	
	//------------Factory Helpers------------//
	private void addDatum(String label, String value, boolean isInteger){
		displayData.add(new TileObjectDisplayDatum(label, value, isInteger));
	}
	
	private static void addBasicItemData(TileObjectDisplayData data, String name, String weight){
		data.addDatum("Name: ", name, false);
		data.addDatum("Weight: ", weight, true);
	}
	
	private static void addBasicCharacterData(TileObjectDisplayData data, String name, String health, String attack){
		data.addDatum("Name: ", name, false);
		data.addDatum("Health: ", health, true);
		data.addDatum("Attack: ", attack, true);
	}
	
	//------------Getters------------//
	public String getTypeName(){
		return type;
	}
	
	public int size(){
		return displayData.size();
	}
	
	/**
	 * Function to look up the value of the Datum given its name
	 * @param datumName - label to compare with the datum
	 * @return - value of the field
	 */
	public String getDatumValue(String datumName) {
		String fieldName = datumName +": ";
		String value = null;
		 for (int i=0; i<displayData.size(); i++ ) {
			 
			 TileObjectDisplayDatum dataLine = displayData.get(i);
			 
			 String label = dataLine.getName();
			 if(label.equals(fieldName)) 
			 {		 
				 value = dataLine.getValue();
			 }
		 }
		 return value;
	}
	
	/**
	 * A TileObjectDisplayData is valid if all of its datum are valid
	 * @return True if the display data is valid, false otherwise
	 */
	public boolean isValid(){
		for (TileObjectDisplayDatum datum : displayData){
			if (!datum.isValid()){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public Iterator<TileObjectDisplayDatum> iterator() {
		return displayData.iterator();
	}
	
	//------------Get objects --------//
	public Item getItem() {
		String itemName = null;
		int itemWeight = 0;
		Item newItem = null;
		
		 if (type.equals("Item")) {
			 
			 itemName =  getDatumValue("Name");
			 itemWeight = Integer.parseInt(getDatumValue("Weight"));
			
			 newItem = new Item(itemName, itemWeight);
		 }
		 return newItem;
	}
	
	/**
	 * Get a Weapon from the data
	 * @return Weapon 
	 */
	public Weapon getWeapon() {
		int attack = 0;
		int weight = 0;
		Weapon newWeapon = null;
		String weaponName;
		
		if(type.equals("Weapon")) {
			weaponName=getDatumValue("Name");
			attack = Integer.parseInt(getDatumValue("Attack"));
			weight = Integer.parseInt(getDatumValue("Weight"));
			newWeapon = new Weapon(weaponName, weight, attack);
		}
		return newWeapon;
	}
	
	/**
	 * Get the character that was created.
	 * @param myPosition - position of the character
	 * @return a character of of type Monster or Player
	 */
	public Character getCharacter(Tile myPosition) {
		String name = null;
		int health = 0;
		int attack = 0;
		int stamina = 0;
		Character newCharacter = null;
		
		if (type.equals("Monster")) { 
			 
			 name =  getDatumValue("Name");
			 health = Integer.parseInt(getDatumValue("Health"));
			 attack = Integer.parseInt(getDatumValue("Attack"));
			 newCharacter = new Monster(name, health, attack, myPosition);
		}else if (type.equals("Player")) {
			name =  getDatumValue("Name");
			health = Integer.parseInt(getDatumValue("Health"));
			attack = Integer.parseInt(getDatumValue("Attack"));
			stamina = Integer.parseInt(getDatumValue("Stamina"));
			newCharacter =  new Player(name, health, attack, stamina, myPosition);
		}
		
		return newCharacter;
	}
	
	/**
	 * Function getWall creates an edge in specified direction between the two tiles and sets edge as un-crossable
	 * @param t1 - first tile
	 * @param t2 - second tile
	 * @return edge that is not crossable ie a wall
	 */
	public Edge getWall(Tile t1, Tile t2) {
		Edge newEdge=null;
		Direction direction;
		
		if (type.endsWith("Wall")) {
			direction = getDirection();
			newEdge = new Edge(t1, t2, false, direction, direction.getOppositeDirection());
		}
		
		return newEdge;
	}
	/**
	 * Function to create an Exit that is a locked door.
	 * @param t1 - first tile
	 * @param t2 - second tile
	 * @return an edge (Exit type)
	 */
	public Edge getDoor(Tile t1, Tile t2) {
		Edge newEdge = null;
		Direction direction;
		Item key = null;
		String keyName;
		
		if (type.endsWith("Door")) {
			direction = getDirection();
			
			keyName = getDatumValue("Key Name");
			key = new Item(keyName, 1);
			
			newEdge = new Exit(t1, t2, true, direction, direction.getOppositeDirection(), key);
		}
	
		return newEdge;
	}
	
	/**
	 * Get the direction base on string ie NORTH Wall, EAST Wall
	 * @return direction of the wall
	 */
	
	private Direction getDirection() {
		String sDir[]= type.split(" ");
		Direction direction = Direction.getDirection(sDir[0]);
		return direction;
	}

	//------------Single Datum------------//
	class TileObjectDisplayDatum {
		private String label;
		private String value;
		private boolean isInteger;
		
		public TileObjectDisplayDatum(String label, String value, boolean isInteger) {
			this.label = label;
			this.value = value;
			this.isInteger = isInteger;
		}

		/**
		 * A datum is invalid if it is the empty string. In addition if it is an integer,
		 * if it cannot be parsed as such, it is invalid. 
		 * 
		 * @return True if the value is valid, false otherwise.
		 */
		public boolean isValid(){
			if (value.isEmpty()) return false;
			if (!isInteger) return true;

			for (int i = 0; i < value.length(); i++){
				if (!java.lang.Character.isDigit(value.charAt(i))){
					return false;
				}
			}
			return true;
		}

		public String getName() {
			return label;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
}
