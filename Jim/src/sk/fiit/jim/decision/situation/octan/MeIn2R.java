package sk.fiit.jim.decision.situation.octan;

import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.decision.situation.Situation;

/**
 * @author Samuel Benkovic
 * Situation when i stand in 
 *		  
 *		  Enemy
 * ========___========
 * -------------------
 * 		4L	|	4R
 * -------------------
 * 		3L	|	3R
 * -------------------
 * 		2L	|	2R
 * -------------------
 * 		1L	|	1R
 * ========___========
 *         Our
 *         
 * @return boolean - Returns true only if i am standing in quadrant R2
 */

public class MeIn2R extends Situation {
	
	public static final double POSITION_MAX_X = 0;
	public static final double POSITION_MIN_X = -8;
	public static final double POSITION_MAX_Y = 0;

	private AgentModel agentModel = AgentModel.getInstance();

	public boolean checkSituation() {
		if ((this.agentModel.getPosition().getX() <= MeIn2R.POSITION_MAX_X)
				&& (this.agentModel.getPosition().getX() >= MeIn2R.POSITION_MIN_X)
				&& (this.agentModel.getPosition().getY() < MeIn2R.POSITION_MAX_Y)) {
			return true;
		}
		return false;
	}
}
