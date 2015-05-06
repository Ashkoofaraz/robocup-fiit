package sk.fiit.jim.agent.skills.dynamic;

import static java.lang.Math.pow;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sk.fiit.jim.agent.moves.EffectorData;
import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.jim.agent.moves.LowSkill;
import sk.fiit.jim.agent.moves.LowSkills;
import sk.fiit.jim.agent.moves.Phase;

/**
 * 
 * @author Pidanic
 *
 */
public class DirectionalKick extends DynamicMove {

    private static boolean kicked = false;
    
    private final double angleDeg;
    
    public DirectionalKick(double angle)
    {
        angleDeg = angle;
    }
    
    
	@Override
	public LowSkill pickLowSkill() {
	    if(!kicked) {
	        kicked = true;
	        return createDynamicKick("left");
	    }
	    return null;
	}

	@Override
	public void checkProgress() throws Exception {
	}
	
	/*
     * Vytvara dynamicky kop
     * Na zaciatku sa vypocitaju parametre kopu a nasledne sa nastavia fazy
     * Kop je zalozeny na kick_right_normal_stand a kick_left_normal_stand ma 6 faz pricom dynamicky sa nastavuju fazy 2-3
     * Je mozne bud vsetky fazy zadat priamo, alebo je mozne nacitat existujuci kop a nahradit niektore jeho fazy alebo len hodnoty vo fazach
     */
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
        Phase phase4 = phases.get(4);
        
        EffectorData ed = new EffectorData();
        ed.endAngle = getLLE2fromAlpha2(angleDeg);
        ed.effector = Joint.LLE2;
        phase4.effectors.add(ed);
        
    }
    
    // kvadraticka
    private static double getLLE2fromAlpha2(double alpha)
    {
        double lle2 =  0.0769165501 * pow(alpha, 2) - 0.5679788145 * alpha + 15.4529727642;
        if(lle2 > 45)
        {
            lle2 = 45;
        }
        if(lle2 < 15)
        {
            lle2 = 15;
        }
        return lle2;
    }
    
}