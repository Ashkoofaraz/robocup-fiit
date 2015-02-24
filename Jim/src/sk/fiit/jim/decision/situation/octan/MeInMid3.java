package sk.fiit.jim.decision.situation.octan;

import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.decision.situation.Situation;

/**
 * @author Martin Adamik
 * Situation is true when i stand in middle between 3L and 3R
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
 * @return boolean - Returns true only if i am standing in in middle between 3L and 3R
 */

public class MeInMid3 extends Situation {
	
	public static final double POSITION_MIN_X = 0;
	public static final double POSITION_MAX_X = 8;
	public static final double POSITION_MIN_Y = -3.5;
	public static final double POSITION_MAX_Y = 3.5;

	
	private AgentModel agentModel = AgentModel.getInstance();

	public boolean checkSituation() {
		if ((this.agentModel.getPosition().getX() > MeInMid3.POSITION_MIN_X)
				&& (this.agentModel.getPosition().getX() < MeInMid3.POSITION_MAX_X)
				&& (this.agentModel.getPosition().getY() > MeInMid3.POSITION_MIN_Y) 
				&& (this.agentModel.getPosition().getY() < MeInMid3.POSITION_MAX_Y)) {
			return true;
		}
		return false;
	}
}