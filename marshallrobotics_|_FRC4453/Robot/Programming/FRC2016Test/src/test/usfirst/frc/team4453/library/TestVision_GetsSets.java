package test.usfirst.frc.team4453.library;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.usfirst.frc.team4453.library.Vision;

public class TestVision_GetsSets {

	private int badIndex = -99;
	private int goodIndex = 0;
	private double badValue = -99.0;
	private double goodValue = 0.0;
	private double[] zeroArray = {goodValue};
	private double[] testArray = {badValue};
	
	@Before
	public void before() {
		Vision.setTgtIndex(badIndex);
		Vision.setHeight(testArray);
		Vision.setWidth(testArray);
		Vision.setArea(testArray);
		Vision.setCenterX(testArray);
		Vision.setCenterY(testArray);
		Vision.setAngle(badValue);
		Vision.setRatio(badValue);
	}

	@After
	public void after() {
		Vision.setTgtIndex(goodIndex);
		Vision.setHeight(zeroArray);
		Vision.setWidth(zeroArray);
		Vision.setArea(zeroArray);
		Vision.setCenterX(zeroArray);
		Vision.setCenterY(zeroArray);
		Vision.setAngle(goodValue);
		Vision.setRatio(goodValue);
	}
	
	@Test
	public void test_GetTgtHeight() {
		assertTrue(Vision.getTgtHeight() == badValue);
		Vision.setTgtIndex(goodIndex);
		Vision.setHeight(zeroArray);
		assertTrue(Vision.getTgtHeight() == goodValue);
	}
	
	@Test
	public void test_GetTgtWidth() {
		assertTrue(Vision.getTgtWidth() == badValue);
		Vision.setTgtIndex(goodIndex);
		Vision.setWidth(zeroArray);
		assertTrue(Vision.getTgtWidth() == goodValue);
	}
	
	@Test
	public void test_GetTgtPos() {
		double[] output = Vision.getTgtPos();
		System.out.println("Expected: "+badValue+"   Actual: "+output[0]+","+output[1]);
		assertTrue( output[0] == badValue);
		assertTrue( output[1] == badValue);
		Vision.setTgtIndex(goodIndex);
		Vision.setCenterX(zeroArray);
		Vision.setCenterY(zeroArray);
		output = Vision.getTgtPos();
		System.out.println("Expected: "+goodValue+"   Actual: "+output[0]+","+output[1]);
		assertTrue(output[0] == goodValue);
		assertTrue(output[1] == goodValue);
	}

	@Test
	public void test_GetAngle() {
		assertTrue(Vision.getAngle() == badValue);
		Vision.setAngle(goodValue);
		assertTrue(Vision.getAngle() == goodValue);
	}
	
	@Test
	public void test_GetRatio() {
		assertTrue(Vision.getRatio() == badValue);
		Vision.setRatio(goodValue);
		assertTrue(Vision.getRatio() == goodValue);
	}
}
