package sk.fiit.jim.agent.moves.ik;

import java.util.HashMap;
import java.util.Map;

import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.robocup.library.geometry.Point3D;
import static java.lang.Math.*;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.*;

public class LeftArmIk
{
    private double theta1;

    private double theta2;

    private double theta3;

    private double theta4;

    // mal by som si dat pozor na osi a stranu?
    private double l1 = SHOULDER_OFFSET_Y + ELBOW_OFFSET_Y; // ShoulderOffsetY +
                                                            // ElbowOffsetY

    private double l2 = SHOULDER_OFFSET_Z; // ShoulderOffsetZ

    private double l3 = UPPER_ARM_LENGTH; // UpperArmLength

    private double l4 = HAND_OFFSET_X + LOWER_ARM_LENGTH; // HandOffsetX +
                                                          // LowerArmLength.

    // leftShoulderPitch (sx, sy, sz) = (0, l1, l2)

    // treba si dat pozor, ktora os, prava alebo lava?
    private double sx = 0;

    private double sy = l1;

    private double sz = l2;

    private double[][] T = new double[4][4];

    public LeftArmIk(Point3D endpoint, Angle angle)
    {
        double ax = angle.getAx();
        double ay = angle.getAy();
        double az = angle.getAz();
        double px = endpoint.x;
        double py = endpoint.y;
        double pz = endpoint.z;
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

    public LeftArmIk(double[][] target)
    {
        T = target;
    }

    // radians
    double getTheta4()
    {
        // TODO optimalizuj odstran mocninu a odmocninu
        double d = sqrt((sx - T[0][3]) * (sx - T[0][3]) + (sy - T[1][3])
                * (sy - T[1][3]) + (sz - T[2][3]) * (sz - T[2][3]));
        double nominator = l3 * l3 + l4 * l4 - d * d;
        double denominator = 2 * l3 * l4;
        theta4 = -1 * (PI - acos(nominator / denominator));
        return theta4;
    }

    double getTheta2()
    {
        double nominator = T[1][3] - l1
                - ((l4 * sin(theta4) * T[1][1]) / (cos(theta4)));
        double denominator = l3 + l4 * cos(theta4) + l4
                * (sin(theta4) * sin(theta4)) / cos(theta4);
        theta2 = acos(nominator / denominator);
        // +  PI / 2 in result
        return theta2 ;
    }

    double getTheta2_b()
    {
        theta2 = -1 * theta2;
        return theta2;
    }

    double getTheta3_1()
    {
        theta3 = -1 * asin(T[1][2] / (sin(theta2 - PI / 2)));
        return theta3;
    }

    double getTheta3_2()
    {
        theta3 = -1 *  theta3 - PI;
        return theta3;
    }

    double getTheta1()
    {
        if(abs(theta3) != PI / 2)
        {
            double nominator = T[2][2]
                    + ((T[0][2] * sin(-1 * theta3) * cos(theta3 - PI / 2)) / (cos(-1
                            * theta3)));
            double denominator = cos(-1 * theta3)
                    + (cos(theta2 - PI / 2) * cos(theta2 - PI / 2)
                            * sin(-1 * theta3) * sin(-1 * theta3))
                    / (cos(-1 * theta3));
            // TODO +- theta1
            theta1 = acos(nominator / denominator);
        }
        else if((abs(theta3) == PI / 2) && theta2 != 0.0)
        {
            // TODO +- theta1
            theta1 = acos((T[0][3]) / (cos(theta2 - PI / 2) * sin(-1 * theta3)));
        }
        else if((abs(theta3) == PI / 2) && theta2 == 0.0)
        {
//            double[][] T_ = Constants.inverseMatrix(A4End);
//            double T14_ = T_[0][3];
            // T*(A4end^-1)
            double[][] A4End = MatrixOperations.createTranslation(HAND_OFFSET_X + LOWER_ARM_LENGTH, 0, 0);
            double[][] T_ = MatrixOperations.mult(T, MatrixOperations.inverse(A4End));
            // TODO +- theta1
            theta1 = acos(T_[1][3] / l3);
        }
        return theta1;
    }

    public Map<Joint, Double> getResult()
    {
        Map<Joint, Double> result = new HashMap<Joint, Double>();
        theta4 = toDegrees(getTheta4());
        if(validateJointRange(Joint.LAE4, theta4))
        {
            result.put(Joint.LAE4, theta4);
            theta2 = toDegrees(getTheta2() + PI / 2);
            if(validateJointRange(Joint.LAE2, theta2))
            {
                result.put(Joint.LAE2, theta2);
            }
            else 
            {
                theta2 = toRadians(theta2 - 90);
                theta2 = toDegrees(getTheta2_b() +  PI / 2);
                if(validateJointRange(Joint.LAE2, theta2));
                {
                    result.put(Joint.LAE2, theta2);
                }
            }
            
            theta3 = toDegrees(getTheta3_1());
            if(validateJointRange(Joint.LAE3, theta3))
            {
                result.put(Joint.LAE3, theta3);
            }
            else 
            {
                theta3 = toDegrees(getTheta3_2());
                if(validateJointRange(Joint.LAE3, theta3));
                {
                    result.put(Joint.LAE3, theta3);
                }
            }
            theta1 = (getTheta1());
            if(validateJointRange(Joint.LAE1, theta1))
            {
                result.put(Joint.LAE1, theta1);
            }
            else
            {
                theta1 = -theta1;
                if(validateJointRange(Joint.LAE1, theta1))
                {
                    result.put(Joint.LAE1, theta1);
                }
            }
        }
        return result;
    }

    private static boolean validateJointRange(Joint joint, double angle)
    {
        return Double.NaN != angle && angle >= joint.getLow()
                && angle <= joint.getUp();
    }
}
