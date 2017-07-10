package org.usfirst.frc.team4453.library.vision;

import java.io.Serializable;
import java.util.LinkedList;

public class Pipeline implements Serializable {
	private static final long serialVersionUID = -359657508442222721L;
	private LinkedList<PipelineStep> steps = new LinkedList<PipelineStep>();
	
	/**
	 * Adds a step to the end of the pipeline.
	 * @param in
	 */
	public void add(PipelineStep in)
	{
		steps.add(in);
	}
	
	/**
	 * Stops all running pipeline steps, than starts all steps.
	 * @throws InterruptedException If the calling thread is interrupted while waiting for a step to stop.
	 */
	public void start() throws InterruptedException
	{
		stop();
		for(PipelineStep p : steps)
		{
			p.start();
		}
	}
	
	/**
	 * Stops all running steps.
	 * @throws InterruptedException If the calling thread is interrupted while waiting for a step to stop.
	 */
	public void stop() throws InterruptedException
	{
		for(PipelineStep p : steps)
		{
			p.stop();
		}
	}
	
	/**
	 * Returns the step after the specified one, or null if it does not exist.
	 * @param in The step to find the next of.
	 * @return The next step, or null if the specified step is not in this pipeline or it is the last step.
	 */
	public PipelineStep getNextStep(PipelineStep in)
	{
		int i = steps.lastIndexOf(in);
		if(i != -1 && steps.size() != i+1)
		{
			return steps.get(i+1);
		}
		return null;
	}
}
