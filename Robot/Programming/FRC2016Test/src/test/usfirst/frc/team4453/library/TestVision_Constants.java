package test.usfirst.frc.team4453.library;

import static org.junit.Assert.*;

import org.junit.Test;
import org.usfirst.frc.team4453.library.Tilt;
import org.usfirst.frc.team4453.library.Vision;

public class TestVision_Constants {

	@Test
	public void testMPI() {
		double n = Vision.getMPI();
		System.out.println("getMPI: "+n);
		assertTrue("getMPI",n == (2.54/100));
	}

	@Test
	public void testFOV() {
		double n = Vision.getFOV();
		System.out.println("getFOV: "+n);
		assertTrue("getFOV",n == 37.4);
	}

	@Test
	public void testFOVx() {
		double n = Vision.getFOVx();
		System.out.println("getFOVx: "+n);
		assertTrue("getFOVx",n == 320.0);
	}

	@Test
	public void testFOVy() {
		double n = Vision.getFOVy();
		System.out.println("getFOVy: "+n);
		assertTrue("getFOVy",n == 240.0);
	}

	@Test
	public void testTGTx() {
		double n = Vision.getTGTx();
		System.out.println("getTGTx: "+n);
		assertTrue("getTGTx",n == 20.0*Vision.getMPI());
	}

	@Test
	public void testTGTy() {
		double n = Vision.getTGTy();
		System.out.println("getTGTy: "+n);
		assertTrue("getTGTy",n == 12.0*Vision.getMPI());
	}

	@Test
	public void testCAMh() {
		double n = Vision.getCAMh();
		System.out.println("getCAMh: "+n);
		assertTrue("getCAMh",n == 14.5*Vision.getMPI());
	}

	@Test
	public void testTWRh() {
		double n = Vision.getTWRh();
		System.out.println("getTWRh: "+n);
		assertTrue("getTWRh",n == 89.25*Vision.getMPI());
	}

	@Test
	public void testTanFOV() {
		double n = Vision.getTanFOV();
		System.out.println("getTanFOV: "+n);
		assertTrue("getTanFOV",n == 2*Math.tan(Math.toRadians(Vision.getFOV()/2.0)));
	}

	public void testTGTh() {
		double n = Vision.getTGTh();
		System.out.println("getTGTh: "+n);
		assertTrue("getTGTh",n == Vision.getTWRh() - Vision.getCAMh());
	}
}
