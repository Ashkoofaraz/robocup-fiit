package sk.fiit.jim.agent.moves.kinematics.test;

import java.io.IOException;

import sk.fiit.jim.agent.moves.kinematics.ForwardKinematicResult;
import sk.fiit.jim.agent.moves.kinematics.Kinematics;
import sk.fiit.jim.agent.moves.kinematics.Orientation;
import sk.fiit.robocup.library.geometry.Point3D;

/**
 * Class for calculations testing
 * 
 * @author Pidanic
 *
 */
public class IkMain
{
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

//        BufferedWriter br = new BufferedWriter(new FileWriter(new File("left_arm_points.txt")));
//        Kinematics kin = Kinematics.getInstance();
//        for (int le1 = (int) Joint.LAE1.getLow(); le1 <= (int) Joint.LAE1.getUp(); le1++)
//        {
//            for (int le2 = (int) Joint.LAE2.getLow(); le2 <= (int) Joint.LAE2.getUp(); le2++)
//            {
//                for (int le3 = (int) Joint.LAE3.getLow(); le3 <= (int) Joint.LAE3.getUp(); le3++)
//                {
//                    for (int le4 = (int) Joint.LAE4.getLow(); le4 <= (int) Joint.LAE4.getUp(); le4++)
//                    {
//                        br.append(new ForwardKinematicResult(kin.getForwardLeftHand(Math.toRadians(le1),
//                                Math.toRadians(le2), Math.toRadians(le3), Math.toRadians(le4))).toString() + '\n');
//                        // System.out.println(new
//                        // ForwardKinematicResult(kin.getForwardLeftHand(Math.toRadians(le1),
//                        // Math.toRadians(le2), Math.toRadians(le3),
//                        // Math.toRadians(le4))));
//                    }
//                }
//            }
//        }
//        br.flush();
//        br.close();
        
        ForwardKinematicResult frk = new ForwardKinematicResult(Kinematics.getInstance().getForwardLeftLeg(Math.toRadians(-90), Math.toRadians(-25), Math.toRadians(-25), Math.toRadians(-130), Math.toRadians(-45), Math.toRadians(-45)));
        System.out.println(frk.getEndPoint() + " " + frk.getOrientation());
        Point3D point6 = frk.getEndPoint();
        Orientation orientation6 = frk.getOrientation();
        System.out.println(Kinematics.getInstance().getInverseLeftLeg(point6, orientation6));
        
    }
}
