package org.usfirst.frc.team4453.robot.subsystems;

import org.usfirst.frc.team4453.robot.Robot;
import org.usfirst.frc.team4453.robot.RobotMap;
import org.usfirst.frc.team4453.robot.commands.DrivingWithJoysticks;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class Chassis extends PIDSubsystem {

	private static RobotDrive drive;
	
	private double speed = 1.0;
	
	private static CANTalon talonFrontLeftDrive;
	private static CANTalon talonBackLeftDrive;
	private static CANTalon talonFrontRightDrive;
	private static CANTalon talonBackRightDrive;
	
    // Initialize your subsystem here
    public Chassis() {
     	super("Chassis", 7.0, 0.0, 8.0);
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
     	
     	System.out.println("Chassis starting...");
     	
     	talonFrontLeftDrive  = new CANTalon (RobotMap.LEFT_FRONT_DRIVE_MOTOR);
		talonBackLeftDrive = new CANTalon (RobotMap.LEFT_BACK_DRIVE_MOTOR);
		talonFrontRightDrive = new CANTalon (RobotMap.RIGHT_FRONT_DRIVE_MOTOR);
		talonBackRightDrive = new CANTalon (RobotMap.RIGHT_BACK_DRIVE_MOTOR);
		
		drive = new RobotDrive(talonFrontLeftDrive,talonBackLeftDrive,talonFrontRightDrive,talonBackRightDrive);
		
		System.out.println("Chassis is running");
    }
    
    public void arcadeDrive(double leftCmd, double rightCmd) {
    	drive.arcadeDrive(leftCmd*speed, rightCmd*speed, true);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DrivingWithJoysticks());
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
    	return 0.0;
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    }
}
