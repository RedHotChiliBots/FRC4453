package org.usfirst.frc.team4453.test.library;

import static org.junit.Assert.*;

import org.junit.Test;
import org.usfirst.frc.team4453.library.Tilt;

public class TestTilt {

	@Test
	public void testCalcSide() {
		assertEquals("Test Case 1 Coarse", 5.613, Tilt.calcSide(39.02), 1);
		assertEquals("Test Case 2 Coarse", 2.292, Tilt.calcSide(15.97), 1);
	}

	@Test
	public void testCalcAngle() {
		assertEquals("Test Case 1 Coarse", 39.02, Tilt.calcAngle(5.613), 5);
		assertEquals("Test Case 2 Coarse", 15.97, Tilt.calcAngle(2.29), 5);
	}

	@Test
	public void testGetMPI() {
		assertEquals("MPI is constant.", Tilt.getMPI(), Tilt.getMPI() , 0.01);
		assertTrue("MPI is positive.", Tilt.getMPI() >= 0);
		assertNotEquals("MPI is nonzero.", Tilt.getMPI(), 0, 0.01);
		assertEquals("39.3701 inches == 1 meter", Tilt.getMPI() * 39.3701, 1, 0.1);
	}

	@Test
	public void testGetG() {
		assertEquals("G is constant", Tilt.getG(), Tilt.getG(), 0.01);
		assertEquals("G is close to 9.8", Tilt.getG(), 9.8, 0.1);
	}

}
