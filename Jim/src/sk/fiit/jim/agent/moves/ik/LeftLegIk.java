package sk.fiit.jim.agent.moves.ik;

import static java.lang.Math.*;
import sk.fiit.robocup.library.geometry.Point3D;

public class LeftLegIk
{
    private double[][] T = new double[4][4];
    
    private double theta1;

    private double theta2;

    private double theta3;

    private double theta4;
    
    private double theta5;
    
    private double theta6;

    private double l1; //TODO

    private double l2; // TODO
    
    public LeftLegIk(Point3D end, Angle angle)
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
    
    public double getTheta4()
    {
        double T14_ = 0.0; // TODO
        double T24_ = 0.0; // TODO
        double T34_ = 0.0; // TODO
        double d = sqrt((-T14_)*(-T14_) + (-T24_ ) * (-T24_) + (-T34_)*(-T34_));
        double nominator = (l1 * l1) + (l2 * l2) - d*d;
        double denominator = 2*l1*l2;
        //+-
        theta4 = (PI - acos(nominator/denominator));
        return theta4;
    }
    
    public double getTheta5()
    {
        return theta5;
    }
}
