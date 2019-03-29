package org.world.cavecity;

/*
 * RG
 * World class for the cave city stage
 * Construction creates QuadTree, interactables, etc
 * 
 * TODO: implement midground, ladder pngt
 * TODO: finish making world with blocks
 */
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.world.QuadTree;
import org.world.World;
import org.world.interactable.Ladder;
import org.world.interactable.Platform;

public class CaveCity extends World
{
	private final int worldWidth = 6400, worldHeight = 2240;

	public CaveCity()
	{
		images = new BufferedImage[3];
		try // Reads in available images
		{
			images[0] = ImageIO.read(new File("src/org/world/cavecity/Map/CaveCityBackGround.png"));
			images[1] = ImageIO.read(new File("src/org/world/cavecity/Map/CaveCityMidGround.png"));
			images[2] = ImageIO.read(new File("src/org/world/cavecity/Map/CaveCityForeGround.png"));
		}
		catch (IOException e)
		{
			System.out.println("IMAGE READING ERROR (CaveCity): " + e);
		}

		fullMap = new Rectangle(0, 0, worldWidth, worldHeight);
		spawnX = 6208;
		spawnY = 2120;
		filePath = "src/org/world/cavecity/CaveCity.txt";

		readMap();
	}
}
