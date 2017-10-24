package org.usfirst.frc.team4453.library.vision.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.usfirst.frc.team4453.library.vision.Data;
import org.usfirst.frc.team4453.library.vision.Pipeline;
import org.usfirst.frc.team4453.library.vision.PipelineStep;

public class PipelineTest {

	private class TestPipelineStep extends PipelineStep
	{
		public TestPipelineStep(Pipeline p) {
			super(p);
		}

		@Override
		protected boolean execute(Data in) {
			return true;
		}
	}
	
	private class Test2PipelineStep extends PipelineStep
	{
		public boolean hasRun = false;
		public Test2PipelineStep(Pipeline p) {
			super(p);
		}

		@Override
		protected boolean execute(Data in) {
			in.put("test", "orange");
			hasRun = true;
			return true;
		}
	}
	
	private class Test3PipelineStep extends PipelineStep
	{
		public boolean hasRun = false;
		public String recvStr = "";
		public Test3PipelineStep(Pipeline p) {
			super(p);
		}

		@Override
		protected boolean execute(Data in) {
			recvStr = (String) in.get("test");
			hasRun = true;
			return true;
		}
		
	}
	
	@Test
	public void testAdd() {
		Pipeline p = new Pipeline();
		TestPipelineStep step1 = new TestPipelineStep(p);
		TestPipelineStep step2 = new TestPipelineStep(p);

		
		p.add(step1);
		assertTrue(p.isFirst(step1));
		
		p.add(step2);
		assertTrue(p.isFirst(step1));
		assertFalse(p.isFirst(step2));
		assertSame(step2, p.getNextStep(step1));
	}

	@Test
	public void testStart() throws InterruptedException {
		Pipeline p = new Pipeline();
		Test2PipelineStep step1 = new Test2PipelineStep(p);
		Test3PipelineStep step2 = new Test3PipelineStep(p);
		p.add(step1);		
		p.add(step2);
		
		p.start();
		
		Thread.sleep(100);
		
		assertTrue(step1.hasRun);
		assertTrue(step2.hasRun);
		assertEquals("orange", step2.recvStr);
	}

	@Test
	public void testStop() throws InterruptedException {
		Pipeline p = new Pipeline();
		Test2PipelineStep step1 = new Test2PipelineStep(p);
		Test2PipelineStep step2 = new Test2PipelineStep(p);
		p.add(step1);		
		p.add(step2);
		
		p.start();
		
		Thread.sleep(100);
		
		p.stop();
		step1.hasRun = false;
		step2.hasRun = false;
		
		Thread.sleep(100);
		
		assertFalse(step1.hasRun);
		assertFalse(step2.hasRun);
	}

	@Test
	public void testGetNextStep() {
		Pipeline p = new Pipeline();
		TestPipelineStep step1 = new TestPipelineStep(p);
		TestPipelineStep step2 = new TestPipelineStep(p);
		TestPipelineStep step3 = new TestPipelineStep(p);
		
		p.add(step1);		
		p.add(step2);
		
		assertSame(step2, p.getNextStep(step1));
		assertNull(p.getNextStep(step2));
		assertNull(p.getNextStep(step3));
	}

	@Test
	public void testIsFirst() {
		Pipeline p = new Pipeline();
		TestPipelineStep step1 = new TestPipelineStep(p);
		TestPipelineStep step2 = new TestPipelineStep(p);
		TestPipelineStep step3 = new TestPipelineStep(p);
		
		p.add(step1);		
		p.add(step2);
		
		assertTrue(p.isFirst(step1));
		assertFalse(p.isFirst(step2));
		assertFalse(p.isFirst(step3));
	}

}
