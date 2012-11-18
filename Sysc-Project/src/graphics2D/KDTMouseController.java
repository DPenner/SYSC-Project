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
	Point middle;
	//MapView view;  // replace with KDTView
	FirstPersonView view;
	CommandController kdtCC;
	
	//--- modify the view in arg support NEW view 
	public KDTMouseController(CommandController kdtCC, FirstPersonView view) {
		this.view = view;
		view.addMouseListener(this);
		this.kdtCC = kdtCC;	
		//middle = new Point(200,200);
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		//test double-click
		if(e.getClickCount()==2)  //check that it is on an item
		{
			System.out.println("Double-clicked.");
		}
		
		// handle direction
		Direction direction = directionContaining(e.getPoint());
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
			
		//test directions
		/*
		if (e.getX()<(middle.getX()-100) )
		{  //west
			System.out.println("Player should go west. (" + e.getX() +", " + e.getY()+ ")" );
			kdtCC.execGo(Direction.WEST);
					
		}
		else if (e.getX()> middle.getX()+100) 
		{	//east
			System.out.println("Player should go east. (" + e.getX() +", " + e.getY()+ ")" );
			kdtCC.execGo(Direction.EAST);
		}
		else if (e.getY() < middle.getY()-100)
		{	//north
			System.out.println("Player should go north. (" + e.getX() +", " + e.getY()+ ")" );
			kdtCC.execGo(Direction.NORTH);
			
		}
		else if (e.getY() > middle.getY()+100)
		{	//south
			System.out.println("Player should go south. (" + e.getX() +", " + e.getY()+ ")" );
			kdtCC.execGo(Direction.SOUTH);
		}
		*/
	
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
		
		if(e.getButton()== BUTTON3 && isItemContains(e.getPoint())) {
		//if(e.getButton()== BUTTON3 && isItemContains(e.getPoint())) {
			kdtCC.execPickup();
			System.out.println("Button presses was " + e.getButton());
		}
		
		boolean isInventoryPanel = e.getSource() instanceof graphics2D.InventoryPanel;
		if( isInventoryPanel) {
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
		}
	}
	/**
	 * 
	 * @param point
	 * @return
	 */
	private boolean isItemContains(Point point) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	//------ Methods for testing purposes ------
	/**
	 * Testing directioContaining panel
	 * @param point
	 * @return
	 */
	private Direction directionContaining(Point point) {
		// TODO Auto-generated method stub
		return Direction.SOUTH;
		//return null;
	}
}

