package org.usfirst.frc.team4453.test.subsystems;

import java.lang.reflect.Field;

import org.usfirst.frc.team4453.robot.RobotMap;
import org.usfirst.frc.team4453.robot.subsystems.GearGrabber;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import junit.framework.TestCase;

public class TestGearGrabber extends TestCase {

	public void testInit() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
	{
		GearGrabber test = new GearGrabber();
		
		Field grabberField = test.getClass().getDeclaredField("grabber");
		grabberField.setAccessible(true);
		DoubleSolenoid grabber = (DoubleSolenoid) grabberField.get(test);
		assertEquals("Grabber extend id is set correctly.", RobotMap.GEAR_GRAB, grabber.testFwdID);
		assertEquals("Grabber retract id is set correctly.", RobotMap.GEAR_RELEASE, grabber.testRevID);
		
		Field tipperField = test.getClass().getDeclaredField("tipper");
		tipperField.setAccessible(true);
		DoubleSolenoid tipper = (DoubleSolenoid) tipperField.get(test);
		assertEquals("Grabber extend id is set correctly.", RobotMap.GEAR_TIPUP, tipper.testFwdID);
		assertEquals("Grabber retract id is set correctly.", RobotMap.GEAR_TIPDOWN, tipper.testRevID);
	}
	
	public void testGrab() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		GearGrabber test = new GearGrabber();
		test.grab();
		
		Field grabberField = test.getClass().getDeclaredField("grabber");
		grabberField.setAccessible(true);
		DoubleSolenoid grabber = (DoubleSolenoid) grabberField.get(test); // I can't believe this works.
		assertEquals("Solonoid is extended", DoubleSolenoid.Value.kForward, grabber.testSetVal);
	}

	public void testRelease() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		GearGrabber test = new GearGrabber();
		test.release();
		
		Field grabberField = test.getClass().getDeclaredField("grabber");
		grabberField.setAccessible(true);
		DoubleSolenoid grabber = (DoubleSolenoid) grabberField.get(test);
		assertEquals("Solonoid is retracted", DoubleSolenoid.Value.kReverse, grabber.testSetVal);
	}

	public void testTipUp() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		GearGrabber test = new GearGrabber();
		test.tipUp();
		
		Field tipperField = test.getClass().getDeclaredField("tipper");
		tipperField.setAccessible(true);
		DoubleSolenoid tipper = (DoubleSolenoid) tipperField.get(test);
		assertEquals("Solonoid is retracted", DoubleSolenoid.Value.kForward, tipper.testSetVal);
	}

	public void testTipDown() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		GearGrabber test = new GearGrabber();
		test.tipDown();
		
		Field tipperField = test.getClass().getDeclaredField("tipper");
		tipperField.setAccessible(true);
		DoubleSolenoid tipper = (DoubleSolenoid) tipperField.get(test);
		assertEquals("Solonoid is retracted", DoubleSolenoid.Value.kReverse, tipper.testSetVal);
	}

}
