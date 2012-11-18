package graphics2D;

import gameCore.Inventory;
import gameCore.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
/**
 * Panel for player inventory
 * 
 * @author Group D
 * @author Main author: Karen Madore
 * 
 * Group D Members
 * ---------------
 * Karen Madore
 * Trang Pham
 * Darrell Penner
 * 
 *
 * @version 2.0
 */

public class InventoryPanel extends JPanel implements PlayerListener{
	private Player player;
	private Inventory inventory;
	
	private BufferedImage img_redkey;
	
	private static int ITEM_HEIGHT = 50;
	private static int ITEM_WIDTH = 50;
	private static int IMG_XOFFSET = 20;
	private static int IMG_YOFFSET = 50;
	
	private static int TEXT_OFFSET = 10;
	private static int TEXT_TAB1=20;
	private static int ROW_OFFSET = 25;
	
	/**
	 * Constructor for InventoryPanel
	 * @param p - the player in the game
	 */
	public InventoryPanel(Player p){
		
		player=p;
		inventory = player.getInventory();
		
		//addComponentsToInventoryPanel();
		player.addPlayerListener(this);
		
		loadImage();
	}
	
	/**
	 * Format and draw the Player's inventory list on the panel
	 */
	@Override
	public void paintComponent(Graphics g) {
		int top = 12;
		int row = 1;
		
		super.paintComponent(g);
		g.drawString("Player Inventory", TEXT_OFFSET, top);
		drawInventory(g, top + ROW_OFFSET);
	}
	
	/**
	 * Test loading an image
	 */
	private void loadImage() {
		//test drawing an image
		BufferedImage img_redkey = null;
		try {
		    img_redkey = ImageIO.read(new File("/resources/redkey.jpg"));
		    
		} catch (IOException e) {
			System.out.printf("image does not exist. ");
		}
	}
	
	private void drawKey(Graphics g, int index, Color keyColor)
	{	
		int xOffset = IMG_XOFFSET;
		int yOffset = IMG_YOFFSET + ITEM_HEIGHT * index;
		
		if(img_redkey != null) {
			g.drawImage(img_redkey, 0, 25, 50, 50, 0, 0, 50, 50, null);
		}else {
			g.setColor(Color.BLACK);
			g.drawRect(xOffset+7, yOffset+19, 6, 24);
			g.setColor(keyColor);
			g.fillRect(xOffset+7, yOffset+19, 6, 24);
			
			g.setColor(Color.BLACK);
			g.drawRect(xOffset+7, yOffset+33, 10, 5);
			g.setColor(keyColor);
			g.fillRect(xOffset+7, yOffset+33, 10, 5);
			
			g.setColor(Color.BLACK);
			g.drawOval(xOffset+0, yOffset+0, 20, 20);
			g.setColor(keyColor);
			g.fillOval(xOffset+0, yOffset+0, 20, 20);
			g.setColor(Color.BLACK);
			g.drawOval(xOffset+7,yOffset+3, 6, 6);
			g.fillOval(xOffset+7,yOffset+3, 6, 6);
			
		}
	}
	
	
	/**
	 * Draws a specific inventory item from the inventory list on the panel
	 * @param g - graphics layer to draw it on
	 * @param y - drawing at y position on the panel
	 */
	private void drawInventory(Graphics g, int y){
		for(int i=0; i<inventory.size(); i++)
		{
			//g.drawString(inventory.getItem(i).toString(), TEXT_TAB1, y+ i*ROW_OFFSET);
			String itemName = inventory.getItem(i).toString();
			
			if(itemName.equals("RedKey")) drawKey(g, i, Color.RED);
			
			if(itemName.equals("BlueKey")) drawKey(g, i, Color.BLUE);
		}
		//testing position
		drawKey(g, 0, Color.RED);
		drawKey(g, 1, Color.BLUE);		
	}

	public boolean hasItem(Point p)
	{	if(inventory.size()==0) return false;
		
		System.out.println("Clicked on (" + p.getX() + ", " + p.getY()+ ")");
		
		Point topLeft = new Point(IMG_XOFFSET, IMG_YOFFSET);
		
		
		int totalHeight = IMG_YOFFSET + ITEM_HEIGHT * inventory.size();
		if(withinBounds(p, topLeft, ITEM_WIDTH, totalHeight))
		{
			return true;
		}
		else {
			return false;	
		}
		
	}
	
	public String getItemAtPosition(Point p) {
		double yPos = p.getY();
		
		int index = (int) yPos % 50;
		//error check some more
		String itemName = inventory.getItem(index).toString();
		return itemName;
	}
	
	/**
     * Is the click within these bounds
     * @param p		- point returned by mouse event
     * @param topleft - top left corner of the area of start from
     * @param width - width of area check
     * @param height - height of area to check
     * @return
     */
    private boolean withinBounds(Point p, Point topleft, int width, int height){
    
		double topLimit;
		double bottomLimit;
		
		double leftLimit;
		double rightLimit;
		
		topLimit = topleft.getY();
		bottomLimit = topLimit + height;
		
		leftLimit = topleft.getY();
		rightLimit = leftLimit + width;
		
		boolean inLREdges = (p.x > leftLimit && p.x < rightLimit); 
		boolean inTBEdges = (p.y > topLimit && p.y < bottomLimit);
		
		return (inLREdges && inTBEdges);
	}
    
	

	/**
	 * Repaint the panel if the player notifies of an itemAdded event
	 */
	@Override
	public void itemAdded(PlayerEvent e) {
		this.repaint();
	}

	/**
	 * Repaint the panel if the player notifies of an itemDropped event
	 */
	@Override
	public void itemDropped(PlayerEvent e) {
		
		this.repaint();
	}

	/**
	 * Repaint the panel if the player notifies panel of stats changed.  Nothing to do for this panel.
	 */
	@Override
	public void statsChanged(PlayerEvent e) {
		//currently do not need to do anything on inventory panel
		
	}
	
}
