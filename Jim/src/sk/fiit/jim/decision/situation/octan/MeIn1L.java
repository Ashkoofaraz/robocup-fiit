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
 * @return boolean - Returns true only if i am standing in quadrant L1
 */

public class MeIn1L extends Situation {
	
	public static final double POSITION_MAX_X = -8;
	public static final double POSITION_MIN_Y = 0;

	private AgentModel agentModel = AgentModel.getInstance();

	public boolean checkSituation() {
		if ((this.agentModel.getPosition().getX() < MeIn1L.POSITION_MAX_X)
				&& (this.agentModel.getPosition().getY() >= MeIn1L.POSITION_MIN_Y)) {
			return true;
		}
		return false;
	}
}
