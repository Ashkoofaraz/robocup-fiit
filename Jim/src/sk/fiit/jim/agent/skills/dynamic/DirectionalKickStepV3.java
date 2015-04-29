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

public class DirectionalKickStepV3 extends DynamicMove {

    private static boolean stepped = false;
    
    private static boolean kicked = false;
    
    private double shift;
    
    private double angle;
    
    private String side = "left";
    
    public DirectionalKickStepV3(double kickAngle)
    {
        double agentActualY = AgentModel.getInstance().getPosition().getY();
        //System.out.println("Agent actual: " + agentActualY);
        double agentRequiredY = shiftLLE2_45(angle);
        //System.out.println("Agent requir: " + agentRequiredY);
        //this.angle = kickAngle;
        //this.shift = Math.abs(agentRequiredY - agentActualY);
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
	    double agentY = AgentModel.getInstance().getPosition().getY();
        double ballY = WorldModel.getInstance().getBall().getPosition().getY();
        System.out.println("agent y: " + agentY );
        System.out.println("ball  y: " + ballY);

        if(!stepped)
        {
            stepped = true;
            System.out.println("step");
            return createDynamicStep(side);
        }
        stepped = true;
        if(stepped && !kicked) {
            kicked = true;
            System.out.println("kick");
            return createDynamicKick(side);
        }
        
        return null;
	}
	
	public LowSkill createDynamicStep(String side)
    {           
        List<Phase> phases = getBaseSkillPhasesStep(side);
        
        String ui = UUID.randomUUID().toString();

        ArrayList<String> types = new ArrayList<String>();
        types.add("step");   
        
        LowSkill ls = addSkill("dynamic_step_" + ui);

        alterStepPhases(phases, side);
            
        addPhases(phases, ls.name);
        return ls;
    }

	private void alterStepPhases(List<Phase> phases, String side) 
    {
	    double shiftAngle = calculateAngle(this.shift);
	    System.out.println("Shift angle: " + shiftAngle);
	    if("left".equals(side))
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
//        if(side.equals("right"))
//        {
//            
//            baseSkill = LowSkills.get("kick_right_normal_stand");
//        }
//        else
//        {
            baseSkill = LowSkills.get("kick_left_normal_stand");
//        }
        
        return getPhasesForSkill(baseSkill);        
    }
    
    private void alterKickPhases(List<Phase> phases, String side) 
    {
        System.out.println(phases);
        Phase phase4 = phases.get(4);
        
        EffectorData ed = new EffectorData();
        ed.endAngle = 45;
        ed.effector = Joint.LLE2;
        phase4.effectors.add(ed);
    }
	
	@Override
	public void checkProgress() throws Exception {
	}
	
	 private static double shiftLLE2_15(double alpha)
	 {
	     double shift = 9.27342617283407e-7 * Math.pow(alpha, 3) - 7.0503887961265e-6 * Math.pow(alpha, 2) - 0.0032616022 * alpha - 0.043525855;
	     return shift;
	 }
	 
	 private static double shiftLLE2_45(double alpha)
	 {
	     double shift = -2.84119818365287e-6 * Math.pow(alpha, 3) - 0.0003790696 * Math.pow(alpha, 2) - 0.0169741164 * alpha - 0.2438899745;
	     return shift;
	 }
	 
	 private int calculateAngle(double shift)
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
