package sk.fiit.jim.agent.moves.ik;

import sk.fiit.jim.agent.moves.Joint;

/**
 * 
 * Utility class that is used for common kinematic calculations.
 * 
 * @author Pidanic
 *
 */
final class KinematicUtils
{
    // radians
    private static final double EPSILON = 0.01;
    
    private KinematicUtils()
    {
        throw new AssertionError(KinematicUtils.class.getName() + " can not be instantiated.");
    }
    
    /**
     * Validates if given angle value in range of given {@link Joint}.
     * @param joint {@link Joint}.
     * @param angle angle value in degrees.
     * @return <code>true</code> if in joint range, otherwise <code>false</code>.
     */
    static boolean validateJointRangeInDegrees(Joint joint, double angle)
    {
        return !Double.isNaN(angle) && angle >= joint.getLow()
                && angle <= joint.getUp();
    }
    
    /**
     * Validates if given angle value in range of given {@link Joint}.
     * @param joint {@link Joint}.
     * @param angle angle value in radians.
     * @return <code>true</code> if in joint range, otherwise <code>false</code>.
     */
    static boolean validateJointRangeInRadians(Joint joint, double angle)
    {
        return !Double.isNaN(angle) && angle >= Math.toRadians(joint.getLow())
                && angle <= Math.toRadians(joint.getUp());
    }
    
    /**
     * <p>This method validates and corrects fractions inaccuracies of floating points 
     * if computed angle in radians is out of range of inverse trigonometric functions <tt>arcsine</tt> and <tt>arccosine</tt>.
     * </p>
     * <p>
     * angle is validate against &epsilon; constant of value <tt>0.01 rad</tt> approx. <tt>0.5°</tt. 
     * </p>
     * @param angle angle to validate.
     * @return -1 if angle is lower than -1 and is in <pre>|angle + 1| < &epsilon; </pre>
     *         1 if angle is greater than 1 and is in <pre>|angle - 1| < &epsilon; </pre>
     *         angle value otherwise.
     */
    static double validateArcsinArccosRange(double angle)
    {
        if(angle < -1 && Math.abs(angle + 1) < EPSILON)
        {
            return -1;
        }
        if(angle > 1 && Math.abs(angle - 1) < EPSILON)
        {
            return 1;
        }
        return angle;
    }
}
