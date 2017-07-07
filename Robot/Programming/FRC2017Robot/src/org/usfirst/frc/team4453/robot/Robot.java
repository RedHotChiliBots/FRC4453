
package org.usfirst.frc.team4453.robot;

import org.usfirst.frc.team4453.library.Vision;
import org.usfirst.frc.team4453.robot.commands.AutoBoilerAndGear;
import org.usfirst.frc.team4453.robot.commands.AutoDriveDistance;
import org.usfirst.frc.team4453.robot.commands.AutoGearCenter;
import org.usfirst.frc.team4453.robot.commands.AutoGearLeft;
import org.usfirst.frc.team4453.robot.commands.AutoGearRight;
import org.usfirst.frc.team4453.robot.commands.AutoNoOp;
import org.usfirst.frc.team4453.robot.commands.ShooterReset;
import org.usfirst.frc.team4453.robot.subsystems.Bubbler;
import org.usfirst.frc.team4453.robot.subsystems.Chassis;
import org.usfirst.frc.team4453.robot.subsystems.Climber;
import org.usfirst.frc.team4453.robot.subsystems.GearGrabber;
import org.usfirst.frc.team4453.robot.subsystems.Hopper;
import org.usfirst.frc.team4453.robot.subsystems.Shooter;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot {
	public static Preferences prefs;
	public static final String[] prefsList = {"gSafeDist", "gMinDist", "glAlignDist", "glAlignAngle", "grAlignDist", "grAlignAngle"};
	// subsystems
	public static Chassis chassis;
	public static Shooter shooter;
	public static Bubbler bubbler;
	public static GearGrabber geargrabber;
	public static Hopper hopper;
	public static Climber climber;
	// public static Accel accel;
	public static AHRS ahrs;
	public static OI oi;
	public static Triggers triggers;

	private static Command autonomousCommand;
	private static SendableChooser<Command> autoChooser;


	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		prefs = Preferences.getInstance();
		
		// initialize Sensors - do before subsystems to support references
		try {
//			ahrs = new AHRS(SPI.Port.kMXP, (byte) 200);
			ahrs = new AHRS(SPI.Port.kMXP); 
		}
		catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
		}
		NetworkTable.globalDeleteAll();
		
		// initialize Subsystems
		chassis = new Chassis();
		bubbler = new Bubbler();
		geargrabber = new GearGrabber();
		hopper = new Hopper();
		shooter = new Shooter(); // Requires hopper.
		climber = new Climber();
		// initialize OI after subsystems to guarantee any required subsystems are
		// initialized first
		oi = new OI();
		// initialize Triggers last since it expects oi to be initialized
		triggers = new Triggers();

		// display subsystems on dashboard
		SmartDashboard.putData(chassis);
		SmartDashboard.putData(bubbler);
		SmartDashboard.putData(geargrabber);
		SmartDashboard.putData(hopper);
		SmartDashboard.putData(shooter);

		// initialize autonomous command chooser //TODO add all the auto commands
		autoChooser = new SendableChooser<Command>();
		autoChooser.addDefault("NoOp", new AutoNoOp());
