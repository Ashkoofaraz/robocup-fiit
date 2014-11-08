package sk.fiit.jim.agent.moves.ik;

import sk.fiit.jim.agent.moves.Joint;

final class Utils
{
    private Utils()
    {
    }
    
    static boolean validateJointRange(Joint joint, double angle)
    {
        return Double.NaN != angle && angle >= joint.getLow()
                && angle <= joint.getUp();
    }
}
