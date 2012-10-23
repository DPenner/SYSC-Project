README

AUTHORS:
Karen Madore
Trang Pham
Darrell Penner

ROLES:
Karen Madore:
- Characters, Inventory, and Items
   Classes: Charcter, Player, Monster, Inventory, Item, Weapon, and expanding list of characters and items in future.

Trang Pham:
- I/O
   Command, CommandWord, Parser, CommandWords
- gameLoader
   lvl0.xml, Level, LevelCreator, Game, EndGameException

Darrell Penner:
- Game Layout
    Classes: Room, Tile, Edge, Exit

DELIVERABLES:
Design Documentation.docx - Our documentation on the design of our game.

KNOWN ISSUES:
- An exception is thrown when the user attempts to go in a direction that does not exist.
  A temporary fix is in place where the exception is caught and a message is displayed saying
  that "Edge does not exist in that direction". This message can be treated as if there was a wall there.
- Exit outputs "unlocking exit" even when exit is already unlocked.

ROADMAP:
- Allow monsters to move.  
      Ideas: 1. allow certain monsters to move only within a room
             2. make some monsters move randomly
             3. monsters have the co-ordinates of the player and attempt to go toward player trying to kill it.

- Create friendly characters. 
- Add Keys, a type of item.
- "direction" to be implemented as an enum
