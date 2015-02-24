package sk.fiit.jim.decision.situation;


import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.WorldModel;

/**
 * Situations when no team has ball posession
 *
 * @author  Matej Badal <matejbadal@gmail.com>
 * @year	2013/2014
 * @team	RFC Megatroll
 */
public class NoOneHasBall extends Situation {

	private AgentModel agentModel = AgentModel.getInstance();

	@Override
	public boolean checkSituation() {
		if (this.agentModel.getDistanceNereastToBall(WorldModel.getInstance()
				.getTeamPlayers()) >= Situation.EDGE_DISTANCE_FROM_BALL
				&& this.agentModel.getDistanceNereastToBall(WorldModel
				.getInstance().getOpponentPlayers()) >= Situation.EDGE_DISTANCE_FROM_BALL
				&& agentModel.getDistanceFromBall() >= Situation.EDGE_DISTANCE_FROM_BALL) {
			return true;
		}
		return false;
	}
}
