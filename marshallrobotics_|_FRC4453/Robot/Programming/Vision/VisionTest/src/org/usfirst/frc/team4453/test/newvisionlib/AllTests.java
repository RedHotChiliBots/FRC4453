package org.usfirst.frc.team4453.test.newvisionlib;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		TargetTest.class,
		ColorTest.class,
		ColorTargetPointTest.class,
		Point2dTest.class,
		Point3dTest.class,
		org.usfirst.frc.team4453.test.newvisionlib.NetworkTablesDataSource.AllTests.class
})
public class AllTests {

}
