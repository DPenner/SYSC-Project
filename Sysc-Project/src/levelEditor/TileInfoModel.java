package levelEditor;

import gameCore.Character;
import gameCore.Direction;
import gameCore.Edge;
import gameCore.Exit;
import gameCore.Item;
import gameCore.Monster;
import gameCore.Player;
import gameCore.Room;
import gameCore.Tile;
import gameCore.Weapon;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Provides a model for providing information and editing information about a given Tile.
 * Note that the information in this class and the actual contents of the Tile are not guaranteed
 * to be at sync except for at construction, and after the method calls reloadTile() or saveTile()
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

public class TileInfoModel {
	private TileObjectDisplayData characterData;
	private Map<Direction, TileObjectDisplayData> edgeData;
	private List<TileObjectDisplayData> itemData;
	private boolean hasPlayer;
	private Tile activeTile;
	private LevelEditor editor;
	private List<TileInfoListener> listeners;
	
	protected TileInfoModel(LevelEditor editor, Tile activeTile){
		this.activeTile = activeTile;
		this.editor = editor;
		edgeData = new HashMap<Direction, TileObjectDisplayData>();
		itemData = new ArrayList<TileObjectDisplayData>();
		clearCharacterData(); //initialize hasPlayer and characterData
		
		listeners = new ArrayList<TileInfoListener>();
		
		loadTile(activeTile);
	}
	
	//------------Adding Data------------//	
	private void addItem(Item i){
		TileObjectDisplayData newItemData = TileObjectDisplayData.getItemDisplayData(i);
		itemData.add(newItemData);
		notifyDataAdded(newItemData);
	}
	protected void addItem(){
		addItem(null);
	}
	
	private void addWeapon(Weapon w){
		TileObjectDisplayData newWeaponData = TileObjectDisplayData.getWeaponDisplayData(w);
		itemData.add(newWeaponData);
		notifyDataAdded(newWeaponData);
	}
	protected void addWeapon(){
		addWeapon(null);
	}
	
	private void addMonster(Monster m){
		if (characterData != null){
			throw new UnsupportedOperationException("Cannot add two charcters to a tile");
		}
		characterData = TileObjectDisplayData.getMonsterDisplayData(m);
		notifyDataAdded(characterData);
	}
	protected void addMonster(){
		addMonster(null);
	}
	
	private void addPlayer(Player p){
		if (characterData != null){
			throw new UnsupportedOperationException("Cannot add two charcters to a tile");
		}
		characterData = TileObjectDisplayData.getPlayerDisplayData(p);
		hasPlayer = true;
		notifyDataAdded(characterData);
	}
	protected void addPlayer(){
		addPlayer(null);
	}
	
	protected void addWall(Direction d){
		TileObjectDisplayData wallData = TileObjectDisplayData.getWallDisplayData(d);
		edgeData.put(d, wallData);
		notifyDataAdded(wallData);
	}
	
	private void addDoor(Exit door, Direction d){
		TileObjectDisplayData doorData = TileObjectDisplayData.getDoorDisplayData(door, d);
		edgeData.put(d, doorData);
		notifyDataAdded(doorData);
	}
	protected void addDoor(Direction d){
		addDoor(null, d);
	}
	//------------Loading and Saving------------//
	/**
	 * Resets the information to the last saved state (ie the current contents of the Tile)
	 */
	protected void reloadTile(){
		clearData();	//Removes all data
		loadTile(activeTile);   //restores data from previous tile state
	}
	
	private void loadTile(Tile t){
		for (Item i : t.getInventory()){
			if (i instanceof Weapon){
				addWeapon((Weapon) i);
			}
			else {
				addItem(i);
			}
		}
		Character c = t.getCharacter();
		if (c instanceof Player){
			addPlayer((Player) c);
		}
		else if (c instanceof Monster){
			addMonster((Monster) c);
		}
		
		for (Direction d : t.getAllDirections()){
			if (t.hasExit(d)){
				Tile defaultTile1 = new Tile(new Point(0, 0), new Room());
				Tile defaultTile2 = new Tile(new Point(0, 0), new Room());
				addDoor(new Exit(defaultTile1, defaultTile2, true, new Item(t.getExitKey(d), 1)), d);
			}
			else if (t.isCrossableByDefault(d)){
				addWall(d);
			}
		}
		
		notifyDataSaved(); //Though not explicitly "saved", after loading a tile, the info exactly represents a tile
	}
	
