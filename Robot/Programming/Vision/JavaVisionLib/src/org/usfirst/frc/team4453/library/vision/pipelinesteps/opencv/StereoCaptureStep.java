package org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.usfirst.frc.team4453.library.vision.Data;
import org.usfirst.frc.team4453.library.vision.Pipeline;
import org.usfirst.frc.team4453.library.vision.PipelineStep;

/**
 * This PipelineStep reads frames from two OpenCV VideoCapture objects at the same time and adds them under the given names.
 * @author Conner Ebbinghaus
 *
 */
public class StereoCaptureStep extends PipelineStep {
	/**
	 * The two cameras frames are read from.
	 */
	private VideoCapture caml, camr;
	
	/**
	 * The names for exported frames.
	 */
	private String exportNameL, exportNameR;
	
	/**
	 * Constructor. Default data names are "frameL" and "frameR.
	 * @param p The pipeline.
	 * @param l The VideoCapture object to read from (left).
	 * @param r The VideoCapture object to read from (right).
	 */
	public StereoCaptureStep(Pipeline p, VideoCapture l, VideoCapture r) {
		super(p, 1);
		caml=l;
		camr=r;
		exportNameL="frameL";
		exportNameR="frameR";
	}
	
	/**
	 * Constructor.
	 * @param p The Pipeline.
	 * @param l The VideoCapture object to read from (left).
	 * @param r The VideoCapture object to read from (right).
	 * @param el Name to export frames under (left).
	 * @param er Name to export frames under (right).
	 */
	public StereoCaptureStep(Pipeline p, VideoCapture l, VideoCapture r, String el, String er) {
		super(p, 1);
		caml=l;
		camr=r;
		exportNameL=el;
		exportNameR=er;
	}

	@Override
	protected boolean execute(Data in) {
		Mat framel = new Mat();
		Mat framer = new Mat();
		
		caml.grab();
		camr.grab();
		caml.retrieve(framel);
		camr.retrieve(framer);
		
		in.put(exportNameL, framel);
		in.put(exportNameR, framer);
		
		return caml.isOpened() && camr.isOpened();
	}

}
