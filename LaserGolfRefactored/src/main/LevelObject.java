package main;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Contains all methods each level object must implement.
 */
public interface LevelObject {
	
	/**
	 * Checks if a level object has collided with a level's lasers, with an appropriate action being complete.
	 * @param lasers - a level's lasers
	 * @param level - a level
	 */
	public abstract void checkForCollisionWithLaser(ArrayList<Laser>lasers,Level level);
	
	/**
	 * Draws a level object.
	 * @param g - Graphics instance used to draw graphics object
	 */
	public abstract void draw(Graphics2D g);
	
	/**
	 * Checks if a mirror can be placed on a level by checking if the mirror intersects a level object. If the mirror intersects
	 * a level object, the mirror is not placed.
	 * @param gameManager - the game's manager
	 * @return - true if mirror can be placed, false if mirror cannot be placed.
	 */
	public abstract boolean checkIfMirrorCanBePlaced(GameManager gameManager);
	
	/**
	/**
	 * Checks if a mirror can be rotated on a level by checking if the mirror intersects a level object. If the mirror intersects
	 * a level object, the mirror is not rotated.
	 * @param selectedMirror - the selected mirror attempted to be rotated
	 * * @return - true if mirror can be rotated, false if mirror cannot be rotated
	 */
	public abstract boolean checkIfMirrorCanBeRotated(Mirror selectedMirror);
	
	
}
