package org.usfirst.frc.team4453.newvisionlib;

/**
 * Enum for types of targets.
 * @author conner
 *
 */
public enum TargetType implements java.io.Serializable {
	/**
	 * An invalid target.
	 */
	INVALID,
	
	/**
	 * Target that uses colored dots.
	 */
	COLOR_DOTS,
	
	/**
	 * Target using a reference image.
	 */
	IMAGE;
}
