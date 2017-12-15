package org.usfirst.frc.team4453.robot.subsystems;

import org.usfirst.frc.team4453.robot.RobotMap;
import org.usfirst.frc.team4453.robot.commands.ClimberStop;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {

	private CANTalon climber; 
	
	public Climber()
	{
		climber = new CANTalon(RobotMap.CLIMBER_MOTOR);
		climber.changeControlMode(TalonControlMode.PercentVbus);
		climber.enableBrakeMode(false);
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new ClimberStop());
    }
    
    public void climb()
    {
    	climber.set(1);
    }
    
    public void stop()
    {
    	climber.set(0);
    }
}

