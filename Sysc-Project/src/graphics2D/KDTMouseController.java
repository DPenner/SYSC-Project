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
	private static final int RIGHTCLICK = 3;
	private static final int LEFTCLICK = 1;
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
		if(e.getButton() == RIGHTCLICK) {
			if(view.isItemContains(e.getPoint())) kdtCC.execPickup();
			else if (view.isFloorContains(e.getPoint())) kdtCC.execDrop();
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

