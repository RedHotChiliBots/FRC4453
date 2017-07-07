package org.usfirst.frc.team4453.library;


/**
 *
 * @author Developer
 */
public class Kalman {
	
	private double q = 0.0;	// Process nNoise
	private double r = 0.0;	// Sensor Noise
	private double x = 0.0;	// Filtered Value 
	private double p = 0.0;	// Estimated Error
	private double p1 = 0.0;// Estimated Error (saved)
	private double k = 0.0;	// Kalman Gain

	/**
	 * Kalman constructor - set the constants and initialize
	 * other values to zero.
	 * 
	 * @param q double Process noise constant
	 * @param r double Sensor noise constant
	 */
	public Kalman(double q, double r) {
		this.q = q;
		this.r = r;
		this.p = 0.0;
		this.x = 0.0;
		
		//System.out.printf("Constructor: %6.3f\t%6.3f\t%6.3f\t%6.3f\n", this.q, this.r, this.p, this.x);
	}
	
	/**
	 * Kalman constructor - set the constants and filter values.
	 * 
	 * @param q double Process noise constant
	 * @param r double Sensor noise constant
	 * @param p	double Initial Estimated Error
	 * @param x double Initial Measurement Value
	 */
	public Kalman(double q, double r, double p, double x) {
		this.q = q;
		this.r = r;
		this.p = p;
		this.x = x;

		//System.out.printf("Constructor: %6.3f\t%6.3f\t%6.3f\t%6.3f\n", this.q, this.r, this.p, this.x);
	}
	
	/**
	 * updateKalman - Single dimension Kalman filter
	 * 
	 * @param measurement double New raw measurement
	 * @return double            New filtered value
	 * 
	 * @see http://interactive-matter.eu/blog/2009/12/18/filtering-sensor-data-with-a-kalman-filter/
	 */
	public double kalmanUpdate(double measurement) {
		//System.out.printf("Measurement: %6.3f\n", measurement);

		//prediction update
		p = p + q;
		p1 = p;
		//System.out.printf("Prediction p: %6.3f\n", p);

		//measurement update
		k = p / (p + r);
		x = x + k * (measurement - x);
		p = (1 - k) * p;
		//System.out.printf("Update k x p: %6.3f\t%6.3f\t%6.3f\n", k, x, p);

		return x;
	}
	
	/**
	 * 
	 * @param q double - Set Process Noise (constant)
	 */
	public void setQ( double q) {
		this.q = q;
	}
	
	/**
	 * 
	 * @param r double - Set Sensor Noise (constant)
	 */
	public void setR( double r) {
		this.r = r;
	}
	
	/**
	 * 
	 * @return double - Process Noise (constant)
	 */
	public double getQ() {
		return q;
	}
	
	/**
	 * 
	 * @return double - Sensor Noise (constant)
	 */
	public double getR() {
		return r;
	}
	
	/**
	 * 
	 * @return double - Estimated Error (saved)
	 */
	public double getP1() {
		return p1;
	}
	
	/**
	 * 
	 * @return double - Estimated Error
	 */
	public double getP() {
		return p;
	}

	/**
	 * 
	 * @return double - New filtered value
	 */
	public double getX() {
		return x;
	}

	/**
	 * 
	 * @return double - Kalman Gain
	 */
	public double getK() {
		return k;
	}
}