package main;

import java.awt.Color;

public class Level16 extends Level {
	public Level16(GameManager gameManager)
	{
		super(gameManager, 7);
		
		levelObjects.add(new Emitter(50, 50, Math.PI / 2, Color.GREEN));
		
		levelObjects.add(new BeamSplitter(new Sensor(100, 300, Color.GREEN),
				new Emitter(110, 310, 0, Color.BLUE),
				new Emitter(110, 290, -Math.PI / 4, Color.RED)));
		
		levelObjects.add(new BeamSplitter(new Sensor(330, 100, Color.RED),
				new Emitter(340, 110, 0, Color.MAGENTA),
				new Emitter(340, 90, 0, Color.ORANGE)));
		
		levelObjects.add(new Sensor(950, 220, Color.MAGENTA));
		levelObjects.add(new Sensor(950, 200, Color.ORANGE));
		levelObjects.add(new Sensor(730, 50, Color.BLUE));
	

		levelObjects.add(new Blackbody(700, 0, 700, 300));
		levelObjects.add(new Blackbody(600, 800 , 600, 200));
	}
}
