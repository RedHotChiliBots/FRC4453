
package org.usfirst.frc.team4453.robot.subsystems;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
//import edu.wpi.first.wpilibj.CounterBase.EncodingType;
//import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
//import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team4453.library.Kalman;
import org.usfirst.frc.team4453.robot.Robot;
import org.usfirst.frc.team4453.robot.RobotMap;
import org.usfirst.frc.team4453.robot.commands.DriveWithJoystick;
/**
 *
 */
public class Chassis extends PIDSubsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private static RobotDrive drive;
	
	private static Talon talonFrontLeftDrive;
	private static Talon talonBackLeftDrive;
	private static Talon talonFrontRightDrive;
	private static Talon talonBackRightDrive;
	
	//private Encoder rightEncoder;
	//private Encoder leftEncoder;

	private static Gyro gyro;
	private static BuiltInAccelerometer accel;

	private static Kalman gyroFilter;
	
	public Chassis() {
     	super("Chassis", 7.0, 0.0, 8.0);
       	System.out.println("Chassis starting...");
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
        
       	talonFrontLeftDrive  = new Talon (RobotMap.FRONT_LEFT_DRIVE_MOTOR);
		talonBackLeftDrive = new Talon (RobotMap.BACK_LEFT_DRIVE_MOTOR);
		talonFrontRightDrive = new Talon (RobotMap.FRONT_RIGHT_DRIVE_MOTOR);
		talonBackRightDrive = new Talon (RobotMap.BACK_RIGHT_DRIVE_MOTOR);
		
		drive = new RobotDrive(talonFrontLeftDrive,talonBackLeftDrive,talonFrontRightDrive,talonBackRightDrive);

		drive.setMaxOutput(0.5);

		drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
		drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
		drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
		drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
		
		LiveWindow.addActuator("Chassis", "Front Left CIM", (Talon) talonFrontLeftDrive);
		LiveWindow.addActuator("Chassis", "Front Right CIM", (Talon) talonFrontRightDrive);
		LiveWindow.addActuator("Chassis", "Back Left CIM", (Talon) talonBackLeftDrive);
		LiveWindow.addActuator("Chassis", "Back Right CIM", (Talon) talonBackRightDrive);
		
		// Configure encoders
		//rightEncoder = new Encoder(RobotMap.DRIVE_RIGHT_ENCODER1,RobotMap.DRIVE_RIGHT_ENCODER2, true, EncodingType.k4X);
		//leftEncoder = new Encoder(RobotMap.DRIVE_LEFT_ENCODER1,RobotMap.DRIVE_LEFT_ENCODER2, false, EncodingType.k4X);
		//rightEncoder.setPIDSourceParameter(PIDSourceParameter.kDistance);
		//leftEncoder.setPIDSourceParameter(PIDSourceParameter.kDistance);

		//if (Robot.isReal()) { // Converts to feet
			//rightEncoder.setDistancePerPulse(0.0785398);
			//leftEncoder.setDistancePerPulse(0.0785398);
		//} else { // Convert to feet 6in diameter wheels with 360 tick simulated encoders
			//rightEncoder.setDistancePerPulse((6.0/*in*/*Math.PI)/(360.0*12.0/*in/ft*/));
			//leftEncoder.setDistancePerPulse((6.0/*in*/*Math.PI)/(360.0*12.0/*in/ft*/));
		//}

		//LiveWindow.addSensor("Chassis", "Right Encoder", rightEncoder);
		//LiveWindow.addSensor("Chassis", "Left Encoder", leftEncoder);

		// Configure gyro
		gyro = new Gyro(RobotMap.GYRO);
		if (Robot.isReal()) {
			gyro.setSensitivity(0.007); // TODO: Handle more gracefully?
			Timer.delay(3.0);	// Wait for gyro calibration
			gyro.reset();
		}
		LiveWindow.addSensor("Chassis", "Gyro", gyro);
		
		gyroFilter = new Kalman(0.13, 10.0);

		accel = new BuiltInAccelerometer(BuiltInAccelerometer.Range.k4G); 
		LiveWindow.addSensor("Chassis", "Accelerometer", accel);
		//double xVal = accel.getX();
		//double yVal = accel.getY();
		//double zVal = accel.getZ();
				
       	System.out.println("Chassis is running.");
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DriveWithJoystick());
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
    	return 0.0;
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
    	drive.mecanumDrive_Cartesian(output, 0.0, 0.0, this.gyroGetAngle());
//+0    	drive.mecanumDrive_Cartesian(0.0, output, 0.0, this.gyroGetAngle());
    }
    
    public void driveWithJoystick( double leftXCmd,  double leftYCmd, double rightXCmd) {
    	drive.mecanumDrive_Cartesian(leftXCmd, leftYCmd, rightXCmd, 0.0); //, this.gyroGetAngle());
    }
    
	/**
	 * Stop the drivetrain from moving.
	 */
	public void stop() {
		drive.mecanumDrive_Cartesian(0,0,0,0);
	}

	public double gyroGetAngle() {
		return gyro.getAngle();
	}
	
	public double gyroGetFilteredAngle() {
		return gyroFilter.kalmanUpdate(gyro.getAngle());
	}

	public double accelGetX() {
		return accel.getX();
	}
	
	public double accelGetY() {
		return accel.getY();
	}
	
	public double accelGetZ() {
		return accel.getZ();
	}
}

