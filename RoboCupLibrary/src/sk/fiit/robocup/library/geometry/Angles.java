package sk.fiit.robocup.library.geometry;

import static java.lang.Math.*;

/**
 *  Angles.java
 *		
 *		Library class dealing with angular calculations 
 *		Uhol od FROM do TO
 *
 *@Title        Jim
 *@author       $Author: marosurbanec $
 */
public final class Angles{
	private double from; //in radian
	private double to;	//in radian
	
	
	public Angles(){
	}
	public Angles(double from, double to){
		this.from = from/ 180*Math.PI;
		this.to = to/180*Math.PI;
	}
	public void setRadian (double from,double to){
		this.from = from;
		this.to = to;
	}
	
	public void setDegree (double from,double to){
		this.from = from/ 180*Math.PI;
		this.to = to/180*Math.PI;
	}
	
	public boolean include (double include_angle){
		if (from < include_angle && to > include_angle)
		return true;
		else return false;
	}
	
	//public static Angles angleRange(Angles from, Angles to){
		//return (from/180.0*Math.PI) (from/180.0*Math::PI)..(to/180.0*Math::PI)
	//}
	
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