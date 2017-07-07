package test.usfirst.frc.team4453.library;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;
import org.usfirst.frc.team4453.library.Vision;


@RunWith(Parameterized.class)
public class TestVision_GetTgtPos {

	private double[] tgtX = new double[1];
	private double[] tgtY = new double[1];
	private double goodValue = 0.0;
	private double[] zeroArray = {goodValue};

	
	private boolean doubleEquals(double a, double b) {
		return (Math.abs(a-b)<0.0000000001);
	}
	
	public TestVision_GetTgtPos(double tgtX, double tgtY) {
		this.tgtX[0] = tgtX;
		this.tgtY[0] = tgtY;
	}
	

	@Parameters
	public static Collection<Double[]> degrees() {
		return Arrays.asList(new Double[][] {
			{ 0.0, 0.0 },
			{ 320.0, 0.0 },
			{ 0.0, 240.0 },
			{ 320.0, 240.0 },
			{ 160.0, 120.0 },
			{ 80.0, 60.0 },
			{ 240.0, 60.0 },
			{ 80.0, 180.0 },
			{ 240.0, 180.0 },
		});
	}

	@Before
	public void before() {
	}
	
	@After
	public void after() {
		Vision.setCenterX(zeroArray);
		Vision.setCenterY(zeroArray);
	}
	
	@Test 
	public void test_GetTgtPos() {
		Vision.setCenterX(tgtX);
		Vision.setCenterY(tgtY);
		double[] tgtPos = Vision.getTgtPos();
		System.out.println("getTgtPos:  tgtX: "+tgtPos[0]+"   tgtY: "+tgtPos[1]);
		assertTrue(doubleEquals(tgtX[0], tgtPos[0]));
		assertTrue(doubleEquals(tgtY[0], tgtPos[1]));
	}
}
