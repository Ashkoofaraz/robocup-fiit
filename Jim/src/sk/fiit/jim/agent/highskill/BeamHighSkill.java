package sk.fiit.jim.agent.highskill;

import sk.fiit.jim.agent.highskill.runner.HighSkillRunner;
import sk.fiit.robocup.library.geometry.Vector3D;

/**
 * 
 * BeamHighSkill.java
 * 
 * This class is used to add beam highskill to HighSkillPlaner queue.
 * 
 * @Title Jim
 * @author Horvath
 * @author gitmen
 *
 */
public class BeamHighSkill extends AbstractHighSkill {

	/**
	 * 
	 * Method insert beam highskill to HighSkillPlaner.
	 * 
	 * @param position
	 */
	public void BeamAgent(Vector3D position) {
		HighSkillRunner.getPlanner().addHighskillToQueue(new Beam(position));
	}
	
}
