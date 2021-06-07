package main;

import java.awt.Color;

public class Level5 extends Level {
	public Level5(GameManager gameManager)
	{
		super(gameManager, 3);
		
		levelObjects.add(new Emitter(100, 200, 0, Color.GREEN));
		levelObjects.add(new Sensor(850, 200, Color.GREEN));
		levelObjects.add(new Explosive(400, 200, this));
	}
}
