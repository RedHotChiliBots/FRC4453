
package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.OI;
import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * DriveWithjoystick - gets commands from the joysticks defined in OI to send to
 * the shooter method to drive the robot.
 * 
 * Requires the shooter. Only command that can be running for the shooter.
 * 
 */
public class ShooterControlWithJoystick extends Command {

	private final double MAX_TILT_RATE = 0.2;
	private final double MAX_YAW_RATE = 1.5;
	private final double MAX_SPEED_RATE = 10;
	private final double MAX_SPEED = 5840;	// No load max rpm per Mini-CIM specs: 5840
	
	private double spdCmd;
	
	public ShooterControlWithJoystick() {
		requires(Robot.shooter);
		setInterruptible(false);
	}

	@Override
	protected void initialize() {
		System.out.println("ShooterControlWithJoystick");
		spdCmd = Robot.shooter.shooterGetSpeed();
		Robot.shooter.shooterStopFire();
	}

	@Override
	protected void execute() {
		SmartDashboard.putString("Control Mode", "Manual");
		// adjust tilt with left joystick Y axis
		double tiltCmd = Robot.shooter.tiltGetSetpoint();
		tiltCmd += Robot.oi.getYShooterStick() * MAX_TILT_RATE;
		
		
		// adjust tilt with right joystick X axis
		double yawCmd = Robot.shooter.yawGetSetpoint();
		yawCmd += Robot.oi.getXShooterStick() * MAX_YAW_RATE;
		
		
		// adjust tilt with triggers - left increases and right decreases speed
		
		spdCmd += Robot.oi.getLeftShooterTrigger() * MAX_SPEED_RATE;
		spdCmd -= Robot.oi.getRightShooterTrigger() * MAX_SPEED_RATE;
		spdCmd = Math.max(-MAX_SPEED, Math.min(0, spdCmd));
		
		/*if(Robot.oi.drive2Controller.getXButton())
		{
			spdCmd = Robot.shooter.getAimSpeed(OI.SHOOT_PRESET.WALL);
			tiltCmd = Robot.shooter.getAimAngle(OI.SHOOT_PRESET.WALL);
		}
		
		if(Robot.oi.drive2Controller.getYButton())
		{
			spdCmd = Robot.shooter.getAimSpeed(OI.SHOOT_PRESET.HOPPER);
			tiltCmd = Robot.shooter.getAimAngle(OI.SHOOT_PRESET.HOPPER);
		}
		
		if(Robot.oi.drive2Controller.getAButton())
		{
			spdCmd = Robot.shooter.getAimSpeed(OI.SHOOT_PRESET.KEY);
			tiltCmd = Robot.shooter.getAimAngle(OI.SHOOT_PRESET.KEY);
		}
		
		if(Robot.oi.drive2Controller.getBButton())
		{
			spdCmd = Robot.shooter.getAimSpeed(OI.SHOOT_PRESET.GEAR);
			tiltCmd = Robot.shooter.getAimAngle(OI.SHOOT_PRESET.GEAR);
		}*/
		
		if(Robot.oi.drive2Controller.getYButton())
		{
			spdCmd = Robot.shooter.getAimSpeed(OI.SHOOT_PRESET.WALL);
			tiltCmd = Robot.shooter.getAimAngle(OI.SHOOT_PRESET.WALL);
		}
		
		Robot.shooter.tiltSetAngle(tiltCmd);
		Robot.shooter.yawSetAngle(yawCmd);
		if(Robot.oi.drive2Controller.getBumper(Hand.kLeft))
		{
			Robot.shooter.shooterFire(spdCmd);
		}	
		else
		{
			Robot.shooter.shooterSpinup(spdCmd);
		}
//		if(Robot.oi.drive2Controller.getRawButton(RobotMap.LEFT_BUMPER))
//		{
//			//Robot.shooter.shooterFire(MAX_SPEED * -Robot.oi.getRightShooterTrigger());
//			Robot.shooter.shooterFire(SmartDashboard.getNumber("SpeedControl", 0));
//		}
//		else
//		{
//			Robot.shooter.shooterStopFire();
//			Robot.shooter.shooterSetSpeed(SmartDashboard.getNumber("SpeedControl", 0));
//		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.shooter.yawSetAngle(Robot.shooter.yawGetAngle());
	}

	@Override
	protected void interrupted() {
		System.out.println("ShooterControlWithJoystick: Interrupted.");
		Robot.shooter.yawSetAngle(Robot.shooter.yawGetAngle());
	}
}
