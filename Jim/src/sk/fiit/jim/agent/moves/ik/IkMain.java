package sk.fiit.jim.agent.moves.ik;

import static sk.fiit.jim.agent.moves.ik.Constants.ELBOW_OFFSET_Y;
import static sk.fiit.jim.agent.moves.ik.Constants.SHOULDER_OFFSET_Y;
import sk.fiit.robocup.library.geometry.Point3D;

public class IkMain
{
    private static double[][] A4End;
    static
    {
        A4End = new double[4][4];
        A4End[0][0] = 1;
        A4End[0][1] = 0;
        A4End[0][2] = 0;
        A4End[0][3] = 10;
        A4End[1][0] = 0;
        A4End[1][1] = 1;
        A4End[1][2] = 0;
        A4End[1][3] = 0;
        A4End[2][0] = 0;
        A4End[2][1] = 0;
        A4End[2][2] = 1;
        A4End[2][3] = 0;
        A4End[3][0] = 0;
        A4End[3][1] = 0;
        A4End[3][2] = 0;
        A4End[3][3] = 1;
    }
    
    private static final Point3D DEFAULT_LEFT_ARM = new Point3D(Constants.HAND_OFFSET_X + Constants.LOWER_ARM_LENGTH + Constants.UPPER_ARM_LENGTH, SHOULDER_OFFSET_Y + ELBOW_OFFSET_Y, Constants.SHOULDER_OFFSET_Z);
    
    public static void main(String[] args)
    {
        Angle angle = new Angle(0, 0, 0);
        //Point3D end = new Point3D(0, 0, 0);
        Point3D end = DEFAULT_LEFT_ARM;
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
    }
}
