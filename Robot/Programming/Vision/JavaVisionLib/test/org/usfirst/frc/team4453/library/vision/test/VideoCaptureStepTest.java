package org.usfirst.frc.team4453.library.vision.test;

import static org.junit.Assert.*;

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
import org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv.VideoCaptureStep;

public class VideoCaptureStepTest {
	static
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	private class TestStep extends PipelineStep
	{
		public Mat out;
		public TestStep(Pipeline p) {
			super(p);
		}

		@Override
		protected boolean execute(Data in) {
			out = (Mat) in.get("frame");
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
	public void testVideoCaptureStepPipelineVideoCapture() throws IOException, InterruptedException {
		File tmpfile = File.createTempFile("vlt", ".avi");
		tmpfile.deleteOnExit();
		VideoWriter writer = new VideoWriter();
		writer.open(tmpfile.getAbsolutePath(), VideoWriter.fourcc('M','J','P','G'), 10, new Size(100,100), true);
		
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
		for(int i = 0; i < 100; i++)
		{
			writer.write(m);
		}
		writer.release();
		
		VideoCapture cap = new VideoCapture();
		cap.open(tmpfile.getAbsolutePath());
		
		Pipeline p = new Pipeline();
		VideoCaptureStep step = new VideoCaptureStep(p, cap);
		TestStep step1 = new TestStep(p);
		
		p.add(step);
		p.add(step1);
		
		p.start();
		Thread.sleep(100);
		while(step1.out == null)
		{
			Thread.sleep(100);
		}
		p.stop();
		
		Mat o = step1.out;
		
		assertEquals(m.size().height, o.size().height, 1);
		assertEquals(m.size().width, o.size().width, 1);
		
		tmpfile.delete();
	}

	@Test
	public void testVideoCaptureStepPipelineVideoCaptureString() throws InterruptedException, IOException {
		File tmpfile = File.createTempFile("vlt", ".avi");
		tmpfile.deleteOnExit();
		VideoWriter writer = new VideoWriter();
		writer.open(tmpfile.getAbsolutePath(), VideoWriter.fourcc('M','J','P','G'), 10, new Size(100,100), true);
		
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
		
		for(int i = 0; i < 100; i++)
		{
			writer.write(m);
		}
		writer.release();
		
		VideoCapture cap = new VideoCapture();
		cap.open(tmpfile.getAbsolutePath());
		
		Pipeline p = new Pipeline();
		VideoCaptureStep step = new VideoCaptureStep(p, cap, "out");
		TestStep2 step1 = new TestStep2(p);
		
		p.add(step);
		p.add(step1);
		
		p.start();
		Thread.sleep(100);
		while(step1.out == null)
		{
			Thread.sleep(100);
		}
		p.stop();
		
		Mat o = step1.out;
		
		assertEquals(m.size().height, o.size().height, 1);
		assertEquals(m.size().width, o.size().width, 1);
		
		tmpfile.delete();
	}

}
