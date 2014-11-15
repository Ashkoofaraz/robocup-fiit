package sk.fiit.jim.agent.skills.dynamic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.jim.agent.moves.LowSkill;
import sk.fiit.jim.agent.moves.LowSkills;
import sk.fiit.jim.agent.moves.Phase;
import sk.fiit.jim.agent.moves.Phases;
import sk.fiit.jim.agent.skills.DynamicSkill;

/**
 * DynamicKickStraight.java
 * 
 * Class containing 1st dynamic skill extended from DynamicSkill. 
 * It is Dynamic straight kick based on kick_right_normal_stand or kick_left_normal_stand 
 * Some phases of kick are static and same as in normal kick. 
 * Others mainly controlling power of kick are dynamic and their value change based on distance to which should kick reach.
 * 
 * @author Pavol Mestanik
 */
public abstract class DynamicKickStraight extends DynamicSkill {
	/*
	 * Vytvara dynamicky kop
	 * Na zaciatku sa vypocitaju parametre kopu a nasledne sa nastavia fazy
	 * Kop je zalozeny na kick_right_normal_stand a kick_left_normal_stand ma 6 faz pricom dynamicky sa nastavuju fazy 2-3
	 * Je mozne bud vsetky fazy zadat priamo, alebo je mozne nacitat existujuci kop a nahradit niektore jeho fazy alebo len hodnoty vo fazach
	 */
	public LowSkill createDynamicKick(String side, double distance)
	{			
		List<Phase> phases = getBaseSkillPhases(side);
	    
		String ui = UUID.randomUUID().toString();

		ArrayList<String> types = new ArrayList<String>();
		types.add("kick");	 
		
		LowSkill ls = addSkill("dynamic_kick" + ui);

	    alterKickPhases(phases, side, distance);
			
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
	
	/*
	 * Vytvara kop
	 * Poradie faz je dolezite, budu na seba nadvazovat v poradi ako su pridane do zoznamu
	 */
	private void alterKickPhases(List<Phase> phases, String side, double distance) 
	{
		//Fazy v zakladnom kope zacinaju s default a fazou0, takze pre X-tu fazu v poradi treba hladat index X+1. Faza default je teda index 0, faza0 je index 1, ...
		double dynamicValue = calculateDynamicValue(distance);
		//Nahradi fazu 2 novou s dynamicky nastavovanou hodnotou klbu
		phases.remove(3);
		phases.add(3, createPhase2(side, dynamicValue));			
	}
	
	private Phase createPhase2(String side, double dynamicValue)
	{
		HashMap<Joint, Double> joints = new HashMap<Joint, Double>();
		
		if(side.equals("right"))
		{
			joints.put(Joint.RLE3, dynamicValue);
			joints.put(Joint.RLE4, -90d);
			joints.put(Joint.RLE5, 60d);
		}
		else
		{
			joints.put(Joint.LLE3, dynamicValue);
			joints.put(Joint.LLE4, -90d);
			joints.put(Joint.LLE5, 60d);
		}
		
		return createPhase(300, joints);
	}
	
	private double calculateDynamicValue(double distance) {
		double result = 40d;
		
		if(distance > 2.15d)
		{
			return result;
		}
		else if((distance <= 2.15d) && (distance >= 1.65d))
		{
			result = (distance - 6.464d) / (-0.1084d);
			result = roundResult(result);
		}
		else if((distance < 1.65d) && (distance >= 1.2d))
		{
			result = (distance - 4.11d) / (-0.04986d);
			result = roundResult(result);
		}
		else if((distance < 1.2d) && (distance >= 1.1d))
		{
			result = 58d;
		}
		else if((distance < 1.1d) && (distance > 1d))
		{
			result = 45d;
		}
		else if((distance <= 1d) && (distance >= 0.86d))
		{
			result = (distance - 2.34d) / (-0.03017d);
			result = roundResult(result);
		}
		else if((distance < 0.86d) && (distance >= 0.7d))
		{
			result = 49d;
		}
		else if((distance < 0.7d) && (distance > 0.58d))
		{
			result = 59d;
		}
		else if((distance <= 0.58d) && (distance > 0.45d))
		{
			result = (distance - 1.813d) / (-0.02095d);
			result = roundResult(result);
		}
		else
		{
			result = 65d;
		}
	
		return result;
	}
	
	private double roundResult(double rawValue)
	{
		return (double)Math.round(rawValue);
	}
}
