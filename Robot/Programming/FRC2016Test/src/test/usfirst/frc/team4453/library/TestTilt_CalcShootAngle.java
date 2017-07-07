package test.usfirst.frc.team4453.library;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.usfirst.frc.team4453.library.Tilt;
import com.kauailabs.navx.frc.AHRS;

@RunWith(Parameterized.class)
public class TestTilt_CalcShootAngle {
	
	private AHRS ahrs;
	private double tilt;
	private double pitch;
	private double expected;

	private boolean doubleEquals(double a, double b) {
		return (Math.abs(a-b)<0.0000001);
	}
	
	public TestTilt_CalcShootAngle(double tilt, double pitch, double expected) {
		this.tilt  = tilt;
		this.pitch  = pitch;
		this.expected = expected;
	}
	
	@Parameters
	public static Collection<Double[]> tilt() {
		return Arrays.asList(new Double[][] {
			{ 0.0, 0.0, 0.0 },
		});
	}

	@Before 
	public void before() {
		ahrs = new AHRS();
	}
	
	@Test 
	public void test_CalcShootAngle() {
		ahrs.setPitch(pitch);
		
		Tilt.setTestTiltAngle(tilt);
		
		double output = Tilt.calcShootAngle(true);
		System.out.println("calcAngle:  Tilt: "+tilt+"   Pitch: "+pitch+"   Expected: "+expected+"   Actual: "+output);
		assertTrue(doubleEquals(expected, output));
	}
}
