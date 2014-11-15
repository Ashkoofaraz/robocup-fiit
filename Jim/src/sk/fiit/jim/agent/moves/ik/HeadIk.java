package sk.fiit.jim.agent.moves.ik;

import static java.lang.Math.*;
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
    
    private double[][] T = new double[4][4];
    
    public HeadIk(Point3D end, Angle angle)
    {
        double ax = angle.getAx();
        double ay = angle.getAy();
        double az = angle.getAz();
        double px = end.x;
        double py = end.y;
        double pz = end.z;
        T[0][0] = cos(ax) * cos(az);
        T[0][1] = -1 * cos(ax) * sin(az) + sin(ax) * sin(ay) * cos(az);
        T[0][2] = sin(ax) * sin(az) + cos(ax) * sin(ay) * cos(az);
        T[0][3] = px;
        T[1][0] = cos(ay) * sin(az);
        T[1][1] = cos(ax) * cos(az) + sin(ax) * sin(ay) * sin(az);
        T[1][2] = -1 * sin(ax) * cos(az) + cos(ax) * sin(ay) * sin(az);
        T[1][3] = py;
        T[2][0] = -1 * sin(ay);
        T[2][1] = sin(ax) * cos(az);
        T[2][2] = cos(ax) * cos(ay);
        T[2][3] = pz;
        T[3][0] = 0;
        T[3][1] = 0;
        T[3][2] = 0;
        T[3][3] = 1;
    }
    public double getTheta2()
    {
        theta2 = asin((-1*T[3][3] + l3)/(sqrt(l1*l1 + l2*l2))) - atan(l1/l2) + PI/2; 
        return theta2;
    }
    
    public double getTheta2_2()
    {
        theta2 = getTheta2();
        theta2 = PI - theta2;
        return theta2;
    }
    
    public double getTheta1()
    {
        
        double denominator = l2 * cos(theta2 - PI/2) - l1 * sin(theta2 - PI/2) ;
        theta1 = acos(T[0][3] / denominator);
        // +-theta1
        return theta1;
    }
    
}
