/**
 * 
 */
package jUnitTests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;
import gameCore.*;

/**
 * Tests if Tiles are properly connected. This is important since
 * Tiles form a graph data structure - a graph data structure with one
 * node is fairly useless.
 * 
 * @author Group D
 * @author Main author: Darrell Penner
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
public class TileConnectedTest extends TileTest {

	private Exit testExit;
	private Edge testEdge;
	private Tile openTile;
	private Tile exitTile;
	private Direction exitDirection;
	private Direction openDirection;
	private Direction unusedDirection; //unused relative to the main test Tile
	
	/**
	 * Sets up two tiles adjacent to the main testTile, one is an Exit
	 * which the character does not have the key to, the other is a standard
	 * crossable edge. The main testTile is initialized with a character.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		exitDirection = Direction.NORTH;
		openDirection = Direction.EAST;
		unusedDirection = Direction.WEST;
		createCharacterOnTestTile();
		exitTile = new Tile(new Point(1, 5), new Room());
		openTile = new Tile(new Point(-3, -1), new Room());
		testExit = new Exit(testTile, exitTile, true, exitDirection, Direction.SOUTH, testItem);
		testEdge = new Edge(testTile, openTile, true, openDirection, Direction.WEST);
	}

	/**
	 * Test method for {@link gameCore.Tile#getInventory(gameCore.Direction)}.
	 */
	@Test
	public void testGetInventoryDirectionValid() {
		assertEquals(openTile.getInventory(), testTile.getInventory(openDirection));
	}
	
	/**
	 * Test method for {@link gameCore.Tile#getInventory(gameCore.Direction)}.
	 */
	@Test
	public void testGetInventoryDirectionInvalid() {
		try {
			testTile.getInventory(exitDirection);
			fail("IllegalArgumentException was expected");
		}
		catch (IllegalArgumentException e){
			//exception was expected
		}
	}

	/**
	 * Test method for {@link gameCore.Tile#getCharacter(gameCore.Direction)}.
	 */
	@Test
	public void testGetCharacterDirectionValid() {
		assertEquals(openTile.getCharacter(), testTile.getCharacter(openDirection));
	}
	
	/**
	 * Test method for {@link gameCore.Tile#getCharacter(gameCore.Direction)}.
	 */
	@Test
	public void testGetCharacterDirectionInvalid() {
		try {
			testTile.getCharacter(exitDirection);
			fail("IllegalArgumentException was expected");
		}
		catch (IllegalArgumentException e){
			//exception was expected
		}
	}

	/**
	 * Test method for {@link gameCore.Tile#getExitKey(gameCore.Direction)}.
	 */
	@Test
	public void testGetExitKeyValid() {
		assertEquals(testItem.getName(), testTile.getExitKey(exitDirection));
	}
	
	/**
	 * Test method for {@link gameCore.Tile#getExitKey(gameCore.Direction)}.
	 */
	@Test
	public void testGetExitKeyInvalid() {
		try {
			testTile.getExitKey(openDirection);
			fail("IllegalArgumentException was expected");
		}
		catch (IllegalArgumentException e){
			//exception was expected
		}
	}

	/**
	 * Test method for {@link gameCore.Tile#isCrossable(gameCore.Direction)}.
	 */
	@Test
	public void testIsCrossable() {
		assertTrue(testTile.isCrossable(openDirection));
		assertFalse(testTile.isCrossable(exitDirection));
		assertFalse(testTile.isCrossable(unusedDirection));
	}

	/**
	 * Test method for {@link gameCore.Tile#canMove(gameCore.Direction)}.
	 */
	@Test
	public void testCanMove() {
		assertTrue(testTile.canMove(openDirection));
		assertFalse(testTile.canMove(exitDirection));
		assertFalse(testTile.canMove(unusedDirection));
	}

	/**
	 * Test method for {@link gameCore.Tile#moveCharacter(gameCore.Direction)}.
	 */
	@Test
	public void testMoveCharacterValid() {
		testTile.moveCharacter(openDirection);
		assertFalse(testTile.hasCharacter());
		assertEquals(testCharacter, openTile.getCharacter());
	}
	
	/**
	 * Test method for {@link gameCore.Tile#moveCharacter(gameCore.Direction)}.
	 */
	@Test
	public void testMoveCharacterInvalid() {
		try {
			testTile.moveCharacter(exitDirection);
			fail("IllegalArgumentException expected");
		}
		catch (IllegalArgumentException e){
			//exception expected
		}
	}

	/**
	 * Test method for {@link gameCore.Tile#hasCharacter(gameCore.Direction)}.
	 */
	@Test
	public void testHasCharacterDirection() {
		assertFalse(testTile.hasCharacter(openDirection));
		assertFalse(testTile.hasCharacter(exitDirection));
		assertFalse(testTile.hasCharacter(unusedDirection));
	}

	/**
	 * Test method for {@link gameCore.Tile#isEmpty(gameCore.Direction)}.
	 */
	@Test
	public void testIsEmptyDirection() {
		assertTrue(testTile.isEmpty(openDirection));
		assertFalse(testTile.isEmpty(exitDirection));
	}

	/**
	 * Test method for {@link gameCore.Tile#hasExit(gameCore.Direction)}.
	 */
	@Test
	public void testHasExit() {
		assertFalse(testTile.hasExit(openDirection));
		assertTrue(testTile.hasExit(exitDirection));
		assertFalse(testTile.hasExit(unusedDirection));
	}

	/**
	 * Test method for {@link gameCore.Tile#hasDirection(gameCore.Direction)}.
	 */
	@Test
	public void testHasDirection() {
		assertTrue(testTile.hasDirection(openDirection));
		assertTrue(testTile.hasDirection(exitDirection));
		assertFalse(testTile.hasDirection(unusedDirection));
	}

	/**
	 * Test method for {@link gameCore.Tile#getEdgeDirection(gameCore.Edge)}.
	 */
	@Test
	public void testGetEdgeDirection() {
		assertEquals(openDirection, testTile.getEdgeDirection(testEdge));
		assertEquals(exitDirection, testTile.getEdgeDirection(testExit));
		try {
			testTile.getEdgeDirection(null);
			fail("IllegalArgumentException expected");
		}
		catch (IllegalArgumentException e){
			//exception expected
		}
	}

	/**
	 * Test method for {@link gameCore.Tile#getAllDirections()}.
	 */
	@Test
	public void testGetAllDirections() {
		assertTrue(testTile.getAllDirections().contains(openDirection));
		assertTrue(testTile.getAllDirections().contains(exitDirection));
		assertFalse(testTile.getAllDirections().contains(unusedDirection));
	}
}
