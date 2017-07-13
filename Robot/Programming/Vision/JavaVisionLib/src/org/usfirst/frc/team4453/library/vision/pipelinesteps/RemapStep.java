package org.usfirst.frc.team4453.library.vision.pipelinesteps;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team4453.library.vision.Data;
import org.usfirst.frc.team4453.library.vision.Pipeline;
import org.usfirst.frc.team4453.library.vision.PipelineStep;

/**
 * This PipelineStep renames data.
 * @author Conner Ebbinghaus
 *
 */
public class RemapStep extends PipelineStep {
	
	/**
	 * The name mappings.
	 * Key -> Value
	 */
	private Map<String, String> mappings;
	
	/**
	 * Constructor.
	 * @param p The Pipeline.
	 * @param m The Mappings.
	 */
	public RemapStep(Pipeline p, Map<String, String> m) {
		super(p,1);
		mappings = m;
	}
	
	/**
	 * Constructor.
	 * @param p The Pipeline.
	 * @param a The name to match.
	 * @param b The name to change the matched name into.
	 */
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
