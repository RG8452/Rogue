package org.entities;

import java.awt.Graphics2D;

import org.Hitbox;

public abstract class Projectile
{
	protected boolean facingRight;
	protected int worldX;
	protected int worldY;
	protected int damage;
	protected int elapsedFrames;
	protected int curAnim;
	protected Hitbox hitbox;
	
	public abstract void draw(Graphics2D g);
	public abstract void render();
}
