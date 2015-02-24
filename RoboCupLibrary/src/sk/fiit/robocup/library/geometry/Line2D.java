package sk.fiit.robocup.library.geometry;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Team High5 (Miro Bimbo)
 * 
 * Respresents 2D line as:
 * two points(x1,y1)(x2,y2),
 * general equation ax + by + c = 0,
 * point(x1,y1) and normal vector(nx,ny),
 * point(x1,y1) and directional vector(kx,ky),
 * 
 */
public class Line2D {
	//points (x1,y1) (x2,y2) lying on line
	private double x1;
	private double y1;
	private double x2;
	private double y2;
	
	//ax + by + c = 0
	private double a;
	private double b;
	private double c;
	
	//normal vector (is orthogonal to line)
	private double nx;
	private double ny;
	
	//directional vector
	private double kx;
	private double ky;
	
	/**
	 * 
	 * Line computed from two points 
	 * Normal and directional vector are computed for first point (x1,y1)
	 * 
	 * @param x1 x axis of first point
	 * @param y1 y axis of first point
	 * @param x2 x axis of second point
	 * @param y2 y axis of second point
	 */
	public Line2D(double x1,double y1,double x2, double y2){
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		
		//direction vector
		kx = x2 - x1;
		ky = y2 - y1;
		
		//normal vector
		nx = -ky;
		ny = kx;
		
		a=nx;
		b=ny;
		c=-(a*x1+b*y1);
	}

	/**
	 * Line computed by point and directional vector.
	 * 
	 * @param x x axis of first point
	 * @param y y axis of first point
	 * @param directionVector x axis of first point
	 */
	public Line2D(double x,double y,Vector2 directionVector){
		this.x1 = x;
		this.y1 = y;
		
		kx = directionVector.getX();
		ky = directionVector.getY();
		
		nx = -ky;
		ny = kx;
		
		a=nx;
		b=ny;
		c=-(a*x1+b*y1);
		
		//aby sa nepovedalo
		x2 = x1+kx;
		y2 = y1+ky;
	}
	
	
	/**
	 * @return normal vector of line (normal vector is orthogonal to line)
	 */
	public Vector2 getNormalVector(){
		return new Vector2(nx,ny);
	}
	
	
	/**
	 * 
	 * Solve general equation of line for given point
	 * 
	 * @param x x axis of point
	 * @param y y axis of point
	 * @return value of a*x+b*y+c 
	 */
	public double solveGeneralEqation(double x, double y){
		return a*x+b*y+c;
	}
	
	/**
	 * 
	 * Returns intersection points for a given circle 
	 * (maximum list of 2 points, minimum empty list).
	 * Works, but needs refactor.
	 * 
	 * source:http://stackoverflow.com/questions/1073336/circle-line-collision-detection
	 * 
	 * */
	public List<Vector2> getCircleIntersection(Circle c){
		List<Vector2> results = new LinkedList<Vector2>();
		// compute the euclidean distance between A and B
		double Ax = x1;
		double Ay = y1;
		double Bx = x2;
		double By = y2;
		
		double LAB = Math.sqrt( (Bx-Ax)*(Bx-Ax)+(By-Ay)*(By-Ay) );

		// compute the direction vector D from A to B
		double Dx = (Bx-Ax)/LAB;
		double Dy = (By-Ay)/LAB;

		// Now the line equation is x = Dx*t + Ax, y = Dy*t + Ay with 0 <= t <= 1.

		// compute the value t of the closest point to the circle center
		double t = Dx*(c.getCenter().getX()-Ax) + Dy*(c.getCenter().getY()-Ay);

		// This is the projection of C on the line from A to B.

		// compute the coordinates of the point E on line and closest to C
		double Ex = t*Dx+Ax;
		double Ey = t*Dy+Ay;

		// compute the euclidean distance from E to C
		double LEC = Math.sqrt( (Ex-c.getCenter().getX())*(Ex-c.getCenter().getX())+(Ey-c.getCenter().getY())*(Ey-c.getCenter().getY()) );

		// test if the line intersects the circle
		if( LEC < c.getRadius() )
		{
		    // compute distance from t to circle intersection point
		    double dt = Math.sqrt( c.getRadius()*c.getRadius() - LEC*LEC);

		    // compute first intersection point
		    double Fx = (t-dt)*Dx + Ax;
		    double Fy = (t-dt)*Dy + Ay;

		    // compute second intersection point
		    double Gx = (t+dt)*Dx + Ax;
		    double Gy = (t+dt)*Dy + Ay;
		    
		    results.add(new Vector2(Fx,Fy));
		    results.add(new Vector2(Gx,Gy));
		}

		// else test if the line is tangent to circle
		else if( LEC == c.getRadius() ){
			results.add(new Vector2(Ex,Ey));
			// tangent point to circle is E
		}
		else{
			// line doesn't touch circle
		}
		return results;
	}
	
	

	@Override
	public String toString() {
		return "Line2D [x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2
				+ ", a=" + a + ", b=" + b + ", c=" + c + ", nx=" + nx + ", ny="
				+ ny + ", kx=" + kx + ", ky=" + ky + "]";
	}

	public double getX1() {
		return x1;
	}

	public double getY1() {
		return y1;
	}

	public double getX2() {
		return x2;
	}

	public double getY2() {
		return y2;
	}

	public double getA() {
		return a;
	}

	public double getB() {
		return b;
	}

	public double getC() {
		return c;
	}

	public double getNx() {
		return nx;
	}

	public double getNy() {
		return ny;
	}

	public double getKx() {
		return kx;
	}

	public double getKy() {
		return ky;
	}

}
