package jUnitTests;

import static org.junit.Assert.*;
import gameCore.*;

import org.junit.*;

/**
 * Tests the default state of a Tile upon its creation. Inheriting classes should modify a portion of
 * the state and test that new state.
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
public class TileDefaultTest extends TileTest {
	
	/**
	 * Test method for {@link gameCore.Tile#getCharacter(gameCore.Direction)}.
	 */
	@Test
	public void testGetCharacterDirection() {
		for (Direction dir : Direction.values()){
			try {
				testTile.getCharacter(dir);
				fail("IllegalArgumentException was expected");
			}
			catch (IllegalArgumentException e){
				//this exception was expected
			}
		}
	}
	
	/**
	 * Test method for {@link gameCore.Tile#getInventory(gameCore.Direction)}.
	 */
	@Test
	public void testGetInventoryDirection() {
		for (Direction dir : Direction.values()){
			try {
				testTile.getInventory(dir);
				fail("IllegalArgumentException was expected");
			}
			catch (IllegalArgumentException e){
				//this exception was expected
			}
		}
	}
	
	/**
	 * Test method for {@link gameCore.Tile#getLocation()}.
	 */
	@Test
	public void testGetLocation() {
		assertEquals(testLocation, testTile.getLocation());
	}
	
	/**
	 * Test method for {@link gameCore.Tile#setVisited()}.
	 */
	@Test
	public void testSetVisited() {
		testTile.setVisited();
		assertTrue(testTile.isVisited());
	}
	
	/**
	 * Test method for {@link gameCore.Tile#isCrossable(gameCore.Direction)}.
	 */
	@Test
	public void testIsCrossable() {
		for (Direction dir : Direction.values()){
			assertFalse(testTile.isCrossable(dir));
		}
	}
	
	/**
	 * Test method for {@link gameCore.Tile#canMove(gameCore.Direction)}.
	 */
	@Test
	public void testCanMove() {
		for (Direction dir : Direction.values()){
			assertFalse(testTile.canMove(dir));
		}
	}
	
	/**
	 * Test method for {@link gameCore.Tile#getExitKey(gameCore.Direction)}.
	 */
	@Test
	public void testGetExitKey() {
		for (Direction dir : Direction.values()){
			try {
				testTile.getExitKey(dir);
				fail("IllegalArgumentException was expected");
			}
			catch (IllegalArgumentException e){
				//this exception was expected
			}
		}
	}
	
	/**
	 * Test method for {@link gameCore.Tile#moveCharacter(gameCore.Direction)}.
	 */
	@Test
	public void testMoveCharacter() {
		for (Direction dir : Direction.values()){
			try {
				testTile.moveCharacter(dir);
				fail("IllegalArgumentException was expected");
			}
			catch (IllegalArgumentException e){
				//this exception was expected
			}
		}
	}
	
	/**
	 * Test method for {@link gameCore.Tile#hasCharacter(gameCore.Direction)}.
	 */
	@Test
	public void testHasCharacterDirection() {
		for (Direction dir : Direction.values()){
			assertFalse(testTile.hasCharacter(dir));
		}
	}
	
	/**
	 * Test method for {@link gameCore.Tile#isEmpty(gameCore.Direction)}.
	 */
	@Test
	public void testIsEmptyDirection() {
		for (Direction dir : Direction.values()){
			assertFalse(testTile.isEmpty(dir));
		}
	}
	
	/**
	 * Test method for {@link gameCore.Tile#hasExit(gameCore.Direction)}.
	 */
	@Test
	public void testHasExit() {
		for (Direction dir : Direction.values()){
			assertFalse(testTile.hasExit(dir));
		}
	}
	
	/**
	 * Test method for {@link gameCore.Tile#isVisited()}.
	 */
	@Test
	public void testIsVisited() {
		assertFalse(testTile.isVisited());
	}
	
	/**
	 * Test method for {@link gameCore.Tile#hasDirection(gameCore.Direction)}.
	 */
	@Test
	public void testHasDirection() {
		for (Direction dir : Direction.values()){
			assertFalse(testTile.hasDirection(dir));
		}
	}
	
	/**
	 * Test method for {@link gameCore.Tile#getAllDirections()}.
	 */
	@Test
	public void testGetAllDirections() {
		assertTrue(testTile.getAllDirections().isEmpty());
	}
	
	/**
	 * Test method for {@link gameCore.Tile#getEdgeDirection(gameCore.Edge)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetEdgeDirection() {
		testTile.getEdgeDirection(new Edge(testTile, null, false));
	}
	
	/**
	 * Test method for {@link gameCore.Tile#getInventory()}.
	 */
	@Test
	public void testGetInventory() {
		assertTrue(testTile.getInventory().isEmpty());
	}
	
	/**
	 * Test method for {@link gameCore.Tile#getCharacter()}.
	 */
	@Test
	public void testGetCharacter() {
		assertNull(testTile.getCharacter());
	}
	
	/**
	 * Test method for {@link gameCore.Tile#removeCharacter()}.
	 */
	@Test
	public void testRemoveCharacter() {
		assertNull(testTile.removeCharacter());
	}
	
	/**
	 * Test method for {@link gameCore.Tile#removeItem(gameCore.Item)}.
	 */
	@Test
	public void testRemoveItem() {
		testTile.removeItem(testItem);
		//nothing should happen, this test passes if no exception is thrown
	}
	
	/**
	 * Test method for {@link gameCore.Tile#hasCharacter()}.
	 */
	@Test
	public void testHasCharacter() {
		assertFalse(testTile.hasCharacter());
	}
	
	/**
	 * Test method for {@link gameCore.Tile#hasItems()}.
	 */
	@Test
	public void testHasItems() {
		assertFalse(testTile.hasItems());
	}
	
	/**
	 * Test method for {@link gameCore.Tile#isEmpty()}.
	 */
	@Test
	public void testIsEmpty() {
		assertTrue(testTile.isEmpty());
	}
	
	/**
	 * Test method for {@link gameCore.Tile#getEdgeDirection(gameCore.Edge)}.
	 */
	@Test
	public void testSetEdge() {
		testTile.setEdge(Direction.NORTH, new Edge(testTile, null, false));
		assertTrue(testTile.hasDirection(Direction.NORTH));
		
		//a second attempt should fail
		try {
			testTile.setEdge(Direction.NORTH, new Edge(testTile, null, false));
			fail("IllegalArgumentException expected");
		}
		catch (IllegalArgumentException e){
			//exception expected
		}
	}
}
