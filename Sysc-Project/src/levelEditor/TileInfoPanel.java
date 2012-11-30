package levelEditor;

import gameCore.Direction;

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
	List<TileObjectPanel> itemInfos;
	TileObjectPanel selectedInfo;
	
	
	TileObjectDisplayData characterData;
	Map<Direction, TileObjectDisplayData> edgeData;
	List<TileObjectDisplayData> itemData;
	
	boolean isDirty;
	
	protected TileInfoPanel(){
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		itemInfos = new ArrayList<TileObjectPanel>();
		edgeData = new HashMap<Direction, TileObjectDisplayData>();
		characterData = null;
		isDirty = false;
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
		add(TileObjectDisplayData.getItemDisplayData(i));
	}
	protected void addItem(){
		addItem(null);
	}
	
	private void addWeapon(Weapon w){
		add(TileObjectDisplayData.getWeaponDisplayData(w));
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