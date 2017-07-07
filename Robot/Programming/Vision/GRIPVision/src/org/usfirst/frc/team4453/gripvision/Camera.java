package org.usfirst.frc.team4453.gripvision;

import java.util.concurrent.TimeUnit;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class Camera {

	
	
	private VideoCapture camera;
	private String name;
	private String host;
	private Integer port = 1180;
	private String url = "?action=stream&dummy=param.mjpg";
	
	public Camera(String n, String h)
	{
		name = n;
		host = h;
		camera = null;
	}
	
	public Camera(String n, String h, int p)
	{
		name = n;
		host = h;
		port = p;
		camera = null;
	}
	
	public Camera(String n, String h, int p, String u)
	{
		name = n;
		host = h;
		port = p;
		url = u;
		camera = null;
	}
	
	public void open() throws Exception
	{
		open(10);
	}
	
	public void open(int timeout) throws Exception
	{
		String fullurl = "http://" + host + ":" + port.toString() + "/" + url;
		camera = new VideoCapture(fullurl);
		
		long startTime = System.nanoTime();
		while(!camera.isOpened())
		{
			if(TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime) < timeout)
			{
				throw new Exception("Opening camera \"" + name + "\" at url \"" + fullurl + "\" timed out.");
			}
		}
		return;
	}
	
	public void close()
	{
		camera = null;
	}
	
	public boolean isOpen()
	{
		return camera!=null ? camera.isOpened() : false;
	}
	
	public Mat getFrame() throws Exception
	{
		if(!isOpen())
		{
			throw new Exception("Camera \"" + name + "\" is not open, but tried to read a frame.");
		}
		Mat ret = new Mat();
		camera.read(ret);
		return ret;
	}

	public String getName() {
		return name;
	}
	

}
