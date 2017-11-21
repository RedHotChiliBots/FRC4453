/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc4453.subsystems;

import org.usfirst.frc4453.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc4453.commands.CommandBase;
import org.usfirst.frc4453.commands.DriveWithJoystick;


/**
 * Subsystem
 * The Chassis subsystem provides the basic drive function for the robot.
 * 
 * @author Developer
 */
public class Chassis extends PIDSubsystem {
	
	private static final double Kp = 3.0;
	private static final double Ki = 0.2;
	private static final double Kd = 0.0;

	private static final double Kgyro = 0.03;

    private static final double ENCODER_PULSES_PER_REV = 250.0;  // E4P-250-250
    private static final double WHEEL_DIAMETER = 6.0/12.0;  // 6" wheels converted to feet
    private static final double DRIVE_ENCODER_DIST_PER_PULSE = (WHEEL_DIAMETER * Math.PI) / ENCODER_PULSES_PER_REV;    // feet per cycle

	private static double driveDirection = 1.0;
	
	private final RobotDrive drive;

	private static Talon talonLeftDrive;
	private static Talon talonRightDrive;
    private static Encoder encoderLeftWheel;
    private static Encoder encoderRightWheel;

	public Chassis() {
		super("Chassis", Kp, Ki, Kd);

		talonLeftDrive = new Talon(RobotMap.LEFT_DRIVE_MOTOR);
		talonRightDrive = new Talon(RobotMap.RIGHT_DRIVE_MOTOR);
		drive = new RobotDrive(talonLeftDrive, talonRightDrive);
		
		drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
		drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
		drive.setSafetyEnabled(false);
		
		drive.tankDrive(0.0, 0.0);

        // Drive encoders
        encoderLeftWheel = new Encoder(RobotMap.LEFT_DRIVE_ENCODER_A, RobotMap.LEFT_DRIVE_ENCODER_B, true, EncodingType.k4X);
        encoderRightWheel = new Encoder(RobotMap.RIGHT_DRIVE_ENCODER_A, RobotMap.RIGHT_DRIVE_ENCODER_B, false, EncodingType.k4X);

        encoderLeftWheel.setMinRate(10);
        encoderLeftWheel.setDistancePerPulse(DRIVE_ENCODER_DIST_PER_PULSE);
        encoderLeftWheel.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
        encoderRightWheel.setMinRate(10);
        encoderRightWheel.setDistancePerPulse(DRIVE_ENCODER_DIST_PER_PULSE);
        encoderRightWheel.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
        encoderLeftWheel.start();
        encoderRightWheel.start();

		//setSetpoint(10.0); //  Sets where the PID controller should move the system to

		getPIDController().disable();

		LiveWindow.addSensor("Chassis", "Left Encoder", encoderLeftWheel);
		LiveWindow.addSensor("Chassis", "Left Encoder", encoderRightWheel);
		LiveWindow.addActuator("Chassis", "Left Motors", (Talon) talonLeftDrive);
		LiveWindow.addActuator("Chassis", "Right Motor", (Talon) talonRightDrive);

		SmartDashboard.putNumber("Encoder (left)", encoderGetLeftRate());
		SmartDashboard.putNumber("Encoder (right)", encoderGetRightRate());
		SmartDashboard.putString("Encoder Dir (left)", (encoderGetLeftDirection() ? "Forward" : "Reverse"));
		SmartDashboard.putString("Encoder Dir (right)", (encoderGetRightDirection() ? "Forward" : "Reverse"));
	}

	// Set the default command to drive with joysticks.
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveWithJoystick());
    }
    
    protected double returnPIDInput() {
        double m_dist;

        m_dist = CommandBase.sensors.sonarGetRange();
        SmartDashboard.putNumber("PID Input (feet)", m_dist);
     
        return m_dist;
    }

    protected void usePIDOutput(double output) {
    	SmartDashboard.putNumber("PID Output", output);
        drive.tankDrive(output, output);
    }

    /**
     * Drive forward in straight line using gyro.
     * Requires that the gyro is reset during command initialization.
     * <p>
     * Uses local variable (driveDirection) for determining drive direction
     * Uses local constant (kGyro) for limiting turn response
     * 
     * @param speed double - motor speed (-1.0 to +1.0)
     * @param angle double - gyro angle (deg)
     */
	public void driveStraight(double speed, double angle) {
		speed *= driveDirection;
		angle *= -Kgyro;
        drive.drive(speed, angle); // drive towards heading 0		
	}
	
	/**
	 * Drive using independent left and right motor commands.
	 * <p>
     * Uses local variable (driveDirection) for determining drive direction
     * 
	 * @param leftCmd double - motor speed (-1.0 to +1.0)
	 * @param rightCmd double - motor speed (-1.0 to +1.0)
	 */
	public void driveWithJoystick(double leftCmd, double rightCmd) {
		leftCmd *= driveDirection;
		rightCmd *= driveDirection;
		
		if (driveDirection == 1.0) {
			drive.tankDrive(leftCmd, rightCmd);
		} else {
			drive.tankDrive(rightCmd, leftCmd);			
		}
	}

	/**
	 * Commands robot to stop
	 */
	public void driveStop() {
		drive.tankDrive(0.0, 0.0);
	}
	
    public void encodersStart() {
        System.out.println("Starting encoders ...");
		encoderLeftWheel.start();
		encoderRightWheel.start();
    }
    
    public void encodersReset() {
        System.out.println("Reseting encoders ...");
		encoderLeftWheel.reset();
		encoderRightWheel.reset();
    }
    
    public void encodersStop() {
		encoderLeftWheel.stop();
		encoderRightWheel.stop();
    }

    public final double encoderGetLeftRate() {
		return encoderLeftWheel.getRate();
    }
    
    public final double encoderGetRightRate() {
		return encoderRightWheel.getRate();
    }
    
    public final double encoderGetLeftDist() {
		return encoderLeftWheel.getDistance();
    }
    
    public final double encoderGetRightDist() {
		return encoderRightWheel.getDistance();
    }
    
   public final boolean encoderGetLeftDirection() {
		return encoderLeftWheel.getDirection();
    }
    
    public final boolean encoderGetRightDirection() {
		return encoderRightWheel.getDirection();
	}
	
	public final void setDriveDirection(double dir) {
		driveDirection = dir;
	}
	
	public final double getDriveDirection() {
		return driveDirection;
	}
}
