package org.world.cavecity;

/*
 * RG
 * World class for the cave city stage
 * Construction creates QuadTree, interactables, etc
 * 
 * TODO: implement midground, ladder pngt
 * TODO: finish making world with blocks
 */
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.world.QuadTree;
import org.world.World;
import org.world.interactable.Ladder;
import org.world.interactable.Platform;

public class CaveCity extends World
{
	private final int worldWidth = 6400, worldHeight = 2240;

	public CaveCity()
	{
		images = new BufferedImage[3];
		try // Reads in available images
		{
			images[0] = ImageIO.read(new File("src/org/world/cavecity/Map/CaveCityBackGround.png"));
			images[1] = ImageIO.read(new File("src/org/world/cavecity/Map/CaveCityMidGround.png"));
			images[2] = ImageIO.read(new File("src/org/world/cavecity/Map/CaveCityForeGround.png"));
		}
		catch (IOException e)
		{
			System.out.println("IMAGE READING ERROR (CaveCity): " + e);
		}

		fullMap = new Rectangle(0, 0, worldWidth, worldHeight);
		worldCollision = new QuadTree(0, fullMap);
		interCollision = new QuadTree(0, fullMap);
		spawnX = 6208;
		spawnY = 2120;

		//OUTER WALLS
		QTAdd(0, 0, worldWidth, block);
		QTAdd(0, 0, block, worldHeight);
		QTAdd(worldWidth - block, 0, block, worldHeight);
		QTAdd(0, worldHeight - block, worldWidth, block);

		//BLOCKS [LEFT HALF]
		//BL Building
		QTAddB(1, 44, 1, 25);
		QTAddB(1, 44, 7, 1);
		QTAddB(7, 44, 1, 5);
		QTAddB(7, 51, 1, 7);
		QTAddB(7, 60, 1, 7);
		ITAdd(new Platform(2, 51, 5));
		ITAdd(new Platform(4, 53, 3));
		ITAdd(new Platform(2, 55, 3));
		ITAdd(new Platform(4, 57, 3));
		ITAdd(new Platform(2, 60, 5));
		ITAdd(new Platform(2, 62, 3));
		ITAdd(new Platform(4, 64, 3));
		ITAdd(new Platform(2, 66, 3));

		//BL floor
		QTAddB(1, 42, 12, 2);
		QTAddB(13, 42, 7, 1);
		QTAddB(20, 42, 4, 2);
		QTAddB(8, 51, 3, 2);
		QTAddB(11, 51, 7, 1);
		QTAddB(8, 60, 10, 2);

		//Big building, second from the left on the bottom
		QTAddB(18, 60, 1, 10);
		QTAddB(18, 51, 1, 7);
		QTAddB(18, 44, 1, 5);
		QTAddB(19, 44, 5, 1);
		QTAddB(24, 42, 1, 4);
		QTAddB(24, 48, 1, 1);
		QTAddB(24, 51, 1, 7);
		QTAddB(24, 60, 1, 7);
		QTAddB(24, 26, 1, 14);
		QTAddB(25, 26, 3, 1);
		QTAddB(25, 51, 11, 1);
		QTAddB(27, 23, 1, 3);
		QTAddB(27, 20, 1, 1);
		QTAddB(27, 17, 1, 1);
		QTAddB(27, 13, 1, 2);
		QTAddB(28, 13, 5, 1);
		QTAddB(33, 13, 1, 2);
		QTAddB(33, 17, 1, 1);
		QTAddB(33, 20, 1, 1);
		QTAddB(33, 23, 1, 4);
		QTAddB(34, 26, 3, 1);
		QTAddB(36, 27, 1, 19);
		QTAddB(30, 29, 1, 11);
		QTAddB(30, 42, 1, 9);
		QTAddB(24, 48, 1, 1);
		QTAddB(36, 48, 1, 1);
		QTAddB(37, 44, 5, 1);
		QTAddB(42, 44, 1, 5);
		QTAddB(42, 51, 1, 7);
		QTAddB(36, 51, 1, 7);
		QTAddB(29, 54, 3, 1);
		QTAddB(30, 55, 1, 5);
		QTAddB(36, 60, 1, 10);
		QTAddB(42, 60, 1, 7);
		ITAdd(new Platform(19, 51, 5));
		ITAdd(new Platform(21, 48, 3));
		ITAdd(new Platform(19, 53, 3));
		ITAdd(new Platform(21, 55, 3));
		ITAdd(new Platform(19, 57, 3));
		ITAdd(new Platform(19, 60, 5));
		ITAdd(new Platform(21, 62, 3));
		ITAdd(new Platform(19, 64, 3));
		ITAdd(new Platform(21, 66, 3));
		ITAdd(new Platform(25, 66, 3));
		ITAdd(new Platform(27, 64, 7));
		ITAdd(new Platform(33, 66, 3));
		ITAdd(new Platform(25, 62, 3));
		ITAdd(new Platform(33, 62, 3));
		ITAdd(new Platform(25, 60, 11));
		ITAdd(new Platform(25, 57, 4));
		ITAdd(new Platform(32, 57, 4));
		ITAdd(new Platform(27, 54, 2));
		ITAdd(new Platform(32, 54, 2));
		ITAdd(new Platform(25, 48, 3));
		ITAdd(new Platform(33, 48, 3));
		ITAdd(new Platform(27, 45, 3));
		ITAdd(new Platform(31, 45, 3));
		ITAdd(new Platform(25, 42, 5));
		ITAdd(new Platform(31, 42, 5));
		ITAdd(new Platform(27, 39, 3));
		ITAdd(new Platform(31, 39, 3));
		ITAdd(new Platform(25, 36, 3));
		ITAdd(new Platform(33, 36, 3));
		ITAdd(new Platform(27, 33, 3));
		ITAdd(new Platform(31, 33, 3));
		ITAdd(new Platform(25, 30, 3));
		ITAdd(new Platform(33, 30, 3));
		ITAdd(new Platform(29, 29, 1));
		ITAdd(new Platform(31, 29, 1));
		ITAdd(new Platform(28, 26, 5));
		ITAdd(new Platform(25, 23, 2));
		ITAdd(new Platform(28, 23, 3));
		ITAdd(new Platform(34, 23, 2));
		ITAdd(new Platform(25, 20, 2));
		ITAdd(new Platform(30, 20, 3));
		ITAdd(new Platform(34, 20, 2));
		ITAdd(new Platform(25, 17, 2));
		ITAdd(new Platform(28, 17, 3));
		ITAdd(new Platform(34, 17, 2));
		ITAdd(new Platform(37, 48, 3));
		ITAdd(new Platform(37, 51, 5));
		ITAdd(new Platform(39, 53, 3));
		ITAdd(new Platform(37, 55, 3));
		ITAdd(new Platform(39, 57, 3));
		ITAdd(new Platform(37, 60, 5));
		ITAdd(new Platform(37, 62, 3));
		ITAdd(new Platform(39, 64, 3));
		ITAdd(new Platform(37, 66, 3));

		//GROUND TO RIGHT OF BL BIG BUILDING
		QTAddB(37, 42, 7, 2); //top block
		QTAddB(44, 42, 11, 1);
		QTAddB(43, 51, 20, 1); //middle block
		QTAddB(43, 52, 12, 1);
		QTAddB(44, 53, 7, 1);
		QTAddB(43, 60, 22, 1); //bottom block
		QTAddB(46, 61, 13, 1);
		QTAddB(49, 62, 9, 1);

		//FLOATING BLOCKS
		QTAddB(41, 33, 1, 1);
		QTAddB(44, 33, 1, 1);
		QTAddB(47, 34, 1, 1);
		QTAddB(50, 34, 1, 1);
		QTAddB(52, 33, 1, 1);
		QTAddB(55, 34, 1, 1);
		QTAddB(58, 35, 1, 1);
		QTAddB(60, 34, 1, 1);
		QTAddB(62, 33, 1, 1);
		QTAddB(65, 33, 1, 1);

		//BUILDING IN THE TOP LEFT
		QTAddB(14, 1, 1, 6);
		QTAddB(20, 1, 1, 2);
		QTAddB(14, 9, 1, 5);
		QTAddB(15, 9, 6, 1);
		QTAddB(20, 5, 1, 5);
		QTAddB(21, 5, 7, 1);
		QTAddB(27, 4, 1, 1);
		QTAddB(1, 16, 14, 1);
		ITAdd(new Platform(1, 4, 4));
		ITAdd(new Platform(1, 7, 4));
		ITAdd(new Platform(1, 10, 4));
		ITAdd(new Platform(10, 10, 4));
		ITAdd(new Platform(10, 13, 4));
		ITAdd(new Platform(17, 6, 3));
		ITAdd(new Platform(15, 16, 2));
		ITAdd(new Ladder(9, 1, 3, "cc"));
		ITAdd(new Ladder(11, 1, 5, "cc"));
		ITAdd(new Ladder(25, 5, 3, "cc"));

		//BLOCKS BELOW IT
		QTAddB(1, 17, 6, 1);
		QTAddB(1, 18, 4, 1);
		QTAddB(1, 19, 1, 1);
		QTAddB(1, 22, 1, 1);
		QTAddB(1, 23, 2, 5);
		QTAddB(3, 24, 2, 3);
		QTAddB(5, 25, 3, 2);
		QTAddB(8, 25, 11, 1);
		QTAddB(12, 26, 3, 1);

		//BUILDING ABOVE FLOATING BLOCKS
		QTAddB(40, 4, 1, 2);
		QTAddB(40, 10, 1, 2);
		QTAddB(41, 5, 3, 1);
		QTAddB(41, 11, 3, 1);
		QTAddB(43, 1, 1, 2);
		QTAddB(43, 6, 1, 3);
		QTAddB(43, 11, 1, 7);
		QTAddB(49, 1, 1, 2);
		QTAddB(49, 5, 1, 13);
		QTAddB(54, 1, 1, 2);
		QTAddB(54, 5, 1, 13);
		QTAddB(60, 1, 1, 2);
		QTAddB(60, 5, 1, 4);
		QTAddB(60, 11, 1, 7);
		QTAddB(61, 11, 3, 1);
		QTAddB(63, 10, 1, 1);
		ITAdd(new Platform(44, 5, 3));
		ITAdd(new Platform(46, 8, 3));
		ITAdd(new Platform(44, 11, 3));
		ITAdd(new Platform(46, 14, 3));
		ITAdd(new Platform(44, 17, 3));
		ITAdd(new Platform(50, 5, 4));
		ITAdd(new Platform(57, 5, 3));
		ITAdd(new Platform(55, 8, 3));
		ITAdd(new Platform(57, 11, 3));
		ITAdd(new Platform(55, 14, 3));
		ITAdd(new Platform(57, 17, 3));
		ITAdd(new Platform(61, 5, 15));
		QTAddB(76, 1, 1, 2);
		QTAddB(76, 5, 1, 6);
		QTAddB(76, 11, 2, 1);
		QTAddB(88, 1, 1, 10);
		QTAddB(87, 11, 2, 1);
		ITAdd(new Platform(77, 8, 3));
		ITAdd(new Platform(83, 5, 5));
		ITAdd(new Platform(85, 8, 3));
		ITAdd(new Platform(78, 11, 9));

		//BLOCKS UNDER THAT BUILDING
		QTAddB(39, 20, 24, 1);
		QTAddB(43, 21, 7, 1);
		QTAddB(54, 21, 6, 1);
		QTAddB(66, 20, 31, 1);
		QTAddB(67, 19, 10, 1);
		QTAddB(71, 18, 3, 1);
		QTAddB(74, 21, 5, 1);
		QTAddB(84, 21, 4, 1);
		QTAddB(87, 19, 7, 1);
		QTAddB(90, 18, 2, 1);

		//FLOATING BLOCKS & RANDOM PLATFORM
		QTAddB(81, 36, 1, 1);
		QTAddB(84, 37, 1, 1);
		QTAddB(87, 38, 1, 1);
		QTAddB(89, 37, 1, 1);
		QTAddB(91, 36, 1, 1);
		QTAddB(94, 36, 1, 1);
		QTAddB(96, 37, 1, 1);
		ITAdd(new Platform(98, 36, 2));

		//REMAINING ROCKS
		QTAddB(66, 51, 14, 1); //Line
		QTAddB(83, 51, 17, 1); //Block right of line
		QTAddB(91, 52, 9, 1);
		QTAddB(94, 53, 6, 1);
		QTAddB(81, 60, 19, 1); //Block below that
		QTAddB(83, 61, 17, 1);

		//BUILDING ABOVE LINE
		QTAddB(70, 26, 1, 2);
		QTAddB(71, 26, 5, 1);
		QTAddB(76, 26, 1, 2);
		QTAddB(70, 30, 1, 1);
		QTAddB(76, 30, 1, 1);
		QTAddB(70, 33, 1, 1);
		QTAddB(76, 33, 1, 1);
		QTAddB(70, 36, 1, 13);
		QTAddB(76, 36, 1, 13);
		ITAdd(new Platform(68, 27, 2));
		ITAdd(new Platform(77, 27, 2));
		ITAdd(new Platform(68, 30, 2));
		ITAdd(new Platform(77, 30, 2));
		ITAdd(new Platform(73, 30, 3));
		ITAdd(new Platform(71, 33, 3));
		ITAdd(new Platform(68, 33, 2));
		ITAdd(new Platform(77, 33, 2));
		ITAdd(new Platform(68, 36, 2));
		ITAdd(new Platform(77, 36, 2));
		ITAdd(new Platform(71, 36, 5));
		ITAdd(new Platform(71, 39, 3));
		ITAdd(new Platform(71, 42, 3));
		ITAdd(new Platform(73, 45, 3));
		ITAdd(new Platform(73, 48, 3));

		//BUILDING BELOW THAT
		QTAddB(67, 60, 1, 7);
		QTAddB(68, 60, 11, 1);
		QTAddB(79, 60, 1, 7);
		QTAddB(73, 64, 1, 3);
		ITAdd(new Platform(68, 64, 5));
		ITAdd(new Platform(74, 64, 5));
		ITAdd(new Platform(68, 66, 2));
		ITAdd(new Platform(77, 66, 2));

		//LEFT HALF DONE; RIGHT HALF STARTS HERE [x=100]
		//FIRST BUILDING
		QTAddB(100, 26, 1, 8);
		QTAddB(100, 36, 1, 13);
		QTAddB(100, 51, 1, 16);
		QTAddB(101, 26, 5, 1);
		QTAddB(106, 26, 1, 8);
		QTAddB(101, 36, 6, 1);
		QTAddB(106, 39, 1, 10);
		QTAddB(106, 51, 12, 1);
		QTAddB(111, 52, 2, 6);
		QTAddB(111, 60, 2, 9);
		QTAddB(117, 26, 1, 11);
		QTAddB(118, 26, 5, 1);
		QTAddB(123, 26, 1, 23);
		QTAddB(117, 39, 1, 12);
		QTAddB(123, 51, 1, 7);
		QTAddB(123, 60, 1, 7);
		ITAdd(new Platform(103, 30, 3));
		ITAdd(new Platform(101, 33, 3));
		ITAdd(new Platform(107, 36, 2));
		ITAdd(new Platform(107, 39, 10));
		ITAdd(new Platform(104, 39, 2));
		ITAdd(new Platform(101, 41, 3));
		ITAdd(new Platform(103, 44, 3));
		ITAdd(new Platform(101, 47, 3));
		ITAdd(new Platform(101, 51, 5));
		ITAdd(new Platform(101, 54, 3));
		ITAdd(new Platform(101, 57, 6));
		ITAdd(new Platform(101, 60, 10));
		ITAdd(new Platform(105, 63, 6));
		ITAdd(new Platform(101, 66, 5));
		ITAdd(new Platform(113, 63, 6));
		ITAdd(new Platform(118, 66, 5));
		ITAdd(new Platform(113, 60, 10));
		ITAdd(new Platform(117, 57, 6));
		ITAdd(new Platform(120, 54, 3));
		ITAdd(new Platform(118, 51, 5));
		ITAdd(new Platform(120, 47, 3));
		ITAdd(new Platform(118, 44, 3));
		ITAdd(new Platform(120, 41, 3));
		ITAdd(new Platform(118, 39, 2));
		ITAdd(new Platform(118, 36, 5));
		ITAdd(new Platform(120, 33, 3));
		ITAdd(new Platform(118, 30, 3));
		ITAdd(new Platform(114, 42, 3));
		ITAdd(new Platform(114, 45, 3));
		ITAdd(new Platform(107, 42, 3));
		ITAdd(new Platform(107, 45, 3));

		//FLOATING BLOCKS TO ITS RIGHT
		QTAddB(126, 27, 1, 1);
		QTAddB(129, 28, 1, 1);
		QTAddB(131, 27, 1, 1);
		QTAddB(134, 27, 1, 1);
		QTAddB(136, 27, 1, 1);
		QTAddB(138, 26, 1, 1);
		QTAddB(141, 27, 1, 1);
		QTAddB(144, 29, 1, 1);
		QTAddB(146, 28, 1, 1);
		QTAddB(148, 27, 1, 1);
		QTAddB(150, 27, 1, 1);
		QTAddB(153, 27, 1, 1);
		QTAddB(155, 28, 1, 1);
		QTAddB(156, 31, 1, 1);
		QTAddB(159, 30, 1, 1);
		QTAddB(161, 29, 11, 1);
		QTAddB(163, 30, 8, 1);
		QTAddB(169, 28, 5, 1);
		QTAddB(170, 27, 3, 1);

		//BLOCKS TO THE RIGHT OF MIRROR BUILDING
		QTAddB(124, 51, 32, 1); //top block with platform
		ITAdd(new Platform(156, 51, 17));
		QTAddB(132, 50, 4, 1);
		QTAddB(138, 50, 4, 1);
		QTAddB(124, 60, 10, 1); //middle block
		QTAddB(124, 61, 12, 1);
		QTAddB(128, 62, 8, 1);
		QTAddB(133, 66, 7, 1); //tiny block
		QTAddB(139, 65, 1, 1);

		//Baby building
		QTAddB(140, 62, 1, 5);
		QTAddB(146, 65, 29, 2);
		QTAddB(140, 52, 1, 8);
		QTAddB(141, 52, 5, 1);
		QTAddB(146, 52, 1, 11);
		ITAdd(new Platform(136, 62, 4));
		ITAdd(new Platform(141, 65, 5));
		ITAdd(new Platform(141, 62, 3));
		ITAdd(new Platform(143, 59, 3));
		ITAdd(new Platform(141, 56, 3));

		//FINAL ROCK
		QTAddB(149, 64, 5, 1);
		QTAddB(167, 64, 9, 1);
		QTAddB(168, 63, 8, 1);
		QTAddB(170, 62, 7, 1);
		QTAddB(171, 61, 6, 1);
		QTAddB(172, 60, 5, 1);
		QTAddB(173, 59, 4, 1);
		QTAddB(174, 58, 4, 1);
		QTAddB(176, 57, 3, 1);
		QTAddB(177, 56, 4, 1);
		QTAddB(178, 55, 4, 1);
		QTAddB(179, 54, 4, 1);
		QTAddB(180, 53, 8, 1);
		QTAddB(181, 52, 18, 1);

		//HUGE BUILDING THAT TAKES UP LIKE 60% OF THE MAP
		QTAddB(173, 51, 26, 1);
		QTAddB(173, 45, 1, 2);
		QTAddB(174, 46, 6, 1);
		QTAddB(177, 45, 3, 1);
		QTAddB(178, 44, 2, 1);
		QTAddB(179, 43, 1, 1);
		QTAddB(180, 38, 1, 11);
		QTAddB(175, 41, 1, 2);
		QTAddB(176, 42, 4, 1);
		QTAddB(177, 37, 1, 2);
		QTAddB(178, 38, 2, 1);
		QTAddB(180, 34, 1, 2);
		QTAddB(181, 34, 5, 1);
		QTAddB(180, 30, 1, 2);
		QTAddB(181, 31, 5, 1);
		QTAddB(182, 26, 1, 2);
		QTAddB(183, 27, 3, 1);
		QTAddB(186, 27, 1, 22);
		QTAddB(192, 26, 1, 23);
		QTAddB(184, 22, 1, 2);
		QTAddB(185, 23, 1, 1);
		QTAddB(186, 22, 1, 3);
		QTAddB(187, 22, 5, 1);
		QTAddB(192, 13, 1, 10);
		QTAddB(190, 13, 2, 1);
		QTAddB(190, 5, 1, 8);
		QTAddB(145, 5, 45, 1);
		QTAddB(179, 1, 1, 2);
		QTAddB(168, 1, 1, 2);
		QTAddB(145, 6, 1, 10);
		QTAddB(136, 16, 10, 1);
		QTAddB(146, 13, 16, 1);
		QTAddB(162, 9, 1, 5);
		QTAddB(162, 16, 1, 1);
		QTAddB(163, 9, 14, 1);
		QTAddB(177, 9, 1, 10);
		QTAddB(134, 19, 44, 1);
		QTAddB(121, 19, 11, 1);
		QTAddB(121, 16, 9, 1);
		QTAddB(120, 13, 1, 7);
		QTAddB(116, 12, 1, 2);
		QTAddB(117, 13, 3, 1);
		QTAddB(117, 9, 1, 2);
		QTAddB(118, 10, 3, 1);
		QTAddB(118, 6, 1, 2);
		QTAddB(119, 7, 2, 1);
		QTAddB(113, 4, 8, 1);
		QTAddB(112, 1, 1, 4);
		QTAddB(120, 1, 1, 1);
		ITAdd(new Platform(163, 16, 3));
		ITAdd(new Platform(163, 13, 3));
		ITAdd(new Platform(168, 14, 4));
		ITAdd(new Platform(174, 13, 3));
		ITAdd(new Platform(174, 16, 3));
		ITAdd(new Platform(146, 16, 16));
		ITAdd(new Platform(142, 13, 3));
		ITAdd(new Platform(142, 10, 3));
		ITAdd(new Platform(142, 7, 3));
		ITAdd(new Platform(132, 19, 2));
		ITAdd(new Platform(130, 16, 6));
		ITAdd(new Platform(121, 13, 3));
		ITAdd(new Platform(121, 10, 3));
		ITAdd(new Platform(121, 7, 3));
		ITAdd(new Platform(121, 4, 3));
		ITAdd(new Platform(183, 47, 3));
		ITAdd(new Platform(181, 44, 3));
		ITAdd(new Platform(183, 41, 3));
		ITAdd(new Platform(181, 38, 3));
		ITAdd(new Platform(187, 48, 3));
		ITAdd(new Platform(189, 45, 3));
		ITAdd(new Platform(187, 42, 3));
		ITAdd(new Platform(189, 39, 3));
		ITAdd(new Platform(187, 36, 3));
		ITAdd(new Platform(189, 33, 3));
		ITAdd(new Platform(187, 30, 3));
		ITAdd(new Platform(187, 27, 5));
		ITAdd(new Platform(195, 25, 4));
		ITAdd(new Platform(193, 22, 3));
		ITAdd(new Platform(196, 19, 3));
		ITAdd(new Platform(193, 16, 3));
		ITAdd(new Platform(193, 13, 6));
		ITAdd(new Platform(191, 10, 3));
		ITAdd(new Platform(191, 7, 3));
	}
}
