package org.players;
/**
 * RG
 * Abstract class that each player will extend
 * Will have the ground rules and fields that each object will need
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
	protected int health, level, maxHealth, curAnimation, elapsedFrames, pWidth, pHeight; //Basic stats
	protected double x, y, xSpeed, ySpeed;	//X and Y are doubles to keep absolute track of the players, while their drawing will be on ints
	protected String status = "Idling"; 	//String to store player's status: Jumping, Moving, Idling
	protected boolean facingRight = true;	//Boolean for direction facing
	protected BufferedImage img = null;		//Buffered image drawn in animation
	protected Rectangle pHurtbox;			//Player's damage area or hurbox
	private static int framesPerAnimationCycle = 4;//Frames it takes for the animation drawn to change

	public void act()	//Reads through the set of all keys and the player moves accordingly
	{
		Set<Integer> readKeys = (TreeSet<Integer>)(DataRetriever.getAllKeys());
		
		//If standing on the ground, the player must be Idling
		if(readKeys.size() == 0 && onGround())
		{
			//This pattern is followed by most key checks: If already doing something, advance animation; else, begin animation
			if(status.equals("Idling")) {elapsedFrames = (elapsedFrames > 8 * framesPerAnimationCycle - 2) ? 0 : elapsedFrames + 1; curAnimation = (int)(elapsedFrames / framesPerAnimationCycle);}
			else {elapsedFrames = 0; curAnimation = 0; status = "Idling";}
			
			return;	//return because you don't need to do anything else if the set is empty
		}
		
		//If right and !left, then must be walking right
		else if(readKeys.contains(DataRetriever.getRight()) && !readKeys.contains(DataRetriever.getLeft()))
		{	
			x += xSpeed;
			
			if(facingRight && status.equals("Moving")) {elapsedFrames = (elapsedFrames > 8 * framesPerAnimationCycle - 2) ? 0 : elapsedFrames + 1; curAnimation = (int)(elapsedFrames / framesPerAnimationCycle);}
			else {elapsedFrames = 0; curAnimation = 0; facingRight = true; status = "Moving";}
		}
		
		//If left and !right, then must be walking left
		else if(readKeys.contains(DataRetriever.getLeft()) && !readKeys.contains(DataRetriever.getRight()))
		{	
			x -= xSpeed;
			
			if(!facingRight && status.equals("Moving")) {elapsedFrames = (elapsedFrames > 8 * framesPerAnimationCycle - 2) ? 0 : elapsedFrames + 1; curAnimation = (int)(elapsedFrames / framesPerAnimationCycle);}
			else{elapsedFrames = 0; curAnimation = 0; facingRight = false; status = "Moving";}
		}
		
		//If right and left, set the player to idle
		else if(readKeys.contains(DataRetriever.getRight()) && readKeys.contains(DataRetriever.getLeft()))
		{
			if(status.equals("Idling")) {elapsedFrames = (elapsedFrames > 8 * framesPerAnimationCycle - 2) ? 0 : elapsedFrames + 1; curAnimation = (int)(elapsedFrames / framesPerAnimationCycle);}
			else {elapsedFrames = 0; curAnimation = 0; status = "Idling";}
		}
		
		//If not touching the ground, status must be in mid-air so no animation is chosen
		if(!onGround()) status = "Jumping";
	}

	//Method to be overridden that draws each player by importing that file
	public abstract void drawPlayer(Graphics2D g2d);

	//Checks if the player is on the ground
	public boolean onGround()
	{
		return true;
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
	public void setXSpeed(double nXS) {xSpeed = nXS;}
	public void setYSpeed(double nYS) {ySpeed = nYS;}
}