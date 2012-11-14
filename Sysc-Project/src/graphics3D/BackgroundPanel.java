package graphics3D;

import gameCore.Direction;
import gameCore.Player;
import gameCore.Tile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BackgroundPanel extends JPanel{

	private static final Color WALL_COLOR = Color.DARK_GRAY;
	private static final Color BACKGROUND_COLOR = Color.BLACK;
	private static final Color CEILING_COLOR = Color.LIGHT_GRAY; 
	private static final Color OPENING_COLOR = Color.BLUE;
	private static final Color FLOOR_COLOR = Color.decode("0x330000"); //dark brown
	private static final Color DOOR_COLOR = Color.decode("0x993300"); //brown
	private static final Color MONSTER_COLOR = Color.RED;
	private static final Color DOORNOB_COLOR = Color.GRAY;
	
	private enum WALLTYPE {OPEN, DOOR, WALL};
	
	//How much to offset x and y for this entire component
	private static final int xOffSet = 0;
	private static final int yOffSet = 0;
	
	private static final int DOOR_WIDTH = 6;
	private static final int DOOR_HEIGHT = 20;
	private static final int DOORNOB_SIZE = 2;
	
	private Tile tile;
	private Direction backDir;
	
	private Polygon ceiling;
	private Polygon floor;
	private Polygon leftWall;
	private Polygon rightWall;
	private Rectangle backWall;
	
	public BackgroundPanel()
	{
		this.setSize(FirstPersonView.OUTER_BOX, FirstPersonView.OUTER_BOX + FirstPersonView.BACKDIR_HEIGHT);
		
		//setup shapes
		
		Rectangle outer = new Rectangle(xOffSet,yOffSet, FirstPersonView.OUTER_BOX, FirstPersonView.OUTER_BOX);
		Rectangle inner = new Rectangle(xOffSet,yOffSet, FirstPersonView.INNER_BOX, FirstPersonView.INNER_BOX);
		inner.setLocation((int)outer.getCenterX() - FirstPersonView.INNER_BOX/2, (int)outer.getCenterY() - FirstPersonView.INNER_BOX/2);
		
		ceiling.addPoint((int)outer.getMinX(), (int)outer.getMinY()); //outer top left
		ceiling.addPoint((int)outer.getMaxX(), outer.y); //outer top right
		ceiling.addPoint(inner.x, inner.y); //inner top left
		ceiling.addPoint(inner.x + inner.width, inner.y); //inner top right
		
		floor.addPoint(outer.x + outer.width, outer.y + outer.height); //outer bot right
		floor.addPoint(outer.x, outer.y + outer.height); //outer bot left
		floor.addPoint(inner.x, inner.y + inner.height); //inner bot left
		floor.addPoint(inner.x + inner.width, inner.y + inner.height); //inner bot right
		
		leftWall.addPoint(outer.x, outer.y); //outer top left
		leftWall.addPoint(outer.x, outer.y + outer.height); //outer bot left
		leftWall.addPoint(inner.x, inner.y + inner.height); //inner bot left
		leftWall.addPoint(inner.x, inner.y); //inner top left
		
		rightWall.addPoint(outer.x + outer.width, outer.y); //outer top right
		rightWall.addPoint(inner.x + inner.width, inner.y); //inner top right
		rightWall.addPoint(outer.x + outer.width, outer.y + outer.height); //outer bot right
		rightWall.addPoint(inner.x + inner.width, inner.y + inner.height); //inner bot right
		
		backWall = inner;
	}
	public void draw(Tile tile, Direction backDir) {
		this.tile = tile;
		this.backDir = backDir;
		repaint();
	}


	//-------------Tile drawing------------//
	//These methods draws parts of the tiles
	/**
	 * Paints this component
	 * @param g The graphics on which to paint
	 */
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		this.setBackground(BACKGROUND_COLOR);
		
		drawLeftWall(g, tile, backDir);
		drawRightWall(g, tile, backDir);
		drawBackWall(g, tile, backDir);
		drawUnchanging(g);
	}
	
	private void drawBackWall(Graphics g, Tile tile, Direction backDir) {
		Direction backWallDir = backDir.getOppositeDirection();
		switch(getWallType(tile, backWallDir))
		{
		case WALL: 
			g.setColor(WALL_COLOR);
			g.fillRect(backWall.x, backWall.y, backWall.width, backWall.height);
			return;
		case DOOR:
			g.setColor(WALL_COLOR);
			g.fillRect(backWall.x, backWall.y, backWall.width, backWall.height);
			
			
			g.setColor(DOOR_COLOR);
			Rectangle door = new Rectangle(xOffSet, yOffSet, DOOR_WIDTH, DOOR_HEIGHT);
			door.setLocation((int)backWall.getCenterX()-DOOR_WIDTH/2, (int)backWall.getMaxY() - DOOR_HEIGHT);
			g.fillRect(door.x, door.y, door.width, door.height);
			
			g.setColor(DOORNOB_COLOR);
			g.fillOval(door.x + DOORNOB_SIZE, (int)door.getCenterY(), DOORNOB_SIZE, DOORNOB_SIZE);
			return;
		case OPEN:
			g.setColor(OPENING_COLOR);
			g.fillRect(backWall.x, backWall.y, backWall.width, backWall.height);
			return;
		}
	}
	private void drawRightWall(Graphics g, Tile tile, Direction backDir) {
		// TODO Auto-generated method stub
		
	}
	private void drawLeftWall(Graphics g, Tile tile, Direction backDir) {
		// TODO Auto-generated method stub
		
	}
	public void drawUnchanging(Graphics g)
	{
		g.setColor(CEILING_COLOR);
		g.fillPolygon(ceiling);
		
		g.setColor(FLOOR_COLOR);
		g.fillPolygon(floor);
		
		g.setColor(WALL_COLOR);
		g.fillRect(0, FirstPersonView.OUTER_BOX, FirstPersonView.OUTER_BOX, FirstPersonView.BACKDIR_HEIGHT);
		
		g.setColor(Color.BLUE);
		Polygon backDirTriangle = new Polygon();
		backDirTriangle.addPoint(FirstPersonView.OUTER_BOX/2 - 10, FirstPersonView.OUTER_BOX + 2);
		backDirTriangle.addPoint(FirstPersonView.OUTER_BOX/2 + 10, FirstPersonView.OUTER_BOX + 2);
		backDirTriangle.addPoint(FirstPersonView.OUTER_BOX/2, FirstPersonView.OUTER_BOX + 2 + 15);
		g.fillPolygon(backDirTriangle);
	}	
	
	private WALLTYPE getWallType(Tile t, Direction dir)
	{
		if(t.hasExit(dir)) return WALLTYPE.DOOR;
		if(t.isCrossable(dir)) return WALLTYPE.OPEN;
		return WALLTYPE.WALL;
	}
}
