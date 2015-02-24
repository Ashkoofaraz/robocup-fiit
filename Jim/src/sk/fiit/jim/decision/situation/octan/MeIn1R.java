package sk.fiit.jim.decision.situation.octan;

import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.decision.situation.Situation;

public class MeIn1R extends Situation {

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
	 * @return boolean - Returns true only if i am standing in quadrant R1
	 */
	public static final double POSITION_MAX_X = -8;
	public static final double POSITION_MAX_Y = 0;
	
	private AgentModel agentModel = AgentModel.getInstance();

	public boolean checkSituation() {
		if ((this.agentModel.getPosition().getX() < MeIn1R.POSITION_MAX_X)
				&& (this.agentModel.getPosition().getY() < MeIn1R.POSITION_MAX_Y)) {
			return true;
		}
		return false;
	}
}