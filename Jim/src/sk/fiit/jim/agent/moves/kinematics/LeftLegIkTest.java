package sk.fiit.jim.agent.moves.kinematics;

import java.util.Map;

import sk.fiit.jim.agent.moves.Joint;
import static java.lang.Math.*;

public class LeftLegIkTest
{
    private static double EPSILON = 2.0;
    
    private static boolean almostEqual(double d1, double d2)
    {
        return Math.abs(d1 - d2) < EPSILON;
    }
    
    public static void main(String[] args)
    {
        Kinematics kinematics = Kinematics.getInstance();
        for(double t1 = Joint.LLE1.getLow(); t1 <= Joint.LLE1.getUp(); t1++)
        {
            for(double t2 = Joint.LLE2.getLow(); t2 <= Joint.LLE2.getUp(); t2++) 
            {
                for(double t3 = Joint.LLE3.getLow(); t3 <= Joint.LLE3.getUp(); t3++)
                {
                    for(double t4 = Joint.LLE4.getLow(); t4 <= Joint.LLE4.getUp(); t4++) 
                    {
                        for(double t5 = Joint.LLE5.getLow(); t5 <= Joint.LLE5.getUp(); t5++)
                        {
                            for(double t6 = Joint.LLE6.getLow(); t6 <= Joint.LLE6.getUp(); t6++)
                            {
                                ForwardKinematicResult forwKinResult = new ForwardKinematicResult(kinematics.getForwardLeftLeg(toRadians(t1), toRadians(t2), toRadians(t3), toRadians(t4), toRadians(t5), toRadians(t6)));
//                                ForwardKinematicResult forwKinResult = new ForwardKinematicResult(Kinematics.getInstance().getForwardLeftLeg(0, Math.PI/4, Math.PI/2, -Math.PI/4, 0, 0));
                                Map<Joint, Double> inverseKinResult = kinematics.getInverseLeftLeg(forwKinResult.getEndPoint(), forwKinResult.getOrientation());
                                if(inverseKinResult.size() != 6 || !validateJointValues(inverseKinResult, t1, t2, t3, t4, t5, t6))
                                {
                                    System.out.println(inverseKinResult);
                                    System.out.println("theta1: " + t1 + ", theta2: " + t2 + ", theta3: " + t3 + ", theta4: " + t4 + ", theta5: " + t5 + ", theta6: " + t6);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean validateJointValues(Map<Joint, Double> inverseKinResult, double t1, double t2, double t3,
            double t4, double t5, double t6)
    {
        double invKinTheta1 = inverseKinResult.get(Joint.LLE1);
        if(!almostEqual(invKinTheta1, t1)) {
            return false;
        }
        double invKinTheta2 = inverseKinResult.get(Joint.LLE2);
        if(!almostEqual(invKinTheta2, t2)) {
            return false;
        }
        double invKinTheta3 = inverseKinResult.get(Joint.LLE3);
        if(!almostEqual(invKinTheta3, t3)) {
            return false;
        }
        double invKinTheta4 = inverseKinResult.get(Joint.LLE4);
        if(!almostEqual(invKinTheta4, t4)) {
            return false;
        }
        double invKinTheta5 = inverseKinResult.get(Joint.LLE5);
        if(!almostEqual(invKinTheta5, t5)) {
            return false;
        }
        double invKinTheta6 = inverseKinResult.get(Joint.LLE6);
        if(!almostEqual(invKinTheta6, t6)) {
            return false;
        }
        return true;
    }
}
