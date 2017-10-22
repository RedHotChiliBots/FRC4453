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
public class TestVision_GetAimPos {

	private double goodValue = 0.0;
	private double[] zeroArray = {goodValue};

	private double[] tgtX = new double[1];
	private double[] tgtY = new double[1];
	private double[] aimX = new double[1];
	private double[] aimY = new double[1];

	private final double FOV = 37.4;	// FOV in degrees
	private final double RESx = 320.0;	// Resolution X in pixels
	private final double RESy = 240.0;	// Resolution Y in pixels

	private final double TGTx = 1+(8/12);
	private final double TGTy = 1+(2/12);

	private boolean doubleEquals(double a, double b) {
		return (Math.abs(a-b)<0.0000000001);
	}
	
	public TestVision_GetAimPos(double tgtX, double tgtY, double aimX, double aimY) {
		this.tgtX[0] = tgtX;
		this.tgtY[0] = tgtY;
		this.aimX[0] = aimX;
		this.aimY[0] = aimY;
	}
	

	@Parameters
	public static Collection<Double[]> degrees() {
		return Arrays.asList(new Double[][] {
			{ 0.0, 0.0, -1.0, 1.0 },
			{ 320.0, 0.0, 1.0, 1.0 },
			{ 0.0, 240.0, -1.0, -1.0 },
			{ 320.0, 240.0, 1.0, -1.0 },
			{ 160.0, 120.0, 0.0, 0.0 },
			{ 80.0, 60.0, -0.5, 0.5 },
			{ 240.0, 60.0, 0.5, 0.5 },
			{ 80.0, 180.0, -0.5, -0.5 },
			{ 240.0, 180.0, 0.5, -0.5 },
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
	public void test_GetAimPos() {
		Vision.setCenterX(tgtX);
		Vision.setCenterY(tgtY);
		double[] aimPos = Vision.getAimPos();
		System.out.println("getAimPos:  tgtX: "+tgtX[0]+"  aimX: "+aimX[0]+"   resultX: "+aimPos[0]);
		System.out.println("getAimPos:  tgtY: "+tgtY[0]+"  aimY: "+aimY[0]+"   resultY: "+aimPos[1]);
		assertTrue(doubleEquals(aimX[0], aimPos[0]));
		assertTrue(doubleEquals(aimY[0], aimPos[1]));
	}
}
