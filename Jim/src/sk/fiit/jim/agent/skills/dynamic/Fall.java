package sk.fiit.jim.agent.skills.dynamic;

import sk.fiit.jim.agent.moves.LowSkill;
import sk.fiit.jim.agent.moves.LowSkills;
import sk.fiit.jim.agent.skills.HighSkill;

public class Fall extends HighSkill {

	@Override
	public LowSkill pickLowSkill() {
		return LowSkills.get("fall_back");
	}

	@Override
	public void checkProgress() throws Exception {
	}

}
