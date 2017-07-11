package org.usfirst.frc.team4453.library.vision.pipelinesteps;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team4453.library.vision.Data;
import org.usfirst.frc.team4453.library.vision.Pipeline;
import org.usfirst.frc.team4453.library.vision.PipelineStep;

public class RemapStep extends PipelineStep {
	private static final long serialVersionUID = 2821882205557526658L;
	private Map<String, String> mappings;
	
	public RemapStep(Pipeline p, Map<String, String> m) {
		super(p,1);
		mappings = m;
	}
	
	public RemapStep(Pipeline p, String a, String b) {
		super(p,1);
		mappings = new HashMap<String,String>();
		mappings.put(a, b);
	}

	@Override
	protected boolean execute(Data in) {
		for(Map.Entry<String, String> ent : mappings.entrySet())
		{
			in.remap(ent.getKey(), ent.getValue());
		}
		return true;
	}

}
