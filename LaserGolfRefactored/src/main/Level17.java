package main;

import java.awt.Color;

public class Level17 extends Level{
	public Level17(GameManager gameManager) {
		super(gameManager, 6);
		
		levelObjects.add(new Emitter(30, 30, Math.PI/2, Color.magenta)); 
		
		levelObjects.add(new ColorFilter(500, 0, 10, 150, Color.red));
		levelObjects.add(new ColorFilter(500, 150, 10, 150, Color.green));
		levelObjects.add(new ColorFilter(500, 300, 10, 150, Color.blue));
		
		levelObjects.add(new Sensor(800, 80, Color.blue));
		levelObjects.add(new Sensor(800, 220, Color.red));
		levelObjects.add(new Sensor(800, 350, Color.green));
		
		levelObjects.add(new ColorFilter(900, 0, 10, 150, Color.blue));
		levelObjects.add(new ColorFilter(900, 150, 10, 150, Color.red));
		levelObjects.add(new ColorFilter(900, 300, 10, 150, Color.green));
		
		double bspx = 30, bspy = 120, bsp_fov = 20, bsp_dist = 5; 
		levelObjects.add(new BeamSplitter(new Sensor(bspx, bspy, Color.magenta), new Emitter(bspx + bsp_dist, bspy - bsp_fov, 0.0, Color.green), new Emitter(bspx + bsp_dist, bspy + bsp_fov, Math.PI/2, Color.magenta)));
		
		bspx = 30; bspy = 250; bsp_fov = 20; bsp_dist = 5; 
		levelObjects.add(new BeamSplitter(new Sensor(bspx, bspy, Color.magenta), new Emitter(bspx + bsp_dist, bspy - bsp_fov, 0.0, Color.blue), new Emitter(bspx + bsp_dist, bspy + bsp_fov, Math.PI/2, Color.red)));
		
		levelObjects.add(new Mirror(0, 300, 50, 350,true)); 
		
		for(int i = 0; i < 5; i++) { 
			levelObjects.add(new Explosive((int)(500 + 300*Math.random()), (int)(400*Math.random()), this));
		}
		
		levelObjects.add(new Mirror(960, 0, 950, 500,true)); 
	}
}
