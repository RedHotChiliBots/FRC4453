package org.usfirst.frc.team4453.robot.subsystems;

import org.usfirst.frc.team4453.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearGrabber extends Subsystem {

	DoubleSolenoid grabber;
	DoubleSolenoid tipper;

	private boolean grab = true;
	private boolean lift = true;
	
	public GearGrabber() {
		super("GearGrabber");

		grabber = new DoubleSolenoid(RobotMap.GEAR_GRAB, RobotMap.GEAR_RELEASE);
		tipper = new DoubleSolenoid(RobotMap.GEAR_TIPUP, RobotMap.GEAR_TIPDOWN);

		grabber.set(DoubleSolenoid.Value.kForward);
		tipper.set(DoubleSolenoid.Value.kForward);
	}

	@Override
	public void initDefaultCommand() {
		// Not used.
	}
	
	public Value getLiftState()
	{
		return tipper.get();
	}
	
	public Value getGrabberState()
	{
		return grabber.get();
	}
	
	public void toggleGrab() {
		grab = !grab;
		if (grab) {
			grabber.set(DoubleSolenoid.Value.kForward);
		} else {
			grabber.set(DoubleSolenoid.Value.kReverse);
		}
	}

	public void toggleLift() {
		lift = !lift;
		if (lift) {
			tipper.set(DoubleSolenoid.Value.kForward);
		} else {
			tipper.set(DoubleSolenoid.Value.kReverse);
		}
	}
	
	public void grab() {
		grabber.set(DoubleSolenoid.Value.kForward);
	}

	public void release() {
		grabber.set(DoubleSolenoid.Value.kReverse);
	}

	public void tipUp() {
		tipper.set(DoubleSolenoid.Value.kForward);
	}

	public void tipDown() {
		tipper.set(DoubleSolenoid.Value.kReverse);
	}
}
