package sk.fiit.jim.agent.moves.ik;

import static java.lang.Math.atan2;
import static java.lang.Math.sqrt;

class ForwardKinematicResult
{
    private double px;
    
    private double py;
    
    private double pz;
    
    private double ax;
    
    private double ay;
    
    private double az;
    
    public ForwardKinematicResult(Matrix T)
    {
        px = T.getValueAt(0, 3);
        py = T.getValueAt(1, 3);
        pz = T.getValueAt(2, 3);
        ax = atan2(T.getValueAt(2, 1), T.getValueAt(2,2 ));
        ay = atan2(-T.getValueAt(2, 0), sqrt(T.getValueAt(2, 1) * T.getValueAt(2, 1) + T.getValueAt(2, 2) * T.getValueAt(2, 2)));
        az = atan2(T.getValueAt(1, 0), T.getValueAt(0, 0));
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
