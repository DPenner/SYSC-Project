package levelEditor;

import javax.swing.*;

public class HelpFrame extends JFrame {
	private static final int DEFAULT_SIZE = 500;
	public HelpFrame(){
		super("Help");
		String helpText = "Left Click to add/remove Tiles. Right Click on a Tile to Open the Tile Navigator.  " +
						  "Inside the Tile Navigator, various items and characters can be edited and placed on the Tile.  " +
						  "For more information on the buttons, hover over them as they have tooltips.";
		JTextArea textBox = new JTextArea(helpText);
		textBox.setLineWrap(true);
		textBox.setWrapStyleWord(true);
		textBox.setEditable(false);
		this.add(textBox);
		this.setSize(DEFAULT_SIZE, DEFAULT_SIZE);
		this.setVisible(true);
	}
}
