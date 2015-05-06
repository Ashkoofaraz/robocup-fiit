package sk.fiit.jim.agent.moves.kinematics.test;

import static java.lang.Math.toRadians;

import java.io.IOException;
import java.util.Locale;
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

    public static void main(String[] args) throws IOException
    {
        long all = 0;
        long wrong = 0;
        Kinematics kinematics = Kinematics.getInstance();
//        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("left_arm_joint4_interval5.csv")));
        for (double t1 = Joint.LAE1.getLow(); t1 <= Joint.LAE1.getUp(); t1 += 5)
        {
            for (double t2 = Joint.LAE2.getLow(); t2 <= Joint.LAE2.getUp(); t2 += 5)
            {
                for (double t3 = Joint.LAE3.getLow(); t3 <= Joint.LAE3.getUp(); t3 += 5)
                {
                    for (double t4 = Joint.LAE4.getLow(); t4 <= Joint.LAE4.getUp(); t4 += 5)
                    {
                        ForwardKinematicResult forwKinResult = new ForwardKinematicResult(
                                kinematics.getForwardLeftHand(toRadians(t1), toRadians(t2), toRadians(t3),
                                        toRadians(t4)));
                        Map<Joint, Double> inverseKinResult = kinematics.getInverseLeftArm(forwKinResult.getEndPoint(),
                                forwKinResult.getOrientation());

                        if(!inverseKinResult.isEmpty())
                        {
                            System.out.println("theta1: " + t1 + ", theta2: " + t2 + ", theta3: " + t3 + ", theta4: "
                                    + t4);
                            System.out.println(inverseKinResult);

                            StringBuilder line = new StringBuilder();
                            line.append((int) t1);
                            line.append(";");
                            line.append((int) t2);
                            line.append(";");
                            line.append((int) t3);
                            line.append(";");
                            line.append((int) t4);
                            line.append(";");

                            line.append(String.format(Locale.GERMAN, "%.2f", forwKinResult.getEndPoint().x));
                            line.append(";");
                            line.append(String.format(Locale.GERMAN, "%.2f", forwKinResult.getEndPoint().y));
                            line.append(";");
                            line.append(String.format(Locale.GERMAN, "%.2f", forwKinResult.getEndPoint().z));
                            line.append(";");
                            line.append(String.format(Locale.GERMAN, "%.2f", forwKinResult.getOrientation()
                                    .getAxRadians()));
                            line.append(";");
                            line.append(String.format(Locale.GERMAN, "%.2f", forwKinResult.getOrientation()
                                    .getAyRadians()));
                            line.append(";");
                            line.append(String.format(Locale.GERMAN, "%.2f", forwKinResult.getOrientation()
                                    .getAzRadians()));
                            line.append(";");

                            line.append(String.format(Locale.GERMAN, "%.2f", inverseKinResult.get(Joint.LAE1)));
                            line.append(";");
                            line.append(String.format(Locale.GERMAN, "%.2f", inverseKinResult.get(Joint.LAE2)));
                            line.append(";");
                            line.append(String.format(Locale.GERMAN, "%.2f", inverseKinResult.get(Joint.LAE3)));
                            line.append(";");
                            line.append(String.format(Locale.GERMAN, "%.2f", inverseKinResult.get(Joint.LAE4)));
                            line.append(";");

                            ForwardKinematicResult validation = new ForwardKinematicResult(
                                    kinematics.getForwardLeftHand(toRadians(inverseKinResult.get(Joint.LAE1)),
                                            toRadians(inverseKinResult.get(Joint.LAE2)),
                                            toRadians(inverseKinResult.get(Joint.LAE3)),
                                            toRadians(inverseKinResult.get(Joint.LAE4))));

                            line.append(String.format(Locale.GERMAN, "%.2f", validation.getEndPoint().x));
                            line.append(";");
                            line.append(String.format(Locale.GERMAN, "%.2f", validation.getEndPoint().y));
                            line.append(";");
                            line.append(String.format(Locale.GERMAN, "%.2f", validation.getEndPoint().z));
                            line.append(";");
                            line.append(String
                                    .format(Locale.GERMAN, "%.2f", validation.getOrientation().getAxRadians()));
                            line.append(";");
                            line.append(String
                                    .format(Locale.GERMAN, "%.2f", validation.getOrientation().getAyRadians()));
                            line.append(";");
                            line.append(String
                                    .format(Locale.GERMAN, "%.2f", validation.getOrientation().getAzRadians()));
                            line.append("\n");

//                            bw.write(line.toString());
                            
                            // System.out.println(inverseKinResult);
                            // System.out.println("theta1: " + t1 + ", theta2: "
                            // + t2 + ", theta3: " + t3 + ", theta4: " + t4 +
                            // ", theta5: " + t5 + ", theta6: " + t6);
                            // System.out.println("original: " +
                            // forwKinResult.getEndPoint() + " " +
                            // forwKinResult.getOrientation());
                            // System.out.println("validated: " +
                            // validation.getEndPoint() + " " +
                            // validation.getOrientation());
                            // System.out.println("----------------------------------------");
                        }
//                        bw.flush();
                    }
                }
            }
        }
//        bw.close();
        System.out.println("all: " + all);
        System.out.println("wrong: " + wrong);
    }

    private static boolean validateJointValues(Map<Joint, Double> inverseKinResult, double t1, double t2, double t3,
            double t4)
    {
        double invKinTheta1 = inverseKinResult.get(Joint.LAE1);
        if(!almostEqual(invKinTheta1, t1))
        {
            return false;
        }
        double invKinTheta2 = inverseKinResult.get(Joint.LAE2);
        if(!almostEqual(invKinTheta2, t2))
        {
            return false;
        }
        double invKinTheta3 = inverseKinResult.get(Joint.LAE3);
        if(!almostEqual(invKinTheta3, t3))
        {
            return false;
        }
        double invKinTheta4 = inverseKinResult.get(Joint.LAE4);
        if(!almostEqual(invKinTheta4, t4))
        {
            return false;
        }
        return true;
    }
}
