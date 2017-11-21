package test.usfirst.frc.team4453.library;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestTilt_Constants.class,
	TestTilt_CalcSide.class,
	TestTilt_CalcAngle.class,
	TestTilt_GetShooterHeight.class,
	TestTilt_GetShooterDist.class,
	TestTilt_CalcShootAngle.class,
})

public class TestTiltSuite {
}