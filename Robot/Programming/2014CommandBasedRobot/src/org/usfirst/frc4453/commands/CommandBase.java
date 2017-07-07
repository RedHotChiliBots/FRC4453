package org.usfirst.frc4453.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc4453.OI;
import org.usfirst.frc4453.subsystems.Chassis;
import org.usfirst.frc4453.subsystems.Collector;
import org.usfirst.frc4453.subsystems.LaunchShoot;
import org.usfirst.frc4453.subsystems.LaunchTilt;
import org.usfirst.frc4453.subsystems.Sensors;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores and creates each control system. To access a subsystem
 * elsewhere in your code in your code use CommandBase.exampleSubsystem
 * 
 * @author Author
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    // Create a single static instance of all of your subsystems
    public static Chassis chassis = new Chassis();
    public static Sensors sensors = new Sensors();
    public static LaunchTilt launchTilt = new LaunchTilt();
    public static LaunchShoot launchShoot = new LaunchShoot();
    public static Collector collector  = new Collector();

    public static SendableChooser autoChooser;

	public static Preferences prefs;

    public static void init() {
		// This MUST be here. If the OI creates Commands (which it very likely
		// will), constructing it during the construction of CommandBase (from
		// which commands extend), subsystems are not guaranteed to be
		// yet. Thus, their requires() statements may grab null pointers. Bad
		// news. Don't move it.
		oi = new OI();

		oi.reverseDistStick(true);
		oi.reverseTiltStick(false);
		launchShoot.reverseShootMotor(true);
		launchTilt.reverseTiltMotor(true);
		
		// Display the command each subsystem is running
		SmartDashboard.putData(chassis);
		SmartDashboard.putData(sensors);
		SmartDashboard.putData(launchShoot);
		SmartDashboard.putData(launchTilt);
		SmartDashboard.putData(collector);
        
		// Initialize Preferences
		boolean pNew = false;
		prefs = Preferences.getInstance();
		if (! prefs.containsKey("CollectorSpeed")) { prefs.putDouble("CollectorSpeed", 1.0); pNew = true; }
		if (! prefs.containsKey("CollectorScale")) { prefs.putDouble("CollectorScale", 1.0); pNew = true; }
		if (! prefs.containsKey("LeftSpeed")) { prefs.putDouble("LeftSpeed", 0.75); pNew = true; }
		if (! prefs.containsKey("RightSpeed")) { prefs.putDouble("RightSpeed", 0.75); pNew = true; }
		if (! prefs.containsKey("ShootDistance")) { prefs.putDouble("ShootDistance", 0.0); pNew = true; }
		if (! prefs.containsKey("ShootTilt")) { prefs.putDouble("ShootTilt", 0.0); pNew = true; }
		if (! prefs.containsKey("AutoDistance")) { prefs.putDouble("AutoDistance", 10.0); pNew = true; }
		if (! prefs.containsKey("AutoTime")) { prefs.putDouble("AutoTime", 1.5); pNew = true; }
		if (! prefs.containsKey("AutoSpeed")) { prefs.putDouble("AutoSpeed", 0.75); pNew = true; }
		if (pNew) {prefs.save(); }
		
		// Display start/stop buttons for each command
		SmartDashboard.putData(new AutoMoveForward());
		SmartDashboard.putData(new AutoShootDistance());
		SmartDashboard.putData(new AutoShootTime());
		SmartDashboard.putData(new ShootHoldTrigger());
		SmartDashboard.putData(new ShootFireTrigger());
		SmartDashboard.putData(new ShootSetDistance(true));
		SmartDashboard.putData(new ShootWithJoystick());
		SmartDashboard.putData(new TiltSetAngle(true));
		SmartDashboard.putData(new TiltWithJoystick());
		SmartDashboard.putData(new ResetLaunchDistTilt());
		SmartDashboard.putData(new ResetCameraServos());
		SmartDashboard.putData(new CollectorSetSpeed(true));
		SmartDashboard.putData(new DriveToDistance(true));
		SmartDashboard.putData(new DriveForTime(true));
		SmartDashboard.putData(new DriveWithTimer(true));
		SmartDashboard.putData(new DriveSetDirection());
		SmartDashboard.putData(new DriveWithJoystick());
		
		// Initialize optional Autonomous commands
		autoChooser = new SendableChooser();
		autoChooser.addDefault("Auto Move Forward", new AutoMoveForward()); 
		autoChooser.addObject("Auto Distance Shoot", new AutoShootDistance());
		autoChooser.addObject("Auto Time Shoot", new AutoShootTime());
		SmartDashboard.putData("Autonomous Commands", autoChooser);	
	}

	public CommandBase(String name) {
		super(name);
	}

	public CommandBase() {
		super();
	}
}
