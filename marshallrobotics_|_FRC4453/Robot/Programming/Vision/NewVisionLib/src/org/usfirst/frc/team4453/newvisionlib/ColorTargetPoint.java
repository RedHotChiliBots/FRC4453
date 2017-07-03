package org.usfirst.frc.team4453.newvisionlib;

/**
 * Class representing a point on a target.
 * @author conner
 *
 */
public class ColorTargetPoint implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8542923611130979899L;

	/**
	 * Id of the point. Unused in vision processing, only available for convenience.
	 */
	public String id;
	
	/**
	 * Mean color of this point. Only used in COLOR_DOT targets.
	 */
	public Color color;
	
	/**
	 * Range of accepted color values for this point. Only used in COLOR_DOT targets.
	 */
	public Color range;
	
	/**
	 * Position of the point relative to the target, ie. model space.
	 */
	public Point3d mdlCoord;
	
	/**
	 * Position of the point on the frame. Filled in by the vision processing code. Only used in COLOR_DOT targets.
	 */
	public Point2d imgCoord;
	
	/**
	 * Position of the point relative to the robot, ie. robot space. Filled in by vision processing code.
	 */
	public Point3d tgtCoord;
	/**
	 * If true, point is not used in vision calulations. All values except for mdlCoord and tgtCoord are unused and not filled in.
	 * This type of point can be used by any type of target.
	 */
	public boolean onlyTranslate = false;
	
	/**
	 * Makes a point for a COLOR_DOT target.
	 * @param i ID of point.
	 * @param c Mean color of point.
	 * @param r Range of accepted color values.
	 * @param mc The points position in model space.
	 * @return The new point.
	 */
	public static ColorTargetPoint makeForCOLOR_DOT(String i, Color c, Color r, Point3d mc)
	{
		ColorTargetPoint ret = new ColorTargetPoint();
		ret.id = i;
		ret.color = c;
		ret.range = r;
		ret.mdlCoord = mc;
		ret.onlyTranslate = false;
		return ret;
	}
	
	/**
	 * Makes a new point for IMAGE targets.
	 * @param i ID of point.
	 * @param mc The points position in model space.
	 * @return The new point.
	 */
	public static ColorTargetPoint makeForIMAGE(String i, Point3d mc)
	{
		ColorTargetPoint ret = new ColorTargetPoint();
		ret.id = i;
		ret.mdlCoord = mc;
		ret.onlyTranslate = false;
		return ret;
	}
	
	/**
	 * Makes a translation-only point. Can be used for any target valid type.
	 * @param i ID of point.
	 * @param mc The points position in model space.
	 * @return The new point.
	 */
	public static ColorTargetPoint makeForTranslate(String i, Point3d mc)
	{
		ColorTargetPoint ret = new ColorTargetPoint();
		ret.id = i;
		ret.mdlCoord = mc;
		ret.onlyTranslate = true;
		return ret;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imgCoord == null) ? 0 : imgCoord.hashCode());
		result = prime * result + ((mdlCoord == null) ? 0 : mdlCoord.hashCode());
		result = prime * result + (onlyTranslate ? 1231 : 1237);
		result = prime * result + ((range == null) ? 0 : range.hashCode());
		result = prime * result + ((tgtCoord == null) ? 0 : tgtCoord.hashCode());
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
		ColorTargetPoint other = (ColorTargetPoint) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		}
		else if (!color.equals(other.color))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (imgCoord == null) {
			if (other.imgCoord != null)
				return false;
		}
		else if (!imgCoord.equals(other.imgCoord))
			return false;
		if (mdlCoord == null) {
			if (other.mdlCoord != null)
				return false;
		}
		else if (!mdlCoord.equals(other.mdlCoord))
			return false;
		if (onlyTranslate != other.onlyTranslate)
			return false;
		if (range == null) {
			if (other.range != null)
				return false;
		}
		else if (!range.equals(other.range))
			return false;
		if (tgtCoord == null) {
			if (other.tgtCoord != null)
				return false;
		}
		else if (!tgtCoord.equals(other.tgtCoord))
			return false;
		return true;
	}
	
	
}