	/**
	 * Saves the information as the new contents of the Tile
	 */
	protected void saveTile(){
		editor.emptyTile(activeTile);   //dumps everything on the tile for replacement
		for (TileObjectDisplayData todd : itemData){
			activeTile.addItem(todd.getItem());
		}
		if (characterData != null){
			Character c = characterData.getCharacter(activeTile);
			if (c instanceof Player){
				editor.setPlayerTile(activeTile);
			}
		}
		
		//sets the edges present
		for (Direction d : edgeData.keySet()){
			TileObjectDisplayData data = edgeData.get(d);
			Edge edgeToAdd = data.getEdge(activeTile);
			activeTile.setEdge(d, edgeToAdd);
		}
		
		//Invisible edges still need to be set in the other directions for connection
		for (Direction d : Direction.values()){
			if (!activeTile.hasDirection(d)){
				Edge edgeToAdd;
				if (editor.getTile(activeTile, d) == null){
					edgeToAdd = new Edge(activeTile, null, false);
				}
				else{
					edgeToAdd = new Edge(activeTile, null, true);
				}
				activeTile.setEdge(d, edgeToAdd);
			}
		}
		
		editor.connectTile(activeTile); //connect to neighbours
		
		notifyDataSaved();
	}
	
	//------------Removing Data------------//
	protected void clearData(){
		clearCharacterData();
		itemData.clear();
		edgeData.clear();
		
		notifyDataCleared();
	}
	
	private void clearCharacterData(){
		characterData = null;
		hasPlayer = false;
	}
	protected void remove(TileObjectDisplayData displayData){
		//check if any of the three categories has the displayData, and remove it.
		
		if (displayData == characterData){
			clearCharacterData();
			notifyDataRemoved(displayData);
			return;
		}
		
		if (itemData.contains(displayData)){
			itemData.remove(displayData);
			notifyDataRemoved(displayData);
			return;
		}
		
		Direction dataDirection = null;
		for (Direction d : activeTile.getAllDirections()){
			if (edgeData.get(d) == displayData){
				dataDirection = d;
				break;
			}
		}
		if (dataDirection != null){  //indicates it was found
			edgeData.remove(dataDirection);
			notifyDataRemoved(displayData);
			return;
		}	
	}
	
	//------------Checks------------//
	protected boolean hasCharacter(){
		return characterData != null;
	}
	protected boolean hasPlayer(){
		return hasPlayer;
	}
	protected boolean hasEdge(Direction d){
		return edgeData.containsKey(d);
	}
	protected boolean isEmpty(){
		return !hasCharacter() && itemData.isEmpty() && edgeData.isEmpty();
	}
	
	/**
	 * Checks if this TileInfoModel contains the given TileObjectDisplayData.
	 * @param displayData The data to check.
	 * @return True if this model contains the data, false otherwise
	 */
	protected boolean contains(TileObjectDisplayData displayData){
		return characterData == displayData ||
			   itemData.contains(displayData) ||
			   edgeData.values().contains(displayData);
	}
	
	//------------Listener Handling------------//
	protected void addListener(TileInfoListener l){
		listeners.add(l);
	}
	protected void removeListener(TileInfoListener l){
		listeners.remove(l);
	}
	
	private void notifyDataAdded(TileObjectDisplayData displayData){
		for (TileInfoListener l : listeners){
			l.dataAdded(new TileInfoEvent(this, displayData));
		}
	}
	
	private void notifyDataRemoved(TileObjectDisplayData displayData){
		for (TileInfoListener l : listeners){
			l.dataRemoved(new TileInfoEvent(this, displayData));
		}
	}
	
	private void notifyDataCleared(){
		for (TileInfoListener l : listeners){
			l.dataCleared(new TileInfoEvent(this, null));
		}
	}
	
	private void notifyDataSaved(){
		for (TileInfoListener l : listeners){
			l.dataSynched(new TileInfoEvent(this, null));
		}
	}
}
