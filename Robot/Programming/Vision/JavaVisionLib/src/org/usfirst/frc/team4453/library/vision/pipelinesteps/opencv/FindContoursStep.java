package org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team4453.library.vision.Data;
import org.usfirst.frc.team4453.library.vision.Pipeline;
import org.usfirst.frc.team4453.library.vision.PipelineStep;

public class FindContoursStep extends PipelineStep {
	private String imageName, contoursName, hierarchyName;
	private int Mode, Method;
	
	public FindContoursStep(Pipeline p, String i, String c, String h, int mode, int method) {
		super(p);
		imageName = i;
		contoursName = c;
		hierarchyName = h;
		Mode = mode;
		Method = method;
	}

	@Override
	protected boolean execute(Data in) {
		ArrayList<MatOfPoint> contours = new ArrayList<>();
		Mat h = new Mat();
		Imgproc.findContours((Mat) in.get(imageName), contours, h, Mode, Method);
		in.put(contoursName, contours);
		in.put(hierarchyName, h);
		return true;
	}

}
