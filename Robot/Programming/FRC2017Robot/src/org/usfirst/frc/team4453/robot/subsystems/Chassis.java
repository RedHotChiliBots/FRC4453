package org.usfirst.frc.team4453.robot.subsystems;

import org.usfirst.frc.team4453.robot.Robot;
import org.usfirst.frc.team4453.robot.RobotMap;
import org.usfirst.frc.team4453.robot.commands.DriveWithJoysticks;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Chassis extends PIDSubsystem {

	public boolean arcadeDrive = false;
	public boolean halfSpeed = false;
	
	private double speed = 1.0;
	private double vel = 0.75; // used with PID controller; set by command

	private RobotDrive drive;

	private CANTalon driveL1Talon;
	private CANTalon driveL2Talon;
	private CANTalon driveR1Talon;
	private CANTalon driveR2Talon;
	
	private Compressor compressor;
	
	private AnalogInput pressureSensor;
		
	private static final double DistancePerRevolution = Math.PI * 8.0;	// 8" diameter wheels
	private static final int TicksPerRevolutions = 1440;
	
	private static boolean autoTurn = false;

	private static final double Kp = 0.3;
	private static final double Ki = 0.0005;
	private static final double Kd = 0.00;

	private static final double PRESSURE_SENSOR_INPUTVOLTAGE = 5.0;
	
	private boolean slaved = false;
	private TalonControlMode motorMode = TalonControlMode.Disabled;
	
	public Chassis() {
		super("Chassis", Kp, Ki, Kd);
		System.out.println("Chassis starting...");

		getPIDController().setContinuous(true); // circular
		getPIDController().setInputRange(0.0, 360.0); // min / max heading in
														// degrees
		getPIDController().setAbsoluteTolerance(1);
		// getPIDController().setPercentTolerance(0.1);
		// getPIDController().setToleranceBuffer(3); // average over three
		// cycles for onTarget
		// getPIDController().setOutputRange(-0.5,0.5); // min motor, max motor

		// Use these to get going:
		getPIDController().setSetpoint(chassisGetYaw()); // Sets where the PID controller should move the system
		// getPIDController().enable(); // Enables the PID controller.

		driveL1Talon = new CANTalon(RobotMap.DRIVE_L1_MOTOR);
		driveL2Talon = new CANTalon(RobotMap.DRIVE_L2_MOTOR);
		driveL1Talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		driveL1Talon.setCloseLoopRampRate(24);
		driveL1Talon.reverseSensor(true);
		//driveL1Talon.configEncoderCodesPerRev(TicksPerRevolutions);
		driveL2Talon.changeControlMode(CANTalon.TalonControlMode.Follower);
		driveL2Talon.set(driveL1Talon.getDeviceID());
		driveL2Talon.reverseOutput(false);
		driveL1Talon.setPID(1, 0, 0);
		driveL1Talon.enable();
		
		driveR1Talon = new CANTalon(RobotMap.DRIVE_R1_MOTOR);
		driveR2Talon = new CANTalon(RobotMap.DRIVE_R2_MOTOR);
		driveR1Talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		driveR1Talon.setCloseLoopRampRate(24);
		driveR1Talon.reverseSensor(true);
		//driveL1Talon.configEncoderCodesPerRev(TicksPerRevolutions);
		driveR2Talon.changeControlMode(CANTalon.TalonControlMode.Follower);
		driveR2Talon.set(driveR1Talon.getDeviceID());
		driveR2Talon.reverseOutput(false);
		driveR1Talon.setPID(1, 0, 0);
		driveR1Talon.enable();
		
		drive = new RobotDrive(driveL1Talon, driveR1Talon);
		drive.setSafetyEnabled(false);

		compressor = new Compressor();
		
		pressureSensor = new AnalogInput(RobotMap.PRESSURE_SENSOR);
		
		LiveWindow.addActuator("Chassis", "Left 1 CIM", driveL1Talon);
		LiveWindow.addActuator("Chassis", "Left 2 CIM", driveL2Talon);
		LiveWindow.addActuator("Chassis", "Right 1 CIM", driveR1Talon);
		LiveWindow.addActuator("Chassis", "Right 2 CIM", driveR2Talon);
			
		Robot.ahrs.zeroYaw();
		
		System.out.println("Chassis is running.");
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticks());
	}

	@Override
	protected double returnPIDInput() {
		// Return your input value for the PID loop
		// e.g. a sensor, like a potentiometer:
		// yourPot.getAverageVoltage() / kYourMaxVoltage;
		double heading = chassisGetYaw();
		SmartDashboard.putNumber("Chassis PID Input ", heading);
		SmartDashboard.putNumber("Chassis PID SetPoint ", chassisGetSetPoint());

		return heading;
	}

	@Override
	protected void usePIDOutput(double output) {
		// Use output to drive your system, like a motor
		SmartDashboard.putNumber("Chassis PID Output", output);
		// System.out.println("SetPoint: "+getSetpoint()+" Heading:
		// "+Robot.ahrs.getHeading()+"Output: "+output);
		arcadeDrive(vel, -output / 1.5);
	}
	
	public boolean isOnTarget(double tol)
	{
		return Math.abs(chassisGetYaw() - getPIDController().getSetpoint()) < tol;
	}
	
	public void slaveMotors(TalonControlMode mode) {
		if(!slaved || motorMode != mode)
		{
			System.out.println("Slaving chassis motors with mode " + mode);
			driveL1Talon.changeControlMode(mode);
			driveL2Talon.changeControlMode(TalonControlMode.Follower);
			driveL2Talon.set(driveL1Talon.getDeviceID());
			driveR1Talon.changeControlMode(TalonControlMode.Follower);
			driveR1Talon.set(driveL1Talon.getDeviceID());
			driveR2Talon.changeControlMode(TalonControlMode.Follower);
			driveR2Talon.set(driveL1Talon.getDeviceID());
			
			driveL1Talon.enable();
			driveR1Talon.enable();
			
			slaved = true;
			motorMode = mode;
		}
	}

	public void unslaveMotors(TalonControlMode mode) {
		if(slaved || motorMode != mode)
		{
			System.out.println("Unslaving chassis motors with mode " + mode);
			driveL1Talon.changeControlMode(mode);
			driveR1Talon.changeControlMode(mode);
			driveR1Talon.set(driveL1Talon.get());

			driveL2Talon.changeControlMode(TalonControlMode.Follower);
			driveL2Talon.set(driveL1Talon.getDeviceID());
			driveR2Talon.changeControlMode(TalonControlMode.Follower);
			driveR2Talon.set(driveR1Talon.getDeviceID());
			
			driveL1Talon.enable();
			driveR1Talon.enable();
			
			slaved = false;
			motorMode = mode;
		}
	}

	public void tankDrive(double leftCmd, double rightCmd) {
		unslaveMotors(TalonControlMode.PercentVbus);
		drive.tankDrive(leftCmd * speed, rightCmd * speed, true);
	}
	
	public void arcadeDrive(double spdCmd, double rotCmd) {
		unslaveMotors(TalonControlMode.PercentVbus);
		drive.arcadeDrive(spdCmd * speed, rotCmd * speed, false);
	}

	public void driveDistanceStart(double distance) {
		unslaveMotors(TalonControlMode.Position);
		driveL1Talon.setPosition(0);
		driveR1Talon.setPosition(0);
		driveL1Talon.set((distance / DistancePerRevolution)*TicksPerRevolutions);
		driveR1Talon.set(-(distance / DistancePerRevolution)*TicksPerRevolutions);
	}

	public void driveDistanceUpdate(double distance)
	{
		driveL1Talon.set((distance / DistancePerRevolution)*TicksPerRevolutions);
		driveR1Talon.set(-(distance / DistancePerRevolution)*TicksPerRevolutions);
	}
	
	public double getDistanceSetpoint() {
		return (driveL1Talon.getSetpoint() / TicksPerRevolutions) * DistancePerRevolution;
	}

	public double getDistance() {
		return (driveL1Talon.getPosition() / TicksPerRevolutions) * DistancePerRevolution;
	}

	public boolean isDistanceOnTarget(double tol) {
		return Math.abs(getDistance() - getDistanceSetpoint()) < tol;
	}

	public void setPidVel(double vel) {
		this.vel = vel;
	}

	public void enableChassisPID(double vel) {
		getPIDController().enable();
		setPidVel(vel);
	}

	public void disableChassisPID() {
		getPIDController().disable();
		vel = 0;
		stop();
	}

	public void chassisSetSetpoint(double heading) {
		getPIDController().setSetpoint(heading);
	}

	public double chassisGetYaw() {
		return Robot.ahrs.getYaw();
	}
	
	public double chassisGetSetPoint() {
		return getPIDController().getSetpoint();
	}

	public void forward(double vel) {
		arcadeDrive(vel, -0.0);
	}

	public void reverse(double vel) {
		arcadeDrive(-vel, 0.0);
	}

	public void rotate(double vel) {
		arcadeDrive(vel, 1.0);
	}

	public void stop() {
		arcadeDrive(0.0, 0.0);
	}
	
	public void enableCompressor()
	{
		compressor.start();
	}
	
	public void disableCompressor()
	{
		compressor.stop();
	}
	
	public double getPressure()
	{
		return 250.0 * (pressureSensor.getVoltage() / PRESSURE_SENSOR_INPUTVOLTAGE) - 25.0;
	}

	public boolean getAutoTurn() {
		return autoTurn;
	}

	public void setAutoTurn(boolean b) {
		autoTurn = b;
	}
}