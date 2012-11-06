/**
 * 
 */
package jUnitTests;

import static org.junit.Assert.*;

import java.awt.Point;

import gameCore.*;
import gameCore.Character;

import org.junit.Before;
import org.junit.Test;

/**
 * Sets-up for testing a tile. Should be inherited for any class testing Tiles
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
public class TileTest {

	protected Tile testTile;
	protected Character testCharacter;
	protected Item testItem;
	protected Point testLocation;
	
	/**
	 * Creates a test Tile, Character, Item and Point. The Tile is initialized
	 * with the the test Point.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		testLocation = new Point(1, 2);
		Room r = new Room();
		testTile = new Tile(testLocation, r);
		testItem = new Item("TestItem", 0);
	}
	
	/**
	 * Helper method for possible subclasses. Puts testCharacter on testTile.
	 */
	public void createCharacterOnTestTile(){
		testCharacter = new Character(null, 0, 0, testTile);
	}
	
	/**
	 * Test method for {@link gameCore.Tile#Tile(java.awt.Point, gameCore.Room)}.
	 */
	@Test
	public void testInvalidConstruction() {
		Tile t;
		try {
			t = new Tile(null, new Room());
			fail("IllegalArgumentException was expected");
		}
		catch (IllegalArgumentException e){
			//expected
		}
		try {
			t = new Tile(new Point(1, 2), null);
			fail("IllegalArgumentException was expected");
		}
		catch (IllegalArgumentException e){
			//expected
		}
		try {
			t = new Tile(null, null);
			fail("IllegalArgumentException was expected");
		}
		catch (IllegalArgumentException e){
			//expected
		}
	}
}
