package org.usfirst.frc.team4453.newvisionlib;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a vision target. Can be of various types.
 * @author conner
 *
 */
public class Target implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3719680918887383177L;

	/**
	 * The type of target this is.
	 */
	public TargetType type = TargetType.INVALID;
	
	/**
	 * States whether this target has been processed by vision code.
	 */
	public boolean hasData = false;
	
	/**
	 * Holds points for the targets.
	 * Used for different things by different target types.
	 * COLOR_DOTS: Stores colors, 3d model and target points, and 2d image points of dots.
	 * IMAGE: Stores the model and target 3d points, along with the 2d image points. Color is ignored.
	 * 
	 * It can also have translation-only points for all target types.
	 */
	public List<ColorTargetPoint> colorPoints = new ArrayList<ColorTargetPoint>();
	
	/**
	 * Used by IMAGE: Filename for image. Image MUST be taken perpendicular to the target image, and cropped to the target.
	 * NOTE: Image MUST be on the PI, as it is loaded from there.
	 */
	public String referenceImageFilename;
	
	/**
	 *  Used by IMAGE: Real world size of target image.
	 */
	public Point2d referenceImageSize;
	
	/**
	 * addPoint - Helper to add points to target.
	 * @param p The point to add.
	 */
	public void addPoint(ColorTargetPoint p)
	{
		colorPoints.add(p);
	}
	
	/**
	 * makeCOLOR_DOT - Makes an empty COLOR_DOT target.
	 * @return New COLOR_DOT target.
	 */
	public static Target makeCOLOR_DOT()
	{
		Target ret = new Target();
		ret.type = TargetType.COLOR_DOTS;
		return ret;
	}
	
	/**
	 * makeIMAGE - Makes an IMAGE target.
	 * @return New IMAGE target.
	 */
	public static Target makeIMAGE(String im, Point2d imsz)
	{
		Target ret = new Target();
		ret.type = TargetType.IMAGE;
		ret.referenceImageFilename = im;
		ret.referenceImageSize = imsz;
		return ret;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colorPoints == null) ? 0 : colorPoints.hashCode());
		result = prime * result + (hasData ? 1231 : 1237);
		result = prime * result + ((referenceImageFilename == null) ? 0 : referenceImageFilename.hashCode());
		result = prime * result + ((referenceImageSize == null) ? 0 : referenceImageSize.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Target other = (Target) obj;
		if (colorPoints == null) {
			if (other.colorPoints != null)
				return false;
		}
		else if (!colorPoints.equals(other.colorPoints))
			return false;
		if (hasData != other.hasData)
			return false;
		if (referenceImageFilename == null) {
			if (other.referenceImageFilename != null)
				return false;
		}
		else if (!referenceImageFilename.equals(other.referenceImageFilename))
			return false;
		if (referenceImageSize == null) {
			if (other.referenceImageSize != null)
				return false;
		}
		else if (!referenceImageSize.equals(other.referenceImageSize))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	
}
