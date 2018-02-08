package org.world.interactable;
/*
 * RG
 * This is an interactable that functions like a normal block
 * Except when a player is standing in it and its down and jump, 
 * They fall through
 */
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Platform extends Interactable
{
	public Platform(int x, int y, int w)
	{
		box = new Rectangle(block * x, block * y - 2, block * w, 5);
	}

	@Override
	public void interact()
	{
		//Interaction done in Player.act()
	}

	@Override
	public void draw(Graphics2D g2d)
	{
		drawHitbox(g2d);
	}
}
