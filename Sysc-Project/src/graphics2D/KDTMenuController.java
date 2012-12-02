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
		this.c = c;
				
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
			saveGameState();
			
		}
		//else if (command.equals("Restore"))
		//{
		//	restoreGameState();
					
		//}		
		else if (command.equals("Undo"))
		{
			c.execUndo();
		}
		else if (command.equals("Redo"))
		{
			c.execRedo();
		}
		
		else if (command.equals("Help"))
		{
			String message = "Press keyboard buttons to perform actions.\n\n"
					+ "Up Arrow \t- moves the player north one unit\n"
					+ "Down Arrow \t- moves the player south one unit\n"
					+ "Right Arrow \t- moves the player east one unit\n"
					+ "Left Arrow \t- moves the player west one unit\n\n"
					+ "S \t\t- searches the current tile that player is on and prints a list of items to the console\n"
					+ "P \t\t- opens dialog with list of items on ground and lets you select which one to pick up\n"
					+ "D \t\t- opens dialog with list of player's inventory items and lets you select which one to drop\n\n"
					+ "U \t\t- undos the last command\n"
					+ "R \t\t- redo the last undid command\n";
					
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
	
	private void saveGameState() {
		Serialize s = new Serialize(p, l);
		boolean writeSuccess;
		String fileName = s.selectFile(MODE_SAVE);
		if(fileName !=null) {
			writeSuccess = s.write_serialize(fileName);
			
			if (writeSuccess) {
				String message = "Game state successfully saved. Exiting game.";
				JOptionPane.showMessageDialog(null, message, "Game State", JOptionPane.INFORMATION_MESSAGE);
				closeFrameAndExit();
			}else {
				String message = "Game state NOT successfully saved.";
				JOptionPane.showMessageDialog(null, message, "Game State", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	

	
}


