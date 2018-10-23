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

import org.DataRetriever;
import org.entities.Entity;
import org.world.World;

public abstract class Enemy extends Entity
{
	protected int lastAttackFrame; //Frame at which the enemy began attacking
	protected boolean inFlight; //True if the enemy can fly
	protected static int framesPerAnimationCycle = 4; //Frames that elapse between each change in animation

	//Enum used to store all possible outputs for the enemy's stauts
	protected enum STATUS
	{
		IDLING, PATHING, JUMPING, ATTACKING, CLIMBING
	}

	protected STATUS status; //Variable used for current status
	protected double pWX, pWY; //Player coords for reference when pathing
	protected double pXMid = pWX + DataRetriever.getPlayer().getWidth() / 2 + DataRetriever.getPlayer().getXOffset();

	//Returns true if the enemy can fly or is on the ground
	public boolean onGround()
	{
		if (inFlight) return false; //If the enemy can fly, it doesn't matter
		else
		{
			// Determine if the enemy is on the ground
			return true;
		}
	}
	
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
		String output = getClassName() + "@" + Integer.toHexString(this.hashCode());
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