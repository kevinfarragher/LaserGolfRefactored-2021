package main;

import java.awt.Color;

public class Level2 extends Level
{
	
	public Level2(GameManager gameManager)
	{
		super(gameManager, 1);
		
		levelObjects.add(new Emitter(50, 50, 0, Color.RED));
		levelObjects.add(new Sensor(950, 400, Color.RED));
		levelObjects.add(new Blackbody(300, 350, 700, 100));
	}
}
