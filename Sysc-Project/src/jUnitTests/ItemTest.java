package jUnitTests;
import gameCore.Item;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ItemTest {
	private Item testItem;
	@Before
	public void setUp() throws Exception {
		testItem= new Item("test item", 2);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testItem() {
		assertNotNull("An item should be created.", testItem);
	}

	@Test
	public void testEqualsObject() {
		assertTrue("Test item is equal to itself.", testItem.equals(testItem));
		Item testItem2= new Item("test item 2",1);
		assertFalse("Test item is not equal to test item2. ", testItem.equals(testItem2));
	}

	@Test
	public void testGetName() {
		assertEquals("Name of item1 is test item.", "test item", testItem.getName());
	}

	@Test
	public void testToString() {
		assertNotNull(testItem.toString());
	}

}
