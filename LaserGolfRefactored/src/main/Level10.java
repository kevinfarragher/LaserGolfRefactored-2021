package main;

import java.awt.Color;

public class Level10 extends Level {
	public Level10(GameManager gameManager)
	{
		super(gameManager, 4);
		
		levelObjects.add(new Emitter(50, 50, 0, Color.magenta));
		levelObjects.add(new Sensor(915, 400, Color.magenta));
		levelObjects.add(new Blackbody(500, 0, 500, 300));
		levelObjects.add(new Blackbody(250, 125, 750, 125));
		levelObjects.add(new Mirror(900, 100,900,430,true));
		levelObjects.add(new Mirror(950, 100,950,430,true));
		levelObjects.add(new Mirror(900, 430,950,430,true));
		levelObjects.add(new Explosive(485, 350, this));
	}
}
