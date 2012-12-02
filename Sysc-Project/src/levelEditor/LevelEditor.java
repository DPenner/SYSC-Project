package levelEditor;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gameCore.*;
import gameLoader.Level;
import gameLoader.Serialize;

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
		//create a level containing all the needed data and them serialize it 
		//and the player
		Level l = new Level();
		//get the grid width and height 
		int maxHeight = 0;
		int maxWidth = 0;
		for(Tile t: tiles)
		{
			if(t.getLocation().x>maxWidth) maxWidth = t.getLocation().x;
			if(t.getLocation().y>maxHeight) maxHeight = t.getLocation().y;
		}
		l.setLevelSize(maxWidth, maxHeight);
		
		l.setPlayer((Player)playerTile.getCharacter());
		l.setElevator(playerTile.getRoom(), playerTile);
		
		for(Tile t: tiles)
		{
			l.setTile(t.getLocation().x, t.getLocation().y, t);
		}
		
		Serialize s = new Serialize((Player) playerTile.getCharacter(), l, null);
		boolean writeSuccess;
		String fileName = selectFile(MODE_SAVE);
		if(fileName !=null) {
			writeSuccess = s.write_serialize(fileName);
			
			if (writeSuccess) {
				String message = "Game state successfully saved. Exiting game.";
				JOptionPane.showMessageDialog(null, message, "Game State", JOptionPane.INFORMATION_MESSAGE);
				closeFrameAndExit();
			}else {
				String message = "Game state NOT successfully saved.";
				JOptionPane.showMessageDialog(null, message, "Game State", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	private String selectFile(int mode) {
		
		String returnVal = null;
		int ret = 0;
		
		final JFileChooser fc = new JFileChooser();
		
		//show open or save dialog
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		if (ret == JFileChooser.CANCEL_OPTION) 
		{
			returnVal = null;
		}
		else if(ret == JFileChooser.APPROVE_OPTION) 
		{
			File file=fc.getSelectedFile();
			
			returnVal=file.getPath();
		}
		else
		{
			ret = fc.showSaveDialog(new JFrame());
		}
		
		return returnVal;
	}
}
