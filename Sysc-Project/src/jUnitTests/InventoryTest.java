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
	private Inventory inventoryList;
	private Item RedKey;
	private Item BlueKey;
	
	/**
	 * Setup: 
	 * 		Instantiate a inventory.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		inventoryList = new Inventory();
		RedKey = new Item("RedKey",1); //RedKey w:1
		BlueKey = new Item("BlueKey",1); //BlueKey w:1
	}


	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		for(int i=0; i<inventoryList.size();i++){
			Item itemToRemove=inventoryList.getItem(i);
			inventoryList.removeItem(itemToRemove);
		}
	}

	/**
	 * Test method for {@link gameCore.Inventory#Inventory()}.
	 */
	@Test
	public void testInventory() {
		assertEquals("Size of empty Inventory is 0.", 0, inventoryList.size());
	}

	/**
	 * Test method for {@link gameCore.Inventory#addItem(gameCore.Item)}.
	 */
	@Test
	public void testAddOneItem() {
		Item item1=new Item("RedKey", 1);
		inventoryList.addItem(item1);
		
		assertEquals("Size of one item in the inventory should be 1.", 1, inventoryList.size());
	}

	/**
	 * Test method for {@link gameCore.Inventory#getItem(int)}.
	 */
	@Test
	public void testGetItem() {
		inventoryList.addItem(RedKey);
		assertEquals("Item retrieved should be RedKey.", RedKey, inventoryList.getItem(0));
	}

	/**
	 * Test method for {@link gameCore.Inventory#getTotalWeight()}.
	 */
	@Test
	public void testGetTotalWeight() {
		inventoryList.addItem(BlueKey);
		assertEquals("Total weight of inventory with BlueKey is 1.", 1, inventoryList.getTotalWeight());
		inventoryList.addItem(RedKey);
		assertEquals("Total weight of inventory with BlueKey and RedKey is 2.",2, inventoryList.getTotalWeight());
	}

	/**
	 * Test method for {@link gameCore.Inventory#removeItem(gameCore.Item)}.
	 */
	@Test
	public void testRemoveItem() {
		inventoryList.addItem(BlueKey);
		inventoryList.removeItem(BlueKey);
		assertFalse("Inventory list should not contain removed BlueKey.", inventoryList.containsItem(BlueKey));
	}

	/**
	 * Test method for {@link gameCore.Inventory#size()}.
	 */
	@Test
	public void testSize() {
		assertEquals("Empty inventory should return size 0.", 0,inventoryList.size());
		inventoryList.addItem(RedKey);
		assertEquals("Size of inventory with one item added should return 1.", 1, inventoryList.size());
		inventoryList.addItem(BlueKey);
		assertEquals("Size of inventory with two items added should return 2.", 2, inventoryList.size());
	}

	/**
	 * Test method for {@link gameCore.Inventory#containsItem(gameCore.Item)}.
	 */
	@Test
	public void testContainsItem() {
		inventoryList.addItem(RedKey);
		assertTrue("Inventory should contain item added RedKey.", inventoryList.containsItem(RedKey));
	}

	/**
	 * Test method for {@link gameCore.Inventory#getIndex(java.lang.String)}.
	 */
	@Test
	public void testGetIndex() {
		inventoryList.addItem(BlueKey);
		inventoryList.addItem(RedKey);
		assertEquals("Index of BlueKey is 0.", 0, inventoryList.getIndex("BlueKey"));
		assertEquals("Index of RedKey is 1.", 1, inventoryList.getIndex("RedKey"));
	}

	/**
	 * Test method for {@link gameCore.Inventory#isEmpty()}.
	 */
	@Test
	public void testIsEmpty() {
		assertTrue("Inventory  is empty.", inventoryList.isEmpty());
		inventoryList.addItem(BlueKey);
		assertFalse("Inventory with one item in it is not empty.", inventoryList.isEmpty());
	}

	/**
	 * Test method for {@link gameCore.Inventory#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals("A empty string should be returned for empty inventory.", "", inventoryList.toString());
		inventoryList.addItem(BlueKey);
		assertNotNull("A string should be returned.", inventoryList.toString());
	}

	/**
	 * Use this if there are multiple tests to run and they are combined into a test suite.
	 * @param args
	 */
	public static void main(String[] args){
		//junit.textui.TestRunner.run(AllInventoryTests.suite());
	}
	
}
