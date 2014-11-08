package sk.fiit.jim.agent.moves.ik;

import static java.lang.Math.*;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.*;

import java.util.HashMap;
import java.util.Map;

import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.robocup.library.geometry.Point3D;

public class LeftLegIk
{
    private double theta1;

    private double theta2;

    private double theta3;

    private double theta4;
    
    private double theta5;
    
    private double theta6;

    private double l1 = THIGH_LENGHT; 

    private double l2 = TIBIA_LENGHT;
    
    private double sx = 0;
    
    private double sy = 0;
    
    private double sz = 0; 
    
    private double[][] T = new double[4][4];
    private double[][] T_ = new double[4][4];
    private double[][] T__ = new double[4][4];
    private double[][] T___ = new double[4][4];
    
    public LeftLegIk(Point3D end, Angle angle)
    {
        double ax = angle.getAx();
        double ay = angle.getAy();
        double az = angle.getAz();
        double px = end.x;
        double py = end.y;
        double pz = end.z;
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
        
        double[][] temp = MatrixOperations.mult(T, MatrixOperations.invert(MatrixOperations.createTranslation(0, 0, -FOOT_HEIGHT)));
        temp = MatrixOperations.mult(MatrixOperations.invert(MatrixOperations.createTranslation(0, HIP_OFFSET_Y, -HIP_OFFSET_Z)), temp);
        temp = MatrixOperations.invert(temp);
        T_ = MatrixOperations.mult(MatrixOperations.createRotationX(PI / 4), temp);
        T_ = MatrixOperations.invert(T_);
        
        // TODO posun vyssie a usetris vypocet
        double[][] left = MatrixOperations.invert(T_);
        double[][] T56LeftLeg = MatrixOperations.createDHTransformation(0, -PI/2, 0, 0);
        double[][] RzLeftLeg = MatrixOperations.createRotationZ(PI);
        double[][] RyLeftLeg = MatrixOperations.createRotationY(-PI/2);
        double[][] right = MatrixOperations.mult(T56LeftLeg, RzLeftLeg);
        right = MatrixOperations.mult(right, RyLeftLeg);
        right = MatrixOperations.invert(right);
        T__ = MatrixOperations.mult(left, right);
        T__ = MatrixOperations.invert(T__);
        
        double[][] T34LeftLeg = MatrixOperations.createDHTransformation(-THIGH_LENGHT, 0, 0, 0);
        double[][] T45LeftLeg = MatrixOperations.createDHTransformation(-TIBIA_LENGHT, 0, 0, 0);
        double[][] TTemp = MatrixOperations.mult(T34LeftLeg, T45LeftLeg);
        TTemp = MatrixOperations.invert(TTemp);
        T___ = MatrixOperations.mult(MatrixOperations.invert(T__), TTemp);
    }
    
    double getTheta4()
    {
        double d = sqrt(T_[0][3] * T_[0][3] + T_[1][3] * T_[1][3] + T_[2][3] * T_[2][3]);
        double nominator = l1*l1 + l2*l2 - d*d;
        double denom = 2*l1*l2;
        theta4 = PI - acos(nominator/denom);
        // TODO +- theta4
        return theta4;
    }
    
    double getTheta5()
    {
        double nominator = T__[1][3]*(l2 + l1*cos(theta4)) + l1*T__[0][3]*sin(theta4);
        double denominator = l1*l1*sin(theta4)*sin(theta4) + (l2 + l1*cos(theta4));
        theta5 = asin(-nominator/denominator);
        return theta5;
    }
    
    double getTheta5_2()
    {
        theta5 = PI - theta5;
        return theta5;
    }
    
    double getTheta6()
    {
        if(l2 * cos(theta5) + l1 * cos(theta4 + theta5) != 0)
        {
            theta6 = atan(T_[1][3] / T_[2][3]);
        }
        else
        {
            theta6 = 0;
        }
        return theta6;
    }
    
    double getTheta2()
    {
        theta2 = acos(T___[1][2]);
        // TODO +- theta2
        // TODO - PI/4
        return theta2;
    }
    
    double getTheta3()
    {
        theta3 = asin(T___[1][1]/sin(theta2 + PI/4));
        return theta3;
    }
    
    double getTheta3_2()
    {
        theta3 = PI - theta3;
        return theta3;
    }
    
    double getTheta1()
    {
        theta1 = acos(T___[0][2]/sin(theta2 + PI/4));
        // TODO +- theta1
        // TODO + PI/2
        return theta1;
    }
    
    public Map<Joint, Double> getResult()
    {
        Map<Joint, Double> result = new HashMap<Joint, Double>();
        theta4 = toDegrees(getTheta4());
        if(Utils.validateJointRange(Joint.LLE4, theta4))
        {
            result.put(Joint.LLE4, theta4);
        }
        else if(Utils.validateJointRange(Joint.LLE4, -theta4))
        {
            theta4 = -theta4;
            result.put(Joint.LLE4, theta4);
        }
        theta5 = toDegrees(getTheta5());
        if(Utils.validateJointRange(Joint.LLE5, theta5))
        {
            result.put(Joint.LLE5, theta5);
        }
        else
        {
            theta5 = 180 - theta5;
            if(Utils.validateJointRange(Joint.LLE5, theta5))
            {
                result.put(Joint.LLE5, theta5);
            }
        }
        theta6 = toDegrees(getTheta6());
        if(Utils.validateJointRange(Joint.LLE6, theta6))
        {
            result.put(Joint.LLE6, theta6);
        }
        
        theta2 = toDegrees(getTheta2()) - 45;
        if(Utils.validateJointRange(Joint.LLE2, theta2))
        {
            result.put(Joint.LLE2, theta2);
        }
        else 
        {
            theta2 = -toDegrees(getTheta2()) - 45;
            if(Utils.validateJointRange(Joint.LLE2, theta2))
            {
                result.put(Joint.LLE2, theta2);
            }
        }
        
        theta3 = toDegrees(getTheta3());
        if(Utils.validateJointRange(Joint.LLE3, theta3))
        {
            result.put(Joint.LLE3, theta3);
        }
        else 
        {
            theta3 = 180 - theta3;
            if(Utils.validateJointRange(Joint.LLE3, theta3))
            {
                result.put(Joint.LLE3, theta3);
            }
        }
        
        theta1 = toDegrees(getTheta1()) + 90;
        if(Utils.validateJointRange(Joint.LLE1, theta1))
        {
            result.put(Joint.LLE1, theta1);
        }
        else 
        {
            theta1 = -toDegrees(getTheta1()) + 90;
            if(Utils.validateJointRange(Joint.LLE1, theta1))
            {
                result.put(Joint.LLE1, theta1);
            }
        }
        return result;
    }
}
