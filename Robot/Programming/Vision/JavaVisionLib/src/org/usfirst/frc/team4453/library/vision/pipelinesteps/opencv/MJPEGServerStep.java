package org.usfirst.frc.team4453.library.vision.pipelinesteps.opencv;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.usfirst.frc.team4453.library.vision.Data;
import org.usfirst.frc.team4453.library.vision.Pipeline;
import org.usfirst.frc.team4453.library.vision.PipelineStep;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * This PipelineStep hosts a server on localhost:8080 and serves the specified frames as a MJPEG Stream. Uses OpenCV's imencode to encode frames.
 * @author Conner Ebbinghaus
 *
 */
public class MJPEGServerStep extends PipelineStep {
	/**
	 * The Http Server Object.
	 */
	private HttpServer server = HttpServer.create();
	
	/**
	 * A HttpHandler that recieves frames and serves them to clients.
	 * @author Conner Ebbinghaus
	 *
	 */
	private class Handler implements HttpHandler
	{
		/**
		 * The incoming queue for frames; limited to 5 in buffer.
		 */
		public ArrayBlockingQueue<Mat> inQueue = new ArrayBlockingQueue<>(5);
		
		/**
		 * The Phaser to manage all of the server threads.
		 * Removes top frame from the queue when all threads are done with it.
		 */
		private Phaser phaser = new Phaser() {
			protected boolean onAdvance(int phase, int parties) { 
				inQueue.poll();
				return false; 
			}
		};
		
		/**
		 * The JPEG quality.
		 */
		public int jpegq = 95;
		
		@Override
		public void handle(HttpExchange ex) throws IOException {
			ex.getResponseHeaders().add("Content-Type", "multipart/x-mixed-replace; boundary=--BoundaryString");
			ex.sendResponseHeaders(200, 0);
			OutputStream out = ex.getResponseBody();
			phaser.register();

			while(true)
			{
				Mat frame = null;
				try {
					phaser.awaitAdvanceInterruptibly(phaser.arrive());
				} catch (InterruptedException e) {
					phaser.arriveAndDeregister();
					return;
				}
				while(frame == null)
				{
					frame = inQueue.peek();
					Thread.yield();
				}
				MatOfByte frameEncMat = new MatOfByte(); 
				int[] paramsA = {Imgcodecs.CV_IMWRITE_JPEG_QUALITY, jpegq};
				MatOfInt params = new MatOfInt();
				params.fromArray(paramsA);
				Imgcodecs.imencode(".jpeg", frame, frameEncMat, params);
				byte[] frameEnc = frameEncMat.toArray();
				out.write((
						"--BoundaryString\r\n" +
								"Content-type: image/jpeg\r\n" +
								"Content-Length: " +
								frameEnc.length +
						"\r\n\r\n").getBytes());
				out.write(frameEnc);
				out.write("\r\n\r\n".getBytes());
				out.flush();
				Thread.yield();
			}
		}
	}
	
	/**
	 * Instantiation of the handler.
	 */
	private Handler handler = new Handler();
	
	/**
	 * Data name of the frame to send.
	 */
	private String frameName;
	
	/**
	 * Constructor.
	 * @param p The Pipeline.
	 * @param n Name of the frame to send in the Data.
	 * @throws IOException If the server could not be started.
	 */
	public MJPEGServerStep(Pipeline p, String n) throws IOException {
		super(p);
		server.bind(new InetSocketAddress("127.0.0.1", 8080), 0);
		server.setExecutor(Executors.newCachedThreadPool());
		server.createContext("/", handler);
		server.start();
		frameName = n;
	}
	
	/**
	 * Constructor.
	 * @param p The Pipeline.
	 * @param n Name of the frame to send in the Data.
	 * @param addr The address to bind the server to.
	 * @throws IOException
	 */

	public MJPEGServerStep(Pipeline p, String n, InetSocketAddress addr) throws IOException {
		super(p);
		server.bind(addr, 0);
		server.setExecutor(Executors.newCachedThreadPool());
		server.createContext("/", handler);
		server.start();
		frameName = n;
	}
	
	@Override
	protected boolean execute(Data in) {
		handler.inQueue.offer((Mat)in.get(frameName));
		return true;
	}
	
	/**
	 * Sets the JPEG quality of the exported frames.
	 * @param q The quality (0-100).
	 */
	public void setQuality(int q)
	{
		handler.jpegq = Math.max(0, Math.min(100, q));
	}
	
	/**
	 * Gets the set quality.
	 * @return The JPEG quality.
	 */
	public int getQuality()
	{
		return handler.jpegq;
	}

	public void stop()
	{
		server.stop(0);
	}
}
