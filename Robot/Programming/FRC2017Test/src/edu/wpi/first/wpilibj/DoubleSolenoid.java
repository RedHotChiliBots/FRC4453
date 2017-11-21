package edu.wpi.first.wpilibj;

public class DoubleSolenoid {
	public int testFwdID, testRevID;
	public DoubleSolenoid(int forward, int reverse)
	{
		testFwdID = forward;
		testRevID = reverse;
	}
	
	public enum Value {
	    kOff,
	    kForward,
	    kReverse
	}
	
	public Value testSetVal = Value.kOff;
	public void set(Value val)
	{
		testSetVal = val;
	}
}
