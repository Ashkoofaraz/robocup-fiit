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
    
    static boolean validateJointRange2(Joint joint, double angle)
    {
        return !Double.isNaN(angle) && angle >= Math.toRadians(joint.getLow())
                && angle <= Math.toRadians(joint.getUp());
    }
    
    static double trim(double val)
    {
        return val < 0.5 && val > -0.5 ? 0 : val;
    }
}
