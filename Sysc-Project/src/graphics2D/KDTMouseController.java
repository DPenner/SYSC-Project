package graphics2D;

import gameCore.Direction;
import graphics3D.FirstPersonView;
import commands.GoCommand;
import commands.CommandController;
import commands.GoCommand;
import commands.DropCommand;
import commands.PickUpCommand;
//import graphics3D.*;


import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import commands.GoCommand;

/**
 * KDTMouseController is a MouseListener for Handling Mouse Events for the KDTView
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
public class KDTMouseController implements MouseListener {
	private static final int BUTTON3 = 3;
	private static final int BUTTON1 = 1;
	FirstPersonView view;
	CommandController kdtCC;
	
	public KDTMouseController(CommandController kdtCC, FirstPersonView fpView) {
		this.view = fpView;
		view.addMouseListener(this);
		this.kdtCC = kdtCC;	
	}
		
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	/**
	 * @param e
	 */
	private void goDirection(MouseEvent e) {
		// handle direction
		
		Direction direction = view.directionContaining(e.getPoint());
		
		//--- remove later ---
		
		if (direction != null)
		{
			System.out.printf("Direction clicked is : " + direction.toString() + "\n");
		}
		
		if(direction != null) {
			switch (direction) 
			{	
				case NORTH:
				{
					kdtCC.execGo(Direction.NORTH);
					
					break;
				}
				case SOUTH:
				{
					kdtCC.execGo(Direction.SOUTH);
					break;
				}
				case WEST:
				{
					kdtCC.execGo(Direction.WEST);
					break;
				}
				case EAST:
				{
					kdtCC.execGo(Direction.EAST);
					break;
				}
			
			}
		}
	}
	

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Right-mouse press to pick-up an item
		if(e.getButton() != BUTTON1) {
				if(e.getButton()== BUTTON3 && view.isItemContains(e.getPoint())) {
				//if(e.getButton()== BUTTON3 && isItemContains(e.getPoint())) {
					
					kdtCC.execPickup();
					
					System.out.println("Button presses was " + e.getButton());
				}
				
				boolean isInventoryPanel = e.getSource() instanceof graphics2D.InventoryPanel;
				if( isInventoryPanel) 
				{
					InventoryPanel inventoryPanel = (InventoryPanel) e.getSource();
					if(inventoryPanel.hasItem(e.getPoint())) 
					{
						//execute pickup
						String itemName = inventoryPanel.getItemAtPosition(e.getPoint());
						if(!itemName.isEmpty())
						{
							kdtCC.execDrop(itemName);
						}
						
						
					}  
				}// end if check for inventory panel	
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		boolean tileContainsItem = view.isItemContains(e.getPoint());
		if (!tileContainsItem) goDirection(e);
	}

}

