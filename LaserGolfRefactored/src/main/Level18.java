package main;

import java.awt.Color;

public class Level18 extends Level {
	public Level18(GameManager gameManager) {
		super(gameManager, 7);
		
		levelObjects.add(new Emitter(25, 25, 0, Color.GREEN));
		
		levelObjects.add(new BeamSplitter(new Sensor(35, 300, Color.RED),
				new Emitter(25, 320, Math.PI / 2, Color.BLUE),
				new Emitter(25, 300, -Math.PI / 5, Color.GREEN),
				new Emitter(25, 280, -Math.PI / 2, Color.MAGENTA)));
		
		levelObjects.add(new BeamSplitter(new Sensor(500, 415, Color.BLUE),
				new Emitter(520, 415, 0, Color.BLUE),
				new Emitter(480, 415, Math.PI, Color.BLUE)));
		
		levelObjects.add(new Sensor(950, 25, Color.GREEN));
		levelObjects.add(new Sensor(25, 125, Color.MAGENTA));
		levelObjects.add(new Sensor(25, 415, Color.BLUE));
		levelObjects.add(new Sensor(950, 415, Color.BLUE));

		levelObjects.add(new ColorFilter(920, 290, 15, 40, Color.RED));
		
		levelObjects.add(new Blackbody(0, 75, 400, 200));
		levelObjects.add(new Blackbody(410, 200, 635, 100));
		levelObjects.add(new Blackbody(440, 275, 665, 175));
		levelObjects.add(new Blackbody(200, 280, 1000, 280));
		levelObjects.add(new Mirror(12, 90, 405, 212,true));
		levelObjects.add(new Mirror(408, 213, 645, 107,true));
		levelObjects.add(new Mirror(430, 270, 668, 162,true));
		levelObjects.add(new Mirror(193, 270, 430, 270,true));
		levelObjects.add(new Mirror(193, 290, 987, 290,true));
		levelObjects.add(new Mirror(193, 328, 987, 328,true));
		levelObjects.add(new Mirror(987, 290, 987, 328,true));
		levelObjects.add(new Blackbody(200, 340, 1000, 340));
	}
}
