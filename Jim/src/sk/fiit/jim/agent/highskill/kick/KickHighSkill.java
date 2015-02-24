
package sk.fiit.jim.agent.highskill.kick;

import sk.fiit.jim.agent.highskill.AbstractHighSkill;
import sk.fiit.jim.agent.highskill.runner.HighSkillRunner;
import sk.fiit.jim.agent.skills.HighSkill;
import sk.fiit.robocup.library.geometry.Vector3D;

/**
 *
 * @author roman
 */
public class KickHighSkill extends AbstractHighSkill {
    
    public static enum KickTypeEnum {
		KICK_FAST(1), KICK_NORMAL(2), KICK_SLOW(3), KICK_STRONG(4), KICK_UNKNOWN(5);

		private int type;

		public int getType() {
			return type;
		}

		KickTypeEnum(int type) {
			this.type = type;
		}
	};
    
	public String kick(Vector3D kicktarget, KickTypeEnum kickType) {
		
            HighSkill kick = new Kick(kicktarget, kickType);		
		HighSkillRunner.getPlanner().addHighskillToQueue(kick);

		return ((Kick) kick).getLowSkillName();
	}
}
