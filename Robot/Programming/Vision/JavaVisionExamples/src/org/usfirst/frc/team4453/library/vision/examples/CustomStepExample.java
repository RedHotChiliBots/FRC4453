package org.usfirst.frc.team4453.library.vision.examples;

import java.io.IOException;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.usfirst.frc.team4453.library.vision.Data;
import org.usfirst.frc.team4453.library.vision.Pipeline;
import org.usfirst.frc.team4453.library.vision.PipelineStep;
import org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv.CascadeClassifierStep;
import org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv.DrawRectsStep;
import org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv.MJPEGServerStep;
import org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv.RescaleStep;
import org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv.VideoCaptureStep;
/**
 * Similar to FaceDetectorExample, but also shows how to use custom steps.
 * @author Conner Ebbinghaus
 *
 */
public class CustomStepExample {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		CascadeClassifier faceDetector = new CascadeClassifier("haarcascade_frontalface_default.xml");

		Pipeline p = new Pipeline();

		p.add(new VideoCaptureStep(p, new VideoCapture(0), "frame"));
		p.add(new RescaleStep(p, new Size(133, 100), "frame", "frame"));
		p.add(new CascadeClassifierStep(p, faceDetector, "frame", "rects"));
		p.add(new DrawRectsStep(p, new Scalar(0, 0, 255), "frame", "rects"));

		// Simple custom step that draws the rectangles' areas.
		p.add(new PipelineStep(p) {
			// All steps must implement execute(Data). If this returns false, the step will stop running.
			@Override
			protected boolean execute(Data in) {
				Mat frame = (Mat) in.get("frame"); // Get the frame
				MatOfRect rects = (MatOfRect) in.get("rects"); // Get the rectangles.
				for(Rect r : rects.toList()) // For every rectangle...
				{
					// Draw the area.
					Imgproc.putText(frame, new Double(r.area()).toString(), r.tl(), Core.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 0, 255));
				}
				in.put("frame", frame); // Put the frame.
				return true; // We want to continue running.
			}
		});
		
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

