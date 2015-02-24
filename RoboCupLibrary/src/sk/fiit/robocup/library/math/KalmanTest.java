package sk.fiit.robocup.library.math;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import sk.fiit.robocup.library.geometry.Vector3D;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;

import static org.junit.Assert.assertThat;

/**
 *  KalmanTest.java
 *
 *@Title        Jim
 *@author       $Author: marosurbanec $
 */
public class KalmanTest{

	private static final double STANDART_NOISE = .05d;
	
	@Test
	public void testCorrections(){
		KalmanForVector filter = new KalmanForVector(.475, .375);
		double standartNoiseErrorReduction = calculateErrorReduction(filter, measurements(STANDART_NOISE), series());
		assertThat(standartNoiseErrorReduction, is(greaterThan(0.0)));
		double increasedNoiseErrorReduction = calculateErrorReduction(filter, measurements(3*STANDART_NOISE), series());
		System.out.printf("Normal noise: %2.3f%%, Triple noise: %2.3f%%\n", standartNoiseErrorReduction*100.0, increasedNoiseErrorReduction*100.0);
//		more noise SHOULD mean better noise reduction. However, noise is random, 
//		meaning better noise reduction is not a always to be excepted
//		assertTrue("More noise, less error reduction?", standartNoiseErrorReduction < increasedNoiseErrorReduction);
	}
	
	private Vector3D[] series(){
		return new Vector3D[]{Vector3D.cartesian(6, 4, -0.5), Vector3D.cartesian(6.03, 4, -0.51), Vector3D.cartesian(6.07, 4.01, -0.49),
					Vector3D.cartesian(6.11, 4, -0.51), Vector3D.cartesian(6.14, 4.03, -0.5), Vector3D.cartesian(6.18, 4.02, -0.52), Vector3D.cartesian(6.22, 4.0, -0.49),
					Vector3D.cartesian(6.25, 3.97, -0.51), Vector3D.cartesian(6.27, 4.01, -0.53), Vector3D.cartesian(6.32, 4.02, -0.52), Vector3D.cartesian(6.36, 3.99, -0.49)};
	}
	
	private Vector3D[] measurements(double noise){
		List<Vector3D> measurements = new ArrayList<Vector3D>();
		for (Vector3D real : series()){
			measurements.add(addNoiseToVector(noise, real));
		}
		return measurements.toArray(new Vector3D[]{});
	}
	
	private double calculateErrorReduction(KalmanForVector filter, Vector3D[] measurements, Vector3D[] real){
		double totalErrorPredicted = 0;
		double totalErrorMeasured = 0;
		
		for (int i = 0; i < measurements.length ; i++){
			Vector3D measure = measurements[i];
			Vector3D predicted = filter.update(measure);
			Vector3D realValue = real[i];
			
			Vector3D diff = predicted.subtract(realValue);
			Vector3D observedDiff = measure.subtract(realValue);
			totalErrorMeasured += observedDiff.getR();
			totalErrorPredicted += diff.getR();
		}
		
		return (totalErrorMeasured - totalErrorPredicted) / totalErrorMeasured;
	}

	private Vector3D addNoiseToVector(double noise, Vector3D real){
		double x =  real.getX() + (Math.random() - .5)*noise*2.0;
		double y =  real.getY() + (Math.random() - .5)*noise*2.0;
		double z =  real.getZ() + (Math.random() - .5)*noise*2.0;
		return Vector3D.cartesian(x, y, z);
	}
	
	@Test
	public void reasonableNoiseCovariances(){
		double[] Qs = {.39, .5, .55, .6, .65, .45, .475};
		double[] Rs = {.43, .34, .5, .3, .4, .35, .375};
		double bestReduction = -10;
		double[] best = {};
		Vector3D[] measurements = measurements(STANDART_NOISE);
		Vector3D[] real = series();
		int better = 0;
		
		for (double Q : Qs){
			for (double R : Rs){
				double errorReduction = calculateErrorReduction(new KalmanForVector(Q, R), measurements, real);
				if (errorReduction > 0.0)
					better++;
				if (errorReduction > bestReduction){
					best = new double[]{Q, R};
					bestReduction = errorReduction; 
				}
			}
		}
		//at least 80% of the adjustments must be better
		assertThat(better, is(greaterThan((int)(Qs.length * Rs.length * 0.80))));
		
		System.out.printf("Best error reduction for Q=%.3f, R=%.3f \n", best[0], best[1]);
	}
}