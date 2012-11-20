README

AUTHORS:
Karen Madore
Trang Pham
Darrell Penner

ROLES:
Karen Madore:
- gameCore 
     Classes: Characters, Inventory, and Items, Player, Monster, Weapon, KDT
- Game2D
     Classes: KDTView, InventoryPanel, PlayerStatusPanel, PlayerListener, PlayerEvent, KDTMouseController, KDTMenuController

Trang Pham:
- I/O (Text based)
   Command, CommandWord, Parser, CommandWords
- gameLoader
   lvl0.xml, Level, LevelCreator, Game, EndGameException
- commands
   Command, CommandController, GoCommand, SreachCommand, DropCommand, PickUpCommand
- in graphics2D
   TextOutputPanel, HelpListener, TextOutputPanelObserver
- graphics3D
   FirstPersonView, ForegroundLayer, BackgroundLayer

Darrell Penner:
- Game Layout
    Classes: Room, Tile, Edge, Exit, LayoutObject and related test classes
- Map Display and controller
    Classes: MapView, MapController, TilePanel, EdgePanel, LayoutPanel

DELIVERABLES:
Design Documentation iteration 3.docx - Our documentation on the design of our game.
Sequence diagrams are in sequence diagrams folder
UML diagrams are in UML diagrams folder
javadoc located inside jar file in folder "doc"

KNOWN ISSUES:
- MapView does not currently consider offsets: if 0 is not the minimum value for x and y,
  it is not taken into consideration.
- MapView scrolling? We currently do not have a level large enough to test its scrolling capabilities.

CHANGES:
- 3D GUI has been implemented with mouse control
- 2D GUI has been implemented 
- Bug fix: an exception is no longer thrown upon a request to check for a non-existing direction,
           false is returned instead.
- Implemented monster move.  Note: not tested yet and will implement in next milestone.  If a player
  is on adjacent tile, the Monster will attack it, otherwise, it will move in random motion.
- Text Command interface has been removed and replaced by GUI interface

ROADMAP:
- use image files to enhance the look of the game.  For example, the characters and items will have 
  more visual appeal.
- create a more complex second level
- enable use of weapons
- Make it actually possible to win the game
