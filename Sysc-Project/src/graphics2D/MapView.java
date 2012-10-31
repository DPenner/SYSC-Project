package graphics2D;

import gameCore.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import java.util.*;

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
	public static final int TILE_SIZE = 40;
	public static final int EDGE_WIDTH = 4;
	public static final int EDGE_LENGTH = TILE_SIZE + EDGE_WIDTH/2;
	
	private TilePanel tileLayer;
	private EdgePanel edgeLayer;
	
	private int xOffset;
	private int yOffset;
	
	public MapView (){
		tileLayer = new TilePanel();
		edgeLayer = new EdgePanel();
		
		this.setBounds(0, 0, 4000, 4000);
		this.addComponentListener(this);
        
        this.add(tileLayer, new Integer(0));
        this.add(edgeLayer, new Integer(1));
        
        setPanelBounds();
        tileLayer.setOpaque(true);
        edgeLayer.setOpaque(false);
        
        xOffset = 0;
        yOffset = 0;
	}
	
	
	
	public void addTile(Tile t){
		tileLayer.addTile(t);
	}
	public void addEdge(Edge edge){
		edgeLayer.addEdge(edge);
	}
	
	private int getOffsettedX(Point location){
		return location.y * TILE_SIZE + xOffset;
	}
	private int getOffsettedY(Point location){
		return location.x * TILE_SIZE + yOffset;
	}
	
	private void setPanelBounds(){
		tileLayer.setBounds(getBounds());
		edgeLayer.setBounds(getBounds());
	}
	
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
	
	
	
	
	
	private class TilePanel extends JPanel implements Observer{
		private Set<Tile> tiles;
		
		public TilePanel(){
			tiles = new HashSet<Tile>();
		}
		
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			this.setBackground(Color.BLACK);
			
			for (Tile t : tiles){
				drawTile(g, t);
			}
		}
		
		@Override
		public void update(Observable arg0, Object arg1) {
			Tile t = (Tile) arg0;
			this.repaint(getTileRectangle(t)); //repaint only necessary area
		}
		
		public void addTile(Tile t){
			tiles.add(t);
			t.addObserver(this);
		}
		
		private void drawTile(Graphics g, Tile t){
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(getTileRectangle(t).x, getTileRectangle(t).y, getTileRectangle(t).width, getTileRectangle(t).height);
		}
		
		private Rectangle getTileRectangle(Tile t){
			return new Rectangle(getOffsettedX(t.getLocation()), getOffsettedY(t.getLocation()), TILE_SIZE, TILE_SIZE);
		}
	}
	
	private class EdgePanel extends JPanel implements Observer{
		private Set<Edge> edges;
		
		public EdgePanel(){
			edges = new HashSet<Edge>();
		}
		
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			
			for (Edge edge : edges){
				drawEdge(g, edge);
			}
		}
		
		@Override
		public void update(Observable arg0, Object arg1) {
			Edge e = (Edge) arg0;
			repaint(getEdgeRectangle(e));
		}
		
		private void addEdge(Edge edge){
			edges.add(edge);
			edge.addObserver(this);
		}

		private void drawEdge(Graphics g, Edge edge) {
			
			
			if (!edge.canCrossByDefault()){ //only draw those that appear to be "walls"
				if (edge instanceof Exit) g.setColor(Color.decode("0x964b00"));
				else g.setColor(Color.BLACK);
				
				g.fillRect(getEdgeRectangle(edge).x, getEdgeRectangle(edge).y, getEdgeRectangle(edge).width, getEdgeRectangle(edge).height);
			}
		}
		
		private Rectangle getEdgeRectangle(Edge edge){
			Direction edgeDirection;
			Point referenceLocation;
			
			referenceLocation = edge.getLocation1();
			if (referenceLocation == null){
				referenceLocation = edge.getLocation2();
				edgeDirection = edge.getDirection2();				
			}
			else{
				edgeDirection = edge.getDirection1();	
			}
			
			switch (edgeDirection)
			{
			case NORTH:
				return new Rectangle(getOffsettedX(referenceLocation), getOffsettedY(referenceLocation) - EDGE_WIDTH/2, EDGE_LENGTH, EDGE_WIDTH);
			case SOUTH:
				return new Rectangle(getOffsettedX(referenceLocation), getOffsettedY(referenceLocation) + TILE_SIZE - EDGE_WIDTH/2, EDGE_LENGTH, EDGE_WIDTH);
			case EAST:
				return new Rectangle(getOffsettedX(referenceLocation) + TILE_SIZE - EDGE_WIDTH/2, getOffsettedY(referenceLocation), EDGE_WIDTH, EDGE_LENGTH);
			case WEST:
				return new Rectangle(getOffsettedX(referenceLocation) - EDGE_WIDTH/2, getOffsettedY(referenceLocation), EDGE_WIDTH, EDGE_LENGTH);
			default:
				throw new IllegalArgumentException("Unknown Direction");
			}
		}
	}

	
	
	
	
	
	//TEMP - testing purposes
	public static void main(String[] args){
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
	}

}