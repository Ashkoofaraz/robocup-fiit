package sk.fiit.jim.agent.moves.kinematics;

import static java.lang.Math.PI;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.ELBOW_OFFSET_Y;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.FOOT_HEIGHT;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.HAND_OFFSET_X;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.HIP_OFFSET_Y;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.HIP_OFFSET_Z;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.LOWER_ARM_LENGTH;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.SHOULDER_OFFSET_Y;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.SHOULDER_OFFSET_Z;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.THIGH_LENGHT;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.TIBIA_LENGHT;
import static sk.fiit.jim.agent.moves.kinematics.SimsparkConstants.UPPER_ARM_LENGTH;

import java.util.Map;

import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.robocup.library.geometry.Point3D;

/**
 * <p>
 * Class hold base forward and inverse kinematic calculations for kinematics
 * chains of Aldeberan Nao robot in 3D simulation league.
 * </p>
 * <p>
 * This implementation is based on work of Kouretes team.
 * </p>
 * 
 * @author Pidanic
 *
 */
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

    /**
     * Calculates forward transformation matrix of left arm kinematics chain.
     * 
     * @param theta1
     *            theta1 angle (left shoulder pitch) in radians.
     * @param theta2
     *            theta2 angle (left shoulder yaw) in radians.
     * @param theta3
     *            theta3 angle (left arm yaw) in radians.
     * @param theta4
     *            theta4 angle (left arm roll) in radians.
     * @return transformation {@link Matrix}.
     * @see Joint
     */
    public Matrix getForwardLeftHand(double theta1, double theta2, double theta3, double theta4)
    {
        Matrix AbaseLeftArm = Matrix.createTranslation(0, SHOULDER_OFFSET_Y + ELBOW_OFFSET_Y, SHOULDER_OFFSET_Z);
        Matrix T01LeftArm = Matrix.createDHTransformation(0, -PI / 2, 0, 0 + theta1);
        Matrix T12LeftArm = Matrix.createDHTransformation(0, PI / 2, 0, -PI / 2 + theta2);
        Matrix T23LeftArm = Matrix.createDHTransformation(0, -PI / 2, UPPER_ARM_LENGTH, 0 + theta3);
        Matrix T34LeftArm = Matrix.createDHTransformation(0, PI / 2, 0, +theta4);
        Matrix RzLeftArm = Matrix.createRotationZ(PI / 2);
        Matrix AendLeftArm = Matrix.createTranslation(HAND_OFFSET_X + LOWER_ARM_LENGTH, 0, 0);

        Matrix resultMatrix = AbaseLeftArm.mult(T01LeftArm).mult(T12LeftArm).mult(T23LeftArm).mult(T34LeftArm)
                .mult(RzLeftArm).mult(AendLeftArm);
        return resultMatrix;
    }

    /**
     * Calculates forward transformation matrix of right arm kinematics chain.
     * 
     * @param theta1
     *            theta1 angle (right shoulder pitch) in radians.
     * @param theta2
     *            theta2 angle (right shoulder yaw) in radians.
     * @param theta3
     *            theta3 angle (right arm yaw) in radians.
     * @param theta4
     *            theta4 angle (right arm roll) in radians.
     * @return transformation {@link Matrix}.
     * @see Joint
     */
    public Matrix getForwardRightHand(double theta1, double theta2, double theta3, double theta4)
    {
        Matrix AbaseRightArm = Matrix.createTranslation(0, -SHOULDER_OFFSET_Y - ELBOW_OFFSET_Y, SHOULDER_OFFSET_Z);
        Matrix T01RightArm = Matrix.createDHTransformation(0, -PI / 2, 0, 0 + theta1);
        Matrix T12RightArm = Matrix.createDHTransformation(0, PI / 2, 0, PI / 2 + theta2);
        Matrix T23RightArm = Matrix.createDHTransformation(0, -PI / 2, -UPPER_ARM_LENGTH, 0 + theta3);
        Matrix T34RightArm = Matrix.createDHTransformation(0, PI / 2, 0, 0 + theta4);
        Matrix RzRightArm = Matrix.createRotationZ(PI / 2);
        Matrix AendRightArm = Matrix.createTranslation(-HAND_OFFSET_X - LOWER_ARM_LENGTH, 0, 0);
        Matrix RzRightArm2 = Matrix.createRotationZ(-PI);

        Matrix resultMatrix = AbaseRightArm.mult(T01RightArm).mult(T12RightArm).mult(T23RightArm).mult(T34RightArm)
                .mult(RzRightArm).mult(AendRightArm).mult(RzRightArm2);
        return resultMatrix;
    }

    /**
     * Calculates forward transformation matrix of left leg kinematics chain.
     * 
     * @param theta1
     *            theta1 angle (left hip yawpitch) in radians.
     * @param theta2
     *            theta2 angle (left hip roll) in radians.
     * @param theta3
     *            theta3 angle (left hip pitch) in radians.
     * @param theta4
     *            theta4 angle (left knee pitch) in radians.
     * @param theta5
     *            theta5 angle (left foot pitch) in radians.
     * @param theta6
     *            theta6 angle (left foot roll) in radians.
     * @return transformation {@link Matrix}.
     * @see Joint
     */
    public Matrix getForwardLeftLeg(double theta1, double theta2, double theta3, double theta4, double theta5,
            double theta6)
    {
        Matrix AbaseLeftLeg = Matrix.createTranslation(0, HIP_OFFSET_Y, -HIP_OFFSET_Z);
        Matrix T01LeftLeg = Matrix.createDHTransformation(0, -3 * PI / 4, 0, -PI / 2 + theta1);
        Matrix T12LeftLeg = Matrix.createDHTransformation(0, -PI / 2, 0, PI / 4 + theta2);
        Matrix T23LeftLeg = Matrix.createDHTransformation(0, PI / 2, 0, 0 + theta3);
        Matrix T34LeftLeg = Matrix.createDHTransformation(-THIGH_LENGHT, 0, 0, 0 + theta4);
        Matrix T45LeftLeg = Matrix.createDHTransformation(-TIBIA_LENGHT, 0, 0, 0 + theta5);
        Matrix T56LeftLeg = Matrix.createDHTransformation(0, -PI / 2, 0, 0 + theta6);
        Matrix RzLeftLeg = Matrix.createRotationZ(PI);
        Matrix RyLeftLeg = Matrix.createRotationY(-PI / 2);
        Matrix AendLeftLeg = Matrix.createTranslation(0, 0, -FOOT_HEIGHT);

        Matrix result = AbaseLeftLeg.mult(T01LeftLeg).mult(T12LeftLeg).mult(T23LeftLeg).mult(T34LeftLeg)
                .mult(T45LeftLeg).mult(T56LeftLeg).mult(RzLeftLeg).mult(RyLeftLeg).mult(AendLeftLeg);
        return result;
    }

    /**
     * Calculates forward transformation matrix of right leg kinematics chain.
     * 
     * @param theta1
     *            theta1 angle (right hip yawpitch) in radians.
     * @param theta2
     *            theta2 angle (right hip roll) in radians.
     * @param theta3
     *            theta3 angle (right hip pitch) in radians.
     * @param theta4
     *            theta4 angle (right knee pitch) in radians.
     * @param theta5
     *            theta5 angle (right foot pitch) in radians.
     * @param theta6
     *            theta6 angle (right foot roll) in radians.
     * @return transformation {@link Matrix}.
     * @see Joint
     */
    public Matrix getForwardRightLeg(double theta1, double theta2, double theta3, double theta4, double theta5,
            double theta6)
    {
        Matrix AbaseRightLeg = Matrix.createTranslation(0, -HIP_OFFSET_Y, -HIP_OFFSET_Z);
        Matrix T01RightLeg = Matrix.createDHTransformation(0, -PI / 4, 0, -PI / 2 + theta1);
        Matrix T12RightLeg = Matrix.createDHTransformation(0, -PI / 2, 0, -PI / 4 + theta2);
        Matrix T23RightLeg = Matrix.createDHTransformation(0, PI / 2, 0, 0 + theta3);
        Matrix T34RightLeg = Matrix.createDHTransformation(-THIGH_LENGHT, 0, 0, 0 + theta4);
        Matrix T45RightLeg = Matrix.createDHTransformation(-TIBIA_LENGHT, 0, 0, 0 + theta5);
        Matrix T56RightLeg = Matrix.createDHTransformation(0, -PI / 2, 0, 0 + theta6);
        Matrix RzRightLeg = Matrix.createRotationZ(PI);
        Matrix RyRightLeg = Matrix.createRotationY(-PI / 2);
        Matrix AendRightLeg = Matrix.createTranslation(0, 0, -FOOT_HEIGHT);

        Matrix result = AbaseRightLeg.mult(T01RightLeg).mult(T12RightLeg).mult(T23RightLeg).mult(T34RightLeg)
                .mult(T45RightLeg).mult(T56RightLeg).mult(RzRightLeg).mult(RyRightLeg).mult(AendRightLeg);
        return result;
    }

    /**
     * Calculates result of inverse kinematics for left arm for given
     * endposition and orientation.
     * 
     * @param endpoint
     *            point in 3D space of end effector.
     * @param angle
     *            end orientation of end effector in 3D space about
     *            corresponding axes.
     * @return sequence of angle values for joints of left arm.
     * @see Joint
     */
    public Map<Joint, Double> getInverseLeftArm(Point3D endpoint, Orientation angle)
    {
        return new LeftArmIk(endpoint, angle).getResult();
    }

    /**
     * Calculates result of inverse kinematics for right arm for given
     * endposition and orientation.
     * 
     * @param endpoint
     *            point in 3D space of end effector.
     * @param angle
     *            end orientation of end effector in 3D space about
     *            corresponding axes.
     * @return sequence of angle values for joints of right arm.
     * @see Joint
     */
    public Map<Joint, Double> getInverseRightArm(Point3D endpoint, Orientation angle)
    {
        return new RightArmIk(endpoint, angle).getResult();
    }

    /**
     * Calculates result of inverse kinematics for left leg for given
     * endposition and orientation.
     * 
     * @param endpoint
     *            point in 3D space of end effector.
     * @param angle
     *            end orientation of end effector in 3D space about
     *            corresponding axes.
     * @return sequence of angle values for joints of left leg.
     * @see Joint
     */
    public Map<Joint, Double> getInverseLeftLeg(Point3D endpoint, Orientation angle)
    {
        return new LeftLegIk2(endpoint, angle).getResult();
    }

    /**
     * Calculates result of inverse kinematics for right leg for given
     * endposition and orientation.
     * 
     * @param endpoint
     *            point in 3D space of end effector.
     * @param angle
     *            end orientation of end effector in 3D space about
     *            corresponding axes.
     * @return sequence of angle values for joints of right leg.
     * @see Joint
     */
    public Map<Joint, Double> getInverseRightLeg(Point3D endpoint, Orientation angle)
    {
        return new RightLegIk(endpoint, angle).getResult();
    }
}
