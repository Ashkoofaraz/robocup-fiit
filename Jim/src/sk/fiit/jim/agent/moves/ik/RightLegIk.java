package sk.fiit.jim.agent.moves.ik;

import static java.lang.Math.PI;
import static java.lang.Math.acos;
import static java.lang.Math.asin;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.THIGH_LENGHT;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.TIBIA_LENGHT;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.robocup.library.geometry.Point3D;

class RightLegIk
{
    private double theta1;

    private double theta2;

    private double theta3;

    private double theta4;
    
    private double theta5;
    
    private double theta6;
    
    private Set<Double> theta1Result = new HashSet<Double>();
    
    private Set<Double> theta2Result = new HashSet<Double>();
    
    private Set<Double> theta3Result = new HashSet<Double>();
    
    private Set<Double> theta4Result = new HashSet<Double>();
    
    private Set<Double> theta5Result = new HashSet<Double>();
    
    private Set<Double> theta6Result = new HashSet<Double>();

    private double l1 = THIGH_LENGHT; 

    private double l2 = TIBIA_LENGHT;
    
    private Matrix T ;
    private Matrix T_ ;
    private Matrix T__ ;
    private Matrix T___ ;
    
    public RightLegIk(Point3D end, Orientation angle)
    {
        T = Matrix.createTransformation(end, angle);
        
        Matrix temp = Matrix.invAbaseRightLeg.mult(T);
        temp = temp.mult(Matrix.invAendRightLeg);
        Matrix tempT_ = Matrix.ROTATION_X_PI_4_MINUS.mult(temp);
        T_ = tempT_.inverse();
        
        Matrix left = tempT_;
        Matrix right = (Matrix.T56RightLeg.mult(Matrix.RzRightLeg).mult(Matrix.RyRightLeg)).inverse();
        Matrix tempT__ = left.mult(right);
        T__ = tempT__.inverse();
        
        Matrix TTemp = (Matrix.T34RightLeg.mult(Matrix.T45RightLeg)).inverse();
        T___ = tempT__.mult(TTemp);
    }
    
    void getTheta4()
    {
        double T_03 = T_.getValueAt(0, 3);
        double T_13 = T_.getValueAt(1, 3);
        double T_23 = T_.getValueAt(2, 3);
        double d = sqrt((0-T_03) * (0-T_03) + (0-T_13) * (0-T_13) + (0-T_23) * (0-T_23));
        double nominator = l1*l1 + l2*l2 - d*d;
        double denom = 2*l1*l2;
        theta4 = PI - acos(nominator/denom);
        if(Utils.validateJointRange2(Joint.RLE4, theta4))
        {
            theta4Result.add(theta4);
        }
        if(Utils.validateJointRange2(Joint.RLE4, -theta4))
        {
            theta4Result.add(-theta4);
        }
    }
    
    void getTheta5()
    {
        double T__03 = T__.getValueAt(0, 3);
        double T__13 = T__.getValueAt(1, 3);
        for(double t4 : theta4Result)
        {
            double nominator = T__13*(l2 + l1*cos(t4)) + l1*T__03*sin(t4);
            double denominator = l1*l1*sin(t4)*sin(t4) + (l2 + l1*cos(t4));
            theta5 = asin(-nominator/denominator);
            if(Utils.validateJointRange2(Joint.RLE5,theta5))
            {
                theta5Result.add(theta5);
            }
            if(Utils.validateJointRange2(Joint.RLE5, PI - theta5))
            {
                theta5Result.add(PI -theta5);
            }
        }
    }
    
    void getTheta6()
    {
        for(double t5: theta5Result)
        {
            for(double t4: theta4Result)
            {
                if(l2 * cos(t5) + l1 * cos(t4 + t5) != 0)
                {
                    double T_13 = T_.getValueAt(1, 3);
                    double T_23 = T_.getValueAt(2, 3);
                    theta6 = atan(T_13 / T_23);
                }
                else
                {
                    theta6 = 0; // undefined
                }
                if(Utils.validateJointRange2(Joint.RLE6, theta6))
                {
                    theta6Result.add(theta6);
                }
            }
        }
    }
    
    void getTheta2()
    {
        double T___12 = T___.getValueAt(1, 2);
        theta2 = acos(T___12);
        // TODO +- theta2
        // TODO + PI/4
        if(Utils.validateJointRange2(Joint.RLE2, theta2 + PI/4))
        {
            theta2Result.add(theta2 + PI/4);
        }
        if(Utils.validateJointRange2(Joint.RLE2, -theta2 + PI/4))
        {
            theta2Result.add(-theta2 + PI/4);
        }
    }
    
    void getTheta3()
    {
        for(double t2 : theta2Result)
        {
            double T___11 = T___.getValueAt(1, 1);
            theta3 = asin(T___11/sin(t2 - PI/4));
            if(Utils.validateJointRange2(Joint.RLE3, theta3))
            {
                theta3Result.add(theta3);
            }
            if(Utils.validateJointRange2(Joint.RLE3, PI - theta3))
            {
                theta3Result.add(PI - theta3);
            }
        }
    }
    
    void getTheta1()
    {
        for(double t2 : theta2Result)
        {
            double T___02 = T___.getValueAt(0,2);
            theta1 = acos(T___02/sin(t2 - PI/4));
            if(Utils.validateJointRange2(Joint.RLE1, theta1 + PI/2))
            {
                theta1Result.add(theta1 + PI/2);
            }
            if(Utils.validateJointRange2(Joint.RLE1, -theta1 + PI/2))
            {
                theta1Result.add(-theta1 + PI/2);
            }
        }
    }
    
    public Map<Joint, Double> getResult()
    {
        getTheta4();
        getTheta5();
        getTheta6();
        getTheta2();
        getTheta3();
        getTheta1();
        Map<Joint, Double> result = new HashMap<Joint, Double>();
        for(double t1 : theta1Result)
        {
            result.put(Joint.RLE1, toDegrees(t1));
        }
        for(double t2 : theta2Result)
        {
            result.put(Joint.RLE2, toDegrees(t2));
        }
        for(double t3 : theta3Result)
        {
            result.put(Joint.RLE3, toDegrees(t3));
        }
        for(double t4 : theta4Result)
        {
            result.put(Joint.RLE4, toDegrees(t4));
        }
        for(double t5 : theta5Result)
        {
            result.put(Joint.RLE5, toDegrees(t5));
        }
        for(double t6 : theta6Result)
        {
            result.put(Joint.RLE6, toDegrees(t6));
        }
        return result;
    }
}
