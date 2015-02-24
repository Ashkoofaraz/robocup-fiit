/**
 * Name:    XYPoint.java
 * Created: 26.2.2012
 * 
 * @author: MiroBimbo
 */
package sk.fiit.robocup.library.geometry;

/**
 * Represents 2D point
 * 
 * @author Miro Bimbo
 *
 */
public class Vector2 {
	
	private double x;
	private double y;
	
	public Vector2(double x,double y){
		this.x=x;
		this.y=y;
	}
	
	/**
	 * @param vector2
	 */
	public Vector2(Vector2 vector2) {
		this.x = vector2.getX();
		this.y = vector2.getY();
	}

	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public static Vector2 interpolate(Vector2 from, Vector2 to, double timelinePosition) {
		Vector2 f = (Vector2) from;
		Vector2 t = (Vector2) to;
		Vector2 current = new Vector2(f.getX() + timelinePosition * (t.getX() - f.getX()),
				f.getY() + timelinePosition * (t.getY() - f.getY()));	
	    return current;
	}

	@Override
	public String toString() {
		return "Vector2 [x=" + x + ", y=" + y + "]";
	}
	
	

}
