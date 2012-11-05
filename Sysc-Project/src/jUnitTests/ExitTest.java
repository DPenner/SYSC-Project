/**
 * 
 */
package jUnitTests;

import static org.junit.Assert.*;

import org.junit.Test;
import gameCore.*;


/**
 * @author DarrellPenner
 *
 */
public class ExitTest extends EdgeTest {

	Item testKey;
	@Override
	public void setUp() throws Exception{
		testKey = new Item("Key of certain doom", 10);
		
		super.setUp();
		
		testCharacter.addItem(testKey);
	}
	
	@Override
	public void initializeTestEdges(){
		testEdgeCrossable = new Exit(testTile1, testTile2, false, Direction.NORTH, Direction.SOUTH, testKey);
		testEdgeUncrossable = new Exit(testTile1, testTile2, true, Direction.EAST, Direction.WEST, testKey);
	}
	
	private Exit castToExit(Edge edge){
		return (Exit) edge;
	}

	/**
	 * Test method for {@link gameCore.Exit#canCross(gameCore.Character)}.
	 */
	@Override
	public void testCanCross() {
		//note that crossing without the key is already tested in superclass
		assertTrue(testEdgeCrossable.canCross(testCharacter));
		assertTrue(testEdgeUncrossable.canCross(testCharacter));
	}

	/**
	 * Test method for {@link gameCore.Exit#cross(gameCore.Tile, gameCore.Character)}.
	 */
	@Override
	public void testCrossUncrossableEdge() {
		//default uncrossable, however this character has a key
		testEdgeUncrossable.cross(testTile1, testCharacter);
		assertEquals(testCharacter, testTile2.getCharacter());
		assertFalse(testTile1.hasCharacter());
		assertTrue(testEdgeUncrossable.isCrossableByDefault()); //check that the exit was actually unlocked
	}
	
	/**
	 * Test method for {@link gameCore.Exit#cross(gameCore.Tile, gameCore.Character)}.
	 */
	@Override
	public void testCrossCrossableEdge() {
		testEdgeCrossable.cross(testTile1, testCharacter);
		assertEquals(testCharacter, testTile2.getCharacter());
		assertFalse(testTile1.hasCharacter());
	}
	/**
	 * Test method for {@link gameCore.Exit#isLocked()}.
	 */
	@Test
	public void testIsLocked() {
		assertFalse(castToExit(testEdgeCrossable).isLocked());
		assertTrue(castToExit(testEdgeUncrossable).isLocked());
	}

	/**
	 * Test method for {@link gameCore.Exit#unlock(gameCore.Item)}.
	 */
	@Test
	public void testUnlock() {
		castToExit(testEdgeCrossable).unlock(testKey);
		castToExit(testEdgeUncrossable).unlock(testKey);
		assertTrue(testEdgeCrossable.isCrossableByDefault());
		assertTrue(testEdgeUncrossable.isCrossableByDefault());
	}

	/**
	 * Test method for {@link gameCore.Exit#getKeyName()}.
	 */
	@Test
	public void testGetKeyName() {
		assertEquals(testKey.getName(), ((Exit) testEdgeCrossable).getKeyName());
		assertEquals(testKey.getName(), ((Exit) testEdgeUncrossable).getKeyName());
	}

}
