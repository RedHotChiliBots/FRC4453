package org.usfirst.frc.team4453.test.newvisionlib;

import org.usfirst.frc.team4453.newvisionlib.ColorTargetPoint;
import org.usfirst.frc.team4453.newvisionlib.Point2d;
import org.usfirst.frc.team4453.newvisionlib.Target;
import org.usfirst.frc.team4453.newvisionlib.TargetType;

import junit.framework.TestCase;

public class TargetTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddPoint() {
		Target test = new Target();
		assertEquals(0, test.colorPoints.size());
		test.addPoint(new ColorTargetPoint());
		assertEquals(1, test.colorPoints.size());
	}

	public void testMakeCOLOR_DOT() {
		Target test = Target.makeCOLOR_DOT();
		assertEquals(TargetType.COLOR_DOTS, test.type);
	}

	public void testMakeIMAGE() {
		Target test = Target.makeIMAGE("TestString", new Point2d(5, 25));
		assertEquals(TargetType.IMAGE, test.type);
		assertEquals("TestString", test.referenceImageFilename);
		assertEquals(new Point2d(5, 25).X, test.referenceImageSize.X);
		assertEquals(new Point2d(5, 25).Y, test.referenceImageSize.Y);
	}

}
