package org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.usfirst.frc.team4453.library.vision.Data;
import org.usfirst.frc.team4453.library.vision.Pipeline;
import org.usfirst.frc.team4453.library.vision.PipelineStep;

/**
 * This PipelineStep reads frames from an OpenCV VideoCapture object and adds them under the given name.
 * @author Conner Ebbinghaus
 *
 */
public class VideoCaptureStep extends PipelineStep {
	/**
	 * The VideoCapture frames are grabbed from.
	 */
	private VideoCapture cam;
	
	/**
	 * The name frames are saved under.
	 */
	private String exportName;
	
	/**
	 * Constructor. Default data name is "frame".
	 * @param p The pipeline.
	 * @param c The VideoCapture object to read from.
	 */
	public VideoCaptureStep(Pipeline p, VideoCapture c) {
		super(p);
		cam=c;
		exportName="frame";
	}
	
	/**
	 * Constructor
	 * @param p The pipeline.
	 * @param c The VideoCapture object to read from.
	 * @param e Name to export frames under.
	 */
	public VideoCaptureStep(Pipeline p, VideoCapture c, String e) {
		super(p, 1);
		cam=c;
		exportName=e;
	}

	@Override
	protected boolean execute(Data in) {
		Mat frame = new Mat();
		cam.read(frame);
		in.put(exportName, frame);
		return cam.isOpened();
	}

}
