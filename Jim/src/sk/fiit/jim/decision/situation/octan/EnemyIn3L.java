package sk.fiit.jim.decision.situation.octan;

import java.util.List;

import sk.fiit.jim.agent.models.Player;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.decision.situation.Situation;

/**
 * @author Michal Petras
 * Situation is true if any enemy stand in this octan.
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
 * @return boolean - Returns true only if enemy is in octan L3
 */

public class EnemyIn3L extends Situation {
	
	public static final double POSITION_MAX_X = 8;
	public static final double POSITION_MIN_X = 0;
	public static final double POSITION_MIN_Y = 0;

	private List<Player> opponents = WorldModel.getInstance()
			.getOpponentPlayers();

	public boolean checkSituation() {
		for (Player p : opponents) {
			if (p.getPosition().getX() < EnemyIn3L.POSITION_MAX_X
					&& p.getPosition().getX() > EnemyIn3L.POSITION_MIN_X
					&& p.getPosition().getY() >= EnemyIn3L.POSITION_MIN_Y) {
				return true;
			}
		}
		return false;
	}
}