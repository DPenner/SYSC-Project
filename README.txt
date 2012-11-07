README

AUTHORS:
Karen Madore
Trang Pham
Darrell Penner

ROLES:
Karen Madore:
- gameCore 
     Classes: Characters, Inventory, and Items, Player, Monster, Weapon, and expanding list of characters 
              and items in future.
- Game2D
     Classes: KDTView, InventoryPanel, PlayerStatusPanel, PlayerListener, PlayerEvent

Trang Pham:
- I/O
   Command, CommandWord, Parser, CommandWords
- gameLoader
   lvl0.xml, Level, LevelCreator, Game, EndGameException

Darrell Penner:
- Game Layout
    Classes: Room, Tile, Edge, Exit, LayoutObject and related test classes
- Map Display and controller
    Classes: MapView, MapController, TilePanel, EdgePanel, LayoutPanel

DELIVERABLES:
Design Documentation.docx - Our documentation on the design of our game.

KNOWN ISSUES:
- MapView does not currently consider offsets: if 0 is not the minimum value for x and y,
  it is not taken into consideration.
- Monster does not die when character attacks and dies with the same attack and health
- MapView scrolling? We currently do not have a level large enough to test its scrolling capabilities.

CHANGES:
- 2D GUI has been implemented
- Bug fix: an exception is no longer thrown upon a request to check for a non-existing direction,
           false is returned instead.
- Implemented monster move.  Note: not tested yet and will implement in next milestone.  If a player
  is on adjacent tile, the Monster will attack it, otherwise, it will move in random motion.
- Command interface has been removed and replaced by GUI interface

ROADMAP:
- use image files to enhance the look of the game.  For example, the characters and items will have 
  more visual appeal.
- create a more complex second level
- create a 3D view of the application
- enable use of weapons
- Make it actually possible to win the game
- Mouse control and pathfinding
