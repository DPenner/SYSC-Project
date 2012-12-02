package levelEditor;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gameCore.*;
import gameLoader.Level;
import gameLoader.Serialize;
import graphics2D.MapController;

public class LevelEditor extends MapController implements ActionListener {
	
	LevelEditorView levelEditorFrame;
	List<Tile> tiles;
	List<Edge> edges;
	Tile playerTile;
	Room globalRoom; //No time to implement room support, everything goes into a single room
	
	protected LevelEditor(LevelEditorView levelEditorFrame){
		super(levelEditorFrame.getEditorView());
		this.levelEditorFrame = levelEditorFrame;
		this.view = levelEditorFrame.getEditorView();
		tiles = new ArrayList<Tile>();
		globalRoom = new Room();
	}
	

	//------------Mouse Events------------//
	@Override
	public void mouseClicked(MouseEvent e){
		Point offsettedLocation = e.getPoint();
		boolean tileExists = view.hasTile(offsettedLocation);
		if (e.getButton() == MouseEvent.BUTTON1){
			Point tileLocation = view.getTileLocation(offsettedLocation);
			
			if (tileExists){
				removeTile(view.getTile(offsettedLocation));
			}
			else { //add tile
				addTile(tileLocation);
			}
		}
		else if (tileExists){
			navigatorClick(offsettedLocation);
		}
	}
	
	private void navigatorClick(Point offsettedLocation){
		Tile itemTile = view.getTile(offsettedLocation);	
		new TileNavigator(levelEditorFrame, this, itemTile);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(LevelEditorView.HELP)){
			new HelpFrame();
		}
	}
	
	protected void addTile(Point tileLocation){
		Tile newTile = new Tile(tileLocation, globalRoom);
		newTile.setVisited(); //everything is visible in the level editor
		view.addTile(newTile);
		tiles.add(newTile);
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
		view.removeTile(t);
		for (Direction d : t.getAllDirections()){
			t.removeEdge(d);
		}
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
	
	protected void addEdge(Edge e){
		view.addEdge(e);
		//edges.add(e);
	}
	protected void removeEdge(Edge e){
		//edges.remove(e);
		view.removeEdge(e);
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
		
		Serialize s = new Serialize((Player) playerTile.getCharacter(), l);
		s.saveToFile();
	}
}
