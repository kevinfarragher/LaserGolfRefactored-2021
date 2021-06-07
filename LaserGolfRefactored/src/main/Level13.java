package main;

import java.awt.Color;

public class Level13 extends Level {
	public Level13(GameManager gameManager)
	{
		super(gameManager, 2);
		
		levelObjects.add(new ColorFilter(400, 100, 20, 50, Color.BLUE));
		levelObjects.add(new ColorFilter(400, 200, 20, 50, Color.GREEN));
		levelObjects.add(new ColorFilter(400, 300, 20, 50, Color.RED));
		levelObjects.add(new Emitter(50, 200, 0, Color.magenta));
		levelObjects.add(new Sensor(915, 200, Color.red));
		levelObjects.add(new Explosive(600, 200, this));
	}
}
