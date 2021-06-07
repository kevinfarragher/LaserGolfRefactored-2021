package main;

import java.awt.Color;

public class Level3 extends Level {

	public Level3(GameManager gameManager)
	{
		super(gameManager, 3);
		
		levelObjects.add(new Emitter(50, 50, 0, Color.RED));
		levelObjects.add(new Sensor(950, 400, Color.RED));
		levelObjects.add(new Blackbody(400, 50, 400, 600));
	}
}
