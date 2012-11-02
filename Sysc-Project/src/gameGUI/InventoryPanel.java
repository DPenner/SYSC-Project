package gameGUI;
import gameCore.Inventory;
import gameCore.Item;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
/**
 * Inventory Panel for the main view >>> Currently implemented in KDTView<<<
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

public class InventoryPanel extends JPanel {
	
	public InventoryPanel(){
		this.setBackground(Color.BLUE);
		this.setSize(200, 400);
		
	}
	
	private void drawInventory(Graphics g){
		
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);  //calling repaint later on, actually calls the paintComponent on anything that can be refreshed.
		g.drawString("Inventory",10, 10);
		drawInventory(g);
		
	}
	
}
