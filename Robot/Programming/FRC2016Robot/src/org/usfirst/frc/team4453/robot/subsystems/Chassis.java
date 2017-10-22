package org.usfirst.frc.team4453.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4453.robot.Robot;
import org.usfirst.frc.team4453.robot.RobotMap;
import org.usfirst.frc.team4453.robot.commands.DriveWithJoystick;

/**
 *
 */
public class Chassis extends PIDSubsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public boolean arcadeDrive = false;
	public boolean tankDrive = false;
	public boolean halfSpeed = false;
	private double speed = 1.0;
	private double vel = 0.75;	// used with PID controller; set by command
	
    private static RobotDrive drive;
    
	private static CANTalon leftFrontDriveTalon;
	private static CANTalon leftBackDriveTalon;
	private static CANTalon rightFrontDriveTalon;
	private static CANTalon rightBackDriveTalon;
	
	private static boolean autoTurn = false;
	
	private Encoder chassisEncoder;
	//private Encoder leftEncoder;
	//private Encoder rightEncoder;
	//private final double wheelDia  = 8.0;
	//private final double wheelCirc = Math.PI * wheelDia;
	//private final int ppr = 1440;
	//private final double distPerPulse = wheelCirc / ppr;

	private static final double Kp = 0.2;	//0.025;
	private static final double Ki = 0.0;
	private static final double Kd = 0.0;

	public Chassis() {
    	super("Chassis", Kp, Ki, Kd);
       	System.out.println("Chassis starting...");

       	//chassisEncoder = new Encoder(RobotMap.CHASSIS_ENCODER1,
       	//							 RobotMap.CHASSIS_ENCODER2,
       	//							 false,
       	//							 EncodingType.k4X);
       	
       	//chassisEncoder.setDistancePerPulse(distPerPulse);
       	
       	//removing for now
    	getPIDController().setContinuous(true);	// circular
    	getPIDController().setInputRange(0.0,360.0);	// min / max heading in degrees
	    getPIDController().setAbsoluteTolerance(0.25);
//	    getPIDController().setPercentTolerance(0.1);
//	    getPIDController().setToleranceBuffer(3);		// average over three cycles for onTarget
//    	getPIDController().setOutputRange(-0.5,0.5);	// min motor, max motor

    	// Use these to get going:
    	getPIDController().setSetpoint(Robot.ahrs.getCompassHeading());	// Sets where the PID controller should move the system
//        getPIDController().enable();		// Enables the PID controller.
        
       	leftFrontDriveTalon  = new CANTalon (RobotMap.LEFT_FRONT_DRIVE_MOTOR);
       	leftBackDriveTalon   = new CANTalon (RobotMap.LEFT_BACK_DRIVE_MOTOR);
       	rightFrontDriveTalon = new CANTalon (RobotMap.RIGHT_FRONT_DRIVE_MOTOR);
       	rightBackDriveTalon  = new CANTalon (RobotMap.RIGHT_BACK_DRIVE_MOTOR);

		
		drive = new RobotDrive(leftFrontDriveTalon, leftBackDriveTalon, rightFrontDriveTalon, rightBackDriveTalon);

		LiveWindow.addActuator("Chassis", "Front Left CIM", (CANTalon) leftFrontDriveTalon);
		LiveWindow.addActuator("Chassis", "Front Right CIM", (CANTalon) rightFrontDriveTalon);
		LiveWindow.addActuator("Chassis", "Back Left CIM", (CANTalon) leftBackDriveTalon);
		LiveWindow.addActuator("Chassis", "Back Right CIM", (CANTalon) rightBackDriveTalon);
		
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
    	double heading = Robot.ahrs.getYaw();
        SmartDashboard.putNumber("Chassis PID Input ", heading);
        SmartDashboard.putNumber("Chassis PID SetPoint ", chassisGetSetPoint());

        return heading;
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
    	SmartDashboard.putNumber("Chassis PID Output", output);
    	//System.out.println("SetPoint: "+getSetpoint()+"  Heading: "+Robot.ahrs.getHeading()+"Output: "+output);
    	drive.arcadeDrive(vel, -output/1.5, true);
    }
    
    public void tankDrive (double leftCmd, double rightCmd) {
//    	System.out.println("Left: "+leftCmd+"   Right: "+rightCmd+"   Speed: "+speed);
    	drive.tankDrive(leftCmd*speed, rightCmd*speed, true);
    }
    
    public void arcadeDrive (double moveCmd, double rotateCmd) {
//    	System.out.println("Left: "+moveCmd+"   Right: "+rotateCmd+"   Speed: "+speed);
    	drive.arcadeDrive(moveCmd*speed, rotateCmd, true);
    }
    
	/**
	 * Stop the drive train from moving.
	 */

    public void setPidVel(double vel) {
    	this.vel = vel;
    }
    
    public void enableChassisPID() {
    	getPIDController().enable();
    }
    
    public void disableChassisPID() {
    	getPIDController().disable();
    }

    public void chassisSetSetpoint(double heading) {
    	//TODO Use PID instead of joystick
    	//TODO Check for high limit and stop
    	getPIDController().setSetpoint(heading);
    }
    
    public double chassisGetHeading() {
    	//TODO Use PID instead of joystick
    	//TODO Check for high limit and stop
    	return Robot.ahrs.getCompassHeading();
    }
    
    public double chassisGetSetPoint() {
    	//TODO Use PID instead of joystick
    	//TODO Check for high limit and stop
    	return getPIDController().getSetpoint();
    }

    public void forward(double vel){
//    	drive.tankDrive(vel, vel);
    	drive.arcadeDrive(vel, -0.2, true);
    }
    
    public void reverse(double vel){
//    	drive.tankDrive(-vel, -vel);
    	drive.arcadeDrive(-vel, 0.0, true);
    }
    
    public void rotate(double vel){
//    	drive.tankDrive(-vel, vel);
    	drive.arcadeDrive(vel, 1.0, true);
    }
    
    public void stop() {
//		drive.tankDrive(0.0, 0.0);
    	drive.arcadeDrive(0.0, 0.0, true);
	}
    
    //not needed
    //public void halfSpeed() {
    //	halfSpeed = !halfSpeed;
    //	if(halfSpeed == true){
    //		speed = 0.5;
    //	}else{
    //		speed = 1.0;
    //	}
	//	SmartDashboard.putBoolean("HalfSpeed", Robot.chassis.halfSpeed);
    //}
    
    public void tankDrive(){
    	arcadeDrive = false;
    	tankDrive = true;
    }
    
    public void arcadeDrive(){
    	tankDrive = false;
    	arcadeDrive = true;
    }
    
    /*
     * 
     */
    public void resetChassisEncoder() {
    	chassisEncoder.reset();
    	setSetpoint(0.0);
    }
    
    public double getChassisPosition() {
    	return chassisEncoder.getDistance();
    }

    public int getChassisCount() {
    	return chassisEncoder.get();
    }

    public boolean isChassisStopped() {
    	return chassisEncoder.getStopped();
    }
      
    /*
     * 
     */
    public double encoderGetDistance() {
    	return chassisEncoder.getDistance();
    }
    
    public double encoderGetRate() {
    	return chassisEncoder.getRate();
    }
    
    public boolean encoderGetDirection() {
    	return chassisEncoder.getDirection();
    }
    
    public boolean getAutoTurn() {
    	return autoTurn;
    }
    
    public void setAutoTurn(boolean b) {
    	autoTurn = b;
    }
}