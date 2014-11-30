package sk.fiit.jim.agent.moves.ik;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.acos;
import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.ELBOW_OFFSET_Y;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.HAND_OFFSET_X;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.LOWER_ARM_LENGTH;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.SHOULDER_OFFSET_Y;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.SHOULDER_OFFSET_Z;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.UPPER_ARM_LENGTH;

import java.util.HashMap;
import java.util.Map;

import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.robocup.library.geometry.Point3D;

/**
 * 
 * The purpose of this class is encapsulating inverse kinematics calculations of
 * left arm.
 * 
 * @author Pidanic
 *
 */
class LeftArmIk
{
    private double theta1;

    private double theta2;

    private double theta3;

    private double theta4;

    private double l1 = SHOULDER_OFFSET_Y + ELBOW_OFFSET_Y;

    private double l2 = SHOULDER_OFFSET_Z;

    private double l3 = UPPER_ARM_LENGTH;

    private double l4 = HAND_OFFSET_X + LOWER_ARM_LENGTH;

    // leftShoulderPitch (sx, sy, sz) = (0, l1, l2)
    private double sx = 0;

    private double sy = l1;

    private double sz = l2;

    private Matrix T;

    public LeftArmIk(Point3D endpoint, Orientation angle)
    {
        T = Matrix.createTransformation(endpoint, angle);
    }

    public LeftArmIk(Matrix target)
    {
        T = target;
    }

    // radians
    double getTheta4()
    {
        double T03 = T.getValueAt(0, 3);
        double T13 = T.getValueAt(1, 3);
        double T23 = T.getValueAt(2, 3);
        // TODO optimalizuj odstran mocninu a odmocninu
        double d = sqrt((sx - T03) * (sx - T03) + (sy - T13) * (sy - T13) + (sz - T23) * (sz - T23));
        double nominator = l3 * l3 + l4 * l4 - d * d;
        double denominator = 2 * l3 * l4;
        theta4 = -1 * (PI - acos(nominator / denominator));
        return theta4;
    }

    double getTheta2()
    {
        double T13 = T.getValueAt(1, 3);
        double T11 = T.getValueAt(1, 1);
        double nominator = T13 - l1 - ((l4 * sin(theta4) * T11) / (cos(theta4)));
        double denominator = l3 + l4 * cos(theta4) + l4 * (sin(theta4) * sin(theta4)) / cos(theta4);
        theta2 = acos(nominator / denominator);
        // + PI / 2 in result
        return theta2;
    }

    double getTheta2_b()
    {
        theta2 = -1 * theta2;
        return theta2;
    }

    double getTheta3_1()
    {
        theta3 = -1 * asin(T.getValueAt(1, 2) / (sin(theta2 - PI / 2)));
        return theta3;
    }

    double getTheta3_2()
    {
        theta3 = -1 * theta3 - PI;
        return theta3;
    }

    double getTheta1()
    {
        if(abs(theta3) != PI / 2)
        {
            double T22 = T.getValueAt(2, 2);
            double T02 = T.getValueAt(0, 2);

            double nominator = T22 + ((T02 * sin(-1 * theta3) * cos(theta3 - PI / 2)) / (cos(-1 * theta3)));
            double denominator = cos(-1 * theta3)
                    + (cos(theta2 - PI / 2) * cos(theta2 - PI / 2) * sin(-1 * theta3) * sin(-1 * theta3))
                    / (cos(-1 * theta3));
            // TODO +- theta1
            theta1 = acos(nominator / denominator);
        }
        else if((abs(theta3) == PI / 2) && theta2 != 0.0)
        {
            // TODO +- theta1
            theta1 = acos((T.getValueAt(0, 3)) / (cos(theta2 - PI / 2) * sin(-1 * theta3)));
        }
        else if((abs(theta3) == PI / 2) && theta2 == 0.0)
        {
            // T*(A4end^-1)
            Matrix T_ = T.mult(Matrix.AendLeftArm.inverse());
            double T_13 = T_.getValueAt(1, 3);
            // TODO +- theta1
            theta1 = acos(T_13 / l3);
        }
        return theta1;
    }

    /**
     * Calculates and returns result of inverse kinematics for left arm.
     * 
     * @see Joint
     */
    public Map<Joint, Double> getResult()
    {
        Map<Joint, Double> result = new HashMap<Joint, Double>();
        theta4 = toDegrees(getTheta4());
        if(KinematicUtils.validateJointRangeInDegrees(Joint.LAE4, theta4))
        {
            result.put(Joint.LAE4, theta4);
            theta2 = toDegrees(getTheta2() + PI / 2);
            if(KinematicUtils.validateJointRangeInDegrees(Joint.LAE2, theta2))
            {
                result.put(Joint.LAE2, theta2);
            }
            else
            {
                theta2 = toRadians(theta2 - 90);
                theta2 = toDegrees(getTheta2_b() + PI / 2);
                if(KinematicUtils.validateJointRangeInDegrees(Joint.LAE2, theta2))
                    ;
                {
                    result.put(Joint.LAE2, theta2);
                }
            }

            theta3 = toDegrees(getTheta3_1());
            if(KinematicUtils.validateJointRangeInDegrees(Joint.LAE3, theta3))
            {
                result.put(Joint.LAE3, theta3);
            }
            else
            {
                theta3 = toDegrees(getTheta3_2());
                if(KinematicUtils.validateJointRangeInDegrees(Joint.LAE3, theta3))
                    ;
                {
                    result.put(Joint.LAE3, theta3);
                }
            }
            theta1 = toDegrees(getTheta1());
            if(KinematicUtils.validateJointRangeInDegrees(Joint.LAE1, theta1))
            {
                result.put(Joint.LAE1, theta1);
            }
            else
            {
                theta1 = -theta1;
                if(KinematicUtils.validateJointRangeInDegrees(Joint.LAE1, theta1))
                {
                    result.put(Joint.LAE1, theta1);
                }
            }
        }
        return result;
    }
}
