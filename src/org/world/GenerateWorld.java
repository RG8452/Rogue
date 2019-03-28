package org.world;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import org.DataRetriever;
import org.world.interactable.Ladder;
import org.world.interactable.ManCannon;
import org.world.interactable.Platform;

public class GenerateWorld
{
	public static void readMap(File currentWorld) throws FileNotFoundException
	{
		BufferedReader reader = new BufferedReader(new FileReader(currentWorld));
		Scanner sc= new Scanner(reader);
		while(sc.hasNext())
		{
			char collisionType = sc.next().charAt(0);
				switch (collisionType)
				{
					case 'B':
						DataRetriever.getWorld().QTAddB(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
						break;
					case 'P':
						DataRetriever.getWorld().ITAdd(new Platform(sc.nextInt(), sc.nextInt(), sc.nextInt()));
						break;
					case 'M':
						DataRetriever.getWorld().ITAdd(new ManCannon(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.next()));
						break;
					case 'L':
						DataRetriever.getWorld().ITAdd(new Ladder(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.next()));
						break;
					default:
						System.out.println("Unrecognized Command : " + collisionType + sc.nextLine());
				}
		}
		sc.close();
	}
	
}
