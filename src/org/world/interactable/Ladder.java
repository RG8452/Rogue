package org.world.interactable;
/*
 * RG
 * Ladder class
 * It has ladders n' ladders
 * Each ladder is composed of an x, y, and height
 * If the player hits up or down while near a ladder, they will begin to climb. 
 */

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Ladder extends Interactable
{
	public Ladder(int x, int y, int height)
	{
		box = new Rectangle((int) (block * (x + .25)), block * y - 1, (int) (block / 2), block * height + 1);
	}

	@Override
	public void interact()
	{
		// Ladders don't do much, and all climbing/animating is done in the player act() method
	}

	@Override
	public void draw(Graphics2D g2d)
	{
		// The drawing is taken care of by drawing the obs image in the World class
	}
}