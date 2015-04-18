package sk.fiit.jim.agent.skills.dynamic;

import static java.lang.Math.pow;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sk.fiit.jim.agent.moves.EffectorData;
import sk.fiit.jim.agent.moves.LowSkill;
import sk.fiit.jim.agent.moves.LowSkills;
import sk.fiit.jim.agent.moves.Phase;
import sk.fiit.robocup.library.geometry.Point3D;
import sk.fiit.robocup.library.geometry.Vector3D;

/**
 * 
 * @author Pidanic
 *
 */
public class DirectionalKick extends DynamicMove{

    private static boolean kicked = false;
    
    private final double angleDeg;
    
    public DirectionalKick(double angle)
    {
        angleDeg = angle;
    }
    
    public DirectionalKick(Point3D endpoint)
    {
        angleDeg = getAlpha(endpoint);
    }
    
    public DirectionalKick(Vector3D vector)
    {
        angleDeg = getAlpha(vector);
    }
    
    public DirectionalKick(Point3D ballStart, Point3D ballEnd)
    {
        angleDeg = getAlpha(ballStart, ballEnd);
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
        EffectorData ed = phase4.getEfectorData("LLE2");
        ed.endAngle = getLLE2fromAlpha(angleDeg);
    }
    
    // kubicka
    private static double getLLE2fromAlpha(double alpha)
    {
        double lle2 = 0.0051466062 * pow(alpha, 3) + 0.1822084261 * pow(alpha, 2) - 0.2905759535 * alpha + 13.3946227506;
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
    
    // kvadraticka
    private static double getLLE2fromAlpha2(double alpha)
    {
        double lle2 =  0.0769165501 * pow(alpha, 2) - 0.5679788145 * alpha + 15.4529727642;
        return lle2;
    }
    
    // linearna
    private static double getLLE2fromAlpha3(double alpha)
    {
        double lle2 = -1.605689631 * alpha + 14.8133378154;
        return lle2;
    }
    
    
    private static double getAlpha(Point3D point)
    {
        return Math.toDegrees(Math.atan2(point.y, point.x));
    }
    
    private static double getAlpha(Vector3D vector)
    {
        return Math.toDegrees(Math.atan2(vector.getY(), vector.getX()));
    }
    
    private static double getAlpha(Point3D start, Point3D end)
    {
        double diffX = end.x - start.x;
        double diffY = end.y - start.y;
        double alpha = Math.toDegrees(Math.atan(diffY/diffX));
        return alpha;
    }
}