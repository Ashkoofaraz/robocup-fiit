package sk.fiit.jim.decision.situation;

import java.util.List;

import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.Player;
import sk.fiit.jim.agent.models.TacticalInfo;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.decision.utils.DecisionUtils;

/**
 * @author Tomas Nemecek, Samuel Benkovic, Matej Badal <matejbadal@gmail.com>
 * Situation when our team has the nearest player to the ball
 *
 * @return boolean - Returns true only if someone from our team is the nearest to the ball
 */



public class BallIsOurs extends Situation  {
	 
	public boolean checkSituation() {
        TacticalInfo tacticalInfo = TacticalInfo.getInstance();

        if (DecisionUtils.getSituationFactory().createInstance(FightForBall.class.getName()).checkSituation()) {
            return false;
        }

        List<Player> allTeamMembers = WorldModel.getInstance().getAllTeamPLayers();
		Player ourMinPlayer = tacticalInfo.whoIsNearestToBall(allTeamMembers);

		List<Player> opponents = WorldModel.getInstance().getOpponentPlayers();
		Player theirMinPlayer = tacticalInfo.whoIsNearestToBall(opponents);

        if (theirMinPlayer == null) {
            return true;
        }

        return ourMinPlayer.getDistanceFromBall() < theirMinPlayer.getDistanceFromBall();
	}
}
