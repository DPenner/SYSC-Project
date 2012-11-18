package graphics3D;

import gameCore.Direction;
import gameCore.Tile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ForegroundPanel extends JPanel{
	
	private static final Color ITEM_COLOR = Color.decode("0x964B00");
	private static final Color ITEM_DECORATION_COLOR = Color.GRAY;
	private static final Rectangle ITEM_AREA_RECT = new Rectangle( FirstPersonView.OUTER_BOX/2-100, FirstPersonView.OUTER_BOX-(FirstPersonView.OUTER_BOX-FirstPersonView.INNER_BOX)/2 - 20, 80, 40 );
	private static final Color DOOR_COLOR = Color.decode("0x993300"); //brown
	private static final Color MONSTER_COLOR = Color.RED;
	private static final int MONSTER_SIZE = 60;
	private static final Color DOORNOB_COLOR = Color.GRAY;
	private static final int DOOR_WIDTH = 50;
	private static final int DOOR_HEIGHT = 100;
	private static final int DOORNOB_SIZE = 8;
	
	//How much to offset x and y for this entire component
	private static final int xOffSet = 0;
	private static final int yOffSet = 0;
	
	private Tile tile;
	private Direction backDir;

	
	private Polygon leftDoor;
	private Polygon rightDoor;
	private Rectangle backWall;
	
	public ForegroundPanel()
	{
		this.setSize(FirstPersonView.OUTER_BOX, FirstPersonView.OUTER_BOX + FirstPersonView.BACKDIR_HEIGHT);
		
		Rectangle outer = new Rectangle(xOffSet,yOffSet, FirstPersonView.OUTER_BOX, FirstPersonView.OUTER_BOX);
		Rectangle inner = new Rectangle(xOffSet,yOffSet, FirstPersonView.INNER_BOX, FirstPersonView.INNER_BOX);
		inner.setLocation((int)outer.getCenterX() - FirstPersonView.INNER_BOX/2, (int)outer.getCenterY() - FirstPersonView.INNER_BOX/2);
		
		int doorPositioningOuter = (outer.width - inner.width)/2 + inner.width + DOOR_WIDTH/2;
		int doorPositioningInner = (outer.width - inner.width)/2 + inner.width - DOOR_WIDTH/2;
		
		Rectangle dpOuter = new Rectangle(xOffSet,yOffSet, doorPositioningOuter, doorPositioningOuter);
		dpOuter.setLocation((int)outer.getCenterX() - doorPositioningOuter/2, (int)outer.getCenterY() - doorPositioningOuter/2);
		
		Rectangle dpInner = new Rectangle(xOffSet,yOffSet, doorPositioningInner, doorPositioningInner);
		dpInner.setLocation((int)outer.getCenterX() - doorPositioningInner/2, (int)outer.getCenterY() - doorPositioningInner/2);
		
		leftDoor = new Polygon();
		//leftDoor
		leftDoor.addPoint((int) dpOuter.getMinX(), (int) dpOuter.getMaxY());//bot left of outer
		leftDoor.addPoint((int) dpInner.getMinX(), (int) dpInner.getMaxY());//bot left of outer//bot left of inner
		leftDoor.addPoint((int) dpInner.getMinX(), (int) dpInner.getMaxY()-DOOR_HEIGHT);//bot left of inner - height
		leftDoor.addPoint((int) dpOuter.getMinX(), (int) dpOuter.getMaxY()-DOOR_HEIGHT);//bot left of outer - height
	
		rightDoor = new Polygon();
		//rightDoor
		rightDoor.addPoint((int) dpInner.getMaxX(), (int) dpInner.getMaxY());//inner bot right 
		rightDoor.addPoint((int) dpOuter.getMaxX(), (int) dpOuter.getMaxY());//outer bot right
		rightDoor.addPoint((int) dpOuter.getMaxX(), (int) dpOuter.getMaxY()-DOOR_HEIGHT);//out bot right - height
		rightDoor.addPoint((int) dpInner.getMaxX(), (int) dpInner.getMaxY()-DOOR_HEIGHT);//in bot right - height
		
		backWall = inner;
	}
	
	public void draw(Tile t, Direction backDir) {
		this.tile = t;
		this.backDir = backDir;
		repaint();
	}

	/**
	 * Paints this component
	 * @param g The graphics on which to paint
	 */
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);

		drawLeft(g);
		drawRight(g);
		drawBack(g);
		
		if(tile.hasItems()) drawItemChest(g);
}
	
	private void drawRight(Graphics g) {
		Direction left = backDir.getRightDirection();
		Rectangle leftDoorBound = leftDoor.getBounds();
		if(tile.hasExit(left))
		{
			g.setColor(DOOR_COLOR);
			g.fillPolygon(leftDoor);
			
			g.setColor(DOORNOB_COLOR);
			g.fillOval((int)leftDoorBound.getMinX() + DOORNOB_SIZE/2, (int)leftDoorBound.getMinY() + DOOR_HEIGHT/2 + DOORNOB_SIZE, DOORNOB_SIZE, DOORNOB_SIZE);
		}
		else if(tile.hasCharacter(left))
		{
			g.setColor(MONSTER_COLOR);
			g.fillOval((int)leftDoorBound.getMaxX()- MONSTER_SIZE/2, (int)leftDoorBound.getCenterY() - MONSTER_SIZE, MONSTER_SIZE/2, MONSTER_SIZE);
		}
	}

	private void drawLeft(Graphics g) {
		Direction right = backDir.getLeftDirection();
		Rectangle rightDoorBound = rightDoor.getBounds();
		if(tile.hasExit(right))
		{
			g.setColor(DOOR_COLOR);
			g.fillPolygon(rightDoor);
			
			g.setColor(DOORNOB_COLOR);
			g.fillOval((int)rightDoorBound.getMinX() + DOORNOB_SIZE/2, (int)rightDoorBound.getMinY() + DOOR_HEIGHT/2 + DOORNOB_SIZE, DOORNOB_SIZE, DOORNOB_SIZE);
		}
		else if(tile.hasCharacter(right))
		{
			g.setColor(MONSTER_COLOR);
			g.fillOval((int)rightDoorBound.getMinX(), (int)rightDoorBound.getCenterY()- MONSTER_SIZE, MONSTER_SIZE/2, MONSTER_SIZE);
		}
	}

	private void drawBack(Graphics g) {
		Direction back = backDir.getOppositeDirection();
		if(tile.hasExit(back))
		{
			g.setColor(DOOR_COLOR);
			Rectangle door = new Rectangle(xOffSet, yOffSet, DOOR_WIDTH, DOOR_HEIGHT);
			door.setLocation((int)backWall.getCenterX()-DOOR_WIDTH/2, (int)backWall.getMaxY() - DOOR_HEIGHT);
			g.fillRect(door.x, door.y, door.width, door.height);
			
			g.setColor(DOORNOB_COLOR);
			g.fillOval(door.x + DOORNOB_SIZE, (int)door.getCenterY(), DOORNOB_SIZE, DOORNOB_SIZE);
		}
		else if(tile.hasCharacter(back))
		{
			g.setColor(MONSTER_COLOR);
			g.fillOval((int)backWall.getCenterX()-MONSTER_SIZE/2, (int)(backWall.getMinY()+ MONSTER_SIZE*1.5), MONSTER_SIZE, MONSTER_SIZE);
		}
	}

	private void drawItemChest(Graphics g)
	{
		//draw item chest
		final int ITEM_HEIGHT_OFFSET = 0;
		final int ITEM_WIDTH_OFFSET = 0;
		final int DECORATION_WIDTH = 5;
		final int DECORATION_OFFSET1 = 15;
		final int DECORATION_OFFSET2 = 60;
		
		g.setColor(ITEM_COLOR);
		g.fill3DRect(ITEM_AREA_RECT.x + ITEM_WIDTH_OFFSET, ITEM_AREA_RECT.y + ITEM_HEIGHT_OFFSET, 
					 ITEM_AREA_RECT.width - 2*ITEM_WIDTH_OFFSET, ITEM_AREA_RECT.height - 2*ITEM_HEIGHT_OFFSET, true);
		g.setColor(ITEM_DECORATION_COLOR);
		g.fill3DRect(ITEM_AREA_RECT.x + DECORATION_OFFSET1, ITEM_AREA_RECT.y + ITEM_HEIGHT_OFFSET, DECORATION_WIDTH, ITEM_AREA_RECT.height - 2*ITEM_HEIGHT_OFFSET, true);
		g.fill3DRect(ITEM_AREA_RECT.x + DECORATION_OFFSET2, ITEM_AREA_RECT.y + ITEM_HEIGHT_OFFSET, DECORATION_WIDTH, ITEM_AREA_RECT.height - 2*ITEM_HEIGHT_OFFSET, true);
	}

	public boolean isItemContains(Point p) {
		return (ITEM_AREA_RECT.contains(p) && tile.hasItems());
	}
}
