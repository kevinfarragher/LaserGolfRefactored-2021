package main;

import java.awt.Color;

public class Level4 extends Level {
	public Level4(GameManager gameManager)
	{
		super(gameManager, 2);
		
		levelObjects.add(new Emitter(100,200,0,Color.GREEN));
		levelObjects.add(new Blackbody(300, 100, 300, 300));
		levelObjects.add(new ColorFilter(400, 50, 15, 100, Color.BLUE));
		levelObjects.add(new Sensor(850,200, Color.BLUE));
	}
}
