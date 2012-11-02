package gameGUI;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class InputPanel extends JPanel {
	
	public InputPanel(){
		this.setBackground(Color.ORANGE);
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);  //calling repaint later on, actually calls the paintComponent on anything that can be refreshed.
		g.drawString("Input Panel.", 10, 10);
	}

}
