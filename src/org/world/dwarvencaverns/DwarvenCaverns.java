package org.world.dwarvencaverns;
/*
 * World class for the dwarven caverns stage
 */

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.world.QuadTree;
import org.world.World;

public class DwarvenCaverns extends World
{
	private final int worldWidth = 6144, worldHeight = 1152;
	
	public DwarvenCaverns()
	{
		try
		{
			background = ImageIO.read(new File("src/org/world/dwarvencaverns/CavernsBackGround.png"));
			midground = ImageIO.read(new File("src/org/world/dwarvencaverns/CavernsMidGround.png"));
			foreground = ImageIO.read(new File("src/org/world/dwarvencaverns/CavernsForeGround.png"));
		}
		catch(IOException e) {System.out.println("IMAGE READING ERROR (DCaverns): " + e);}
		
		fullMap = new Rectangle(0, 0, worldWidth, worldHeight);
		worldCollision = new QuadTree(0, fullMap);
	}
}
