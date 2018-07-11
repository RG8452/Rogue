package org.entities.enemies.crossbowman;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.Hitbox;
import org.Startup;
import org.world.World;

public class CrossbowBolt
{
	private static int width = 12, height = 6, xOffset = 75, yOffset = 16;
	private int worldX, worldY, damage;
	private BufferedImage bolt;
	private boolean facingRight;
	private Hitbox hbox;
	Crossbowman parent;
	
	public CrossbowBolt(int h, int k, boolean right, int d, Crossbowman c)
	{
		worldX = h;
		worldY = k;
		damage = d;
		hbox = new Hitbox(h + xOffset, k + yOffset, width, height);
		parent = c;
		try
		{
			if(facingRight)
				bolt = ImageIO.read(new File("src/org/entities/enemies/crossbowman/Animations/RightFacing/Attack/RBolt.png"));
			else
				bolt = ImageIO.read(new File("src/org/entities/enemies/crossbowman/Animations/LeftFacing/Attack/LBolt.png"));
		}
		catch(IOException e) {System.out.println("Image reading error (CrossbowBolt): " + e);}
	}
	
	public void draw(Graphics2D g)
	{
		g.drawImage(bolt, worldX - (int) World.getDrawX(), worldY - (int) World.getDrawY(), null);
		g.setColor(new Color(0, 40, 255, 110));
		if (Startup.getRunner().hitboxesEnabled()) g.fillRect((int) (hbox.getX() - World.getDrawX()), (int) (hbox.getY() - World.getDrawY()), width, height);
	}

	public void render()
	{
		if(hbox.render(damage, true))
			parent.destroyBolt();
		worldX += (facingRight) ? 8 : -8;
		hbox.setLocation(worldX + xOffset, worldY + yOffset);
	}
}
