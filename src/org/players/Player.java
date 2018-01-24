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
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import org.DataRetriever;
import org.panels.GamePanel;
import org.world.World;

public abstract class Player
{
	protected int health, level, maxHealth, curAnimation, elapsedFrames, pWidth, pHeight, xOffset, yOffset; //Basic stats
	protected double x, y, worldX, worldY, xSpeed, ySpeed, jumpDelta;	//X and Y are doubles to keep absolute track of the players, while their drawing will be on ints
	protected boolean facingRight = true, onGround = false;	//Boolean for direction facing and ground checking
	protected BufferedImage img = null;		//Buffered image drawn in animation
	protected BufferedImage[] lAnims;	//Array of all animations
	protected BufferedImage[] rAnims;
	protected Rectangle pHurtbox;			//Player's damage area or hurbox
	private static int framesPerAnimationCycle = 4;//Frames it takes for the animation drawn to change
	protected enum STATUS{IDLING, MOVING, JUMPING, CROUCHED};//Enum used to store all possible outputs for the player's stauts
	protected STATUS status;			//Variable used for current status

	public void act()	//Reads through the set of all keys and the player moves accordingly
	{
		Set<Integer> readKeys = (TreeSet<Integer>)(DataRetriever.getAllKeys());
		
		//If standing on the ground, the player must be Idling
		if(readKeys.size() == 0 && onGround)
		{
			//This pattern is followed by most key checks: If already doing something, advance animation; else, begin animation
			if(status == STATUS.IDLING) {elapsedFrames = (elapsedFrames > 8 * framesPerAnimationCycle - 2) ? 0 : elapsedFrames + 1; curAnimation = (int)(elapsedFrames / framesPerAnimationCycle);}
			else {elapsedFrames = 0; curAnimation = 0; status = STATUS.IDLING;}
			
			return;	//return because you don't need to do anything else if the set is empty
		}
		
		else if(readKeys.contains(DataRetriever.getDown()) && onGround) {status = STATUS.CROUCHED;}
		
		//If right and !left, then must be walking right
		else if(readKeys.contains(DataRetriever.getRight()) && !readKeys.contains(DataRetriever.getLeft()) && !(status == STATUS.CROUCHED))
		{	
			worldX += xSpeed;
			if(worldX > DataRetriever.getWorld().getWidth() - GamePanel.hScreenX) x = GamePanel.hScreenX + (GamePanel.hScreenX - (DataRetriever.getWorld().getWidth() - worldX));
			else if(worldX < GamePanel.hScreenX) x = worldX;
			
			if(facingRight && status == STATUS.MOVING) {elapsedFrames = (elapsedFrames > 8 * framesPerAnimationCycle - 2) ? 0 : elapsedFrames + 1; curAnimation = (int)(elapsedFrames / framesPerAnimationCycle);}
			else {elapsedFrames = 0; curAnimation = 0; facingRight = true; status = STATUS.MOVING;}
		}
		
		//If left and !right, then must be walking left
		else if(readKeys.contains(DataRetriever.getLeft()) && !readKeys.contains(DataRetriever.getRight()) && !(status == STATUS.CROUCHED))
		{	
			worldX -= xSpeed;
			if(worldX < GamePanel.hScreenX) x = worldX;
			else if(worldX > DataRetriever.getWorld().getWidth() - GamePanel.hScreenX) x = worldX - World.getDrawX();
			
			if(!facingRight && status == STATUS.MOVING) {elapsedFrames = (elapsedFrames > 8 * framesPerAnimationCycle - 2) ? 0 : elapsedFrames + 1; curAnimation = (int)(elapsedFrames / framesPerAnimationCycle);}
			else{elapsedFrames = 0; curAnimation = 0; facingRight = false; status = STATUS.MOVING;}
		}
		
		//If right and left, set the player to idle
		else if(readKeys.contains(DataRetriever.getRight()) && readKeys.contains(DataRetriever.getLeft()))
		{
			if(status == STATUS.IDLING) {elapsedFrames = (elapsedFrames > 8 * framesPerAnimationCycle - 2) ? 0 : elapsedFrames + 1; curAnimation = (int)(elapsedFrames / framesPerAnimationCycle);}
			else {elapsedFrames = 0; curAnimation = 0; status = STATUS.IDLING;}
		}
		
		//If the player jumps, add a ton to their y velocity
		if(readKeys.contains(DataRetriever.getJump()) && onGround) ySpeed -= jumpDelta;
		
		//If not touching the ground, status must be in mid-air so no animation is chosen
		if(!onGround) status = STATUS.JUMPING;
		
		pHurtbox.setLocation((int)x + xOffset, (int)y + yOffset);
		for(Rectangle r: DataRetriever.getWorld().getCollisionTree().retrieve(new ArrayList<Rectangle>(), pHurtbox))
		{ 
			Rectangle2D r2d = (Rectangle2D)(new Rectangle((int)(r.getX() - World.getDrawX()), (int)(r.getY() - World.getDrawY()), (int)r.getWidth(), (int)r.getHeight()));
			while(pHurtbox.intersects(r2d)) //pHurtbox.intersects(r)
			{
				worldX = facingRight ? worldX - 1 : worldX + 1;
				if(worldX < GamePanel.hScreenX) x = worldX;
				else if(worldX > DataRetriever.getWorld().getWidth() - GamePanel.hScreenX) x = worldX - World.getDrawX();
				else x = GamePanel.hScreenX;
				pHurtbox.setLocation((int)x + xOffset, (int)y + yOffset);	
			}
		}
		
		worldY += ySpeed;

		if(worldY < GamePanel.hScreenY) y = worldY;
		else if(worldY > DataRetriever.getWorld().getHeight() - GamePanel.hScreenY) y = worldY - World.getDrawY();
		else y = worldY + pHeight;
		
		onGround = false;
		
		pHurtbox.setLocation((int)x + xOffset, (int)y + yOffset);
		for(Rectangle r: DataRetriever.getWorld().getCollisionTree().retrieve(new ArrayList<Rectangle>(), pHurtbox))
		{
			Rectangle2D r2d = (Rectangle2D)(new Rectangle((int)(r.getX() - World.getDrawX()), (int)(r.getY() - World.getDrawY()), (int)r.getWidth(), (int)r.getHeight()));
			while(pHurtbox.intersects(r2d)) //pHurtbox.intersects(r)
			{
				worldY = ySpeed > 0 ? worldY - 1 : worldY + 1;
				if(worldY < GamePanel.hScreenY) y = worldY;
				else if(worldY > DataRetriever.getWorld().getHeight() - GamePanel.hScreenY) y = worldY - World.getDrawY();
				else y = GamePanel.hScreenY;
				pHurtbox.setLocation((int)x + xOffset, (int)y + yOffset);
				if(ySpeed > 0) onGround = true;
			}
		}
		
		if(onGround) {ySpeed = DataRetriever.getGravityConstant();}
		else {ySpeed += DataRetriever.getGravityConstant();}
	}

	//Method to be overridden that draws each player by importing that file
	public abstract void drawPlayer(Graphics2D g2d);
	
	//All getters
	public double getX() {return x;} 
	public double getY() {return y;}
	public double getWorldX() {return worldX;}
	public double getWorldY() {return worldY;}
	public int getXOffset() {return xOffset;}
	public int getHealth() {return health;}
	public int getLevel() {return level;}
	public int getMaxHealth() {return maxHealth;}
	public int getCurAnimation() {return curAnimation;}
	public double getXSpeed() {return xSpeed;}
	public double getYSpeed() {return ySpeed;}
	public Rectangle getHurtbox() {return pHurtbox;}
	public int getWidth() {return pWidth;}
	public int getHeight() {return pHeight;}
	
	//Setter methods
	public void setX(double nX) {x = nX;}
	public void setY(double nY) {y = nY;}
	public void setWorldX(double nWX) {worldX = nWX;}
	public void setWorldY(double nWY) {worldY = nWY;}
	public void setHealth(int nH) {health = nH;}
	public void setMaxHealth(int nMH) {maxHealth = nMH;}
	public void setXSpeed(double nXS) {xSpeed = nXS;}
	public void setYSpeed(double nYS) {ySpeed = nYS;}
}