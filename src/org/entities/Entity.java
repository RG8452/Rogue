package org.entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.Hitbox;

public abstract class Entity
{
	protected int curAnimation; //Frame of animation that the entity is on
	protected int elapsedFrames; //Frames elapsed since last animation change
	protected int xOffset; //Offset of sprite drawing vs. hitbox generation, x
	protected int yOffset; //Same statistic as above, but vertically
	protected int width; //Width of the entity
	protected int height; //Height of the entity
	protected int level; //Entity's level, adjusts statistics
	protected int health; //Statistic that stores current Health (damage until dead)
	protected int maxHealth; //Statistic to store maximum health
	protected int baseDamage = getBaseDamage(); //Statistic to store the base damage of the entity
	protected int damage; //Statistic which stores actual damage
	protected double armor; //Number representing the defense of the enemy 
	protected double worldX; //Horizontal positioning of the enemy in the world
	protected double worldY; //Vertical positioning of the enemy in the world
	protected double xSpeed; //Speed at which the enemy moves horizontally
	protected double ySpeed; //Speed at which the enemy moves vertically
	protected boolean facingRight = true; //Boolean to store direction of orientation
	protected boolean onGround; //Boolean which stores whether or not the enemy is on the ground
	protected BufferedImage img = null; //Buffered image drawn in animation

	protected Rectangle worldbox; //The area in which the entity can be damaged
	protected Hitbox hitbox; //Hitbox that the entity is generating

	public abstract void act(); //Method that makes entities move

	//@formatter:off
	//Getter methods
	public int getXOffset() {return xOffset;}
	public int getYOffset() {return yOffset;}
	public int getHealth() {return health;}
	public int getLevel() {return level;}
	public int getMaxHealth() {return maxHealth;}
	public int getCurAnimation() {return curAnimation;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	public double getXSpeed() {return xSpeed;}
	public double getYSpeed() {return ySpeed;}
	public double getWorldX() {return worldX;}
	public double getWorldY() {return worldY;}
	public Rectangle getWorldbox() {return worldbox;}
	
	protected abstract int getBaseDamage();
	protected abstract int getBaseHealth();
	protected abstract String getClassName(); //Returns the class name of the enemy
	
	//Setter methods
	public void setWorldX(double nWX) {worldX = nWX;}
	public void setWorldY(double nWY) {worldY = nWY;}
	public void setHealth(int nH) {health = nH;}
	public void setMaxHealth(int nMH) {maxHealth = nMH;}
	public void setXSpeed(double nXS) {xSpeed = nXS;}
	public void setYSpeed(double nYS) {ySpeed = nYS;}
	//@formatter:on
}
