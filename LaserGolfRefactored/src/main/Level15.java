package main;

import java.awt.Color;

public class Level15 extends Level {
	public Level15(GameManager gameManager) {
		super(gameManager, 10);
		
		for (int i = 0; i < 1000; i += Explosive.SIZE)
		{
			levelObjects.add(new Explosive(i, 0, this));
			levelObjects.add(new Explosive(i, 420, this));
		}
		for (int i = 0; i < 450; i += Explosive.SIZE)
		{
			levelObjects.add(new Explosive(0, i, this));
			levelObjects.add(new Explosive(970, i, this));
		}
		for (int i = 75; i < 375; i += Explosive.SIZE)
			levelObjects.add(new Explosive(750, i, this));
		
		levelObjects.add(new Emitter(50, 50, Math.PI / 2, Color.GREEN));
		
		levelObjects.add(new BeamSplitter(new Sensor(50, 200, Color.GREEN),
				new Emitter(50, 210, Math.PI / 2, Color.RED),
				new Emitter(60, 200, 0, Color.RED)));
		levelObjects.add(new BeamSplitter(new Sensor(500, 200, Color.CYAN),
				new Emitter(505, 195, -Math.PI / 4, Color.ORANGE),
				new Emitter(510, 200, 0, Color.BLUE),
				new Emitter(505, 205, Math.PI / 4, Color.PINK)));
		
		levelObjects.add(new ColorFilter(30, 400, 100, 20, Color.CYAN));
		levelObjects.add(new ColorFilter(725, 50, 75, 20, Color.MAGENTA));
		
		levelObjects.add(new Sensor(925, 150, Color.MAGENTA));
		levelObjects.add(new Sensor(925, 200, Color.RED));
		levelObjects.add(new Sensor(925, 250, Color.BLUE));
	}
}
