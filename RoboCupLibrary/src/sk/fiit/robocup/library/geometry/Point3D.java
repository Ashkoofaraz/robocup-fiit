/**
 * Name:    Point3D.java
 * Created: Feb 27, 2011
 * 
 * @author: relation
 */
package sk.fiit.robocup.library.geometry;

/**
 * TODO: Replace with a brief purpose of class / interface.
 * 
 * @author relation
 *
 */
public class Point3D {
	public double x, y, z;	//TODO bobo refactor to getter and setter

	public Point3D(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

    public Point3D(String string, String string0, String string1) {
        throw new UnsupportedOperationException("Not yet implemented");
        //Lol?
    }

    @Override
    public String toString() {
        return String.format("%1$.4f %2$.4f %3$.4f", x, y, z);
    }


}
