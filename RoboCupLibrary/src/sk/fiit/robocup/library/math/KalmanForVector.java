package sk.fiit.robocup.library.math;

import sk.fiit.robocup.library.geometry.Vector3D;

/**
 *  KalmanForVector.java
 *  
 *  Kalman filter predicting and updating a {@link Vector3D}
 *
 *@Title        Jim
 *@author       $Author: marosurbanec $
 */
public class KalmanForVector{
	
	KalmanForVariable x;
	KalmanForVariable y;
	KalmanForVariable z;
	
	public KalmanForVector(){
		this(.475, .375);
	}
	
	public KalmanForVector(double q, double r){
		x = new KalmanForVariable(q, r);
		y = new KalmanForVariable(q, r);
		z = new KalmanForVariable(q, r);
	}
	
	
	public Vector3D update(Vector3D observed){
		double x = this.x.update(observed.getX());
		double y = this.y.update(observed.getY());
		double z = this.z.update(observed.getZ());
		return Vector3D.cartesian(x, y, z);
	}
}