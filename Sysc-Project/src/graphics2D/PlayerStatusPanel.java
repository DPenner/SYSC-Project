package graphics2D;

import gameCore.Direction;
import gameCore.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * View for the KDT Maze
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


public class PlayerStatusPanel extends JPanel implements PlayerListener{
	private Player player;
	
	private static int TEXT_TAB1=20;
	private static int TEXT_TAB2=52;
	private static int LINE_HEIGHT=20;
	private static int FIRST_LINE = LINE_HEIGHT;  //start the first line of text here
		
	public PlayerStatusPanel(Player p){
		player=p;
		//this.setBackground(Color.BLACK);
		player.addPlayerListener(this);
	}
	/**
	 * Draw the status with the given Label and value at the given x,y position
	 * or alternatively draw text at the given line number
	 * @param g	-graphics
	 * @param sLabel - String to display on label
	 * @param value	- value of the status.
	 * @param x		- x position to draw status
	 * @param line	- line position to draw status
	 */
	private void drawStatus(Graphics g, String sLabel, int value, int x, int lineNo){
		int positionFromTopofPanel = FIRST_LINE + (LINE_HEIGHT * lineNo);
		
		g.drawString(sLabel, x, positionFromTopofPanel); //label for the string
		
		if(value >= 0 ) {
			g.drawString(value+"", x+TEXT_TAB2, positionFromTopofPanel);
		}
		
	}
	
	/**
	 * PaintCompenent Method - paints the graphics associated with the PlayerStatusPanel
	 */
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);  //calling repaint later on, actually calls the paintComponent on anything that can be refreshed.
		//g.setColor(Color.WHITE);
		g.drawString("Player Status", 10, 17);
		
		int playerHealth=player.getHealth();
		drawStatus(g, "Health:", playerHealth, TEXT_TAB1, 1);
		drawStatus(g, "Attack:", player.getAttack(),TEXT_TAB1, 2);
		
		if(player.isDead()){
			g.setColor(Color.RED);
			//g.drawString("Player has died.",TEXT_TAB1, 3);
			//g.drawString("GAME OVER!", TEXT_TAB1, 4);
			drawStatus(g, "Player has died.", -1, TEXT_TAB1, 3);
			drawStatus(g, "GAME OVER!", -1, TEXT_TAB1, 4);
					
		}
		
	}

	/**
	 * player status panel does not need this
	 */
	@Override
	public void itemAdded(PlayerEvent e) {
		// player status panel does not need this
		
	}

	/**
	 * player status panel does not need this
	 */
	@Override
	public void itemDropped(PlayerEvent e) {
		//player status panel does not need this
		
	}

	/**
	 * Update the Player's status on the panel
	 */
	@Override
	public void statsChanged(PlayerEvent e) {
		// TODO Auto-generated method stub
		this.repaint();
	}
	@Override
	public void positionChanged(PlayerEvent e, Direction backDir) {
		// TODO Auto-generated method stub
		
	}

}
