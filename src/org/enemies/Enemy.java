package org.enemies;
/**
 * RG
 * Abstract class that each enemy will extend
 * Will lay the ground rules and basic methods that all enemy objects will use
 */



import java.awt.Graphics2D;
import java.awt.Image;

public abstract class Enemy 
{
	protected int maxHealth, health, curAnimation, elapsedAnimationFrames;	//Basic stats for health and animation
	protected double x, y, xSpeed, ySpeed;	//Position stored as double but drawn as int to maintain absolute accuracty
	protected boolean canFly, facingRight;	//Booleans for direction facing as well as flight
	protected String status = "Idling";		//String to represent status of enemy, will be "Idling", "Moving", "Damaged", "Attacking?"
	protected Image img;					//Image to be drawn by each class
	private static int framesPerAnimation = 4;	//Frames that elapse between each change in animation
	
    protected void act()
    {
    	//Methods for making the enemy act
    }
    
    public boolean onGround()
    {
    	if(canFly) return true;	//If the enemy can fly, it doesn't matter
    	else
    	{
    		//Determine if the enemy is on the ground
    		return true;
    	}
    }
    
    //Method for drawing the enemy, to be overridden for each class to import jpgs
    protected abstract void drawEnemy(Graphics2D g2d);	
    
    //Getter methods
    public double getX() {return x;}
	public double getY() {return y;}
	public int getHealth() {return health;}
	public int getMaxHealth() {return maxHealth;}
	public int getCurAnimation() {return curAnimation;}
	public double getXSpeed() {return xSpeed;}
	public double getYSpeed() {return ySpeed;}
	
	//Setter methods
	public void setX(double nX) {x = nX;}
	public void setY(double nY) {y = nY;}
	public void setHealth(int nH) {health = nH;}
	public void setXSpeed(double nXS) {xSpeed = nXS;}
	public void setYSpeed(double nYS) {ySpeed = nYS;}
}