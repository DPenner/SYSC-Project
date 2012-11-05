/**
 * 
 */
package jUnitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import gameCore.*;
import gameCore.Character;
import java.awt.Point;

/**
 * @author DarrellPenner
 *
 */
public class EdgeTest {

	protected Tile testTile1;
	private Point testPoint1;
	protected Tile testTile2;
	private Point testPoint2;
	protected Character testCharacter;
	protected Edge testEdgeCrossable;
	protected Edge testEdgeUncrossable;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testPoint1 = new Point(1,3);
		testPoint2 = new Point(2,-1);
		
		initializeTestTiles();
		initializeCharacter();
		initializeTestEdges();
	}
	
	protected void initializeTestTiles(){
		testTile1 = new Tile(testPoint1, new Room());
		testTile2 = new Tile(testPoint2, new Room());
	}
	
	protected void initializeTestEdges(){
		testEdgeCrossable = new Edge(testTile1, testTile2, true, Direction.NORTH, Direction.SOUTH);
		testEdgeUncrossable = new Edge(testTile1, testTile2, false, Direction.EAST, Direction.WEST);
	}

	protected void initializeCharacter(){
		testCharacter = new Character("Bob", 10, 10, testTile1);
	}
	/**
	 * Test method for {@link gameCore.Edge#canCross(gameCore.Character)}.
	 */
	@Test
	public void testCanCross() {
		assertTrue(testEdgeCrossable.canCross(testCharacter));
		assertFalse(testEdgeUncrossable.canCross(testCharacter));
	}
	
	/**
	 * Test method for {@link gameCore.Edge#isCrossableByDefault()}.
	 */
	@Test
	public void testIsCrossableByDefault() {
		assertTrue(testEdgeCrossable.isCrossableByDefault());
		assertFalse(testEdgeUncrossable.isCrossableByDefault());
	}

	/**
	 * Test method for {@link gameCore.Edge#cross(gameCore.Tile, gameCore.Character)}.
	 */
	@Test
	public void testCrossUncrossableEdge() {
		try {
			testEdgeUncrossable.cross(testTile1, testCharacter);
			fail("IllegalArgumentException was expected");
		}
		catch (IllegalArgumentException e){
			//exception was expection
		}
	}
	
	/**
	 * Test method for {@link gameCore.Edge#cross(gameCore.Tile, gameCore.Character)}.
	 */
	@Test
	public void testCrossCrossableEdge() {
		//A successful cross
		testEdgeCrossable.cross(testTile1, testCharacter);
		assertEquals(testCharacter, testTile2.getCharacter());
		assertFalse(testTile1.hasCharacter());
	}
	
	/**
	 * Test method for {@link gameCore.Edge#cross(gameCore.Tile, gameCore.Character)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCrossIllegal() {
		//A successful cross
		testEdgeCrossable.cross(testTile2, testCharacter);
	}

	/**
	 * Test method for {@link gameCore.Edge#getOtherTile(gameCore.Tile)}.
	 */
	@Test
	public void testGetOtherTile() {
		assertEquals(testTile2, testEdgeCrossable.getOtherTile(testTile1));
		assertEquals(testTile1, testEdgeUncrossable.getOtherTile(testTile2));
	}

	/**
	 * Test method for {@link gameCore.Edge#getDirection1()}.
	 */
	@Test
	public void testGetDirection1() {
		assertEquals(Direction.NORTH, testEdgeCrossable.getDirection1());
	}

	/**
	 * Test method for {@link gameCore.Edge#getDirection2()}.
	 */
	@Test
	public void testGetDirection2() {
		assertEquals(Direction.SOUTH, testEdgeCrossable.getDirection2());
	}

	/**
	 * Test method for {@link gameCore.Edge#getLocation1()}.
	 */
	@Test
	public void testGetLocation1() {
		assertEquals(testPoint1, testEdgeCrossable.getLocation1());
		assertEquals(testPoint1, testEdgeUncrossable.getLocation1());
	}

	/**
	 * Test method for {@link gameCore.Edge#getLocation2()}.
	 */
	@Test
	public void testGetLocation2() {
		assertEquals(testPoint2, testEdgeCrossable.getLocation2());
		assertEquals(testPoint2, testEdgeUncrossable.getLocation2());
	}

	/**
	 * Test method for {@link gameCore.Edge#isVisited()}.
	 */
	@Test
	public void testIsVisited() {
		assertEquals(testTile1.isVisited() || testTile2.isVisited(), testEdgeCrossable.isVisited());
	}

}
