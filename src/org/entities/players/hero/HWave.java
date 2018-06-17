package org.entities.players.hero;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.DataRetriever;
import org.Hitbox;
import org.Startup;
import org.world.World;

/**
 * RG This class is a small class made for the wave that the hero's 4th attack fires
 */

public class HWave
{
	private static int width = 45, height = 48, framesPerAnim = 3;
	private int elapsedFrames, curAnim, worldX, worldY, damage;
	private Hitbox hbox;
	private boolean facingRight;
	private BufferedImage[] animations;

	public HWave(int h, int k, boolean faceRight, int d)
	{
		worldX = h;
		worldY = k;
		damage = d;
		facingRight = faceRight;
		hbox = new Hitbox(h + 76, k + 50, width, height);
		elapsedFrames = 0;
		curAnim = 0;
		animations = new BufferedImage[12];

		try
		{
			if (facingRight)
			{
				animations[0] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_RWave1.png"));
				animations[1] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_RWave2.png"));
				animations[2] = animations[0];
				animations[3] = animations[1];
				animations[4] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_RWave3.png"));
				animations[5] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_RWave4.png"));
				animations[6] = animations[4];
				animations[7] = animations[5];
				animations[8] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_RWave5.png"));
				animations[9] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_RWave6.png"));
				animations[10] = animations[8];
				animations[11] = animations[9];
			}
			else
			{
				animations[0] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_LWave1.png"));
				animations[1] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_LWave2.png"));
				animations[2] = animations[0];
				animations[3] = animations[1];
				animations[4] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_LWave3.png"));
				animations[5] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_LWave4.png"));
				animations[6] = animations[4];
				animations[7] = animations[5];
				animations[8] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_LWave5.png"));
				animations[9] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_LWave6.png"));
				animations[10] = animations[8];
				animations[11] = animations[9];
			}
		}
		catch (IOException e)
		{
			System.out.println("Image reading error (HWave): " + e);
		}
	}

	public void draw(Graphics2D g)
	{
		g.drawImage(animations[curAnim], worldX - (int) World.getDrawX(), worldY - (int) World.getDrawY(), null);
		g.setColor(new Color(0, 40, 255, 110));
		if (Startup.getRunner().hitboxesEnabled()) g.fillRect((int) (hbox.getX() - World.getDrawX()), (int) (hbox.getY() - World.getDrawY()), width, height);
	}

	public void render()
	{
		elapsedFrames++;
		curAnim = elapsedFrames / framesPerAnim;

		if (curAnim >= animations.length)
		{
			((Hero) DataRetriever.getPlayer()).destroyWave();
			return;
		}

		hbox.render(damage, false);
		worldX += (facingRight) ? 2 : -2;
		hbox.setLocation(worldX + 75, worldY + 16);
	}

	//@formatter:off
	public int getFrame() {return curAnim;}
	//@formatter:on
}