//		autoChooser.addObject("Hopper-Boiler", new AutoStratPrimary());
//		autoChooser.addObject("Baseline-Boiler", new AutoStratSecondary());
		autoChooser.addObject("GearLeft", new AutoGearLeft());
		autoChooser.addObject("GearRight", new AutoGearRight());
		autoChooser.addObject("GearCenter", new AutoGearCenter());
		autoChooser.addObject("Baseline", new AutoDriveDistance(8*12));
		autoChooser.addObject("Boiler And Gear", new AutoBoilerAndGear());
		SmartDashboard.putData("Auto Mode", autoChooser);
		autonomousCommand = null;

		// initialize Smartdashboard
		SmartDashboard.putString("Vison Status", "N/A");
		Vision.update();
		telemetry();
		
		for(int i = 0; i < prefsList.length; i++)
		{
			if(!prefs.containsKey(prefsList[i]))
			{
				prefs.putDouble(prefsList[i], 0);
			}
		}
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	@Override
	public void disabledInit() {
		chassis.stop();
		shooter.stop();
		bubbler.stop();
		
		shooter.initDefaultCommand();
		Scheduler.getInstance().removeAll();
	}

	@Override
	public void disabledPeriodic() {
		telemetry();
		Vision.update();
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		// schedule the autonomous command from chooser
		autonomousCommand = autoChooser.getSelected();
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		telemetry();
		Vision.update();
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
		chassis.disable();
		chassis.stop();
		shooter.stop();
		bubbler.stop();

		// ahrs.setYawOffset(Math.toRadians(SmartDashboard.getNumber("YawOffset", 0.0)));
		(new ShooterReset()).start();

	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		telemetry();
		Vision.update();
		Scheduler.getInstance().run();
	}

	/**
	 * 
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}

	public static void telemetry() {

		// NavX Info.
		SmartDashboard.putBoolean("IMU_Connected", ahrs.isConnected());
		SmartDashboard.putBoolean("IMU_IsCalibrating", ahrs.isCalibrating());
		SmartDashboard.putNumber("IMU_Yaw", ahrs.getYaw());
		SmartDashboard.putNumber("IMU_Pitch", ahrs.getPitch());
		SmartDashboard.putNumber("IMU_Roll", ahrs.getRoll());
		SmartDashboard.putNumber("IMU_CompassHeading", ahrs.getCompassHeading());
		SmartDashboard.putNumber("IMU_Accel_X", ahrs.getWorldLinearAccelX());
		SmartDashboard.putNumber("IMU_Accel_Y", ahrs.getWorldLinearAccelY());
		SmartDashboard.putBoolean("IMU_IsMoving", ahrs.isMoving());
		SmartDashboard.putBoolean("IMU_IsRotating", ahrs.isRotating());
		SmartDashboard.putNumber("Velocity_X", ahrs.getVelocityX());
		SmartDashboard.putNumber("Velocity_Y", ahrs.getVelocityY());
		SmartDashboard.putNumber("Displacement_X", ahrs.getDisplacementX());
		SmartDashboard.putNumber("Displacement_Y", ahrs.getDisplacementY());
		SmartDashboard.putNumber("IMU_Temp_C", ahrs.getTempC());
		
		// Pressure
		SmartDashboard.putNumber("Pneumatics Pressure", chassis.getPressure());
		
		// Shooter
		SmartDashboard.putNumber("Shooter Yaw (angle)", shooter.yawGetAngle());
		SmartDashboard.putNumber("Shooter Yaw (Raw)", shooter.yawEncoderPos());
		SmartDashboard.putNumber("Shooter Yaw Setpoint", shooter.yawGetSetpoint());
		SmartDashboard.putBoolean("Shooter Yaw limit", shooter.yawHitLimit());
		
		SmartDashboard.putNumber("Shooter Tilt (angle)", shooter.tiltGetAngle());
		SmartDashboard.putNumber("Shooter Tilt (dist)", shooter.tiltGetDist());
		SmartDashboard.putNumber("Shooter Tilt (Raw)", shooter.tiltEncoderPos());
		SmartDashboard.putNumber("Shooter Tilt Setpoint", shooter.tiltGetSetpoint());
		
		SmartDashboard.putNumber("Shooter RPM", shooter.shooterGetSpeed());
		SmartDashboard.putNumber("Shooter RPM Setpoint", shooter.shooterGetSetpoint());
		// Chassis
		SmartDashboard.putNumber("Chassis Distance Setpoint", chassis.getDistanceSetpoint());
		SmartDashboard.putNumber("Chassis Distance", chassis.getDistance());
		
		//Grabber
		SmartDashboard.putString("Grabber State", geargrabber.getGrabberState() == Value.kForward ? "CLOSED" : "OPEN");
		SmartDashboard.putString("Gear Lift State", geargrabber.getLiftState() == Value.kForward ? "UP" : "DOWN");
		
		// Hopper
		SmartDashboard.putNumber("Agitator Current Draw", hopper.getAgitatorCurrent());
		
		// Vision
		SmartDashboard.putString("Vision Status", Vision.getStatus());
		SmartDashboard.putString("Vision Remote Status", Vision.getStatusRemote());
		SmartDashboard.putString("Current Camera", Vision.getCurrentCamera());
		
		SmartDashboard.putBoolean("Boiler Found", Vision.boilerVisible());
		SmartDashboard.putNumber("Boiler Angle", Vision.getBoilerAngleOffset());
		SmartDashboard.putNumber("Boiler Dist", Vision.getBoilerDist());
		
		SmartDashboard.putBoolean("Gear Found", Vision.gearVisible());
		SmartDashboard.putNumber("Gear Angle", Vision.getGearAngleOffsetAdjusted());
		SmartDashboard.putNumber("Gear Dist", Vision.getGearDistAdjusted());
		
	}
}
