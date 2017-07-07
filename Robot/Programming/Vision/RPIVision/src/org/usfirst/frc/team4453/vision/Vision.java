package org.usfirst.frc.team4453.vision;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.usfirst.frc.team4453.visionlib.IDataSource;
import org.usfirst.frc.team4453.visionlib.NetworkTablesDataSource;
import org.usfirst.frc.team4453.visionlib.Target;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision {
	public static VideoCapture videoCapture;
	private static String url = "http://localhost:1180/?action=stream&dummy=param.mjpg";
	
	private static Scalar lowerHSV = new Scalar(58, 126, 164);
	private static Scalar upperHSV = new Scalar(125, 255, 255);
	
	public static void main(String[] args) {
		System.out.println("Raspberry Pi Vision Code Starting...");
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("roboRIO-4453-FRC.local");
		NetworkTable subtable = NetworkTable.getTable("/GRIP/Stronghold");
		NetworkTable smartDashboard = NetworkTable.getTable("/SmartDashboard");
		
		int count = 0;
		while (!subtable.isConnected() && count++ < 100) {
			System.out.println("Waiting for NetworkTable Server!");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (!subtable.isConnected()) {
			System.out.println("Failed to connect to NetworkTable!");
			return;
		}
		smartDashboard.putNumber("Vision FPS", 0);
		smartDashboard.putNumber("Vision Contours", 0);
		smartDashboard.putNumber("Vision Conn. Test", -1);
		smartDashboard.putString("Vision Status", "OPENING CAMERA");
		NetworkTable.flush();
		
		System.out.println("Opening Camera - "+url);
		videoCapture = new VideoCapture();
		videoCapture.set(Videoio.CV_CAP_PROP_BUFFERSIZE, 1);
		videoCapture.open(url);

		if (!videoCapture.isOpened()) {
			System.out.println("Failed to open camera!");
			return;
		}

		smartDashboard.putString("Vision Status", "INITIALIZING");
		NetworkTable.flush();
		
		System.out.println("Started up, running loop...");
		
		int counter = 0;
		Mat image = new Mat();
		Mat HSVimage = new Mat();
		Mat filteredImage = new Mat();
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat();
		MatOfPoint2f points = new MatOfPoint2f();
		Target bestrect = new Target(0, 0, 0, 0);
		
		
		IDataSource dataSource = new NetworkTablesDataSource(subtable);
		
		
		long startTime = System.currentTimeMillis();
		long frames = 0;
		boolean keepRunning = true;
		
		smartDashboard.putString("Vision Status", "CLEARING BUFFER");
		NetworkTable.flush();
		
		System.out.println("Throwing away 50 frames...");
		for(int i = 0; i < 50; i++)
		{
			videoCapture.grab();
		}
		smartDashboard.putString("Vision Status", "RUNNING");
		NetworkTable.flush();
		
		while (keepRunning) {
			if (!videoCapture.isOpened()) {
				System.out.println("Camera was closed on us!");
				keepRunning = false;
			}
			if(!subtable.isConnected())
			{
				return;
			}
			double testNum = Math.random();
			subtable.putNumber("test", testNum);
			NetworkTable.flush();
			if(subtable.getNumber("test", -99) != testNum)
			{
				return;
			}
			if(!videoCapture.grab())
			{
				continue;
			}
			videoCapture.retrieve(image);
			
			if(image.empty())
			{
				Thread.yield();
				continue;
			}
			
			Imgproc.cvtColor(image, HSVimage, Imgproc.COLOR_BGR2HSV);

			
			Core.inRange(HSVimage, lowerHSV, upperHSV,filteredImage);

			contours.clear();

			Imgproc.findContours(filteredImage, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

			bestrect.centerX = 0;
			bestrect.centerY = 0;
			bestrect.height = 0;
			bestrect.width = 0;
			
			System.out.println(contours.size());
			
			for (int i = 0; i < contours.size(); i++) {
				
				points.fromList(contours.get(i).toList());
				Rect uprightrect = Imgproc.boundingRect(contours.get(i));
				if(uprightrect.width < 20)
				{
					continue;
				}
					
				//RotatedRect rotatedrect = Imgproc.minAreaRect(points);
				

				//HeightWidth rect = new HeightWidth(rotatedrect.size.height, uprightrect.width,
				//		uprightrect.x + uprightrect.width / 2, rotatedrect.center.y);
				Target rect = new Target(uprightrect.height, uprightrect.width,
						uprightrect.x + uprightrect.width / 2, uprightrect.y + uprightrect.height / 2);
				
				if (rect.width > bestrect.width) {
					bestrect = rect;
				}

			}

			while (!subtable.isConnected() && count++ < 100) {
				System.out.println("Waiting for NetworkTable Server!");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			dataSource.putData(bestrect);
			
			//System.gc();
			
			long elapsedTime = System.currentTimeMillis() - startTime;
			double fps = ++frames / Math.max(1,TimeUnit.MILLISECONDS.toSeconds(elapsedTime));
			if (elapsedTime > 1000) {
				System.out.println("FPS: " + fps);
			}
			smartDashboard.putNumber("Vision FPS", fps);
			smartDashboard.putNumber("Vision Contours", contours.size());
			counter = (counter + 1) % 10;
			smartDashboard.putNumber("Vision Conn. Test", counter);
			NetworkTable.flush();
			//Thread.yield();
			
		}
			
		videoCapture.release();
		NetworkTable.shutdown();

		return;
	}

}
