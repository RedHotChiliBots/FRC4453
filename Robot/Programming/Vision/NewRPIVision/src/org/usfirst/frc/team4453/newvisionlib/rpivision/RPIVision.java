package org.usfirst.frc.team4453.newvisionlib.rpivision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.DMatch;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.MatOfPoint3f;
import org.opencv.core.Point;
import org.opencv.core.Point3;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;
import org.opencv.videoio.VideoCapture;
import org.usfirst.frc.team4453.newvisionlib.IDataSource;
import org.usfirst.frc.team4453.newvisionlib.TargetType;
import org.usfirst.frc.team4453.newvisionlib.NetworkTablesDataSource.NetworkTablesDataSource;


public class RPIVision {
	static Size patternSize = new Size(4, 4); // Number of interior corners on the calibration checkerboard. Or, just (H-1, W-1).
	static double squareSize = 0.75; // Real world size of a square. Unit does not matter, but it has to be consistant within the code.
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Load the native library for OpenCV.
		
		IDataSource dataSource = new NetworkTablesDataSource("roboRIO-4453-FRC.local", "/Vision"); // Initalize the DataSource
		
		dataSource.setSetting("status", "WAITFORROBOT"); // Update satatus
		while(!dataSource.push())
		{
			System.out.println("Waiting for dataSource...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		System.out.println("Waiting for Robot...");
		while(!dataSource.getSetting("robotStatus").equals("READY")) // Wait for the robot to be ready.
		{
			dataSource.pull();
			Thread.yield();
		}
		
		// Get some settings from the robot.
		patternSize.width = Double.parseDouble(dataSource.getSetting("calibPatternSizeX"));
		patternSize.height = Double.parseDouble(dataSource.getSetting("calibPatternSizeY"));
		
		squareSize = Double.parseDouble(dataSource.getSetting("calibSquareSize"));
		
		dataSource.setSetting("status", "INITALIZING");
		dataSource.push();
		System.out.println("Initalizing...");
		boolean keepRunning = true; // Do we need to keep running? Always yes, for now.
		boolean isCalibrated = false; // Are we calibrated yet?
		
		Mat frame = new Mat(); // Where we store the frame from the camera.
		MatOfPoint2f corners = new MatOfPoint2f(); // Storage for the calibration stuff.
		Mat cameraMatrix = new Mat(); // The calibration coefficients. Has magical numbers for calibration.
		MatOfDouble distCoeffs = new MatOfDouble(); // More magical calibration numbers I don't understand.
		List<Mat> rvecs = new ArrayList<Mat>(); // Still more calibration numbers.
		List<Mat> tvecs = new ArrayList<Mat>(); // Same as above.
		
		dataSource.setSetting("status", "PREPROCESSING");
		dataSource.push();
		System.out.println("Preprocessing...");
		// Make the Macher, Detector and Extractor for IMAGE based processing.
		DescriptorMatcher FLANN = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
		FeatureDetector SIFTD = FeatureDetector.create(FeatureDetector.SIFT);
		DescriptorExtractor SIFTE = DescriptorExtractor.create(DescriptorExtractor.SIFT);
		
		// Storage for preprocessed data.
		HashMap<Integer, MatOfKeyPoint> siftKeypoints = new HashMap<Integer, MatOfKeyPoint>();
		HashMap<Integer, Mat> siftDescriptors = new HashMap<Integer, Mat>();
		HashMap<Integer, Mat> referenceImages = new HashMap<Integer, Mat>();
		
		// Preprocess the targets.
		for(int i = 0; i < dataSource.numOfTargets(); i++)
		{
			if(dataSource.getData(i).type == TargetType.COLOR_DOTS)
			{
				// Nothing has to be done.
			}
			else if(dataSource.getData(i).type == TargetType.IMAGE)
			{
				// Find features in reference images.
				Mat referenceImage = Imgcodecs.imread(dataSource.getData(i).referenceImageFilename);
				referenceImages.put(i, referenceImage);
				
				MatOfKeyPoint keypoints = new MatOfKeyPoint();
				SIFTD.detect(referenceImage, keypoints);
				siftKeypoints.put(i, keypoints);
				
				//Make descriptors from features.
				Mat descriptors = new Mat();
				SIFTE.compute(referenceImage, keypoints, descriptors);
				siftDescriptors.put(i, descriptors);
			}
			else
			{
				// INVALID? Or something else we don't know about?
				System.out.println("Warning: A target is not valid. Removing it...");
				dataSource.removeTarget(i);
				i--;
			}
		}
		
		dataSource.setSetting("status", "OPENING CAMERA");
		dataSource.push();
		System.out.println("Opening Camera...");
		VideoCapture videoCapture = new VideoCapture(); // Gets us image from camera.
		
		while(!videoCapture.isOpened())
		{
			dataSource.pull();
			videoCapture.open(dataSource.getSetting("cameraUrl")); // Open the camera.
		}
		
		dataSource.setSetting("status", "RUNNING");
		dataSource.push();
		System.out.println("Running...");
		while(keepRunning)
		{
			videoCapture.read(frame); // Get image from camera.
			Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2RGB); // Convert from the evil BGR format to the nice RGB format.

			if(!isCalibrated) 
			{
				
				if(Calib3d.findChessboardCorners(frame, patternSize, corners)) // Try to find chessboard corners.
				{
					System.out.println("Found chessboard, calibrating...");
					//Mat calibOut = frame.clone();
					//Calib3d.drawChessboardCorners(calibOut, patternSize, corners, true); // Draw a debug image.
					//Imgcodecs.imwrite("calibOut.png", calibOut); // Save the debug image.
					
					List<Mat> cornerList = new ArrayList<Mat>(); // OpenCV likes lots of arrays, so we appease it, by putting our array into another array.
					cornerList.add(corners);
					
					System.out.println("Calibrating camera...");
					Calib3d.calibrateCamera(genChessboardObjPnts(), cornerList, frame.size(), cameraMatrix, distCoeffs, rvecs, tvecs); // Do the calibration.
					isCalibrated = true; // Yes, we are calibrated.
				}
				else
				{
					//System.out.println("Warning: Not calibrated yet. Working uncalibrated for now. Please show the camera a 5x5 checkerboard image.");
				}
			}
			
			dataSource.pull(); // Retrieve new data from the dataSource
			if(isCalibrated) // Only run if we are calibrated.
			{
				for (int targetI = 0; targetI < dataSource.numOfTargets(); targetI++)
				{
					List<Point3> objectPoints = new ArrayList<Point3>(); // Stores coordinants on object space.
					List<Point> imagePoints = new ArrayList<Point>(); // Stores coordinants in image space.
					if(dataSource.getData(targetI).type == TargetType.COLOR_DOTS)
					{
						imagePoints.clear();
						objectPoints.clear();			

						for (int imagePointI = 0; imagePointI < dataSource.getData(targetI).colorPoints.size(); imagePointI++)
						{
							if (!dataSource.getData(targetI).colorPoints.get(imagePointI).onlyTranslate) // Don't consider translate-only points in calculations.
							{
								objectPoints.add(OpenCVConverters.Point3dToOpenCVPoint3(dataSource.getData(targetI).colorPoints.get(imagePointI).mdlCoord)); // Add the point to the object points.
								
								Mat filteredImage = new Mat();
								// Get upper and lower RGB values from the range and median color.
								double lowerbR = Math.max(0, Math.min(255, dataSource.getData(targetI).colorPoints.get(imagePointI).color.R - dataSource.getData(targetI).colorPoints.get(imagePointI).range.R / 2));
								double lowerbG = Math.max(0, Math.min(255, dataSource.getData(targetI).colorPoints.get(imagePointI).color.G - dataSource.getData(targetI).colorPoints.get(imagePointI).range.G / 2));
								double lowerbB = Math.max(0, Math.min(255, dataSource.getData(targetI).colorPoints.get(imagePointI).color.B - dataSource.getData(targetI).colorPoints.get(imagePointI).range.B / 2));
								double upperbR = Math.max(0, Math.min(255, dataSource.getData(targetI).colorPoints.get(imagePointI).color.R + dataSource.getData(targetI).colorPoints.get(imagePointI).range.R / 2));
								double upperbG = Math.max(0, Math.min(255, dataSource.getData(targetI).colorPoints.get(imagePointI).color.G + dataSource.getData(targetI).colorPoints.get(imagePointI).range.G / 2));
								double upperbB = Math.max(0, Math.min(255, dataSource.getData(targetI).colorPoints.get(imagePointI).color.B + dataSource.getData(targetI).colorPoints.get(imagePointI).range.B / 2));
								// Filter the image based on these values.
								Core.inRange(frame, new Scalar(lowerbR, lowerbG, lowerbB), new Scalar(upperbR, upperbG, upperbB), filteredImage);
								// System.out.println(new Scalar(lowerbR, lowerbG, lowerbB) + " - " + new Scalar(upperbR, upperbG, upperbB));
								// Imgcodecs.imwrite("target"+targetI+"point"+imagePointI+"filter.png", filteredImage);
								
								List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
								Mat hierarchy = new Mat();
								//Find countours.
								Imgproc.findContours(filteredImage, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_TC89_KCOS);
								
								Point bestCenter = new Point(0, 0);
								float bestRadius = 0;
								// Find the best match.
								for (int contourI = 0; contourI < contours.size(); contourI++) {
									Point center = new Point(0, 0);
									float[] radius = {0};
									MatOfPoint2f tmp = new MatOfPoint2f();
									tmp.fromArray(contours.get(contourI).toArray());
									Imgproc.minEnclosingCircle(tmp, center, radius);
									if (radius[0] > bestRadius) {
										bestRadius = radius[0];
										bestCenter = center;
									}
								}
								
								// Add the image point.
								dataSource.getData(targetI).colorPoints.get(imagePointI).imgCoord = OpenCVConverters.OpenCVPointToPoint2d(bestCenter);
								imagePoints.add(bestCenter);
							}
						}
						
						
						// Imgproc.cvtColor(debugImg, debugImg, Imgproc.COLOR_RGB2BGR);
						// Imgcodecs.imwrite("target"+targetI+"debug.png", debugImg);
					}
					else if(dataSource.getData(targetI).type == TargetType.IMAGE)
					{
						imagePoints.clear();
						objectPoints.clear();
						
						// Find features in the frame.
						MatOfKeyPoint frameKeypoints = new MatOfKeyPoint();
						SIFTD.detect(frame, frameKeypoints);
						
						// Extract descriptors from features
						Mat frameDescriptors = new Mat();
						SIFTE.compute(frame, frameKeypoints, frameDescriptors );
						
						// Match the features from the frame to the features from the reference image.
						List<MatOfDMatch> matches = new ArrayList<MatOfDMatch>();
						FLANN.knnMatch(frameDescriptors, siftDescriptors.get(targetI), matches, 4);
						
						
						for(int matchI = 0; matchI < matches.size(); matchI++)
						{
							List<DMatch> matchList = matches.get(matchI).toList();
							for(int matchListI = 0; matchListI < matches.size(); matchListI++)
							{
								DMatch match = matchList.get(matchListI); // Get the match.
								
								// Extract the corresponding points from the two images.
								KeyPoint trainpnt = siftKeypoints.get(targetI).toList().get(match.trainIdx);
								KeyPoint framepnt = frameKeypoints.toList().get(match.queryIdx);
								
								// Add them.
								objectPoints.add(new Point3((trainpnt.pt.x/referenceImages.get(targetI).cols()) * dataSource.getData(targetI).referenceImageSize.X, (trainpnt.pt.y/referenceImages.get(targetI).rows()) * dataSource.getData(targetI).referenceImageSize.Y, 0.0));
								imagePoints.add(framepnt.pt);
							}
						}
					}
					else
					{
						continue; // Skip invalid targets (shouldn't really be any, but ust in case...)
					}
					
					// Convert the lists of points to mats of points
					MatOfPoint3f objPointsMat = new MatOfPoint3f();
					objPointsMat.fromList(objectPoints);
					MatOfPoint2f imgPointsMat = new MatOfPoint2f();
					imgPointsMat.fromList(imagePoints);

					
					Mat rvec = new Mat(); // Rotation Vector
					Mat tvec = new Mat(); // Translation Vector
					
					// Calculate rvec and tvec.
					Calib3d.solvePnP(objPointsMat, imgPointsMat, cameraMatrix, distCoeffs, rvec, tvec, false,Calib3d.CV_ITERATIVE);
					
					// convert rvec to a matrix
					Mat rmat = new Mat();
					Calib3d.Rodrigues(rvec, rmat);
					
					
					for (int pointI = 0; pointI < dataSource.getData(targetI).colorPoints.size(); pointI++) {
						// Do some conversions.
						Mat modelPoint = OpenCVConverters.Point3ToMat(OpenCVConverters.Point3dToOpenCVPoint3(dataSource.getData(targetI).colorPoints.get(pointI).mdlCoord));
						
						// Perform rotation and translation from model space into robot space. This is the good stuff.
						Mat targetPoint = new Mat();
						Core.add(rmat.mul(modelPoint), tvec, targetPoint); // Matrix math: R = RMAT * M + TVEC						
						
						// Add the data to the targets.
						dataSource.getData(targetI).colorPoints.get(pointI).tgtCoord = OpenCVConverters.OpenCVPoint3ToPoint3d(OpenCVConverters.MatToPoint3(targetPoint));
					}
					// Say that we have completed this target.
					dataSource.getData(targetI).hasData = true;
				}
				// Push the data to network tables.
				dataSource.push();
			}
		}
	}
	private static List<Mat> genChessboardObjPnts() // Function that generates 3d points of a checkerboard. Used for calibration.
	{
		List<Point3> pts = new ArrayList<Point3>();
		for(double x = 0; x < patternSize.width; x = x+1)
		{
			for(double y = 0; y < patternSize.height; y = y+1)
			{
				Point3 point = new Point3();
				point.x = x * squareSize;
				point.y = y * squareSize;
				point.z = 0;
				pts.add(point);
			}
		}
		List<Mat> ret = new ArrayList<Mat>();
		ret.add(Converters.vector_Point3f_to_Mat(pts));
		return ret;
		
	}
	
}
