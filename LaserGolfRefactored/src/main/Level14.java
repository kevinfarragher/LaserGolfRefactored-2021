package main;

import java.awt.Color;

public class Level14 extends Level {
	public Level14(GameManager gameManager) {
		super(gameManager, 12);
		
		levelObjects.add(new Emitter(50, 50, 0, Color.RED));
		levelObjects.add(new Blackbody(120, 0, 120, 110));
		
		levelObjects.add(new Emitter(50, 400, 0, Color.RED));
		levelObjects.add(new Blackbody(120, 450, 120, 340));
		
		levelObjects.add(new Emitter(930, 50, 0, Color.RED));
		levelObjects.add(new Blackbody(860, 0, 860, 110));
		
		levelObjects.add(new Emitter(930, 400, 0, Color.RED));
		levelObjects.add(new Blackbody(860, 450, 860, 340));
		
		levelObjects.add(new Blackbody(440, 170, 540, 170));
		levelObjects.add(new Sensor(465, 190, Color.GREEN)); 
		levelObjects.add(new Sensor(495, 190, Color.GREEN)); 
		levelObjects.add(new Sensor(465, 220, Color.BLUE));
		levelObjects.add(new Sensor(495, 220, Color.BLUE));
		levelObjects.add(new Blackbody(440, 260, 540, 260));
		
		levelObjects.add(new ColorFilter(480,20,20,20,Color.BLUE));
		levelObjects.add(new ColorFilter(480,410,20,20,Color.GREEN));
		
		levelObjects.add(new Explosive(225,400,this));
		levelObjects.add(new Explosive(745,400,this));
		levelObjects.add(new Explosive(225,50,this));
		levelObjects.add(new Explosive(745,50,this));
		levelObjects.add(new Explosive(225,210,this));
		levelObjects.add(new Explosive(745,210,this));
	}
}
