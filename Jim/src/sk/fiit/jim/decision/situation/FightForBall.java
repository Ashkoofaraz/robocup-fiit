package sk.fiit.jim.decision.situation;

import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.WorldModel;

/**
 * @author Samuel Benkovic <sppred@gmail.com>
 * @year 2013/2014
 * @team RFC Megatroll
 */
public class FightForBall extends Situation {

	private AgentModel agentModel = AgentModel.getInstance();

	/**
	 * Main method used to decide if player is in situation FightForBall.
	 */
	public boolean checkSituation() {
		if ((this.agentModel.getDistanceNereastToBall(WorldModel.getInstance()
				.getTeamPlayers()) < Situation.EDGE_DISTANCE_FROM_BALL || agentModel
				.getDistanceFromBall() < Situation.EDGE_DISTANCE_FROM_BALL)
				&& this.agentModel.getDistanceNereastToBall(WorldModel
						.getInstance().getOpponentPlayers()) < Situation.EDGE_DISTANCE_FROM_BALL) {
			return true;
		}
		return false;
	}

	/**
	 * Method to set agentModel
	 * 
	 * @param agentModel
	 */
	public void setAgentModel(AgentModel agentModel) {
		this.agentModel = agentModel;
	}
}
