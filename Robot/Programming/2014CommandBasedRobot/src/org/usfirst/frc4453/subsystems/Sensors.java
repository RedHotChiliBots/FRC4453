
package org.usfirst.frc4453.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
//TODO import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc4453.Library;
import org.usfirst.frc4453.RobotMap;
import org.usfirst.frc4453.commands.CameraSetServo;

/**
 * Subsystem:
 * The Sensors subsystem contains general sensor and actuator
 * objects and associated methods.
 */
public class Sensors extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private static Servo servoHorzCamera;
    private static Servo servoVertCamera;
    private static AnalogChannel sonarRange;
    private static Gyro gyro;
//TODO	private static Solenoid solenoidSwitch;

    private static double servoVertReverse = 1;
    private static double servoHorzReverse = 1;

    private static final double GYRO_SENSITIVITY = 12.5 / 1000; // 12.5mV/deg/sec converted to V/deg/sec
    private static final double SONAR_VOLTS_TO_FEET = (5.0/5120) * 254.0 * 12.0;    // V/mm * mm/inch * inch/feet

    private static double initAngle = 0.0;
    
    /**
     * Constructor initializing the following objects
     * 	Camera Servos
     * 	Sonar
     * 	Gyro
     */
    public Sensors() {
    	// Camera servos
        servoVertCamera = new Servo(RobotMap.VERT_CAMERA_SERVO);
        servoHorzCamera = new Servo(RobotMap.HORZ_CAMERA_SERVO);
        //servoVertReverse(true);
        
        // Distance sonar
        sonarRange = new AnalogChannel(RobotMap.SONAR);
        
        // Directional gyro
        gyro = new Gyro(RobotMap.GYRO);
        
        gyro.setSensitivity(GYRO_SENSITIVITY);

		gyro.reset();
		
//TODO		solenoidSwitch = new Solenoid(RobotMap.SHOOT_TRIGGER,RobotMap.CAMERA_LIGHT);
//TODO		solenoidSwitch.set(false);

		Timer.delay(3.0);

		LiveWindow.addSensor("Sensors", "Gyro", gyro);
		LiveWindow.addSensor("Sensors", "Sonar", sonarRange);
		LiveWindow.addActuator("Servos", "Horz Servo", servoHorzCamera);
		LiveWindow.addActuator("Servos", "Vert Servo", servoVertCamera);

        // Display sensor values on dashboard
		SmartDashboard.putNumber("Servo (vert)", servoGetCameraVert());
		SmartDashboard.putNumber("Servo (horz)", servoGetCameraHorz());
		SmartDashboard.putNumber("Sonar", sonarGetRange());
		SmartDashboard.putNumber("Gyro", gyroGetAngle());
    }
    
    protected void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new CameraSetServo());
    }
    
    /**
     * Set direction for Vertical servo 
     * @param rev boolean - 1 forward; -1 reverse
     */
	public static void servoVertReverse(boolean rev) {
		//System.out.println("Setting horizontal servo "+pos);
		servoVertReverse = (rev ? -1 : 1);
	}

    /**
     * Set direction for Horizontal servo 
     * @param rev boolean - 1 forward; -1 reverse
     */
	public static void servoHorzReverse(boolean rev) {
		//System.out.println("Setting horizontal servo "+pos);
		servoHorzReverse = (rev ? -1 : 1);
	}

	/**
	 * Set position for Horizontal servo
	 * @param pos double - range -45 to +45
	 */
	public void servoSetCameraHorz(double pos) {
		//System.out.println("Setting horizontal servo "+pos);
		servoHorzCamera.set(Library.angle2Servo(pos)*servoHorzReverse);
	}
	
	/**
	 * Set position for Vertical servo
	 * @param pos double - range -45 to +45
	 */
	public void servoSetCameraVert(double pos) {
		//System.out.println("Setting vertical servo "+pos);
		servoVertCamera.set(Library.angle2Servo(pos)*servoVertReverse);
	}

	/**
	 * Get position for Horizontal servo
	 * @return double - range -45 to +45
	 */
	public final double servoGetCameraHorz() {
		double pos = servoHorzCamera.get();
		//System.out.println("Getting horizontal servo "+pos);
		return (Library.servo2Angle(pos)*servoHorzReverse);
        }
	
	/**
	 * Get position for Vertical servo
	 * @return double - range -45 to +45
	 */
	public final double servoGetCameraVert() {
		double pos = servoVertCamera.get();
		//System.out.println("Getting vertical servo "+pos);
		return (Library.servo2Angle(pos)*servoVertReverse);
	}

	/**
	 * Get sonar range
	 * @return double - feet
	 */
    public final  double sonarGetRange() {
        return sonarRange.getVoltage() * SONAR_VOLTS_TO_FEET;	// feet
    }
    
    /**
     * Reset gyro position
     */
	public void gyroReset() {
		gyro.reset();
	}
	
	/**
	 * Get gyro angle
	 * @return double - degrees
	 */
	public final double gyroGetAngle() {
		return gyro.getAngle();
	}
	
	/**
	 * Set initial gyro angle
	 */
	public final void gyroSetInitialAngle() {
		gyroReset();
		initAngle = gyroGetAngle();
	}
	
	/**
	 * Get initial gyro angle
	 *
	 * @return double - angle (degrees)
	 */
	public final double gyroGetInitialAngle() {
		return initAngle;
	}
}
