package test.usfirst.frc.team4453.library;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;
import org.usfirst.frc.team4453.library.Vision;


@RunWith(Parameterized.class)
public class TestVision_FindBestTarget {

	private double[] width = new double[3];
	private double   tgt = 0.0;
	double [] nullWidths = {};


	private boolean doubleEquals(double a, double b) {
		return (Math.abs(a-b)<0.01);
	}
	
	public TestVision_FindBestTarget(double w0, double w1, double w2, double tgt) {
		this.width[0] = w0;
		this.width[1] = w1;
		this.width[2] = w2;
		this.tgt = tgt;
	}
	

	@Parameters
	public static Collection<Double[]> degrees() {
		return Arrays.asList(new Double[][] {
			{1.0,0.0,0.0, 0.0},
			{0.0,1.0,0.0, 1.0},
			{0.0,0.0,1.0, 2.0},
		});
	}

	@Before
	public void before() {
	}

	@After
	public void after() {
		Vision.setWidth(nullWidths);
	}

	@Test 
	public void test_FindBestTarget() {
		// verify that no target result in -99 (invalid) index
		// also, that no exceptions are thrown due to null pointer
		//Vision.setWidth(nullWidths);
		int output = Vision.findBestTarget();
		System.out.println("findBestTarget:   Target: "+output);
		assertTrue(doubleEquals(-99, output));

		// verify that each of three targets can be identified
		Vision.setWidth(width);
		output = Vision.findBestTarget();
		System.out.println("findBestTarget:  Widths: "+width[0]+","+width[1]+","+width[2]+"   Target: "+output);
		assertTrue(doubleEquals((int)tgt, output));
	}
}
