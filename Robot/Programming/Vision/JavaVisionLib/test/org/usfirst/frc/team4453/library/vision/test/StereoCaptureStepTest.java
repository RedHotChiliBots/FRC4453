package org.usfirst.frc.team4453.library.vision.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import org.usfirst.frc.team4453.library.vision.Data;
import org.usfirst.frc.team4453.library.vision.Pipeline;
import org.usfirst.frc.team4453.library.vision.PipelineStep;
import org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv.StereoCaptureStep;

public class StereoCaptureStepTest {
	static
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	private class TestStep extends PipelineStep
	{
		public Mat outL, outR;
		public TestStep(Pipeline p) {
			super(p);
		}

		@Override
		protected boolean execute(Data in) {
			outL = (Mat) in.get("frameL");
			outR = (Mat) in.get("frameR");
			return true;
		}
	}
	
	private class TestStep2 extends PipelineStep
	{
		public Mat outL, outR;
		public TestStep2(Pipeline p) {
			super(p);
		}

		@Override
		protected boolean execute(Data in) {
			outL = (Mat) in.get("outA");
			outR = (Mat) in.get("outB");
			return true;
		}
	}
	
	@Test
	public void testVideoCaptureStepPipelineVideoCapture() throws IOException, InterruptedException {
		File tmpfile1 = File.createTempFile("vlt", ".avi");
		tmpfile1.deleteOnExit();
		File tmpfile2 = File.createTempFile("vlt", ".avi");
		tmpfile2.deleteOnExit();
		
		VideoWriter writerl = new VideoWriter();
		writerl.open(tmpfile1.getAbsolutePath(), VideoWriter.fourcc('M','J','P','G'), 10, new Size(50,100), true);
		VideoWriter writerr = new VideoWriter();
		writerr.open(tmpfile2.getAbsolutePath(), VideoWriter.fourcc('M','J','P','G'), 10, new Size(100,50), true);
		
		
		Mat ml = Mat.zeros(new Size(50,100), CvType.CV_8UC3);
		Mat mr = Mat.zeros(new Size(100,50), CvType.CV_8UC3);
		for(int x = 1; x <= 50; x++)
		{
			for(int y = 1; y <= 100; y++)
			{
				byte[] data = new byte[3];
				data[0] = (byte) (Math.random() * 255);
				data[1] = (byte) (Math.random() * 255);
				data[2] = (byte) (Math.random() * 255);
				ml.put(x, y, data);
			}
		}
		for(int x = 1; x <= 100; x++)
		{
			for(int y = 1; y <= 50; y++)
			{
				byte[] data = new byte[3];
				data[0] = (byte) (Math.random() * 255);
				data[1] = (byte) (Math.random() * 255);
				data[2] = (byte) (Math.random() * 255);
				mr.put(x, y, data);
			}
		}
		
		for(int i = 0; i < 100; i++)
		{
			writerl.write(ml);
			writerr.write(mr);
		}
		writerl.release();
		writerr.release();

		VideoCapture capl = new VideoCapture();
		capl.open(tmpfile1.getAbsolutePath());
		VideoCapture capr = new VideoCapture();
		capr.open(tmpfile2.getAbsolutePath());
		
		Pipeline p = new Pipeline();
		StereoCaptureStep step = new StereoCaptureStep(p, capl, capr);
		TestStep step1 = new TestStep(p);
		
		p.add(step);
		p.add(step1);
		
		p.start();
		Thread.sleep(100);
		while(step1.outL == null || step1.outR == null)
		{
			Thread.sleep(100);
		}
		p.stop();
		
		Mat oL = step1.outL;
		Mat oR = step1.outR;
		
		assertEquals(ml.size().height, oL.size().height, 1);
		assertEquals(ml.size().width, oL.size().width, 1);
		assertEquals(mr.size().height, oR.size().height, 1);
		assertEquals(mr.size().width, oR.size().width, 1);
		
		tmpfile1.delete();
	}

	@Test
	public void testVideoCaptureStepPipelineVideoCaptureString() throws InterruptedException, IOException {
		File tmpfile1 = File.createTempFile("vlt", ".avi");
		tmpfile1.deleteOnExit();
		File tmpfile2 = File.createTempFile("vlt", ".avi");
		tmpfile2.deleteOnExit();
		
		VideoWriter writerl = new VideoWriter();
		writerl.open(tmpfile1.getAbsolutePath(), VideoWriter.fourcc('M','J','P','G'), 10, new Size(50,100), true);
		VideoWriter writerr = new VideoWriter();
		writerr.open(tmpfile2.getAbsolutePath(), VideoWriter.fourcc('M','J','P','G'), 10, new Size(100,50), true);
		
		
		Mat ml = Mat.zeros(new Size(50,100), CvType.CV_8UC3);
		Mat mr = Mat.zeros(new Size(100,50), CvType.CV_8UC3);
		for(int x = 1; x <= 50; x++)
		{
			for(int y = 1; y <= 100; y++)
			{
				byte[] data = new byte[3];
				data[0] = (byte) (Math.random() * 255);
				data[1] = (byte) (Math.random() * 255);
				data[2] = (byte) (Math.random() * 255);
				ml.put(x, y, data);
			}
		}
		for(int x = 1; x <= 100; x++)
		{
			for(int y = 1; y <= 50; y++)
			{
				byte[] data = new byte[3];
				data[0] = (byte) (Math.random() * 255);
				data[1] = (byte) (Math.random() * 255);
				data[2] = (byte) (Math.random() * 255);
				mr.put(x, y, data);
			}
		}
		
		for(int i = 0; i < 100; i++)
		{
			writerl.write(ml);
			writerr.write(mr);
		}
		writerl.release();
		writerr.release();

		VideoCapture capl = new VideoCapture();
		capl.open(tmpfile1.getAbsolutePath());
		VideoCapture capr = new VideoCapture();
		capr.open(tmpfile2.getAbsolutePath());
		
		Pipeline p = new Pipeline();
		StereoCaptureStep step = new StereoCaptureStep(p, capl, capr, "outA", "outB");
		TestStep2 step1 = new TestStep2(p);
		
		p.add(step);
		p.add(step1);
		
		p.start();
		Thread.sleep(100);
		while(step1.outL == null || step1.outR == null)
		{
			Thread.sleep(100);
		}
		p.stop();
		
		Mat oL = step1.outL;
		Mat oR = step1.outR;
		
		assertEquals(ml.size().height, oL.size().height, 1);
		assertEquals(ml.size().width, oL.size().width, 1);
		assertEquals(mr.size().height, oR.size().height, 1);
		assertEquals(mr.size().width, oR.size().width, 1);
		
		tmpfile1.delete();
	}
}
