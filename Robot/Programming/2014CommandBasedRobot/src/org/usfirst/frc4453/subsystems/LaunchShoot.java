/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc4453.subsystems;

import org.usfirst.frc4453.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc4453.commands.ShootSetDistance;

/***
 * @author Developer
 */
public class LaunchShoot extends PIDSubsystem {
	
	private static final double Kp = 0.5;
	private static final double Ki = 0.0;
	private static final double Kd = 0.0;

    private static final double ENCODER_PULSES_PER_REV = 360.0;  // E4P-360-250
//    private static final double LAUNCH_ROPE_DIAMETER = 0.125;  // 1/8" rope
    private static final double LAUNCH_ROPE_DIAMETER = 0.0;  // 1/8" rope
    private static final double LAUNCH_AXLE_DIAMETER = 0.5;  // 1/2" axle
    private static final double LAUNCH_GEAR_RATIO = 1/1;  // 1:1
	public static final double LAUNCH_ENCODER_DIST_PER_PULSE = (((LAUNCH_AXLE_DIAMETER+LAUNCH_ROPE_DIAMETER) * Math.PI) * LAUNCH_GEAR_RATIO) / ENCODER_PULSES_PER_REV;    // feet per cycle

	public static final double MIN_STRETCH = 0.0;	// inches
    public static final double MAX_STRETCH = 21.0;	// inches
    
    public static final double
    	RESET_STRETCH = 0.0 * MAX_STRETCH,
    	HALF_STRETCH = 0.5 * MAX_STRETCH,
    	FULL_STRETCH = 1.0 * MAX_STRETCH;
    
	private static Victor victorLaunchMotors;
    private static Encoder encoderLaunchAxle;
	private static Relay relayLaunchTrigger;

	private double shootMotorDirection = 1.0;
	
	public LaunchShoot() {
		super("LaunchShoot", Kp, Ki, Kd);

		System.out.println("Launch Motor");
		victorLaunchMotors = new Victor(RobotMap.SHOOT_MOTORS);

		// Launch Trigger
		System.out.println("Launch Trigger");
		relayLaunchTrigger = new Relay(RobotMap.SHOOT_TRIGGER);
		relayLaunchTrigger.setDirection(Relay.Direction.kBoth);
		//resetShooterTrigger();
		
        // Launch encoder
		System.out.println("Launch Encoder");
        encoderLaunchAxle = new Encoder(RobotMap.SHOOT_ENCODER_A, RobotMap.SHOOT_ENCODER_B, false, EncodingType.k4X);

        //encoderLaunchAxle.setMinRate(10);
		System.out.println("Launch Encoder");
        encoderLaunchAxle.setDistancePerPulse(LAUNCH_ENCODER_DIST_PER_PULSE);
        encoderLaunchAxle.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
        encoderLaunchAxle.start();

        // Initialize PID Controller
		System.out.println("PID Controller");		
		setAbsoluteTolerance(0.05);
		getPIDController().setContinuous(false);
		setInputRange(MIN_STRETCH,MAX_STRETCH);
		
		launchSetPoint(RESET_STRETCH); //  Sets where the PID controller should move the system to
		
		getPIDController().enable();
		
		LiveWindow.addSensor("LaunchShoot", "Launch Encoder", encoderLaunchAxle);
		LiveWindow.addActuator("LaunchShoot", "Launch Motor", (Victor) victorLaunchMotors);

		SmartDashboard.putNumber("Encoder (shoot)", getDist());
		SmartDashboard.putString("Encoder Dir (shoot)", (encoderGetDirection() ? "Forward" : "Reverse"));
	}

	// Set the default command to drive with joysticks.
    protected void initDefaultCommand() {
        setDefaultCommand(new ShootSetDistance());
        //setDefaultCommand(new ShootWithJoystick());
    }
    
    protected double returnPIDInput() {
        double m_dist;

        m_dist = getDist();
        SmartDashboard.putNumber("Shoot PID Input (inches)", m_dist);
        SmartDashboard.putNumber("Shoot PID Set (inches)", launchGetSetPoint());
     
        return m_dist;
    }

    protected void usePIDOutput(double output) {
		output *= shootMotorDirection;
    	SmartDashboard.putNumber("Shoot PID Output", output);
		victorLaunchMotors.pidWrite(output);
    }

    public void encoderStart() {
        System.out.println("Starting encoders ...");
        encoderLaunchAxle.start();
    }
    
    public void encoderReset() {
        System.out.println("Reseting encoders ...");
        encoderLaunchAxle.reset();
    }
    
    public void encoderStop() {
    	encoderLaunchAxle.stop();
    }

    public final double getDist() {
		return encoderLaunchAxle.getDistance();
    }
    
    public final void launchSetPoint(double dist) {
    	getPIDController().setSetpoint(dist);
    }
    
    public final double launchGetSetPoint() {
    	return getPIDController().getSetpoint();
    }

    public final boolean encoderGetDirection() {
		return encoderLaunchAxle.getDirection();
    }

	public void holdShooterTrigger() {
		relayLaunchTrigger.set(Relay.Value.kForward);
	}
	
	public void fireShooterTrigger() {
		relayLaunchTrigger.set(Relay.Value.kOff);
	}
	
	public void resetShooterTrigger() {
		relayLaunchTrigger.set(Relay.Value.kOff);
	}

	public Relay.Value getShooterTrigger() {
		return relayLaunchTrigger.get();
	}
	
	public void shootWithJoystick(double cmd) {
		victorLaunchMotors.set(cmd);
	}

	public void reverseShootMotor(boolean dir) {
		shootMotorDirection = (dir ? -1.0 : 1.0);
	}
	
	public boolean isReverseShootMotor() {
		return (shootMotorDirection == -1.0);
	}
}
