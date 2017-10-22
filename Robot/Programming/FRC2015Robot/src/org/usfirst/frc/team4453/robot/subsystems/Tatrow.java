package org.usfirst.frc.team4453.robot.subsystems;

import org.usfirst.frc.team4453.robot.RobotMap;
import org.usfirst.frc.team4453.robot.commands.SetTatrowStop;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Tatrow extends Subsystem {
	
	private static Relay spike_right;
	private static Relay spike_left;
    
	public Tatrow(){
		spike_right = new Relay (RobotMap.TATROW_RIGHT_SPIKE);
		spike_left = new Relay (RobotMap.TATROW_LEFT_SPIKE);
		tatrowStop();
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new SetTatrowStop());
    }
    
    public void tatrowForward() {
    	spike_right.set(Relay.Value.kReverse);
		spike_left.set(Relay.Value.kForward);
    }
    
    public void tatrowReverse() {
    	spike_right.set(Relay.Value.kForward);
		spike_left.set(Relay.Value.kReverse);
    }
    
    public void tatrowStop() {
    	spike_right.set(Relay.Value.kOff);
		spike_left.set(Relay.Value.kOff);
    }
    
}

