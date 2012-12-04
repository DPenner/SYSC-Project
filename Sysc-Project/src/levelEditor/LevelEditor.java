package levelEditor;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gameCore.*;
import gameCore.Character;
import gameLoader.Level;
import gameLoader.Serialize;
import graphics2D.MapController;

/**
 * LevelEditorView displays the main Level Editor window
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

public class LevelEditor extends MapController implements ActionListener {
	
	private LevelEditorView levelEditorFrame;
	private List<Tile> tiles;
	private Tile playerTile;
	private Room globalRoom; //No time to implement room support, everything goes into a single room
	
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
				Tile clickTile = view.getTile(offsettedLocation);
				if (clickTile.isEmpty()){
					removeTile(clickTile);
				}
				else { //double check removal if there are things on the tile
					if (JOptionPane.showConfirmDialog(levelEditorFrame, "This tile is not empty, remove anyways?") == JOptionPane.YES_OPTION){
						removeTile(clickTile);
					}
				}
			}
			else {
				addTile(tileLocation);
			}
		}
		else if (tileExists){
			Tile clickTile = view.getTile(offsettedLocation);
			new TileNavigator(levelEditorFrame, this, clickTile);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(LevelEditorView.HELP)){
			new HelpFrame();
		} else if (e.getActionCommand().equals(LevelEditorView.SAVE)) {
			this.save(); //save the level
		}
	}
	
	protected void addTile(Point tileLocation){
		Tile newTile = new Tile(tileLocation, globalRoom);
		newTile.setVisited(); //everything is visible in the level editor
		connectTile(newTile);
		view.addTile(newTile);
		tiles.add(newTile);
	}
	
	protected void removeTile(Tile t){
		Set<Direction> directions = new HashSet<Direction>(t.getAllDirections());
		for (Direction d : directions){
			t.disconnectEdge(d); //disconnect the tile
		}
		tiles.remove(t);
		view.removeTile(t);
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
	}
	protected void removeEdge(Edge e){
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
		
		globalRoom.setVisited(); //make all tiles visible, better than all tiles dark
		Serialize s = new Serialize((Player) playerTile.getCharacter(), l);
		s.saveToFile();
	}
	
	/**
	 * This method completely guts the Tile (removes all Items, Characters, Edges, etc.).
	 * This method is generally used in conjunction with others to refill the Tile with user
	 * requested objects. Use with caution.
	 */
	protected void emptyTile(Tile t){
		t.removeAllItems();
		Character c = t.removeCharacter();
		if (c instanceof Player){
			playerTile = null;
		}
		
		// This step to avoid concurrentmodificationException from t.getAllDirections()
		Set<Direction> directions = new HashSet<Direction>(t.getAllDirections()); 
		for (Direction d: directions){
			t.disconnectEdgeFully(d);
		}
	}
	
	/**
	 * Connects a Tile with Edges being unconnected to other Tiles
	 * @param t
	 */
	private void connectTile(Tile t){
		for (Direction d : Direction.values()){
			connectTile(t, d);
		}
	}
	
	private void connectTile(Tile t, Direction d){
		Tile otherTile = getTile(t, d);
		if (otherTile != null){
			t.connect(d, d.getOppositeDirection(), otherTile);
		}
	}
	
	private void reverseConnectTile(Tile t){
		for (Direction d : Direction.values()){
			reverseConnectTile(t, d);
		}
	}
	private void reverseConnectTile(Tile t, Direction d){
		Tile otherTile = getTile(t, d);
		if (otherTile != null){
			otherTile.connect(d.getOppositeDirection(), d, t);
		}
	}
	
	/**
	 * Gets the Tile one "step" in Direction of given step from given Tile
	 * @param t Tile to step off of
	 * @param step The direction to "step"
	 */
	protected Tile getTile(Tile t, Direction step){
		Point location = t.getLocation();
		switch (step){
		case EAST:
			return getTile(new Point(location.x + 1, location.y));
		case NORTH:
			return getTile(new Point(location.x, location.y - 1));
		case SOUTH:
			return getTile(new Point(location.x, location.y + 1));
		case WEST:
			return getTile(new Point(location.x - 1, location.y));
		default:
			return null;
		}
	}
	
	private Tile getTile(Point location){
		Tile retval = null;
		for (Tile t : tiles){
			if (t.getLocation().equals(location)){
				retval = t;
			}
		}
		return retval;
	}
	
	protected void setPlayerTile(Tile t){
		playerTile = t;
	}
}
