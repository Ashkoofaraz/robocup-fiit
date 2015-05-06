package sk.fiit.jim.decision.tactic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sk.fiit.jim.agent.AgentInfo;
import sk.fiit.jim.agent.highskill.runner.HighSkillPlanner;
import sk.fiit.jim.agent.highskill.runner.HighSkillRunner;
import sk.fiit.jim.agent.skills.dynamic.DirectionalKick;
import sk.fiit.jim.agent.skills.dynamic.DirectionalKickStep;
import sk.fiit.jim.agent.skills.dynamic.DirectionalKickStepV2;
import sk.fiit.jim.agent.skills.dynamic.DirectionalKickStepV3;
import sk.fiit.jim.agent.skills.dynamic.DirectionalKickStepV4;
import sk.fiit.jim.agent.skills.dynamic.DirectionalKickStepV5;
import sk.fiit.jim.agent.skills.dynamic.DynamicKick2;

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
			planner.addHighskillToQueue(new DirectionalKickStepV4(-40));
			AgentInfo.logState("DefaultTactic - DefaultHighSkill");
		}
	}

	@Override
	public List<String> getPrescribedSituations() {
		return this.prescribedSituations;
	}

}
