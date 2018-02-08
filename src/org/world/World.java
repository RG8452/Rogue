package org.world;
/*
 * RG
 * This is the abstract class that will define the methods and stuff for each world
 * The individual Worlds will extend this class and implement its fields and stuff
 * Every world will consist of one huge png for the image drawn
 * The worlds also will contain a final QuadTree of Rectangles that will be used for collision 
 *
 * TODO: Add some form of variable to determine stage chronology
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.DataRetriever;
import org.Startup;
import org.panels.GamePanel;
import org.world.interactable.Interactable;

public abstract class World
{
	protected Rectangle fullMap; // Rectangle for the full map, used for making the QTree
	protected BufferedImage background, midground, foreground; // images to be drawn
	protected QuadTree worldCollision, interCollision; // QTree for collision
	protected ArrayList<Interactable> stuff; // Interactable list
	protected static int block = 32; // Number of pixels in a block
	protected static double drawX, drawY, spawnX, spawnY; // Corner where screen drawing begins & player spawn

	// Draws the portion of the World that is visible based on the player's world coords
	// The player is assumed to be perfectly center except when the screen can no longer scroll
	public void drawVisibleWorld(Graphics2D g2d)
	{
		int sX = GamePanel.screenX;
		int sY = GamePanel.screenY; // Screen dimensions
		setDrawX();
		setDrawY();

		g2d.drawImage(background, 0, 0, sX, sY, (int) drawX, (int) drawY, sX + (int) drawX, sY + (int) drawY, null); // Draws all three images successively
		g2d.drawImage(midground, 0, 0, sX, sY, (int) drawX, (int) drawY, sX + (int) drawX, sY + (int) drawY, null);
		g2d.drawImage(foreground, 0, 0, sX, sY, (int) drawX, (int) drawY, sX + (int) drawX, sY + (int) drawY, null);

		for (Interactable moistBiscuits : stuff)
			moistBiscuits.draw(g2d);

		if (Startup.getRunner().worldboxesEnabled()) drawHitboxes(g2d);
	}

	public void drawHitboxes(Graphics2D g2d)
	{
		double tX, tY;
		g2d.setColor(new Color(255, 0, 255, 80));
		for (Rectangle r : worldCollision.retrieve(new ArrayList<Rectangle>(), DataRetriever.getPlayer().getWorldbox()))
		{
			if (r.getX() + r.getWidth() > drawX || r.getX() < drawX + GamePanel.screenX)
			{
				if (r.getY() + r.getHeight() > drawY || r.getY() < drawY + GamePanel.screenY)
				{
					tX = r.getX() - drawX;
					tY = r.getY() - drawY;

					g2d.fillRect((int) tX, (int) tY, (int) r.getWidth(), (int) r.getHeight());
				}
			}
		}
	}

	//@formatter:off
	public QuadTree getCollisionTree() {return worldCollision;}
	public QuadTree getInterTree() {return interCollision;}
	public Rectangle getFullMap() {return fullMap;}
	public ArrayList<Interactable> getInteractables() {return stuff;}
	public int getWidth() {return (int) fullMap.getWidth();}
	public int getHeight() {return (int) fullMap.getHeight();}
	
	public void QTAdd(int x, int y, int w, int h) {worldCollision.insert(new Rectangle(x, y, w, h));}
	public void QTAddB(int x, int y, int w, int h) {worldCollision.insert(new Rectangle(block * x, block * y, block * w, block * h));}
	public void ITAdd(Interactable i) {interCollision.insert(i.getZone());}
	public static void setDrawX(double dX) {drawX = dX;}
	public static void setDrawY(double dY) {drawY = dY;}
	public static double getDrawX() {return drawX;}
	public static double getDrawY() {return drawY;}
	public double getSpawnX() {return spawnX;}
	public double getSpawnY() {return spawnY;}
	//@formatter:on

	public static void setDrawX()
	{
		int sX = GamePanel.screenX;
		double pWX = DataRetriever.getPlayer().getWorldX();
		if (sX / 2 > pWX) drawX = 0; // If past the left edge, then start at 0
		else if (DataRetriever.getWorld().getFullMap().getWidth() - pWX < sX / 2) drawX = (int) (DataRetriever.getWorld().getFullMap().getWidth() - sX); // If past the right edge, start at max right
		else drawX = (int) (pWX - sX / 2); // Otherwise, center the player
	}

	public static void setDrawY()
	{
		int sY = GamePanel.screenY;
		double pWY = DataRetriever.getPlayer().getWorldY();
		if (sY / 2 > pWY) drawY = 0; // Same as X methods but for the Y values
		else if (DataRetriever.getWorld().getFullMap().getHeight() - pWY < sY / 2) drawY = (int) (DataRetriever.getWorld().getFullMap().getHeight() - sY);
		else drawY = (int) (pWY - sY / 2);
	}
}