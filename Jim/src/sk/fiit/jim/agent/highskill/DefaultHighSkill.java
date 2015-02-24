package sk.fiit.jim.agent.highskill;

import sk.fiit.jim.agent.moves.LowSkill;
import sk.fiit.jim.agent.moves.LowSkills;
import sk.fiit.jim.agent.skills.HighSkill;

public class DefaultHighSkill extends HighSkill {

	private String lowSkillName = "";
	private boolean end = true;

	public DefaultHighSkill(String moveName) {
		this.lowSkillName = moveName;
	}

	@Override
	public LowSkill pickLowSkill() {

		// write here your lowskill
		if (end) {
			end = false;
			return LowSkills.get(lowSkillName);
		}
		return null;
	}

	@Override
	public void checkProgress() throws Exception {
		// Not supported yet.
	}

}
