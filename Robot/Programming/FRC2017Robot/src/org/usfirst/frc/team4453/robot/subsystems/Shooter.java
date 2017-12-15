package org.usfirst.frc.team4453.robot.subsystems;

import org.usfirst.frc.team4453.library.Tilt;
import org.usfirst.frc.team4453.robot.Robot;
import org.usfirst.frc.team4453.robot.RobotMap;
import org.usfirst.frc.team4453.robot.commands.AutoShooterControl;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shooter extends Subsystem {

	// Shooter
	private CANTalon shooter;
	// Shooter Constants
	private static final int SHOOTER_ENCODER_PULSES_PER_REV = 20;
	private static final double[][] SHOOTER_TABLE = {{1.73, -4158, 16.03},
													 {2.39, -3832, 23.68},
													 {2.86, -4678, 24.57}, 
													 {3.81, -5840, 27.69},
													 {4.11, -5840, 28.71}};
	// Tilt
	private CANTalon tilt;
	private TalonControlMode tiltMode = TalonControlMode.Disabled;
	// Tilt Constants
	private static final int TILT_ENCODER_PULSES_PER_REV = 560;	// NeveRest20 = 560 counts per rev
	private static final double TILT_LEAD_SCREW_PITCH = 0.25;
	private static final double TILT_PULSES_PER_INCH = TILT_ENCODER_PULSES_PER_REV / TILT_LEAD_SCREW_PITCH;
//	private static final double TILT_ENCODER_DIST_PER_PULSE = TILT_LEAD_SCREW_PITCH / TILT_ENCODER_PULSES_PER_REV;
	private static final double TILT_MIN = Tilt.calcSide(0.0) * TILT_PULSES_PER_INCH;
	private static final double TILT_MAX = Tilt.calcSide(45.0) * TILT_PULSES_PER_INCH;

	// Yaw
	private CANTalon yaw;
	private TalonControlMode yawMode = TalonControlMode.Disabled;
	// Yaw Constants
	private static final int YAW_ENCODER_PULSES_PER_REV = 560;	// NeveRest20 = 560 counts per rev
	private static final double YAW_GEARING_RATIO = 10.5 / 1.1875;	// Motor revolutions per yaw rotation. TODO: Figure this out.
	private static final double YAW_PULSES_PER_DEGREE = (YAW_ENCODER_PULSES_PER_REV * YAW_GEARING_RATIO) / 360;
	private static final double YAW_MIN = -55 * YAW_PULSES_PER_DEGREE;
	private static final double YAW_MAX = 55 * YAW_PULSES_PER_DEGREE;

	// Initialize your subsystem here
	public Shooter() {
		System.out.println("Shooter starting...");

		// Shooter Setup.
		shooter = new CANTalon(RobotMap.SHOOTER_MOTOR);
		shooter.changeControlMode(TalonControlMode.Speed);
		shooter.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooter.configEncoderCodesPerRev(SHOOTER_ENCODER_PULSES_PER_REV);
		shooter.setPID(5, 0.01, 12, 0, 0, 0, 0);
		shooter.enable();

		// Tilt Setup.
		tilt = new CANTalon(RobotMap.TILT_MOTOR);
//		tilt.changeControlMode(TalonControlMode.Position);
		setTiltPosition();
		tilt.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		//tilt.configEncoderCodesPerRev(TILT_ENCODER_PULSES_PER_REV);
		tilt.setPID(2, 0, 0, 0, 0, 0, 0);
		tilt.setForwardSoftLimit(TILT_MAX);
		tilt.enableForwardSoftLimit(false);
		tilt.setReverseSoftLimit(TILT_MIN);
		tilt.enableReverseSoftLimit(false);
		tilt.enableLimitSwitch(true, true);
		tilt.enable();

		// Yaw Setup.
		yaw = new CANTalon(RobotMap.YAW_MOTOR);
//		yaw.changeControlMode(TalonControlMode.Position);
		setYawPosition();
		yaw.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		//yaw.configEncoderCodesPerRev(YAW_ENCODER_PULSES_PER_REV);
		yaw.setPID(3, 0, 0, 0, 0, 0, 0);
		yaw.configMaxOutputVoltage(12.0);
		yaw.setCloseLoopRampRate(120.0);
		yaw.setForwardSoftLimit(YAW_MAX);
		yaw.enableForwardSoftLimit(false);
		yaw.setReverseSoftLimit(YAW_MIN);
		yaw.enableReverseSoftLimit(false);
		yaw.enableLimitSwitch(true, true);
		yaw.enable();

		// Setup LiveWindow for Test Mode
		LiveWindow.addActuator("Shooter", "Shooter", shooter);
		LiveWindow.addActuator("Shooter", "Tilt", tilt);
		LiveWindow.addActuator("Shooter", "Yaw", yaw);

		System.out.println("ShooterTable: " + SHOOTER_TABLE.length + " entries.");
		
		for(int i = 0; i < SHOOTER_TABLE.length; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				System.out.print(SHOOTER_TABLE[i][j] + " ");
			}
			System.out.println();
		}
		
//		yawResetEncoder();

		System.out.println("Shooter is running");
	}

	@Override
	public void initDefaultCommand() {		
//		setDefaultCommand(new ShooterNoOp());
		setDefaultCommand(new AutoShooterControl());
//		setDefaultCommand(new ShooterControlWithJoystick());
	}
	
	public void reinitDefaultCommand() {
		setDefaultCommand(new AutoShooterControl());
//		setDefaultCommand(new ShooterControlWithJoystick());		
	}
	
	public void stop()
	{
		shooterStop();
		yawStop();
		tiltStop();
	}

	private void setYawPosition() {
		if(yawMode != TalonControlMode.Position)
		{
			System.out.println("Changing Yaw to Position mode");
			yaw.changeControlMode(TalonControlMode.Position);
			yaw.reset();
			yaw.enable();
			yawMode = TalonControlMode.Position;
		}
	}
	
	private void setYawPercentVbus() {
		if(yawMode != TalonControlMode.PercentVbus)
		{
			System.out.println("Changing Yaw to PercentVbus mode");
			yaw.changeControlMode(TalonControlMode.PercentVbus);
			yawMode = TalonControlMode.PercentVbus;
		}
	}
	
	private void setTiltPosition() {
		if(tiltMode != TalonControlMode.Position)
		{
			System.out.println("Changing Tilt to Position mode");
			tilt.changeControlMode(TalonControlMode.Position);
			tilt.reset();
			tilt.enable();
			tiltMode = TalonControlMode.Position;
		}
	}
	
	private void setTiltPercentVbus() {
		if(tiltMode != TalonControlMode.PercentVbus)
		{
			System.out.println("Changing Tilt to PercentVbus mode");
			tilt.changeControlMode(TalonControlMode.PercentVbus);
			tiltMode = TalonControlMode.PercentVbus;
		}
	}
	
	
	//=============== SHOOTER METHODS ===============

	public double shooterGetSpeed() {
		return shooter.getSpeed()*4.0;
	}

	public void shooterSetSpeed(double spd) {
		shooter.set(spd);
	}
	
	public double shooterGetSetpoint()

	{
		return shooter.getSetpoint();
	}
	public void shooterSpinup(double speed) {
		Robot.hopper.gateClose();
		shooterSetSpeed(speed);
	}

	public void shooterFire(double speed) {
		Robot.hopper.gateOpen();
		shooterSetSpeed(speed);
	}
	
	public void shooterStopFire() {
		Robot.hopper.gateClose();
	}
	
	public void shooterStop() {
		shooterSetSpeed(0.0);
		shooterStopFire();
	}

	public boolean shooterIsReady(double tol) {
		return Math.abs(shooterGetSpeed() - shooter.get()) < tol;
	}
	
	public double getAimSpeed(double dist) {
		double[] lower = {
				0, 0, 0
		};
		double[] upper = {
				10000000, 0, 0
		};
		for (int i = 0; i < SHOOTER_TABLE.length; i++) {
			if (lower[0] < SHOOTER_TABLE[i][0] && SHOOTER_TABLE[i][0] <= dist) {
//				System.out.print("New lower:" + SHOOTER_TABLE[i][0]);
				lower = SHOOTER_TABLE[i].clone();
			}
			if (upper[0] > SHOOTER_TABLE[i][0] && SHOOTER_TABLE[i][0] >= dist) {
//				System.out.print("New upper:" + SHOOTER_TABLE[i][0]);
				upper = SHOOTER_TABLE[i].clone();
			}
		}
		
//		System.out.print("upper: ");
//		for(int i = 0; i < upper.length; i++)
//		{
//			System.out.print(upper[i] + " ");
//		}
//		System.out.println();
//
//		System.out.print("lower: ");
//		for(int i = 0; i < lower.length; i++)
//		{
//			System.out.print(lower[i] + " ");
//		}
//		System.out.println();
		
		SmartDashboard.putNumber("ShooterCalc Upper Speed", upper[1]);
		SmartDashboard.putNumber("ShooterCalc Lower Speed", lower[1]);
		
		double lowerbias = (upper[0] - dist) / (upper[0] - lower[0]);
		double upperbias = (dist - lower[0]) / (upper[0] - lower[0]);
		if(upper[0] != lower[0])
		{
			return (lower[1] * lowerbias + upper[1] * upperbias);
		}
		else
		{
			return lower[1];
		}
	}

	public double getAimAngle(double dist) {
		double[] lower = {
				0, 0, 0
		};
		double[] upper = {
				10000000, 0, 0
		};
		for (int i = 0; i < SHOOTER_TABLE.length; i++) {
			if (lower[0] < SHOOTER_TABLE[i][0] && SHOOTER_TABLE[i][0] <= dist) {
//				System.out.print("New lower:" + SHOOTER_TABLE[i][0]);
				lower = SHOOTER_TABLE[i].clone();
			}
			if (upper[0] > SHOOTER_TABLE[i][0] && SHOOTER_TABLE[i][0] >= dist) {
//				System.out.print("New upper:" + SHOOTER_TABLE[i][0]);
				upper = SHOOTER_TABLE[i].clone();
			}
		}

		double lowerbias = (upper[0] - dist) / (upper[0] - lower[0]);
		double upperbias = (dist - lower[0]) / (upper[0] - lower[0]);

//		System.out.print("upper: ");
//		for(int i = 0; i < upper.length; i++)
//		{
//			System.out.print(upper[i] + " ");
//		}
//		System.out.println();
//
//		System.out.print("lower: ");
//		for(int i = 0; i < lower.length; i++)
//		{
//			System.out.print(lower[i] + " ");
//		}
//		System.out.println();
		
		SmartDashboard.putNumber("ShooterCalc Upper Angle", upper[2]);
		SmartDashboard.putNumber("ShooterCalc Lower Angle", lower[2]);
		
		if(upper[0] != lower[0])
		{
			return (lower[2] * lowerbias + upper[2] * upperbias);
		}
		else
		{
			return lower[2];
		}
	}

	//=============== TILT METHODS ===============

	public void tiltResetEncoder()
	{
		tilt.setEncPosition(0);
	}

	public double tiltGetDist() {
		return tilt.getEncPosition() / TILT_PULSES_PER_INCH;
	}

	public double tiltGetAngle() {
		return Tilt.calcAngle(tiltGetDist());
	}

