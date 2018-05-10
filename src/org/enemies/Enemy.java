package org.enemies;
/**
 * RG
 * Abstract class that each enemy will extend
 * Will lay the ground rules and basic methods that all enemy objects will use
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.world.World;

public abstract class Enemy
{
	protected int maxHealth, health, curAnimation, elapsedFrames, lastAttackFrame, eWidth, eHeight, xOffset, yOffset, level; //Basic stats for health and animation
	protected double worldX, worldY, x, y, xSpeed, ySpeed; //Position stored as double but drawn as int to maintain absolute accuracty
	protected boolean canFly, facingRight; //Booleans for direction facing as well as flight
	protected BufferedImage img; //Image to be drawn by each class
	protected BufferedImage[] rAnims; //Arrays for left and right images
	protected BufferedImage[] lAnims;
	protected Rectangle eWorldbox; //Rectangle for checking pos in world
	protected static int framesPerAnimationCycle = 4; //Frames that elapse between each change in animation

	//Enum used to store all possible outputs for the enemy's stauts
	protected enum STATUS
	{
		IDLING, MOVING, JUMPING, ATTACKING
	};

	protected STATUS status; //Variable used for current status
	protected double pWX, pWY; //Player coords for reference when pathing

	public void act()
	{
		//Methods for making the enemy act
	}

	public boolean onGround()
	{
		if (canFly) return true; //If the enemy can fly, it doesn't matter
		else
		{
			// Determine if the enemy is on the ground
			return true;
		}
	}

	// Method for drawing the enemy, to be overridden for each class to import jpgs
	public abstract void drawEnemy(Graphics2D g2d);

	protected void drawHurtbox(Graphics2D g2d) // Draws the hurtbox where the enemy would be vulnerable
	{
		g2d.setColor(new Color(255, 0, 0, 100));
		g2d.fillRect((int)(eWorldbox.getX() - World.getDrawX()), (int)(eWorldbox.getY() - World.getDrawY()), eWidth, eHeight);
	}

	//@formatter:off
	// Getter methods
	public double getX() {return x;}
	public double getY() {return y;}
	public int getHealth() {return health;}
	public int getMaxHealth() {return maxHealth;}
	public int getCurAnimation() {return curAnimation;}
	public int getLastAttackFrame() {return lastAttackFrame;}
	public double getXSpeed() {return xSpeed;}
	public double getYSpeed() {return ySpeed;}
	public Rectangle getWorldbox() {return eWorldbox;}

	// Setter methods
	public void setX(double nX) {x = nX;}
	public void setY(double nY) {y = nY;}
	public void setHealth(int nH) {health = nH;}
	public void damage(int d) {health -= d;}
	public void setXSpeed(double nXS) {xSpeed = nXS;}
	public void setYSpeed(double nYS) {ySpeed = nYS;}
	public void delayLAF() {lastAttackFrame++;}
	//@formatter:on
}