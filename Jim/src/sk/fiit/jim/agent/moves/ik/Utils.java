package sk.fiit.jim.agent.moves.ik;

import sk.fiit.jim.agent.moves.Joint;

final class Utils
{
    private Utils()
    {
    }
    
    static boolean validateJointRange(Joint joint, double angle)
    {
        return !Double.isNaN(angle) && angle >= joint.getLow()
                && angle <= joint.getUp();
    }
    
    static double trim(double val)
    {
        return val < 0.01 ? 0 : val < -0.1 ? 0 : val;
    }
}
