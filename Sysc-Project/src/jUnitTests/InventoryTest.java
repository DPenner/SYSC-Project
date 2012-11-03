/**
 * 
 */
package jUnitTests;

import static org.junit.Assert.*;
import gameCore.Inventory;
import gameCore.Item;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the Inventory class
 * 
 * @author kmadore
 *
 */
public class InventoryTest {
	//------------Fields------------//
	private Inventory inv;
	
	/**
	 * Setup: 
	 * 		Instantiate a inventory.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		inv = new Inventory();
	}


	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link gameCore.Inventory#Inventory()}.
	 */
	@Test
	public void testInventory() {
		assertEquals("Size of empty Inventory is 0.", 0, inv.size());
	}

	/**
	 * Test method for {@link gameCore.Inventory#addItem(gameCore.Item)}.
	 */
	@Test
	public void testAddItem() {
		Item item1=new Item("RedKey", 1);
		inv.addItem(item1);
		
		assertEquals("Size of one item in the inventory should be 1.", 1, inv.size());
	}

	/**
	 * Test method for {@link gameCore.Inventory#getItem(int)}.
	 */
	@Test
	public void testGetItem() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Inventory#getTotalWeight()}.
	 */
	@Test
	public void testGetTotalWeight() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Inventory#removeItem(gameCore.Item)}.
	 */
	@Test
	public void testRemoveItem() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Inventory#size()}.
	 */
	@Test
	public void testSize() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Inventory#containsItem(gameCore.Item)}.
	 */
	@Test
	public void testContainsItem() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Inventory#getIndex(java.lang.String)}.
	 */
	@Test
	public void testGetIndex() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Inventory#isEmpty()}.
	 */
	@Test
	public void testIsEmpty() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gameCore.Inventory#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	
}
