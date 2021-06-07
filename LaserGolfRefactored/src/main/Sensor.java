package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Timer;

/**
 * A sensor that can detect when it has been hit by a laser.
 * 
 */
public class Sensor extends Rectangle2D.Double implements LevelObject
{
	/**
	 * The amount of time (in milliseconds) a sensor's hit flag stays true following a hit.
	 */
	public static final int TIMEOUT = 1000;

	/**
	 * The width and height of the sensor.
	 */
	public static final double SIZE = 20;
	
	/**
	 * Controls the resetting of the hit flag after the timeout elapses.
	 */
	private Timer timeoutController;
	
	/**
	 * The color of laser this sensor responds to.
	 */
	private final Color color;

	/**
	 * Whether this sensor has been hit by a laser of the appropriate color.
	 */
	public boolean hit = false;

	/**
	 * Constructs a sensor with a given x and y-coordinate and color.
	 * @param x - The sensor's x position
	 * @param y - The sensor's y position
	 * @param color - The color of laser this sensor responds to
	 */
	public Sensor(double x, double y, Color color)
	{
		super(x, y, SIZE, SIZE);
		this.color = color;
		timeoutController = new Timer(TIMEOUT, e -> {hit = false;});
		timeoutController.setRepeats(false);
	}
	
	/**
	 * Returns the color of the sensor.
	 * @return - sensor's color
	 */
	public Color getColor()
	{
		return color;
	}

	/**
	 * Returns whether the sensor has been hit by a laser of its color
	 * @return - whether the sensor has been hit by a laser of its color
	 */
	public boolean isHit()
	{
		return hit;
	}
	
	/**
	 * Checks if a sensor has collided with a laser. If so, the sensor is labeled as hit.
	 * @param lasers - the lasers of a level to be checked for a hit
	 */
	public void checkForCollisionWithLaser(ArrayList<Laser> lasers, Level level)
	{	
		Iterator<Laser> laserIter = lasers.iterator();
		while (laserIter.hasNext()) {
			Laser laser = laserIter.next(); 
			if (laser.getColor().equals(this.color) && laser.intersects(this))
			{
				laserIter.remove();
				hit = true;
				timeoutController.restart();
			}
		}
	}
	
	/**
	 * Checks if a mirror can be placed on a level by checking if the mirror intersects the sensor. If the mirror intersects
	 * the sensor, the mirror is not placed.
	 * @param gameManager - the game's manager
	 * @return - true if mirror can be placed, false if mirror cannot be placed.
	 */
	@Override
	public boolean checkIfMirrorCanBePlaced(GameManager gameManager) {
		//if the mirror, placed using the starting and current points, intersects the sensor, the mirror isn't placed.
		//if(new Mirror(gameManager.startMouseX,gameManager.startMouseY, gameManager.currMouseX,gameManager.currMouseY).ptSegDist(new Point2D.Double(this.sensors.get(i).getCenterX(), this.sensors.get(i).getCenterY()))<10) { 
		if(new Mirror(gameManager.startMouseX,gameManager.startMouseY, gameManager.currMouseX,gameManager.currMouseY,true).intersects(this)) { 
			return false;
		}
		return true;
	}

	/**
	 * Checks if a mirror can be rotated on a level by checking if the mirror intersects the sensor. If the mirror intersects
	 * the sensor, the mirror is not rotated.
	 * @param gameManager - the game's manager
	 * @return - true if mirror can be rotated, false if mirror cannot be rotated.
	 */
	@Override
	public boolean checkIfMirrorCanBeRotated(Mirror selectedMirror) {
		//if the rotated version of the mirror intersects the sensor, the mirror isn't rotated
		Mirror mRotated=new Mirror(selectedMirror.currRotatedX1,selectedMirror.currRotatedY1,selectedMirror.currRotatedX2,selectedMirror.currRotatedY2,true); //the rotated version of the mirror
		if(mRotated.ptSegDist(new Point2D.Double(getCenterX(), getCenterY()))<Sensor.SIZE/2) {  
			return false;
		}
		return true;
	}

	/**
	 * Draws the sensor on the screen.
	 * @param g - graphics instance used to draw sensor
	 */
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(3));
		g.draw(this);
		g.setColor(color);
		g.fill(this);
	}
}
