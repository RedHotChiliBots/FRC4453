package org.usfirst.frc.team4453.library.vision;

import java.io.Serializable;
import java.util.concurrent.ArrayBlockingQueue;


/**
 * This class represents a step in a pipeline.
 * Running this class will run this class' execute method in a loop until it returns false.
 * Any class wanting to function as a pipeline step should subclass this.
 * @author Conner Ebbinghaus
 *
 */
public abstract class PipelineStep implements Serializable, Runnable {
	private static final long serialVersionUID = -5477198664992763293L;
	private ArrayBlockingQueue<Object> inQueue;
	private Pipeline pipeline;
	private PipelineStep next = null;
	private boolean stopReq = false;
	private Thread thread = new Thread(this);
	/**
	 * Constructor.
	 * @param p The pipeline this step belongs to.
	 * @param queueSize The size of the input queue.
	 */
	public PipelineStep(Pipeline p, int queueSize)
	{
		inQueue = new ArrayBlockingQueue<Object>(queueSize);
		pipeline = p;
		thread.setDaemon(true);
	}
	
	/**
	 * Constructor. The size of the input queue is 5 by default.
	 * @param p The pipeline this step belongs to.
	 */
	public PipelineStep(Pipeline p)
	{
		inQueue = new ArrayBlockingQueue<Object>(5);
		pipeline = p;
		thread.setDaemon(true);
	}
	
	/**
	 * Called by the previous step to add an object to our queue.
	 * @param in The object to add.
	 * @throws InterruptedException If this thread is interrupted while blocking.
	 */
	private void addToQueue(Object in) throws InterruptedException
	{
		inQueue.put(in);
	}
	
	
	/**
	 * Sends the object to the next object in the pipeline, blocking if the next step's queue is full. Does nothing if this step is the last in the pipeline.
	 * @param in The object to send.
	 * @throws InterruptedException If this thread is interrupted while blocking.
	 */
	protected void sendToNext(Object in) throws InterruptedException
	{
		if(next == null)
		{
			next = pipeline.getNextStep(this);
		}
		if(next != null)
		{
			next.addToQueue(in);
		}
	}
	
	/**
	 * Grabs the first object in the queue, blocking if it is empty.
	 * @return The first object on the queue.
	 * @throws InterruptedException If this thread is interrupted while blocking.
	 */
	protected Object recieveNext() throws InterruptedException
	{
		return inQueue.take();
	}
	
	/**
	 * Called in a loop by run().
	 * @return True if this step should continue running.
	 */
	protected abstract boolean execute();
	
	/**
	 * Calls the execute() method in a loop, until either it returns true or stop() is called.
	 */
	public void run()
	{
		while(execute() && !stopReq){Thread.yield();}
	}
	
	/** 
	 * Starts a thread running this class.
	 */
	public void start()
	{
		thread.start();
	}
	
	/**
	 * Asks the run() method to stop, and waits for it to finish.
	 * @throws InterruptedException If the calling thread is interrupted while waiting.
	 */
	public void stop() throws InterruptedException
	{
		stopReq = true;
		thread.join();
	}
}
