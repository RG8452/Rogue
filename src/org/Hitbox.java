package org;
/*
 * RG
 * This class will create Hitbox objects, which will be used for damaging enemies & such
 * A Hitbox is essentially a Rectangle with x,y,width,height, and a HashSet of all enemies that it has hit
 * It's also notable that the hitbox coords use world coords, NOT screen coords
 * Variables such as Damage, piercing, hitscan, DoT, etc may be introduced later
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;

import org.entities.enemies.Enemy;
import org.world.World;

public class Hitbox extends Rectangle
{
	private HashSet<Enemy> hitEnemies;

	public Hitbox(int x, int y, int width, int height)
	{
		super(x, y, width, height);

		hitEnemies = new HashSet<Enemy>();
	}

	//Renders the hitbox on a given frame, checks enemies, and damages them
	public void render(int damage, boolean singleTarget)
	{
		for (Enemy e : this.checkEnemies(singleTarget))
		{
			e.damageEnemy(damage);
		}
		//System.out.println(hitEnemies);
	}

	//Returns a list of all enemies in the current hitbox
	private ArrayList<Enemy> checkEnemies(boolean onePunch)
	{
		ArrayList<Enemy> spaghettiAndMeatballs = new ArrayList<>();
		for (Enemy e : DataRetriever.getAllEnemies()) //Loop through all enemies
		{
			if (e.getWorldbox().intersects(this)) //If this hits the enemy
			{
				if (hitEnemies.add(e)) //If this enemy hasn't been hit yet
				{
					spaghettiAndMeatballs.add(e);
					if (onePunch) return spaghettiAndMeatballs; //Return early if single target
				}
			}
		}
		return spaghettiAndMeatballs;
	}

	//Draw the hitbox on the screen in blue
	public void drawHitbox(Graphics2D g)
	{
		g.setColor(new Color(0, 40, 255, 110));
		g.fillRect((int) (x - World.getDrawX()), (int) (y - World.getDrawY()), width, height);
	}

	//@formatter:off
	public HashSet<Enemy> getHitEnemies() {return hitEnemies;}
	public void clearEnemies() {hitEnemies.clear();}
	// All get methods and intersects(Rectangle r) are inherited from Rectangle
	// Also setSize(w,h) and setLocation(x,y)
	//@formatter:on
}