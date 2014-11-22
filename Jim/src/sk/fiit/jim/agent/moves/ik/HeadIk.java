package sk.fiit.jim.agent.moves.ik;

import static java.lang.Math.PI;
import static java.lang.Math.acos;
import static java.lang.Math.asin;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import sk.fiit.robocup.library.geometry.Point3D;

class HeadIk
{
    // milimeters
    private static final double CAMERA_X_TOP = 53.9;
    private static final double CAMERA_X_BOTTOM = 48.8;
    private static final double CAMERA_Z_TOP = 67.9;
    private static final double CAMERA_Z_BOTTOM = 23.8;
    
    private double theta1;
    
    private double theta2;
    
    private double l1 = CAMERA_X_TOP;  // CameraX
    
    private double l2 = CAMERA_Z_TOP; // CameraZ
    
    private double l3 = SimsparkConstants.NECK_OFFSET_Z; // NechOfffsetZ
    
    private Matrix T;
    
    public HeadIk(Point3D end, Orientation angle)
    {
        T = Matrix.createTransformation(end, angle);
    }
    double getTheta2()
    {
        double T33 = T.getValueAt(3, 3);
        theta2 = asin((-1*T33 + l3)/(sqrt(l1*l1 + l2*l2))) - atan(l1/l2) + PI/2; 
        return theta2;
    }
    
    double getTheta2_2()
    {
        theta2 = getTheta2();
        theta2 = PI - theta2;
        return theta2;
    }
    
    double getTheta1()
    {
        double T03 = T.getValueAt(0, 3);
        double denominator = l2 * cos(theta2 - PI/2) - l1 * sin(theta2 - PI/2) ;
        theta1 = acos(T03 / denominator);
        // +-theta1
        return theta1;
    }
    
}
