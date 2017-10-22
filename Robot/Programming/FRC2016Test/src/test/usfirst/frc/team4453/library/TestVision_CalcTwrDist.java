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
public class TestVision_CalcTwrDist {

	private double[] tgtX = new double[1];
	private double[] tgtY = new double[1];
	private double   dist = 0.0;


	private boolean doubleEquals(double a, double b) {
		return (Math.abs(a-b)<0.01);
	}
	
	public TestVision_CalcTwrDist(double tgtX, double tgtY, double dist) {
		this.tgtX[0] = tgtX;
		this.tgtY[0] = tgtY;
		this.dist = dist;
	}
	

	@Parameters
	public static Collection<Double[]> degrees() {
		return Arrays.asList(new Double[][] {
			{ 40.0, 28.0, 5.625},
		});
	}

	@Before
	public void before() {
	}

	@Test 
	public void test_CalcTwrDist() {
		Vision.setWidth(tgtX);
		Vision.setHeight(tgtY);
		double output = Vision.calcTwrDist();
		System.out.println("calcTwrDist:  Tgt: "+tgtX[0]+","+tgtY[0]+"   Dist: "+output);
		assertTrue(doubleEquals(dist, output));
	}
}
