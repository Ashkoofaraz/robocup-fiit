package sk.fiit.jim.agent.moves.kinematics;

import static java.lang.Math.PI;
import static java.lang.Math.acos;
import static java.lang.Math.asin;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.THIGH_LENGHT;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.TIBIA_LENGHT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.robocup.library.geometry.Point3D;

/**
 * 
 * The purpose of this class is encapsulating inverse kinematics calculations of
 * left leg.
 * 
 * @author Pidanic
 *
 */
class LeftLegIk2
{
    private double l1 = THIGH_LENGHT;

    private double l2 = TIBIA_LENGHT;

    private Matrix T;

    private Matrix T_;

    private Matrix T__;

    private Matrix T___;

    public LeftLegIk2(Point3D end, Orientation angle)
    {
        T = Matrix.createTransformation(end, angle);

//        Matrix temp = Matrix.INV_A_BASE_LEFT_LEG.mult(T);
//        temp = temp.mult(Matrix.INV_A_END_LEFT_LEG);
//        Matrix tempT_ = Matrix.R_X_PI_4.mult(temp);
//        T_ = tempT_.inverse();
//
//        Matrix left = tempT_;
//        Matrix right = Matrix.T_56_LEFT_LEG.mult(Matrix.R_Z_LEFT_LEG).mult(Matrix.R_Y_LEFT_LEG);
//        Matrix tempT__ = left.mult(right.inverse());
//        T__ = tempT__.inverse();
//
//        Matrix TTemp = Matrix.T_34_LEFT_LEG.mult(Matrix.T_45_LEFT_LEG);
//        T___ = tempT__.mult(TTemp.inverse());
    }

    private List<Double> getTheta4()
    {
        List<Double> result = new ArrayList<>();
        double T_03 = T_.getValueAt(0, 3);
        double T_13 = T_.getValueAt(1, 3);
        double T_23 = T_.getValueAt(2, 3);
        double d = sqrt((0 - T_03) * (0 - T_03) + (0 - T_13) * (0 - T_13) + (0 - T_23) * (0 - T_23));
        double nominator = l1 * l1 + l2 * l2 - d * d;
        double denom = 2 * l1 * l2;
        double theta4 = PI - acos(KinematicUtils.validateArcsinArccosRange(nominator / denom));
        if(KinematicUtils.validateJointRangeInRadians(Joint.LLE4, theta4))
        {
            result.add(theta4);
        }
        if(KinematicUtils.validateJointRangeInRadians(Joint.LLE4, -theta4))
        {
            result.add(-theta4);
        }
        return result;
    }

    private List<Double> getTheta5(final double theta4)
    {
        List<Double> result = new ArrayList<>();
        double T__03 = T__.getValueAt(0, 3);
        double T__13 = T__.getValueAt(1, 3);
        double nominator = T__13 * (l2 + l1 * cos(theta4)) + l1 * T__03 * sin(theta4);
        double denominator = l1 * l1 * sin(theta4) * sin(theta4) + (l2 + l1 * cos(theta4));
        double theta5 = asin(KinematicUtils.validateArcsinArccosRange(-nominator / denominator));
        if(KinematicUtils.validateJointRangeInRadians(Joint.LLE5, theta5))
        {
            result.add(theta5);
        }
        if(KinematicUtils.validateJointRangeInRadians(Joint.LLE5, PI - theta5))
        {
            result.add(PI - theta5);
        }
        return result;
    }

    private List<Double> getTheta6()
    {
        // TODO podmienka cos(l2*theta5 + l1*cos(theta4 + theta5))
        List<Double> result = new ArrayList<>();
        double T_13 = T_.getValueAt(1, 3);
        double T_23 = T_.getValueAt(2, 3);
        double theta6 = atan(T_13 / T_23);
        if(KinematicUtils.validateJointRangeInRadians(Joint.LLE6, theta6))
        {
            result.add(theta6);
        }
        return result;
    }

