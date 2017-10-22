package test.usfirst.frc.team4453.library;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.usfirst.frc.team4453.library.Tilt;

@RunWith(Parameterized.class)
public class TestTilt_CalcAngle {

	private double side;
	private double expected;

	private boolean doubleEquals(double a, double b) {
		return (Math.abs(a-b)<0.0000001);
	}
	
	public TestTilt_CalcAngle(double side, double expected) {
		this.side  = side;
		this.expected = expected;
	}
	
	@Parameters
	public static Collection<Double[]> tilt() {
		return Arrays.asList(new Double[][] {
			{ 0.0, -28.69141470 },
			{ 2.574181440, 0.0 },
			{ 5.143386940, 30.0 },
			{ 6.266090400, 45.0 },
			{ 7.247264520, 60.0 },
			{ 7.813190230, 70.0 },
			{ 8.067860730, 75.0 },
		});
	}

	@Test 
	public void test_CalcAngle() {
		double output = Tilt.calcAngle(side);
		System.out.println("calcAngle:  Side: "+side+"   Expected: "+expected+"   Actual: "+output);
		assertTrue(doubleEquals(expected, output));
	}
}
