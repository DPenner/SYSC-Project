package graphics3D;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;

import gameCore.Direction;
import gameCore.Edge;
import gameCore.Player;
import gameCore.Tile;
import gameLoader.Level;
import graphics2D.PlayerEvent;
import graphics2D.PlayerListener;

import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;

/**
 * FirstPersonView provides a 3D view of the current tile.
 * 
 * @author Group D
 * @author Main Author: Trang Pham
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
@SuppressWarnings("serial")
public class FirstPersonView extends JScrollPane implements PlayerListener, Serializable  {
	private static final long serialVersionUID = 1L;
	protected static final int OUTER_BOX = 300;
	protected static final int INNER_BOX = 180;
	protected static final int BACKDIR_HEIGHT = 20;
	
	private static final Integer BACKGROUND_LAYER_DEPTH = 0;
	private static final Integer FOREGROUND_LAYER_DEPTH = 100;
	
	private static final Direction DEFAULT_BACKDIR = Direction.SOUTH;
	
	private JLayeredPane map;
	private BackgroundPanel backgroundLayer;
	private ForegroundPanel foregroundLayer;
	
	/**
	 * Takes a tile, and initializes the view to display all walls, exits, and items in the tile
	 * @param t The tile to initialize. If null, a blank view is created.
	 */
	public FirstPersonView(Player player){
		
		player.addPlayerListener(this);
		//set up the map pane
		map = new JLayeredPane();
		this.getViewport().add(map);
		
		//add panels
		backgroundLayer = new BackgroundPanel();
		foregroundLayer = new ForegroundPanel();
		map.add(backgroundLayer, BACKGROUND_LAYER_DEPTH);
        map.add(foregroundLayer, FOREGROUND_LAYER_DEPTH);
        
        //configure panels
        backgroundLayer.setOpaque(true);
        foregroundLayer.setOpaque(false);
        
        Tile t = player.getPosition();
        //draw the tile the player is currently on
        backgroundLayer.draw(t, DEFAULT_BACKDIR);
		foregroundLayer.draw(t, DEFAULT_BACKDIR);
        
        this.setMinimumSize(new Dimension(OUTER_BOX, OUTER_BOX + BACKDIR_HEIGHT));
	}

	@Override
	public void positionChanged(PlayerEvent e, Direction backDir) {
		Tile newPosition = e.getPosition();
		backgroundLayer.draw(newPosition, DEFAULT_BACKDIR);
		foregroundLayer.draw(newPosition, DEFAULT_BACKDIR);
	}
	
	public boolean isItemContains(Point p)
	{
		return foregroundLayer.isItemContains(p);
	}
	
	public Direction directionContaining(Point p)
	{
		return backgroundLayer.directionContaining(p);
	}
	
	public boolean isFloorContains(Point p)
	{
		return backgroundLayer.isFloorContains(p);
	}
	/*
	 * Unimplemented methods of player listener
	 */
	@Override
	public void itemAdded(PlayerEvent e) {
		Tile newPosition = e.getPosition();
		if(newPosition == null) System.out.println("TILE NULL");
		backgroundLayer.draw(newPosition, DEFAULT_BACKDIR);
		foregroundLayer.draw(newPosition, DEFAULT_BACKDIR);
	}

	@Override
	public void itemDropped(PlayerEvent e) {
		Tile newPosition = e.getPosition();
		backgroundLayer.draw(newPosition, DEFAULT_BACKDIR);
		foregroundLayer.draw(newPosition, DEFAULT_BACKDIR);
	}

	@Override
	public void statsChanged(PlayerEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerRestored(PlayerEvent e) {
		// TODO Auto-generated method stub
		Tile newPosition = e.getPlayer().getPosition();
		backgroundLayer.draw(newPosition, DEFAULT_BACKDIR);
		foregroundLayer.draw(newPosition, DEFAULT_BACKDIR);
	}
}
