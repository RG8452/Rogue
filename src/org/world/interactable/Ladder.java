package org.world.interactable;
/*
 * RG
 * Ladder class
 * It has ladders n' ladders
 * Each ladder is composed of an x, y, and height
 * If the player hits up or down while near a ladder, they will begin to climb. 
 */

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.Startup;
import org.panels.GamePanel;
import org.world.World;

public class Ladder extends Interactable
{
	private BufferedImage img;
	private int height, wX, wY;

	public Ladder(int x, int y, int h, String type)
	{
		this.height = h;
		this.wX = x * block;
		this.wY = y * block;
		img = null;
		this.setRect(wX + block/4, wY, block/2, height * block);

		if (type.equals("dc"))
		{
			try
			{
				img = ImageIO.read(new File("src/org/world/dwarvencaverns/Interactables/Ladder.png"));
			}
			catch (IOException e)
			{
				System.out.println("IMAGE READING ERROR (DwarvenCaverns Ladder): " + e);
			}
		}
		else if(type.equals("cc"))
		{
			try
			{
				img = ImageIO.read(new File("src/org/world/cavecity/Interactables/Ladder.png"));
			}
			catch (IOException e)
			{
				System.out.println("IMAGE READING ERROR (CaveCity Ladder): " + e);
			}
		}
	}

	@Override
	public void interact()
	{
		// Ladders don't do much, and all climbing/animating is done in the player act() method
	}

	@Override
	public void draw(Graphics2D g2d)
	{
		if (wX + block > World.getDrawX() && wX < World.getDrawX() + GamePanel.screenX)
		{
			if (wY + block * height > World.getDrawY() && wY < World.getDrawY() + GamePanel.screenY)
			{
				for (int lad = 0; lad < height; lad++)
				{
					g2d.drawImage(img, (int) (wX - World.getDrawX()), (int) (wY - World.getDrawY() + block * lad), null);
				}
			}
		}
		if (Startup.getRunner().worldboxesEnabled()) drawHitbox(g2d);
	}
}