package org.world.dwarvencaverns;
/*
 * RG
 * World class for the dwarven caverns stage
 * Block, interactable, and image composition done in construction
 */

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.world.QuadTree;
import org.world.World;
import org.world.interactable.Ladder;
import org.world.interactable.ManCannon;

public class DwarvenCaverns extends World
{
	// Note: 192 x 54 blocks in total
	// Spawn should be set as TL of pHitbox is 184, 33
	private final int worldWidth = 6144, worldHeight = 1728; // Data for exact size of the world

	public DwarvenCaverns()
	{
		images = new BufferedImage[3];
		try // Reads in available images
		{
			images[0] = ImageIO.read(new File("src/org/world/dwarvencaverns/Map/CavernsBackGround.png"));
			images[1] = ImageIO.read(new File("src/org/world/dwarvencaverns/Map/CavernsMidGround.png"));
			images[2] = ImageIO.read(new File("src/org/world/dwarvencaverns/Map/CavernsForeGround.png"));
		}
		catch (IOException e)
		{
			System.out.println("IMAGE READING ERROR (DCaverns): " + e);
		}

		fullMap = new Rectangle(0, 0, worldWidth, worldHeight); // Instantiate various objects
		spawnX = 5824;
		spawnY = 1056;
		filePath = "src/org/world/dwarvencaverns/DwarvenCaverns.txt";

		readMap();
	}
}