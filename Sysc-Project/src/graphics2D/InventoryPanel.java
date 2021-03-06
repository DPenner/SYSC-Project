package graphics2D;

import gameCore.Direction;
import gameCore.Inventory;
import gameCore.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
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

public class InventoryPanel extends JPanel implements PlayerListener, Serializable  {
	private static final long serialVersionUID = 1L;
	private Player player;
	private Inventory inventory;
	
	private BufferedImage img_redkey;
	
	private static final int ITEM_HEIGHT = 25;
	private static final int ITEM_WIDTH = 50;
	private static final int IMG_XOFFSET = 20;
	private static final int IMG_YOFFSET = 50;
	
	private static final int TEXT_OFFSET = 10;
	private static final int TEXT_TAB1 = 20;
	private static final int LINE_HEIGHT = 20;
	private static final int FIRST_LINE = LINE_HEIGHT;
	 
	/**
	 * Constructor for InventoryPanel
	 * @param p - the player in the game
	 */
	public InventoryPanel(Player p){
		
		player=p;
		inventory = player.getInventory();
		
		player.addPlayerListener(this);
		
		
	}
	
	/**
	 * Format and draw the Player's inventory list on the panel
	 */
	@Override
	public void paintComponent(Graphics g) {
			
		super.paintComponent(g);
		
		g.drawString("Player Inventory", 10, 17);
		
		drawInventory(g);
		
	}
	
	/**
	 * Test loading an image
	 */
	private void loadImage() {
		//test drawing an image
		//BufferedImage img_redkey = null;
		try {
			img_redkey = ImageIO.read(new File("resources/redkey.jpg"));
		    
		} catch (IOException e) {
			System.out.printf("image does not exist. ");
		}
	}
	
	private void drawItem(Graphics g, int index, String itemName)
	{	
		int xOffset = IMG_XOFFSET;
		int yOffset =  FIRST_LINE + (ITEM_HEIGHT * index);
				//IMG_YOFFSET + ITEM_HEIGHT * index;
		
		
		try {
			String imagePath = "resources/"+itemName +".png"; 
			BufferedImage image = ImageIO.read(new File(imagePath));
			g.drawImage(image, xOffset, yOffset, null);
				
		} catch (IOException e) {
			g.drawString(itemName, xOffset, yOffset);
		}
		
	}
		
	private void drawKey(Graphics g, int index, Color keyColor)
	{	
		int xOffset = IMG_XOFFSET;
		int yOffset = IMG_YOFFSET + ITEM_HEIGHT * index;
		
		if (keyColor.equals(Color.RED)) {
			loadImage();
			g.drawImage(img_redkey, xOffset, yOffset, null);
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
	private void drawInventory(Graphics g){
		for(int i = 0; i < inventory.size(); i++)
		{
			String itemName = inventory.getItem(i).toString();
			
			int positionFromTopofPanel = FIRST_LINE + (LINE_HEIGHT * (i+1));
			
			// WORKING V2 g.drawString(itemName, IMG_XOFFSET, positionFromTopofPanel);
			
			drawItem(g, i, itemName);
			
			/*  working v2
			if(itemName.equals("RedKey")) 
			{	
				drawKey(g, i, Color.RED);
			}
			else if(itemName.equals("BlueKey")) 
			{
				drawKey(g, i, Color.BLUE);
			}else
			{
				g.drawString(itemName,IMG_XOFFSET, IMG_YOFFSET *(i+1));
			}
			*/
		}
		//testing position
		//drawKey(g, 0, Color.RED);
		//drawKey(g, 1, Color.BLUE);		
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
     * @return true if the click is within the Bounds of the item
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
	@Override
	public void positionChanged(PlayerEvent e, Direction backDir) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerRestored(PlayerEvent e) {
		this.repaint();
		
	}
	
}
