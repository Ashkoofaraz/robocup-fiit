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

public class DirectionalKickStepV5 extends DynamicMove {

    private static boolean stepped = false;
    
    private static boolean kicked = false;
    
    private double angle;
    
    public DirectionalKickStepV5(double kickAngle)
    {
        this.angle = kickAngle;
    }
    
    
	@Override
	public LowSkill pickLowSkill() {
        double ballPosY = WorldModel.getInstance().getBall().getPosition().getY();
        double agentPosY = AgentModel.getInstance().getPosition().getY();
        System.out.println("ball y: " + ballPosY);
        System.out.println("agent y: " + agentPosY);
        double requiredShiftFromBall = calculatePlayerShiftLinear(angle);
        System.out.println("requt shift: " +requiredShiftFromBall);
        double shiftDist = requiredShiftFromBall - agentPosY;
        
        System.out.println("step_shift: " + shiftDist );
        
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
    }
	
	@Override
	public void checkProgress() throws Exception {
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
            return -0.001020809*alpha - 0.0849254591;
        }
        return 0;
    }
	
	private int calculateLegAngle(double shift)
    {
        // TODO asi odstrnániť c, aby posun 0 bol aj uhol 0
        int angle = (int)(-334.2834552537 * shift - 3.9572967371);

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
