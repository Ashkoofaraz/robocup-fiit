package sk.fiit.jim.decision.situation.octan;

import java.util.List;

import sk.fiit.jim.agent.models.Player;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.decision.situation.Situation;


/**
 * @author Samuel Benkovic
 * Situation is any teammate stand in this octan.
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
 * @return boolean - Returns true only if teammate is in octan L1
 */

public class TeammateIn1L extends Situation {

	public static final double POSITION_MAX_X = -8;
	public static final double POSITION_MIN_Y = 0;
	
	private List<Player> teammate = WorldModel.getInstance().getTeamPlayers();

	public boolean checkSituation() {
		for (Player p : teammate) {
			if ((p.getPosition().getX() < TeammateIn1L.POSITION_MAX_X)
					&& (p.getPosition().getY() > TeammateIn1L.POSITION_MIN_Y)) {
				return true;
			}
		}
		return false;
	}
}
