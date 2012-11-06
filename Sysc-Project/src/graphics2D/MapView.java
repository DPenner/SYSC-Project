package graphics2D;

import gameCore.*;
import gameLoader.Level;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.*;

/**
 * MapView provides a view of the map, by displaying its Tiles and Edges in a 2D fashion
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

public class MapView extends JScrollPane {
	private static final int TILE_SIZE = 40;
	private static final int EDGE_WIDTH = 4;
	
	private static final int MINIMUM_WIDTH = 5 * TILE_SIZE;
	private static final int MINIMUM_HEIGHT = MINIMUM_WIDTH;
	private static final int DEFAULT_WIDTH = 1000;
	private static final int DEFAULT_HEIGHT = DEFAULT_WIDTH;
	
	private static final Integer TILE_LAYER_DEPTH = 0;
	private static final Integer EDGE_LAYER_DEPTH = 100;
	
	private JLayeredPane map;
	private TilePanel tileLayer;
	private EdgePanel edgeLayer;
	
	//Helps set up the panel's bounds
	private int levelWidth;
	private int levelHeight;
	
	//offset is the value of the Tile at the top left corner
	private int xOffset;
	private int yOffset;
	
	/**
	 * Default constructor - a MapView with nothing on it
	 */
	public MapView(){
		this(null);
	}
	
	/**
	 * Takes a level, and initializes the view to display all edges and tiles in the level.
	 * @param l The level to initialize. If null, a blank view is created.
	 */
	public MapView(Level l){
        // TODO - Split off the different parts of this constructor into private/protected worker methods
		//store level information
		if (l != null){
			levelWidth = getOffsettedX(l.getGridWidth());
			levelHeight = getOffsettedX(l.getGridHeight());
		}
		else {
			levelWidth = DEFAULT_WIDTH;
			levelHeight = DEFAULT_HEIGHT;
		}
			
		//set up the map pane
		map = new JLayeredPane();
		this.getViewport().add(map);
		
		//add panels
		tileLayer = new TilePanel(this);
		edgeLayer = new EdgePanel(this);
		map.add(tileLayer, TILE_LAYER_DEPTH);
        map.add(edgeLayer, EDGE_LAYER_DEPTH);
        
        //configure panels
        tileLayer.setOpaque(true);
        edgeLayer.setOpaque(false);
        
        //Add the tiles and edges to the map
		if (l != null){
			for (int i = 0; i < l.getTiles().length; i++){
				for (int j = 0; j < l.getTiles()[i].length; j++){
					addTile(l.getTiles()[i][j]);
				}
			}
			for (Edge edge : l.getEdges()){
				addEdge(edge);
			}
		}
		
		//sets up this component
		this.setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
		xOffset = 0; //TEMP, currently offsets not properly calculated
		yOffset = 0;
		
		setPanelBounds();
	}
	
	//------------Set-up-----------//
	/**
	 * Adds a tile to the MapView
	 * @param t The Tile to be added. 
	 */
	public void addTile(Tile t){
		tileLayer.addLayoutObject(t);
	}
	
	/**
	 * Adds an Edge to the MapView
	 * @param edge The edge to be added.
	 */
	public void addEdge(Edge edge){
		edgeLayer.addLayoutObject(edge);
	}
	
	//------------Scaling-----------//
	//These methods scale back and forth between a Tile's location and its location in this MapView
	protected int getOffsettedX(int tileX){
		return (tileX + xOffset) * TILE_SIZE;
	}
	protected int getOffsettedX(Point tileLocation){
		return getOffsettedX(tileLocation.x);
	}
	protected int getOffsettedY(int tileY){
		return (tileY + yOffset) * TILE_SIZE;
	}
	protected int getOffsettedY(Point tileLocation){
		return tileLocation.y * TILE_SIZE;
	}
	
	private Point getTileLocation(Point offsettedLocation){
		return new Point(offsettedLocation.x/TILE_SIZE - xOffset, offsettedLocation.y/TILE_SIZE - yOffset);
	}
	protected Tile getTile(Point offsettedLocation){
		return tileLayer.getTile(getTileLocation(offsettedLocation));
	}
	
	protected int getTileSize(){
		return TILE_SIZE;
	}
	protected int getEdgeWidth(){
		return EDGE_WIDTH;
	}
	
	//------------Miscellaneous------------//
	//Controls Tile highlighting
	protected void highLight(Tile t){
		tileLayer.highLight(t);
	}
	protected void unHighLight(Tile t){
		tileLayer.unHighLight(t);
	}
	
	//-----------Component Set up------------//
	/**
	 * Sets the panel bounds to whichever is larger: the level, or the size of this component
	 */
	public void setPanelBounds(){
		int width = Math.max(levelWidth, this.getSize().width);
		int height = Math.max(levelHeight, this.getSize().height);
		this.setBounds(0, 0, width, height);
		map.setBounds(this.getBounds());
		tileLayer.setBounds(this.getBounds());
		edgeLayer.setBounds(this.getBounds());
	}
}
