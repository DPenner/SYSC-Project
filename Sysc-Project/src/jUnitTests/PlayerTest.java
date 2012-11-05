package jUnitTests;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;
import gameCore.Character;
import gameCore.Inventory;
import gameCore.Item;
import gameCore.Player;
import gameCore.Room;
import gameCore.Tile;
import gameCore.Direction;
import gameCore.Edge;
import gameLoader.EndGameException;

import graphics2D.PlayerListener;
import graphics2D.PlayerStatusPanel;
/**
 * A set of test for Player Class
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
public class PlayerTest {
	private static final int PLAYER_INITIAL_ATTACK = 2;
	private static final int PLAYER_INITIAL_HEALTH = 10;
	private static final int PLAYER_STAMINA=5;
	
	private Room room;
		 
	private Tile myPosition;
	private Tile northTile;
	private Tile southTile;
	
	private Inventory cInventory;
	private Player testPlayer;
	private Item RedKey;
	private StringBuffer output;
	
	@Before
	public void setUp() throws Exception {
		room = new Room();
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,1);
		myPosition=new Tile(p1, room);
		northTile = new Tile(p2, room);
		cInventory= new Inventory();
		testPlayer=new Player("TestPlayer", PLAYER_INITIAL_HEALTH, PLAYER_INITIAL_ATTACK,PLAYER_STAMINA, myPosition);
		RedKey = new Item("RedKey", 1);
	}

	@After
	public void tearDown() throws Exception {
	}
	/**
	 * Test Player's adjustHealth method
	 */
	@Test
	public void testAdjustHealth() {
		//test reduce health
		testPlayer.adjustHealth(-2);
		assertEquals("Test player's health value should be 8.", 8, testPlayer.getHealth());
		//test increase health
		//testPlayer.adjustHealth(2);
		//assertEquals("Test player's health value should be 10.", 10, testPlayer.getHealth());
	}

	/**
	 * Test instantiation of Player
	 */
	@Test
	public void testPlayer() {
		assertNotNull("Player should exist.", testPlayer);;
	}

	/**
	 * Test Player's movement
	 * @throws EndGameException 
	 */
	@Test
	public void testMove() throws EndGameException {
		//no edge so will not be able to move
		
		assertFalse("Player should not be able to move NORTH", testPlayer.move(Direction.NORTH, output));
		Edge e=new Edge(myPosition, northTile, true);
		assertTrue("Player should be able to move NORTH",testPlayer.move(Direction.NORTH, output));
				
		
		
	}

	/**
	 * Test the Pickup of the item(a String)
	 */
	@Test
	public void testPickUpItem() {
		myPosition.addItem(RedKey);
		testPlayer.pickUpItem(RedKey.getName());
		assertTrue("Player should have RedKey now. ", testPlayer.hasItem(RedKey));
		
	}

	/**
	 * Test the dropping of items
	 */
	@Test
	public void testDrop() {
		myPosition.addItem(RedKey);
		testPlayer.pickUpItem(RedKey.getName());
		testPlayer.dropItem(RedKey);
		assertFalse("Player should not have RedKey now. ", testPlayer.hasItem(RedKey));
	
	}

	/**
	 * ViewInventory should return a non-empty string
	 */
	@Test
	public void testViewInventory() {
		assertNotNull("A non-empty string should be returned.", testPlayer.viewInventory());
	}

	/**
	 * ViewHealth should return a non-empty string
	 */
	@Test
	public void testViewHealth() {
		assertNotNull("A non-empty string should be returned.", testPlayer.viewHealth());
	}

	@Test
	public void testAddPlayerListener() {
		PlayerListener pl = new PlayerStatusPanel(testPlayer);
		testPlayer.addPlayerListener(pl);
		assertEquals("PlayerListeners count should be 1.", 1, testPlayer.countPlayerListeners());
	}

	@Test
	public void testRemovePlayerListener() {
		fail("Not yet implemented");
	}

}
