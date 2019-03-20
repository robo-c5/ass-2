
----------------- maybe done?
Whenever move or rotate, call pushStats method in MazeSolvingRobot 
write pushstats method
-----------------

setup socket in run method

pcclientgui might work for that

write up action method for AbleToEnd


!!!!!!!!!!!!!!
Astaring back to start is an issue because some areas may be inaccessible, so not all tiles can be visited, so Astar path is never calculated- fix would be to check if all unexplored tiles are unreachable, if yes, the maze has been mapped as fully as it can be, so should return