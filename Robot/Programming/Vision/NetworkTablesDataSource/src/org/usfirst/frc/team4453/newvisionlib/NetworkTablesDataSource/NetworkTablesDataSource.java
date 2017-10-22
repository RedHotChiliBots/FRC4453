package org.usfirst.frc.team4453.newvisionlib.NetworkTablesDataSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team4453.newvisionlib.IDataSource;
import org.usfirst.frc.team4453.newvisionlib.Target;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Implementation of a data source using Network Tables.
 * @author conner
 *
 */
public class NetworkTablesDataSource implements IDataSource {
	private ArrayList<Target> targets = new ArrayList<Target>();
	private Map<String, String> settings = new HashMap<String, String>();
	private NetworkTable table;
	
	public NetworkTablesDataSource(NetworkTable tbl)
	{
		table = tbl;
	}
	
	public NetworkTablesDataSource(String ip, String tbl)
	{
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress(ip);
		table = NetworkTable.getTable(tbl);
	}
	
	@Override
	public Target getData(int id) {
		
		if(id >= targets.size())
		{
			return null;
		}
		
		return targets.get(id);
	}

	@Override
	public boolean putData(Target data, int id) {
		targets.set(id, data);
		return table.isConnected();
	}

	@Override
	public int addTarget(Target target) {
		targets.add(target);
		return targets.size() - 1;
	}

	@Override
	public boolean removeTarget(int id) {
		if(id >= targets.size())
		{
			return false;
		}
		
		targets.remove(id);
		table.putValue("VisionData", targets);
		
		return table.isConnected();
	}

	@Override
	public int numOfTargets() {		
		return targets.size();
	}

	@Override
	public boolean push() {
		ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
		ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
		ObjectOutputStream objstream1 = null;
		ObjectOutputStream objstream2 = null;
		try {
			objstream1 = new ObjectOutputStream(stream1);
			objstream2 = new ObjectOutputStream(stream2);
			objstream1.writeObject(targets);
			objstream1.close();
			objstream2.writeObject(settings);
			objstream2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		table.putString("VisionData", new String( Base64.getEncoder().encode(stream1.toByteArray())));
		table.putString("VisionSettings", new String( Base64.getEncoder().encode(stream2.toByteArray())));
		try {
			stream1.close();
			stream2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NetworkTable.flush();
		return table.isConnected();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean pull() {
		ByteArrayInputStream stream1 = new ByteArrayInputStream(Base64.getDecoder().decode(table.getString("VisionData", "").getBytes()));
		ByteArrayInputStream stream2 = new ByteArrayInputStream(Base64.getDecoder().decode(table.getString("VisionSettings", "").getBytes()));
		ObjectInputStream objstream1 = null;
		ObjectInputStream objstream2 = null;
		try {
			objstream1 = new ObjectInputStream(stream1);
			objstream2 = new ObjectInputStream(stream2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			targets = (ArrayList<Target>) objstream1.readObject();
			settings.putAll((Map<String, String>) objstream2.readObject());
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return table.isConnected();
	}

	@Override
	public String getSetting(String setting) {
		if(!settings.containsKey(setting))
		{
			return "";
		}
		return settings.get(setting);
	}

	@Override
	public boolean setSetting(String setting, String value) {
		settings.put(setting, value);
		return table.isConnected();
	}

}
