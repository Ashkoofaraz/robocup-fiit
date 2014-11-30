package sk.fiit.jim.agent.moves.ik;

import static java.lang.Math.PI;
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
        
        long time = System.nanoTime();
        Matrix forwardLeftLeg = new Kinematics().getForwardLeftLeg(-PI/2, 0, 0, 0, 0, 0);
        System.out.println(forwardLeftLeg);
        ForwardKinematicResult leftLeg = new ForwardKinematicResult(forwardLeftLeg);
        System.out.println(leftLeg);
        System.out.println(new LeftLegIk(new Point3D(leftLeg.getPx(), leftLeg.getPy(), leftLeg.getPz()), Orientation.fromRadians(leftLeg.getAx(), leftLeg.getAy(), leftLeg.getAz())).getResult());
        long diff = System.nanoTime() - time;
        System.err.println(diff / 1000000000.0);
        
        long time2 = System.nanoTime();
        Matrix forwardRightLeg = new Kinematics().getForwardRightLeg(-PI/2, 0, 0, 0, 0, 0);
        System.out.println(forwardRightLeg);
        ForwardKinematicResult rightLeg = new ForwardKinematicResult(forwardRightLeg);
        System.out.println(rightLeg);
        System.out.println(new RightLegIk(new Point3D(rightLeg.getPx(), rightLeg.getPy(), rightLeg.getPz()), Orientation.fromRadians(rightLeg.getAx(), rightLeg.getAy(), rightLeg.getAz())).getResult());
        long diff2 = System.nanoTime() - time2;
        System.err.println(diff2 / 1000000000.0);
        
        System.out.println(KinematicUtils.validateArcsinArccosRange(1.000007));
        System.out.println(KinematicUtils.validateArcsinArccosRange(-1.000007));
        System.out.println(KinematicUtils.validateArcsinArccosRange(0));
        System.out.println(KinematicUtils.validateArcsinArccosRange(-1.700007));
        System.out.println(KinematicUtils.validateArcsinArccosRange(-0.99999));
        System.out.println(Math.toDegrees(0.01));
    }
}
