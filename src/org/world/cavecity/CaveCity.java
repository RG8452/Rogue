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
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.world.QuadTree;

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
			midground = background;
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
		QTAdd(0, 0, worldWidth, block);
		QTAdd(0, 0, block, worldHeight);
		QTAdd(worldWidth - block, 0, block, worldHeight);
		QTAdd(0, worldHeight - block, worldWidth, block);
		
		//BLOCKS
		//BL Building
		QTAddB(1, 44, 1, 25);
		QTAddB(1, 44, 7, 1);
		QTAddB(7, 44, 1, 5);
		QTAddB(7, 51, 1, 7);
		QTAddB(7, 60, 1, 7);
		ITAdd(new Platform(2, 51, 5));
		ITAdd(new Platform(4, 53, 3));
		ITAdd(new Platform(2, 55, 3));
		ITAdd(new Platform(4, 57, 3));
		ITAdd(new Platform(2, 60, 5));
		ITAdd(new Platform(2, 62, 3));
		ITAdd(new Platform(4, 64, 3));
		ITAdd(new Platform(2, 66, 3));
		
		//BL floor
		QTAddB(1, 42, 12, 2);
		QTAddB(13, 42, 7, 1);
		QTAddB(20, 42, 4, 2);
		QTAddB(8, 51, 3, 2);
		QTAddB(11, 51, 7, 1);
		QTAddB(8, 60, 10, 2);
	}
}
