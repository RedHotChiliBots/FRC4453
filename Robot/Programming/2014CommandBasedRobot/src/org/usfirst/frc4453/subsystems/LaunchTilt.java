/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc4453.subsystems;

//import com.sun.squawk.util.MathUtils;
import org.usfirst.frc4453.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc4453.Library;
import org.usfirst.frc4453.commands.TiltSetAngle;

/***
 * Trig equations
 * http://www.mathsisfun.com/algebra/trig-solving-triangles.html
 * 
 * @author Developer
 */
public class LaunchTilt extends PIDSubsystem {
	
	private static final double Kp = 0.5;
	private static final double Ki = 0.0;
	private static final double Kd = 0.0;

//	private static final double TILT_TRI_SIDE_A = 12+(19/32);	// from pivot to base mount
//	private static final double TILT_TRI_SIDE_B = 12+(19/32);	// from pivot to slide mount
//	private static final double TILT_TRI_FACTOR_1 = MathUtils.pow(TILT_TRI_SIDE_A,2) + MathUtils.pow(TILT_TRI_SIDE_B,2);
//	private static final double TILT_TRI_FACTOR_2 = 2 * TILT_TRI_SIDE_A * TILT_TRI_SIDE_B;
	
    private static final double ENCODER_PULSES_PER_REV = 360.0;  // E4P-360-250
    private static final double TILT_LEAD_SCREW_PITCH = 0.70;  // 0.070 inches per turn
    public static final double TILT_ENCODER_DIST_PER_PULSE = TILT_LEAD_SCREW_PITCH / ENCODER_PULSES_PER_REV;	// inches
    public static final double MIN_ANGLE = 0.0;	// degrees
    public static final double MAX_ANGLE = 60.0;	// degrees
    
    public static final double
    	RESET_TILT = 0.0 * MAX_ANGLE,
    	HALF_TILT = 32.0,  //0.5 * MAX_ANGLE,
    	FULL_TILT = 1.0 * MAX_ANGLE;
    
	private static Victor victorTiltMotors;
    private static Encoder encoderTiltScrew;

	private double tiltMotorDirection = 1.0;
	
	public LaunchTilt() {
		super("LaunchTilt", Kp, Ki, Kd);

		System.out.println("LaunchTilt constructor ...");
		
		victorTiltMotors = new Victor(RobotMap.TILT_MOTORS);

        // Launch encoder
		encoderTiltScrew = new Encoder(RobotMap.TILT_ENCODER_A, RobotMap.TILT_ENCODER_B, false, EncodingType.k4X);

		//encoderTiltScrew.setMinRate(10);
		encoderTiltScrew.setDistancePerPulse(TILT_ENCODER_DIST_PER_PULSE);
		encoderTiltScrew.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
		encoderTiltScrew.start();
        
		setAbsoluteTolerance(0.05);
		getPIDController().setContinuous(false);
		setInputRange(Library.calcSide(MIN_ANGLE),Library.calcSide(MAX_ANGLE));		
		
		setAngle(RESET_TILT); //  Sets where the PID controller should move the system to
		
		getPIDController().enable();
		
		SmartDashboard.putNumber("Encoder (tilt)", getAngle());
		SmartDashboard.putString("Encoder Dir (tilt)", (encoderGetDirection() ? "Forward" : "Reverse"));

		LiveWindow.addSensor("LaunchTilt", "Tilt Encoder", encoderTiltScrew);
		LiveWindow.addActuator("LaunchTilt", "Tilt Motor", (Victor) victorTiltMotors);

		System.out.println("LaunchTilt leaving constructor.");
	}

	// 
    protected void initDefaultCommand() {
        setDefaultCommand(new TiltSetAngle());
        //setDefaultCommand(new TiltWithJoystick());
    }
    
    protected double returnPIDInput() {
        double m_dist;

        m_dist = getDist();
        SmartDashboard.putNumber("Tilt PID Input (deg)", Library.calcAngle(m_dist));
        SmartDashboard.putNumber("Tilt PID Set (deg)", Library.calcAngle(tiltGetSetPoint()));
     
        return m_dist;
    }

    protected void usePIDOutput(double output) {
		output *= tiltMotorDirection;
    	SmartDashboard.putNumber("Tilt PID Output", output);
		victorTiltMotors.pidWrite(output);
    }

//    public static double calcSide(double angle) {
//    	return (Math.sqrt(TILT_TRI_FACTOR_1 - (TILT_TRI_FACTOR_2 * Math.cos(Math.toRadians(angle)))));
//    }
    
//    public static double calcAngle(double dist) {
//    	return (Math.toDegrees(MathUtils.acos((TILT_TRI_FACTOR_1 - MathUtils.pow(dist,2)) / TILT_TRI_FACTOR_2)));
//    }

    public final double getAngle() {
		return Library.calcAngle(encoderTiltScrew.getDistance());
    }
    
    public final void setAngle(double angle) {
    	getPIDController().setSetpoint(Library.calcSide(angle));
    }

    public final double getDist() {
		return encoderTiltScrew.getDistance();
    }
    
    public final void setDist(double dist) {
    	getPIDController().setSetpoint(dist);
    }

    public final double tiltGetSetPoint() {
    	return getPIDController().getSetpoint();
    }

    public void encoderStart() {
        System.out.println("Starting encoders ...");
        encoderTiltScrew.start();
    }
    
    public void encoderReset() {
        System.out.println("Reseting encoders ...");
        encoderTiltScrew.reset();
    }
    
    public void encoderStop() {
    	encoderTiltScrew.stop();
    }
	
	public final boolean encoderGetDirection() {
		return encoderTiltScrew.getDirection();
    }

	public void tiltWithJoystick(double cmd) {
		victorTiltMotors.set(cmd);
	}

	public void reverseTiltMotor(boolean dir) {
		tiltMotorDirection = (dir ? -1.0 : 1.0);
	}
	
	public boolean isReverseTiltMotor() {
		return (tiltMotorDirection == -1.0);
	}
}
