package sk.fiit.jim.decision.situation;

import sk.fiit.jim.agent.models.TacticalInfo;


/**
 * @author Samuel Benkovic
 * @team RFC Megatroll
 * @year 2013/2014
 */

public class BallNearestToMe extends Situation {

	@Override
	public boolean checkSituation() {
		if(TacticalInfo.getInstance().isBallNearestToMe()==true){
			return true;
		}
		return false;
	}

}
