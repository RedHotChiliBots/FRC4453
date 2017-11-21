package org.usfirst.frc.team4453.gripvision;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;

public interface IPipeline {
	public void process(Mat frame);
	public ArrayList<MatOfPoint> output();
}
