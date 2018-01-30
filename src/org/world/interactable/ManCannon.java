package org.world.interactable;
/*
 * RG
 * It's a man cannon
 * It blasts men upwards
 * Each man cannon will have a unique field for y velocity subtracted 
 */

import java.awt.Rectangle;
import java.awt.Graphics2D;

public class ManCannon extends Interactable
{
	private double upDelta;
	private int curAnimation, elapsedAnimationFrames, framesPerAnimation = 4;	//Animation info
	
	public ManCannon(int x, int y, int upBlocks)
	{
		box = new Rectangle(block * x, block * y - 2, block, block * 2);
		upDelta = upBlocks * 5;
	}
	
	@Override
	public void interact()
	{
		//You can't actually interact with man cannons, so the processing will be done in Player.act()
	}
	
	@Override
	public void draw(Graphics2D g2d)
	{
		//TODO: Determine map state, read in images, do animation, etc
	}
	
	public double getUpDelta() {return upDelta;}
}