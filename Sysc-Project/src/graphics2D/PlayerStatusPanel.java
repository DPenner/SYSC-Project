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
	private static int TEXT_OFFSET=50;
	private static int TEXT_TAB1=20;
	private static int ROW_OFFSET=25;
		
	public PlayerStatusPanel(Player p){
		player=p;
		//this.setBackground(Color.BLACK);
		player.addPlayerListener(this);
	}
	/**
	 * Draw the status with the given Label and value at the given x,y position
	 * @param g	-graphics
	 * @param sLabel - String to display on label
	 * @param value	- value of the status.
	 * @param x		- x position to draw status
	 * @param y		- y position to draw status
	 */
	private void drawStatus(Graphics g, String sLabel, int value, int x, int y){
		g.drawString(sLabel, x, y+10); //label for the string
		g.drawString(value+"", x+TEXT_OFFSET+2, y+10);
		
	}
	
	//--- Draw a monster ---
	
	private void drawMonster(){
		
	}
	

	@Override

	/**
	 * PaintCompenent Method - paints the graphics associated with the PlayerStatusPanel
	 */

	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);  //calling repaint later on, actually calls the paintComponent on anything that can be refreshed.
		//g.setColor(Color.WHITE);
		g.drawString("Player Status", 10, 12);
		
		int playerHealth=player.getHealth();
		drawStatus(g, "Health:", playerHealth, TEXT_TAB1, ROW_OFFSET);
		drawStatus(g, "Attack:", player.getAttack(),TEXT_TAB1, ROW_OFFSET*2);
		
		if(player.isDead()){
			g.setColor(Color.RED);
			g.drawString("Player has died.",TEXT_TAB1, ROW_OFFSET*3);
			g.drawString("GAME OVER!", TEXT_TAB1, ROW_OFFSET*4);
					
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
