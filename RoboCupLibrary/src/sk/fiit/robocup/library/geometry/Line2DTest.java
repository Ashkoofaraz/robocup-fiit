package sk.fiit.robocup.library.geometry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class Line2DTest {
	
	@Test
	public void twoPointsInitializationTest(){
		Line2D line = new Line2D(1,2,3,4);
		assertEquals(1.0,line.getX1(),0.01);
		assertEquals(2.0,line.getY1(),0.01);
		assertEquals(3.0,line.getX2(),0.01);
		assertEquals(4.0,line.getY2(),0.01);
		
		assertEquals(2.0,line.getKx(),0.01);
		assertEquals(2.0,line.getKy(),0.01);
		
		assertEquals(-2.0,line.getNx(),0.01);
		assertEquals(2.0,line.getNy(),0.01);
		
		assertEquals(-2.0,line.getA(),0.01);
		assertEquals(2.0,line.getB(),0.01);
		assertEquals(-2.0,line.getC(),0.01);
	}
	
	@Test
	public void getCircleIntersectionTest1(){
		Line2D line = new Line2D(0,0,2,0);
		Circle c = new Circle(new Vector2(1,0),1);
		
		List<Vector2> asd = line.getCircleIntersection(c);
		assertEquals(0.0,asd.get(0).getX(),0.01);
		assertEquals(0.0,asd.get(0).getY(),0.01);
		
		assertEquals(2.0,asd.get(1).getX(),0.01);
		assertEquals(0.0,asd.get(1).getY(),0.01);	
	}
	
	@Test
	public void getCircleIntersectionTest2(){
		Line2D line = new Line2D(0,0,1,1);
		Circle c = new Circle(new Vector2(0,0),1);
		double x = -Math.sqrt(2)/2;
		
		List<Vector2> asd = line.getCircleIntersection(c);
		assertEquals(x,asd.get(0).getX(),0.01);
		assertEquals(x,asd.get(0).getY(),0.01);
		
		assertEquals(-x,asd.get(1).getX(),0.01);
		assertEquals(-x,asd.get(1).getY(),0.01);	
	}
	
	
	@Test
	public void wtfTest2(){
		Line2D line = new Line2D(0.00,-3.00,10.0,0.0);
		Circle c = new Circle(new Vector2(0.00,-3.00),0.5);
		
		List<Vector2> asd = line.getCircleIntersection(c);
		assertEquals(2,asd.size());
	}
	
	@Test
	public void numberOfReturnedPointsTest(){
		Line2D line = new Line2D(0.06,-3.18,10.5,0);
		
		assertEquals(-3.18,line.getA(),0.01);
		assertEquals(10.44,line.getB(),0.01);
		assertEquals(33.38,line.getC(),0.01);
		
		Circle c = new Circle(new Vector2(0.06,-3.18),0.5);
		
		List<Vector2> asd = line.getCircleIntersection(c);
		assertEquals(2,asd.size());
	}
	
	@Test
	public void frontBackTest(){
		//ball is in the middle of the field, target is right goal
		Line2D line = new Line2D(0.00,0.00,10.5,0);
		Line2D ort = new Line2D(0.00,0.00,line.getNormalVector());
		
		//behind ball
		assertTrue(ort.solveGeneralEqation(10, 1)<0);
		//in front of ball
		assertTrue(ort.solveGeneralEqation(-10, 1)>0);
		
		//right
		assertTrue(line.solveGeneralEqation(1, -10)<0);
		//left
		assertTrue(line.solveGeneralEqation(1, 10)>0);
	}
	
	@Test
	public void frontBackTest2(){
		Line2D line = new Line2D(0.00,-1.00,1.0,0.0);
		Line2D ort = new Line2D(0.00,-1.00,line.getNormalVector());
		
		//behind
		assertTrue(ort.solveGeneralEqation(1, -1)<0);
		assertTrue(ort.solveGeneralEqation(0, 0)<0);
		
		//in front of
		assertTrue(ort.solveGeneralEqation(-1, -1)>0);
		assertTrue(ort.solveGeneralEqation(0, -2)>0);
		
		//left
		assertTrue(line.solveGeneralEqation(0, 0)>0);
		assertTrue(line.solveGeneralEqation(-1, -1)>0);
		
		//right
		assertTrue(line.solveGeneralEqation(0,-2)<0);
		assertTrue(line.solveGeneralEqation(1, -1)<0);
	}

}
