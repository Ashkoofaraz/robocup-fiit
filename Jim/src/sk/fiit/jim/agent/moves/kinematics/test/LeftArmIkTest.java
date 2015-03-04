package sk.fiit.jim.agent.moves.kinematics.test;

import static java.lang.Math.toRadians;

import java.util.Map;

import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.jim.agent.moves.kinematics.ForwardKinematicResult;
import sk.fiit.jim.agent.moves.kinematics.Kinematics;

public class LeftArmIkTest
{
private static double EPSILON = 4.0;
    
    private static boolean almostEqual(double d1, double d2)
    {
        return Math.abs(d1 - d2) < EPSILON;
    }
    
    public static void main(String[] args)
    {
        long all = 0;
        long wrong = 0;
        Kinematics kinematics = Kinematics.getInstance();
        for(double t1 = Joint.LAE1.getLow(); t1 <= Joint.LAE1.getUp(); t1++)
        {
            for(double t2 = Joint.LAE2.getLow(); t2 <= Joint.LAE2.getUp(); t2++) 
            {
                for(double t3 = Joint.LAE3.getLow(); t3 <= Joint.LAE3.getUp(); t3++)
                {
                    for(double t4 = Joint.LAE4.getLow(); t4 <= Joint.LAE4.getUp(); t4++) 
                    {
                        all++;
                        ForwardKinematicResult forwKinResult = new ForwardKinematicResult(kinematics.getForwardLeftHand(toRadians(t1), toRadians(t2), toRadians(t3), toRadians(t4)));
                        Map<Joint, Double> inverseKinResult = kinematics.getInverseLeftArm(forwKinResult.getEndPoint(), forwKinResult.getOrientation());
                        if(inverseKinResult.size() != 4 || !validateJointValues(inverseKinResult, t1, t2, t3, t4))
                        {
                            wrong++;
                            System.out.println(inverseKinResult);
                            System.out.println("theta1: " + t1 + ", theta2: " + t2 + ", theta3: " + t3 + ", theta4: " + t4);
                            if(inverseKinResult.size() == 6) {
                                ForwardKinematicResult validation = new ForwardKinematicResult(
                                        kinematics.getForwardLeftHand(
                                                toRadians(inverseKinResult.get(Joint.LAE1)), toRadians(inverseKinResult.get(Joint.LAE2)), toRadians(inverseKinResult.get(Joint.LAE3)), toRadians(inverseKinResult.get(Joint.LAE4))));
                                System.out.println("original: " + forwKinResult.getEndPoint() + " " + forwKinResult.getOrientation());
                                System.out.println("validated: " + validation.getEndPoint() + " " + validation.getOrientation());
                            }
                            System.out.println("----------------------------------------");
                        }
                    }
                }
            }
        }
        System.out.println("all: " + all);
        System.out.println("wrong: " + wrong);
    }

    private static boolean validateJointValues(Map<Joint, Double> inverseKinResult, double t1, double t2, double t3,
            double t4)
    {
        double invKinTheta1 = inverseKinResult.get(Joint.LAE1);
        if(!almostEqual(invKinTheta1, t1)) {
            return false;
        }
        double invKinTheta2 = inverseKinResult.get(Joint.LAE2);
        if(!almostEqual(invKinTheta2, t2)) {
            return false;
        }
        double invKinTheta3 = inverseKinResult.get(Joint.LAE3);
        if(!almostEqual(invKinTheta3, t3)) {
            return false;
        }
        double invKinTheta4 = inverseKinResult.get(Joint.LAE4);
        if(!almostEqual(invKinTheta4, t4)) {
            return false;
        }
        return true;
    }
}
