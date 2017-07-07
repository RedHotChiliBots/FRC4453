package org.usfirst.frc.team4453.newvisionlib;
/**
 * Class representing a 3d point.
 * @author conner
 *
 */
public class Point3d implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -156105188720148656L;

	/**
	 * X position
	 */
	public double X;
	
	/**
	 * Y position
	 */
	public double Y;
	
	/**
	 * Z position
	 */
	public double Z;
	
	/**
	 * Default constructor. Sets all values to 0.
	 */
	public Point3d()
	{
		X = 0;
		Y = 0;
		Z = 0;
	}
	
	/**
	 * Constructor initializing values.
	 * @param x X value.
	 * @param y Y value.
	 * @param z Z value.
	 */
	public Point3d(double x, double y, double z)
	{
		X = x;
		Y = y;
		Z = z;	
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
		temp = Double.doubleToLongBits(Z);
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
		Point3d other = (Point3d) obj;
		if (Double.doubleToLongBits(X) != Double.doubleToLongBits(other.X))
			return false;
		if (Double.doubleToLongBits(Y) != Double.doubleToLongBits(other.Y))
			return false;
		if (Double.doubleToLongBits(Z) != Double.doubleToLongBits(other.Z))
			return false;
		return true;
	}
	
	
}
