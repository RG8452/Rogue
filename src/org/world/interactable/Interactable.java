package org.world.interactable;
/*
 * RG
 * This abstract class will be used to have anything that will be on a map but not be a player/enemy
 * This includes chests, man cannons, ladders, etc. 
 * It's not very functional but it will make polymorphism easier (probably)
 */

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Interactable
{
	protected Rectangle box;
	protected int block = 32;
	
	public abstract void interact();
	
	public abstract void draw(Graphics2D g2d);
	
	public Rectangle getZone() {return box;}
	public int getX() {return (int)box.getX();}
	public int getY() {return (int)box.getY();}
	public int getWidth() {return (int)box.getWidth();}
	public int getHeight() {return (int)box.getHeight();}
	
	@Override
	public String toString()
	{
		String output = "";
		output += "X: " + getX() + "\tY: " + getY();
		output += "W: " + getWidth() + "\tH: " + getHeight();
		return output;
	}
}
