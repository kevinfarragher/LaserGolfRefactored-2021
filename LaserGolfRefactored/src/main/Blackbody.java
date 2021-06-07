package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Black bodies absorb lasers, causing failure to complete a level on an attempt upon collision
 *
 */
public class Blackbody extends Surface{

	/**
	 * Black body's color
	 */
	static final Color BLACKBODY_COLOR = new Color(0x543a3a);
	
	/**
	 * Constructs a black body with given starting and ending points.
	 * @param x1 - the x-coordinate of the black body's starting point
	 * @param y1 - the y-coordinate of the black body's starting point
	 * @param x2 - the x-coordinate of the black body's ending point
	 * @param y2 - the y-coordinate of the black body's ending point
	 */
	public Blackbody(double x1, double y1, double x2, double y2) {
		super(x1, y1, x2, y2, BLACKBODY_COLOR);
	}	
	
	/**
	 * Checks if a black body has collided with a laser. If so, the level is reset. 
	 * @param lasers - the lasers of a level
	 */
	public void checkForCollisionWithLaser(ArrayList<Laser>lasers,Level level) {
		for (Laser laser : lasers) {
			if(this.ptSegDist(new Point2D.Double(laser.getCenterX(), laser.getCenterY()))<10) { //if a laser intersects a black body, the level is reset.
				lasers.clear(); 
				level.isStarted=false;
				level.resetLevel();
				return;
			}
		}
	}
	
	/**
	 * Checks if a mirror can be placed on a level by checking if the mirror intersects the black body. If the mirror intersects
	 * the black body, the mirror is not placed.
	 * @param gameManager - the game's manager
	 * @return - true if mirror can be placed, false if mirror cannot be placed.
	 */
	@Override
	public boolean checkIfMirrorCanBePlaced(GameManager gameManager) {
		//if the mirror, placed using the starting and current points, intersects the black body, the mirror isn't placed.
		if(new Mirror(gameManager.startMouseX,gameManager.startMouseY, gameManager.currMouseX,gameManager.currMouseY,true).intersectsLine(this)) {
			return false;
		}
		return true; 
	}

	/**
	 * Checks if a mirror can be rotated on a level by checking if the mirror intersects the black body. If the mirror intersects
	 * the black body, the mirror is not rotated.
	 * @param selectedMirror - the selected mirror attempted to be rotated
	 * @return - true if mirror can be rotated, false if mirror cannot be rotated.
	 */
	@Override
	public boolean checkIfMirrorCanBeRotated(Mirror selectedMirror) {
		//if the rotated version of the mirror intersects the black body, the mirror isn't rotated
		Mirror mRotated=new Mirror(selectedMirror.currRotatedX1,selectedMirror.currRotatedY1,selectedMirror.currRotatedX2,selectedMirror.currRotatedY2,true); //the rotated version of the mirror
		if(mRotated.intersectsLine(this)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Draws the black body.
	 * @param g - Graphics instance used to draw the black body
	 */
	public void draw(Graphics2D g) {
		Graphics2D copy = (Graphics2D) g.create(); //Copy of graphics instance created to avoid rotating all graphics objects when the black body is drawn
		copy.setColor(col);
		copy.setStroke(new BasicStroke(20));
		copy.rotate(Math.toRadians(additionalRotation), (x1+x2)/2, (y1+y2)/2);
		copy.draw(this);
		copy.dispose();
	}
}
