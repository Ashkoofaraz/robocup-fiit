package sk.fiit.jim.decision.situation;

import java.util.List;

import sk.fiit.jim.agent.models.Player;
import sk.fiit.jim.agent.models.WorldModel;

/*
 * Author: michal petras (michpet@gmail.com)
 * Team RFC Megatroll 
 * 2013/2014
 * 
 */

public class MostEnemyInMid extends Situation {
	
	public static final double POSITION_Y_RIGHT = -5;
	public static final double POSITION_Y_LEFT = 5;
	public static final double POSITION_MAX_Y_LEFT = 11;
	public static final double POSITION_MAX_Y_RIGHT = -11;
	
	int count_left = 0;
	int count_right = 0;
	int count_mid = 0;

	private List<Player> opponents = WorldModel.getInstance()
			.getOpponentPlayers();

	public boolean checkSituation() {
		for (Player p : opponents) {
			if ( p.getPosition().getY() < MostEnemyInMid.POSITION_Y_RIGHT && 
					p.getPosition().getY() > MostEnemyInMid.POSITION_MAX_Y_RIGHT) {
			count_right++;	
			}
			if ( p.getPosition().getY() > MostEnemyInMid.POSITION_Y_LEFT && 
					p.getPosition().getY() < MostEnemyInMid.POSITION_MAX_Y_LEFT) {
			count_left++;	
			}
			if ( p.getPosition().getY() > MostEnemyInMid.POSITION_Y_RIGHT && 
					p.getPosition().getY() < MostEnemyInMid.POSITION_Y_LEFT) {
			count_mid++;	
			}
		}
		
		if(count_mid> count_left && count_mid> count_right){return true;}		
		return false;
	}
}