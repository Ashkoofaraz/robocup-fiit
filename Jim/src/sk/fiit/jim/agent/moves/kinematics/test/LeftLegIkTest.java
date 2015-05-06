package sk.fiit.jim.agent.moves.kinematics.test;

import static java.lang.Math.toRadians;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.jim.agent.moves.kinematics.ForwardKinematicResult;
import sk.fiit.jim.agent.moves.kinematics.Kinematics;

public class LeftLegIkTest
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
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("left_leg_joint6_interval5.csv")));
        BufferedWriter bw2 = new BufferedWriter(new FileWriter(new File("left_leg_joint6_interval5_no_ik.csv")));
        for (double t1 = Joint.LLE1.getLow(); t1 <= Joint.LLE1.getUp(); t1 += 5)
        {
            for (double t2 = Joint.LLE2.getLow(); t2 <= Joint.LLE2.getUp(); t2 += 5)
            {
                for (double t3 = Joint.LLE3.getLow(); t3 <= Joint.LLE3.getUp(); t3 += 5)
                {
                    for (double t4 = Joint.LLE4.getLow(); t4 <= Joint.LLE4.getUp(); t4 += 5)
                    {
                        for (double t5 = Joint.LLE5.getLow(); t5 <= Joint.LLE5.getUp(); t5 += 5)
                        {
                            for (double t6 = Joint.LLE6.getLow(); t6 <= Joint.LLE6.getUp(); t6 += 5)
                            {
                                ForwardKinematicResult forwKinResult = new ForwardKinematicResult(
                                        kinematics.getForwardLeftLeg(toRadians(t1), toRadians(t2), toRadians(t3),
                                                toRadians(t4), toRadians(t5), toRadians(t6)));
                                Map<Joint, Double> inverseKinResult = kinematics.getInverseLeftLeg(
                                        forwKinResult.getEndPoint(), forwKinResult.getOrientation());

                                // System.out.println("theta1: " + t1 +
                                // ", theta2: " + t2 + ", theta3: " + t3 +
                                // ", theta4: " + t4 + ", theta5: " + t5 +
                                // ", theta6: " + t6);
                                // System.out.println(inverseKinResult);

                                StringBuilder line = new StringBuilder();
                                line.append((int) t1);
                                line.append(";");
                                line.append((int) t2);
                                line.append(";");
                                line.append((int) t3);
                                line.append(";");
                                line.append((int) t4);
                                line.append(";");
                                line.append((int) t5);
                                line.append(";");
                                line.append((int) t6);
                                line.append(";");

                                StringBuilder line2 = new StringBuilder(line);

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

                                line.append(String.format(Locale.GERMAN, "%.2f", inverseKinResult.get(Joint.LLE1)));
                                line.append(";");
                                line.append(String.format(Locale.GERMAN, "%.2f", inverseKinResult.get(Joint.LLE2)));
                                line.append(";");
                                line.append(String.format(Locale.GERMAN, "%.2f", inverseKinResult.get(Joint.LLE3)));
                                line.append(";");
                                line.append(String.format(Locale.GERMAN, "%.2f", inverseKinResult.get(Joint.LLE4)));
                                line.append(";");
                                line.append(String.format(Locale.GERMAN, "%.2f", inverseKinResult.get(Joint.LLE5)));
                                line.append(";");
                                line.append(String.format(Locale.GERMAN, "%.2f", inverseKinResult.get(Joint.LLE6)));
                                line.append(";");

                                line2.append(String.format(Locale.GERMAN, "%.2f", inverseKinResult.get(Joint.LLE1)));
                                line2.append(";");
                                line2.append(String.format(Locale.GERMAN, "%.2f", inverseKinResult.get(Joint.LLE2)));
                                line2.append(";");
                                line2.append(String.format(Locale.GERMAN, "%.2f", inverseKinResult.get(Joint.LLE3)));
                                line2.append(";");
                                line2.append(String.format(Locale.GERMAN, "%.2f", inverseKinResult.get(Joint.LLE4)));
                                line2.append(";");
                                line2.append(String.format(Locale.GERMAN, "%.2f", inverseKinResult.get(Joint.LLE5)));
                                line2.append(";");
                                line2.append(String.format(Locale.GERMAN, "%.2f", inverseKinResult.get(Joint.LLE6)));
                                line2.append("\n");

                                ForwardKinematicResult validation = new ForwardKinematicResult(
                                        kinematics.getForwardLeftLeg(toRadians(inverseKinResult.get(Joint.LLE1)),
                                                toRadians(inverseKinResult.get(Joint.LLE2)),
                                                toRadians(inverseKinResult.get(Joint.LLE3)),
                                                toRadians(inverseKinResult.get(Joint.LLE4)),
                                                toRadians(inverseKinResult.get(Joint.LLE5)),
                                                toRadians(inverseKinResult.get(Joint.LLE6))));

                                line.append(String.format(Locale.GERMAN, "%.2f", validation.getEndPoint().x));
                                line.append(";");
                                line.append(String.format(Locale.GERMAN, "%.2f", validation.getEndPoint().y));
                                line.append(";");
                                line.append(String.format(Locale.GERMAN, "%.2f", validation.getEndPoint().z));
                                line.append(";");
                                line.append(String.format(Locale.GERMAN, "%.2f", validation.getOrientation()
                                        .getAxRadians()));
                                line.append(";");
                                line.append(String.format(Locale.GERMAN, "%.2f", validation.getOrientation()
                                        .getAyRadians()));
                                line.append(";");
                                line.append(String.format(Locale.GERMAN, "%.2f", validation.getOrientation()
                                        .getAzRadians()));
                                line.append("\n");

                                bw.write(line.toString());
                                bw2.write(line2.toString());
                                // System.out.println(inverseKinResult);
                                // System.out.println("theta1: " + t1 +
                                // ", theta2: " + t2 + ", theta3: " + t3 +
                                // ", theta4: " + t4 + ", theta5: " + t5 +
                                // ", theta6: " + t6);
                                // System.out.println("original: " +
                                // forwKinResult.getEndPoint() + " " +
                                // forwKinResult.getOrientation());
                                // System.out.println("validated: " +
                                // validation.getEndPoint() + " " +
                                // validation.getOrientation());
                                // System.out.println("----------------------------------------");
                            }
                            bw.flush();
                            bw2.flush();
                        }
                    }

                }
            }
        }
        bw.close();
        bw2.close();
        // System.out.println("all: " + all);
        // System.out.println("wrong: " + wrong);
    }

    private static boolean validateJointValues(Map<Joint, Double> inverseKinResult, double t1, double t2, double t3,
            double t4, double t5, double t6)
    {
        double invKinTheta1 = inverseKinResult.get(Joint.LLE1);
        if(!almostEqual(invKinTheta1, t1))
        {
            return false;
        }
        double invKinTheta2 = inverseKinResult.get(Joint.LLE2);
        if(!almostEqual(invKinTheta2, t2))
        {
            return false;
        }
        double invKinTheta3 = inverseKinResult.get(Joint.LLE3);
        if(!almostEqual(invKinTheta3, t3))
        {
            return false;
        }
        double invKinTheta4 = inverseKinResult.get(Joint.LLE4);
        if(!almostEqual(invKinTheta4, t4))
        {
            return false;
        }
        double invKinTheta5 = inverseKinResult.get(Joint.LLE5);
        if(!almostEqual(invKinTheta5, t5))
        {
            return false;
        }
        double invKinTheta6 = inverseKinResult.get(Joint.LLE6);
        if(!almostEqual(invKinTheta6, t6))
        {
            return false;
        }
        return true;
    }
}
