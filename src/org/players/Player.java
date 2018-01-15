package org.players;
/**
 * RG
 * Abstract class that each player will extend
 * Will have the ground rules and fields that each object will need
 * Most of the variable names are either self-explanatory or just basic coordinate and stats
 *
 * NOTE: Later versions to include base stats like base damage, armor, jump velocity?, range [in hitbox]?, crit chance
 */

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Set;
import java.util.TreeSet;

import org.DataRetriever;

public abstract class Player
{
	protected int health, level, maxHealth, curAnimation, elapsedFrames, pWidth, pHeight, xOffset, yOffset; //Basic stats
	protected double x, y, xSpeed, ySpeed, jumpDelta;	//X and Y are doubles to keep absolute track of the players, while their drawing will be on ints
	protected boolean facingRight = true;	//Boolean for direction facing
	protected BufferedImage img = null;		//Buffered image drawn in animation
	protected BufferedImage[] lAnims = new BufferedImage[10];	//Array of all animations
	protected BufferedImage[] rAnims = new BufferedImage[10];
	protected Rectangle pHurtbox;			//Player's damage area or hurbox
	private static int framesPerAnimationCycle = 4;//Frames it takes for the animation drawn to change
	protected enum STATUS{IDLING, MOVING, JUMPING};//Enum used to store all possible outputs for the player's stauts
	protected STATUS status;			//Variable used for current status

	public void act()	//Reads through the set of all keys and the player moves accordingly
	{
		Set<Integer> readKeys = (TreeSet<Integer>)(DataRetriever.getAllKeys());
		
		//If standing on the ground, the player must be Idling
		if(readKeys.size() == 0 && onGround())
		{
			//This pattern is followed by most key checks: If already doing something, advance animation; else, begin animation
			if(status == STATUS.IDLING) {elapsedFrames = (elapsedFrames > 8 * framesPerAnimationCycle - 2) ? 0 : elapsedFrames + 1; curAnimation = (int)(elapsedFrames / framesPerAnimationCycle);}
			else {elapsedFrames = 0; curAnimation = 0; status = STATUS.IDLING;}
			
			return;	//return because you don't need to do anything else if the set is empty
		}
		
		//If right and !left, then must be walking right
		else if(readKeys.contains(DataRetriever.getRight()) && !readKeys.contains(DataRetriever.getLeft()))
		{	
			x += xSpeed;
			
			if(facingRight && status == STATUS.MOVING) {elapsedFrames = (elapsedFrames > 8 * framesPerAnimationCycle - 2) ? 0 : elapsedFrames + 1; curAnimation = (int)(elapsedFrames / framesPerAnimationCycle);}
			else {elapsedFrames = 0; curAnimation = 0; facingRight = true; status = STATUS.MOVING;}
		}
		
		//If left and !right, then must be walking left
		else if(readKeys.contains(DataRetriever.getLeft()) && !readKeys.contains(DataRetriever.getRight()))
		{	
			x -= xSpeed;
			
			if(!facingRight && status == STATUS.MOVING) {elapsedFrames = (elapsedFrames > 8 * framesPerAnimationCycle - 2) ? 0 : elapsedFrames + 1; curAnimation = (int)(elapsedFrames / framesPerAnimationCycle);}
			else{elapsedFrames = 0; curAnimation = 0; facingRight = false; status = STATUS.MOVING;}
		}
		
		//If right and left, set the player to idle
		else if(readKeys.contains(DataRetriever.getRight()) && readKeys.contains(DataRetriever.getLeft()))
		{
			if(status == STATUS.IDLING) {elapsedFrames = (elapsedFrames > 8 * framesPerAnimationCycle - 2) ? 0 : elapsedFrames + 1; curAnimation = (int)(elapsedFrames / framesPerAnimationCycle);}
			else {elapsedFrames = 0; curAnimation = 0; status = STATUS.IDLING;}
		}
		
		if(readKeys.contains(DataRetriever.getJump()) && onGround()) ySpeed -= jumpDelta;
		
		//If not touching the ground, status must be in mid-air so no animation is chosen
		if(!onGround()) status = STATUS.JUMPING;
		
		y += ySpeed;
		if(!onGround()) ySpeed += DataRetriever.getGravityConstant();
		else ySpeed = DataRetriever.getGravityConstant();
		
		if(onGround()) moveToGround();
		pHurtbox.setLocation((int)x + xOffset, (int)y + yOffset);
	}

	//Method to be overridden that draws each player by importing that file
	public abstract void drawPlayer(Graphics2D g2d);

	//Checks if the player is on the ground (Currently arbitrated to y == 800)
	public boolean onGround()
	{
		return ((int)y + pHeight >= 800);
	}
	
	//Move the player to be exactly on the ground if they're below the ground
	public void moveToGround()
	{
		while(onGround()) y -= .25;
		y += .25;
	}
	
	//All getters
	public double getX() {return x;}
	public double getY() {return y;}
	public int getHealth() {return health;}
	public int getLevel() {return level;}
	public int getMaxHealth() {return maxHealth;}
	public int getCurAnimation() {return curAnimation;}
	public double getXSpeed() {return xSpeed;}
	public double getYSpeed() {return ySpeed;}
	public Rectangle getHurtbox() {return pHurtbox;}
	
	//Setter methods
	public void setX(double nX) {x = nX;}
	public void setY(double nY) {y = nY;}
	public void setHealth(int nH) {health = nH;}
	public void setMaxHealth(int nMH) {maxHealth = nMH;}
	public void setXSpeed(double nXS) {xSpeed = nXS;}
	public void setYSpeed(double nYS) {ySpeed = nYS;}
}