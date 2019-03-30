# Rogue
I was bored in high school and wanted a chance to program something for fun, so I began programming a very basic game. After mentioning that I was starting what I hoped would be a big project to some friends, I found some people to work with and thanks to that this has gotten much bigger than I ever intended (probably too big, in all honesty). Hat tip to Keon Williams for creating all of the art work in this game and Eli McIntire-Gavlick for helping brainstorm concepts.

This game is meant to be a 2D, side-on roguelike platformer (a la Risk of Rain 1) in which the player moves around the map killing enemies until they achieve an object and progress to the next level. The player will ideally gain money and experience from enemies, and be able to purchase items that serve as simple upgrades with that money.
The enemies will level up as a function of time (e.g. every three minutes, enemies get slightly stronger and force the players to kill enemies over time to maintain a sustainable level). The difficulties in the game should eventually change the amount of time that it takes enemies to get stronger, as well as the quantity and status of enemies (level up every 3, 5, 7 minutes or more armored enemies or what have you).

The game runs a JFrame with a single JPanel, which is swapped out using different classes in the src/org/panels in order to change the actionListeners() and therefore user input as well as the graphics drawn. src/org/GUI.java has comments describing how this system works in more detail.
The actual game, once the player begins it, is run by a Runner.java thread which allows all enemies and players to act and refreshes the screen. This runner has access to a developer console mid game (hit ~ while paused) that allows testing things during the game.

This game is a study in polymorphism. Firstly, the worlds that the player navigates are abstracted (src/org/world/World.java) and implement a data type called a Quad Tree which recursively stores Rectangle hitboxes in order to reduce the retrieval time of all impactable hitboxes to O(N^1/4). A more complete description is in the src/org/world/QuadTree.java class comments. Each world extension simply specifies where every hitbox is, then the player will check the quad tree for collisions.

The hierarchy of the actually established things in the game is as follows:
* Entity - This is the highest level of abstraction. All things that have health bars are entities, and share the majority of the data points that have been set in this class.
  * Enemy - This abstraction specifies more focused data points and methods specifically designed for the enemies. This streamlines the creation of enemies.
    * Individual Enemy Class - Lowest level of abstraction; this is where you specify all of the images used, specific pathing methods, and distinct values.
      * Animation images for any individual class
  * Player - This abstraction listens to key inputs and streamlines the creation of different hero types. It's significantly more complex due to having to check for collisions as well as parse user input.
    * Individual Player Class - Lowest level of abstraction; defines the images, attack types, hitboxes, and distinct values for any given player type.
      * Animation images for any individual class

The game has kind of ceased development recently after it became time to program enemy AI and college got busy, but hopefully it will pick back up in the near future.

Lots of the game's universal constants are stored in the equivalent of a constants.h in src/org/DataRetriever.java so that serves as a source of lots of the information passed around the structures. If you have any questions then post them or send me a message or something and I'll try to get around to it.

-Ryan Gahagan
