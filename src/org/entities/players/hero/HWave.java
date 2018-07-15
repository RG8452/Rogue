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
import org.entities.Projectile;
import org.world.World;

/**
 * RG This class is a small class made for the wave that the hero's 4th attack fires
 */

public class HWave extends Projectile
{
	private static int width = 45, height = 48, framesPerAnim = 3;
	private static BufferedImage[] rAnims = new BufferedImage[12];
	private static BufferedImage[] lAnims = new BufferedImage[12];

	static
	{
		try
		{
			rAnims[0] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_RWave1.png"));
			rAnims[1] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_RWave2.png"));
			rAnims[2] = rAnims[0];
			rAnims[3] = rAnims[1];
			rAnims[4] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_RWave3.png"));
			rAnims[5] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_RWave4.png"));
			rAnims[6] = rAnims[4];
			rAnims[7] = rAnims[5];
			rAnims[8] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_RWave5.png"));
			rAnims[9] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_RWave6.png"));
			rAnims[10] = rAnims[8];
			rAnims[11] = rAnims[9];

			lAnims[0] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_LWave1.png"));
			lAnims[1] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_LWave2.png"));
			lAnims[2] = lAnims[0];
			lAnims[3] = lAnims[1];
			lAnims[4] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_LWave3.png"));
			lAnims[5] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_LWave4.png"));
			lAnims[6] = lAnims[4];
			lAnims[7] = lAnims[5];
			lAnims[8] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_LWave5.png"));
			lAnims[9] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_LWave6.png"));
			lAnims[10] = lAnims[8];
			lAnims[11] = lAnims[9];
		}
		catch (IOException e)
		{
			System.out.println("Image reading error (HWave): " + e);
		}
	}

	public HWave(int h, int k, boolean faceRight, int d)
	{
		worldX = h;
		worldY = k;
		damage = d;
		facingRight = faceRight;
		hitbox = new Hitbox(h + 76, k + 50, width, height);
		elapsedFrames = 0;
		curAnim = 0;
	}

	public void draw(Graphics2D g)
	{
		g.drawImage(facingRight ? rAnims[curAnim] : lAnims[curAnim], worldX - (int) World.getDrawX(), worldY - (int) World.getDrawY(), null);
		g.setColor(new Color(0, 40, 255, 110));
		if (Startup.getRunner().hitboxesEnabled()) g.fillRect((int) (hitbox.getX() - World.getDrawX()), (int) (hitbox.getY() - World.getDrawY()), width, height);
	}

	public void render()
	{
		elapsedFrames++;
		curAnim = elapsedFrames / framesPerAnim;

		if (curAnim >= rAnims.length)
		{
			((Hero) DataRetriever.getPlayer()).destroyWave();
			return;
		}

		hitbox.render(damage, false);
		worldX += (facingRight) ? 2 : -2;
		hitbox.setLocation(worldX + 75, worldY + 16);
	}

	//@formatter:off
	public int getFrame() {return curAnim;}
	//@formatter:on
}
