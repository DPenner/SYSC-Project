package graphics2D;

import gameCore.*;
import gameLoader.Level;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

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

public class MapView extends JLayeredPane implements Scrollable
{
	private static final int DEFAULT_TILE_SIZE = 40;
	private static final int DEFAULT_EDGE_WIDTH = 4;
	private int tileSize;
	private int edgeWidth;
	
	private static final Integer TILE_LAYER_DEPTH = 0;
	private static final Integer EDGE_LAYER_DEPTH = 100;
	
	private TilePanel tileLayer;
	private EdgePanel edgeLayer;
	
	//Refers to Tile rather than offsetted values
	private int minX;
	private int minY;
	private int maxX;
	private int maxY;
	
	//------------Constructors and helpers------------//
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
		this(l, DEFAULT_TILE_SIZE, DEFAULT_EDGE_WIDTH);
	}
	
	public MapView(Level l, int tileSize, int edgeWidth){
		setLevelOffset(l);
		addPanels(tileSize, edgeWidth);
		addLayoutObjects(l);
		setPanelBounds();
	}
	
	private void setLevelOffset(Level l){
		minX = 0;
		minY = 0;
		
		if (l != null){
			maxX = l.getGridWidth();
			maxY = l.getGridHeight();
		}
		else {
			maxX = 0;
			maxY = 0;
		}
	}

	private void addPanels(int tileSize, int edgeWidth){
		tileLayer = new TilePanel(this);
		edgeLayer = new EdgePanel(this);
		
		this.add(tileLayer, TILE_LAYER_DEPTH);
		this.add(edgeLayer, EDGE_LAYER_DEPTH);
		        
		tileLayer.setOpaque(true);
	    edgeLayer.setOpaque(false);
	    setEdgeWidth(edgeWidth);
	    setTileSize(tileSize);
	}
	
	private void addLayoutObjects(Level l){
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
	}
	
	/**
	* Sets the panel bounds to to the size of this component
	*/
	public void setPanelBounds(){
		int width = this.getSize().width;
		int height = this.getSize().height;
		this.setBounds(0, 0, width, height);
		tileLayer.setBounds(this.getBounds());
		edgeLayer.setBounds(this.getBounds());
	}
	
	//------------Adding and removing LayoutObjects-----------//
	/**
	* Adds a tile to the MapView
	* @param t The Tile to be added.
	*/
	public void addTile(Tile t){
		tileLayer.addLayoutObject(t);
		if (t.getLocation().x < minX){
			minX = t.getLocation().x;
		}
		if (t.getLocation().x > maxX){
			maxX = t.getLocation().x;
		}
		if (t.getLocation().y < minY){
			minY = t.getLocation().y;
		}
		if (t.getLocation().y > maxY){
			maxY = t.getLocation().y;
		}
	}
	
	/**
	* Removes a tile to the MapView
	* @param t The Tile to be removed.
	*/
	public void removeTile(Tile t){
		tileLayer.removeLayoutObject(t);
	}
	
	/**
	* Adds an Edge to the MapView.
	* @param edge The edge to be added.
	*/
	public void addEdge(Edge edge){
		edgeLayer.addLayoutObject(edge);
	}
	
	/**
	* Removes an Edge to the MapView.
	* @param edge The edge to be removed.
	*/
	public void removeEdge(Edge edge){
		edgeLayer.removeLayoutObject(edge);
	}
	
	//------------Scaling and Size-----------//
	//These methods scale back and forth between a Tile's location and its location in this MapView
	protected int getOffsettedX(int tileX){
		return (tileX + minX) * tileSize;
	}
	protected int getOffsettedX(Point tileLocation){
		return getOffsettedX(tileLocation.x);
	}
	protected int getOffsettedY(int tileY){
		return (tileY + minY) * tileSize;
	}
	protected int getOffsettedY(Point tileLocation){
		return getOffsettedY(tileLocation.y);
	}
	
	/**
	* Gets the Tile Location from a view's offsetted location
	* @param offsettedLocation The view's offsetted location
	* @return The Tile's location (in terms of the model, not the view)
	*/
	public Point getTileLocation(Point offsettedLocation){
		return new Point(offsettedLocation.x/tileSize - minX, offsettedLocation.y/tileSize - minY);
	}
	
	/**
	* Checks if there is a Tile at the given offsetted location.
	* @param offsettedLocation The offsetted location to check.
	* @return True if there is a tile at the specified location, false otherwise.
	*/
	public boolean hasTile(Point offsettedLocation){
		return tileLayer.hasTile(getTileLocation(offsettedLocation));
	}
	
	/**
	* Gets the tile at the given offsetted location
	* @param offsettedLocation The offsetted location at which to retrieve the tile
	* @return The Tile at the given location
	*/
	public Tile getTile(Point offsettedLocation){
		return tileLayer.getTile(getTileLocation(offsettedLocation));
	}
	
	/**
	* Sets the tile size at which this view should display tiles
	* @param size The size at which tiles should be displayed
	*/
	public void setTileSize(int size){
		tileSize = size;
		repaint();
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(getOffsettedX(maxX) + tileSize, getOffsettedY(maxY) + tileSize);
	}
	
	/**
	* Sets the edge width at which this view should display edges
	* @param size The width at which edges should be displayed
	*/
	public void setEdgeWidth(int size){
		edgeWidth = size;
		repaint();
	}
	
	/**
	* Gets the tile size of this MapView.
	* @return The tile size
	*/
	public int getTileSize(){
		return tileSize;
	}
	
	/**
	* Gets the edge width of this MapView
	* @return the edgeWidth
	*/
	public int getEdgeWidth(){
		return edgeWidth;
	}
	
	//------------Scrolling Support------------//
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		return 0;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		return 1;
	}
	
	//------------Miscellaneous------------//
	/**
	* Highlights the given tile.
	* @param t The tile to highlight.
	*/
	protected void highLight(Tile t){
		tileLayer.highLight(t);
	}
	
	/**
	* Unhighlights the given tile.
	* @param t The tile to unhighlight.
	*/
	protected void unHighLight(Tile t){
		tileLayer.unHighLight(t);
	}
}