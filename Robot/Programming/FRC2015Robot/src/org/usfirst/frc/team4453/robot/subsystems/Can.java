package org.usfirst.frc.team4453.robot.subsystems;

import org.usfirst.frc.team4453.robot.RobotMap;
import org.usfirst.frc.team4453.robot.commands.SetCanMove;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Can extends Subsystem {
   
	//Here we define the two Talon motors for the can (Right and Left sides of the Robot). 
	public static Talon rightCanMotor;
	public static Talon leftCanMotor;
	
	//Here we define the limit switch that tells us when the can is stowed. 
	private static DigitalInput canLimit;
	
	public Can(){
		/*This section tells us where to find "canLimit" as well as "rightCanMotor" and 
		"leftCanMotor" on the Robot Map. */
		
		canLimit = new DigitalInput(RobotMap.CAN_LIMIT);
		
		rightCanMotor = new Talon(RobotMap.RIGHT_CAN_MOTOR);
		leftCanMotor = new Talon(RobotMap.LEFT_CAN_MOTOR);

	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	setDefaultCommand(new SetCanMove());
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void setCanMove(double cmd){
    	// here we tell motors to turn in the direction of the value cmd.
    	leftCanMotor.set(cmd);
    	rightCanMotor.set(cmd); 
    }
    public void setCanStop() {
    	// here we tell motors to stop.
    	leftCanMotor.set(0.0);
    	rightCanMotor.set(0.0); 
	}
    
    public boolean getCanLimit(){
		// here we get the can's limit switch which is in the back of the robot.
    	return canLimit.get();
    }
}

