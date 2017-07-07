package test.usfirst.frc.team4453.library;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;
import org.usfirst.frc.team4453.library.Vision;


@RunWith(Parameterized.class)
public class TestVision_CalcAngle {

	private double width;
	private double angle;

	private final double FOV = 37.4;	// FOV in degrees
	private final double RESx = 320.0;	// Resolution X in pixels
	private final double RESy = 240.0;	// Resolution Y in pixels


	private boolean doubleEquals(double a, double b) {
		return (Math.abs(a-b)<0.0000000001);
	}
	
	public TestVision_CalcAngle(double width, double angle) {
		this.width = width;
		this.angle = angle;
	}
	

	@Parameters
	public static Collection<Double[]> degrees() {
		return Arrays.asList(new Double[][] {
			// 	angle = (FOV * getWidth()) / RESx
			{ 0.0, 0.0 },
		});
	}

	@Before
	public void before() {
	}

	@Test 
	public void test_CalcAngle() {
		double[] aWidth = {width};
		Vision.setWidth(aWidth);
		double output = Vision.calcAngle();
		System.out.println("calcAngle:  Width: "+width+"   Angle: "+angle);
		assertTrue(doubleEquals(angle, output));
	}
}
