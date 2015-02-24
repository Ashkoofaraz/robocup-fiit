package sk.fiit.jim.decision.situation;

import sk.fiit.jim.agent.models.AgentModel;

/**
 * @author Matej Badal <matejbadal@gmail.com>
 * @year 2013/2014
 * @team RFC Megatroll
 */
public class BallCloseToMe extends Situation {

    @Override
    public boolean checkSituation() {
        if (AgentModel.getInstance().getDistanceFromBall() < Situation.EDGE_DISTANCE_FROM_BALL) {
            return true;
        }
        return false;
    }
}
