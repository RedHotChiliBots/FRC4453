package org.usfirst.frc.team4453.library;

public class Kalman {
	  double q; //process noise covariance
	  double r; //measurement noise covariance
	  double x; //value
	  double p; //estimation error covariance
	  double k; //kalman gain
	  
	  
	  public Kalman() {
		    this.q = 0.125;
		    this.r = 5.0;
		    this.p = 0.0;
		    this.x = 0.0;	  
	  }
	  
	  public Kalman(double q, double r, double p, double intial_value) {
	    this.q = q;
	    this.r = r;
	    this.p = p;
	    this.x = intial_value;
	  }
	  	 
	  public double Update(double measurement) {
	    //prediction update
	    //omit x = x
	    p = p + q;

	    //measurement update
	    k = p / (p + r);
	    x = x + k * (measurement - x);
	    p = (1 - k) * p;
	    
	    return x;
	  }
	  
	  public double getResult() {
		  return x;
	  }
}
