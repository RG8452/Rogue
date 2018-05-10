package org;
/*
 * RG
 * This class will create Hitbox objects, which will be used for damaging enemies & such
 * A Hitbox is essentially a Rectangle with x,y,width,height, and a HashSet of all enemies that it has hit
 * It's also notable that the hitbox coords use world coords, NOT screen coords
 * Variables such as Damage, piercing, hitscan, DoT, etc may be introduced later
 */

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;

import org.enemies.Enemy;

public class Hitbox extends Rectangle
{
	private HashSet<Enemy> hitEnemies;
	private int generatedFrame;

	public Hitbox(int x, int y, int width, int height)
	{
		super(x, y, width, height);

		hitEnemies = new HashSet<Enemy>();
		generatedFrame = DataRetriever.getFrame();
	}

	private void render() // Method for checking all contact with the enemies onthe gamePanel
	{
		// loop through all enemies and if they're in the hitbox, damage them(depending on piercing and stuff)
	}

	//@formatter:off
	public HashSet<Enemy> getHitEnemies() {return hitEnemies;}
	public void clearEnemies() {hitEnemies.clear();}
	// All get methods and intersects(Rectangle r) are inherited from Rectangle
	// Also setSize(w,h) and setLocation(x,y)
	//@formatter:on
}