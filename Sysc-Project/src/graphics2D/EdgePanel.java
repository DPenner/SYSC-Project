package graphics2D;

import gameCore.Direction;
import gameCore.Edge;
import gameCore.Exit;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.JPanel;

class EdgePanel extends JPanel implements Observer{
	//public static final int EDGE_LENGTH = MapView.TILE_SIZE;
	public static final Color DEFAULT_EDGE_COLOR = Color.decode("0x606060");
	
	private int edgeLength;
	private int edgeWidth;
	private int tileSize;
	
	private MapView parentMap;
	private Set<Edge> edges;
	
	public EdgePanel(MapView mapView){
		parentMap = mapView;
		edges = new HashSet<Edge>();
		
		tileSize = parentMap.getTileSize();
		edgeWidth = parentMap.getEdgeWidth();
		edgeLength = tileSize;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
			
		synchronized (edges){
			for (Edge edge : edges){
				drawEdge(g, edge);
			}
		}
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		Edge e = (Edge) arg0;
		repaint(getEdgeRectangle(e));
	}
	
	protected void addEdge(Edge edge){
		synchronized (edges){
			edges.add(edge);
		}
		edge.addObserver(this);
		repaint(getEdgeRectangle(edge, true));
	}

	private void drawEdge(Graphics g, Edge edge) {
					
		if (!edge.canCrossByDefault() && edge.isInVisitedRoom()){ //only draw those that appear to be "walls" and have been visited
			g.setColor(DEFAULT_EDGE_COLOR);
			
			g.fill3DRect(getEdgeRectangle(edge, true).x, getEdgeRectangle(edge, true).y, 
					     getEdgeRectangle(edge, true).width, getEdgeRectangle(edge, true).height, true);
			
			if (edge instanceof Exit) {
				g.setColor(MapView.BROWN);
				g.fill3DRect(getEdgeRectangle(edge).x, getEdgeRectangle(edge).y, 
						     getEdgeRectangle(edge).width, getEdgeRectangle(edge).height, true);
			}
		}
	}
	
	
	private Rectangle getEdgeRectangle(Edge edge){
		return getEdgeRectangle(edge, false);
	}
	
	//if an Edge is "large", it spills over a little into the adjacent tiles, this is to prevent messy corners. 
	private Rectangle getEdgeRectangle(Edge edge, boolean isLarge){
		Direction edgeDirection;
		Point referenceLocation;
		
		int effectiveEdgeLength = isLarge ? edgeLength + edgeWidth : edgeLength - edgeWidth;
		int lengthOffset = isLarge ? -edgeWidth/2 : edgeWidth/2;
		int widthOffset = edgeWidth/2;
		
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
			return new Rectangle(parentMap.getOffsettedX(referenceLocation) + lengthOffset, 
					parentMap.getOffsettedY(referenceLocation) - widthOffset, effectiveEdgeLength, edgeWidth);
		case SOUTH:
			return new Rectangle(parentMap.getOffsettedX(referenceLocation) + lengthOffset, 
					parentMap.getOffsettedY(referenceLocation) + tileSize - widthOffset, effectiveEdgeLength, edgeWidth);
		case EAST:
			return new Rectangle(parentMap.getOffsettedX(referenceLocation) + tileSize - widthOffset, 
					parentMap.getOffsettedY(referenceLocation)  + lengthOffset, edgeWidth, effectiveEdgeLength);
		case WEST:
			return new Rectangle(parentMap.getOffsettedX(referenceLocation) - widthOffset, 
					parentMap.getOffsettedY(referenceLocation)  + lengthOffset, edgeWidth, effectiveEdgeLength);
		default:
			return new Rectangle(0, 0, 0, 0); //Essentially returns a blank - other unforeseen directions simply won't be drawn
		}
	}
}