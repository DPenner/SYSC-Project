package jUnitTests;

import static org.junit.Assert.*;

/**
 * Tests a tile which contains a Character and an Item
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
public class TileWithCharacterAndItemsTest extends TileDefaultTest{
	
	@Override 
	public void setUp() throws Exception {
		super.setUp();
		createCharacterOnTestTile();
		testTile.addItem(testItem);
	}
	
	/**
	 * Test method for {@link gameCore.Tile#getInventory()}.
	 */
	@Override
	public void testGetInventory() {
		assertFalse(testTile.getInventory().isEmpty());
	}
	
	/**
	 * Test method for {@link gameCore.Tile#getCharacter()}.
	 */
	@Override
	public void testGetCharacter() {
		assertEquals(testCharacter, testTile.getCharacter());
	}
	
	/**
	 * Test method for {@link gameCore.Tile#removeCharacter()}.
	 */
	@Override
	public void testRemoveCharacter() {
		assertEquals(testCharacter, testTile.removeCharacter());
		assertFalse(testTile.hasCharacter());
	}
	
	/**
	 * Test method for {@link gameCore.Tile#removeItem(gameCore.Item)}.
	 */
	@Override
	public void testRemoveItem() {
		testTile.removeItem(testItem);
		assertFalse(testTile.hasItems());
	}
	
	/**
	 * Test method for {@link gameCore.Tile#hasCharacter()}.
	 */
	@Override
	public void testHasCharacter() {
		assertTrue(testTile.hasCharacter());
	}
	
	/**
	 * Test method for {@link gameCore.Tile#hasItems()}.
	 */
	@Override
	public void testHasItems() {
		assertTrue(testTile.hasItems());
	}
	
	/**
	 * Test method for {@link gameCore.Tile#isEmpty()}.
	 */
	@Override
	public void testIsEmpty() {
		assertFalse(testTile.isEmpty());
	}
}
