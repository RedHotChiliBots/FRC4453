
package org.usfirst.frc.team4453.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4453.robot.Robot;

/**
 * DriveWithjoystick - gets commands from the joysticks defined in OI 
 * to send to the shooter method to drive the robot.
 * 
 * Requires the shooter. Only command that can be running for the shooter.
 * 
 */
public class TiltWithJoystick extends Command {

	private final double LAUNCH_TILT_MAX_RATE = 0.05;
	
    private double tiltCmd;

    public TiltWithJoystick() {
        // Is primary command for shooter. No other command can "require" the shooter.
        requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("TiltWithJoystick");
    }

	protected void execute() {
		//System.out.println("from joystick");
		tiltCmd = Robot.shooter.getSetpoint();
				//tiltGetDist();
		//System.out.print("TiltWithJoystick: Initial tiltCmd = "+tiltCmd);

    	double yCmd = Robot.oi.getRightYShooterStick();
//        double zCmd = Robot.oi.getThrottleShooter();

        // Use throttle for testing then change to constant
        // to avoid over-shooting and under-shooting during matches
       
//		tiltCmd += (yCmd * zCmd);
		tiltCmd += (yCmd * LAUNCH_TILT_MAX_RATE);

		//System.out.println(" Final tiltCmd = "+tiltCmd);
		
		//System.out.println("throttle value" +(zCmd));
		//System.out.println("joystick value" +(yCmd));
		//System.out.println("tilt value" +(yCmd * zCmd));

		Robot.shooter.setSetpoint(tiltCmd);
				//tiltSetDist(tiltCmd);
	}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
