package org.usfirst.frc.team4453.library.vision.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.usfirst.frc.team4453.library.vision.Data;
import org.usfirst.frc.team4453.library.vision.Pipeline;
import org.usfirst.frc.team4453.library.vision.PipelineStep;
import org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv.RescaleStep;

public class RescaleStepTest {
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
	public void testRescaleStep() throws InterruptedException {
		Pipeline p = new Pipeline();
		
		p.add(new TestStep1(p));
		p.add(new RescaleStep(p, new Size(50, 50), "frame", "out"));
		TestStep2 out = new TestStep2(p);
		p.add(out);
		
		p.start();
		while(out.data == null)
		{
			Thread.sleep(10);
		}
		p.stop();
		assertNotNull(out.data.get("frame"));
		assertNotNull(out.data.get("out"));
		Mat frame = (Mat) out.data.get("frame");
		Mat frameOut = (Mat) out.data.get("out");
		assertEquals(new Size(100,100), frame.size());
		assertEquals(new Size(50,50), frameOut.size());
	}

}
