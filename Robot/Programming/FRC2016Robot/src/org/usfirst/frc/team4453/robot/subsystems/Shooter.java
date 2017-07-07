package org.usfirst.frc.team4453.robot.subsystems;

import org.usfirst.frc.team4453.library.Tilt;
import org.usfirst.frc.team4453.robot.RobotMap;
import org.usfirst.frc.team4453.robot.commands.TiltWithJoystick;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shooter extends PIDSubsystem {
	
	private static CANTalon shooterMaster;
	private static CANTalon shooterSlave;
	private static CANTalon tiltMaster;
	private static CANTalon tiltSlave;
	
	private static Encoder tiltEncoder;
	
	private static DigitalInput minLimit;
	
	private static boolean isReady = false;
	private static boolean autoTilt = false;
	
	private Compressor compressor;
	private DoubleSolenoid shooterSolenoid;

	private static final double Kp = 2.5;	//0.025;
	private static final double Ki = 0.0;
	private static final double Kd = 0.0;
	
	private static final double ENCODER_PULSES_PER_REV = 360.0;
	private static final double TILT_LEAD_SCREW_PITCH = 0.25;
	private static final double TILT_ENCODER_DIST_PER_PULSE = TILT_LEAD_SCREW_PITCH / ENCODER_PULSES_PER_REV;
	private static final double MIN_DIST = 0.0;
	private static final double MAX_DIST = Tilt.calcSide(50.0);
	
    // Initialize your subsystem here
    public Shooter() {
    	super("Shooter", Kp, Ki, Kd);
    	System.out.println("Shooter starting...");

        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    	
    	// Define shooter motors as Master and Slave where we control the
    	// master motor and the slave follows.
    	// The master motor runs in reverse.
    	// Note: Running the slave in reverse did not work
    	shooterMaster = new CANTalon(RobotMap.LEFT_SHOOTER_MOTOR);
    	shooterSlave = new CANTalon(RobotMap.RIGHT_SHOOTER_MOTOR);
    	shooterSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
    	shooterSlave.set(shooterMaster.getDeviceID());
    	shooterMaster.reverseOutput(true);

    	// Define tilt motors as Master and Slave where we control the
    	// master motor and the slave follows.
    	tiltMaster = new CANTalon(RobotMap.LEFT_TILT_MOTOR);
    	tiltSlave = new CANTalon(RobotMap.RIGHT_TILT_MOTOR);
    	tiltSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
    	tiltSlave.set(tiltMaster.getDeviceID());
    	tiltMaster.reverseOutput(false);
    	
    	// Define the low limit position switch
    	minLimit = new DigitalInput(RobotMap.SHOOTER_MIN_LIMIT);
     	
    	// Define the compressor and solenoid
    	// Note: by defining any solenoid the compressor is automatically enabled
	    compressor = new Compressor(RobotMap.COMPRESSOR);
	    shooterSolenoid = new DoubleSolenoid(RobotMap.SHOOTER_SOLENOID1ST, RobotMap.SHOOTER_SOLENOID2ND);

	    // Define the tilt encoder use to control the position of the shooter's tilt angle
	    // Configure encoder parameters - taken from example; commented out values are arbitrary
    	tiltEncoder = new Encoder( RobotMap.TILT_ENCODER_A, RobotMap.TILT_ENCODER_B, false, Encoder.EncodingType.k4X);
//    	tiltEncoder.setMaxPeriod(0.1);
//    	tiltEncoder.setMinRate(10);
    	tiltEncoder.setDistancePerPulse(TILT_ENCODER_DIST_PER_PULSE);
//    	tiltEncoder.setReverseDirection(true);
//    	tiltEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
//    	tiltEncoder.setSamplesToAverage(7);
     	tiltEncoder.reset();
    	
    	// Configure PID Controller, reset position, and enable
	    getPIDController().setContinuous(false);
	    getPIDController().setInputRange(MIN_DIST, MAX_DIST);	// about 0.0 and 6.6 inches
//	    getPIDController().setAbsoluteTolerance(5.0);	// did not work
	    getPIDController().setPercentTolerance(0.5);	// 0.5 / 100.0 * (max - min) = 0.033 inches
//	    getPIDController().setToleranceBuffer(3);		// average over three cycles for onTarget
//	    getPIDController().setOutputRange(-1.0, 1.0);
	    getPIDController().enable();

	    // Setup LiveWindow for Test Mode
		LiveWindow.addActuator("Tilt PID", "PID Subsystem", getPIDController());
		LiveWindow.addActuator("Shooter", "Master CIM", (CANTalon) shooterMaster);
		LiveWindow.addActuator("Shooter", "Slave CIM", (CANTalon) shooterSlave);
	    LiveWindow.addActuator("Shooter", "Compressor", compressor);
	    LiveWindow.addActuator("Shooter", "Solenoid", shooterSolenoid);

		LiveWindow.addActuator("Tilt", "Master CIM", (CANTalon) tiltMaster);
		LiveWindow.addActuator("Tilt", "Slave CIM", (CANTalon) tiltSlave);
	    LiveWindow.addSensor("Tilt", "Tilt Encoder", tiltEncoder);
	    LiveWindow.addSensor("Tilt", "Tilt Limit", minLimit);
	    	    		
    	System.out.println("Shooter is running");
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new TiltWithJoystick());
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
    	double dist = tiltGetDist();
    	
//    	SmartDashboard.putNumber("Encoder PPR ", ENCODER_PULSES_PER_REV);
//    	SmartDashboard.putNumber("Screw Pitch ", TILT_LEAD_SCREW_PITCH);
//    	SmartDashboard.putNumber("Encoder DistPP X1000", TILT_ENCODER_DIST_PER_PULSE*1000);

    	SmartDashboard.putNumber("Tilt Setpoint (angle) ", Tilt.calcAngle(getSetpoint()));
    	SmartDashboard.putNumber("Tilt Setpoint (dist)  ", getSetpoint());
	    SmartDashboard.putNumber("Tilt Encoder (Raw)", tiltEncoder.get());
	    SmartDashboard.putNumber("Tilt Encoder (Dist)", tiltGetDist());
	    SmartDashboard.putNumber("Tilt Encoder (Angle)", tiltGetAngle());
	    SmartDashboard.putNumber("Tilt Encoder (Rate)", tiltEncoder.getRate());
	    SmartDashboard.putString("Tilt Encoder Dir", (tiltEncoder.getDirection() ? "Forward" : "Reverse"));
	    SmartDashboard.putBoolean("Tilt Limit Switch", minLimit.get());

    	return dist;
    }
    
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    	SmartDashboard.putNumber("Tilt PID Output", -output);
    	tiltMaster.set(-output);
    }

    public double tiltGetDist() {
    	return tiltEncoder.getDistance();
    }
    
    public void tiltSetDist(double dist) {
    	getPIDController().setSetpoint(dist);
    }
    
    public double tiltGetAngle() {
    	return Tilt.calcAngle(tiltEncoder.getDistance());
    }
    
    public void tiltSetAngle(double angle) {
    	getPIDController().setSetpoint(Tilt.calcSide(angle));
    }
    
    public double tiltGetSetPoint() {
    	return getPIDController().getSetpoint();
    }
    
    public void tiltSetSetPoint(double setPoint) {
    	getPIDController().setSetpoint(setPoint);
     }
    
    
    public void tiltResetEncoder() {
	    tiltEncoder.reset();
    }
    
    public boolean tiltGetLowerLimit() {
	    return minLimit.get();
    }
    
    public void shooterFire() {
    	shooterMaster.set(1.0);
    }
    
    public void shooterCollect() {
    	shooterMaster.set(-0.5);
    }
    
    public void shooterStop() {
    	shooterMaster.set(0.0);
    }
    
    public void tiltLower() {
    	tiltMaster.set(1.0);
    }
    
    public void tiltRaise() {
    	tiltMaster.set(-1.0);
    }
    
    public void tiltStop() {
    	tiltMaster.set(0.0);
    }
    
    public void tiltReset() {
    	while (! tiltGetLowerLimit()) {
    		tiltLower();
    	}
    	
    	tiltStop();
    	Timer.delay(1.0);	// allow motor to stop
    	tiltResetEncoder();
    	getPIDController().enable();	// enable toter PID controller
    	tiltSetDist(0.0);
    }

    public void solenoidFire() {
    	shooterSolenoid.set(DoubleSolenoid.Value.kForward);	
    }
    
    public void solenoidReset() {
    	shooterSolenoid.set(DoubleSolenoid.Value.kReverse);	
    }
    
    public void solenoidClear() {
    	shooterSolenoid.set(DoubleSolenoid.Value.kOff);	
    }
    
    public boolean getIsReady() {
    	return isReady;
    }
    
    public void setIsReady(boolean b) {
    	isReady = b;
    }
    
    public boolean getAutoTilt() {
    	return autoTilt;
    }
    
    public void setAutoTilt(boolean b) {
    	autoTilt = b;
    }
}
