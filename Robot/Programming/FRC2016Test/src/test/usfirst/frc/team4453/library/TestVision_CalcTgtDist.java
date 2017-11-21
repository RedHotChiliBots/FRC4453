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
public class TestVision_CalcTgtDist {

	
	private double heading = 0.0;
	private double pitch   = 0.0;
	private double[] tgtX = new double[1];
	private double[] tgtY = new double[1];
	private double[] distX = new double[1];
	private double[] distY = new double[1];

	private boolean doubleEquals(double a, double b) {
		return (Math.abs(a-b)<0.01);
	}
	
	public TestVision_CalcTgtDist(double heading, double pitch, double tgtX, double tgtY, double distX, double distY) {
		this.heading = heading;
		this.pitch = pitch;
		this.tgtX[0] = tgtX;
		this.tgtY[0] = tgtY;
		this.distX[0] = distX;
		this.distY[0] = distY;
	}
	

	@Parameters
	public static Collection<Double[]> degrees() {
		return Arrays.asList(new Double[][] {
			{ 0.0, 0.0, 0.0, 0.0, 6.0, 3.85},
			{ 0.0, 0.0, 10.0, 6.0, 6.0, 3.85},
			{ 0.0, 0.0, 20.0, 12.0, 6.0, 3.85},
			{ 0.0, 0.0, 30.0, 18.0, 6.0, 3.85},
			{ 0.0, 0.0, 40.0, 24.0, 6.0, 3.85},
			{ 0.0, 0.0, 50.0, 30.0, 6.0, 3.85},
			{ 0.0, 0.0, 60.0, 36.0, 6.0, 3.85},
			{ 0.0, 0.0, 70.0, 42.0, 6.0, 3.85},
			{ 0.0, 0.0, 80.0, 48.0, 6.0, 3.85},
			{ 0.0, 0.0, 90.0, 54.0, 6.0, 3.85},
			{ 0.0, 0.0, 100.0, 60.0, 6.0, 3.85},
		});
	}

	@Before
	public void before() {
	}

	@Test 
	public void test_CalcTgtDist() {
		Vision.setWidth(tgtX);
		Vision.setHeight(tgtY);
		Vision.findBestTarget();
//		double[] output = vision.calcTgtDist(Math.toRadians(heading), Math.toRadians(pitch));
		double[] output = Vision.calcTgtDist();
		System.out.println("calcTgtDist:  Tgt: "+Vision.getTgtWidth()+","+Vision.getTgtHeight()+"   Dist: "+output[0]+","+output[1]);
		assertTrue(doubleEquals(distX[0], output[0]));
		assertTrue(doubleEquals(distY[0], output[1]));
	}
}
