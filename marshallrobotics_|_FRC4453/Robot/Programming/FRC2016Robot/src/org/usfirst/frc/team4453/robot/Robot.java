
package org.usfirst.frc.team4453.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4453.robot.commands.AutoNoOp;
import org.usfirst.frc.team4453.robot.commands.AutoPortcullis;
import org.usfirst.frc.team4453.robot.commands.AutoRamparts;
import org.usfirst.frc.team4453.robot.commands.AutoRockWall;
import org.usfirst.frc.team4453.robot.commands.AutoRoughTerrain;
import org.usfirst.frc.team4453.robot.commands.TiltReset;
import org.usfirst.frc.team4453.robot.commands.AutoApproach;
import org.usfirst.frc.team4453.robot.commands.AutoChevalDeFrise;
import org.usfirst.frc.team4453.robot.commands.AutoLowBar;
import org.usfirst.frc.team4453.robot.commands.AutoMoat;
import org.usfirst.frc.team4453.library.Tilt;
import org.usfirst.frc.team4453.library.Vision;
import org.usfirst.frc.team4453.newvisionlib.Point3d;
import org.usfirst.frc.team4453.robot.subsystems.Chassis;
//import org.usfirst.frc.team4453.robot.subsystems.Climber;
import org.usfirst.frc.team4453.robot.subsystems.Shooter;

import com.kauailabs.navx.frc.AHRS;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

// comment
public class Robot extends IterativeRobot {
	public static Preferences prefs;
	
	//subsystems
	public static Chassis chassis;
	public static Shooter shooter;
//	public static Climber climber;
	
//	public static Accel accel;
	public static AHRS ahrs;
	public static OI oi;
		
    private static Command autonomousCommand;
	private static SendableChooser autoChooser;

	private double start_time;
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // initialize Preferences
    	prefs = Preferences.getInstance();
        initPrefs();
        
    	// initialize Sensors - do before subsystems to support references
        try {
            /* Communicate w/navX MXP via the MXP SPI Bus.                                     */
            /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
            /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
            ahrs = new AHRS(SPI.Port.kMXP,(byte)200);
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }

//        ahrs    = new AHRS("/dev/ttyUSB0");	// must be before Accel
//        accel   = new Accel();	// Accel references AHRS
//        accel.initST();
        
    	// initialize Subsystems
        chassis = new Chassis();
        shooter = new Shooter();
//        climber = new Climber();

        // initialize OI last to guarantee any required subsystems are initialized first
        oi = new OI();
        
        // display subsystems on dashboard
        SmartDashboard.putData(chassis);
        SmartDashboard.putData(shooter);
//        SmartDashboard.putData(climber);
        
        // accumulate acceleration error average before autonomous
//        ahrs.setCalcErrors(true);
//        accel.setCalcErrors(true);
 
    	// initialize autonomous command chooser
    	autoChooser = new SendableChooser();
    	autoChooser.addDefault("NoOp", new AutoNoOp());
    	autoChooser.addObject("Approach", new AutoApproach());
    	autoChooser.addObject("Cheval de Frise", new AutoChevalDeFrise());
    	autoChooser.addObject("Low Bar", new AutoLowBar());
    	autoChooser.addObject("Moat", new AutoMoat());
    	autoChooser.addObject("Portcullis", new AutoPortcullis());
    	autoChooser.addObject("Ramparts", new AutoRamparts());
    	autoChooser.addObject("Rock Wall", new AutoRockWall());
    	autoChooser.addObject("Rough Terrain", new AutoRoughTerrain());
    	SmartDashboard.putData("Auto Mode", autoChooser);
        autonomousCommand = null;

        // initialize Smartdashboard
        start_time = Timer.getFPGATimestamp();
        
        // update command data on SmartDashboard
		SmartDashboard.putNumber("YawOffset", Robot.prefs.getDouble("yawOffset",0.0));
       	SmartDashboard.putNumber("ccUpAngle", Robot.prefs.getDouble("ccUpAngle",30.0));	// degrees
       	SmartDashboard.putNumber("ccDnAngle", Robot.prefs.getDouble("ccDnAngle",10.0));	// degrees
    	SmartDashboard.putNumber("crsTime",   Robot.prefs.getDouble("crsTime",2.6));	// seconds
       	SmartDashboard.putNumber("crsVel",    Robot.prefs.getDouble("crsVel",0.35));	// speed [0-1]
		SmartDashboard.putNumber("appVel",    Robot.prefs.getDouble("appVel",0.5));
       	SmartDashboard.putNumber("defPitch",  Robot.prefs.getDouble("defPitch",5.0));	// degrees
       	SmartDashboard.putNumber("defDelay",  Robot.prefs.getDouble("defDelay",1.5));	// degrees
		SmartDashboard.putNumber("tiltAngle1", Robot.prefs.getDouble("tiltAngle1",0.0));
		SmartDashboard.putNumber("tiltAngle2", Robot.prefs.getDouble("tiltangle2",0.0));
		SmartDashboard.putNumber("Shoot Vel", Robot.prefs.getDouble("shootVel",10.5));
       	SmartDashboard.putNumber("Defense Number",0);
       	SmartDashboard.putString("Vison Status", "N/A");
       	
