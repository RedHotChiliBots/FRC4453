package org.usfirst.frc.team4453.library.vision.test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;
import org.usfirst.frc.team4453.library.vision.Data;
import org.usfirst.frc.team4453.library.vision.Pipeline;
import org.usfirst.frc.team4453.library.vision.PipelineStep;
import org.usfirst.frc.team4453.library.vision.pipelinesteps.RemapStep;

public class RemapStepTest {

	private class TestStep1 extends PipelineStep
	{
		public TestStep1(Pipeline p) {
			super(p);
		}

		@Override
		protected boolean execute(Data in) {
			in.put("foo1", "orange");
			in.put("foo2", "orange2");
			return true;
		}
	}
	
	private class TestStep2 extends PipelineStep
	{
		public Data data;
		public TestStep2(Pipeline p) {
			super(p);
		}

		@Override
		protected boolean execute(Data in) {
			data = in;
			return false;
		}
	}
	
	@Test
	public void testRemapStepPipelineMapOfStringString() throws InterruptedException {
		Pipeline p = new Pipeline();
		HashMap<String,String> map = new HashMap<>();
		map.put("foo1", "bar1");
		map.put("bar2", "baz");
		map.put("foo2", "bar2");
		map.put("bar2", "baz");
		
		RemapStep step = new RemapStep(p, map);
		TestStep1 step1 = new TestStep1(p);
		TestStep2 step2 = new TestStep2(p);
		
		p.add(step1);
		p.add(step);
		p.add(step2);
		
		p.start();
		Thread.sleep(100);
		
		p.stop();
		
		assertNull(step2.data.get("foo1"));
		assertEquals("orange", step2.data.get("bar1"));
		assertNull(step2.data.get("foo2"));
		assertEquals("orange2", step2.data.get("bar2"));
		assertNull(step2.data.get("baz"));
	}

	@Test
	public void testRemapStepPipelineStringString() throws InterruptedException {
		Pipeline p = new Pipeline();
		
		RemapStep step = new RemapStep(p, "foo1", "bar1");
		TestStep1 step1 = new TestStep1(p);
		TestStep2 step2 = new TestStep2(p);
		
		p.add(step1);
		p.add(step);
		p.add(step2);
		
		p.start();
		Thread.sleep(100);
		
		p.stop();
		
		assertNull(step2.data.get("foo1"));
		assertEquals("orange", step2.data.get("bar1"));
		assertEquals("orange2", step2.data.get("foo2"));
	}

}
