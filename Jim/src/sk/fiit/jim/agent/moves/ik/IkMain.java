package sk.fiit.jim.agent.moves.ik;

import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.*;
import static java.lang.Math.*;
import sk.fiit.robocup.library.geometry.Point3D;

public class IkMain
{
    private static final Point3D DEF_LEFT_ARM = new Point3D(195, 98, 75); // predpazena
    
    private static final Point3D DEF_RIGHT_ARM = new Point3D(195, -98, 75); // predpazena
    
    private static final Point3D DEF_LEFT_ARM_TORSO = new Point3D(190, 100, 75);
    
    private static final Point3D DEF_LEFT_LEG = new Point3D(0.0, 55.0, -385.0);
    
    private static final Point3D DEF_RIGHT_LEG = new Point3D(0.0, -55.0, -385.0);
    
    public static void main(String[] args)
    {
        System.out.println("left leg");
        Matrix forwardLeftLeg = new Kinematics().getForwardLeftLeg(0, -PI/8, 0, 0, 0, 0);
        System.out.println(forwardLeftLeg);
        ForwardKinematicResult leftLeg = new ForwardKinematicResult(forwardLeftLeg);
        System.out.println(leftLeg);
        System.out.println(new LeftLegIk(new Point3D(leftLeg.getPx(), leftLeg.getPy(), leftLeg.getPz()), Orientation.fromRadians(leftLeg.getAx(), leftLeg.getAy(), leftLeg.getAz())).getResult());
        System.out.println(new LeftLegIk2(new Point3D(leftLeg.getPx(), leftLeg.getPy(), leftLeg.getPz()), Orientation.fromRadians(leftLeg.getAx(), leftLeg.getAy(), leftLeg.getAz())).getResult());
    }
}
