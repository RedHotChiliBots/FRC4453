
package org.usfirst.frc.team4453.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4453.robot.Robot;

/**
 *
 */
public class ToterWithJoystick extends Command {
	
	private static final double toterRate = 0.1;

    public ToterWithJoystick() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.toter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("ToterWithJoystick");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double pos = Robot.toter.toterGetSetPoint();
 		SmartDashboard.putNumber("toter SetPoint",pos);
 		double cmd = Robot.oi.getToterStick();
 		SmartDashboard.putNumber("toter Joystick",cmd);
 		
       	Robot.toter.toterSetSetpoint(pos + (cmd*toterRate));
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
