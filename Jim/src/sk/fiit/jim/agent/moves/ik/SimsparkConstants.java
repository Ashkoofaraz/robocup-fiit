package sk.fiit.jim.agent.moves.ik;

/**
 * 
 * Class for constants in Simspark simulation environment for Alderbaran Nao
 * robot.
 * 
 * @author Pidanic
 *
 */
final class SimsparkConstants
{
    /**
     * offset of from torso to neck at <tt>z</tt>-axis in millimeters
     */
    static final double NECK_OFFSET_Z = 90;

    /**
     * offset of from torso to shoulder at <tt>z</tt>-axis in millimeters
     */
    static final double SHOULDER_OFFSET_Y = 98;

    /**
     * offset of from shoulder to elbow at <tt>y</tt>-axis in millimeters
     */
    static final double ELBOW_OFFSET_Y = 0;

    /**
     * length of upper arm in millimeters
     */
    static final double UPPER_ARM_LENGTH = 90;

    /**
     * length of lower arm in millimeters
     */
    static final double LOWER_ARM_LENGTH = 105;

    /**
     * offset of from torso to shoulder at <tt>z</tt>-axis in milimeters
     */
    static final double SHOULDER_OFFSET_Z = 75;

    /**
     * offset of from elbow to hand at <tt>x</tt>-axis in milimeters
     */
    static final double HAND_OFFSET_X = 0;

    /**
     * offset of from torso to hip at <tt>z</tt>-axis in milimeters
     */
    static final double HIP_OFFSET_Z = 115;

    /**
     * offset of from torso to hip at <tt>y</tt>-axis in milimeters
     */
    static final double HIP_OFFSET_Y = 55;

    /**
     * length of thigh in millimeters
     */
    static final double THIGH_LENGHT = 120;

    /**
     * length of tibia in millimeters
     */
    static final double TIBIA_LENGHT = 100;

    /**
     * length of foot in millimeters
     */
    static final double FOOT_HEIGHT = 50;

    /**
     * offset of from elbow to hand at <tt>z</tt>-axis in milimeters
     */
    static final double HAND_OFFSET_Z = 9;

    private SimsparkConstants()
    {
        throw new AssertionError(SimsparkConstants.class.getName() + " can not be instatiated.");
    }
}
