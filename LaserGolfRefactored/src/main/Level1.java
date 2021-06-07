package main;

import java.awt.Color;

public class Level1 extends Level {

	/**
	 * Constructs level 1 of the game with a given gameManager. Objects are added to the level.
	 * @param driver - driver of the program
	 * @param gameManager - gameManager of the program
	 */
	public Level1(GameManager gameManager) {
		super(gameManager, 0);
		
		levelObjects.add(new Emitter(100,200,0,Color.GREEN));
		levelObjects.add(new Sensor(850,200, Color.GREEN));
	}

}
