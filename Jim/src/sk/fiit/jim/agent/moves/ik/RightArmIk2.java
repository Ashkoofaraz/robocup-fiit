package sk.fiit.jim.agent.moves.ik;

import static java.lang.Math.*;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.*;

import java.util.HashMap;
import java.util.Map;

import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.robocup.library.geometry.Point3D;

// TODO v clanku pri thetha1 nie je abs.hodnota a v diplomovke ano
class RightArmIk2
{
    private double theta1;

    private double theta2;

    private double theta3;

    private double theta4;
    
    private Matrix T;
    
    private Matrix T_;
    
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

    private double sy = -l1;

    private double sz = l2;
    
    public RightArmIk2(Point3D endpoint, Orientation angle)
    {
        T = Matrix.createKinematic(endpoint, angle);
        T_ = T.mult(Matrix.ROTATION_Z_PI_MINUS.inverse());
    }

    // radians
    double getTheta4()
    {
        double T_03 = T_.getValueAt(0, 3);
        double T_13 = T_.getValueAt(1, 3);
        double T_23 = T_.getValueAt(2, 3);
        // TODO optimalizuj odstran mocninu a odmocninu
        double d = sqrt((sx - T_03) * (sx - T_03) + (sy - T_13)
                * (sy - T_23) + (sz - T_23) * (sz - T_23));
        double nominator = l3 * l3 + l4 * l4 - d * d;
        double denominator = 2 * l3 * l4;
        theta4 = PI - acos(nominator / denominator);
        return theta4;
    }
    
    double getTheta2()
    {
        double T_13 = T_.getValueAt(1, 3);
        double T_11 = T_.getValueAt(1, 1);
        double nominator = -T_13 - l1
                - ((l4 * sin(theta4) * T_11) / (cos(theta4)));
        double denominator = l3 + l4 * cos(theta4) + l4
                * (sin(theta4) * sin(theta4)) / cos(theta4);
        theta2 = acos(nominator / denominator);
        // - PI / 2 in result // TODO
        return theta2 ;
    }
    
    double getTheta2_b()
    {
        theta2 = -1 * theta2;
        return theta2;
    }
    
    double getTheta3_1()
    {
        double T_12 = T_.getValueAt(1, 2);
        theta3 = asin(T_12 / (sin(theta2 + PI / 2)));
        return theta3;
    }
    
    double getTheta3_2()
    {
        theta3 = PI - 1 * theta3 ;
        return theta3;
    }
    
    double getTheta1()
    {
        double T_22 = T_.getValueAt(2,2);
        double T_02 = T_.getValueAt(0,2);
        double T_03 = T_.getValueAt(0,3);
        if(theta3 != PI / 2)
        {
            double nominator = T_22
                    + ((T_02 * sin(theta3) * cos(theta3 + PI / 2)) / (cos(theta3)));
            double denominator = cos(theta3)
                    + (cos(theta2 + PI / 2) * cos(theta2 + PI / 2)
                            * sin(theta3) * sin(theta3)) / (cos(theta3));
            // TODO +- theta1
            theta1 = acos(nominator / denominator);
        }
        else if((abs(theta3) == PI / 2) && theta2 != 0.0)
        {
            // TODO +- theta1
            theta1 = acos((T_03) / (cos(theta2 + PI / 2) * sin(theta3)));
        }
        else if((abs(theta3) == PI / 2) && theta2 == 0.0)
        {
            Matrix T__ = T.mult(Matrix.invAendRightArm);
            // TODO +- theta1
            theta1 = acos(T__.getValueAt(1, 3) / l3);
        }
        return theta1;
    }
    
    public Map<Joint, Double> getResult()
    {
        Map<Joint, Double> result = new HashMap<Joint, Double>();
        theta4 = toDegrees(getTheta4());
        if(Utils.validateJointRange(Joint.RAE4, theta4))
        {
            result.put(Joint.RAE4, theta4);
            theta2 = toDegrees(getTheta2() - PI / 2);
            if(Utils.validateJointRange(Joint.RAE2, theta2))
            {
                result.put(Joint.RAE2, theta2);
            }
            else 
            {
                theta2 = toRadians(theta2 + 90);
                theta2 = toDegrees(getTheta2_b() -  PI / 2);
                if(Utils.validateJointRange(Joint.RAE2, theta2));
                {
                    result.put(Joint.RAE2, theta2);
                }
            }
            
            theta3 = toDegrees(getTheta3_1());
            if(Utils.validateJointRange(Joint.RAE3, theta3))
            {
                result.put(Joint.RAE3, theta3);
            }
            else 
            {
                theta3 = toDegrees(getTheta3_2());
                if(Utils.validateJointRange(Joint.RAE3, theta3));
                {
                    result.put(Joint.RAE3, theta3);
                }
            }
            theta1 = (getTheta1());
            if(Utils.validateJointRange(Joint.RAE1, theta1))
            {
                result.put(Joint.RAE1, theta1);
            }
            else
            {
                theta1 = -theta1;
                if(Utils.validateJointRange(Joint.RAE1, theta1))
                {
                    result.put(Joint.RAE1, theta1);
                }
            }
        }
        return result;
    }
}
