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

		
	//How much to offset x and y for this entire component
	private static final int xOffSet = 0;
	private static final int yOffSet = 0;
	
	private Tile tile;
	private Direction backDir; //the direction player came from
	private Direction forwardDir;
	
	private Polygon ceiling;
	private Polygon floor;
	private Polygon leftWall;
	private Polygon rightWall;
	private Rectangle backWall;
	private Rectangle backDirRect;
	
	public BackgroundPanel()
	{
		this.setSize(FirstPersonView.OUTER_BOX, FirstPersonView.OUTER_BOX + FirstPersonView.BACKDIR_HEIGHT);
		
		//setup shapes
		
		Rectangle outer = new Rectangle(xOffSet,yOffSet, FirstPersonView.OUTER_BOX, FirstPersonView.OUTER_BOX);
		Rectangle inner = new Rectangle(xOffSet,yOffSet, FirstPersonView.INNER_BOX, FirstPersonView.INNER_BOX);
		inner.setLocation((int)outer.getCenterX() - FirstPersonView.INNER_BOX/2, (int)outer.getCenterY() - FirstPersonView.INNER_BOX/2);
		
		/*
		 * NOTE: order of adding points matter for the shape of the polygon
		 * should go: counter clockwise or clockwise but going diagonally gives an hour glass shape!
		 */
		ceiling = new Polygon();
		ceiling.addPoint((int)outer.getMaxX(), outer.y); //outer top right
		ceiling.addPoint((int)outer.getMinX(), (int)outer.getMinY()); //outer top left
		ceiling.addPoint(inner.x, inner.y); //inner top left
		ceiling.addPoint(inner.x + inner.width, inner.y); //inner top right
		
		floor = new Polygon();
		floor.addPoint(outer.x + outer.width, outer.y + outer.height); //outer bot right
		floor.addPoint(outer.x, outer.y + outer.height); //outer bot left
		floor.addPoint(inner.x, inner.y + inner.height); //inner bot left
		floor.addPoint(inner.x + inner.width, inner.y + inner.height); //inner bot right
		
		leftWall = new Polygon();
		leftWall.addPoint(outer.x, outer.y); //outer top left
		leftWall.addPoint(outer.x, outer.y + outer.height); //outer bot left
		leftWall.addPoint(inner.x, inner.y + inner.height); //inner bot left
		leftWall.addPoint(inner.x, inner.y); //inner top left
		
		rightWall = new Polygon();
		rightWall.addPoint(inner.x + inner.width, inner.y); //inner top right
		rightWall.addPoint(outer.x + outer.width, outer.y); //outer top right
		rightWall.addPoint(outer.x + outer.width, outer.y + outer.height); //outer bot right
		rightWall.addPoint(inner.x + inner.width, inner.y + inner.height); //inner bot right
		
		backWall = inner;
		
		backDirRect = new Rectangle((int)outer.getMinX(), (int)outer.getMaxY(), FirstPersonView.OUTER_BOX, FirstPersonView.BACKDIR_HEIGHT);
	}
	public void draw(Tile tile, Direction backDir) {
		this.tile = tile;
		this.backDir = backDir;
		forwardDir = backDir.getOppositeDirection();
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
		
		Direction wallDir = backDir.getRightDirection();
		drawWall(g, tile, wallDir, leftWall);
		
		wallDir = backDir.getLeftDirection();
		drawWall(g, tile, wallDir, rightWall);
		
		drawBackWall(g, tile, backDir);
		
		drawUnchanging(g);
	}
	
	private void drawWall(Graphics g, Tile tile, Direction dir, Polygon wall) {
		if(tile.isCrossable(dir)) g.setColor(OPENING_COLOR);
		else g.setColor(WALL_COLOR);
		
		g.fillPolygon(wall);
	}
	
	private void drawBackWall(Graphics g, Tile tile, Direction backDir)
	{
		Direction backWallDir = backDir.getOppositeDirection();
		if(tile.isCrossable(backWallDir)) g.setColor(OPENING_COLOR);
		else g.setColor(WALL_COLOR);
		
		g.fillRect(backWall.x, backWall.y, backWall.width, backWall.height);
	}

	public void drawUnchanging(Graphics g)
	{
		//draw lines between the walls, floor, and ceiling

		g.setColor(CEILING_COLOR);
		g.fillPolygon(ceiling);
		
		g.setColor(FLOOR_COLOR);
		g.fillPolygon(floor);
		
		//Area for pressing to go back
		g.setColor(WALL_COLOR);
		g.fillRect(0, FirstPersonView.OUTER_BOX, FirstPersonView.OUTER_BOX, FirstPersonView.BACKDIR_HEIGHT);
		
		g.setColor(Color.BLUE);
		Polygon backDirTriangle = new Polygon();
		backDirTriangle.addPoint(FirstPersonView.OUTER_BOX/2 - 10, FirstPersonView.OUTER_BOX + 2);
		backDirTriangle.addPoint(FirstPersonView.OUTER_BOX/2 + 10, FirstPersonView.OUTER_BOX + 2);
		backDirTriangle.addPoint(FirstPersonView.OUTER_BOX/2, FirstPersonView.OUTER_BOX + 2 + 15);
		g.fillPolygon(backDirTriangle);
	}
	
	public Direction directionContaining(Point p){
		if(backWall.contains(p)) return forwardDir;
		if(backDirRect.contains(p)) return backDir;
		if(rightWall.contains(p)) return forwardDir.getRightDirection();
		if(leftWall.contains(p)) return forwardDir.getLeftDirection();
		return null;
	}
	public boolean isFloorContains(Point p) {
		return floor.contains(p);
	}
}
