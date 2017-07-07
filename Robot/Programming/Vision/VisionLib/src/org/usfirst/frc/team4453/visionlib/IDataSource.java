package org.usfirst.frc.team4453.visionlib;

public interface IDataSource {
	public abstract Target getData();
	public abstract boolean putData(Target data); // Should only be called by image processing code (i.e. RPIVision)
}