       	Robot.ahrs.zeroYaw();
       	
       	Vision.init();
       	
        telemetry();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){
    	// zero accelerometer, velocity, and distance info 
//    	for(int i=0; i < 3; i++) {
//    		Robot.accel.setAccel(i, 0.0);
//    		Robot.accel.setVel(i, 0.0);
//    		Robot.accel.setDist(i, 0.0);
//    	}
    	// restart accelerometer error averaging
//        ahrs.setCalcErrors(true);
//        accel.setCalcErrors(true);
    }

	public void disabledPeriodic() {
		telemetry();
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
    	// stop averaging accelerometer error
//    	accel.setCalcErrors(false);
//    	ahrs.setCalcErrors(false);
//    	ahrs.setYawOffset(Math.toRadians(SmartDashboard.getNumber("YawOffset", 0.0)));
//        Command tiltReset = new TiltReset();
//        tiltReset.start();
        
    	// wait to all accelerometer averaging cycle to complete
       	Timer.delay(0.5);	// allow motor to stop

    	// schedule the autonomous command from chooser
		autonomousCommand = (Command) autoChooser.getSelected();
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        telemetry();
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    	chassis.disable();

//    	accel.setCalcErrors(false);
//    	ahrs.setCalcErrors(false);
//    	ahrs.setYawOffset(Math.toRadians(SmartDashboard.getNumber("YawOffset", 0.0)));

        Command tiltReset = new TiltReset();
        tiltReset.start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	telemetry();
        Scheduler.getInstance().run();
    }
    
    /**
     * 
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    private void telemetry() {  
//        boolean isTable = table.isConnected();
//        System.out.println("Network Table Connected? "+(isTable ? "Yes" : "No"));
//        if (isTable) {
//        	keys = table.getKeys();
//    		System.out.println("Number of keys: "+keys.size());        	
//        	for (String key : keys) {
//       		System.out.println("Key: "+key);
//       	}
//        }
        if(!Vision.isInited())
        {
        	SmartDashboard.putString("Vison Status", "UNINITED");
        }
        else
        {
        	SmartDashboard.putString("Vison Status", Vision.getStatus());
        }
    	
    	
		Point3d tgtPos = Vision.getTargetPosition("TheTarget");

		SmartDashboard.putNumber("centerX", tgtPos.X);
		SmartDashboard.putNumber("centerY", tgtPos.Y);
		SmartDashboard.putNumber("centerZ", tgtPos.Z);
		SmartDashboard.putNumber("Target Distance (vision)", Vision.calcTgtDist("TheTarget"));
				
		double tiltAngle = Robot.shooter.tiltGetAngle();	// Get this from the shooter encoder
		SmartDashboard.putNumber("Tilt Deg", tiltAngle);
		
		Tilt.calcShootAngle(false);
		
		
		
        /* Calculate/display effective update rate in hz */
        double delta_time = Timer.getFPGATimestamp() - start_time;
        double update_count = ahrs.getUpdateCount();
        if ( update_count > 0 ) {
        	double avg_updates_per_sec = delta_time / update_count;
        	if ( avg_updates_per_sec > 0.0 ) {
        		SmartDashboard.putNumber("IMU_EffUpdateRateHz", 1.0 / avg_updates_per_sec);
        	}
        }
        
        /* Display 6-axis Processed Angle Data                                      */
        SmartDashboard.putBoolean(  "IMU_Connected",        ahrs.isConnected());
        SmartDashboard.putBoolean(  "IMU_IsCalibrating",    ahrs.isCalibrating());
        SmartDashboard.putNumber(   "IMU_Yaw",              ahrs.getYaw());
        SmartDashboard.putNumber(   "IMU_Pitch",            ahrs.getPitch());
        SmartDashboard.putNumber(   "IMU_Roll",             ahrs.getRoll());
        
        /* Display tilt-corrected, Magnetometer-based heading (requires             */
        /* magnetometer calibration to be useful)                                   */
        
        SmartDashboard.putNumber(   "IMU_CompassHeading",   ahrs.getCompassHeading());
        
        /* Display 9-axis Heading (requires magnetometer calibration to be useful)  */
        SmartDashboard.putNumber(   "IMU_FusedHeading",     ahrs.getFusedHeading());

        /* These functions are compatible w/the WPI Gyro Class, providing a simple  */
        /* path for upgrading from the Kit-of-Parts gyro to the navx MXP            */
        
        SmartDashboard.putNumber(   "IMU_TotalYaw",         ahrs.getAngle());
        SmartDashboard.putNumber(   "IMU_YawRateDPS",       ahrs.getRate());

        /* Display Processed Acceleration Data (Linear Acceleration, Motion Detect) */
        
        SmartDashboard.putNumber(   "IMU_Accel_X",          ahrs.getWorldLinearAccelX());
        SmartDashboard.putNumber(   "IMU_Accel_Y",          ahrs.getWorldLinearAccelY());
        SmartDashboard.putBoolean(  "IMU_IsMoving",         ahrs.isMoving());
        SmartDashboard.putBoolean(  "IMU_IsRotating",       ahrs.isRotating());

        /* Display estimates of velocity/displacement.  Note that these values are  */
        /* not expected to be accurate enough for estimating robot position on a    */
        /* FIRST FRC Robotics Field, due to accelerometer noise and the compounding */
        /* of these errors due to single (velocity) integration and especially      */
        /* double (displacement) integration.                                       */
        
        SmartDashboard.putNumber(   "Velocity_X",           ahrs.getVelocityX());
        SmartDashboard.putNumber(   "Velocity_Y",           ahrs.getVelocityY());
        SmartDashboard.putNumber(   "Displacement_X",       ahrs.getDisplacementX());
        SmartDashboard.putNumber(   "Displacement_Y",       ahrs.getDisplacementY());
        
        /* Display Raw Gyro/Accelerometer/Magnetometer Values                       */
        /* NOTE:  These values are not normally necessary, but are made available   */
        /* for advanced users.  Before using this data, please consider whether     */
        /* the processed data (see above) will suit your needs.                     */
        
