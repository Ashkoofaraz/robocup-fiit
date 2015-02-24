package sk.fiit.robocup.library.geometry;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *  AnglesTest.java
 *
 *@Title        Jim
 *@author       $Author: marosurbanec $
 */
public class AnglesTest{
	
	@Test
	public void normalize(){
		double tooLargeAngle = 7*Math.PI;
		assertEquals(Math.PI, Angles.normalize(tooLargeAngle), .01);
	}
	
	@Test
	public void angleDiff(){
		assertEquals(Angles.angleDiff(-Math.PI / 4.0, Math.PI / 4.0), Math.PI / 2.0, .01);
		assertEquals(Angles.angleDiff(Math.PI / 4.0, Math.PI / 8.0), Math.PI / 8.0, .01);
		assertEquals(Angles.angleDiff(Math.PI / 4.0, Math.PI / 4.0), 0.0, .01);
		assertEquals(Angles.angleDiff(0.0, 3*Math.PI), Math.PI, .01);
		assertEquals(Angles.angleDiff(3.0*Math.PI/2.0, -3.0*Math.PI/2.0), Math.PI, .01);
		assertEquals(Angles.angleDiffInDeg(90.0, 120.0), 30.0, .01);
	}
	@Test
	public void angleinclude(){
		Angles straight_range2 = new Angles();
		straight_range2.setDegree(345.0, 360.0);
		assertFalse(straight_range2.include(6.3));
		
		
	
	}
}