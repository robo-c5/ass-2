Maze make some methods static

T - might be good idea to detect when you've crossed double black, and then travel half tile, then it won't get out of synch?
T - couple of behaviours causing errors with null pointers, will debug next time
T - make sure tiles are marked as visited when the robot has stepped on the tile, and also add this tile to the navpath at same time

Issue in Maze - when looking for tiles adjacent to a given tile, returned tile is one too far over- e.g. getNearestTile returns (65, 25) instead of (45, 25), when given (25, 25)
not sure if this is because getNearestTile has two .getAdjacent() calls on one object? Anyway calling it a day here

perhaps add a Visitable flag to Tiles so that don't have to check every tile in case where Tile is entirely enclosed by walls.

use colour sensors on grid lines?

two methods:

update maze object & redraw it
robot position & heading - directed arrow

Robot wise - maybe put send off maze once complete a move
send off position when complete a move
send off heading when complete a rotation

or just once a second


pc server 2 threads max prolly - drawing & communication

look at gui update stuff - remember to delete stuff that ain't ours before submission

EV3Server : pushing stats should run on a separate thread

general idea: use RobotStats for pushing and receiving stats ONCE

