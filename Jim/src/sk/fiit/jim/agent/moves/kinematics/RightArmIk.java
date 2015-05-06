package sk.fiit.jim.agent.moves.kinematics;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.acos;
import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.ELBOW_OFFSET_Y;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.HAND_OFFSET_X;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.LOWER_ARM_LENGTH;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.SHOULDER_OFFSET_Y;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.SHOULDER_OFFSET_Z;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.UPPER_ARM_LENGTH;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.robocup.library.geometry.Point3D;

/**
 * 
 * The purpose of this class is encapsulating inverse kinematics calculations of
 * right arm.
 * 
 * @author Pidanic
 *
 */
class RightArmIk
{
    private double l1 = SHOULDER_OFFSET_Y + ELBOW_OFFSET_Y;

    private double l2 = SHOULDER_OFFSET_Z;

    private double l3 = UPPER_ARM_LENGTH;

    private double l4 = HAND_OFFSET_X + LOWER_ARM_LENGTH;

    private double sx = 0;

    private double sy = l1;

    private double sz = l2;

    private Matrix T;

    public RightArmIk(Point3D endpoint, Orientation angle)
    {
        this(Matrix.createTransformation(endpoint, angle));
    }

    public RightArmIk(Matrix target)
    {
        T = target;
        T = T.mult(Matrix.R_Z_RIGHT_ARM.inverse());
    }

    // radians
    private List<Double> getTheta4()
    {
        List<Double> result = new ArrayList<>();
        double T03 = T.getValueAt(0, 3);
        double T13 = T.getValueAt(1, 3);
        double T23 = T.getValueAt(2, 3);
        double d = sqrt((sx - T03) * (sx - T03) + (sy - T13) * (sy - T13) + (sz - T23) * (sz - T23));
        double nominator = l3 * l3 + l4 * l4 - d * d;
        double denominator = 2 * l3 * l4;
        double theta4 = PI - acos(KinematicUtils.validateArcsinArccosRange(nominator / denominator));
        if(KinematicUtils.validateJointRangeInRadians(Joint.RAE4, theta4))
        {
            result.add(theta4);
        }
        return result;

    }

    private List<Double> getTheta2(final double theta4)
    {
        List<Double> result = new ArrayList<>();

        double T13 = T.getValueAt(1, 3);
        double T11 = T.getValueAt(1, 1);
        double nominator = -T13 - l1 - ((l4 * sin(theta4) * T11) / (cos(theta4)));
        double denominator = l3 + l4 * cos(theta4) + l4 * (sin(theta4) * sin(theta4)) / cos(theta4);
        double theta2 = acos(KinematicUtils.validateArcsinArccosRange(nominator / denominator));
        if(KinematicUtils.validateJointRangeInRadians(Joint.RAE2, theta2 - PI / 2))
        {
            result.add(theta2 - PI / 2);
        }
        if(KinematicUtils.validateJointRangeInRadians(Joint.RAE2, -theta2 - PI / 2))
        {
            result.add(-theta2 - PI / 2);
        }

        return result;
    }

    private List<Double> getTheta3(final double theta2)
    {
        List<Double> result = new ArrayList<>();
        double theta3 = asin(KinematicUtils.validateArcsinArccosRange(T.getValueAt(1, 2) / (sin(theta2 + PI / 2))));
        if(KinematicUtils.validateJointRangeInRadians(Joint.RAE3, theta3))
        {
            result.add(theta3);
        }
        if(KinematicUtils.validateJointRangeInRadians(Joint.RAE3, PI - theta3))
        {
            result.add(PI - theta3);
        }
        return result;
    }

    private List<Double> getTheta1(final double theta2, final double theta3)
    {
        List<Double> result = new ArrayList<>();
        if(!KinematicUtils.almostEquals(abs(theta3), PI / 2))
        {
            double T22 = T.getValueAt(2, 2);
            double T02 = T.getValueAt(0, 2);

            double nominator = T22 + ((T02 * sin(theta3) * cos(theta3 + PI / 2)) / (cos(theta3)));
            double denominator = cos(theta3)
                    + (cos(theta2 + PI / 2) * cos(theta2 + PI / 2) * sin(theta3) * sin(theta3)) / (cos(theta3));
            // +- theta1
            double theta1 = acos(KinematicUtils.validateArcsinArccosRange(nominator / denominator));
            if(KinematicUtils.validateJointRangeInRadians(Joint.RAE1, theta1))
            {
                result.add(theta1);
            }
            if(KinematicUtils.validateJointRangeInRadians(Joint.RAE1, -theta1))
            {
                result.add(-theta1);
            }
        }
        else if(KinematicUtils.almostEquals(abs(theta3), PI / 2) && !KinematicUtils.almostEquals(theta2, 0.0))
        {
            // +- theta1
            double theta1 = acos(KinematicUtils.validateArcsinArccosRange((T.getValueAt(0, 3))
                    / (cos(theta2 + PI / 2) * sin(theta3))));
            if(KinematicUtils.validateJointRangeInRadians(Joint.RAE1, theta1))
            {
                result.add(theta1);
            }
            if(KinematicUtils.validateJointRangeInRadians(Joint.RAE1, -theta1))
            {
                result.add(-theta1);
            }
        }
        else if(KinematicUtils.almostEquals(abs(theta3), PI / 2) && KinematicUtils.almostEquals(theta2, 0.0))
        {
            // T*(A4end^-1)
            Matrix T_ = T.mult(Matrix.A_END_RIGHT_ARM.inverse());
            double T_13 = T_.getValueAt(1, 3);
            // +- theta1
            double theta1 = acos(KinematicUtils.validateArcsinArccosRange(T_13 / l3));
            if(KinematicUtils.validateJointRangeInRadians(Joint.RAE1, theta1))
            {
                result.add(theta1);
            }
            if(KinematicUtils.validateJointRangeInRadians(Joint.RAE1, -theta1))
            {
                result.add(-theta1);
            }
        }
        return result;
    }

    /**
     * Calculates and returns result of inverse kinematics for right arm.
     * 
     * @see Joint
     */
    public Map<Joint, Double> getResult()
    {
        Map<Joint, Double> result = new HashMap<Joint, Double>();
        List<Double> theta4 = getTheta4();
        for (double t4 : theta4)
        {
            List<Double> theta2 = getTheta2(t4);
            for (double t2 : theta2)
            {
                List<Double> theta3 = getTheta3(t2);
                for (double t3 : theta3)
                {
                    List<Double> theta1 = getTheta1(t2, t3);
                    for (double t1 : theta1)
                    {
                        result.put(Joint.RAE1, toDegrees(t1));
                        result.put(Joint.RAE2, toDegrees(t2));
                        result.put(Joint.RAE3, toDegrees(t3));
                        result.put(Joint.RAE4, toDegrees(t4));
                    }
                }
            }
        }

        return result;
    }
}
