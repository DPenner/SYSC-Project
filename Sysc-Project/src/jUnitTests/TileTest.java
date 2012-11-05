/**
 * 
 */
package jUnitTests;

import java.awt.Point;

import gameCore.*;
import gameCore.Character;

import org.junit.Before;

/**
 * @author DarrellPenner
 *
 */
public class TileTest {

	protected Tile testTile;
	protected Character testCharacter;
	protected Item testItem;
	protected Point testLocation;
	
	@Before
	public void setUp(){
		testLocation = new Point(1, 2);
		Room r = new Room();
		testTile = new Tile(testLocation, r);
		testItem = new Item("TestItem", 0);
	}
		
	public void createCharacterOnTestTile(){
		testCharacter = new Character(null, 0, 0, testTile);
	}
}
