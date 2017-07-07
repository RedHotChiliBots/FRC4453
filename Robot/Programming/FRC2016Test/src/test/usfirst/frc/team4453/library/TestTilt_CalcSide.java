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
public class TestTilt_CalcSide {

	private double angle;
	private double expected;

	private boolean doubleEquals(double a, double b) {
		return (Math.abs(a-b)<0.0000001);
	}
	
	public TestTilt_CalcSide(double angle, double expected) {
		this.angle = angle;
		this.expected  = expected;
	}
	

	@Parameters
	public static Collection<Double[]> degrees() {
		return Arrays.asList(new Double[][] {
			{ -28.69141470, 0.0 },
			{ 0.0, 2.574181440 },
			{ 30.0, 5.143386940 },
			{ 45.0, 6.266090400 },
			{ 60.0, 7.247264520 },
			{ 70.0, 7.813190230 },
			{ 75.0, 8.067860730 },
			});
	}

	@Test 
	public void test_CalcSide() {
		double output = Tilt.calcSide(angle);
		System.out.println("calcSide:  Angle: "+angle+"   Expected: "+expected+"   Actual: "+output);
		assertTrue(doubleEquals(expected, output));
	}
}
