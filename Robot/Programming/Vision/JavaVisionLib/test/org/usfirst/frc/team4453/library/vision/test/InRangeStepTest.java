package org.usfirst.frc.team4453.library.vision.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.usfirst.frc.team4453.library.vision.Data;
import org.usfirst.frc.team4453.library.vision.Pipeline;
import org.usfirst.frc.team4453.library.vision.PipelineStep;
import org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv.InRangeStep;

public class InRangeStepTest {
	static
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	private class TestStep1 extends PipelineStep
	{
		public Mat inframe;
		public TestStep1(Pipeline p) {
			super(p);
		}

		@Override
		protected boolean execute(Data in) {
			in.put("frame", inframe);
			return true;
		}
	}
	
	private class TestStep2 extends PipelineStep
	{
		public Mat out;
		public TestStep2(Pipeline p) {
			super(p);
		}

		@Override
		protected boolean execute(Data in) {
			out = (Mat) in.get("out");
			return true;
		}
	}
	
	@Test
	public void testInRangeStep() throws InterruptedException {
		Pipeline p = new Pipeline();
		TestStep1 step1 = new TestStep1(p);
		InRangeStep step = new InRangeStep(p, "frame", "out", new Scalar(0,0,0), new Scalar(100,100,100));
		TestStep2 step2 = new TestStep2(p);
		Mat m = Mat.zeros(100, 100, CvType.CV_8UC3);
		for(int x = 1; x <= 100; x++)
		{
			for(int y = 1; y <= 100; y++)
			{
				byte[] data = new byte[3];
				data[0] = (byte) (Math.random() * 255);
				data[1] = (byte) (Math.random() * 255);
				data[2] = (byte) (Math.random() * 255);
				m.put(x, y, data);
			}
		}
		
		step1.inframe = m;
		
		p.add(step1);
		p.add(step);
		p.add(step2);
		
		p.start();
		Thread.sleep(200);
		while(step2.out == null)
		{
			Thread.sleep(100);
		}
		p.stop();
		
		Mat o = step2.out;
		Mat e = new Mat();
		Core.inRange(m, new Scalar(0,0,0), new Scalar(100,100,100), e);
	
		for(int x = 1; x <= 100; x++)
		{
			for(int y = 1; y <= 100; y++)
			{
				byte[] edata = new byte[3];
				byte[] odata = new byte[3];
				e.get(x, y, edata);
				o.get(x, y, odata);
				assertEquals(edata[0], odata[0]);
				assertEquals(edata[1], odata[1]);
				assertEquals(edata[2], odata[2]);
			}
		}
	}

}
