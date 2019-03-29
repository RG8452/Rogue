package org.world.humanvillage;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.world.World;

//HumanVillage class, this is the first world!
//It reads in its fore-, back-, and mid-grounds
//Then it declares some non-static vars and reads in the map
public class HumanVillage extends World
{
	private final int worldWidth = 5600, worldHeight = 2560;
	
	public HumanVillage()
	{
		images = new BufferedImage[3];
		try //Reads in available images
		{
			images[0] = ImageIO.read(new File("src/org/world/humanvillage/Map/HVBackground.png"));
			images[1] = ImageIO.read(new File("src/org/world/humanvillage/Map/HVMidground.png"));
			images[2] = ImageIO.read(new File("src/org/world/humanvillage"));
		}
		catch (IOException e)
		{
			System.out.println("IMAGE READING ERROR (HVillage): " + e);
		}
		
		fullMap = new Rectangle(0, 0, worldWidth, worldHeight);
		spawnX = 2080;
		spawnY = 1344;
		filePath = "src/org/world/humanvillage/HumanVillage.txt";
		
		readMap();
	}
}
