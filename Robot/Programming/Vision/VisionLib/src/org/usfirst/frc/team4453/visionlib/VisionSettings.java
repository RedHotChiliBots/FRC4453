package org.usfirst.frc.team4453.visionlib;

public class VisionSettings {

	// Camera Info
	public double FOV = -99;
	public double FOVx = -99;
	public double FOVy = -99;

	// Physical Target Info
	public double TGTx = -99;
	public double TGTy = -99;
	public double TGTh = -99;
	
	public double tanFOV()
	{
		return 2*Math.tan(Math.toRadians(FOV/2.0));
	}
	
	public IDataSource dataSource;
}
