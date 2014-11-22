package sk.fiit.jim.agent.moves.ik;

import static java.lang.Math.PI;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.ELBOW_OFFSET_Y;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.FOOT_HEIGHT;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.HAND_OFFSET_X;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.HIP_OFFSET_Y;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.HIP_OFFSET_Z;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.LOWER_ARM_LENGTH;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.SHOULDER_OFFSET_Y;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.SHOULDER_OFFSET_Z;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.THIGH_LENGHT;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.TIBIA_LENGHT;
import static sk.fiit.jim.agent.moves.ik.SimsparkConstants.UPPER_ARM_LENGTH;

import java.util.Map;

import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.robocup.library.geometry.Point3D;

public class Kinematics
{
    private static final Kinematics INSTANCE = new Kinematics();
    
    Kinematics()
    {
    }
    
    public static Kinematics getInstance()
    {
        return INSTANCE;
    }
    
    public Matrix getForwardLeftHand(double theta1, double theta2, double theta3, double theta4)
    {
        Matrix AbaseLeftArm = Matrix.createTranslation(0, SHOULDER_OFFSET_Y + ELBOW_OFFSET_Y, SHOULDER_OFFSET_Z);
        Matrix T01LeftArm = Matrix.createDHTransformation(0, - PI/2, 0, 0 + theta1); // a, alpha, d, theta
        Matrix T12LeftArm = Matrix.createDHTransformation(0, PI/2, 0, -PI/2 + theta2);
        Matrix T23LeftArm = Matrix.createDHTransformation(0, - PI/2, UPPER_ARM_LENGTH, 0 + theta3);
        Matrix T34LeftArm = Matrix.createDHTransformation(0, PI/2, 0, + theta4);
        Matrix RzLeftArm =  Matrix.createRotationZ(PI/2);
        Matrix AendLeftArm =Matrix.createTranslation(HAND_OFFSET_X + LOWER_ARM_LENGTH, 0, 0);
        
        Matrix resultMatrix = AbaseLeftArm.mult(T01LeftArm).mult(T12LeftArm).mult(T23LeftArm).mult(T34LeftArm).mult(RzLeftArm).mult(AendLeftArm);
        return resultMatrix;
    }
    
    public Matrix getForwardRightHand(double theta1, double theta2, double theta3, double theta4)
    {
        Matrix AbaseRightArm = Matrix.createTranslation(0, -SHOULDER_OFFSET_Y - ELBOW_OFFSET_Y, SHOULDER_OFFSET_Z);
        Matrix T01RightArm = Matrix.createDHTransformation(0, - PI/2, 0, 0 + theta1); // a, alpha, d, theta
        Matrix T12RightArm = Matrix.createDHTransformation(0, PI/2, 0, PI/2 + theta2);
        Matrix T23RightArm = Matrix.createDHTransformation(0, - PI/2, -UPPER_ARM_LENGTH, 0 + theta3);
        Matrix T34RightArm = Matrix.createDHTransformation(0, PI/2, 0, 0 + theta4);
        Matrix RzRightArm =  Matrix.createRotationZ(PI/2);
        Matrix AendRightArm =Matrix.createTranslation(-HAND_OFFSET_X - LOWER_ARM_LENGTH, 0, 0);
        Matrix RzRightArm2 = Matrix.createRotationZ(-PI);
        
        Matrix resultMatrix = AbaseRightArm.mult(T01RightArm).mult(T12RightArm).mult(T23RightArm).mult(T34RightArm).mult(RzRightArm).mult(AendRightArm).mult(RzRightArm2);
        return resultMatrix;
    }
    
    public Matrix getForwardLeftLeg(double theta1, double theta2, double theta3, double theta4, double theta5, double theta6)
    {
        Matrix AbaseLeftLeg =Matrix.createTranslation(0, HIP_OFFSET_Y, -HIP_OFFSET_Z);
        Matrix T01LeftLeg =  Matrix.createDHTransformation(0, - 3*PI/4, 0, -PI/2 + theta1); // a, alpha, d, theta
        Matrix T12LeftLeg =  Matrix.createDHTransformation(0, -PI/2, 0, PI/4 + theta2);
        Matrix T23LeftLeg =  Matrix.createDHTransformation(0, PI/2, 0, 0 + theta3);
        Matrix T34LeftLeg =  Matrix.createDHTransformation(-THIGH_LENGHT, 0, 0, 0+ theta4);
        Matrix T45LeftLeg =  Matrix.createDHTransformation(-TIBIA_LENGHT, 0, 0, 0+ theta5);
        Matrix T56LeftLeg =  Matrix.createDHTransformation(0, -PI/2, 0, 0 + theta6);
        Matrix RzLeftLeg =   Matrix.createRotationZ(PI);
        Matrix RyLeftLeg =   Matrix.createRotationY(-PI/2);
        Matrix AendLeftLeg = Matrix.createTranslation(0, 0, -FOOT_HEIGHT);
        
        Matrix result = AbaseLeftLeg.mult(T01LeftLeg).mult(T12LeftLeg).mult(T23LeftLeg).mult(T34LeftLeg).mult(T45LeftLeg).mult(T56LeftLeg).mult(RzLeftLeg).mult(RyLeftLeg).mult(AendLeftLeg);
        return result;
    }
    
    public Matrix getForwardRightLeg(double theta1, double theta2, double theta3, double theta4, double theta5, double theta6)
    {
        Matrix AbaseRightLeg =Matrix.createTranslation(0, -HIP_OFFSET_Y, -HIP_OFFSET_Z);
        Matrix T01RightLeg =  Matrix.createDHTransformation(0, - PI/4, 0, -PI/2 + theta1); // a, alpha, d, theta
        Matrix T12RightLeg =  Matrix.createDHTransformation(0, -PI/2, 0, -PI/4 + theta2);
        Matrix T23RightLeg =  Matrix.createDHTransformation(0, PI/2, 0, 0 + theta3);
        Matrix T34RightLeg =  Matrix.createDHTransformation(-THIGH_LENGHT, 0, 0, 0+ theta4);
        Matrix T45RightLeg =  Matrix.createDHTransformation(-TIBIA_LENGHT, 0, 0, 0+ theta5);
        Matrix T56RightLeg =  Matrix.createDHTransformation(0, -PI/2, 0, 0 + theta6);
        Matrix RzRightLeg =   Matrix.createRotationZ(PI);
        Matrix RyRightLeg =   Matrix.createRotationY(-PI/2);
        Matrix AendRightLeg = Matrix.createTranslation(0, 0, -FOOT_HEIGHT);
        
        Matrix result = AbaseRightLeg.mult(T01RightLeg).mult(T12RightLeg).mult(T23RightLeg).mult(T34RightLeg).mult(T45RightLeg).mult(T56RightLeg).mult(RzRightLeg).mult(RyRightLeg).mult(AendRightLeg);
        return result;
    }
    
    public Map<Joint, Double> getInverseLeftArm(Point3D endpoint, Orientation angle)
    {
        return new LeftArmIk(endpoint, angle).getResult();
    }
    
    public Map<Joint, Double> getInverseRightArm(Point3D endpoint, Orientation angle)
    {
        return new RightArmIk(endpoint, angle).getResult();
    }
    
    public Map<Joint, Double> getInverseLeftLeg(Point3D endpoint, Orientation angle)
    {
        return new LeftLegIk(endpoint, angle).getResult();
    }
    
    public Map<Joint, Double> getInverseRightLeg(Point3D endpoint, Orientation angle)
    {
        return new RightLegIk(endpoint, angle).getResult();
    }
}
