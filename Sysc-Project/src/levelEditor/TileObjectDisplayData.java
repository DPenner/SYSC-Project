package levelEditor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import gameCore.Direction;

class TileObjectDisplayData implements Iterable<TileObjectDisplayData.TileObjectDisplayDatum> {
	
	String type;
	List<TileObjectDisplayDatum> displayData;
	boolean isCharacterData;
	
	private TileObjectDisplayData(String type){
		this.type = type;
		displayData = new ArrayList<TileObjectDisplayDatum>();
		isCharacterData = false;
	}
	
	//------------Factory Methods------------//
	public static TileObjectDisplayData getItemDisplayData(){
		TileObjectDisplayData data = new TileObjectDisplayData("Item");
		addBasicItemData(data);
		return data;
	}
	
	public static TileObjectDisplayData getWeaponDisplayData(){
		TileObjectDisplayData data = new TileObjectDisplayData("Weapon");
		addBasicItemData(data);
		data.addDatum("Attack: ", "1", true);
		return data;
	}
	
	public static TileObjectDisplayData getMonsterDisplayData(){
		TileObjectDisplayData data = new TileObjectDisplayData("Monster");
		addBasicCharacterData(data);
		return data;
	}
	
	public static TileObjectDisplayData getPlayerDisplayData(){
		TileObjectDisplayData data = new TileObjectDisplayData("Player");
		addBasicCharacterData(data);
		data.addDatum("Stamina: ", "0", true);
		return data;
	}
	
	public static TileObjectDisplayData getWallDisplayData(Direction d){
		TileObjectDisplayData data = new TileObjectDisplayData(d.toString().toUpperCase() + " Wall: ");
		return data;
	}
	
	public static TileObjectDisplayData getDoorDisplayData(Direction d){
		TileObjectDisplayData data = new TileObjectDisplayData(d.toString().toUpperCase() + " Door: ");
		data.addDatum("Key Name: ", "MonKey", false);
		return data;
	}
	
	//------------Factory Helpers------------//
	private void addDatum(String label, String defaultValue, boolean isInteger){
		displayData.add(new TileObjectDisplayDatum(label, defaultValue, isInteger));
	}
	
	private static void addBasicItemData(TileObjectDisplayData data){
		data.addDatum("Name: ", "New Item", false);
		data.addDatum("Weight: ", "0", true);
	}
	
	private static void addBasicCharacterData(TileObjectDisplayData data){
		data.addDatum("Name: ", "New Character", false);
		data.addDatum("Health: ", "1", true);
		data.addDatum("Attack: ", "0", true);
		data.isCharacterData = true;
	}
	
	//------------Getters------------//
	public String getTypeName(){
		return type;
	}
	
	public int size(){
		return displayData.size();
	}
	
	public boolean isCharacterData(){
		return isCharacterData;
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
		
		public TileObjectDisplayDatum(String label, String defaultValue, boolean isInteger) {
			this.label = label;
			this.value = defaultValue;
			this.isInteger = isInteger;
		}

		public boolean isValid(){
			if (value.isEmpty()) return false;
			if (!isInteger) return true;
			
			//using try-catch though not ideal, is more maintainable than 
			//the alternative of checking if every character is a digit
			try { 
				Integer.parseInt(value);
			}
			catch (NumberFormatException e){
				return false;
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
