package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * A color filter that changes the color of the lasers passing through it.
 */
public class ColorFilter extends Rectangle2D.Double implements LevelObject
{
	
	/**
	 * The color to which this filter changes the laser passing through it.
	 */
	private Color color;

	/**
	 * Constructs a color filter. 
	 * @param x - the x coordinate of the color filter's location
	 * @param y - the y coordinate of the color filter's location
	 * @param width - the color filter's width
	 * @param height - the color filter's height
	 * @param color - the color filter's color 
	 */
	public ColorFilter(double x, double y, double width, double height, Color color)
	{
		super(x, y, width, height);
		this.color = color;
	}
	
	/**
	 * Checks if a color filter has collided with a laser. If so, the color of the laser passing through the is changed 
	 * to the color of the filter.
	 * @param lasers - the lasers to be checked for passing through this filter
	 */
	public void checkForCollisionWithLaser(ArrayList<Laser>lasers,Level level) {
		for (Laser laser : lasers) {
			if (laser.intersects(this)) {
				laser.setColor(color);
			}
		}
	}
	
	/**
	 * Checks if a mirror can be placed on a level by checking if the mirror intersects the color filter. If the mirror intersects
	 * the color filter, the mirror can still be placed.
	 * @param gameManager - the game's manager
	 * @return - true if mirror can be placed, false if mirror cannot be placed.
	 */
	@Override
	public boolean checkIfMirrorCanBePlaced(GameManager gameManager) {
		//if the mirror, placed using the starting and current points, intersects the color filter, the mirror isn't placed.
		/*if(new Mirror(gameManager.startMouseX,gameManager.startMouseY, gameManager.currMouseX,gameManager.currMouseY,true).intersects(this)){
			return false;
		}*/ //based on old implementation
		return true;
	}

	/**
	 * Checks if a mirror can be rotated on a level by checking if the mirror intersects the color filter. If the mirror intersects
	 * the color filter, the mirror can still be rotated.
	 * @param gameManager - the game's manager
	 * @return - true if mirror can be rotated, false if mirror cannot be rotated.
	 */
	@Override
	public boolean checkIfMirrorCanBeRotated(Mirror selectedMirror) {
		//if the rotated version of the mirror intersects the color filter, the mirror isn't rotated
		Mirror mRotated=new Mirror(selectedMirror.currRotatedX1,selectedMirror.currRotatedY1,selectedMirror.currRotatedX2,selectedMirror.currRotatedY2,true); //the rotated version of the mirror
		//if(mRotated.ptSegDist(new Point2D.Double(this.colorfilters.get(i).getCenterX(), this.colorfilters.get(i).getCenterY()))<Explosive.SIZE/2) { //if rotated version of the mirror contains/intersects one of the level's color filters, the mirror is reset to its previous (initial) rotation before this current rotation. The mirror's currents points are reset to their previous values before this current rotation started and the selected mirror for rotation is set to null. 
		/*if(mRotated.intersects(this)){ 
			return false;
		}*/ //based on old implementation
		return true;
	}	
	
	/**
	 * Draws the color filter.
	 * @param g - graphics instance used to draw the color filter
	 */
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.fill(this);
	}
}
