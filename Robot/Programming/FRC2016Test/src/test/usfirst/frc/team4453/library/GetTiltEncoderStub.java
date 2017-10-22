package test.usfirst.frc.team4453.library;

import org.usfirst.frc.team4453.robot.subsystems.Shooter;

public class GetTiltEncoderStub extends Shooter {
	
	private static double dist;
	private static double angle;
	
	public GetTiltEncoderStub() {
	}
	
	public double tiltGetDist() {
		return dist;
	}

	public void tiltSetDist(double d) {
		dist = d;
	}

	public double tiltGetAngle() {
		return angle;
	}

	public void tiltSetAngle(double a) {
		angle = a;
	}
}
