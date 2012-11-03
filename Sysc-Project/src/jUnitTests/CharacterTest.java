/**
 * 
 */
package jUnitTests;

import static org.junit.Assert.*;

import java.awt.Point;

import gameCore.Room;
import gameCore.Tile;
import gameCore.Character;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for Character class
 * 
 * @author Group D
 * @author Main author: Karen Madore
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
public class CharacterTest {
	private Room room;
	private Tile myPosition;
	/**
	 * Before the character can be created, a Tile and the room the tile
	 * resides must be instantiated before the character can be created.
	 * 	(Characters always reside on some Tile on the board)
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		room = new Room();
		Point position = new Point(0,0);
		myPosition=new Tile(position, room);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link gameCore.Character#Character(java.lang.String, int, int, gameCore.Tile)}.
	 */
	@Test
	public void testCharacter() {
		Character c=new Character("Test Character", 10, 2, myPosition);
		
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Character#attack(gameCore.Character)}.
	 */
	@Test
	public void testAttack() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Character#use(gameCore.Item)}.
	 */
	@Test
	public void testUseItem() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Character#use(gameCore.Item, gameCore.Character)}.
	 */
	@Test
	public void testUseItemCharacter() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Character#adjustHealth(int)}.
	 */
	@Test
	public void testAdjustHealth() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Character#die()}.
	 */
	@Test
	public void testDie() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Character#dropItem(gameCore.Item)}.
	 */
	@Test
	public void testDropItem() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Character#hasItem(gameCore.Item)}.
	 */
	@Test
	public void testHasItem() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Character#isDead()}.
	 */
	@Test
	public void testIsDead() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Character#getInventoryString()}.
	 */
	@Test
	public void testGetInventoryString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Character#getInventory()}.
	 */
	@Test
	public void testGetInventory() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Character#addItem(gameCore.Item)}.
	 */
	@Test
	public void testAddItem() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Character#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Character#getAttack()}.
	 */
	@Test
	public void testGetAttack() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Character#setAttack(int)}.
	 */
	@Test
	public void testSetAttack() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Character#getHealth()}.
	 */
	@Test
	public void testGetHealth() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Character#setHealth(int)}.
	 */
	@Test
	public void testSetHealth() {
		fail("Not yet implemented");
	}

}
