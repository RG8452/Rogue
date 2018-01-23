package org.world;
/*
 * RG
 * This is the abstract class that will define the methods and stuff for each world
 * The individual Worlds will extend this class and implement its fields and stuff
 * Every world will consist of one huge png for the image drawn
 * The worlds also will contain a final QuadTree of Rectangles that will be used for collision 
 */

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.Startup;
import org.panels.GamePanel;

public abstract class World
{
	protected Rectangle fullMap;
	protected BufferedImage background, midground, foreground;
	protected QuadTree worldCollision;
	
	protected void drawVisibleWorld(Graphics2D g2d)
	{
/*		int sX = GamePanel.screenX; int sY = GamePanel.screenY;
		double pWX = ((GamePanel)Startup.getGUI().getPanel()).getPlayer().getWorldX();
		double pWY = ((GamePanel)Startup.getGUI().getPanel()).getPlayer().getWorldY();
		
		if(sX/2 > pWX)*/
	}
	
	public QuadTree getCollisionTree() {return worldCollision;}
}