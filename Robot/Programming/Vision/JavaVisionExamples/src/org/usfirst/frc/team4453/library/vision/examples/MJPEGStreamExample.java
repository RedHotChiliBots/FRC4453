package org.usfirst.frc.team4453.library.vision.examples;

import java.io.IOException;

import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;
import org.usfirst.frc.team4453.library.vision.Pipeline;
import org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv.MJPEGServerStep;
import org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv.VideoCaptureStep;

/**
 * This is a simple test application.
 * It serves frames from the first recognized camera on http://localhost:8080
 * @author Conner Ebbinghaus
 *
 */
public class MJPEGStreamExample {

	public MJPEGStreamExample() {
	}

	public static void main(String[] args) {
		// Since we are using OpenCV PipelineSteps, we need to load OpenCV.
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		// Create the pipeline.
		Pipeline pipeline = new Pipeline();
		
		// Create an OpenCV VideoCapture, and open the first recognized camera.
		VideoCapture camera = new VideoCapture();
		camera.open(0);
		
		// Create a new VideoCaptureStep that reads frames from our camera and
		// makes them available to other steps under the name "frame".
		pipeline.add(new VideoCaptureStep(pipeline, camera, "frame"));
		
		// Create a new MJPEGServerStep that reads frames under the name "frame"
		// and serves them. This can fail if the server cannot be started, so we
		// need to handle it.
		try {
			pipeline.add(new MJPEGServerStep(pipeline, "frame"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		// Start our pipeline!
		pipeline.start();
		
		//Wait forever, the pipeline runs in other threads.
		while(true)
		{
			
		}
	}

}
