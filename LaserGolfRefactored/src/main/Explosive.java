package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * A crate of explosives that causes the player to fail the current level if a laser touches it.
 */
public class Explosive extends Rectangle2D.Double implements LevelObject
{
	/**
	 * The width and height of the crate of explosives.
	 */
	public static final int SIZE = 30;
	
	/**
	 * The level on which this crate of explosives exists.
	 */
	private Level level;
	
	/**
	 * The "danger" icon displayed on the front of the crate of explosives.
	 */
	private Polygon triangle;
	
	/**
	 * Constructs an explosive.
	 * @param x - the x-coordinate at which the crate of explosives is to be placed
	 * @param y - the y-coordinate at which the crate of explosives is to be placed
	 * @param level - the level on which the crate of explosives is to be placed
	 */
	public Explosive(int x, int y, Level level)
	{
		super(x, y, SIZE, SIZE);
		this.level = level;
		triangle = new Polygon();
		triangle.addPoint(x + 5, y + SIZE - 5);
		triangle.addPoint(x + (SIZE / 2), y + 5);
		triangle.addPoint(x + SIZE - 5, y + SIZE - 5);
	}
	
	/**
	 * Checks whether this crate of explosives has been hit and restarts the level if so.
	 * @param lasers - level's lasers
	 */
	public void checkForCollisionWithLaser(ArrayList<Laser> lasers, Level level)
	{
		for (Laser laser : lasers)
			if (laser.intersects(this))
			{
				level.resetLevel();
				break;
			}
	}
	
	/**
	 * Checks if a mirror can be placed on a level by checking if the mirror intersects the explosive. If the mirror intersects
	 * the explosive, the mirror is not placed.
	 * @param gameManager - the game's manager
	 * @return - true if mirror can be placed, false if mirror cannot be placed.
	 */
	@Override
	public boolean checkIfMirrorCanBePlaced(GameManager gameManager) {
		//if the mirror, placed using the starting and current points, intersects the explosive, the mirror isn't placed.
		if(new Mirror(gameManager.startMouseX,gameManager.startMouseY, gameManager.currMouseX,gameManager.currMouseY,true).intersects(this)){
			return false;
		}
		return true;
	}

	/**
	 * Checks if a mirror can be rotated on a level by checking if the mirror intersects the explosive. If the mirror intersects
	 * the explosive, the mirror is not rotated.
	 * @param gameManager - the game's manager
	 * @return - true if mirror can be rotated, false if mirror cannot be rotated.
	 */
	@Override
	public boolean checkIfMirrorCanBeRotated(Mirror selectedMirror) {
		//if the rotated version of the mirror intersects the explosive, the mirror isn't rotated
		Mirror mRotated=new Mirror(selectedMirror.currRotatedX1,selectedMirror.currRotatedY1,selectedMirror.currRotatedX2,selectedMirror.currRotatedY2,true); //the rotated version of the mirror
		if(mRotated.ptSegDist(new Point2D.Double(getCenterX(), getCenterY()))<Explosive.SIZE/2) {  
			return false;
		}
		return true;
	}	
	
	/**
	 * Draws the explosive.
	 * @param g - Graphics objects used to draw explosive. 
	 */
	public void draw(Graphics2D g)
	{
		g.setColor(Color.DARK_GRAY);
		g.fill(this);
		g.setColor(Color.YELLOW);
		g.fill(triangle);
		g.setColor(Color.BLACK);
		g.setFont(g.getFont().deriveFont(Font.BOLD));
		g.drawString("!", (float) (getX() + (SIZE * .45)), (float) (getY() + (SIZE * .75)));
	}
}
