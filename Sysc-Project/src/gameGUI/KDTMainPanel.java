package gameGUI;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class KDTMainPanel extends JPanel{
	
	public KDTMainPanel(){
		this.setBackground(Color.WHITE);
		this.setSize(400, 800);
	}
	
	private void makePlayer(){
		Graphics g;
		
	}
	@Override
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);  //calling repaint later on, actually calls the paintComponent on anything that can be refreshed.
		g.drawRoundRect(0, 0, 300, 500, 5, 5);
		g.drawString("Main",10,10);
		
		//g.drawLine(0, 0, 100, 100);
		//g.drawOval(getX() + getWidth()/4, getY() + getHeight()/4, getWidth()/2, getHeight()/2);  //getWidth/getHeight are from JPanel
	}
	
	

}
