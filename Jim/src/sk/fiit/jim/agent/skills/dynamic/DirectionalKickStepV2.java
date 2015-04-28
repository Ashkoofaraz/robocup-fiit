package sk.fiit.jim.agent.skills.dynamic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.moves.LowSkill;
import sk.fiit.jim.agent.moves.LowSkills;
import sk.fiit.jim.agent.moves.Phase;

public class DirectionalKickStepV2 extends DynamicMove {

    private static boolean stepped = false;
    
    private static boolean kicked = false;
    
    private double shift;
    
    private double angle;
    
    private String side;
    
    public DirectionalKickStepV2(double angle)
    {
        double agentActualY = AgentModel.getInstance().getPosition().getY();
        System.out.println("Agent actual: " + agentActualY);
        double agentRequiredY = calculateKickPosY(angle);
        System.out.println("Agent requir: " + agentRequiredY);
        this.angle = angle;
        this.shift = Math.abs(agentRequiredY - agentActualY);
        if(agentRequiredY > agentActualY)
        {
            side = "left";
        }
        else
        {
            side = "right";
        }
    }
    
    
	@Override
	public LowSkill pickLowSkill() {
	    if(!stepped)
	    {
	        stepped = true;
	        return createDynamicKick(side);
	    }
	    stepped = true;
        if(stepped && !kicked) {
            kicked = true;
            return LowSkills.get("kick_left_normal_stand");
        }
        return null;
	}
	
	public LowSkill createDynamicKick(String side)
    {           
        List<Phase> phases = getBaseSkillPhases(side);
        
        String ui = UUID.randomUUID().toString();

        ArrayList<String> types = new ArrayList<String>();
        types.add("kick");   
        
        LowSkill ls = addSkill("dynamic_step_" + ui);

        alterKickPhases(phases, side);
            
        addPhases(phases, ls.name);
        return ls;
    }

	private void alterKickPhases(List<Phase> phases, String side) 
    {
        if("left".equals(side))
        {
            phases.get(1).getEfectorData("RLE2").endAngle = calculateRLE2Angle(shift);
        }
        else
        {
            phases.get(1).getEfectorData("LLE2").endAngle = -calculateRLE2Angle(shift);
        }
    }
	
	private List<Phase> getBaseSkillPhases(String side)
    {
        LowSkill baseSkill = null;
        if(side.equals("right"))
        {
            baseSkill = LowSkills.get("step_right_very_small");
        }
        else
        {
            baseSkill = LowSkills.get("step_left_very_small");
        }
        
        System.out.println("BaseSkill: " + baseSkill);
        return getPhasesForSkill(baseSkill);        
    }
	
	@Override
	public void checkProgress() throws Exception {
	}
	
	private int calculateRLE2Angle(double shift)
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
	
	 private static double calculateKickPosY(double alpha)
	 {
	     double shift = 2.28129772512726e-6 * Math.pow(alpha, 3)
	             + 5.50804199512415e-5 * Math.pow(alpha, 2) - 0.0040088375 * alpha - 0.0746776193;
	     return shift;
	 }

}
