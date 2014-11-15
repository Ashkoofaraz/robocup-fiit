package sk.fiit.jim.agent.skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import sk.fiit.jim.agent.moves.*;
import sk.fiit.jim.log.Log;
import sk.fiit.jim.log.LogType;

/**
 * DynamicSkill.java
 * 
 * Base class for new dynamic skills. It is extended from classic HighSkill so dynamic skills can use same mechanism as classic skills.
 * After calculation and creation of phases there is no difference between dynamic and classic skill.
 * 
 * @author Pavol Mestanik
 */
public abstract class DynamicSkill extends HighSkill {    	
	/*
	 * Vytvori novy skill a priradi zaciatocnu fazu na zaklade jeho mena
	 */
	protected LowSkill addSkill(String name)//, List<String> types, String extendedFrom)
	{
		LowSkill ls = new LowSkill();
		ls.name = name;
		ls.initialPhase = name.concat("_Phase0");
		//ls.setExtendedFromMove(extendedFrom);
		//ls.setType(types);		
		
		LowSkills.addSkill(ls);
		
		return ls;
	}
	
	/*
	 * Pridava fazy do zoznamu faz.
	 * Pridavaju sa v poradi ako su v zozname a mena faz su generovane podla nazvu skillu ku ktoremu patria 
	 */
	protected void addPhases(List<Phase> phases, String skillName)
	{
		String phaseText = "_Phase";
	    String basePhaseName = skillName.concat(phaseText);
	    		
		for(int i = 0; i < phases.size(); ++i)
		{
			Phase p = phases.get(i);
			p.name = basePhaseName + i;
			
			if((i + 1) == phases.size())
			{
				p.isFinal = true;
				p.finalizationPhase = basePhaseName + i;
			}
			else
			{
				p.next = basePhaseName + (i + 1);
			}
			
			Phases.addPhase(p);			
		}
	}
	
	/*
	 * Vytvori novu fazu do ktorej je nasledne mozne pridavat nastavenia efektorov
	 */
	protected Phase createPhase(double duration, Map<Joint, Double> joints)
	{
		Phase p = new Phase();
		p.duration = roundToNearestTwenty(duration);
		p.effectors = new ArrayList<EffectorData>();
		
		Iterator<Entry<Joint, Double>> it = joints.entrySet().iterator();

		while(it.hasNext()) {			
			Entry<Joint, Double> entry = it.next();
			p.effectors.add(createEffectorData(entry.getKey(), entry.getValue()));
		}
		
		return p;
	}
	
	/*
	 * Vytvara novy udaj o zmene efektoru
	 */
	protected EffectorData createEffectorData(Joint j, double endAngle)
	{
		EffectorData ed = new EffectorData();
		ed.effector = j;
		ed.endAngle = endAngle;
		return ed;
	}
	
	/*
	 * Vrati vsetky fazy pre dany LowSkill v poradi. Potrebne pre nacitanie faz ako podkladu pre dynamicky kop.
	 */
	protected List<Phase> getPhasesForSkill(LowSkill baseSkill)
	{
		ArrayList<Phase> phases = new ArrayList<Phase>();
		String nextPhase = baseSkill.initialPhase;
		if(baseSkill != null)
		{
			while(!nextPhase.isEmpty())
			{
				Phase phase = Phases.get(nextPhase);
				phases.add(phase);
				if(phase.isFinal)
				{
					nextPhase = "";
				}
				else
				{
					nextPhase = phase.next;
				}
			}
		}
		
		return phases;
	}
	
	/*
	 * Zaokruhluje duration na nasobky 20ms kvoli nastaveniu servera a prevadza potom cele cislo na desatiny vydelenim 1000 
	 * Skopirovane z SkillFromXmlLoader.java
	 */
	protected double roundToNearestTwenty(Double supplied)
	{
		Double calculated = Double.valueOf(supplied);
		if (supplied.intValue() % 20 >= 10 || supplied < 20)
			calculated = supplied - (supplied.intValue() % 20) + 20.0;
		else
			calculated = supplied - (supplied.intValue() % 20);
		
		return calculated / 1000.0;
	}
}
