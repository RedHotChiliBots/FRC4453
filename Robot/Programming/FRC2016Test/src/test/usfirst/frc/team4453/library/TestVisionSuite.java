package test.usfirst.frc.team4453.library;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestVision_Constants.class,
	TestVision_GetsSets.class,
	TestVision_GetTgtPos.class,
	TestVision_GetAimPos.class,
	TestVision_FindBestTarget.class,
	TestVision_CalcTgtDist.class,
	TestVision_CalcTwrDist.class,
})

public class TestVisionSuite {
}