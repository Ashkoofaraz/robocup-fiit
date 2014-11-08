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
        Angle angle = new Angle(0, 0, 0);
        //Point3D end = new Point3D(0, 0, 0);
        Point3D endLeftArm = DEF_LEFT_ARM;
        LeftArmIk la = new LeftArmIk(endLeftArm, angle);
        System.out.println(la.getResult());
        
        Point3D endRightArm = DEF_RIGHT_ARM;
        RightArmIk ra = new RightArmIk(endRightArm, angle);
        System.out.println(ra.getResult());
        
        Point3D endLeftLeg = DEF_LEFT_LEG;
        LeftLegIk le = new LeftLegIk(endLeftLeg, angle);
        System.out.println(le.getResult());
       
        // left arm forward
        double[][] AbaseLeftArm = MatrixOperations.createTranslation(0, SHOULDER_OFFSET_Y + ELBOW_OFFSET_Y, SHOULDER_OFFSET_Z);
        double[][] T01LeftArm = MatrixOperations.createDHTransformation(0, - PI/2, 0, 0); // a, alpha, d, theta
        double[][] T12LeftArm = MatrixOperations.createDHTransformation(0, PI/2, 0, - PI/2);
        double[][] T23LeftArm = MatrixOperations.createDHTransformation(0, - PI/2, UPPER_ARM_LENGTH, 0);
        double[][] T34LeftArm = MatrixOperations.createDHTransformation(0, PI/2, 0, 0);
        double[][] RzLeftArm = MatrixOperations.createRotationZ(PI/2);
        double[][] AendLeftArm = MatrixOperations.createTranslation(HAND_OFFSET_X + LOWER_ARM_LENGTH, 0, 0);
        
        double[][] TLeftArm = MatrixOperations.mult(AbaseLeftArm, T01LeftArm);
        TLeftArm = MatrixOperations.mult(TLeftArm, T12LeftArm);
        TLeftArm = MatrixOperations.mult(TLeftArm, T23LeftArm);
        TLeftArm = MatrixOperations.mult(TLeftArm, T34LeftArm);
        TLeftArm = MatrixOperations.mult(TLeftArm, RzLeftArm);
        TLeftArm = MatrixOperations.mult(TLeftArm, AendLeftArm);
        
        System.out.println("left arm");
        MatrixOperations.print(TLeftArm);
        System.out.println(new ForwardKinematicResult(TLeftArm));
        
        // Right arm forward
        double[][] AbaseRightArm = MatrixOperations.createTranslation(0, -SHOULDER_OFFSET_Y - ELBOW_OFFSET_Y, SHOULDER_OFFSET_Z);
        double[][] T01RightArm = MatrixOperations.createDHTransformation(0, - PI/2, 0, 0); // a, alpha, d, theta
        double[][] T12RightArm = MatrixOperations.createDHTransformation(0, PI/2, 0, PI/2);
        double[][] T23RightArm = MatrixOperations.createDHTransformation(0, - PI/2, -UPPER_ARM_LENGTH, 0);
        double[][] T34RightArm = MatrixOperations.createDHTransformation(0, PI/2, 0, 0);
        double[][] RzRightArm = MatrixOperations.createRotationZ(PI/2);
        double[][] AendRightArm = MatrixOperations.createTranslation(-HAND_OFFSET_X - LOWER_ARM_LENGTH, 0, 0);
        double[][] RzRightArm2 = MatrixOperations.createRotationZ(-PI);
        
        double[][] TRightArm = MatrixOperations.mult(AbaseRightArm, T01RightArm);
        TRightArm = MatrixOperations.mult(TRightArm, T12RightArm);
        TRightArm = MatrixOperations.mult(TRightArm, T23RightArm);
        TRightArm = MatrixOperations.mult(TRightArm, T34RightArm);
        TRightArm = MatrixOperations.mult(TRightArm, RzRightArm);
        TRightArm = MatrixOperations.mult(TRightArm, AendRightArm);
        TRightArm = MatrixOperations.mult(TRightArm, RzRightArm2);
        
        System.out.println("Right arm");
        MatrixOperations.print(TRightArm);
        System.out.println(new ForwardKinematicResult(TRightArm));
        
        // left leg forward
        double[][] AbaseLeftLeg = MatrixOperations.createTranslation(0, HIP_OFFSET_Y, -HIP_OFFSET_Z);
        double[][] T01LeftLeg = MatrixOperations.createDHTransformation(0, - 3*PI/4, 0, -PI/2); // a, alpha, d, theta
        double[][] T12LeftLeg = MatrixOperations.createDHTransformation(0, -PI/2, 0, PI/4);
        double[][] T23LeftLeg = MatrixOperations.createDHTransformation(0, PI/2, 0, 0);
        double[][] T34LeftLeg = MatrixOperations.createDHTransformation(-THIGH_LENGHT, 0, 0, 0);
        double[][] T45LeftLeg = MatrixOperations.createDHTransformation(-TIBIA_LENGHT, 0, 0, 0);
        double[][] T56LeftLeg = MatrixOperations.createDHTransformation(0, -PI/2, 0, 0);
        double[][] RzLeftLeg = MatrixOperations.createRotationZ(PI);
        double[][] RyLeftLeg = MatrixOperations.createRotationY(-PI/2);
        double[][] AendLeftLeg = MatrixOperations.createTranslation(0, 0, -FOOT_HEIGHT);
        
        double[][] TLeftLeg = MatrixOperations.mult(AbaseLeftLeg, T01LeftLeg);
        TLeftLeg = MatrixOperations.mult(TLeftLeg, T12LeftLeg);
        TLeftLeg = MatrixOperations.mult(TLeftLeg, T23LeftLeg);
        TLeftLeg = MatrixOperations.mult(TLeftLeg, T34LeftLeg);
        TLeftLeg = MatrixOperations.mult(TLeftLeg, T45LeftLeg);
        TLeftLeg = MatrixOperations.mult(TLeftLeg, T56LeftLeg);
        TLeftLeg = MatrixOperations.mult(TLeftLeg, RzLeftLeg);
        TLeftLeg = MatrixOperations.mult(TLeftLeg, RyLeftLeg);
        TLeftLeg = MatrixOperations.mult(TLeftLeg, AendLeftLeg);
        
        System.out.println("left leg");
        MatrixOperations.print(TLeftLeg);
        System.out.println(new ForwardKinematicResult(TLeftLeg));
        
        // Right leg forward
        double[][] AbaseRightLeg = MatrixOperations.createTranslation(0, -HIP_OFFSET_Y, -HIP_OFFSET_Z);
        double[][] T01RightLeg = MatrixOperations.createDHTransformation(0, - PI/4, 0, -PI/2); // a, alpha, d, theta
        double[][] T12RightLeg = MatrixOperations.createDHTransformation(0, -PI/2, 0, -PI/4);
        double[][] T23RightLeg = MatrixOperations.createDHTransformation(0, PI/2, 0, 0);
        double[][] T34RightLeg = MatrixOperations.createDHTransformation(-THIGH_LENGHT, 0, 0, 0);
        double[][] T45RightLeg = MatrixOperations.createDHTransformation(-TIBIA_LENGHT, 0, 0, 0);
        double[][] T56RightLeg = MatrixOperations.createDHTransformation(0, -PI/2, 0, 0);
        double[][] RzRightLeg = MatrixOperations.createRotationZ(PI);
        double[][] RyRightLeg = MatrixOperations.createRotationY(-PI/2);
        double[][] AendRightLeg = MatrixOperations.createTranslation(0, 0, -FOOT_HEIGHT);
        
        double[][] TRightLeg = MatrixOperations.mult(AbaseRightLeg, T01RightLeg);
        TRightLeg = MatrixOperations.mult(TRightLeg, T12RightLeg);
        TRightLeg = MatrixOperations.mult(TRightLeg, T23RightLeg);
        TRightLeg = MatrixOperations.mult(TRightLeg, T34RightLeg);
        TRightLeg = MatrixOperations.mult(TRightLeg, T45RightLeg);
        TRightLeg = MatrixOperations.mult(TRightLeg, T56RightLeg);
        TRightLeg = MatrixOperations.mult(TRightLeg, RzRightLeg);
        TRightLeg = MatrixOperations.mult(TRightLeg, RyRightLeg);
        TRightLeg = MatrixOperations.mult(TRightLeg, AendRightLeg);
        
        System.out.println("Right leg");
        MatrixOperations.print(TRightLeg);
        System.out.println(new ForwardKinematicResult(TRightLeg));
    }
}
