package org.usfirst.frc.team4453.robot.subsystems;

import org.usfirst.frc.team4453.robot.RobotMap;
import org.usfirst.frc.team4453.robot.commands.HopperDefault;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Hopper extends Subsystem {

	private Servo hopperGate;

	private static final int AGITATOR_TICKS_PER_REVOLUTION = 1680;
	private static final double AGITATOR_MAX = (80.0 / 360.0) * AGITATOR_TICKS_PER_REVOLUTION;
	private static final double AGITATOR_MIN = (5.0 / 360.0) * AGITATOR_TICKS_PER_REVOLUTION;
	private static final double AGITATOR_STALL_CURRENT = 2.0; // TODO
	private boolean hopperOpen = false;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	private CANTalon agitator;

	private TalonControlMode agitatorMode = TalonControlMode.Disabled;

	public Hopper() {
		hopperGate = new Servo(RobotMap.HOPPER_GATE);
		gateClose();

		agitator = new CANTalon(RobotMap.AGITATOR_MOTOR);
		agitator.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		agitator.reverseSensor(false);
		agitator.reverseOutput(false);
		agitator.setPID(50.0, 0.0, 0.0); // , 0.0, 0, 0.0, 0); //SDS
		agitator.setCloseLoopRampRate(96.0);
		agitator.setForwardSoftLimit(AGITATOR_MAX);
		agitator.enableForwardSoftLimit(false);
		agitator.setReverseSoftLimit(AGITATOR_MIN);
		agitator.enableReverseSoftLimit(false);
		agitator.enableLimitSwitch(true, true);
		agitator.enableZeroSensorPositionOnReverseLimit(true);
		agitator.enable();
	}

	public void initDefaultCommand() {
		setDefaultCommand(new HopperDefault());
	}

	public void gateOpen() {
		hopperGate.set(1.0);
		hopperOpen = true;
	}

	public void gateClose() {
		hopperGate.set(0.47);
		hopperOpen = false;
	}

	public boolean isGateOpen() {
		return hopperOpen;
	}

	public void agitatorEnableSoftLimits(boolean enable) {
		agitator.enableForwardSoftLimit(enable);
		agitator.enableReverseSoftLimit(enable);
	}

	public boolean agitatorHitLimit() {
		return agitator.isRevLimitSwitchClosed() | agitator.isFwdLimitSwitchClosed();
	}

	public void agitatorLeft() {
		agitatorSetPosition();
		System.out.println("AGITATOR_MAX: " + AGITATOR_MAX);
		agitator.set(AGITATOR_MAX);
	}

	public void agitatorMove(double val) {
		agitatorSetPercentVBus();
		agitator.set(val);
	}

	public boolean agitatorOnTarget(double tol) {
		if (agitatorMode != TalonControlMode.Position) {
			System.out.println("agitatorOnTarget: Not in Position Mode");
			return true;
		}
		System.out.println("Agitator: setPoint: " + agitator.getSetpoint() + "   Position: " + agitator.getPosition());
		return Math.abs(agitator.getSetpoint() - agitator.getPosition()) < (tol/ 360.0) * AGITATOR_TICKS_PER_REVOLUTION;
	}

	public void agitatorResetEncoder() {
		agitator.setPosition(0);
	}

	public void agitatorRight() {
		agitatorSetPosition();
		System.out.println("AGITATOR_MIN: " + AGITATOR_MIN);
		agitator.set(AGITATOR_MIN);
	}

	// Agitator
	public void agitatorSetPercentVBus() {
		if (agitatorMode != TalonControlMode.PercentVbus) {
			System.out.println("Changing agitator to PercentVBus...");
			agitator.changeControlMode(TalonControlMode.PercentVbus);
			agitator.reset();
			agitator.enable();
			agitatorMode = TalonControlMode.PercentVbus;
		}
	}

	public void agitatorSetPosition() {
		if (agitatorMode != TalonControlMode.Position) {
			System.out.println("Changing agitator to Position...");
			agitator.changeControlMode(TalonControlMode.Position);
			agitator.reset();
			agitator.enable();
			agitatorMode = TalonControlMode.Position;
		}
	}

	public void agitatorStop() {
		agitatorSetPercentVBus();
		agitator.set(0);
	}
	
	public double getAgitatorCurrent()
	{
		return agitator.getOutputCurrent();
	}
	
	public boolean isAgitatorStalled()
	{
		return getAgitatorCurrent() > AGITATOR_STALL_CURRENT;
	}
}