//	public void tiltSetAngle(double angle) {
///		tiltSetSetPoint(Tilt.calcSide(angle));
//	}

	public void tiltSetAngle(double angle) {
//		if(tiltMode != TalonControlMode.Position)
//		{
//			tilt.changeControlMode(TalonControlMode.Position);
//			tiltMode = TalonControlMode.Position;
//		}
		tiltSetSetPoint(Tilt.calcSide(angle) * TILT_PULSES_PER_INCH);
	}

	public double tiltGetSetpoint() {
		return Tilt.calcAngle(tilt.getSetpoint() / TILT_PULSES_PER_INCH);
	}

	public int tiltEncoderPos() {
		return tilt.getEncPosition();
	}
	
	public void tiltSetSetPoint(double setPoint) {
		setTiltPosition();
//		if(tiltMode != TalonControlMode.Position)
//		{
//			tilt.changeControlMode(TalonControlMode.Position);
//			tiltMode = TalonControlMode.Position;
//		}
		setPoint = Math.max(TILT_MIN, Math.min(TILT_MAX, setPoint));
		tilt.setSetpoint(setPoint);
	}

	public boolean tiltGetLowerLimit() {
		return tilt.isRevLimitSwitchClosed();
	}

	public boolean tiltIsReady(double tol) {
		return Math.abs(tiltGetSetpoint() - tiltGetAngle()) < tol;
	}
	
	public void tiltLower() {
		setTiltPercentVbus();
//		if(tiltMode != TalonControlMode.PercentVbus)
//		{
//			tilt.changeControlMode(TalonControlMode.PercentVbus);
//			tiltMode = TalonControlMode.PercentVbus;
//		}
		tilt.set(-1.0);
	}

	public void tiltRaise() {
		setTiltPercentVbus();
//		if(tiltMode != TalonControlMode.PercentVbus)
//		{
//			tilt.changeControlMode(TalonControlMode.PercentVbus);
//			tiltMode = TalonControlMode.PercentVbus;
//		}
		tilt.set(1.0);
	}

	public void tiltStop() {
		setTiltPercentVbus();
//		if(tiltMode != TalonControlMode.PercentVbus)
//		{
//			tilt.changeControlMode(TalonControlMode.PercentVbus);
//			tiltMode = TalonControlMode.PercentVbus;
//		}
		tilt.set(0.0);
	}

	public void tiltEnableSoftLimit(boolean enable)
	{
		tilt.enableForwardSoftLimit(enable);
		tilt.enableReverseSoftLimit(enable);
	}
	
	//=============== YAW METHODS ===============
	
	public void yawSetPosition(double pos) {
		setYawPosition();
//		if(yawMode != TalonControlMode.Position)
//		{
//			yaw.changeControlMode(TalonControlMode.Position);
//			yawMode = TalonControlMode.Position;
//		}
		pos = Math.max(YAW_MIN, Math.min(YAW_MAX, pos));
		yaw.set(pos);
	}
	
	public void yawSetAngle(double angle) {
		setYawPosition();
//		if(yawMode != TalonControlMode.Position)
//		{
//			yaw.changeControlMode(TalonControlMode.Position);
//			yawMode = TalonControlMode.Position;
//		}
		yawSetPosition(angle * YAW_PULSES_PER_DEGREE);
	}
	
	public double yawGetSetpoint() {
		return yaw.getSetpoint() / YAW_PULSES_PER_DEGREE;
	}

	public double yawGetAngle() {
		return yawEncoderPos() / YAW_PULSES_PER_DEGREE;
	}

	public boolean yawHitLimit() {
		return yaw.isRevLimitSwitchClosed() | yaw.isFwdLimitSwitchClosed();
	}

	public void yawStop()
	{
		setYawPercentVbus();
//		if(yawMode != TalonControlMode.PercentVbus)
//		{
//			yaw.changeControlMode(TalonControlMode.PercentVbus);
//			yawMode = TalonControlMode.PercentVbus;
//		}
		yaw.set(0);
	}
	
	public void yawEnableSoftLimit(boolean enable)
	{
		yaw.enableForwardSoftLimit(enable);
		yaw.enableReverseSoftLimit(enable);
	}
	
	public void yawResetEncoder()
	{
		yaw.setEncPosition(0);
	}
	
	public int yawEncoderPos()
	{
		return yaw.getEncPosition();
	}
	
	public void yawMove(double speed)
	{
		setYawPercentVbus();
//		if(yawMode != TalonControlMode.PercentVbus)
//		{
//			yaw.changeControlMode(TalonControlMode.PercentVbus);
//			yawMode = TalonControlMode.PercentVbus;
//		}
		yaw.set(speed);
	}

	public boolean yawIsReady(double tol) {
		return Math.abs(yawGetSetpoint() - yawGetAngle()) < tol;
	}
}
