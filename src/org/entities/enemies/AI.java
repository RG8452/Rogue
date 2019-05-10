package org.entities.enemies;

import java.awt.Rectangle;
import java.util.List;

/** This interface is intended as a general framework for the motion
 * and pathfinding algorithms of all types of enemies in the game. It 
 * provides a set of basic methods all enemy types will have.
 * <p>
 * These are then defined and used by {@link Enemy} and all it's subclasses
 * for use across several types of enemies with different patterns of motion.
 * @author Keon W.
 */
public interface AI
{
	/** All actions enemies can perform are defined by this method.
	 * This includes movement, attacks, etc. all based on the 
	 * value stored in {@code status}
	 * @see Enemy#status
	 */
	abstract void act();	
	
	/** Gives the enemy a location to move to based on the player's location. This is
	 * accomplished by making a call to {@code destination} before any movement happens.
	 * <p>
	 * If an enemy is not on the same plane as the player they begin moving left and right
	 * after a call to {@code destination} similar to if the player and enemy are vertically
	 * aligned.
	 * @param PlayerY determines if the player is in the same vertical plane as the enemy
	 */
	abstract void follow(boolean PlayerY);
	
	
	/** Checks if the enemy is currently touching a collision along it's bottom edge.
	 * @return {@code true} if the enemy is touching the ground; {@code false} otherwise.
	 */
	abstract boolean onGround();
	
	/** Looks for the player's vertical position and determine's if they are in the enemy's
	 * range.
	 * @return {@code true} if the player is in range; {@code false} otherwise.
	 */
	abstract boolean playerInVerticalRange();
	
	/** Helper method for {@code playerInVerticalRange}.
	 * @param y is the player's vertical position in double format.
	 * @return {@code true} if the player is above this enemy.
	 */
	abstract boolean above(double y);
	
	/** Helper method for {@code playerInVerticalRange}.
	 * @param y is the player's vertical position in double format.
	 * @return {@code true} if the player is below this enemy.
	 */
	abstract boolean below(double y);
	
	/** If the enemy is simply moving along a path they will eventually come into
	 * collision with a wall, and therefore need a way to check for walls in their
	 * path and return if one is in close enough range to merit turning around and
	 * setting a new destination.
	 * <p>
	 * This method aids the {@link #destination(boolean)} method in choosing what
	 * the {@code currentDestination} is.
	 * @param facingRight determines which side needs to be checked for a wall
	 * @return {@code true} if a wall is within a block (32 pixels) of the current
	 * {@code enemy}; {@code false} otherwise.
	 * @see Enemy#currentDestination
	 */
	abstract boolean approachingWall(boolean facingRight);
	
	/** Determines the location this enemy should head towards in the horizontal plane based
	 * on the player's vertical position.
	 * @param playerY determines if the player is in the same vertical plane as the enemy
	 * @return The X coordinate this enemy should move towards.
	 */
	abstract int destination(boolean playerY);
	
	/** Helper method which determines which vertical collision
	 * this enemy is standing on in the current frame. Used to determine
	 * where the edge of the "ground" is in {@link #destination(boolean)}.
	 * @param currentNode is the node of the {@code quadTree} this enemy is currently in.
	 * @return The collision touching the bottom edge of this enemy's hitbox when 
	 * {@code onGround} is {@code true}.
	 */
	abstract Rectangle currentGround(List<Rectangle> currentNode);
}
