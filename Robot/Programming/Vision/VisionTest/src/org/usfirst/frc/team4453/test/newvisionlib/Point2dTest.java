package org.usfirst.frc.team4453.test.newvisionlib;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team4453.newvisionlib.Point2d;

public class Point2dTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDefaultConstructor() {
		Point2d test = new Point2d();
		assertNotNull(test);
		assertEquals(0, test.X, 0);
		assertEquals(0, test.Y, 0);
	}
	
	@Test
	public void testConstructor()
	{
		Point2d test = new Point2d(1, 2);
		assertNotNull(test);
		assertEquals(1, test.X, 0);
		assertEquals(2, test.Y, 0);
	}
	
	@Test
	public void testEquals()
	{
		assertEquals(new Point2d(1, 2), new Point2d(1, 2));
		assertNotEquals(new Point2d(1, 2), new Point2d(1, 3));
	}
	
	

}
