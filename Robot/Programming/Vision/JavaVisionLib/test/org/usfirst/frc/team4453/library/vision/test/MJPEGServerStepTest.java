package org.usfirst.frc.team4453.library.vision.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.usfirst.frc.team4453.library.vision.Data;
import org.usfirst.frc.team4453.library.vision.Pipeline;
import org.usfirst.frc.team4453.library.vision.PipelineStep;
import org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv.MJPEGServerStep;

public class MJPEGServerStepTest {
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
			out = (Mat) in.get("frame");
			return true;
		}
	}
	
	@Test
	public void testMJPEGServerStep() throws InterruptedException, IOException {
		Pipeline p = new Pipeline();
		TestStep1 step1 = new TestStep1(p);
		MJPEGServerStep step = new MJPEGServerStep(p, "frame");
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
		Thread.sleep(100);
		Mat c = new Mat();
		VideoCapture cap = new VideoCapture();
		cap.open("http://localhost:8080");
		Thread.sleep(100);
		cap.read(c);
		p.stop();
		
		assertEquals(m.size().height, c.size().height, 1);
		assertEquals(m.size().width, c.size().width, 1);
		
		Mat o = step2.out;
	
		for(int x = 1; x <= 100; x++)
		{
			for(int y = 1; y <= 100; y++)
			{
				byte[] mdata = new byte[3];
				byte[] odata = new byte[3];
				m.get(x, y, mdata);
				o.get(x, y, odata);
				assertEquals(mdata[0], odata[0]);
				assertEquals(mdata[1], odata[1]);
				assertEquals(mdata[2], odata[2]);
			}
		}
		step.stop();
	}
	
	@Test
	public void testMJPEGServerStepCustomBind() throws InterruptedException, IOException {
		Pipeline p = new Pipeline();
		TestStep1 step1 = new TestStep1(p);
		MJPEGServerStep step = new MJPEGServerStep(p, "frame", new InetSocketAddress("0.0.0.0", 8082));
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
		Thread.sleep(100);
		Mat c = new Mat();
		VideoCapture cap = new VideoCapture();
		cap.open("http://localhost:8082");
		Thread.sleep(100);
		cap.read(c);
		p.stop();
		
		assertEquals(m.size().height, c.size().height, 1);
		assertEquals(m.size().width, c.size().width, 1);
		
		Mat o = step2.out;
	
		for(int x = 1; x <= 100; x++)
		{
			for(int y = 1; y <= 100; y++)
			{
				byte[] mdata = new byte[3];
				byte[] odata = new byte[3];
				m.get(x, y, mdata);
				o.get(x, y, odata);
				assertEquals(mdata[0], odata[0]);
				assertEquals(mdata[1], odata[1]);
				assertEquals(mdata[2], odata[2]);
			}
		}
		step.stop();
	}

	@Test
	public void testGetSetQuality() throws IOException
	{
		Pipeline p = new Pipeline();
		MJPEGServerStep step = new MJPEGServerStep(p, "");
		int val = (int) (Math.random()*100);
		step.setQuality(val);
		assertEquals(val, step.getQuality());
		step.stop();
	}
}
