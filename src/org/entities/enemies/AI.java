package org.entities.enemies;

import java.awt.Rectangle;
import java.util.List;

public interface AI
{
	abstract void act();							// method where all acting takes place
	abstract void follow(boolean PlayerY);			// assortment of pathing algos and helper methods
	
	abstract boolean onGround();
	abstract boolean playerInVerticalRange();				// looks for the player's vertical location, assists with abstract follow(); algorithm
	abstract boolean above(double y);						// returns true if the player is above the enemy
	abstract boolean below(double y);						// returns true if the player is below the enemy
	abstract boolean approachingWall(boolean facingRight);
	
	abstract int destination(boolean playerY);		// contains coordinates of the location for enemies to go to
	abstract Rectangle currentGround(List<Rectangle> currentNode);		// contains the collision box on which enemies are currently standing
}
