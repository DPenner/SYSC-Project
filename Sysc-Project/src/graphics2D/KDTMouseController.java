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
	//------------Fields------------//
	private static final int RIGHTCLICK = 3;
	private static final int LEFTCLICK = 1;
	FirstPersonView view;
	CommandController kdtCC;
	//------------Constructors------------//
	/**
	 * KDTMouseController constructor
	 * @param kdtCC
	 * @param fpView
	 */
	public KDTMouseController(CommandController kdtCC, FirstPersonView fpView) {
		this.view = fpView;
		view.addMouseListener(this);
		this.kdtCC = kdtCC;	
	}

	/**
	 * goDirection Method checks to see if user clicked on an specific direction(opening/door) in FirstPersonView
	 * and executes the go command to move player to the tile in the specified direction
	 *  
	 * @param e - MouseEvent fired from KDTView
	 */
	private void goDirection(MouseEvent e) {
		// handle direction
		
		Direction direction = view.directionContaining(e.getPoint());
		
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
	/**
	 * Unimplemented methods for MouseListener interface
	 */
	
	@Override
	public void mouseClicked(MouseEvent e) {
	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		

	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	/**
	 * MousePressed method checks for right-clicks and executes pickup or drop.
	 * If the an item was right-clicked, then execute a pickup.
	 * If the floor was right-clicked, then execute a drop.
	 * 
	 * @param e - MouseEvent fired from KDTView
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// Right-mouse press to pick-up an item
		if(e.getButton() == RIGHTCLICK) {
			if(view.isItemContains(e.getPoint())) kdtCC.execPickup();
			else if (view.isFloorContains(e.getPoint())) kdtCC.execDrop();
		}
	}
	/**
	 * MouseReleased method -if user did not click on an area where the items are located, then go in 
	 * the direction clicked.
	 * 
	 * @param e - MouseEvent fired from KDTView
	 */
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		boolean tileContainsItem = view.isItemContains(e.getPoint());
		if (!tileContainsItem) goDirection(e);
	}

}

