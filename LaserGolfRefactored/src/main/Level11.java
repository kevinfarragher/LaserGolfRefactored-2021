package main;

import java.awt.Color;

public class Level11 extends Level {
	public Level11(GameManager gameManager)
	{
		super(gameManager, 5);
		
		levelObjects.add(new Emitter(25, 25, Math.PI / 4, Color.RED));
		levelObjects.add(new Blackbody(70, 100, 100, 70));
		levelObjects.add(new Blackbody(900, 0, 900, 100));
		levelObjects.add(new BeamSplitter(new Sensor(300, 200, Color.RED),
				new Emitter(305, 195, -Math.PI / 4, Color.GREEN),
				new Emitter(310, 200, 0, Color.BLUE)));
		levelObjects.add(new Sensor(950, 25, Color.GREEN));
		levelObjects.add(new Sensor(950, 410, Color.BLUE));
	}
}
