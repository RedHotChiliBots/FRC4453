package org.usfirst.frc.team4453.newvisionlib.rpivision;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Point3;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.usfirst.frc.team4453.newvisionlib.Color;
import org.usfirst.frc.team4453.newvisionlib.Point2d;
import org.usfirst.frc.team4453.newvisionlib.Point3d;

public class OpenCVConverters {
	public static Color OpenCVScalarToColor(Scalar s)
	{
		return new Color(s.val[0], s.val[1], s.val[2]);
	}
	public static Scalar ColorToOpenCVScalar(Color c)
	{
		return new Scalar(c.R, c.G, c.B);
	}
	public static Point2d OpenCVPointToPoint2d(Point p)
	{
		return new Point2d(p.x, p.y);
	}
	public static Point Point2dToOpenCVPoint(Point2d p)
	{
		return new Point(p.X, p.Y);
	}
	public static Point3d OpenCVPoint3ToPoint3d(Point3 p)
	{
		return new Point3d(p.x, p.y, p.z);
	}
	public static Point3 Point3dToOpenCVPoint3(Point3d p)
	{
		return new Point3(p.X, p.Y, p.Z);
	}
	public static Mat Point3ToMat(Point3 p)
	{
		Mat ret = Mat.zeros(new Size(3, 1), CvType.CV_32F);
		ret.put(0, 0, p.x);
		ret.put(1, 0, p.y);
		ret.put(2, 0, p.z);
		return ret;
	}
	public static Point3 MatToPoint3(Mat m)
	{
		return new Point3(m.get(0, 0)[0], m.get(1, 0)[0], m.get(2, 0)[0]);
	}
}
