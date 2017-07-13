package org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.usfirst.frc.team4453.library.vision.Data;
import org.usfirst.frc.team4453.library.vision.Pipeline;
import org.usfirst.frc.team4453.library.vision.PipelineStep;

/**
 * This PipelineStep runs the OpenCV inrange() function on the specified mat.
 * @author Conner Ebbinghaus
 *
 */
public class InRangeStep extends PipelineStep {
	/**
	 * Bounds for inrange.
	 */
	private Scalar lower, upper;
	
	/**
	 * Input and output datanames.
	 */
	private String nameIn, nameOut;
	
	/**
	 * Constructor
	 * @param p The Pipeline.
	 * @param ni The input image name.
	 * @param no The output image name.
	 * @param l The lower bound.
	 * @param u The upper bound.
	 */
	public InRangeStep(Pipeline p, String ni, String no ,Scalar l, Scalar u) {
		super(p);
		lower = l;
		upper = u;
		nameIn = ni;
		nameOut = no;
	}

	@Override
	protected boolean execute(Data in) {
		Mat frame = (Mat) in.get(nameIn);
		Mat out = new Mat();
		Core.inRange(frame, lower, upper, out);
		in.put(nameOut, out);
		return true;
	}

}
