package graphics3D;

import gameCore.Tile;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ForegroundPanel extends JPanel{
	
	private static final Color ITEM_COLOR = Color.decode("0x964B00");
	private static final Color ITEM_DECORATION_COLOR = Color.GRAY;

	private boolean hasItems;
	public ForegroundPanel() {
		hasItems = false;
	}

	public void draw(Tile t) {
		if(t.hasItems() != hasItems)
		{
			hasItems = t.hasItems();
			repaint();
		}
	}

	/**
	 * Paints this component
	 * @param g The graphics on which to paint
	 */
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//draw item chest
	}
}
