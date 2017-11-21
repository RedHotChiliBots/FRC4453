package org.usfirst.frc.team4453.library.vision.examples;

import java.io.IOException;

import org.opencv.core.Core;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.usfirst.frc.team4453.library.vision.Pipeline;
import org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv.CascadeClassifierStep;
import org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv.DrawRectsStep;
import org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv.MJPEGServerStep;
import org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv.RescaleStep;
import org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv.VideoCaptureStep;

public class FaceDetectorExample {

	public static void main(String[] args) {
		// Since we are using OpenCV PipelineSteps, we need to load OpenCV.
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		// We create a new cascade classifier from an xml file. This one finds faces in an image.
		CascadeClassifier faceDetector = new CascadeClassifier("haarcascade_frontalface_default.xml");

		// We create a pipeline...
		Pipeline p = new Pipeline();

		// .. and add a camera too it.
		p.add(new VideoCaptureStep(p, new VideoCapture(0), "frame"));

		// We shrink the image to make processing faster.
		p.add(new RescaleStep(p, new Size(133, 100), "frame", "frame"));

		// We add our classifier.
		p.add(new CascadeClassifierStep(p, faceDetector, "frame", "rects"));

		// We draw the resulting matches.
		p.add(new DrawRectsStep(p, new Scalar(0, 0, 255), "frame", "rects"));

		// And we serve the results.
		try {
			p.add(new MJPEGServerStep(p, "frame"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		p.start();
		while(true){}
	}

}
