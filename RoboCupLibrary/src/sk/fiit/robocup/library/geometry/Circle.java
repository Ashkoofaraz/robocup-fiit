/**
 * Name:    Circle.java
 * Created: 26.2.2012
 * 
 * @author: Miro Bimbo
 */
package sk.fiit.robocup.library.geometry;



/**
 * Represents 2D circle
 * 
 * @author Miro Bimbo
 *
 */
public class Circle {
	
	private Vector2 center;
	private double radius;
	
	/** 
	 * create circle with 0,0 center and 0 radius
	 */
	public Circle(){
		this.center=new Vector2(0,0);
		this.radius=0;
	}
	
	public Circle(Vector2 center, double radius){
		this.center = center;
		this.radius = radius;
	}
	
	public Vector2 getCenter() {
		return center;
	}

	public double getRadius() {
		return radius;
	}

	@Override
	public String toString() {
		return "Circle [center=" + center + ", radius=" + radius + "]";
	}
	
	


}
