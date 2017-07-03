package org.usfirst.frc.team4453.newvisionlib;

/**
 * Class representing a 2d point.
 * @author conner
 *
 */
public class Point2d implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4527226566523120208L;

	/**
	 * X position.
	 */
	public double X;
	
	/**
	 * Y position
	 */
	public double Y;
	
	/**
	 * Default constructor. Sets all values to 0.
	 */
	public Point2d()
	{
		X = 0;
		Y = 0;
	}
	
	/**
	 * Constructor initializing values.
	 * @param x X value.
	 * @param y Y value.
	 */
	public Point2d(double x, double y)
	{
		X = x;
		Y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(X);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(Y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point2d other = (Point2d) obj;
		if (Double.doubleToLongBits(X) != Double.doubleToLongBits(other.X))
			return false;
		if (Double.doubleToLongBits(Y) != Double.doubleToLongBits(other.Y))
			return false;
		return true;
	}
	
	
}