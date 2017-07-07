package org.usfirst.frc.team4453.visionlib;

public class ColorRectTargetDef extends TargetDef {

	ColorRectTargetDef()
	{
		HighColor = new Color(255, 255, 255);
		LowColor = new Color(0, 0, 0);
		RealHeight = 1;
		RealWidth = 1;
	}
	
	ColorRectTargetDef(Color Low, Color High, double H, double W)
	{
		HighColor = High;
		LowColor = Low;
		RealHeight = H;
		RealWidth = W;
	}
	
	@Override
	public TargetType getType() {
		return TargetType.COLORRECT;
	}
	
	public Color HighColor;
	public Color LowColor;
	public double RealHeight, RealWidth;

}
