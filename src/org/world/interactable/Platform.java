package org.world.interactable;

/*
 * RG
 * This is an interactable that functions like a normal block
 * Except when a player is standing in it and its down and jump, 
 * They fall through
 */
import java.awt.Graphics2D;

public class Platform extends Interactable
{
	private boolean transparent;

	public Platform(int x, int y, int w)
	{
		this.setRect(x * block, y * block - 2, w * block, 10);
		transparent = true;
	}

	@Override
	public void interact()
	{
		//Interaction done in Player.act()
	}

	@Override
	public void draw(Graphics2D g2d)
	{
		//Drawing is done with foreground.png
	}

	//@formatter:off
	public void setTransparent(boolean b) {transparent = b;}
	public boolean getTransparent() {return transparent;}
	//@formatter:on
}
