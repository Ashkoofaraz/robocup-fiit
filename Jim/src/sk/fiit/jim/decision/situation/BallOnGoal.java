package sk.fiit.jim.decision.situation;

import sk.fiit.jim.agent.models.WorldModel;

public class BallOnGoal extends Situation {

	private WorldModel worldModel = WorldModel.getInstance();

	public boolean checkSituation() {
		return (this.worldModel.getBall().getPrediction().getX() >= 15);
	}
}