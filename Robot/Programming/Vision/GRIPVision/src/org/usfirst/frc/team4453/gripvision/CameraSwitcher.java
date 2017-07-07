package org.usfirst.frc.team4453.gripvision;

import java.util.HashMap;

import org.opencv.core.Mat;

public class CameraSwitcher {
	HashMap<String, Camera> cameras = new HashMap<String, Camera>();
	String currentCamera;
	public void addCamera(Camera c)
	{
		cameras.put(c.getName(), c);
		try {
			c.open();
		}
		catch (Exception e) {
		}
		currentCamera = c.getName();
	}
	
	public boolean switchCamera(String c) throws Exception
	{
		if(cameras.get(c) == null)
		{
			throw new Exception("Failed to switch to camera \"" + c + "\": Camera not found.");
		}
		if(!cameras.get(c).isOpen())
		{
			try{
				cameras.get(c).open();
			}
			catch(Exception e)
			{
				throw new Exception("Failed to switch to camera \"" + c + "\"", e);
			}
		}
		currentCamera = c;
		return true;
	}
	
	public Mat getFrame() throws Exception
	{
		Mat ret = null;
		for(Camera c : cameras.values())
		{
			if(c.getName().equals(currentCamera))
			{
				try {
					ret = c.getFrame();
				}
				catch (Exception e) {
					throw new Exception("Failed to read from camera.", e);
				}
			}
			else
			{
				try {
					c.getFrame();
				}
				catch (Exception e) {
					// Don't care about this camera currently.
				}
			}
		}
		if(ret == null)
		{
			throw new Exception("Camera not found.");
		}
		return ret;
	}

	public String getCurrentCamera() {
		return currentCamera;
	}
}
