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

	private Tile testTile1;
	private Point testPoint1;
	private Direction direction1;
	private Tile testTile2;
	private Point testPoint2;
	private Direction direction2;
	private Character testCharacter;
	protected Edge testEdge;
	protected Edge testEdgeUncrossable;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testPoint1 = new Point(1,3);
		testPoint2 = new Point(2,-1);
		testTile1 = new Tile(testPoint1, new Room());
		testCharacter = new Character("Bob", 10, 10, testTile1);
		testTile2 = new Tile(testPoint2, new Room());
		testEdge = new Edge(testTile1, testTile2, true, Direction.NORTH, Direction.SOUTH);
		testEdgeUncrossable = new Edge(testTile1, testTile2, false, Direction.EAST, Direction.WEST);
	}

	/**
	 * Test method for {@link gameCore.Edge#canCross(gameCore.Character)}.
	 */
	@Test
	public void testCanCross() {
		assertTrue(testEdge.canCross(testCharacter));
		assertFalse(testEdgeUncrossable.canCross(testCharacter));
	}
	
	/**
	 * Test method for {@link gameCore.Edge#isCrossableByDefault()}.
	 */
	@Test
	public void testIsCrossableByDefault() {
		assertTrue(testEdge.isCrossableByDefault());
		assertFalse(testEdgeUncrossable.isCrossableByDefault());
	}

	/**
	 * Test method for {@link gameCore.Edge#cross(gameCore.Tile, gameCore.Character)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCrossUncrossableEdge() {
		testEdgeUncrossable.cross(testTile1, testCharacter);
	}
	
	/**
	 * Test method for {@link gameCore.Edge#cross(gameCore.Tile, gameCore.Character)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCrossCrossableEdge() {
		//A successful cross
		testEdgeUncrossable.cross(testTile1, testCharacter);
		assertTrue(testTile2.getCharacter().equals(testCharacter));
		assertFalse(testTile1.hasCharacter());
		
		//Trying to cross when there is no character on the tile
		testEdgeUncrossable.cross(testTile1, testCharacter);
	}

	/**
	 * Test method for {@link gameCore.Edge#getOtherTile(gameCore.Tile)}.
	 */
	@Test
	public void testGetOtherTile() {
		assertEquals(testTile2, testEdge.getOtherTile(testTile1));
		assertEquals(testTile1, testEdgeUncrossable.getOtherTile(testTile2));
	}

	/**
	 * Test method for {@link gameCore.Edge#getDirection1()}.
	 */
	@Test
	public void testGetDirection1() {
		assertEquals(Direction.NORTH, testEdge.getDirection1());
	}

	/**
	 * Test method for {@link gameCore.Edge#getDirection2()}.
	 */
	@Test
	public void testGetDirection2() {
		assertEquals(Direction.SOUTH, testEdge.getDirection2());
	}

	/**
	 * Test method for {@link gameCore.Edge#getLocation1()}.
	 */
	@Test
	public void testGetLocation1() {
		assertEquals(testPoint1, testEdge.getLocation1());
		assertEquals(testPoint1, testEdgeUncrossable.getLocation1());
	}

	/**
	 * Test method for {@link gameCore.Edge#getLocation2()}.
	 */
	@Test
	public void testGetLocation2() {
		assertEquals(testPoint2, testEdge.getLocation2());
		assertEquals(testPoint2, testEdgeUncrossable.getLocation2());
	}

	/**
	 * Test method for {@link gameCore.Edge#isVisited()}.
	 */
	@Test
	public void testIsVisited() {
		assertEquals(testTile1.isVisited() || testTile2.isVisited(), testEdge.isVisited());
	}

}
