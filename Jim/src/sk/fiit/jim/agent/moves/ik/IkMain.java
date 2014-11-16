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
        
        Orientation angle = Orientation.fromRadians(0, 0, 0);
        //Point3D end = new Point3D(0, 0, 0);
        Kinematics kinematics = new Kinematics();
        
        long preRunTime = System.nanoTime();
        kinematics.getInverseLeftArm(DEF_LEFT_ARM, angle);
        kinematics.getInverseRightArm(DEF_RIGHT_ARM, angle);
        kinematics.getInverseLeftLeg(DEF_LEFT_LEG, angle);
        kinematics.getInverseRightLeg(DEF_RIGHT_LEG, angle);
        long preRunDiff = System.nanoTime() - preRunTime;
        System.err.println(preRunDiff / 1000000000.0);
        
        long preRunTime2 = System.nanoTime();
        kinematics.getInverseLeftArm2(DEF_LEFT_ARM, angle);
        kinematics.getInverseRightArm2(DEF_RIGHT_ARM, angle);
        kinematics.getInverseLeftLeg2(DEF_LEFT_LEG, angle);
        kinematics.getInverseRightLeg2(DEF_RIGHT_LEG, angle);
        long preRunDiff2 = System.nanoTime() - preRunTime2;
        System.err.println(preRunDiff2 / 1000000000.0);
        
        long time = System.nanoTime();
        Point3D endLeftArm = DEF_LEFT_ARM;
        System.out.println(kinematics.getInverseLeftArm(endLeftArm, angle));
        
        Point3D endRightArm = DEF_RIGHT_ARM;
        System.out.println(kinematics.getInverseRightArm(endRightArm, angle));
        
        Point3D endLeftLeg = DEF_LEFT_LEG;
        System.out.println(kinematics.getInverseLeftLeg(endLeftLeg, angle));
        
        Point3D endRightLeg = DEF_RIGHT_LEG;
        System.out.println(kinematics.getInverseRightLeg(endRightLeg, angle));
       
        long diff = System.nanoTime() - time;
        System.err.println(diff / 1000000000.0);
        
        // left arm diff
        long timeLeftArm1 = System.nanoTime();
        System.out.println(new LeftArmIk(endLeftArm, angle).getResult());
        long diffLeftArm1 = System.nanoTime() - timeLeftArm1;
        System.err.println("leftArmIK " +diffLeftArm1 / 1000000000.0);
        
        long timeLeftArm2 = System.nanoTime();
        System.out.println(new LeftArmIk2(endLeftArm, angle).getResult());
        long diffLeftArm2 = System.nanoTime() - timeLeftArm2;
        System.err.println("leftArmIK2 " +diffLeftArm2 / 1000000000.0);
        
        // right arm diff
        long timeRightArm1 = System.nanoTime();
        System.out.println(new RightArmIk(endRightArm, angle).getResult());
        long diffRightArm1 = System.nanoTime() - timeRightArm1;
        System.err.println("rightArmIK " +diffRightArm1 / 1000000000.0);
        
        long timeRightArm2 = System.nanoTime();
        System.out.println(new RightArmIk2(endRightArm, angle).getResult());
        long diffRightArm2 = System.nanoTime() - timeRightArm2;
        System.err.println("rightArmIK2 " +diffRightArm2 / 1000000000.0);
        
        // left leg diff
        long timeLeftLeg1 = System.nanoTime();
        System.out.println(new LeftLegIk(endLeftLeg, angle).getResult());
        long diffLeftLeg1 = System.nanoTime() - timeLeftLeg1;
        System.err.println("leftLegIK " +diffLeftLeg1 / 1000000000.0);
        
        long timeLeftLeg2 = System.nanoTime();
        System.out.println(new LeftLegIk2(endLeftLeg, angle).getResult());
        long diffLeftLeg2 = System.nanoTime() - timeLeftLeg2;
        System.err.println("leftLegIK2 " +diffLeftLeg2 / 1000000000.0);
        
        // right leg diff
        long timeRightLeg1 = System.nanoTime();
        System.out.println(new RightLegIk(endRightLeg, angle).getResult());
        long diffRightLeg1 = System.nanoTime() - timeRightLeg1;
        System.err.println("rightLegIK " +diffRightLeg1 / 1000000000.0);
        
        long timeRightLeg2 = System.nanoTime();
        System.out.println(new RightLegIk2(endRightLeg, angle).getResult());
        long diffRightLeg2 = System.nanoTime() - timeRightLeg2;
        System.err.println("rightLegIK2 " +diffRightLeg2 / 1000000000.0);
        
        // left arm forward
        System.out.println("left arm");
        System.out.println(new Kinematics().getForwardLeftHand(0, 0, 0, 0));
        
        // Right arm forward
        System.out.println("Right arm");
        System.out.println(new Kinematics().getForwardRightHand(0, 0, 0, 0));
        
        // left leg forward
        System.out.println("Left leg");
        System.out.println(new Kinematics().getForwardLeftLeg(0, 0, 0, 0, 0, 0));
        
        // Right leg forward
        System.out.println("Right leg");
        System.out.println(new Kinematics().getForwardRightLeg(0, 0, 0, 0, 0, 0));
        
     // Right arm forward
//        System.out.println("Right arm");
//        System.out.println(new Kinematics().getForwardRightHand(PI/2, 0, 0, 0));
//        
//        System.out.println(new RightArmIk(new Point3D(120, -98, 0), angle).getResult());
    }
}
