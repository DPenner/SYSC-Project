package jUnitTests;
/**
* Tests the Level class
*
* @author Group D
* @author Main Author: Trang Pham
*
* Group D Members
* ---------------
* Karen Madore
* Trang Pham
* Darrell Penner
*
*
* @version 2.0
*
*/
import static org.junit.Assert.*;

import java.awt.Point;
import java.util.List;

import gameLoader.Level;
import gameCore.*;
import gameCore.Character;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LevelTest {

	private Level l;
	private Room r;
	private Tile t;
	@Before
	public void setUp() throws Exception {
		l = new Level();
		l.setLevelSize(5, 10);
		
		r = new Room();
		t = new Tile(new Point(2,2), r);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void setLevelSizeTest()
	{
		assertEquals(l.getGridHeight(), 10);
		assertEquals(l.getGridWidth(), 5);
	}
	
	@Test
	public void setTileTest()
	{
		assertTrue(l.setTile(2, 2, t));
		assertTrue(l.setTile(3, 4, t));
	}

	@Test 
	public void getTileTest()
	{
		Tile t2 = new Tile(new Point(2,3), r);
		
		l.setTile(2, 2, t);
		l.setTile(2, 3, t2);
		
		assertEquals(t, l.getTile(2, 2));
		assertTrue(t != l.getTile(2, 3));
		assertEquals(t2, l.getTile(2,3));	
	}
	
	@Test
	public void addEdgeThatIsAnExitTest()
	{
		Tile t2 = new Tile(new Point(2,3), r);
		
		l.setTile(2, 2, t);
		l.setTile(2, 3, t2);
		
		Item key = new Item("key", 2);
		
		boolean addEdgeReturn = l.addEdge(t, t2, "north", "", key, false);

		List<Edge> e = l.getEdges();
		assertTrue(addEdgeReturn);
		
		
		assertTrue(e.get(0) instanceof Exit);
		assertFalse(e.get(0).isCrossableByDefault());
		assertEquals(e.get(0).getDirection1(), Direction.NORTH);
		assertEquals(e.get(0).getDirection2(), Direction.SOUTH);
		assertEquals(e.get(0).getLocation1(), new Point(2,2));
		assertEquals(e.get(0).getLocation2(), new Point(2,3));
		assertEquals(e.get(0).getOtherTile(t), t2);
		assertEquals(e.get(0).getOtherTile(t2), t);
	}
	
	@Test
	public void addEdgeThatIsBoundaryOfMap()
	{
		l.setTile(2, 2, t);
		//index1
		assertTrue(l.addEdge(t, null, "south", "", null, false));
		
		List<Edge> e = l.getEdges();
		assertTrue(e.get(0) instanceof Edge);
		assertFalse(e.get(0).isCrossableByDefault());
		assertEquals(e.get(0).getDirection1(), Direction.SOUTH);
		assertEquals(e.get(0).getLocation1(), new Point(2,2));		
	}
	
	@Test
	public void addCharacterTest()
	{
		l.setTile(2, 2, t);

		Character c = l.addCharacter("Bob", 10, 12, 2, 2);
		assertEquals(c.getAttack(), 12);
		assertEquals(c.getHealth(), 10);
		assertEquals(t.getCharacter(), c);
	}
	
	@Test
	public void addItemTest()
	{
		l.setTile(2, 2, t);
		
		Item i = new Item("RandomItem", 10);
		assertTrue(l.addItem("RandomItem", 10, 2, 2));
		assertTrue(l.getTile(2, 2).getInventory().containsItem(i));
	}
	
	@Test 
	public void addWeaponTest()
	{
		l.setTile(2, 2, t);
		
		Weapon w = new Weapon("Weapon", 2, 6);
		assertTrue(l.addWeapon("Weapon", 2, 6, 2, 2));
		assertTrue(l.getTile(2, 2).getInventory().containsItem(w));
	}
	
	@Test
	public void setElevatorTest()
	{
		Room r = new Room();
		Tile t = new Tile(new Point(2,2), r);
		l.setTile(2, 2, t);
		l.setElevator(r, t);
		assertTrue(l.getPlayer() != null);
		assertEquals(l.getElevator(), r);
	}
}
