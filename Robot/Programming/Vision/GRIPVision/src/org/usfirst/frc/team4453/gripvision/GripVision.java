package org.usfirst.frc.team4453.gripvision;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class GripVision {
	private static double gearWidth = 0.20955;
	private static double boilerWidth = 0.381;
	private static double FOV = 62.2;
	private static double boilerFudgeFactor = 0.7938;
	private static double effectiveBoilerWidth = 0;
	private static boolean gearPreferWide = false;
	public static void main(String[] args) {
		IPipeline pipeline = new Pipeline();
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("roboRIO-4453-FRC.local");
		NetworkTable table = NetworkTable.getTable("/Vision");
		
		table.putString("RPIStatus", "Starting...");
		NetworkTable.flush();
		CameraSwitcher cameras = new CameraSwitcher();
		cameras.addCamera(new Camera("boiler", "rpicameraboiler.local"));
		cameras.addCamera(new Camera("gear", "rpicameragear.local"));
		try {
			cameras.switchCamera("boiler");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		table.putString("RPIStatus", "Running...");
		NetworkTable.flush();
		
		table.putNumber("param:gearWidth", gearWidth);
		table.putNumber("param:boilerWidth", boilerWidth);
		table.putNumber("param:FOV", FOV);
		table.putBoolean("param:gearPreferWide", gearPreferWide);
		table.putString("param:setCamera", cameras.getCurrentCamera());
		table.putString("param:getCamera", cameras.getCurrentCamera());
		
		NetworkTable.flush();
		
		int conntestNum = 0;
		long lastSynced = System.currentTimeMillis();
		boolean didConnWarn = false;
		
		while(true)
		{
			gearWidth = table.getNumber("param:gearWidth", gearWidth);
			boilerWidth = table.getNumber("param:boilerWidth", boilerWidth);
			FOV = table.getNumber("param:FOV", FOV);
			effectiveBoilerWidth = boilerWidth * boilerFudgeFactor;
			gearPreferWide = table.getBoolean("param:gearPreferWide", gearPreferWide);
			
			try {
				cameras.switchCamera(table.getString("param:setCamera", cameras.getCurrentCamera()));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			Mat frame;
			try {
				frame = cameras.getFrame();
			}
			catch (Exception e) {
				continue;
			}
			long startTime = System.nanoTime();
			pipeline.process(frame);
			long endTime=System.nanoTime();
			ArrayList<MatOfPoint> output = pipeline.output();

			Rect boilerTop = null;
			Rect boilerBot = null;
			Rect gearLeft = null;
			Rect gearRight = null;

			for(MatOfPoint i : output)
			{
				Rect aabb = Imgproc.boundingRect(i);
				if(aabb.area() < 10)
				{
					continue;
				}
				else if(aabb.height / aabb.width > 1)
				{
					if(gearLeft == null)
					{
						gearLeft = aabb;
						gearRight = aabb;
					}
					else if(gearLeft == gearRight)
					{
						gearRight = gearLeft;
						gearLeft = aabb;
					}
					else if(gearPreferWide && gearLeft.height / gearLeft.width > aabb.height / aabb.width)
					{
						gearRight = gearLeft;
						gearLeft = aabb;
					}
					else if(gearPreferWide && gearRight.height / gearRight.width > aabb.height / aabb.width)
					{
						gearRight = aabb;
					}
					else if(!gearPreferWide && gearLeft.height / gearLeft.width < aabb.height / aabb.width)
					{
						gearRight = gearLeft;
						gearLeft = aabb;
					}
					else if(!gearPreferWide && gearRight.height / gearRight.width < aabb.height / aabb.width)
					{
						gearRight = aabb;
					}
				}
				else if(aabb.height / aabb.width < 1)
				{
					if(boilerTop == null)
					{
						boilerTop = aabb;
						boilerBot = aabb;
					}
					else if(boilerTop == boilerBot)
					{
						boilerBot = boilerTop;
						boilerTop = aabb;
					}
					else if(boilerTop.height / boilerTop.width < aabb.height / aabb.width)
					{
						boilerBot = boilerTop;
						boilerTop = aabb;
					}
					else if(boilerBot.height / boilerBot.width < aabb.height / aabb.width)
					{
						boilerBot = aabb;
					}
				}
				else
				{
					continue;
				}
			}
			if(gearLeft != null && gearRight != null)
			{
				if(Math.abs((gearLeft.y + gearLeft.height)/2 - (gearRight.y + gearRight.height)/2) > 25)
				{
					gearLeft = null;
					gearRight = null;
				}
				else if(gearLeft.x > gearRight.x)
				{
					Rect tmp = gearRight;
					gearRight = gearLeft;
					gearLeft = tmp;
				}
			}
			if(boilerTop != null && boilerBot != null)
			{
				if(Math.abs((boilerTop.x + boilerTop.width)/2 - (boilerBot.x + boilerBot.width)/2) > 15)
				{
					boilerTop = null;
					boilerBot = null;
				}
				else if(boilerTop.x > boilerBot.x)
				{
					Rect tmp = boilerBot;
					boilerBot = boilerTop;
					boilerTop = tmp;
				}
			}
			boolean isBoilerFound = true;
			boolean isGearFound = true;
			if(gearLeft == null || gearRight == null || gearLeft == gearRight)
			{
				isGearFound = false;
			}
			if(boilerTop == null || boilerBot == null || boilerTop == boilerBot)
			{
				isBoilerFound = false;
			}

			Point boiler = new Point();
			Point gear = new Point();

			if(isBoilerFound)
			{
				Point topCenter = new Point(boilerTop.x + boilerTop.width/2, boilerTop.y + boilerTop.height/2);
				Point botCenter = new Point(boilerBot.x + boilerBot.width/2, boilerBot.y + boilerBot.height/2);
				boiler.x = (topCenter.x + botCenter.x)/2;
				boiler.y = (topCenter.y + botCenter.y)/2;
			}
			Point leftCenter = null;
			Point rightCenter = null;
			if(isGearFound)
			{
				leftCenter = new Point(gearLeft.x + gearLeft.width/2, gearLeft.y + gearLeft.height/2);
				rightCenter = new Point(gearRight.x + gearRight.width/2, gearRight.y + gearRight.height/2);
				gear.x = (leftCenter.x + rightCenter.x)/2;
				gear.y = (leftCenter.y + rightCenter.y)/2;
			}
			double boilerDist = 0;
			double boilerAngleOffset = 0;
			if(isBoilerFound)
			{
				boilerDist = (effectiveBoilerWidth * (double)frame.width()) / ((((double)boilerTop.width+(double)boilerBot.width)/2.0) * 2.0*Math.tan(Math.toRadians((double)FOV/2.0)));
				double boilerXOffset = boiler.x - (frame.width()/2);
				boilerAngleOffset = Math.toDegrees(Math.atan((double)boilerXOffset / (((double)frame.width()/2.0) / Math.tan(Math.toRadians((double)FOV/2.0)))));
			}
			double gearDist = 0;
			double gearAngleOffset = 0;
			if(isGearFound)
			{
				gearDist = (gearWidth * (double)frame.width()) / (Math.abs((double)leftCenter.x - (double)rightCenter.x) * 2*Math.tan(Math.toRadians((double)FOV/2.0)));
				double gearXOffset = (double)gear.x - ((double)frame.width()/2.0);
				gearAngleOffset = Math.toDegrees(Math.atan((double)gearXOffset / (((double)frame.width()/2.0) / Math.tan(Math.toRadians((double)FOV/2.0)))));
			}

			table.putNumber("general:frameTimeNano", endTime - startTime);

			table.putNumber("frame:width", frame.width());
			table.putNumber("frame:height", frame.height());

			table.putBoolean("gear:Found", isGearFound);
			table.putNumber("gear:X", gear.x);
			table.putNumber("gear:Y", gear.y);
			table.putNumber("gear:Distance", gearDist);
			table.putNumber("gear:angleOffset", gearAngleOffset);

			table.putBoolean("boiler:Found", isBoilerFound);
			table.putNumber("boiler:X", boiler.x);
			table.putNumber("boiler:Y", boiler.y);
			table.putNumber("boiler:Distance", boilerDist);
			table.putNumber("boiler:angleOffset", boilerAngleOffset);
			table.putString("param:getCamera", cameras.getCurrentCamera());
			
			table.putNumber("debug:connTestToRobot", conntestNum);
			table.putNumber("debug:connTestToVisionReturn", table.getNumber("debug:connTestToVision", -1));
			NetworkTable.flush();
			
			if(conntestNum == table.getNumber("debug:connTestToRobotReturn", -1))
			{
				lastSynced = System.currentTimeMillis();
				conntestNum++;
				if(conntestNum > 100)
				{
					conntestNum = 0;
				}
			}
			else if(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - lastSynced) > 5 && !didConnWarn)
			{
				System.out.println("Warning: Last sync with robot was over 5 seconds ago. Bad connection.");
				System.out.flush();
				didConnWarn = true;
			}
			else if(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - lastSynced) > 10)
			{
				System.out.println("Error: No sync for 10 seconds. Assuming connection lost. Restarting vision code...");
				System.out.flush();
				return;
			}
			else
			{
				didConnWarn = false;
			}
			System.gc();
		}
	
	}

}
