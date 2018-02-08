package org.world.dwarvencaverns;
/*
 * RG
 * World class for the dwarven caverns stage
 * Block, interactable, and image composition done in construction
 */

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.world.QuadTree;
import org.world.World;
import org.world.interactable.Interactable;
import org.world.interactable.Ladder;
import org.world.interactable.ManCannon;

public class DwarvenCaverns extends World
{
	// Note: 192 x 54 blocks in total
	// Spawn should be set as TL of pHitbox is 184, 33
	private final int worldWidth = 6144, worldHeight = 1728; // Data for exact size of the world

	public DwarvenCaverns()
	{
		try // Reads in available images
		{
			background = ImageIO.read(new File("src/org/world/dwarvencaverns/Map/CavernsBackGround.png"));
			midground = ImageIO.read(new File("src/org/world/dwarvencaverns/Map/CavernsMidGround.png"));
			foreground = ImageIO.read(new File("src/org/world/dwarvencaverns/Map/CavernsForeGround.png"));
		}
		catch (IOException e)
		{
			System.out.println("IMAGE READING ERROR (DCaverns): " + e);
		}

		fullMap = new Rectangle(0, 0, worldWidth, worldHeight); // Instantiate various objects
		worldCollision = new QuadTree(0, fullMap);
		stuff = new ArrayList<Interactable>();
		spawnX = 5888;
		spawnY = 1056;

		// OUTER WALLS
		QTAdd(0, 0, worldWidth, block);
		QTAdd(0, 0, block, worldHeight);
		QTAdd(worldWidth - block, 0, block, worldHeight);
		QTAdd(0, worldHeight - block, worldWidth, block);

		// CORRECT POSITION
		QTAddB(1, 4, 3, 1);
		QTAddB(1, 8, 5, 1);
		QTAddB(1, 12, 7, 1);
		QTAddB(1, 16, 19, 1);
		QTAddB(20, 16, 39, 4);
		QTAddB(20, 20, 1, 9);
		QTAddB(20, 29, 42, 1);
		QTAddB(21, 30, 1, 2);
		QTAdd(59 * block, (int) (16.5 * block), block, (int) (3.5 * block));
		QTAddB(60, 17, 1, 3);
		QTAddB(61, 18, 1, 2);
		QTAdd(62 * block, (int) (18.5 * block), 17 * block, (int) (1.5 * block));
		QTAddB(74, 18, 4, 1);
		QTAdd(75 * block, (int) (17.5 * block), 2 * block, (int) (block / 2));
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
		QTAddB(36, 52, 11, 1);
		QTAddB(40, 51, 6, 1);
		QTAddB(59, 52, 16, 1);
		QTAddB(62, 51, 3, 1);
		QTAddB(68, 51, 5, 1);
		QTAddB(70, 50, 2, 1);
		QTAddB(82, 52, 14, 1);
		QTAddB(83, 51, 7, 1);
		QTAddB(84, 50, 2, 1);
		QTAddB(110, 52, 9, 1);
		QTAddB(1, 35, 110, 2);
		QTAddB(111, 35, 4, 1);
		QTAddB(6, 37, 3, 1);
		QTAddB(7, 38, 2, 2);
		QTAddB(7, 40, 1, 2);
		QTAddB(23, 37, 6, 1);
		QTAddB(23, 38, 5, 1);
		QTAddB(23, 39, 3, 1);
		QTAddB(24, 40, 2, 2);
		QTAddB(49, 37, 6, 1);
		QTAddB(75, 37, 9, 1);
		QTAddB(91, 37, 2, 7);
		QTAddB(91, 44, 11, 1);
		QTAddB(91, 45, 6, 1);
		QTAddB(91, 46, 4, 1);
		QTAddB(91, 47, 2, 2);
		QTAddB(95, 41, 5, 1);
		QTAddB(98, 40, 3, 1);
		QTAddB(98, 38, 4, 2);
		QTAddB(98, 37, 5, 1);
		QTAddB(103, 45, 3, 1);
		QTAddB(103, 46, 2, 3);
		QTAddB(108, 46, 6, 1);
		QTAddB(110, 47, 3, 1);
		QTAddB(104, 39, 13, 1);
		QTAddB(121, 35, 8, 1);
		QTAddB(119, 36, 10, 1);
		QTAddB(117, 37, 12, 1);
		QTAddB(116, 38, 13, 1);
		QTAddB(115, 39, 15, 2);
		QTAddB(115, 41, 16, 3);
		QTAddB(125, 44, 6, 1);
		QTAddB(127, 45, 4, 1);
		QTAddB(128, 46, 2, 1);
		QTAddB(132, 47, 8, 1);
		QTAddB(135, 48, 6, 1);
		QTAddB(139, 49, 11, 1);
		QTAddB(145, 48, 6, 1);
		QTAddB(152, 50, 8, 1);
		QTAddB(154, 49, 4, 1);
		QTAddB(153, 46, 5, 1);
		QTAddB(158, 46, 3, 4);
		QTAddB(161, 45, 2, 3);
		QTAddB(163, 45, 12, 2);
		QTAddB(166, 44, 3, 1);
		QTAddB(175, 45, 1, 1);
		QTAddB(170, 42, 13, 1);
		QTAddB(179, 43, 3, 1);
		QTAddB(177, 49, 9, 1);
		QTAddB(180, 50, 4, 1);
		QTAddB(182, 48, 2, 1);
		QTAddB(185, 46, 4, 1);
		QTAddB(131, 35, 35, 2);
		QTAddB(161, 34, 2, 1);
		QTAddB(166, 35, 22, 1);
		QTAddB(188, 34, 3, 2);
		QTAddB(190, 33, 1, 1);
		QTAddB(134, 37, 9, 2);
		QTAddB(146, 37, 14, 2);
		QTAddB(160, 37, 3, 1);
		QTAddB(132, 39, 25, 1);
		QTAddB(133, 40, 21, 1);
		QTAddB(132, 30, 1, 3);
		QTAddB(133, 29, 1, 2);
		QTAddB(134, 29, 2, 1);
		QTAddB(135, 28, 3, 1);
		QTAddB(137, 27, 12, 1);
		QTAddB(139, 26, 4, 1);
		QTAddB(144, 26, 3, 1);
		QTAddB(148, 28, 3, 1);
		QTAddB(150, 29, 3, 1);
		QTAddB(152, 30, 7, 1);
		QTAddB(157, 32, 3, 1);
		QTAddB(162, 30, 12, 1);
		QTAddB(165, 29, 4, 1);
		QTAddB(177, 29, 14, 1);
		QTAddB(182, 24, 9, 1);
		QTAddB(140, 19, 6, 1);
		QTAddB(142, 20, 6, 1);
		QTAddB(143, 21, 6, 1);
		QTAddB(148, 22, 6, 1);
		QTAddB(156, 22, 5, 1);
		QTAddB(162, 20, 5, 1);
		QTAddB(166, 19, 7, 1);
		QTAddB(174, 17, 12, 1);
		QTAddB(176, 16, 6, 1);
		QTAddB(178, 15, 2, 1);
		QTAddB(181, 13, 10, 1);
		QTAddB(184, 12, 4, 1);
		QTAddB(189, 10, 2, 1);
		QTAddB(143, 11, 4, 1);
		QTAddB(144, 12, 4, 1);
		QTAddB(149, 13, 5, 1);
		QTAddB(151, 14, 4, 1);
		QTAddB(156, 15, 10, 1);
		QTAddB(164, 14, 7, 1);
		QTAddB(172, 10, 8, 1);
		QTAddB(173, 9, 3, 1);
		QTAddB(178, 9, 9, 1);
		QTAddB(160, 10, 2, 1);
		QTAddB(161, 9, 2, 1);
		QTAddB(163, 9, 7, 2);
		QTAddB(164, 11, 5, 1);
		QTAddB(148, 9, 11, 1);
		QTAddB(152, 8, 6, 1);
		QTAddB(153, 7, 2, 1);
		QTAddB(146, 4, 5, 1);
		QTAddB(148, 5, 4, 1);

		QTAddB(99, 4, 42, 1);
		QTAddB(100, 5, 40, 5); // GIRTH BOII

		stuff.add(new Ladder(56, 49, 4, "dc"));
		stuff.add(new Ladder(84, 45, 5, "dc"));
		stuff.add(new Ladder(104, 39, 6, "dc"));
		stuff.add(new Ladder(113, 46, 6, "dc"));
		stuff.add(new Ladder(132, 39, 8, "dc"));
		stuff.add(new Ladder(150, 48, 5, "dc"));
		stuff.add(new Ladder(182, 42, 6, "dc"));

		stuff.add(new ManCannon(63, 33, 6, "dc"));
		stuff.add(new ManCannon(21, 27, 5, "dc"));
		stuff.add(new ManCannon(81, 22, 5, "dc"));
		stuff.add(new ManCannon(8, 14, 4, "dc"));
		stuff.add(new ManCannon(7, 10, 5, "dc"));
		stuff.add(new ManCannon(5, 6, 4, "dc"));
		stuff.add(new ManCannon(97, 6, 4, "dc"));
		stuff.add(new ManCannon(155, 28, 8, "dc"));
	}
}