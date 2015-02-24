package sk.fiit.jim.decision.situation;

import java.util.List;

import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.agent.models.Player;

/**
 * @author Samuel Benkovi�
 *
 * Method that chack if enemy is infront of me.
 * @return boolean - Returns true only if someone from opposite team is in front of player.
 */

public class EnemyInFrontOfMe extends Situation {

	private double InFrontOfMeRangeX = 4;
	private double InFrontOfMeRangeY = 3;
	
	List<Player> opponents = WorldModel.getInstance().getOpponentPlayers();
	AgentModel agent = AgentModel.getInstance();
	public boolean checkSituation() {
		
			for (Player p : opponents) {
				if ((p.getPosition().getX() - agent.getPosition().getX()) > InFrontOfMeRangeX  ) {
					if ((p.getPosition().getY() - agent.getPosition().getY()) > InFrontOfMeRangeY  ) {
						return true;
					}
				}
			}
		return false;
	}
}
