package org.usfirst.frc.team4453.test.newvisionlib;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team4453.newvisionlib.Color;

public class ColorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDefaultConstructor() {
		Color test = new Color();
		assertNotNull(test);
		assertEquals(0.0, test.R, 0);
		assertEquals(0.0, test.G, 0);
		assertEquals(0.0, test.B, 0);
	}

	@Test
	public void testConstructor() {
		Color test = new Color(4, 7, 23);
		assertNotNull(test);
		assertEquals(4.0, test.R, 0);
		assertEquals(7.0, test.G, 0);
		assertEquals(23.0, test.B, 0);
	}
	
	@Test
	public void testEquals()
	{
		assertEquals(new Color(5, 6, 7), new Color(5, 6, 7));
		assertNotEquals(new Color(5, 6, 7), new Color(5, 8, 7));
	}

}
