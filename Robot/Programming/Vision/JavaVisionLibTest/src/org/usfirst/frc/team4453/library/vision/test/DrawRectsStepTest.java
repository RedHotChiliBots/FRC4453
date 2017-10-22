package org.usfirst.frc.team4453.library.vision.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.usfirst.frc.team4453.library.vision.Data;
import org.usfirst.frc.team4453.library.vision.Pipeline;
import org.usfirst.frc.team4453.library.vision.PipelineStep;
import org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv.DrawRectsStep;
import org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv.InRangeStep;

public class DrawRectsStepTest {
	static
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	private class TestStep1 extends PipelineStep
	{
		public TestStep1(Pipeline p) {
			super(p);
		}

		@Override
		protected boolean execute(Data in) {
			in.put("frame", Mat.zeros(100, 100, CvType.CV_8U));
			MatOfRect r = new MatOfRect();
			Rect rs[] = {new Rect(5, 5, 10, 10)};
			r.fromArray(rs);
			in.put("rects", r);
			return true;
		}
	}
	
	private class TestStep2 extends PipelineStep
	{
		public Data data = null;
		public TestStep2(Pipeline p) {
			super(p);
		}

		@Override
		protected boolean execute(Data in) {
			data = in;
			return false;
		}
	}
	@Test
	public void testDrawRectsStep() throws InterruptedException {
		Pipeline p = new Pipeline();
		
		p.add(new TestStep1(p));
		p.add(new DrawRectsStep(p, new Scalar(255, 0, 0), "frame", "rects"));
		p.add(new InRangeStep(p, "frame", "binary", new Scalar(255, 0, 0), new Scalar(255, 0, 0)));
		TestStep2 out = new TestStep2(p);
		p.add(out);
		
		p.start();
		while(out.data == null)
		{
			Thread.sleep(10);
		}
		p.stop();
		
		assertNotNull(out.data.get("frame"));
		assertNotNull(out.data.get("rects"));
		Mat frame = (Mat) out.data.get("frame");
		
		assertEquals(new Size(100, 100), frame.size());
		
		boolean foundNonZero = false;
		for(int x = 0; x < 100 && !foundNonZero; x++)
		{
			for(int y = 0; y < 100 && !foundNonZero; y++)
			{
				for(int e = 0; e < frame.get(x, y).length && !foundNonZero; e++)
				{
					if(frame.get(x, y)[e] != 0)
					{
						foundNonZero = true;
					}
				}
			}
		}
		
		assertTrue(foundNonZero);
	}

}
