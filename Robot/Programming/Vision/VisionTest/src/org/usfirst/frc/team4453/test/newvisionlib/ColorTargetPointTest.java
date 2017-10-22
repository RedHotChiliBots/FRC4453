package org.usfirst.frc.team4453.test.newvisionlib;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team4453.newvisionlib.Color;
import org.usfirst.frc.team4453.newvisionlib.ColorTargetPoint;
import org.usfirst.frc.team4453.newvisionlib.Point3d;

public class ColorTargetPointTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMakeForCOLOR_DOT() {
		ColorTargetPoint test = ColorTargetPoint.makeForCOLOR_DOT("Test", new Color(1, 2, 3), new Color(4, 5, 6), new Point3d(7, 8, 9));
		assertEquals("Test", test.id);
		assertEquals(new Point3d(7, 8, 9), test.mdlCoord);
		assertEquals(new Color(1, 2, 3), test.color);
		assertEquals(new Color(4, 5, 6), test.range);
	}

	@Test
	public void testMakeForIMAGE() {
		ColorTargetPoint test = ColorTargetPoint.makeForIMAGE("Test", new Point3d(1, 2, 3));
		assertEquals("Test", test.id);
		assertEquals(new Point3d(1, 2, 3), test.mdlCoord);
	}

	@Test
	public void testMakeForTranslate() {
		ColorTargetPoint test = ColorTargetPoint.makeForTranslate("Test", new Point3d(1, 2, 3));
		assertEquals("Test", test.id);
		assertEquals(new Point3d(1, 2, 3), test.mdlCoord);
	}

}
