package graphics2D;

import gameCore.*;
import gameLoader.Level;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.*;

/**
 * MapView provides a view of the map, which consists of Edges and Tiles
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

public class MapView extends JScrollPane implements ComponentListener {
	private static final int TILE_SIZE = 40;
	private static final int EDGE_WIDTH = 4;
	
	private static final int MINIMUM_WIDTH = 5 * TILE_SIZE;
	private static final int MINIMUM_HEIGHT = MINIMUM_WIDTH;
	private static final int DEFAULT_WIDTH = 1000;
	private static final int DEFAULT_HEIGHT = DEFAULT_WIDTH;
	
	private static final Integer TILE_LAYER_DEPTH = 0;
	private static final Integer EDGE_LAYER_DEPTH = 10;
	
	private JLayeredPane map;
	private TilePanel tileLayer;
	private EdgePanel edgeLayer;
	
	//offset is the value of the Tile at the top left corner
	private int xOffset;
	private int yOffset;
	
	public MapView(){
		this(null);
	}
	
	public MapView(Level l){
      
		//set up the map pane
		map = new JLayeredPane();
		this.getViewport().add(map);
		
		//add panels
		tileLayer = new TilePanel(this);
		edgeLayer = new EdgePanel(this);
		map.add(tileLayer, TILE_LAYER_DEPTH);
        map.add(edgeLayer, EDGE_LAYER_DEPTH);
        
        //configure panels
        setPanelBounds(l);
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
		this.addComponentListener(this);
		this.setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
		xOffset = 0; //TEMP, currently offsets not properly calculated
		yOffset = 0;
	}
	
	//------------Set-up-----------//
	public void addTile(Tile t){
		tileLayer.addLayoutObject(t);
	}
	public void addEdge(Edge edge){
		edgeLayer.addLayoutObject(edge);
	}
	
	//------------Scaling-----------//
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
	protected void highLight(Tile t){
		tileLayer.highLight(t);
	}
	protected void unHighLight(Tile t){
		tileLayer.unHighLight(t);
	}
	
	//-----------Component Set up------------//
	private void setPanelBounds(Level level){
		this.setBounds(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		if (level == null){
			map.setBounds(this.getBounds());
		}
		else {
			map.setBounds(0, 0, getOffsettedX(level.getGridWidth()), getOffsettedY(level.getGridHeight()));
		}
		map.setBounds(this.getBounds());
		tileLayer.setBounds(map.getBounds());
		edgeLayer.setBounds(map.getBounds());
	}
	
	//------------Component events-----------//
	@Override
	public void componentHidden(ComponentEvent arg0) {
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {	
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		this.setBounds(0, 0, getSize().width, getSize().height);
	}
	
	@Override
	public void componentShown(ComponentEvent arg0) {
	}
}
