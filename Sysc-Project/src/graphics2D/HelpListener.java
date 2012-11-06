package graphics2D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;


public class HelpListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
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
