package sk.fiit.jim.decision.tactic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import sk.fiit.jim.agent.AgentInfo;
import sk.fiit.jim.agent.highskill.DefaultHighSkill;
import sk.fiit.jim.agent.highskill.Turn;
import sk.fiit.jim.agent.highskill.move.MovementHighSkill.MovementSpeedEnum;
import sk.fiit.jim.agent.highskill.runner.HighSkillPlanner;
import sk.fiit.jim.agent.highskill.runner.HighSkillRunner;
import sk.fiit.jim.agent.skills.dynamic.DynamicKick;
import sk.fiit.jim.agent.skills.dynamic.DynamicKick2;
import sk.fiit.robocup.library.geometry.Vector3D;

/**
 * 
 * DefaultTactic.java
 * 
 * Default tactic to test highskills which is planning infinitelly
 * 
 * @Title Jim
 * @author Horvath
 * @author gitmen
 *
 */
public class DefaultTactic extends Tactic {
	
	public static final int DEFAULT_STATE = 1;
	
	private ArrayList<String> prescribedSituations = new ArrayList<String>(Arrays.asList("i_am_default"));

	@Override
	public boolean getInitCondition(List<String> currentSituations) {
		return true;
	}

	@Override
	public boolean getProgressCondition(List<String> currentSituations) {
		return true;
	}

	@Override
	protected int checkState(List<String> currentSituations) {
		return DefaultTactic.DEFAULT_STATE;
	}

	@Override
	public void run() {
		HighSkillPlanner planner = HighSkillRunner.getPlanner();
		if (planner.getNumberOfPlannedHighSkills() == 0){
			
			// write here your highskill
			planner.addHighskillToQueue(new DynamicKick2());
			AgentInfo.logState("DefaultTactic - DefaultHighSkill");
		}
	}

	@Override
	public List<String> getPrescribedSituations() {
		return this.prescribedSituations;
	}

}
