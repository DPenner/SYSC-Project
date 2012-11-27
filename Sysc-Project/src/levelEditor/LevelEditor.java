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
	
	public void addTile(Tile t){
		t.setVisited(); //everything is visible in the level editor
		tiles.add(t);
	}
	
	public Tile getTile(Point location){
		for (Tile t : tiles){
			if (t.getLocation().equals(location)){
				return t;
			}
		}
		throw new IllegalArgumentException("No tile found at that location");
	}
	
	public void removeTile(Tile tile){
		tiles.remove(tile);
	}

	public void addPlayer(String name, int health, int attack, int stamina, Tile playerTile){
		if (playerTile == null){
			throw new IllegalArgumentException("playerTile cannot be null");
		}
		removePlayer(); //Only one player allowed
		playerTile.removeCharacter(); //removing any possible monsters
		new Player(name, health, attack, stamina, playerTile);
		this.playerTile = playerTile;
	}
	
	public void removePlayer(){
		if (playerTile != null){
			playerTile.removeCharacter(); 
		}
		playerTile = null;
	}
	
	public void addMonster(String name, int health, int attack, Tile monsterTile){
		if (monsterTile == null){
			throw new IllegalArgumentException("monsterTile cannot be null");
		}
		if (monsterTile == playerTile){
			removePlayer();
		}
		new Monster(name, health, attack, monsterTile);
	}
	
	public void removeMonster(Tile monsterTile){
		if (monsterTile == playerTile){
			throw new IllegalArgumentException("The player is not a monster");
		}
		monsterTile.removeCharacter();
	}
}
