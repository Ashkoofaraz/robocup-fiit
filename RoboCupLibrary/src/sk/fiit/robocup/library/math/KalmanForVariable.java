package sk.fiit.robocup.library.math;

/**
 *  KalmanForVariable.java
 *  
 *  Kalman filter tracing a single observable variable
 *
 *@Title        Jim
 *@author       $Author: marosurbanec $
 */
class KalmanForVariable{
	double x_est_last = Double.NEGATIVE_INFINITY;
	double P_last = 0;
	//the noise in the system
	double Q;
	double R;
	
	double K;
	double P;
	double P_temp;
	double x_temp_est;
	double x_est;
	
	public KalmanForVariable(double Q, double R){
		this.Q = Q;
		this.R = R;
	}
	
	public double update(double observed){
		if (x_est_last == Double.NEGATIVE_INFINITY)
			x_est_last = observed;
		x_temp_est = x_est_last;
		P_temp = P_last + Q;
		//calculate the Kalman gain
		K = (float)(P_temp * (1.0/(P_temp + R)));
		x_est = x_temp_est + K * (observed - x_temp_est); 
		P = (1- K) * P_temp;
		
		P_last = P;
		x_est_last = x_est;
		return x_est;
	}
}