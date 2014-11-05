package sk.fiit.jim.agent.moves.ik;

import static java.lang.Math.*;

public class ForwardKinematicResult
{
    private double px;
    
    private double py;
    
    private double pz;
    
    private double ax;
    
    private double ay;
    
    private double az;
    
    public ForwardKinematicResult(double[][] T)
    {
        px = T[0][3];
        py = T[1][3];
        pz = T[2][3];
        ax = atan2(T[2][1], T[2][2]);
        ay = atan2(-T[2][0], sqrt(T[2][1] * T[2][1] + T[2][2] * T[2][2]));
        az = atan2(T[1][0], T[0][0]);
    }

    public double getPx()
    {
        return px;
    }

    public double getPy()
    {
        return py;
    }

    public double getPz()
    {
        return pz;
    }

    public double getAx()
    {
        return ax;
    }

    public double getAy()
    {
        return ay;
    }

    public double getAz()
    {
        return az;
    }

    @Override
    public String toString()
    {
        return String.format("px py pz [%.2f %.2f %.2f] ax ay az [%.2f %.2f %.2f]", px, py, pz, ax, ay, az);
    }
}
