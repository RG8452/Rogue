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

import org.DataRetriever;
import org.panels.GamePanel;

public abstract class World
{
	protected Rectangle fullMap;		//Rectangle for the full map, used for making the QTree
	protected BufferedImage background, midground, foreground;	//images to be drawn
	protected QuadTree worldCollision;	//QTree for collision
	protected static int block = 32; 	//Number of pixels in a block
	
	//Draws the portion of the World that is visible based on the player's world coords
	//The player is assumed to be perfectly center except when the screen can no longer scroll
	public void drawVisibleWorld(Graphics2D g2d)
	{
		int sX = GamePanel.screenX; int sY = GamePanel.screenY;		//Screen dimensions
		double pWX = DataRetriever.getPlayer().getWorldX();	//Player coords
		double pWY = DataRetriever.getPlayer().getWorldY();
		int drawX, drawY = 0;
		
		if(sX/2 > pWX) {drawX = 0;}		//If past the left edge, then start at 0
		else if(fullMap.getWidth() - pWX < sX/2) {drawX = (int)(fullMap.getWidth() - sX);}	//If past the right edge, start at max right
		else {drawX = (int)(pWX - sX/2);}		//Otherwise, center the player
		
		if(sY/2 > pWY) drawY = 0;		//Same as X methods but for the Y values
		else if(fullMap.getHeight() - pWY < sY/2) drawY = (int)(fullMap.getHeight() - sY);
		else drawY = (int)(pWY - sY/2);
		
		g2d.drawImage(background, 0, 0, sX, sY, drawX, drawY, sX + drawX, sY + drawY, null);	//Draws all three images successively
		g2d.drawImage(midground, 0, 0, sX, sY, drawX, drawY, sX + drawX, sY + drawY, null);
		g2d.drawImage(foreground, 0, 0, sX, sY, drawX, drawY, sX + drawX, sY + drawY, null);
	}
	
	public QuadTree getCollisionTree() {return worldCollision;}
	public int getWidth() {return (int)fullMap.getWidth();}
	public int getHeight() {return (int)fullMap.getHeight();}
}