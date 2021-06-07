package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Emitters fire/give off lasers.
 */
public class Emitter extends Ellipse2D.Double implements LevelObject{

	public static final double DIAMETER = 20; //Diameter of this emitter's base ellipse.  
	
	public double theta; //Angle at which this emitter shall emit lasers.  
	
	private Color emitcolor; //Color of the emitter and the laser it emits. 
	
	/**
	 * Constructs an emitter with a given x-coordinate and y-coordinate, theta, and color with a width of 20 and height of 20. The emitter's laser is initialized. 
	 * @param x - x-coordinate of the emitter
	 * @param y - y-coordinate of the emitter
	 * @param itheta - theta of the emitter. (Angle it's rotated at)
	 * @param iemitcolor - color of the emitter
	 */
	public Emitter(double x, double y, double itheta, Color iemitcolor) {
		super(x, y, DIAMETER, DIAMETER); 
		theta = itheta; 
		emitcolor = iemitcolor; 
	}
	
	/**
	 * Returns the color of the emitter
	 * @return
	 */
	public Color getColor() {
		return emitcolor; 
	}
	
	/**
	 * Initializes an emitter's laser and adds the laser to the level's list of lasers. 
	 * @param lasers - the level's list of lasers
	 */
	public void fire(ArrayList<Laser> lasers) {
		lasers.add(new Laser(this.getCenterX(), this.getCenterY(), theta, emitcolor)); //original
	}
	
	/**
	 * No implementation. Lasers are allowed to free go through emitters. 
	 */
	@Override
	public void checkForCollisionWithLaser(ArrayList<Laser> lasers, Level level) {
	}
	
	/**
	 * Checks if a mirror can be placed on a level by checking if the mirror intersects the emitter. If the mirror intersects
	 * the emitter, the mirror is not placed.
	 * @param gameManager - the game's manager
	 * @return - true if mirror can be placed, false if mirror cannot be placed.
	 */
	@Override
	public boolean checkIfMirrorCanBePlaced(GameManager gameManager) {
		//if the mirror, placed using the starting and current points, intersects one of the level's emitters, the mirror isn't placed.
		if(new Mirror(gameManager.startMouseX,gameManager.startMouseY, gameManager.currMouseX,gameManager.currMouseY,true).ptSegDist(new Point2D.Double(getCenterX(), getCenterY()))<10) { 
			return false;
		}
		return true;
	}

	/**
	 * Checks if a mirror can be rotated on a level by checking if the mirror intersects the emitter. If the mirror intersects
	 * the emitter, the mirror is not rotated.
	 * @param gameManager - the game's manager
	 * @return - true if mirror can be rotated, false if mirror cannot be rotated.
	 */
	@Override
	public boolean checkIfMirrorCanBeRotated(Mirror selectedMirror) {
		//if the rotated version of the mirror intersects the emitter, the mirror isn't rotated
		Mirror mRotated=new Mirror(selectedMirror.currRotatedX1,selectedMirror.currRotatedY1,selectedMirror.currRotatedX2,selectedMirror.currRotatedY2,true); //the rotated version of the mirror
		if(mRotated.ptSegDist(new Point2D.Double(getCenterX(), getCenterY()))<Emitter.DIAMETER/2) { 
			return false;
		}
		return true;
	}	
	
	/**
	 * Rotates the emitter
	 */
	public void rotate() {
		theta+=Math.PI/4;
		//System.out.println("Emitter Angle:" + this.emitters.get(i).theta + " Laser Angle:" + this.emitters.get(i).laser.theta);
	}
	
	/**
	 * The emitter is reset by resetting its theta (angle) to 0.
	 */
	public void reset() {
		theta=0;
	}
	
	/**
	 * Draws the emitter. 
	 * @param g - Graphics instance used to draw the emitter
	 */
	public void draw(Graphics2D g) {
		Graphics2D copy = (Graphics2D) g.create(); //Copy of graphics instance created to avoid rotating all graphics objects when the emitter is drawn
		copy.setColor(emitcolor);
		copy.fill(this);
		final double linelen = 40; 
		copy.setColor(Color.BLACK);
		Line2D.Double l = new Line2D.Double(this.getCenterX(), this.getCenterY(), this.getCenterX() + linelen*Math.cos(theta), this.getCenterY() + linelen*Math.sin(theta));
		copy.draw(l); //line drawn to indicate to the user the direction the laser will be shot
		copy.dispose();
	}
}
