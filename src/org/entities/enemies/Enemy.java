package org.entities.enemies;
/**
 * RG
 * Abstract class that each enemy will extend
 * Will lay the ground rules and basic methods that all enemy objects will use
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import org.DataRetriever;
import org.entities.Entity;
import org.world.World;

public abstract class Enemy extends Entity implements AI
{
	protected int lastAttackFrame; //Frame at which the enemy began attacking
	protected boolean inFlight; //True if the enemy can fly
	protected static int framesPerAnimationCycle = 4; //Frames that elapse between each change in animation
	public String EnemyType;

	//Enum used to store all possible outputs for the enemy's status
	protected enum STATUS
	{
		IDLING, PATHING, JUMPING, ATTACKING, CLIMBING
	}

	protected STATUS status; //Variable used for current status\
	private int currentDestination; // location the enemy is headed towards
	private Rectangle collisionBox = new Rectangle(); // rectangle the enemy is standing on
	
	public void runCollision()
	{
		runCollisionX();
		runCollisionY();

		if (onGround) ySpeed = DataRetriever.getGravityConstant(); // If on ground, reset falling speed
		else ySpeed += DataRetriever.getGravityConstant(); // If in air, fall faster
	}

	protected void runCollisionX()
	{
		// Reset Hurtbox and then check for collisions with any nearby rect; if colliding, force out of the block
		World.setDrawX();
		worldbox.setLocation((int) worldX + xOffset, (int) worldY + yOffset);
		for (Rectangle r : DataRetriever.getWorld().getCollisionTree().retrieve(new ArrayList<Rectangle>(), getWorldbox()))
		{
			Rectangle2D r2d = (Rectangle2D) (new Rectangle((int) (r.getX()), (int) (r.getY()), (int) r.getWidth(), (int) r.getHeight()));
			while (worldbox.intersects(r2d)) // pHurtbox.intersects(r)
			{
				worldX = facingRight ? worldX - 1 : worldX + 1;
				worldbox.setLocation((int) worldX + xOffset, (int) worldY + yOffset);
			}
		}

		World.setDrawX();
	}

	protected void runCollisionY()
	{
		worldY += ySpeed; // Change y vars
		World.setDrawY();
		onGround = false;
		boolean ceilingContact = false;

		// Just like the x, this checks y collisions and stops the player from getting through hitboxes
		worldbox.setLocation((int) worldX + xOffset, (int) worldY + yOffset);
		for (Rectangle r : DataRetriever.getWorld().getCollisionTree().retrieve(new ArrayList<Rectangle>(), getWorldbox()))
		{
			Rectangle2D r2d = (Rectangle2D) (new Rectangle((int) (r.getX()), (int) (r.getY()), (int) r.getWidth(), (int) r.getHeight()));
			while (worldbox.intersects(r2d)) // pHurtbox.intersects(r)
			{
				worldY = ySpeed < 0 ? worldY + 1 : worldY - 1;
				worldbox.setLocation((int) worldX + xOffset, (int) worldY + yOffset);
				if (ySpeed > 0) onGround = true;
				else ceilingContact = true;
			}
		}

		World.setDrawY();
		worldbox.setLocation((int) worldX + xOffset, (int) worldY + yOffset);
		if (ceilingContact) ySpeed = DataRetriever.getGravityConstant();
	}

	/* This sets a destination for the AI to move to depending on the player's vertical coordinates.
	 * If the player is outside the vertical range of the AI it will then continue in the same direction
	 * along the collision it is currently on until it either encounters an edge or a wall blocking it's
	 * path. Can be revisited for single block height jumps.
	 */
	public int destination(boolean playerY)
	{
		if (playerY) currentDestination = (int) DataRetriever.getPlayer().getWorldbox().getCenterX();
		else if (!playerY)
		{
			if (worldX == currentDestination)
				facingRight = !facingRight;
			else if (approachingWall(facingRight))
			{
				facingRight = !facingRight;
				worldX = facingRight ? worldX + (xSpeed *2) : worldX - (xSpeed *2);
			}
			if ((worldX != currentDestination) && facingRight)
				 currentDestination = (int) currentGround(DataRetriever.getWorld().getCollisionTree().retrieve(new ArrayList<Rectangle>(), getWorldbox())).getMaxX()
									- getWidth()/2;
			else currentDestination = (int) currentGround(DataRetriever.getWorld().getCollisionTree().retrieve(new ArrayList<Rectangle>(), getWorldbox())).getMinX()
									+ getWidth()/2;
		}
		return currentDestination;
	}
	
	/* Helper method which determines which vertical collision
	 * the AI is standing on in the current frame. Used to determine
	 * where the edge of the "ground" is in destination(boolean) method.
	 */
	public Rectangle currentGround(List<Rectangle> currentNode)
	{
		for (Rectangle r : currentNode)
		{
			if (r.contains(worldbox.getCenterX() + getXOffset(), worldbox.getMaxY() + getYOffset()))
			{
				collisionBox = r;
				return collisionBox;
			}
		}
		
		return collisionBox;
	}
	
	/* Used to prevent AI from endlessly colliding with walls and such.
	 * This is another method that assists with pathing in the
	 * destination(boolean) method.
	 */
	public boolean approachingWall(boolean facingRight)
	{
		for (Rectangle r : DataRetriever.getWorld().getCollisionTree().retrieve(new ArrayList<Rectangle>(), getWorldbox()))
		{
			if (!facingRight && r.contains(worldbox.getMinX() - getXOffset(), worldbox.getCenterY()))
				return true;
			else if (!facingRight && r.contains(worldbox.getMinX() - 1, worldbox.getCenterY()))
				return true;
			else if (facingRight && r.contains(worldbox.getMaxX() + getXOffset(), worldbox.getCenterY()))
				return true;
			else if (facingRight && r.contains(worldbox.getMaxX() + 1, worldbox.getCenterY()))
				return true;
		}
		return false;
	}

	/* Determines if the player is within the vertical range of the AI.
	 * Aid to destination(boolean) method.
	 */
	public boolean playerInVerticalRange()
	{
		if (!(above(DataRetriever.getPlayer().getWorldbox().getCenterY()) || below(DataRetriever.getPlayer().getWorldbox().getCenterY())))
			return true;
		return false;
	}
	
	// Determines if the player is above the AI
	public boolean above(double y)
	{
		if (worldbox.getCenterY() < y) return true;
		return false;
	}
	//Same as above but below
	public boolean below(double y)
	{
		if (worldbox.getCenterY() > y) return true;
		return false;
	}
	
	//Method to power level an enemy up several times
	public void powerLevel(int levels)
	{
		while (levels-- > 0)
			levelUp();
	}

	//Levels an enemy up, increases stats
	protected void levelUp()
	{
		maxHealth *= 1.375;
		damage *= 1.25;
		health = maxHealth;
		level++;
	}

	//Method for when the enemy takes damage; should check for deaths & make health bars
	public void damageEnemy(int d)
	{
		health -= d;
	}
	
	// Method for drawing the enemy, to be overridden for each class to import jpgs
	public abstract void drawEnemy(Graphics2D g2d);

	//Method for retrieving the enemy's masked resistance value byte
	//From left to right: Blind, Electric, Fire, Frozen, Poison, Vulnerable, Weak, Instant-Kill
	//These flags should be set by declaring a byte with the notation 0bxxxxxxxx where every x is 1 if resist or 0 if not
	protected abstract byte getResistanceByte();

	//Draws in the enemy hitbox
	protected void drawHurtbox(Graphics2D g2d) //Draws the hurtbox where the enemy would be vulnerable
	{
		g2d.setColor(new Color(255, 0, 0, 100));
		g2d.fillRect((int) (worldbox.getX() - World.getDrawX()), (int) (worldbox.getY() - World.getDrawY()), width, height);
	}

	//@Override
	public String toString()
	{
		String output = getClassName() + "@" + Integer.toHexString(hashCode());
		output += String.format(" WX:%d WY:%d MH:%d H:%d LVL:%d", (int) worldX, (int) worldY, maxHealth, health, level);
		return output;
	}

	//@formatter:off
    //Getter methods
	public int getLastAttackFrame() {return lastAttackFrame;}
	
	//Resistance getters; Pulls flags out of the masked resistance byte to determine resistances
	public boolean resistBlind() {return (getResistanceByte() & 0x80) != 0;} //First byte is blindness
	public boolean resistElec() {return (getResistanceByte() & 0x40) != 0;} //Second byte is electricity
	public boolean resistFire() {return (getResistanceByte() & 0x20) != 0;} //Third byte is fire
	public boolean resistFreeze() {return (getResistanceByte() & 0x10) != 0;} //Fourth byte is freeze
	public boolean resistPoison() {return (getResistanceByte() & 0x8) != 0;} //Fifth byte is poison
	public boolean resistVuln() {return (getResistanceByte() & 0x4) != 0;} //Sixth byte is vulnerability (defense down)
	public boolean resistWeak() {return (getResistanceByte() & 0x2) != 0;} //Seventh byte is weakness (damage down)
	public boolean resistInsta() {return (getResistanceByte() & 0x1) != 0;} //Eighth byte is instant death
	
	//Setter methods
	public void delayLAF() {lastAttackFrame++;}
	//@formatter:on
}