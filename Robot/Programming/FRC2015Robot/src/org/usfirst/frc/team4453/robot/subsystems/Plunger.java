package org.usfirst.frc.team4453.robot.subsystems;

import org.usfirst.frc.team4453.robot.RobotMap;
import org.usfirst.frc.team4453.robot.commands.SetPlungerMove;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Plunger extends Subsystem {
    
	private static Talon rightPlungerMotor;
	private static Talon leftPlungerMotor;

	private static DigitalInput plungerFRLimit;
	private static DigitalInput plungerFLLimit;
	private static DigitalInput plungerBRLimit;
	private static DigitalInput plungerBLLimit;
	
	public Plunger(){
		plungerFRLimit = new DigitalInput(RobotMap.PLUNGER_FR_LIMIT);
		plungerFLLimit = new DigitalInput(RobotMap.PLUNGER_FL_LIMIT);
		plungerBRLimit = new DigitalInput(RobotMap.PLUNGER_BR_LIMIT);
		plungerBLLimit = new DigitalInput(RobotMap.PLUNGER_BL_LIMIT);
	
		rightPlungerMotor = new Talon(RobotMap.RIGHT_PLUNGER_MOTOR);
		leftPlungerMotor = new Talon(RobotMap.LEFT_PLUNGER_MOTOR);
		
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	setDefaultCommand(new SetPlungerMove());
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setPlungerMove(double cmd){
    	if (plungerFLLimit.get() && cmd>0.0) {
    		leftPlungerMotor.set(0.0);
    	} else if (plungerBLLimit.get() && cmd<0.0){
    		leftPlungerMotor.set(0.0);
    	} else {
    		leftPlungerMotor.set(cmd); 
    	}
   
    	if (plungerFRLimit.get() && cmd>0.0) {
    		rightPlungerMotor.set(0.0);
    	} else if (plungerBRLimit.get() && cmd<0.0){
    		rightPlungerMotor.set(0.0);
    	} else {
    		rightPlungerMotor.set(cmd); 
    	}
    }
    
    public void setPlungerStop(){
    	leftPlungerMotor.set(0.0);
    	rightPlungerMotor.set(0.0);   
    }
    
    public boolean getRightRearLimit() {
    	return plungerBRLimit.get();
    }
    
    public boolean getLeftRearLimit() {
    	return plungerBLLimit.get();
    }
}


