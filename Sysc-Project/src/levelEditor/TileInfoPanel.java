package levelEditor;

import gameCore.Character;
import gameCore.Direction;
import gameCore.Tile;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import gameCore.*;

/**
 * TileInfoPanel displays all things editable about a given Tile
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

class TileInfoPanel extends JPanel implements Scrollable
{
	private List<TileObjectPanel> itemInfos;
	private TileObjectPanel selectedInfo;
	
	private Tile infoTile;
	
	private TileObjectDisplayData characterData;
	private Map<Direction, TileObjectDisplayData> edgeData;
	private List<TileObjectDisplayData> itemData;
	
	private Tile defaultTile1;
	private Tile defaultTile2;
	
	boolean isDirty;
	
	protected TileInfoPanel(Tile infoTile){
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		itemInfos = new ArrayList<TileObjectPanel>();
		edgeData = new HashMap<Direction, TileObjectDisplayData>();
		itemData = new ArrayList<TileObjectDisplayData>();
		characterData = null;
		isDirty = false;
		
		defaultTile1 = new Tile(new Point(0, 0), new Room());
		defaultTile2 = new Tile(new Point(0, 0), new Room());
	}
	
	private void add(TileObjectDisplayData data){
		TileObjectPanel newInfo = new TileObjectPanel(this, data);
		this.add(newInfo);
		itemInfos.add(newInfo);
		this.setSelectedInfo(newInfo);
		this.setDirty(true);
		this.revalidate();
	}
	
	private void addItem(Item i){
		TileObjectDisplayData newItemData = TileObjectDisplayData.getItemDisplayData(i);
		itemData.add(newItemData);
		add(newItemData);
	}
	protected void addItem(){
		addItem(null);
	}
	
	private void addWeapon(Weapon w){
		TileObjectDisplayData newWeaponData = TileObjectDisplayData.getWeaponDisplayData(w);
		itemData.add(newWeaponData);
		add(newWeaponData);
	}
	protected void addWeapon(){
		addWeapon(null);
	}
	
	private void addMonster(Monster m){
		characterData = TileObjectDisplayData.getMonsterDisplayData(m);
		add(characterData);
	}
	protected void addMonster(){
		addMonster(null);
	}
	
	private void addPlayer(Player p){
		characterData = TileObjectDisplayData.getPlayerDisplayData(p);
		add(characterData);
	}
	protected void addPlayer(){
		addPlayer(null);
	}
	
	protected void addWall(Direction d){
		TileObjectDisplayData wallData = TileObjectDisplayData.getWallDisplayData(d);
		edgeData.put(d, wallData);
		add(wallData);
	}
	
	private void addDoor(Exit door, Direction d){
		TileObjectDisplayData doorData = TileObjectDisplayData.getDoorDisplayData(door, d);
		edgeData.put(d, doorData);
		add(doorData);
	}
	protected void addDoor(Direction d){
		addDoor(null, d);
	}
	
	protected void setSelectedInfo(TileObjectPanel info){
		unSelect(); //only one can be selected at a time
		selectedInfo = info;
		selectedInfo.highlight();
	}
	
	private TileObjectPanel unSelect(){
		if (selectedInfo != null){
			selectedInfo.unHighlight();
		}
		TileObjectPanel retval = selectedInfo;
		selectedInfo = null;
		return retval;
	}
	
	protected void removeSelected(){
		if (hasSelection()){
			TileObjectPanel removed = unSelect();
			itemInfos.remove(removed);
			this.remove(removed);
			this.setDirty(true);
			
			TileObjectDisplayData removedData = removed.getDisplayData();
			if (characterData == removedData){
				characterData = null;
			}
			
			Direction removeDirection = null;
			for (Direction d : edgeData.keySet()){
				if (edgeData.get(d) == removedData){
					removeDirection = d;
				}
			}
			if (removeDirection != null){
				edgeData.remove(removeDirection);
			}		
			
			this.revalidate();
		}
	}
	
	protected void loadTile(Tile t){
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
				addDoor(new Exit(defaultTile1, defaultTile2, true, new Item(t.getExitKey(d), 1)), d);
			}
			else if (t.isCrossableByDefault(d)){
				addWall(d);
			}
		}
	}
	
	void reloadTile(){
		clear();
		loadTile(infoTile);
	}
	
	protected void clear(){
		unSelect();
		for (TileObjectPanel info : itemInfos){
			this.remove(info);
		}
		itemInfos.clear();
		characterData = null;
		edgeData.clear();
		this.setDirty(true);
		this.revalidate();
	}
	
	protected boolean isDirty(){
		return isDirty;
	}
	protected void setDirty(boolean b){
		isDirty = b;
	}
	protected boolean isEmpty(){
		return itemInfos.isEmpty();
	}
	protected boolean hasSelection(){
		return selectedInfo != null;
	}
	
	protected boolean hasCharacter(){
		return characterData != null;
	}
	protected boolean hasEdge(Direction d){
		return edgeData.containsKey(d);
	}
	
	//------------Scrolling support-----------//
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle arg0, int arg1, int arg2) {
		return 0;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return true;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2) {
		return 1;
	}

	
}