package sk.fiit.jim.agent.moves.ik;

import static sk.fiit.jim.agent.moves.ik.Constants.ELBOW_OFFSET_Y;
import static sk.fiit.jim.agent.moves.ik.Constants.SHOULDER_OFFSET_Y;
import sk.fiit.robocup.library.geometry.Point3D;

public class IkMain
{
    private static final Point3D DEF_LEFT_ARM = new Point3D(190, 100, 75);
    
    private static final Point3D DEF_LEFT_ARM_TORSO = new Point3D(190, 100, 75);
    
    public static void main(String[] args)
    {
        Angle angle = new Angle(0, 0, 0);
        //Point3D end = new Point3D(0, 0, 0);
        Point3D end = DEF_LEFT_ARM;
        LeftArmIk la = new LeftArmIk(end, angle);
        System.out.println("theta4: " + la.getTheta4());
        System.out.println("theta4: " + Math.toDegrees(la.getTheta4()) +"°");
        System.out.println("theta2: " + la.getTheta2());
        System.out.println("theta2: " + Math.toDegrees(la.getTheta2()) +"°");
        System.out.println("theta3_1: " + la.getTheta3_1());
        System.out.println("theta3_1: " + Math.toDegrees(la.getTheta3_1()) +"°");
        System.out.println("theta1: " + la.getTheta1());
        System.out.println("theta1: " + Math.toDegrees(la.getTheta1()) +"°");
        System.out.println("theta3_2: " + la.getTheta3_2());
        System.out.println("theta3_2: " + Math.toDegrees(la.getTheta3_2()) +"°");
        System.out.println("theta1: " + la.getTheta1());
        System.out.println("theta1: " + Math.toDegrees(la.getTheta1()) +"°");
        System.out.println("theta2_b: " + la.getTheta2_b());
        System.out.println("theta2_b: " + Math.toDegrees(la.getTheta2_b()) +"°");
    }
}
