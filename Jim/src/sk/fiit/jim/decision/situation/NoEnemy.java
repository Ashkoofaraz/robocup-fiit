package sk.fiit.jim.decision.situation;

import java.util.List;

import sk.fiit.jim.agent.models.Player;
import sk.fiit.jim.agent.models.WorldModel;

/**
 * @author Samuel Benkovic 
 * @year 2013/2014
 * @team RFC Megatroll
 * Situation that is true if there is no enemy in the field
 */

public class NoEnemy extends Situation {
	private List<Player> opponents = WorldModel.getInstance()
			.getOpponentPlayers();
	
	

	@Override
	public boolean checkSituation() {
		if(opponents.size() == 0){return true;}
		else return false;
	}
	

}
