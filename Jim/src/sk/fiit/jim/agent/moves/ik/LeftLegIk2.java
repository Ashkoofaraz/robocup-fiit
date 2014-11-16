package sk.fiit.jim.agent.moves.ik;

import static java.lang.Math.*;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.*;

import java.util.HashMap;
import java.util.Map;

import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.robocup.library.geometry.Point3D;

class LeftLegIk2
{
    private double theta1;

    private double theta2;

    private double theta3;

    private double theta4;
    
    private double theta5;
    
    private double theta6;

    private double l1 = THIGH_LENGHT; 

    private double l2 = TIBIA_LENGHT;
    
    private Matrix T;
    private Matrix T_ ;
    private Matrix T__;
    private Matrix T___;
    
    public LeftLegIk2(Point3D end, Orientation angle)
    {
        T = Matrix.createKinematic(end, angle);
        
        Matrix temp = Matrix.invAbaseLeftLeg.mult(T);
        temp = temp.mult(Matrix.invAendLeftLeg);
        Matrix tempT_ = Matrix.ROTATION_X_PI_4.mult(temp);
        T_ = tempT_.inverse();
        
        Matrix left = tempT_;
        Matrix right = Matrix.T56LeftLeg.mult(Matrix.RzLeftLeg).mult(Matrix.RyLeftLeg);
        Matrix tempT__ = left.mult(right.inverse());
        T__ = tempT__.inverse();
        
        
        Matrix TTemp = Matrix.T34LeftLeg.mult(Matrix.T45LeftLeg);
        T___ = tempT__.mult(TTemp.inverse());
    }
    
    double getTheta4()
    {
        double T_03 = T_.getValueAt(0, 3);
        double T_13 = T_.getValueAt(1, 3);
        double T_23 = T_.getValueAt(2, 3);
        double d = sqrt((0-T_03) * (0-T_03) + (0-T_13) * (0-T_13) + (0-T_23) * (0-T_23));
        double nominator = l1*l1 + l2*l2 - d*d;
        double denom = 2*l1*l2;
        theta4 = PI - acos(nominator/denom);
        // TODO +- theta4
        return theta4;
    }
    
    double getTheta5()
    {
        double T__03 = T__.getValueAt(0, 3);
        double T__13 = T__.getValueAt(1, 3);
        
        double nominator = T__13*(l2 + l1*cos(theta4)) + l1*T__03*sin(theta4);
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
            double T_13 = T_.getValueAt(1, 3);
            double T_23 = T_.getValueAt(2, 3);
            theta6 = atan(T_13 / T_23);
        }
        else
        {
            theta6 = 0; // undefined
        }
        return theta6;
    }
    
    double getTheta2()
    {
        double T___12 = T___.getValueAt(1, 2);
        theta2 = acos(T___12);
        // TODO +- theta2
        // TODO - PI/4
        return theta2;
    }
    
    double getTheta3()
    {
        double T___11 = T___.getValueAt(1, 1);
        theta3 = asin(T___11/sin(theta2 + PI/4));
        return theta3;
    }
    
    double getTheta3_2()
    {
        theta3 = PI - theta3;
        return theta3;
    }
    
    double getTheta1()
    {
        double T___02 = T___.getValueAt(0,2);
        theta1 = acos(T___02/sin(theta2 + PI/4));
        // TODO +- theta1
        // TODO + PI/2
        return theta1;
    }
    
    public Map<Joint, Double> getResult()
    {
        Map<Joint, Double> result = new HashMap<Joint, Double>();
        double theta4Deg = toDegrees(getTheta4());
        if(Utils.validateJointRange(Joint.LLE4, theta4Deg))
        {
            result.put(Joint.LLE4, theta4Deg);
        }
        else if(Utils.validateJointRange(Joint.LLE4, -theta4Deg))
        {
            theta4Deg = -theta4Deg;
            result.put(Joint.LLE4, toDegrees(-theta4Deg));
        }
        double theta5Deg = toDegrees(getTheta5());
        if(Utils.validateJointRange(Joint.LLE5, theta5Deg))
        {
            result.put(Joint.LLE5, theta5Deg);
        }
        else
        {
            theta5Deg = 180 - theta5Deg;
            if(Utils.validateJointRange(Joint.LLE5, theta5Deg))
            {
                result.put(Joint.LLE5, theta5Deg);
            }
        }
        double theta6Deg = toDegrees(getTheta6());
        if(Utils.validateJointRange(Joint.LLE6, theta6Deg))
        {
            result.put(Joint.LLE6, theta6Deg);
        }
        
        double theta2Deg = toDegrees(getTheta2()) - 45;
        if(Utils.validateJointRange(Joint.LLE2, theta2Deg))
        {
            result.put(Joint.LLE2, theta2Deg);
        }
        else 
        {
            theta2Deg = -toDegrees(getTheta2()) - 45;
            if(Utils.validateJointRange(Joint.LLE2, theta2Deg))
            {
                result.put(Joint.LLE2, theta2Deg);
            }
        }
        
        double theta3Deg = toDegrees(getTheta3());
        if(Utils.validateJointRange(Joint.LLE3, theta3Deg))
        {
            result.put(Joint.LLE3, theta3Deg);
        }
        else 
        {
            theta3Deg = 180 - theta3Deg;
            if(Utils.validateJointRange(Joint.LLE3, theta3Deg))
            {
                result.put(Joint.LLE3, theta3Deg);
            }
        }
        
        double theta1Deg = toDegrees(getTheta1()) + 90;
        if(Utils.validateJointRange(Joint.LLE1, theta1Deg))
        {
            result.put(Joint.LLE1, theta1Deg);
        }
        else 
        {
            theta1Deg = -toDegrees(getTheta1()) + 90;
            if(Utils.validateJointRange(Joint.LLE1, theta1Deg))
            {
                result.put(Joint.LLE1, theta1Deg);
            }
        }
        return result;
    }
}
