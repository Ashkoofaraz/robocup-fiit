package sk.fiit.jim.decision.tactic.defense;

import java.util.List;

import sk.fiit.jim.decision.situation.BallIsOurs;
import sk.fiit.jim.decision.situation.EnemyInFrontOfMe;
import sk.fiit.jim.decision.tactic.Tactic;

/**
 * Parent class for defensive tactics
 * 
 * @author  Vladimir Bosiak <vladimir.bosiak@gmail.com>
 * @year	2013/2014
 * @team	RFC Megatroll
 */
public abstract class BaseDefend extends Tactic {

	/**
	 * Because defensive tactics have stop when we have ball & enemy is far
	 */
	@Override
	public boolean getProgressCondition(List<String> currentSituations) {
		if (currentSituations.contains(BallIsOurs.class.getName())
			&& !currentSituations.contains(EnemyInFrontOfMe.class.getName())) {
			return true;
		}
		return false;
	}
	
}
