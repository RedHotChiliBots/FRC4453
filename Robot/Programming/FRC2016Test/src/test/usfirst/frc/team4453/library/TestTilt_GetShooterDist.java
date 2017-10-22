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

@RunWith(Parameterized.class)
public class TestTilt_GetShooterDist {
	
	private double tilt;
	private double expected;

	private boolean doubleEquals(double a, double b) {
		return (Math.abs(a-b)<0.0000001);
	}
	
	public TestTilt_GetShooterDist(double tilt, double expected) {
		this.tilt  = tilt;
		this.expected = expected;
	}
	
	@Parameters
	public static Collection<Double[]> tilt() {
		return Arrays.asList(new Double[][] {
			{ 0.0, 0.0 },
			{ 30.0, 0.0 },
			{ 45.0, 0.0 },
			{ 60.0, 0.0 },
			{ 90.0, 0.0 },
			{ 0.0-34.34609740006151, 0.0 },
			{ 30.0-34.34609740006151, 0.0 },
			{ 45.0-34.34609740006151, 0.0 },
			{ 60.0-34.34609740006151, 0.0 },
			{ 90.0-34.34609740006151, 0.0 },
		});
	}

	@Before 
	public void before() {
	}
	
	@Test 
	public void test_GetShooterDist() {

		Tilt.setTestTiltAngle(tilt);
		
		double output = Tilt.getShooterDist(true);
		System.out.println("getShooterDist:  Tilt: "+tilt+"   Expected: "+expected+"   Actual: "+output);
		assertTrue(doubleEquals(expected, output));
	}
}