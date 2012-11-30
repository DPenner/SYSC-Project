package levelEditor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import gameCore.*;
import gameCore.Character;

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
			addBasicCharacterData(data, c.toString(), Integer.toString(c.getHealth()), Integer.toString(c.getAttack()));
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
	
	@Override
	public Iterator<TileObjectDisplayDatum> iterator() {
		return displayData.iterator();
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
	}
}
