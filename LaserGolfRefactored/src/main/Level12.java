package main;

import java.awt.Color;

public class Level12 extends Level {
	public Level12(GameManager gameManager)
	{
		super(gameManager, 2);
		
		levelObjects.add(new Emitter(50, 50, 0, Color.GREEN));
		levelObjects.add(new Emitter(50, 400, 0, Color.RED));
		levelObjects.add(new Sensor(800, 50, Color.RED));
		levelObjects.add(new Sensor(800, 400, Color.GREEN));
		levelObjects.add(new Blackbody(400, 100, 400, 350));
	}
}
