package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * A single laser particle, which is fired from an emitter.
 */
public class Laser extends Ellipse2D.Double
{
	/**
	 * The mirror the laser has most recently reflected off of.
	 */
	public Mirror mostRecentMirrorReflectedOff=null; 

	/**
	 * The speed at which the laser propagates.
	 */
	public static final double SPEED = 3.0;
	
	/**
	 * The diameter of the laser.
	 */
	public static final double DIAMETER = 10;
	
	/**
	 * The angle of the laser's travel.
	 */
	public double theta;
	
	/**
	 * The color of the laser.
	 */
	private Color color;

	/**
	 * Constructs a laser with a given x and y-coordinate, theta, and color. 
	 * @param x - The initial x-position.
	 * @param y - The initial y-position.
	 * @param angle - The initial angle of travel, in radians.
	 * @param color - The color of the laser.
	 */
	public Laser(double x, double y, double angle, Color color)
	{
		super(x, y, DIAMETER, DIAMETER);
		this.theta = angle;
		this.color = color;
	}
	
	/**
	 * Returns the color of the laser.
	 * @return
	 */
	public Color getColor()
	{
		return color;
	}
	
	/**
	 * Sets the color of the laser.
	 * @param color
	 */
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	/**
	 * Returns the theta (angle) of the laser.
	 * @return
	 */
	public double getTheta()
	{
		return theta;
	}
	
	/**
	 * Updates the position of the laser according to its speed and its theta (angle).
	 */
	public void tick() {
		this.x += Laser.SPEED * Math.cos(theta); 
		this.y += Laser.SPEED * Math.sin(theta); 
	}
	
	/**
	 * Draws the laser
	 * @param g - Graphics instance used to draw the laser
	 */
	public void draw(Graphics2D g) {
		Graphics2D copy = (Graphics2D) g.create(); //Copy of graphics instance created to avoid rotating all graphics objects when the laser is drawn
		copy.setColor(color);
		copy.rotate(Math.toRadians(theta), getCenterX(), getCenterY());
		copy.fill(this);
		copy.dispose();
	}
}
