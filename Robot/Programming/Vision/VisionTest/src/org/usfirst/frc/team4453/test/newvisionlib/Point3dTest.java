package org.usfirst.frc.team4453.test.newvisionlib;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team4453.newvisionlib.Point3d;

public class Point3dTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDefaultConstructor() {
		Point3d test = new Point3d();
		assertNotNull(test);
		assertEquals(0, test.X, 0);
		assertEquals(0, test.Y, 0);
		assertEquals(0, test.Z, 0);
	}
	
	@Test
	public void testConstructor()
	{
		Point3d test = new Point3d(1, 2, 3);
		assertNotNull(test);
		assertEquals(1, test.X, 0);
		assertEquals(2, test.Y, 0);
		assertEquals(3, test.Z, 0);
	}
	
	@Test
	public void testEquals()
	{
		assertEquals(new Point3d(1, 2, 3), new Point3d(1, 2, 3));
		assertNotEquals(new Point3d(1, 2, 3), new Point3d(1, 3, 2));
	}
	
	

}
