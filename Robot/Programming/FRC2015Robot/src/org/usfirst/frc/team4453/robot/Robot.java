
package org.usfirst.frc.team4453.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4453.robot.commands.DriveWithJoystick;
import org.usfirst.frc.team4453.robot.commands.ResetToterMotor;
import org.usfirst.frc.team4453.robot.subsystems.Can;
import org.usfirst.frc.team4453.robot.subsystems.Chassis;
import org.usfirst.frc.team4453.robot.subsystems.Plunger;
import org.usfirst.frc.team4453.robot.subsystems.Tatrow;
import org.usfirst.frc.team4453.robot.subsystems.Toter;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    private static Command autonomousCommand;
	private static Command resetToterCmd;
//	private static boolean initOneTime = true;

	public static OI oi;
	public static Chassis chassis;
	public static Toter toter;
	public static Tatrow tatrow;
	public static Plunger plunger;
	public static Can can;

	public SendableChooser autoChooser;

	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	System.out.println("Robot starting...");
 
 		SmartDashboard.putString("disabledInit", "");
 		SmartDashboard.putString("disabledPeriodic", "");
 		SmartDashboard.putString("robotInit", "");
 		SmartDashboard.putString("ResetToterMotor", "");

        // instantiate subsystems
    	chassis = new Chassis();
    	toter = new Toter();
    	tatrow = new Tatrow();
    	plunger = new Plunger();
    	can = new Can();

    	
    	// create subsystem data blocks
    	SmartDashboard.putData(chassis);
    	SmartDashboard.putData(toter);
    	SmartDashboard.putData(tatrow);
    	SmartDashboard.putData(plunger);
    	SmartDashboard.putData(can);

    	
		// This MUST be here. If the OI creates Commands (which it very likely
		// will), constructing it during the construction of CommandBase (from
		// which commands extend), subsystems are not guaranteed to be
		// yet. Thus, their requires() statements may grab null pointers. Bad
		// news. Don't move it.
		oi = new OI();

		// instantiate the command used for the autonomous period
		autoChooser = new SendableChooser();
		autoChooser.addDefault("Drive with Joystick", new DriveWithJoystick());
		autoChooser.addObject("Reset Toter", new ResetToterMotor());
		SmartDashboard.putData("Auto Mode", autoChooser);
        // instantiate the command used for the autonomous period
        //autonomousCommand = new DriveWithJoystick();
		
		// Initialize subsystems
    	// reverse toter motor until reaching limit switch, then stop
		//toter.resetToterMotor();
		
 		SmartDashboard.putString("robotInit", "here");

//		resetToterCmd = (Command) new ResetToterMotor();
//    	resetToterCmd.start();
    	
    	System.out.println("Robot is running.");
    }
	
    public void autonomousInit() {
        // schedule the autonomous command
		autonomousCommand = (Command) autoChooser.getSelected();
        if (autonomousCommand != null) {
        	autonomousCommand.start();
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        display();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
        	autonomousCommand.cancel();
        }
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        display();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){
 		SmartDashboard.putString("disabledInit", "here");
		resetToterCmd = (Command) new ResetToterMotor();
    	resetToterCmd.start();
    }

	public void disabledPeriodic() {
 		SmartDashboard.putString("disabledPeriodic", "here");
//		System.out.println("Disabled");
//		if (initOneTime){
//			Robot.toter.resetToterMotor();
//	    	initOneTime = false;
//		}
		Scheduler.getInstance().run();
	}

    /**
     * This function is called periodically during test mode
     */
    public void testInit() {
    }
    
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    private void display() {
        // Display sensor values on dashboard
// 		SmartDashboard.putNumber("Sonar", CommandBase.sensors.sonarGetRange());
 		SmartDashboard.putNumber("Gyro", Robot.chassis.gyroGetAngle());
 		SmartDashboard.putNumber("Gyro Filtered", Robot.chassis.gyroGetFilteredAngle());
 		SmartDashboard.putNumber("Accel X", Robot.chassis.accelGetX());
 		SmartDashboard.putNumber("Accel Y", Robot.chassis.accelGetY());
 		SmartDashboard.putNumber("Accel Z", Robot.chassis.accelGetZ());
 		SmartDashboard.putNumber("Encoder Rate (toter)", Robot.toter.encoderGetRate());
 		SmartDashboard.putNumber("Encoder Dist (toter)", Robot.toter.encoderGetDistance());
 		SmartDashboard.putString("Encoder Dir  (toter)", (Robot.toter.encoderGetDirection() ? "Forward" : "Reverse"));

    }
}
