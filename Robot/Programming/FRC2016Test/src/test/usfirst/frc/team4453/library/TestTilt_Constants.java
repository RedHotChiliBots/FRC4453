package test.usfirst.frc.team4453.library;

import static org.junit.Assert.*;

import org.junit.Test;
import org.usfirst.frc.team4453.library.Tilt;

public class TestTilt_Constants {

	@Test
	public void testMPI() {
		double n = Tilt.getMPI();
		System.out.println("getMPI: "+n);
		assertTrue("getMPI",n == (2.54/100));
	}

	@Test
	public void testVEL() {
		double n = Tilt.getVEL();
		System.out.println("getVEL: "+n);
		assertTrue("getVEL",n == 8.5972881);
	}

	@Test
	public void testG() {
		double n = Tilt.getG();
		System.out.println("getG: "+n);
		assertTrue("getG",n == 9.80665);
	}

	@Test
	public void testPIVOTx() {
		double n = Tilt.getPIVOTx();
		System.out.println("getPIVOTx: "+n);
		assertTrue("getPIVOTx",n == 7.63707590*Tilt.getMPI());
	}
	
	@Test
	public void testPIVOTy() {
		double n = Tilt.getPIVOTy();
		System.out.println("getPIVOTy: "+n);
		assertTrue("getPIVOTy",n == 5.20740728*Tilt.getMPI());
	}
	
	
	@Test
	public void testPIVOTz() {
		double n = Tilt.getPIVOTz();
		System.out.println("getPIVOTz: "+n);
		assertTrue("getPIVOTz",n == 8.97775344*Tilt.getMPI());
	}

	@Test
	public void testBALLx() {
		double n = Tilt.getBALLx();
		System.out.println("getBALLx: "+n);
		assertTrue("getBALLx",n == 9.37118570*Tilt.getMPI());
	}

	@Test
	public void testBALLy() {
		double n = Tilt.getBALLy();
		System.out.println("getBALLy: "+n);
		assertTrue("getBALLy",n == 5.625*Tilt.getMPI());
	}

	@Test
	public void testBALLh() {
		double n = Tilt.getBALLh();
		System.out.println("getBALLh: "+n);
		assertTrue("getBALLh",n == Math.sqrt(Math.pow(Tilt.getBALLx(), 2)+Math.pow(Tilt.getBALLy(), 2)));
	}

	@Test
	public void testBALLangle() {
		double n = Tilt.getBALLangle();
		System.out.println("getBALLangle: "+n);
		assertTrue("getBALLangle",n == Math.toDegrees(Math.atan(Tilt.getBALLy()/Tilt.getBALLx())));
	}
}
