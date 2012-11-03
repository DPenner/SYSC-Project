/**
 * 
 */
package jUnitTests;

import static org.junit.Assert.*;

import java.awt.Point;

import gameCore.Inventory;
import gameCore.Item;
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
	private static final int ATTACKER_INITIAL_ATTACK = 2;
	private static final int ATTACKER_INITIAL_HEALTH = 10;
	private static final int DEFENDER_INITIAL_ATTACK = 1;
	private static final int DEFENDER_INITIAL_HEALTH = 8;
	private Room room;
	private Tile position1;
	private Tile position2;
	private Tile position3;
	private Tile charPosition;
	private Inventory cInventory;
	private Character testCharacter;
	private Character deadCharacter;
	
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
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,1);
		Point p3 = new Point(0,2);
		Point p4 = new Point(1,0);
		position1=new Tile(p1, room);
		position2 = new Tile(p2, room);
		charPosition = new Tile(p3, room);
		position3= new Tile(p4, room);
		cInventory= new Inventory();
		testCharacter=new Character("TestCharacter", DEFENDER_INITIAL_HEALTH, DEFENDER_INITIAL_ATTACK, charPosition);
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
		assertNotNull("Newly instantiated test character should not be null.", testCharacter);
	}

	/**
	 * Test method for {@link gameCore.Character#attack(gameCore.Character)}.
	 */
	@Test
	public void testAttack() {
		Character defender=new Character("Defender", DEFENDER_INITIAL_HEALTH, DEFENDER_INITIAL_ATTACK, position2);
		Character attacker=new Character("Attacker", ATTACKER_INITIAL_HEALTH, ATTACKER_INITIAL_ATTACK, position3);
		//first attack
		attacker.attack(defender);//ie player attacks monster who is the defender
		assertEquals("Attacker health should be 9.", 9, attacker.getHealth());
		assertEquals("Defender health should be 6.", 6, defender.getHealth());
		//second attack
		attacker.attack(defender);//ie player attacks monster who is the defender
		assertEquals("Attacker health should be 8.", 8, attacker.getHealth());
		assertEquals("Defender health should be 4.", 4, defender.getHealth());
		//third attack
		attacker.attack(defender);//ie player attacks monster who is the defender
		assertEquals("Attacker health should be 7.", 7, attacker.getHealth());
		assertEquals("Defender health should be 2.", 2, defender.getHealth());
		//fourth attack
		attacker.attack(defender);//ie player attacks monster who is the defender
		assertEquals("Attacker health should be 6.", 6, attacker.getHealth());
		assertEquals("Defender health should be 0.", 0, defender.getHealth());
		assertTrue("Defender should be dead now. ", defender.isDead());
	}

	/*
	 * Test method for {@link gameCore.Character#use(gameCore.Item)}.
	 * 
	 * --- not used in version 2 ---
	 *
	@Test
	public void testUseItem() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Character#use(gameCore.Item, gameCore.Character)}.
	 * 
	 * --- not used in version 2 ---
	 *
	@Test
	public void testUseItemCharacter() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Character#adjustHealth(int)}.
	 */
	@Test
	public void testAdjustHealth() {
			
		//test negative value decrease character's health 
		int reduceHealthbyTwo = -2;
		testCharacter.adjustHealth(reduceHealthbyTwo);
		assertEquals("Test Character's health should be 6.", 6, testCharacter.getHealth());
		
		//ensure health does not fall below 0
		int reduceHealthbyTwenty = -20;
		testCharacter.adjustHealth(reduceHealthbyTwenty);
		assertEquals("Test Character's health should be 0.", 0, testCharacter.getHealth());
		
		//test increasing player health
		testCharacter.adjustHealth(5);
		assertEquals("Test Character's health should be 5.", 5, testCharacter.getHealth());
		
	}

	/**
	 * Test method for {@link gameCore.Character#dropItem(gameCore.Item)}.
	 */
	@Test
	public void testDropItem() {
		//no item to drop so Tile should not have anything
		Item RedKey = new Item("RedKey", 1);
		
		assertFalse("The tile should NOT have an item.", charPosition.hasItems());
		
		testCharacter.dropItem(RedKey);
		
		assertFalse("RedKey item dropped.", testCharacter.hasItem(RedKey));
		assertTrue("The tile should now have an item.", charPosition.hasItems());
		
		
	}

	/**
	 * Test method for {@link gameCore.Character#hasItem(gameCore.Item)}.
	 */
	@Test
	public void testHasItem() {
		Item RedKey = new Item("RedKey", 1);
		testCharacter.addItem(RedKey);
		assertTrue("Character should have a RedKey item.", testCharacter.hasItem(RedKey));
		
		testCharacter.dropItem(RedKey);
		assertFalse("Character should NOT have a RedKey item.", testCharacter.hasItem(RedKey));
	}

	/**
	 * Test method for {@link gameCore.Character#isDead()}.
	 */
	@Test
	public void testIsDead() {
		Character deadCharacter=new Character("DeadCharacter", 0, 0, position1);
		assertEquals("Character is with health 0 is dead.", 0, deadCharacter.getHealth());
		assertTrue("Character is dead.", deadCharacter.isDead());
	}
	/**
	 * Test isDead method returns false if character is alive.
	 */
	@Test
	public void testIsAlive(){
		Character aliveCharacter=new Character("Alive Character", 1, 0, position2);
		assertFalse("Character is not dead.", aliveCharacter.isDead());
	}

	/**
	 * Test method for {@link gameCore.Character#getInventoryString()}.
	 */
	@Test
	public void testGetInventoryString() {
		Item RedKey=new Item("RedKey",1);
		testCharacter.addItem(RedKey);
		assertNotNull("A string should be returned.  empty string if inventory does not contain any items.", testCharacter.getInventory());
	}

	/**
	 * Test method for {@link gameCore.Character#getInventory()}.
	 */
	@Test
	public void testGetInventory() {
		Item RedKey=new Item("RedKey",1);
		testCharacter.addItem(RedKey);
		assertNotNull("Character should have an inventory and getInventory should return it.", testCharacter.getInventory());
	}

	/**
	 * Test method for {@link gameCore.Character#addItem(gameCore.Item)}.
	 */
	@Test
	public void testAddItem() {
		
		Item RedKey=new Item("RedKey",1);
		testCharacter.addItem(RedKey);
		assertNotNull("Character should have an inventory and getInventory should return it.", testCharacter.getInventory());
	}

	/**
	 * Test method for {@link gameCore.Character#toString()}.
	 */
	@Test
	public void testToString() {
		assertNotNull("It should return string representing Test Character.", testCharacter.toString());
	}

	/**
	 * Test method for {@link gameCore.Character#getAttack()}.
	 */
	@Test
	public void testGetAttack() {
		
	}

	/**
	 * Test method for {@link gameCore.Character#setAttack(int)}.
	 */
	@Test
	public void testSetAttack() {
		testCharacter.setAttack(20);
		assertEquals("Set test character's health to 20.", 20, testCharacter.getAttack());
	}

	/**
	 * Test method for {@link gameCore.Character#getHealth()}.
	 */
	@Test
	public void testGetHealth() {
		assertEquals("Test characters health.", DEFENDER_INITIAL_HEALTH, testCharacter.getHealth());
	}

	/**
	 * Test method for {@link gameCore.Character#setHealth(int)}.
	 */
	@Test
	public void testSetHealth() {
		testCharacter.setHealth(11);
		assertEquals("Test setting character's health to 11.", 11, testCharacter.getHealth());
	}

}
