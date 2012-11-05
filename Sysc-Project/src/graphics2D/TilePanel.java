package graphics2D;

import gameCore.Edge;
import gameCore.Tile;
import gameCore.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * TilePanel is a specialized panel that displays Tiles for its parent MapView. This includes displaying
 * the Tiles' contents
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

class TilePanel extends LayoutPanel<Tile>{
	
	public static final Color DEFAULT_TILE_COLOR = Color.LIGHT_GRAY;
	public static final Color BACKGROUND_COLOR = Color.BLACK;
	public static final Color HIGHLIGHT_TILE_COLOR = Color.decode("0x2277AA");
	public static final Color ITEM_COLOR = Color.decode("0x964B00");
	
	private int tileSize;
	private int edgeWidth;
	
	private Map<Point, Tile> tileLookup; //indexing by point makes it easier to locate the tile
	private Map<Point, Color> tileColors;
	
	protected TilePanel(MapView mapView){
		super(mapView);
		tileLookup = new HashMap<Point, Tile>();
		tileColors = new HashMap<Point, Color>();
		
		//saved internally for convenience
		tileSize = mapView.getTileSize();
		edgeWidth = mapView.getEdgeWidth();
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		this.setBackground(BACKGROUND_COLOR);
	}
	
	@Override
	protected void addLayoutObject(Tile t){
		tileColors.put(t.getLocation(), DEFAULT_TILE_COLOR);
		tileLookup.put(t.getLocation(), t);
		super.addLayoutObject(t);
	}
	
	@Override
	protected void drawLayoutObject(Graphics g, Tile t){
		Rectangle rect = getTileRectangle(t);
		
		if (t.isVisited()){
			drawTileBase(g, rect, getTileColor(t));

			if (t.hasItems()){
				drawItems(g, rect);
			}
			
			if (t.hasCharacter()){
				
				if (t.getCharacter() instanceof Player){ //TEMP to replace with polymorphism
					drawCharacter(g, rect, Color.GREEN);
				}
				else{
					drawCharacter(g, rect, Color.RED);
				}
			}	
		}
	}
	
	private void drawTileBase(Graphics g, Rectangle tileRect, Color color){
		g.setColor(color);
		g.fillRect(tileRect.x, tileRect.y, tileRect.width, tileRect.height);
	}
	private void drawCharacter(Graphics g, Rectangle tileRect, Color color){ //TEMP magic numbers
		g.setColor(color);
		g.fillOval(tileRect.x + 10, tileRect.y + 10, tileRect.width - 20, tileRect.height - 20);
	}
	private void drawItems(Graphics g, Rectangle tileRect){ //TEMP magic numbers
		g.setColor(ITEM_COLOR);
		g.fill3DRect(tileRect.x + 8, tileRect.y + 12, tileRect.width - 16, tileRect.height - 24, true);
		g.setColor(Color.GRAY);
		g.fill3DRect(tileRect.x + 11, tileRect.y + 12, 3, tileRect.height - 24, true);
		g.fill3DRect(tileRect.x + 27, tileRect.y + 12, 3, tileRect.height - 24, true);
	}
	
	private Rectangle getTileRectangle(Tile t){
		return new Rectangle(parentMap.getOffsettedX(t.getLocation()), 
				             parentMap.getOffsettedY(t.getLocation()), tileSize, tileSize);
	}
	
	private Rectangle getTileAndEdgeRectangle(Tile t){
		return new Rectangle(getTileRectangle(t).x - edgeWidth, getTileRectangle(t).y - edgeWidth,
							 getTileRectangle(t).width + edgeWidth*2, getTileRectangle(t).height + edgeWidth*2);
	}
	
	protected Tile getTile(Point tileLocation){
		return tileLookup.get(tileLocation);
	}
	protected void highLight(Tile t){
		setTileColor(t, HIGHLIGHT_TILE_COLOR);
		repaint(getTileRectangle(t));
	}
	protected void unHighLight(Tile t){
		setTileColor(t, DEFAULT_TILE_COLOR);
		repaint(getTileRectangle(t));
	}
	
	private Color getTileColor(Tile t){
		return tileColors.get(t.getLocation());
	}
	private void setTileColor(Tile t, Color c){
		tileColors.put(t.getLocation(), c);
	}
	
	@Override
	protected Rectangle getRepaintRectangle(Tile t){
		return getTileAndEdgeRectangle(t);
	}
}
