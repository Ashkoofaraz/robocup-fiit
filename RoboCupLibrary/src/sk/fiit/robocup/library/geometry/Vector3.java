package sk.fiit.robocup.library.geometry;


//TODO: switch to its immutable counterpart

@Deprecated
public class Vector3 {

	private double x = 0;
	private double y = 0;
	private double z = 0;
	
	public Vector3() {
    }

    public Vector3(double x, double y, double z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}
    
    public Vector3(Vector3 copyFrom) {
        this.x = copyFrom.x;
        this.y = copyFrom.y;
        this.z = copyFrom.z;
    }

	public void setX(double x) {
		this.x = x;
	}

	public double getX() {
		return x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getY() {
		return y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getZ() {
		return z;
	}

	public Vector3 subtract(Vector3 a) {
    	return new Vector3(this.x - a.x, this.y - a.y, this.z - a.z);
    }
	
	public Vector3 addition(Vector3 a) {
    	return new Vector3(this.x + a.x, this.y + a.y, this.z + a.z);
    }
	
	public Vector3 division(double d) {
		return new Vector3(this.x / d, this.y / d, this.z / d);
    }
        

    public double getXYDistanceFrom(Vector3 b) {
        return (Math.sqrt(Math.pow(x - b.x, 2) + Math.pow(y - b.y, 2)));
    }

    public Point3D asPoint3D() {
        return new Point3D(x, y, z);
    }

    @Override
    public String toString() {
        return String.format("X: %.4f Y: %.4f Z: %.4f", x, y, z);
    }


}
