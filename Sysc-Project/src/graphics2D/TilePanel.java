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
 * the Tiles' contents including Characters and Items
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
class TilePanel extends JPanel implements Observer{
	
	public static final Color DEFAULT_TILE_COLOR = Color.LIGHT_GRAY;
	public static final Color HIDDEN_TILE_COLOR = Color.BLACK;
	public static final Color BACKGROUND_COLOR = Color.BLACK;
	public static final Color HIGHLIGHT_TILE_COLOR = Color.decode("0x2277AA");
	public static final Color ITEM_COLOR = Color.decode("0x964B00");
	
	private int tileSize;
	private int edgeWidth;
	
	private MapView parentMap;
	private Map<Point, Tile> tiles; //indexing by point makes it easier to locate the tile
	private Map<Point, Color> tileColors;
	private Queue<Tile> tilesToAdd;
	
	protected TilePanel(MapView mapView){
		parentMap = mapView;
		tiles = new HashMap<Point, Tile>();
		tileColors = new HashMap<Point, Color>();
		tilesToAdd = new ConcurrentLinkedQueue<Tile>();
		
		//saved internally for convenience
		tileSize = mapView.getTileSize();
		edgeWidth = mapView.getEdgeWidth();
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		this.setBackground(Color.BLACK);
		
		for (Tile t : tiles.values()){
			drawTile(g, t);
		}
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		Tile t = (Tile) arg0;
		this.repaint(getTileAndEdgeRectangle(t)); //repaint only necessary area
	}
	
	protected void addTile(Tile t){
		setTileColor(t, DEFAULT_TILE_COLOR);
		if (t == null){
			throw new IllegalArgumentException("Tile cannot be null");
		}
		tilesToAdd.add(t);
		t.addObserver(this);
		
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
            	Tile newTile = tilesToAdd.remove();
            	tiles.put(newTile.getLocation(), newTile);		
    			repaint(getTileAndEdgeRectangle(newTile));
            }
        });
	}
	
	private void drawTile(Graphics g, Tile t){
		Rectangle rect = getTileRectangle(t);
		
		if (t.isVisited()){
			drawTileBase(g, rect, getTileColor(t));
			
			//drawItems
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
		/*else {
			drawTileBase(g, rect, HIDDEN_TILE_COLOR);
		}*/
		
		
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
		return tiles.get(tileLocation);
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
}