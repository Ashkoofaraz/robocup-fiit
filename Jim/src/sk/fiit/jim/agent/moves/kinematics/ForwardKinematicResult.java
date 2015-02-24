package sk.fiit.jim.agent.moves.kinematics;

import static java.lang.Math.atan2;
import static java.lang.Math.sqrt;
import sk.fiit.robocup.library.geometry.Point3D;

/**
 * 
 * Class that encapsulates result from transformation matrix of kinematics
 * chains and calculates positions and orientation of endeffector.
 * 
 * @author Pidanic
 *
 */
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
        ax = atan2(T.getValueAt(2, 1), T.getValueAt(2, 2));
        ay = atan2(-T.getValueAt(2, 0),
                sqrt(T.getValueAt(2, 1) * T.getValueAt(2, 1) + T.getValueAt(2, 2) * T.getValueAt(2, 2)));
        az = atan2(T.getValueAt(1, 0), T.getValueAt(0, 0));
    }

    @Override
    public String toString()
    {
        return String.format("px py pz [%.2f %.2f %.2f] ax ay az [%.2f %.2f %.2f]", px, py, pz, ax, ay, az);
    }

    /**
     * 
     * @return result position of the end effector in 3-dimensional space.
     */
    public Point3D getEndPoint()
    {
        return new Point3D(px, py, pz);
    }

    /**
     * 
     * @return orientation of the end effector in 3-dimensional space about
     *         corresponding axes.
     */
    public Orientation getOrientation()
    {
        return Orientation.fromRadians(ax, ay, az);
    }
}
