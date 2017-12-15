package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HopperAgitatorRun extends Command {

	private boolean isLeft = false;
	
    public HopperAgitatorRun() {
    	requires(Robot.hopper);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("HopperAgitatorRun starting...");    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.hopper.isGateOpen())
    	{
        	System.out.println("HopperAgitatorRun: Gate Open");    	
    		
    		if(Robot.hopper.agitatorOnTarget(20) || Robot.hopper.isAgitatorStalled())// || Robot.hopper.agitatorHitLimit())
    		{
    	    	System.out.print("HopperAgitatorRun: At limit; ");    	

    			if(isLeft)
    			{
    		    	System.out.println("Switching to Right");    	
    				Robot.hopper.agitatorRight();
    				isLeft = false;
    			}
    			else
    			{
    		    	System.out.println("Switching to Left");    	
    				Robot.hopper.agitatorLeft();
    				isLeft = true;
    			}
    		}
    		else
    		{
		    	System.out.print("HopperAgitatorRun: Moving ");    	

		    	if(isLeft)
    			{
    		    	System.out.println("Left");    	
    				Robot.hopper.agitatorLeft();
    			}
    			else
    			{
    		    	System.out.println("Right");    	
    				Robot.hopper.agitatorRight();
    			}
    		}
    	}
    	else
    	{
	    	System.out.println("HopperAgitatorRun: Stop.");    	  	
//    		Robot.hopper.agitatorStop();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.hopper.agitatorStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.hopper.agitatorStop();
    }
}
