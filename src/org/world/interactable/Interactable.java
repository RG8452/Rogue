package org.world.interactable;
/*
 * RG
 * This abstract class will be used to have anything that will be on a map but not be a player/enemy
 * This includes chests, man cannons, ladders, etc. 
 * It's not very functional but it will make polymorphism easier (probably)
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.world.World;

public abstract class Interactable extends Rectangle
{
	protected static int block = 32;
	protected int elapsedFrames, curAnimation;

	public abstract void interact();

	public abstract void draw(Graphics2D g2d);
	
	@Override
	public String toString() 
	{
		String output = "";
		output += "X: " + getX() + "\tY: " + getY();
		output += "W: " + getWidth() + "\tH: " + getHeight();
		return output;
	}
	
	public void drawHitbox(Graphics2D g2d)
	{
		g2d.setColor(new Color(255, 255, 255, 100));
		g2d.fillRect((int) (getX() - World.getDrawX()), (int) (getY() - World.getDrawY()), (int) getWidth(), (int) getHeight());
	}
}