    private List<Double> getTheta2()
    {
        List<Double> result = new ArrayList<>();
        double T___12 = T___.getValueAt(1, 2);
        double theta2 = acos(KinematicUtils.validateArcsinArccosRange(T___12));
        if(KinematicUtils.validateJointRangeInRadians(Joint.LLE2, theta2 - PI / 4))
        {
            result.add(theta2 - PI / 4);
        }
        if(KinematicUtils.validateJointRangeInRadians(Joint.LLE2, -theta2 - PI / 4))
        {
            result.add(-theta2 - PI / 4);
        }
        return result;
    }

    private List<Double> getTheta3(final double theta2)
    {
        List<Double> result = new ArrayList<>();
        double T___11 = T___.getValueAt(1, 1);
        double theta3 = asin(KinematicUtils.validateArcsinArccosRange(T___11 / sin(theta2 + PI / 4)));
        if(KinematicUtils.validateJointRangeInRadians(Joint.LLE3, theta3))
        {
            result.add(theta3);
        }
        if(KinematicUtils.validateJointRangeInRadians(Joint.LLE3, PI - theta3))
        {
            result.add(PI - theta3);
        }
        return result;
    }

    private List<Double> getTheta1(final double theta2)
    {
        List<Double> result = new ArrayList<>();
        double T___02 = T___.getValueAt(0, 2);
        double theta1 = acos(KinematicUtils.validateArcsinArccosRange(T___02 / sin(theta2 + PI / 4)));
        if(KinematicUtils.validateJointRangeInRadians(Joint.LLE1, theta1 + PI / 2))
        {
            result.add(theta1 + PI / 2);
        }
        if(KinematicUtils.validateJointRangeInRadians(Joint.LLE1, -theta1 + PI / 2))
        {
            result.add(-theta1 + PI / 2);
        }
        return result;
    }

    /**
     * Calculates and returns result of inverse kinematics for left leg.
     * 
     * @see Joint
     */
    public Map<Joint, Double> getResult()
    {
        Map<Joint, Double> result = new HashMap<Joint, Double>();
        
        Matrix temp = Matrix.INV_A_BASE_LEFT_LEG.mult(T);
        temp = temp.mult(Matrix.INV_A_END_LEFT_LEG);
        Matrix tempT_ = Matrix.R_X_PI_4.mult(temp);
        T_ = tempT_.inverse();
        List<Double> thetas6 = this.getTheta6();
        List<Double> thetas4 = this.getTheta4();

        for(double t4 : thetas4) {
            for(double t6 : thetas6) {
                Matrix left = tempT_;
                Matrix T56 = Matrix.createDHTransformation(0, -PI / 2, 0, t6);
                Matrix right = T56.mult(Matrix.R_Z_LEFT_LEG).mult(Matrix.R_Y_LEFT_LEG);
                Matrix tempT__ = left.mult(right.inverse());
                T__ = tempT__.inverse();
                
                List<Double> thetas5 = this.getTheta5(t4);
                for(double t5 : thetas5) {
                    Matrix T34 = Matrix.createDHTransformation(-THIGH_LENGHT, 0, 0, t4);
                    Matrix T45 = Matrix.createDHTransformation(-TIBIA_LENGHT, 0, 0, t5);
                    Matrix TTemp = T34.mult(T45);
                    T___ = tempT__.mult(TTemp.inverse());
                    
                    List<Double> thetas2 = getTheta2();
                    for(double t2 : thetas2) {
                        List<Double> thetas3 = this.getTheta3(t2);
                        for(double t3 : thetas3) {
                            List<Double> thetas1 = this.getTheta1(t2);
                            for(double t1 : thetas1) {
                                result.put(Joint.LLE1, toDegrees(t1));
                                result.put(Joint.LLE2, toDegrees(t2));
                                result.put(Joint.LLE3, toDegrees(t3));
                                result.put(Joint.LLE4, toDegrees(t4));
                                result.put(Joint.LLE5, toDegrees(t5));
                                result.put(Joint.LLE6, toDegrees(t6));
                            }
                        }
                        
                    }
                    
                }
            }
        }
        return result;
    }
}
