package levelEditor;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class HelpFrame extends JFrame {
	public HelpFrame(){
		JPanel panel = new JPanel();
		String helpText = "Left Click to add/remove Tiles. Right Click on a Tile to Open the Tile Navigator.  " +
						  "Inside the Tile Navigator, various items and characters can be edited and placed on the Tile.  ";
		JTextArea textBox = new JTextArea(helpText);
		textBox.setLineWrap(true);
		this.add(textBox);
		this.setSize(500, 500);
		this.setVisible(true);
	}
}
