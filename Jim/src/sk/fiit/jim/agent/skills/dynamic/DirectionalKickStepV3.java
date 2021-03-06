package sk.fiit.jim.agent.skills.dynamic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.agent.moves.EffectorData;
import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.jim.agent.moves.LowSkill;
import sk.fiit.jim.agent.moves.LowSkills;
import sk.fiit.jim.agent.moves.Phase;

/**
 * 
 * Creates directional kick using small steps and fixing RLE2/LLE3 to 15 to be
 * able to kick with desired angle.
 * 
 * @author Pidanic
 *
 */
public class DirectionalKickStepV3 extends DynamicMove
{

    private static boolean stepped = false;

    private static boolean kicked = false;

    private double angle;

    public DirectionalKickStepV3(double kickAngle)
    {
        this.angle = kickAngle;
    }

    @Override
    public LowSkill pickLowSkill()
    {
        double ballPosY = WorldModel.getInstance().getBall().getPosition().getY();
        double agentPosY = AgentModel.getInstance().getPosition().getY();
        double requiredShiftFromBall = calculatePlayerShiftLinear(angle);
        double shiftDist = requiredShiftFromBall - agentPosY;

        System.out.println("step_shift: " + shiftDist);

        String step;
        if(shiftDist > 0)
        {
            step = "step_left_very_small";
        }
        else
        {
            step = "step_right_very_small";
        }
        if(!stepped)
        {
            stepped = true;
            return createDynamicStep(step, Math.abs(shiftDist));
        }
        if(!kicked)
        {
            kicked = true;
            return createDynamicKick("left");
        }
        return null;
    }

    public LowSkill createDynamicStep(String step, double shift)
    {
        List<Phase> phases = getBaseSkillPhasesStep(step);

        String ui = UUID.randomUUID().toString();

        ArrayList<String> types = new ArrayList<String>();
        types.add("step");

        LowSkill ls = addSkill("dynamic_step_" + ui);

        alterStepPhases(phases, step, shift);

        addPhases(phases, ls.name);
        return ls;
    }

    private void alterStepPhases(List<Phase> phases, String side, double shift)
    {
        double shiftAngle = calculateLegAngle(shift);
        System.out.println("Shift angle: " + shiftAngle);
        if("step_left_very_small".equals(side))
        {
            phases.get(1).getEfectorData("RLE2").endAngle = shiftAngle;
        }
        else
        {
            phases.get(1).getEfectorData("LLE2").endAngle = -shiftAngle;
        }
    }

    private List<Phase> getBaseSkillPhasesStep(String side)
    {
        LowSkill baseSkill = LowSkills.get(side);

        System.out.println("BaseSkill: " + baseSkill);
        return getPhasesForSkill(baseSkill);
    }

    public LowSkill createDynamicKick(String side)
    {
        List<Phase> phases = getBaseSkillPhases(side);

        String ui = UUID.randomUUID().toString();

        ArrayList<String> types = new ArrayList<String>();
        types.add("kick");

        LowSkill ls = addSkill("dynamic_kick_" + ui);

        alterKickPhases(phases, side);

        addPhases(phases, ls.name);
        return ls;
    }

    private List<Phase> getBaseSkillPhases(String side)
    {
        LowSkill baseSkill = null;
        if(side.equals("right"))
        {

            baseSkill = LowSkills.get("kick_right_normal_stand");
        }
        else
        {
            baseSkill = LowSkills.get("kick_left_normal_stand");
        }

        return getPhasesForSkill(baseSkill);
    }

    private void alterKickPhases(List<Phase> phases, String side)
    {
        System.out.println(phases);
        Phase phase4 = phases.get(4);

        EffectorData ed;
        if("left".equals(side))
        {
            ed = new EffectorData();
            ed.endAngle = 15;
            ed.effector = Joint.LLE2;
        }
        else
        {
            ed = new EffectorData();
            ed.endAngle = -15;
            ed.effector = Joint.RLE2;
        }
        phase4.effectors.add(ed);
    }

    @Override
    public void checkProgress() throws Exception
    {
    }

    // 15
    private static double calculatePlayerShiftLinear(double alpha)
    {
        double shift = 9.27342617283407e-7 * Math.pow(alpha, 3) - 7.0503887961265e-6 * Math.pow(alpha, 2)
                - 0.0032616022 * alpha - 0.043525855;
        return shift;
    }

    private int calculateLegAngle(double shift)
    {
        int angle = (int) (-334.2834552537 * shift - 3.9572967371);

        if(angle > 0)
        {
            angle = 0;
        }
        if(angle < -45)
        {
            angle = -45;
        }
        return angle;
    }
}
