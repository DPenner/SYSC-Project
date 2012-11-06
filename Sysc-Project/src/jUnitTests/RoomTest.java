/**
 * 
 */
package jUnitTests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import gameCore.Room;
import gameCore.Tile;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests a Room.
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
public class RoomTest {

	private Room r;
	private Tile tile1;
	private Tile tile2;
	private Set<Tile> s;
	
	/**
	 * Creates an empty set, and two tiles in a room.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		s = new HashSet<Tile>();
		r = new Room();
		tile1 = new Tile(new Point(1, 1), r);
		tile2 = new Tile(new Point(2, 1), r);
		
		//no direct way to test addTile success, 
		//since Room does not currently allow a way to see its Tiles
		r.addTile(tile1);
		r.addTile(tile2);
	}

	/**
	 * Test method for {@link gameCore.Room#Room(java.util.Set<gameCore.Tile>)}.
	 */
	@Test
	public void testRoomSetConstructor(){
		s.add(tile1);
		s.add(tile2);
		Room r2 = new Room(s);
		//really nothing to assert, just make sure no exception is thrown
	}
	
	/**
	 * Test method for {@link gameCore.Room#Room(java.util.Set<gameCore.Tile>)}.
	 */
	@Test
	public void testRoomSetConstructorBadInput(){
		try {
			Room r2 = new Room(null); //null test
			fail("IllegalArgumentException was expected");
		}
		catch (IllegalArgumentException e){
			//exception expected
		}
		try {
			Room r2 = new Room(s); //empty set test
			fail("IllegalArgumentException was expected");
		}
		catch (IllegalArgumentException e){
			//exception expected
		}
	}
	
	/**
	 * Test method for {@link gameCore.Room#setVisited()}.
	 */
	@Test
	public void testSetVisited() {
		r.setVisited();
		assertTrue(tile1.isVisited());
		assertTrue(tile2.isVisited());
	}
	
	/**
	 * Test method for {@link gameCore.Room#addTile(gameCore.Tile)}.
	 */
	@Test
	public void testAddTileBadInput() {
		try {
			r.addTile(null);
			fail("Expected IllegalArgumentException");
		}
		catch (IllegalArgumentException e){
			//expected exception
		}
	}

}
