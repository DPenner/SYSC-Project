package graphics2D;

import gameCore.Tile;
import gameCore.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

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
	
	private static final Color DEFAULT_TILE_COLOR = Color.LIGHT_GRAY;
	private static final Color BACKGROUND_COLOR = Color.BLACK;
	private static final Color HIGHLIGHT_TILE_COLOR = Color.decode("0x2277AA");
	private static final Color ITEM_COLOR = Color.decode("0x964B00");
	private static final Color ITEM_DECORATION_COLOR = Color.GRAY;
	
	private Map<Point, Tile> tileLookup; //indexing by point makes it easier to locate the tile
	private Map<Point, Color> tileColors;
	
	/**
	 * Constructs a TilePanel for the given mapView
	 * @param mapView The view to display tiles for
	 */
	protected TilePanel(MapView mapView){
		super(mapView);
		tileLookup = new HashMap<Point, Tile>();
		tileColors = new HashMap<Point, Color>();
		
		//saved internally for convenience
	}

	//-------------Tile drawing------------//
	//These methods draws parts of the tiles
	/**
	 * Paints this component
	 * @param g The graphics on which to paint
	 */
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		this.setBackground(BACKGROUND_COLOR);
	}
	
	/**
	 * Draws a tile onto the panel
	 * @param g The graphics on which to draw
	 * @param t The tile to draw
	 */
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
				else {
					drawCharacter(g, rect, Color.RED);
				}
			}	
		}
	}
	
	private void drawTileBase(Graphics g, Rectangle tileRect, Color color){
		g.setColor(color);
		g.fillRect(tileRect.x, tileRect.y, tileRect.width, tileRect.height);
	}
	private void drawCharacter(Graphics g, Rectangle tileRect, Color color){
		int characterOffset = parentMap.getTileSize() / 4;
		g.setColor(color);
		g.fillOval(tileRect.x + characterOffset, tileRect.y + characterOffset, tileRect.width - 2*characterOffset, tileRect.height - 2*characterOffset);
	}
	private void drawItems(Graphics g, Rectangle tileRect){
		int tileSize = parentMap.getTileSize();
		int itemHeightOffset = tileSize / 3;
		int itemWidthOffset = tileSize / 4;
		int decorationWidth = tileSize / 11;
		int decorationOffset1 = tileSize / 3;
		int decorationOffset2 = 2*tileSize / 3;
		
		g.setColor(ITEM_COLOR);
		g.fill3DRect(tileRect.x + itemWidthOffset, tileRect.y + itemHeightOffset, 
					 tileRect.width - 2*itemWidthOffset, tileRect.height - 2*itemHeightOffset, true);
		g.setColor(ITEM_DECORATION_COLOR);
		g.fill3DRect(tileRect.x + decorationOffset1, tileRect.y + itemHeightOffset, decorationWidth, tileRect.height - 2*itemHeightOffset, true);
		g.fill3DRect(tileRect.x + decorationOffset2, tileRect.y + itemHeightOffset, decorationWidth, tileRect.height - 2*itemHeightOffset, true);
	}
	
	/**
	 * Gets an area encompassing the Tile
	 * @param t
	 * @return
	 */
	private Rectangle getTileRectangle(Tile t){
		int tileSize = parentMap.getTileSize();
		return new Rectangle(parentMap.getOffsettedX(t.getLocation()), 
				             parentMap.getOffsettedY(t.getLocation()), tileSize, tileSize);
	}
	
	/**
	 * Gets an area encompassing the tile and its edges.
	 * @param t
	 * @return
	 */
	private Rectangle getTileAndEdgeRectangle(Tile t){
		int edgeWidth = parentMap.getEdgeWidth();
		return new Rectangle(getTileRectangle(t).x - edgeWidth, getTileRectangle(t).y - edgeWidth,
							 getTileRectangle(t).width + edgeWidth*2, getTileRectangle(t).height + edgeWidth*2);
	}
	
	/**
	 * Gets the area for repainting
	 * @param t The tile to retrieve the area for
	 */
	@Override
	protected Rectangle getRepaintRectangle(Tile t){
		return getTileAndEdgeRectangle(t);
	}
	

	//-----------Color control------------//
	private Color getTileColor(Tile t){
		return tileColors.get(t.getLocation());
	}
	private void setTileColor(Tile t, Color c){
		tileColors.put(t.getLocation(), c);
	}
	
	//------------Highlighting-------------//
	protected void highLight(Tile t){
		setTileColor(t, HIGHLIGHT_TILE_COLOR);
		repaint(getTileRectangle(t));
	}
	protected void unHighLight(Tile t){
		setTileColor(t, DEFAULT_TILE_COLOR);
		repaint(getTileRectangle(t));
	}
	
	//------------Adding and getting------------//
	/**
	 * Checks if the given tile is registered to this view
	 * @param tileLocation The location of the tile to check
	 * @return True if the tile exists in this view, false otherwise 
	 */
	protected boolean hasTile(Point tileLocation){
		return tileLookup.containsKey(tileLocation);
	}
	/**
	 * Retrieves the Tile at a particular
	 * @param tileLocation
	 * @return
	 */
	protected Tile getTile(Point tileLocation){
		return tileLookup.get(tileLocation);
	}
	
	/**
	 * Adds a Tile to this panel
	 * @param t The tile to add
	 */
	@Override
	protected void addLayoutObject(Tile t){
		tileColors.put(t.getLocation(), DEFAULT_TILE_COLOR);
		tileLookup.put(t.getLocation(), t);
		super.addLayoutObject(t);
	}
	
	/**
	 * Removes a Tile from this panel
	 */
	@Override
	protected void removeLayoutObject(Tile t){
		tileColors.remove(t.getLocation());
		tileLookup.remove(t.getLocation());
		super.removeLayoutObject(t);
	}
}
