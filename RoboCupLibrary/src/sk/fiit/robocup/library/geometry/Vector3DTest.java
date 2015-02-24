package sk.fiit.robocup.library.geometry;

import org.junit.Test;


import static sk.fiit.robocup.library.geometry.Vector3D.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *  Vector3DTest.java
 *
 *@Title        Jim
 *@author       $Author: marosurbanec $
 */
public class Vector3DTest{
	
	@Test
	public void basicOperations(){
		Vector3D one = cartesian(1, 1, 1);
		assertThat(one.add(cartesian(0, 0, 0)), is(equalTo(one)));
		assertThat(one.add(cartesian(1, 0, 0)), is(equalTo(cartesian(2, 1, 1))));
		assertThat(one.multiply(5.0), is(cartesian(5, 5, 5)));
		assertThat(one.divide(0.2), is(equalTo(cartesian(5, 5, 5))));
		assertThat(one.subtract(cartesian(0, 1, 0)), is(equalTo(cartesian(1, 0, 1))));
		assertThat(one.negate(), is(equalTo(cartesian(-1, -1, -1))));
		assertThat(one.addX(1.0), is(equalTo(cartesian(2, 1, 1))));
		assertThat(one.addY(-1.0), is(equalTo(cartesian(1, 0, 1))));
		assertThat(one.addZ(0.0), is(one));
//		original vector should remain the same
		assertThat(one, is(one));
	}
	
	@Test
	public void conversions(){
		Vector3D one = cartesian(1, 1, 1);
		Vector3D two = cartesian(0, 1, 1);
		Vector3D right = cartesian(-1.0, 1.0, 0.0);
		
		Vector3D cartesian = cartesian(Math.random(), Math.random(), Math.random()); 
		Vector3D spherical = spherical(cartesian.getR(), cartesian.getPhi(), cartesian.getTheta());
		Vector3D spherical2 = spherical(Math.random(), Math.random(), Math.random());
		Vector3D cartesian2 = cartesian(spherical2.getX(), spherical2.getY(), spherical2.getZ());
		
		assertThat(right.getPhi(), is(equalTo(Math.PI / 4.0)));
		assertEquals(one.getR(), Math.sqrt(3.0), .01);
		assertEquals(two.getPhi(), 0.0, .01);
		assertEquals(two.getTheta(), Math.PI / 4.0, .01);
		assertThat(cartesian, is(equalTo(spherical)));
		assertThat(cartesian2, is(equalTo(spherical2)));
	}
	
	@Test
	public void rotations(){
		//the loops is here to measure performance
		for (int i = 0 ; i < 1000 ; i++)
		{
		Vector3D oneAhead = cartesian(0, 1, 0);
		//45 degrees to left
		Vector3D bitToLeft = oneAhead.rotateOverZ(Math.PI / 4.0);
		assertEquals(Math.PI / 4.0, bitToLeft.getPhi(), .01);
		assertEquals(oneAhead.getR(), bitToLeft.getR(), .01);
		Vector3D turnUp = oneAhead.rotateOverX(Math.PI / 2.0);
		assertThat(turnUp, is(cartesian(0, 0, 1)));
		Vector3D left = turnUp.rotateOverY(Math.PI / 2.0);
		assertThat(left, is(cartesian(1, 0, 0)));
		}
	}
	
	@Test
	public void products(){
		//the loops is here to measure performance
		for (int i = 0 ; i < 1000 ; i++)
		{
		Vector3D a = cartesian(0, 1, 0);
		Vector3D b = cartesian(1, 0, 0);
		assertEquals(a.dotProduct(a), 1.0, .01);
		assertEquals(a.dotProduct(b), 0.0, .01);
		assertThat(a.crossProduct(b), is(equalTo(cartesian(0, 0, -1))));
		assertThat(a.crossProduct(a), is(equalTo(cartesian(0, 0, 0))));
		assertThat(b.crossProduct(a), is(equalTo(cartesian(0, 0, 1))));
		}
	}
	
	@Test
	public void setters(){
		Vector3D one = cartesian(1, 1, 1);
		Vector3D eigen = cartesian(0, 1, 0);
		assertThat(one.setX(0), is(equalTo(cartesian(0, 1, 1))));
		assertThat(one.setY(2), is(equalTo(cartesian(1, 2, 1))));
		assertThat(one.setZ(4), is(equalTo(cartesian(1, 1, 4))));
		assertThat(eigen.setR(2), is(equalTo(cartesian(0, 2, 0))));
		assertThat(eigen.setPhi(Math.PI / 2.0), is(equalTo(cartesian(-1, 0, 0))));
		assertThat(eigen.setTheta(Math.PI / 2.0), is(equalTo(cartesian(0, 0, 1))));
		//identity
		assertThat(one, is(one));
		assertThat(eigen, is(eigen));
	}
}