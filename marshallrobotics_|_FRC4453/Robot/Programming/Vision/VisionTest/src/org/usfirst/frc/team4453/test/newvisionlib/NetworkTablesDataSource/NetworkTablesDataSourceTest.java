package org.usfirst.frc.team4453.test.newvisionlib.NetworkTablesDataSource;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team4453.newvisionlib.Target;
import org.usfirst.frc.team4453.newvisionlib.TargetType;
import org.usfirst.frc.team4453.newvisionlib.NetworkTablesDataSource.NetworkTablesDataSource;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class NetworkTablesDataSourceTest {
	NetworkTablesDataSource test1 = null;
	NetworkTablesDataSource test2 = null;
	
	@Before
	public void setUp() throws Exception {
		NetworkTable.setServerMode();
		NetworkTable.globalDeleteAll();
		NetworkTable table = NetworkTable.getTable("test");
		test1 = new NetworkTablesDataSource(table);
		test2 = new NetworkTablesDataSource(table);
	}

	@After
	public void tearDown() throws Exception {
		test1 = null;
		test2 = null;
	}

	@Test
	public void getPutDataSame() {
		Target testdat = new Target();
		int id = test1.addTarget(testdat);
		assertSame(testdat, test1.getData(id));
	}
	
	@Test
	public void getPutDataDifferent()
	{
		Target testdat = new Target();
		testdat.type = TargetType.IMAGE; // Something to test for.
		int id = test1.addTarget(testdat);
		test1.push();
		test2.pull();
		Target result = test2.getData(id);
		assertEquals(testdat.type, result.type);
	}
	
	@Test
	public void getPutSettingSame()
	{
		test1.setSetting("test", "Robotics is awesome!");
		assertEquals(test1.getSetting("test"), "Robotics is awesome!");
	}
	
	@Test
	public void getPutSettingDifferent()
	{
		test1.setSetting("test", "Robotics is awesome!");
		test1.push();
		test2.pull();
		assertEquals("Robotics is awesome!", test2.getSetting("test"));
	}
	
	@Test
	public void settingMerge()
	{
		test1.setSetting("test", "Robotics is awesome!");
		test2.setSetting("test2", "More filler data.");
		test1.push();
		test2.pull();
		assertEquals("Robotics is awesome!", test2.getSetting("test"));
		assertEquals("More filler data.", test2.getSetting("test2"));
	}
	
	@Test
	public void settingOverwrite()
	{
		test1.setSetting("test", "Robotics is awesome!");
		test2.setSetting("test", "Should be overwritten.");
		test1.push();
		test2.pull();
		assertEquals("Robotics is awesome!", test2.getSetting("test"));
	}
	

}
