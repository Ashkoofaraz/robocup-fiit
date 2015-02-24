package sk.fiit.jim.decision.tactic.attack;

import java.util.List;

import sk.fiit.jim.decision.situation.BallIsOurs;
import sk.fiit.jim.decision.tactic.Tactic;

/**
 * Parent class for Offensive tactics
 * 
 * @author  Samuel Benkovic <sppred@gmail.com>
 * @year	2013/2014
 * @team	RFC Megatroll
 */
public abstract class BaseAttack extends Tactic {

    public static final double MICHALS_UBER_SUPER_TROUPER_CONST = 1.3;

    /**
	 * Condition must be true if the given tactic is currently being executed
	 *
	 * @param currentSituations list of current situations
	 * @return boollean
	 */
	public boolean getProgressCondition(List<String> currentSituations) {
        if (currentSituations.contains(BallIsOurs.class.getName())) {
            return true;
        }

        return false;
	}
}
