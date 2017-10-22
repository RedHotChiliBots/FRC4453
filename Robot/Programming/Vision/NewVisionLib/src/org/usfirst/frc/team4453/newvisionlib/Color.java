package org.usfirst.frc.team4453.newvisionlib;

/**
 * Class representing an RGB color value.
 * @author conner
 *
 */
public class Color implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6583499276600378064L;

	/**
	 * Red value.
	 */
	public double R = 0;

	/**
	 * Green value.
	 */
	public double G = 0;
	
	/** 
	 * Blue value.
	 */
	public double B = 0;
	
	/**
	 * Default constructor, sets to (0,0,0).
	 */
	public Color()
	{
		R = 0;
		G = 0;
		B = 0;
	}
	
	/**
	 * Constructor initializing the color's value.
	 * @param r Red value.
	 * @param g Green value.
	 * @param b Blue value.
	 */
	public Color(double r, double g, double b)
	{
		R = r;
		G = g;
		B = b;	
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(B);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(G);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(R);
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
		Color other = (Color) obj;
		if (Double.doubleToLongBits(B) != Double.doubleToLongBits(other.B))
			return false;
		if (Double.doubleToLongBits(G) != Double.doubleToLongBits(other.G))
			return false;
		if (Double.doubleToLongBits(R) != Double.doubleToLongBits(other.R))
			return false;
		return true;
	}
	
	
}
