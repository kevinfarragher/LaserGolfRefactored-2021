package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * Details the properties/behaviors of surfaces. Concrete surfaces (mirrors or blackbodies) can be created for levels. 
 */
public class Surface extends Line2D.Double implements LevelObject {
	
	/**
	 * Surface's theta (angle) that it is initially drawn at and additional rotation
	 */
	protected double additionalRotation;
	protected double theta;
	
	/**
	 * Distance which a laser shall be deemed to have 'collided' with a surface. 
	 */
	protected static final double COLLIDE_DIST = 5; 
	
	/**
	 * Color of this surface. 
	 */
	protected Color col;
	
	/**
	 * Determines whether a surface is part of a level's design or not. If a surface is part of a level's design, 
	 * it cannot be removed/rotated by the user.
	 */
    public boolean isPartOfLevelDesign=true;
	
	/**
	 * Constructs a surface with given starting and ending points and color. Its theta (angle) it was drawn at is determined and its additional rotation is set to 0.
	 * @param x1 - x-coordinate of surface's starting point
     * @param y1 - y-coordinate of surface's starting point
     * @param x2 - x-coordinate of surface's ending point
     * @param y2 - y-coordinate of surface's ending point
	 * @param icol - color of the surface
	 */
	public Surface(double x1, double y1, double x2, double y2, Color icol) {
		super(x1, y1, x2, y2); 
		col = icol; 
		additionalRotation=0;
		theta=getNormal().angleOf();
	}
	
	/**
	 * Returns the color of the surface
	 * @return - the color of the surface
	 */
	protected Color getColor() {
		return col; 
	}
	
	/**
	 * Computes the normal vector to the surface
	 * @return - the normal vector to the surface
	 */
	protected Vector2D getNormal() {
		final double orthogonal_slope = -1.0 / (((y2) - (y1))/((x2) - (x1)));
		return new Vector2D(Math.atan2(orthogonal_slope, 1));
	}
	
	/**
	 * Checks if surface has collided with laser. Overridden by subclasses (Blackbody and Mirror)
	 */
	@Override
	public void checkForCollisionWithLaser(ArrayList<Laser> lasers, Level level) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Checks if a mirror can be placed on a level by checking if the mirror intersects the surface. If the mirror intersects
	 * the surface, the mirror is not placed. Overridden by subclasses (Blackbody and Mirror)
	 * @param gameManager - the game's manager
	 * @return - true if mirror can be placed, false if mirror cannot be placed.
	 */
	@Override
	public boolean checkIfMirrorCanBePlaced(GameManager gameManager) {
		return true;
	}

	/**
	 * Checks if a mirror can be rotated on a level by checking if the mirror intersects the surface. If the mirror intersects
	 * the surface, the mirror is not rotated. Overridden by subclasses (Blackbody and Mirror).
	 * @param gameManager - the game's manager
	 * @return - true if mirror can be rotated, false if mirror cannot be rotated.
	 */
	@Override
	public boolean checkIfMirrorCanBeRotated(Mirror selectedMirror) {
		return true;
	}	
 
	/**
	 * Draws the surface. Overridden by subclasses (Blackbody and Mirror)
	 * @param g - graphics instance used to draw the surface
	 */
	public void draw(Graphics2D g) {
		Graphics2D copy = (Graphics2D) g.create(); //Copy of graphics instance created to avoid rotating all graphics objects when the mirror is drawn
		copy.setColor(col);
		copy.setStroke(new BasicStroke(5));
		copy.rotate(Math.toRadians(additionalRotation), (x1+x2)/2, (y1+y2)/2);
		copy.draw(this);
		copy.dispose();
	}
}
