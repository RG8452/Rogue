package org.world.cavecity;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.world.QuadTree;
/*
 * RG
 * World class for the cave city stage
 * Construction creates QuadTree, interactables, etc
 * 
 * TODO: implement midground, ladder pngt
 * TODO: finish making world with blocks
 */
import org.world.World;
import org.world.interactable.Platform;

public class CaveCity extends World
{
	private final int worldWidth = 6400, worldHeight = 2240;
	
	public CaveCity()
	{
		try // Reads in available images
		{
			background = ImageIO.read(new File("src/org/world/cavecity/Map/CaveCityBackGround.png"));
			//midground = ImageIO.read(new File("src/org/world/cavecity/Map/CaveCityMidGround.png"));
			foreground = ImageIO.read(new File("src/org/world/cavecity/Map/CaveCityForeGround.png"));
		}
		catch (IOException e)
		{
			System.out.println("IMAGE READING ERROR (CaveCity): " + e);
		}
		
		fullMap = new Rectangle(0, 0, worldWidth, worldHeight);
		worldCollision = new QuadTree(0, fullMap);
		interCollision = new QuadTree(0, fullMap);
		spawnX = 96;
		spawnY = 384;
		
		//OUTER WALLS
		QTAddB(0, 0, worldWidth, block);
		QTAddB(0, 0, block, worldHeight);
		QTAddB(worldWidth - block, 0, block, worldHeight);
		QTAddB(0, worldHeight - block, worldWidth, block);
		
		//BLOCKS
		//BL Building
		QTAddB(1, 174, 1, 25);
		QTAddB(1, 174, 7, 1);
		QTAddB(8, 174, 1, 5);
		QTAddB(8, 181, 1, 7);
		QTAddB(8, 190, 1, 7);
		ITAdd(new Platform(2, 181, 5));
		ITAdd(new Platform(4, 183, 3));
		ITAdd(new Platform(2, 185, 3));
		ITAdd(new Platform(4, 187, 3));
		ITAdd(new Platform(2, 191, 5));
		ITAdd(new Platform(2, 193, 3));
		ITAdd(new Platform(4, 195, 3));
		ITAdd(new Platform(2, 197, 3));
	}
}
