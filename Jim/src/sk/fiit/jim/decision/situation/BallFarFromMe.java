package sk.fiit.jim.decision.situation;

import sk.fiit.jim.agent.models.AgentModel;

public class BallFarFromMe extends Situation {

	@Override
	public boolean checkSituation() {
		if (AgentModel.getInstance().getDistanceFromBall() > Situation.EDGE_DISTANCE_FROM_BALL)
			return true;
		return false;
	}

}
