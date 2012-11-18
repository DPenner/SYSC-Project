package graphics2D;

import gameCore.Direction;
import commands.GoCommand;
import gameCore.Direction;
import commands.CommandController;
import commands.GoCommand;
import commands.DropCommand;
import commands.PickUpCommand;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import commands.GoCommand;

public class KDTMouseController implements MouseListener {
	private static final int BUTTON3 = 3;
	Point middle;
	//MapView view;  // replace with KDTView
	MapView view;
	CommandController kdtCC;
	
	public KDTMouseController(CommandController kdtCC, MapView view) {
		this.view = view;
		view.addMouseListener(this);
		this.kdtCC = kdtCC;	
		middle = new Point(200,200);
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		//test double-click
		if(e.getClickCount()==2)  //check that it is on an item
		{
			System.out.println("Double-clicked.");
		}
		
		
		//test directions 
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
		// TODO Auto-generated method stub
		if(e.getButton()== BUTTON3) {
			kdtCC.execPickup();
			System.out.println("Button presses was " + e.getButton());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}