//        SmartDashboard.putNumber(   "RawGyro_X",            ahrs.getRawGyroX());
//        SmartDashboard.putNumber(   "RawGyro_Y",            ahrs.getRawGyroY());
//        SmartDashboard.putNumber(   "RawGyro_Z",            ahrs.getRawGyroZ());
//        SmartDashboard.putNumber(   "RawAccel_X",           ahrs.getRawAccelX());
//        SmartDashboard.putNumber(   "RawAccel_Y",           ahrs.getRawAccelY());
//        SmartDashboard.putNumber(   "RawAccel_Z",           ahrs.getRawAccelZ());
//        SmartDashboard.putNumber(   "RawMag_X",             ahrs.getRawMagX());
//        SmartDashboard.putNumber(   "RawMag_Y",             ahrs.getRawMagY());
//        SmartDashboard.putNumber(   "RawMag_Z",             ahrs.getRawMagZ());
        SmartDashboard.putNumber(   "IMU_Temp_C",           ahrs.getTempC());
        
        /* Omnimount Yaw Axis Information                                           */
        /* For more info, see http://navx-mxp.kauailabs.com/installation/omnimount  */
        AHRS.BoardYawAxis yaw_axis = ahrs.getBoardYawAxis();
        SmartDashboard.putString(   "YawAxisDirection",     yaw_axis.up ? "Up" : "Down" );
        SmartDashboard.putNumber(   "YawAxis",              yaw_axis.board_axis.getValue() );
        
        /* Sensor Board Information                                                 */
//        SmartDashboard.putString(   "FirmwareVersion",      ahrs.getFirmwareVersion());
        
        /* Quaternion Data                                                          */
        /* Quaternions are fascinating, and are the most compact representation of  */
        /* orientation data.  All of the Yaw, Pitch and Roll Values can be derived  */
        /* from the Quaternions.  If interested in motion processing, knowledge of  */
        /* Quaternions is highly recommended.                                       */
//        SmartDashboard.putNumber(   "QuaternionW",          ahrs.getQuaternionW());
//        SmartDashboard.putNumber(   "QuaternionX",          ahrs.getQuaternionX());
//        SmartDashboard.putNumber(   "QuaternionY",          ahrs.getQuaternionY());
//        SmartDashboard.putNumber(   "QuaternionZ",          ahrs.getQuaternionZ());
        
        /* Sensor Data Timestamp */
//        SmartDashboard.putNumber(   "SensorTimestamp",		ahrs.getLastSensorTimestamp());
        
        /* Connectivity Debugging Support                                           */
//        SmartDashboard.putNumber(   "IMU_Byte_Count",       ahrs.getByteCount());
//        SmartDashboard.putNumber(   "IMU_Update_Count",     ahrs.getUpdateCount());
    }
    
    private void initPrefs() {
    	String[] doublePrefs = {
    			"appVel","defPitch","defDelay","yawOffset","tiltAngle1","tiltAngle2",
    			"ccUpAngle","ccDnAngle","crsTime","crsVel","shootVel"};
    	for (String key : doublePrefs) {
    		if (! prefs.containsKey(key)) {
    			prefs.putDouble(key, 0.0);
    		}
    	}
    }
}
