package org.usfirst.frc.team4453.robot;

import org.usfirst.frc.team4453.robot.commands.GrabberToggleGrab;
import org.usfirst.frc.team4453.robot.commands.GrabberToggleLift;
import org.usfirst.frc.team4453.robot.triggers.GearGrabRelease;
import org.usfirst.frc.team4453.robot.triggers.GearLiftDrop;

public class Triggers {

	public GearGrabRelease toggleGrab;
	public GearLiftDrop toggleLift;

	public Triggers() {
		// Gear grab/lift Toggle
		toggleGrab = new GearGrabRelease();
		toggleGrab.whenActive(new GrabberToggleGrab());
		
		toggleLift = new GearLiftDrop();
		toggleLift.whenActive(new GrabberToggleLift());
	}
}
