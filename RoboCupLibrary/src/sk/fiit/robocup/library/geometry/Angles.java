package sk.fiit.robocup.library.geometry;

import static java.lang.Math.*;

/**
 *  Angles.java
 *		
 *		Library class dealing with angular calculations 
 *
 *@Title        Jim
 *@author       $Author: marosurbanec $
 */
public final class Angles{
	private Angles(){}
	
	public static double angleDiff(double first, double second){
		double firstDirection = normalize(first - second);
		double secondDirection = normalize(second - first);
		return min(firstDirection, secondDirection);
	}
	
	public static double angleDiffInDeg(double first, double second){
		return toDegrees(angleDiff(toRadians(first), toRadians(second)));
	}
	
	public static double normalize(double angle){
		if (angle < 2*PI && angle > 0) return angle;
		double closest = IEEEremainder(angle, 2*PI);
		return closest < 0 ? 2*PI + closest : closest;
	}
}