package org.usfirst.frc.team4453.robot.subsystems;

import org.usfirst.frc.team4453.robot.RobotMap;
import org.usfirst.frc.team4453.robot.commands.ToterWithJoystick;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.Talon;

/**
 *
 */
public class Toter extends PIDSubsystem {

	private static Talon toterMotor;
	private static Encoder toterEncoder;
	private static DigitalInput toterLimit;
	
	private static final double Kp = 1.0;
	private static final double Ki = 0.0;
	private static final double Kd = 0.0;
	
	private static final double SPROCKET_DIA = 1.0 + 5.0/16.0;	// inches 
	private static final double PULSE_PER_REV = 1440;	// pulses 
	private static final double DIST_PER_PULSE = (Math.PI * SPROCKET_DIA) / PULSE_PER_REV;	// inches per pulse
	
	private static final double scorePos = 2.25;
	private static final double stepPos = 6.625;
	
	private static final double resetPos = 3.0;
	private static final double [] toterPosition = {
		10.0-resetPos,
		22.5-resetPos,
		35.0-resetPos,
		47.5-resetPos,
		60.0-resetPos,
		72.5-resetPos};
	private static int index = 0;
	
	private static double toterMotorDirection = 1.0;	// Init to forward; set to -1 for reverse
	private static double toterMaxSpeed = 0.5;	// 
	
    // Initialize your subsystem here
    public Toter() {
    	super("Toter", Kp, Ki, Kd);
       	System.out.println("Toter starting...");

       	setAbsoluteTolerance(0.05);
    	getPIDController().setContinuous(false);	// not circular
    	getPIDController().setInputRange(0.0,72.0);	// min inches, max inches
    	getPIDController().setOutputRange(-toterMaxSpeed,toterMaxSpeed);	// min motor, max motor
    	
    	toterMotor = new Talon(RobotMap.TOTER_MOTOR);
    	toterMotor.setSafetyEnabled(false);
    	this.setDirection(-1.0);
		LiveWindow.addActuator("Toter", "Mini CIMs", (Talon) toterMotor);

    	toterEncoder = new Encoder(RobotMap.TOTER_ENCODER1,RobotMap.TOTER_ENCODER2,false,EncodingType.k4X);
    	toterEncoder.setPIDSourceParameter(PIDSourceParameter.kDistance);
    	toterEncoder.setDistancePerPulse(DIST_PER_PULSE);
    	toterEncoder.setReverseDirection(true);
    	toterEncoder.reset();
    	LiveWindow.addSensor("Toter", "Encoder", toterEncoder);
    	
    	toterLimit = new DigitalInput(RobotMap.TOTER_LIMIT);
    	LiveWindow.addSensor("Toter", "Limit Switch", toterLimit);

    	this.enableToterPID();
    	
       	System.out.println("Toter is running");
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ToterWithJoystick());
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
    	double dist = toterEncoder.getDistance();
        SmartDashboard.putNumber("Toter PID Input ", dist);
        SmartDashboard.putNumber("Toter PID Set ", toterGetSetPoint());

        return dist;
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
    	output *= toterMaxSpeed * toterMotorDirection;
    	SmartDashboard.putNumber("Toter PID Output", output);
        toterMotor.pidWrite(output);
    }
    
    public void enableToterPID() {
    	getPIDController().enable();
    }
    
    public void disableToterPID() {
    	getPIDController().disable();
    }
    
    /*
     * 
     */
    public void toterSetSetpoint(double cmd) {
    	//TODO Use PID instead of joystick
    	//TODO Check for high limit and stop
    	this.setSetpoint(cmd);
    }
    
    public double toterGetSetpoint() {
    	//TODO Use PID instead of joystick
    	//TODO Check for high limit and stop
    	return this.getSetpoint();
    }

    public void setDirection(double dir) {
    	toterMotorDirection = dir;
    }
    
    public double getMotorDirection() {
    	return toterMotorDirection;
    }
 
    public void forwardToter() {
    	double speed = toterMaxSpeed * toterMotorDirection;
    	System.out.println("toter.forwardToter: " + speed);
    	toterMotor.set(speed);
    }

    public void reverseToter() {
    	double speed = -toterMaxSpeed * toterMotorDirection;
    	System.out.println("toter.reverseToter: " + speed);
    	toterMotor.set(speed);
    }

    public void stopToter() {
    	toterMotor.set(0.0);
    }
    
    public void resetToterMotor() {
    	System.out.println("toter.resetToterMotor");
    	    while (! this.toterGetLimit()) {
    		this.reverseToter();
    		try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
    	    }
    	this.stopToter();
    	
    	this.resetToterEncoder();
    	
    	System.out.println("Toter at Position "+toterPosition[index]);
    	index=0;
    	setSetpoint(toterPosition[index]);
    }
    
    public double nextToterPosition(){
    	if (index < 5){
    		index++;
    	}
    	double pos = toterPosition[index];
    	
 		SmartDashboard.putNumber("Toter position index",index + 1);
    	return pos;
    }
    
    public void initToterPos(){
    	index = 0;
 		SmartDashboard.putNumber("Toter position index",index + 1);
    	setSetpoint(toterPosition[index]);
    }
    
    public double addScorePosition(){
    	return getSetpoint() + scorePos;
    }
    
    public double addStepPosition(){
    	return getSetpoint() + stepPos;
    }
    
    /*
     * 
     */
    public void resetToterEncoder() {
    	toterEncoder.reset();
    	setSetpoint(0.0);
    	}
    
    public double getToterPosition() {
    	return toterEncoder.getDistance();
    }

    public int getToterCount() {
    	return toterEncoder.get();
    }

    public boolean isToterStopped() {
    	return toterEncoder.getStopped();
    }
      
    /*
     * 
     */
    public boolean toterGetLimit() {
    	return !toterLimit.get();
    }
    
    public double toterGetSetPoint() {
    	return getSetpoint();
    }
    
    public double encoderGetDistance() {
    	return toterEncoder.getDistance();
    }
    
    public double encoderGetRate() {
    	return toterEncoder.getRate();
    }
    
    public boolean encoderGetDirection() {
    	return toterEncoder.getDirection();
    }
}
