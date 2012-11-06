package graphics2D;

import gameCore.Inventory;
import gameCore.Player;

import java.awt.Graphics;

import javax.swing.DefaultListModel;
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
 * @version 1.0
 */

public class InventoryPanel extends JPanel implements PlayerListener{
	private Player player;
	private Inventory inventory;
	
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
	}
	/**
	 * Draws a specific inventory item from the inventory list on the panel
	 * @param g - graphics layer to draw it on
	 * @param y - drawing at y position on the panel
	 */
	private void drawInventory(Graphics g, int y){
		for(int i=0; i<inventory.size(); i++)
		{
			g.drawString(inventory.getItem(i).toString(), TEXT_TAB1, y+ i*ROW_OFFSET);
		}
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
	
	/*
	 * private void addComponentsToInventoryPanel() {
	
				
		JList jl;
		lmodel= new DefaultListModel();
		
		jl=new JList();
		jl.setModel(lmodel);
		jl.setName("InventoryList");

		add(jl);

		 	
		for(int i=0; i<inv.size(); i++){
			lmodel.addElement(inv.getItem(i).toString());
		}
	}
	*/
}
