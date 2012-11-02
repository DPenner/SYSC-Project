package gameGUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class PlayerStatusPanel extends JPanel implements PlayerListener{
	//km private Player p;
	private static int BOX_SIZE=22;
	private static int TEXT_OFFSET=50;
	private static int BOX_OFFSET=-1;
	
	public PlayerStatusPanel(){
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(400,80));
			
	}
	
	private void drawStatus(Graphics g, String sLabel, int value, int x, int y){
		g.drawString(sLabel, x, y+10); //label for the string
		
		g.drawRect(x+TEXT_OFFSET, y+BOX_OFFSET, BOX_SIZE, BOX_SIZE );
		g.drawString(value+"", x+TEXT_OFFSET+2, y+10);
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);  //calling repaint later on, actually calls the paintComponent on anything that can be refreshed.
		g.setColor(Color.WHITE);
		g.drawString("Player Status", 10, 12);
		
		drawStatus(g, "Health:", 8, 20,25);
		drawStatus(g, "Attack:", 10,20,50);
		

		//g.drawRect(0, 0, 400, 300);
		//g.drawString("Test", 20, 20);
		//g.drawLine(0, 0, 100, 100);
		//g.drawOval(getX() + getWidth()/4, getY() + getHeight()/4, getWidth()/2, getHeight()/2);  //getWidth/getHeight are from JPanel
	}

	@Override
	public void itemAdded(PlayerEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void itemDropped(PlayerEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void statsChanged(PlayerEvent e) {
		// TODO Auto-generated method stub
		this.repaint();
		
	}

}
