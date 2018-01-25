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
	//Note: 192 x 54
	private final int worldWidth = 6144, worldHeight = 1728;	//Data for exact size of the world
	
	public DwarvenCaverns()
	{
		try		//Reads in available images
		{
			background = ImageIO.read(new File("src/org/world/dwarvencaverns/CavernsBackGround.png"));
			midground = ImageIO.read(new File("src/org/world/dwarvencaverns/CavernsMidGround.png"));
			foreground = ImageIO.read(new File("src/org/world/dwarvencaverns/CavernsForeGround.png"));
			
		}
		catch(IOException e) {System.out.println("IMAGE READING ERROR (DCaverns): " + e);}
		
		fullMap = new Rectangle(0, 0, worldWidth, worldHeight);	//Instantiate various objects
		worldCollision = new QuadTree(0, fullMap);
		
		//OUTER WALLS
		QTAdd(0, 0, worldWidth, block);
		QTAdd(0, 0, block, worldHeight);
		QTAdd(worldWidth - block, 0, block, worldHeight);
		QTAdd(0, worldHeight - block, worldWidth, block);
		
		//CORRECT POSITION
		QTAddB(1, 4, 3, 1);
		QTAddB(1, 8, 5, 1);
		QTAddB(1, 12, 7, 1);
		QTAddB(1, 16, 19, 1);
		QTAddB(20, 16, 39, 4);
		QTAddB(20, 20, 1, 9);
		QTAddB(20, 29, 42, 1);  
		QTAddB(21, 30, 1, 2);	
		QTAdd(59 * block, (int)(16.5 * block), block, (int)(3.5 * block));	
		QTAddB(60, 17, 1, 3);
		QTAddB(61, 18, 1, 2);
		QTAdd(62 * block, (int)(18.5 * block), 17 * block, (int)(1.5 * block));
		QTAddB(74, 18, 4, 1);
		QTAdd(75 * block, (int)(17.5 * block), 2 * block, (int)(block / 2));
		QTAddB(79, 19, 1, 1);
		QTAddB(9, 8, 1, 1);
		QTAddB(13, 8, 2, 1);
		QTAddB(19, 8, 1, 1);
		QTAddB(24, 8, 1, 1);
		QTAddB(28, 8, 3, 1);
		QTAddB(34, 8, 1, 1);
		QTAddB(39, 8, 1, 1);
		QTAddB(44, 8, 1, 1);
		QTAddB(49, 8, 49, 1);
		QTAddB(7, 4, 42, 1);
		QTAddB(65, 29, 36, 1);
		QTAddB(23, 24, 74, 1);
		
		//Bottom up
		QTAddB(1, 49, 34, 1);
		QTAddB(34, 48, 5, 1);
		QTAddB(38, 47, 10, 1);
		QTAddB(47, 48, 2, 1);
		QTAddB(48, 49, 9, 1);
		QTAddB(1, 45, 32, 1);
		QTAddB(32, 44, 5, 1);
		QTAddB(36, 43, 14, 1);
		QTAddB(49, 44, 2, 1);
		QTAddB(50, 45, 35, 1);
	}
}