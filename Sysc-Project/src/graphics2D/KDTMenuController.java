package graphics2D;

import gameCore.Player;
import gameLoader.Level;
import gameLoader.Serialize;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import commands.CommandController;
/**
 * Controller to listen to KDTMenu 
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
public class KDTMenuController implements ActionListener {
	public static final int MODE_OPEN = 0;
	public static final int MODE_SAVE = 1;
	
	private JFrame f;
	private CommandController c;
	private Player p;
	private Level l;
	
	/**
	 * Constructor for KDTMenuController
	 * @param f - handle to the frame 
	 */
	public KDTMenuController(JFrame f, Player p, Level l, CommandController c){
		this.f = f;
		this.c = c;
		this.p = p;
		this.l = l;			
	}
	
	/**
	 * Method to respond to the menu events
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		String command = e.getActionCommand();
		
		if(command.equals("Exit"))
		{
			exitApplication();
		
		}
		else if (command.equals("Save"))
		{
			Serialize s = new Serialize(p, l);
			s.saveToFile();
		}		
		else if (command.equals("Undo"))
		{
			if(!p.isDead())
			c.execUndo();
		}
		else if (command.equals("Redo"))
		{	if(!p.isDead())
			c.execRedo();
		}
		
		else if (command.equals("Help"))
		{
			String message = "Click to perform actions.\n\n"
					+ "Click the north wall to move north\n"
					+ "Click the west wall to move west\n"
					+ "Click the east wall to move east\n"
					+ "Click the blue triangle to move south\n\n"
					+ "Click on item chest \t\t- opens dialog with list of items on ground and lets you select which one to pick up\n"
					+ "Right Click \t\t- opens dialog with list of player's inventory items and lets you select which one to drop\n\n";
					
			JOptionPane.showMessageDialog(null, message, "Help", JOptionPane.INFORMATION_MESSAGE);
		}
	
	}

	/**
	 * Exit the application
	 */
	private void exitApplication() {
		JFrame optionFrame= new JFrame("");
		int confirmExit = JOptionPane.showConfirmDialog(optionFrame, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
		
		if(confirmExit==JOptionPane.YES_OPTION){
			closeFrameAndExit();	
		}
	}

	/**
	 * Close the frame and Exit
	 */
	private void closeFrameAndExit() {
		f.dispose();
		
		System.exit(0);
	}
}