# AI-Heuristically-Informed-Fuzzy-Maze
## Arjun Kharel - G00298984@gmit.ie
## Module : Artificial Intelligence 
A Heuristically Informed Fuzzy Maze Challenge 



###Main Features: 
1: Threaded(ExecutorService) enemies  moving through the maze independently. (Depth Limited DFS) 

2: The best feature of this project is, as soon as the player moves  the player path instantly updated. When you run the game please press z and move the player . You will see the new player path being generated on the fly after every move of a player.  (AStar algorithm was used).

3: On the left side of the GUI, all the stats has been displayed.  It will tell you how far the (exit point  / Goal node is) and it is calculated based on the player movement.

4 : Fuzzy logic has been implemented. When player collides with an enemy, fight fight is invoked and random weapon is choosen from the player weapon list. 

5: Fuzzy logic victory is shown in the left panel of the game. It will be shown once you collide with an enemy.


###Controls: 
Up arrow

Down Arrow

Left Arrow

Right Arrow

z to zoom out ( please move the player while you are zoomed out, you will be able to see the path being dynamically generated as you move through the maze.

###How to run ?
1: Clone the project from the above link

2: Unzip MazeGame.zip

    - resources folder (contains resources)
    - mazegame.jar
    
execute **java -cp "./mazegame.jar;lib/*" ie.gmit.sw.game.GameRunner**


You can also import the project to eclipse and run it.

import the project (HeuristicallyInformedMaze) in eclipse, please ensure the fuzzy logic library  is added in the build path.

##packages

ie.gmit.sw.fuzzylogic (contains fuzzy logic)

ie.gmit.sw.game

  - Enemy.java
  - GameRunner.java (main method)
  - GameView.java
  - MessageBox.java
  - Player.java
  - Weapon.java


ie.gmit.sw.node
   - Maze.java
   - Node.java
   - NodeType.java
   
ie.gmit.sw.traversers
  - Traversator.java
  - AStarTraversator.java
  - DepthLimitedDFS.java
  - TraversatorStats.java

ie.gmit.sw.sound
  - Sound.java
  - SoundEffect.java
![Alt text](https://github.com/ultimatecodelab/AI-Heuristically-Informed-Fuzzy-Maze/blob/master/maze.PNG "Optional title")
