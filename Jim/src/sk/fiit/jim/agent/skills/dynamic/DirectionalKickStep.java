package sk.fiit.jim.agent.skills.dynamic;

import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.moves.LowSkill;
import sk.fiit.jim.agent.moves.LowSkills;

/**
 * 
 * Creates directional kick using small steps to be able to kick with desired
 * angle.
 * 
 * @author Pidanic
 *
 */
public class DirectionalKickStep extends DynamicMove
{

    private static final double DIST_STEP_VERY_SMALL = 0.012;

    private static final double DIST_STEP_SMALL = 0.0514;

    private static boolean kicked = false;

    private static boolean stepped = false;

    private static int maxSmallSteps = 0;

    private static int maxVerySmallSteps = 0;

    private static int smallSteps = 0;

    private static int verySmallSteps = 0;

    private static String smallStep;

    private static String verySmallStep;

    public DirectionalKickStep(double angle)
    {
        init(angle);
    }

    private static void init(double alpha)
    {
        double playerShiftFromBall = calculatePlayerShiftLinear(alpha);
        System.out.println("shift: " + playerShiftFromBall);
        double diff = playerShiftFromBall - AgentModel.getInstance().getPosition().getY();

        if(diff > 0)
        {
            verySmallStep = "step_left_very_small";
            smallStep = "step_left_small";
        }
        else
        {
            verySmallStep = "step_right_very_small";
            smallStep = "step_right_small";
        }

        maxSmallSteps = (int) (Math.abs(diff) / DIST_STEP_SMALL);
        double smallStepsDistance = maxSmallSteps * DIST_STEP_SMALL;

        diff = Math.abs(diff) - smallStepsDistance;
        maxVerySmallSteps = (int) (Math.abs(diff) / DIST_STEP_VERY_SMALL);

    }

    @Override
    public LowSkill pickLowSkill()
    {
        if(!stepped && smallSteps < maxSmallSteps)
        {
            smallSteps++;
            return LowSkills.get(smallStep);
        }
        if(!stepped && verySmallSteps < maxVerySmallSteps)
        {
            verySmallSteps++;
            return LowSkills.get(verySmallStep);
        }

        stepped = true;
        if(stepped && !kicked)
        {
            kicked = true;
            return LowSkills.get("kick_left_normal_stand");
        }
        return null;
    }

    @Override
    public void checkProgress() throws Exception
    {
    }

    private static double calculatePlayerShift(double alpha)
    {
        double shift = -2.798259731647e-7 * Math.pow(alpha, 4) + 1.48547703927838e-6 * Math.pow(alpha, 3)
                + 0.0002393458 * Math.pow(alpha, 2) - 0.0037578029 * alpha - 0.0854023051;
        return shift;
    }

    private static double calculatePlayerShiftLinear(double alpha)
    {
        if(alpha > 24.6)
        {
            return -0.11;
        }
        if(alpha < -25.9)
        {
            return 0.2;
        }
        if(alpha >= -25.9 && alpha <= 7)
        {
            return -0.0015539554 * alpha - 0.018402892;
        }
        if(alpha <= 24.6 && alpha > 7)
        {
            return -0.001020809 * alpha - 0.0849254591;
        }
        return 0;
    }

}
