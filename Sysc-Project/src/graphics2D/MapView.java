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
 * MapView provides a view of the map, which consists of Edges, Tiles, and contained in the Tiles
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

public class MapView extends JLayeredPane implements ComponentListener {
	private static final int TILE_SIZE = 40;
	private static final int EDGE_WIDTH = 4;
	private static final int MINIMUM_SIZE = 5 * TILE_SIZE;
	
	private static final Integer TILE_LAYER_DEPTH = 0;
	private static final Integer EDGE_LAYER_DEPTH = 10;
	
	protected static final Color BROWN = Color.decode("0x964B00");
	private TilePanel tileLayer;
	private EdgePanel edgeLayer;
	
	private int xOffset;
	private int yOffset;
	
	public MapView(){
		
		//sets up this component
		this.setBounds(0, 0, 4000, 4000);
		this.addComponentListener(this);
		this.setMinimumSize(new Dimension(MINIMUM_SIZE, MINIMUM_SIZE));
        
		//add panels
		tileLayer = new TilePanel(this);
		edgeLayer = new EdgePanel(this);
		this.add(tileLayer, TILE_LAYER_DEPTH);
        this.add(edgeLayer, EDGE_LAYER_DEPTH);
        
        //configure panels
        setPanelBounds();
        tileLayer.setOpaque(true);
        edgeLayer.setOpaque(false);
        
        //set Offsets
        setOffsets(0, 0);
	}
	
	public MapView(Level l){
		this();
	}
	
	public void addTile(Tile t){
		tileLayer.addTile(t);
	}
	public void addEdge(Edge edge){
		edgeLayer.addEdge(edge);
	}
	
	protected int getTileSize(){
		return TILE_SIZE;
	}
	protected int getEdgeWidth(){
		return EDGE_WIDTH;
	}
	
	//------------Scaling-----------//
	protected int getOffsettedX(Point tileLocation){
		return (tileLocation.x + xOffset) * TILE_SIZE;
	}
	protected int getOffsettedY(Point tileLocation){
		return (tileLocation.y + yOffset) * TILE_SIZE;
	}
	private Point getTileLocation(Point offsettedLocation){
		return new Point(offsettedLocation.x/TILE_SIZE - xOffset, offsettedLocation.y/TILE_SIZE - yOffset);
	}
	protected Tile getTile(Point offsettedLocation){
		return tileLayer.getTile(getTileLocation(offsettedLocation));
	}
	protected void highLight(Tile t){
		tileLayer.highLight(t);
	}
	protected void unHighLight(Tile t){
		tileLayer.unHighLight(t);
	}
	
	protected void setOffsets(int xOffset, int yOffset){
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		repaint(); //everything needs to be shifted
	}
	
	private void setPanelBounds(){
		tileLayer.setBounds(this.getBounds());
		edgeLayer.setBounds(this.getBounds());
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
		setPanelBounds();
	}
	
	@Override
	public void componentShown(ComponentEvent arg0) {
	}

	
	
	
	
	
	//TEMP - testing purposes
	/*public static void main(String[] args){
		Room r = new Room();
		Tile one = (new Tile(new Point(0, 0), r));
        Tile two = (new Tile(new Point(0, 1), r));
        Tile three = (new Tile(new Point(1, 0), r));
        Tile four = (new Tile(new Point(1, 1), r));
        
        Edge oneN, oneE, oneW, oneS, twoN, twoE, twoS, threeW, threeS, threeE, fourS, fourE;
        Item key = new Item("key", 0);
        
        oneN = new Edge(null, one, false);
        one.setEdge(Direction.NORTH, oneN);
        oneW = new Edge(null, one, false);
        one.setEdge(Direction.WEST, oneW);
        oneE = new Exit(one, two, true, key);
        one.setEdge(Direction.EAST, oneE);
        two.setEdge(Direction.WEST, oneE);
        oneS = new Edge(one, three, true);
        one.setEdge(Direction.SOUTH, oneS);
        three.setEdge(Direction.NORTH, oneS);
        twoN = new Edge(null, two, false);
        two.setEdge(Direction.NORTH, twoN);
        twoE = new Edge(null, two, false);
        two.setEdge(Direction.EAST, twoE);
        twoS = new Edge(two, four, true);
        two.setEdge(Direction.SOUTH, twoS);
        four.setEdge(Direction.NORTH, twoS);
        threeW = new Edge(null, three, false);
        three.setEdge(Direction.WEST, threeW);
        threeS = new Edge(three, null, false);
        three.setEdge(Direction.SOUTH, threeS);
        threeE = new Edge(three, four, true);
        three.setEdge(Direction.EAST, threeE);
        four.setEdge(Direction.WEST, threeE);
        fourS = new Edge(four, null, false);
        four.setEdge(Direction.SOUTH, fourS);
        fourE = new Edge(four, null, false);
        four.setEdge(Direction.EAST, fourE);
        
        Player p = new Player("bob", 10, 10, 10, one);
        
        JFrame f = new JFrame();
        f.setSize(600, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        MapView tp = new MapView();
        tp.addTile(one);
        tp.addTile(two);
        tp.addTile(three);
        tp.addTile(four);
        
        tp.addEdge(oneS);
        tp.addEdge(oneN);
        tp.addEdge(oneE);
        tp.addEdge(oneW);
        tp.addEdge(twoN);
        tp.addEdge(twoS);
        tp.addEdge(twoE);
        tp.addEdge(threeE);
        tp.addEdge(threeS);
        tp.addEdge(threeW);
        tp.addEdge(fourE);
        tp.addEdge(fourS);
        f.add(tp);
	}*/

}