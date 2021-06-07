package main;

/**
 * A vector in two-dimensional space.
 */
public class Vector2D
{
	/**
	 * The vector's x-component.
	 */
	private double x;
	
	/**
	 * The vector's y-component.
	 */
	private double y;
	
	/**
	 * Constructs a <code>Vector2D</code> with the specified x- and y-components.
	 * 
	 * @param x - the x-component
	 * @param y - the y-component
	 */
	public Vector2D(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructs a <code>Vector2D</code> with a magnitude of 1 in the specified direction.
	 * 
	 * @param theta - the direction angle
	 */
	public Vector2D(double theta)
	{
		this.x = Math.cos(theta);
		this.y = Math.sin(theta);
	}
	
	/**
	 * Returns the sum of this vector and the specified vector.
	 * 
	 * @param other - the vector to be added
	 * @return - the vector sum of this and other
	 */
	public Vector2D add(Vector2D other)
	{
		return new Vector2D(this.x + other.x, this.y + other.y);
	}
	
	/**
	 * Returns the difference of this vector and the specified vector.
	 * 
	 * @param other - the vector to be subtracted
	 * @return - the vector difference of this and other
	 */
	public Vector2D subtract(Vector2D other)
	{
		return new Vector2D(this.x - other.x, this.y - other.y);
	}
	
	/**
	 * Returns the specified scalar multiple of this vector.
	 * 
	 * @param scalar - the scale factor
	 * @return - a scalar multiple of this vector at the specified scale factor
	 */
	public Vector2D scalarMultiply(double scalar)
	{
		return new Vector2D(this.x * scalar, this.y * scalar);
	}
	
	/**
	 * Returns the dot product of this vector and the specified vector.
	 * 
	 * @param other - the vector whose dot product with this vector is to be computed
	 * @return - the dot product of this vector and the other vector
	 */
	public double dotProduct(Vector2D other)
	{
		return this.x * other.x + this.y * other.y;
	}
	
	/**
	 * Returns the direction angle of this vector.
	 * 
	 * @return - the direction angle of this vector
	 */
	public double angleOf()
	{
		return (x < 0)? Math.atan(y/x) + Math.PI : Math.atan(y/x);
	}
}
