package sk.fiit.jim.agent.moves.kinematics.test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

import sk.fiit.jim.agent.moves.kinematics.ForwardKinematicResult;
import sk.fiit.jim.agent.moves.kinematics.Kinematics;
import sk.fiit.jim.agent.moves.kinematics.Orientation;
import sk.fiit.robocup.library.geometry.Point3D;
import sk.fiit.robocup.library.geometry.Vector3D;

import static java.lang.Math.*;
/**
 * Class for calculations testing
 * 
 * @author Pidanic
 *
 */
public class IkMain
{
    private static class StateLogger
    {
        private static StateLogger INSTANCE;

        private static BufferedWriter bw;

        private StateLogger(String name)
        {
            try
            {
                // do 15
                bw = new BufferedWriter(new FileWriter(name));
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        /**
         * @see String#format(Locale, String, Object...)
         */
        public void log(Locale locale, String format, Object... params)
        {
            String line = String.format(locale, format, params);
            try
            {
                bw.write(line);
                bw.flush();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        /**
         * @see String#format(Locale, String, Object...)
         */
        public void log(String format, Object... params)
        {
            log(Locale.getDefault(), format, params);
        }

        public static StateLogger getInstance(String name)
        {
            if(INSTANCE == null)
            {
                INSTANCE = new StateLogger(name);
            }
            return INSTANCE;
        }

        @Override
        protected void finalize() throws Throwable
        {
            bw.close();
            super.finalize();
        }
    }

    private static final Point3D TORSO_DEFAULT_ABS_POSITION = new Point3D(0, 0, 385);

    private static final Point3D DEF_LEFT_ARM = new Point3D(195, 98, 75); // predpazena

    private static final Point3D DEF_RIGHT_ARM = new Point3D(195, -98, 75); // predpazena

    private static final Point3D DEF_LEFT_ARM_TORSO = new Point3D(190, 100, 75);

    private static final Point3D DEF_LEFT_LEG = new Point3D(0.0, 55.0, -385.0);

    private static final Point3D DEF_RIGHT_LEG = new Point3D(0.0, -55.0, -385.0);

    public static void main(String[] args) throws IOException
    {
        // System.out.println("left leg");
        //
        // long time = System.nanoTime();
        // Matrix forwardLeftLeg = new Kinematics().getForwardLeftLeg(-PI/2, 0,
        // 0, 0, 0, 0);
        // System.out.println(forwardLeftLeg);
        // ForwardKinematicResult leftLeg = new
        // ForwardKinematicResult(forwardLeftLeg);
        // System.out.println(leftLeg);
        // System.out.println(new LeftLegIk(leftLeg.getEndPoint(),
        // leftLeg.getOrientation()).getResult());
        // long diff = System.nanoTime() - time;
        // System.err.println(diff / 1000000000.0);
        //
        // long time2 = System.nanoTime();
        // Matrix forwardRightLeg = new Kinematics().getForwardRightLeg(-PI/2,
        // 0, 0, 0, 0, 0);
        // System.out.println(forwardRightLeg);
        // ForwardKinematicResult rightLeg = new
        // ForwardKinematicResult(forwardRightLeg);
        // System.out.println(rightLeg);
        // System.out.println(new RightLegIk(rightLeg.getEndPoint(),
        // rightLeg.getOrientation()).getResult());
        // long diff2 = System.nanoTime() - time2;
        // System.err.println(diff2 / 1000000000.0);
        //
        // System.out.println(KinematicUtils.validateArcsinArccosRange(1.000007));
        // System.out.println(KinematicUtils.validateArcsinArccosRange(-1.000007));
        // System.out.println(KinematicUtils.validateArcsinArccosRange(0));
        // System.out.println(KinematicUtils.validateArcsinArccosRange(-1.700007));
        // System.out.println(KinematicUtils.validateArcsinArccosRange(-0.99999));
        // System.out.println(Math.toDegrees(0.01));

        // BufferedWriter br = new BufferedWriter(new FileWriter(new
        // File("left_arm_points.txt")));
        // Kinematics kin = Kinematics.getInstance();
        // for (int le1 = (int) Joint.LAE1.getLow(); le1 <= (int)
        // Joint.LAE1.getUp(); le1++)
        // {
        // for (int le2 = (int) Joint.LAE2.getLow(); le2 <= (int)
        // Joint.LAE2.getUp(); le2++)
        // {
        // for (int le3 = (int) Joint.LAE3.getLow(); le3 <= (int)
        // Joint.LAE3.getUp(); le3++)
        // {
        // for (int le4 = (int) Joint.LAE4.getLow(); le4 <= (int)
        // Joint.LAE4.getUp(); le4++)
        // {
        // br.append(new
        // ForwardKinematicResult(kin.getForwardLeftHand(Math.toRadians(le1),
        // Math.toRadians(le2), Math.toRadians(le3),
        // Math.toRadians(le4))).toString() + '\n');
        // // System.out.println(new
        // // ForwardKinematicResult(kin.getForwardLeftHand(Math.toRadians(le1),
        // // Math.toRadians(le2), Math.toRadians(le3),
        // // Math.toRadians(le4))));
        // }
        // }
        // }
        // }
        // br.flush();
        // br.close();

        ForwardKinematicResult frk = new ForwardKinematicResult(Kinematics.getInstance().getForwardLeftLeg(
                Math.toRadians(-90), Math.toRadians(-25), Math.toRadians(-25), Math.toRadians(-130),
                Math.toRadians(-45), Math.toRadians(-45)));
        System.out.println(frk.getEndPoint() + " " + frk.getOrientation());
        Point3D point6 = frk.getEndPoint();
        Orientation orientation6 = frk.getOrientation();
        System.out.println(Kinematics.getInstance().getInverseLeftLeg(point6, orientation6));

        logRelativeFootPoitionsForLLE2();
        
        
        double alpha = 0;
        System.out.println(getLLE2fromAlpha(alpha));
        System.out.println(getLLE2fromAlpha2(alpha));
        System.out.println(getLLE2fromAlpha3(alpha));
        
    }

    private static void logRelativeFootPoitionsForLLE2()
    {
        StateLogger logger = StateLogger.getInstance("relativne_pozicie_nohy_pr_imeniacom_LLE2.csv");
        for (int lle2 = 15; lle2 <= 45; lle2++)
        {
            ForwardKinematicResult frk = new ForwardKinematicResult(Kinematics.getInstance().getForwardLeftLeg(0,
                    Math.toRadians(lle2), Math.toRadians(100), Math.toRadians(-75), Math.toRadians(20),
                    Math.toRadians(-20)));
            String format = "%.3f;%.3f;%.3f;%.3f;%.3f;%.3f\n";
            Point3D endpoint = frk.getEndPoint();
            Orientation orientation = frk.getOrientation();
            Object[] params = new Object[] { endpoint.x, endpoint.y, endpoint.z, orientation.getAxRadians(),
                    orientation.getAyRadians(), orientation.getAzRadians() };
            logger.log(Locale.GERMAN, format, params);
        }

    }
    
    // kubicka
    private static double getLLE2fromAlpha(double alpha)
    {
        double lle2 = 0.0051466062 * pow(alpha, 3) + 0.1822084261 * pow(alpha, 2) - 0.2905759535 * alpha + 13.3946227506;
        return lle2;
    }
    
    // kvadraticka
    private static double getLLE2fromAlpha2(double alpha)
    {
        double lle2 =  0.0769165501 * pow(alpha, 2) - 0.5679788145 * alpha + 15.4529727642;
        return lle2;
    }
    
    // linearna
    private static double getLLE2fromAlpha3(double alpha)
    {
        double lle2 = -1.605689631 * alpha + 14.8133378154;
        return lle2;
    }
}
