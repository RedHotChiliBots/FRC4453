package org.usfirst.frc.team4453.visionlib;

public class Target {
	public double height = 0;
	public double width = 0;
	public double centerX = 0;
	public double centerY = 0;
	
	boolean valid = false;
	
	public Target() {
		height = 0;
		width = 0;
		centerX = 0;
		centerY = 0;
		valid = false;
	}

	public Target(double h, double w, double cx, double cy) {
		height = h;
		width = w;
		centerX = cx;
		centerY = cy;
		valid = true;
	}

}
