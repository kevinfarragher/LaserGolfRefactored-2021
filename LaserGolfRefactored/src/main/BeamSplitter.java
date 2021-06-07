package main;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A beam splitter is composed of a sensor and two or more emitters. When the sensor is hit by a laser, each emitter fires.
 */
public class BeamSplitter implements LevelObject
{
	
	/**
	 * The sensor that controls activation of the beam splitter's emitter components.
	 */
	public Sensor sensor;
	
	/**
	 * The emitters to be activated by the sensor.
	 */
	public List<Emitter> emitters;
	
	/**
	 * Constructs a beam splitter with a sensor and emitters
	 * @param sensor - a beam splitter's sensor
	 * @param emitters - a beam splitter's emitters
	 */
	public BeamSplitter(Sensor sensor, List<Emitter> emitters)
	{
		this.sensor = sensor;
		this.emitters = emitters;
	}
	
	/**
	 * Constructs a beam splitter with a sensor and emitters
	 * @param sensor - a beam splitter's sensor
	 * @param emitters - a beam splitter's emitters
	 */
	public BeamSplitter(Sensor sensor, Emitter... emitters)
	{
		this(sensor, new ArrayList<>(Arrays.asList(emitters)));
	}

	/**
	 * Checks whether the sensor has collided with a laser. If so, each of its emitters are fired.
	 * @param laser - the level's lasers
	 */
	public void checkForCollisionWithLaser(ArrayList<Laser>lasers,Level level) {
		sensor.checkForCollisionWithLaser(lasers, level);
		if(sensor.isHit()==true) { 
			for(int i = 0; i < emitters.size(); i++) {
				emitters.get(i).fire(lasers);
			}
		}
	}
	
	/**
	 * Checks if a mirror can be placed on a level by checking if the mirror intersects the beam splitter. If the mirror intersects
	 * the beam splitter, the mirror is not placed.
	 * @param gameManager - the game's manager
	 * @return - true if mirror can be placed, false if mirror cannot be placed.
	 */
	@Override
	public boolean checkIfMirrorCanBePlaced(GameManager gameManager) {
		//if the mirror, placed using the starting and current points, intersects the beam splitter, the mirror isn't placed.
		for(int j=0;j<emitters.size();j++) {
			if(new Mirror(gameManager.startMouseX,gameManager.startMouseY, gameManager.currMouseX,gameManager.currMouseY,true).ptSegDist(new Point2D.Double(emitters.get(j).getCenterX(), emitters.get(j).getCenterY()))<10){
				return false;
			}
		}
		if(new Mirror(gameManager.startMouseX,gameManager.startMouseY, gameManager.currMouseX,gameManager.currMouseY,true).intersects(sensor)) { 
			return false;
		}
		return true; 
	}

	/**
	 * Checks if a mirror can be rotated on a level by checking if the mirror intersects the beam splitter. If the mirror intersects
	 * the beam splitter, the mirror is not rotated.
	 * @param selectedMirror - the selected mirror attempted to be rotated
	 * @return - true if mirror can be rotated, false if mirror cannot be rotated.
	 */
	@Override
	public boolean checkIfMirrorCanBeRotated(Mirror selectedMirror) {
		//if the rotated version of the mirror intersects the beam splitter, the mirror isn't rotated
		Mirror mRotated=new Mirror(selectedMirror.currRotatedX1,selectedMirror.currRotatedY1,selectedMirror.currRotatedX2,selectedMirror.currRotatedY2,true); //the rotated version of the mirror
		for(int j=0;j<emitters.size();j++) {
			if(mRotated.ptSegDist(new Point2D.Double(emitters.get(j).getCenterX(), emitters.get(j).getCenterY()))<Emitter.DIAMETER/2) { 
				return false;
			}
		}
		if(mRotated.ptSegDist(new Point2D.Double(sensor.getCenterX(), sensor.getCenterY()))<Sensor.SIZE/2) { 
			return false;
		}
		return true;
	}	
	
	/**
	 * Draws a beam splitter.
	 * @param g - Graphics instance used to draw the beam splitter. 
	 */
	public void draw(Graphics2D g) {
		sensor.draw(g);
		for(int i = 0; i < emitters.size(); i++) {
			emitters.get(i).draw(g); 
		}
	}
}
