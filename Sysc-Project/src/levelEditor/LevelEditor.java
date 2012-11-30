package levelEditor;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import gameCore.*;

public class LevelEditor {
	List<Tile> tiles;
	Tile playerTile;
	
	public LevelEditor(){
		tiles = new ArrayList<Tile>();
	}
	
	protected void addTile(Tile t){
		t.setVisited(); //everything is visible in the level editor
		tiles.add(t);
	}
	
	protected Tile getTile(Point location){
		for (Tile t : tiles){
			if (t.getLocation().equals(location)){
				return t;
			}
		}
		throw new IllegalArgumentException("No tile found at that location");
	}
	
	protected void removeTile(Tile t){
		tiles.remove(t);
	}

	protected void clearTile(Tile t){
		for (Item i : t.getInventory()){
			t.removeItem(i);
		}
		
		if (t.removeCharacter() instanceof Player){
			playerTile = null;
		}
	}
	
	protected boolean hasPlayer(){
		return playerTile != null;
	}
	
	protected void save(){
		//some form of exporting goes here
	}
}
